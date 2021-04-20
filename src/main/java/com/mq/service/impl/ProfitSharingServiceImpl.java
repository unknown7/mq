package com.mq.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mq.base.BusinessException;
import com.mq.base.Enums;
import com.mq.base.GlobalConstants;
import com.mq.base.RspCode;
import com.mq.mapper.PaymentResultMapper;
import com.mq.mapper.ProfitSharingMapper;
import com.mq.model.*;
import com.mq.query.ProfitSharingQuery;
import com.mq.service.*;
import com.mq.util.DateUtil;
import com.mq.util.SignUtil;
import com.mq.util.OrderNoGenerator;
import com.mq.vo.ProfitSharingVo;
import com.mq.wx.base.WxAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ProfitSharingServiceImpl implements ProfitSharingService {

	protected static final Logger logger = LoggerFactory.getLogger(ProfitSharingServiceImpl.class);

	@Resource
	private WxAPI wxAPI;

	@Resource
	private InvitationRecordService invitationRecordService;

	@Resource
	private OrderService orderService;

	@Resource
	private RewardPointsService rewardPointsService;

	@Resource
	private GlobalConstants globalConstants;

	@Resource
	private UserService userService;

	@Resource
	private PaymentResultMapper paymentResultMapper;

	@Resource
	private EmployeeService employeeService;

	@Resource
	private ProfitSharingMapper profitSharingMapper;

	@Resource
	private OrderNoGenerator orderNoGenerator;

	@Override
	public void profitShare() {
		Date yesterday = DateUtil.addDay(new Date(), -1);
		String prefix = DateUtil.dateToString(yesterday);
		Date begin = DateUtil.stringToDate(prefix + " 00:00:00", DateUtil.DATE_TIME_FORMAT);
		Date end = DateUtil.stringToDate(prefix + " 23:59:59", DateUtil.DATE_TIME_FORMAT);
		List<InvitationRecord> profitSharableList = invitationRecordService.findProfitSharableList(begin, end);
		if (!CollectionUtils.isEmpty(profitSharableList)) {
			profitSharableList.forEach(invitationRecord -> {
				try {
					this.doShare(invitationRecord);
				} catch (Exception e) {
					logger.error("分账失败，{}", JSON.toJSONString(invitationRecord), e);
				}
			});
		}
	}

	@Override
	public PageInfo<ProfitSharingVo> findPage(ProfitSharingQuery query) {
		PageHelper.startPage(query.getPage(), query.getLength());
		PageInfo<ProfitSharingVo> pageInfo = new PageInfo<>(profitSharingMapper.selectByQuery(query));
		return pageInfo;
	}

	@Transactional
	public void doShare(InvitationRecord invitationRecord) throws Exception {
		Order order = orderService.getByInvitationId(invitationRecord.getId());
		RewardPoints rewardPoints = rewardPointsService.getByOrderId(order.getId());
		PaymentResult paymentResult = paymentResultMapper.selectByOutTradeNo(order.getOrderNo());
		withDrawCheck(order, invitationRecord, rewardPoints, paymentResult);
		User user = userService.getById(invitationRecord.getInviterId());
		Employee employee = employeeService.getByOpenId(user.getOpenId());

		String profitSharingNo = orderNoGenerator.nextSharingNo();
		ProfitSharingRequest request = new ProfitSharingRequest();
		request.setMchId(globalConstants.getMchId());
		request.setAppId(globalConstants.getAppId());
		request.setNonceStr(SignUtil.md5(UUID.randomUUID().toString()));
		request.setTransactionId(paymentResult.getTransactionId());
		request.setOutOrderNo(profitSharingNo);
		ProfitSharingRequest.Receiver receiver = new ProfitSharingRequest.Receiver();
		receiver.setType("PERSONAL_OPENID");
		receiver.setAccount(user.getOpenId());
		receiver.setAmount(rewardPoints.getPoints().multiply(new BigDecimal("100")).setScale(0, BigDecimal.ROUND_HALF_UP).toBigInteger().intValue());
		receiver.setDescription("微信小程序分账");
		receiver.setName(employee.getEmployeeName());
		request.setReceivers(Collections.singletonList(receiver));
		ProfitSharingResponse response = wxAPI.profitSharing(request);
		if (response.success()) {
			Date now = new Date();
			ProfitSharing profitSharing = new ProfitSharing();
			profitSharing.setProfitSharingNo(profitSharingNo);
			profitSharing.setOrderNo(order.getOrderNo());
			profitSharing.setSharingAmount(rewardPoints.getPoints());
			profitSharing.setOrderAmount(order.getTotalAmount());
			profitSharing.setCreatedTime(now);
			profitSharing.setModifiedTime(now);
			profitSharing.setDelFlag(Boolean.FALSE);
			profitSharing.setTransactionId(response.getTransactionId());
			profitSharing.setOrderId(response.getOrderId());
			profitSharing.setEmployeeId(employee.getId());

			rewardPoints.setPointsStatus(Enums.PointsStatus.WITHDRAW_SUCCESS.getKey());

			invitationRecord.setStatus(Enums.InvitationStatus.SUCCESS.getKey());

			profitSharingMapper.insertSelective(profitSharing);
			rewardPointsService.modify(rewardPoints);
			invitationRecordService.withdrawSuccess(invitationRecord.getId());
		} else {
			throw new BusinessException(response.getErrCodeDes(), RspCode.PROFIT_SHARING_ERROR.getCode());
		}
	}

	private void withDrawCheck(Order order, InvitationRecord invitationRecord, RewardPoints rewardPoints, PaymentResult paymentResult) {
		Objects.requireNonNull(order, "订单不能为空！");
		Objects.requireNonNull(invitationRecord, "邀约记录不能为空！");
		Objects.requireNonNull(rewardPoints,"奖励记录不能为空！");
		Objects.requireNonNull(paymentResult,"支付结果不能为空！");
		if (!Enums.OrderStatus.PAID.getKey().equals(order.getOrderStatus())) {
			throw new BusinessException(RspCode.ORDER_STATUS_ERROR.getMsg() + order.getOrderNo(), RspCode.ORDER_STATUS_ERROR.getCode());
		}
		if (!Enums.InvitationStatus.PURCHASED.getKey().equals(invitationRecord.getStatus())) {
			throw new BusinessException(RspCode.INVITATION_STATUS_ERROR.getMsg() + invitationRecord.getId(), RspCode.INVITATION_STATUS_ERROR.getCode());
		}
		if (!Enums.PointsStatus.UNUSED.getKey().equals(rewardPoints.getPointsStatus())) {
			throw new BusinessException(RspCode.REWARDS_STATUS_ERROR.getMsg() + rewardPoints.getId(), RspCode.REWARDS_STATUS_ERROR.getCode());
		}
		if (!"SUCCESS".equals(paymentResult.getReturnCode()) || !"SUCCESS".equals(paymentResult.getResultCode())) {
			throw new BusinessException(RspCode.PAYMENT_RESULT_ERROR.getMsg() + paymentResult.getId(), RspCode.PAYMENT_RESULT_ERROR.getCode());
		}
	}
}
