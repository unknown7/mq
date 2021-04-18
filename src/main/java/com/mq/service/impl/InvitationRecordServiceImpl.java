package com.mq.service.impl;

import com.mq.base.Enums;
import com.mq.mapper.InvitationRecordMapper;
import com.mq.mapper.ShareCardMapper;
import com.mq.model.InvitationRecord;
import com.mq.model.ShareCard;
import com.mq.service.InvitationRecordService;
import com.mq.service.UserService;
import com.mq.vo.InvitationRecordVo;
import com.mq.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class InvitationRecordServiceImpl implements InvitationRecordService {

	@Resource
	private InvitationRecordMapper invitationRecordMapper;

	@Resource
	private ShareCardMapper shareCardMapper;

	@Resource
	private UserService userService;

	@Override
	@Transactional
	public void initRecord(Long shareCardId, String skey) {

		InvitationRecord query = new InvitationRecord();
		query.setInviteeSkey(skey);
		query.setShareCardId(shareCardId);
		InvitationRecord history = invitationRecordMapper.selectRecentlyByQuery(query);
		if (history == null) {
			ShareCard shareCard = shareCardMapper.selectByPrimaryKey(shareCardId);

			Date now = new Date();
			InvitationRecord invitationRecord = new InvitationRecord();
			invitationRecord.setShareCardId(shareCardId);
			invitationRecord.setInviteeSkey(skey);
			invitationRecord.setStatus(Enums.InvitationStatus.SCANNED.getKey());
			invitationRecord.setDelFlag(Boolean.FALSE);
			invitationRecord.setCreatedTime(now);
			invitationRecord.setModifiedTime(now);
			invitationRecord.setInviterId(shareCard.getUserId());
			invitationRecord.setGoodsId(shareCard.getGoodsId());
			invitationRecord.setGoodsType(shareCard.getGoodsType());

			invitationRecordMapper.insertSelective(invitationRecord);
		}
	}

	@Override
	@Transactional
	public void registered(Long inviteeId, String skey) {
		InvitationRecord query = new InvitationRecord();
		query.setInviteeSkey(skey);
		query.setStatus(Enums.InvitationStatus.SCANNED.getKey());
		InvitationRecord invitationRecord = invitationRecordMapper.selectRecentlyByQuery(query);
		if (invitationRecord != null) {
			invitationRecord.setInviteeId(inviteeId);
			invitationRecord.setStatus(Enums.InvitationStatus.REGISTERED.getKey());
			invitationRecord.setModifiedTime(new Date());
			invitationRecordMapper.updateByPrimaryKeySelective(invitationRecord);
		}
	}

	@Override
	@Transactional
	public void purchased(Long invitationId) {
		InvitationRecord invitationRecord = new InvitationRecord();
		invitationRecord.setId(invitationId);
		invitationRecord.setStatus(Enums.InvitationStatus.PURCHASED.getKey());
		invitationRecord.setModifiedTime(new Date());
		invitationRecordMapper.updateByPrimaryKeySelective(invitationRecord);
	}

	@Override
	@Transactional
	public void usePoints(Long inviterId, Date unifiedOrderTime) {
		invitationRecordMapper.updateByInviterIdBeforeDate(
			inviterId,
			Enums.InvitationStatus.USED.getKey(),
			Enums.InvitationStatus.PURCHASED.getKey(),
			unifiedOrderTime
		);
	}

	@Override
	@Transactional
	public void withdrawApplyAll(Long inviterId) {
		invitationRecordMapper.updateByInviterIdBeforeDate(
			inviterId,
			Enums.InvitationStatus.APPLIED.getKey(),
			Enums.InvitationStatus.PURCHASED.getKey(),
			new Date()
		);
	}

	@Override
	@Transactional
	public void withdrawApply(Long id) {
		InvitationRecord invitationRecord = invitationRecordMapper.selectByPrimaryKey(id);
		if (invitationRecord != null && Enums.InvitationStatus.PURCHASED.getKey().equals(invitationRecord.getStatus())) {
			invitationRecord.setStatus(Enums.InvitationStatus.APPLIED.getKey());
			invitationRecordMapper.updateByPrimaryKeySelective(invitationRecord);
		}
	}

	@Override
	@Transactional
	public void approvalAgree(Long id) {
		InvitationRecord invitationRecord = invitationRecordMapper.selectByPrimaryKey(id);
		if (invitationRecord != null && Enums.InvitationStatus.APPLIED.getKey().equals(invitationRecord.getStatus())) {
			invitationRecord.setStatus(Enums.InvitationStatus.AGREED.getKey());
			invitationRecordMapper.updateByPrimaryKeySelective(invitationRecord);
		}
	}

	@Override
	@Transactional
	public void approvalReject(Long id) {
		InvitationRecord invitationRecord = invitationRecordMapper.selectByPrimaryKey(id);
		if (invitationRecord != null && Enums.InvitationStatus.APPLIED.getKey().equals(invitationRecord.getStatus())) {
			invitationRecord.setStatus(Enums.InvitationStatus.REJECTED.getKey());
			invitationRecordMapper.updateByPrimaryKeySelective(invitationRecord);
		}
	}

	@Override
	public InvitationRecord getBySkey(String skey, String status) {
		InvitationRecord condition = new InvitationRecord();
		condition.setInviteeSkey(skey);
		condition.setStatus(status);
		InvitationRecord invitationRecord = invitationRecordMapper.selectRecentlyByQuery(condition);
		return invitationRecord;
	}

	@Override
	public InvitationRecord getByInviteeIdAndGoodsId(Long inviteeId, Long goodsId, String goodsType) {
		InvitationRecord condition = new InvitationRecord();
		condition.setInviteeId(inviteeId);
		condition.setGoodsId(goodsId);
		condition.setGoodsType(goodsType);
		InvitationRecord invitationRecord = invitationRecordMapper.selectRecentlyByQuery(condition);
		return invitationRecord;
	}

	@Override
	public List<InvitationRecord> findProfitSharableList(Date begin, Date end) {
		List<InvitationRecord> invitationRecords = invitationRecordMapper.selectProfitSharableByRange(begin, end);
		return invitationRecords;
	}

	@Override
	@Transactional
	public void withdrawSuccess(Long id) {
		invitationRecordMapper.updateStatusByPrimaryKey(id, Enums.InvitationStatus.SUCCESS.getKey());
	}

	@Override
	public List<InvitationRecordVo> findByInviterSkey(String skey) {
		UserVo userVo = userService.getVoBySkey(skey);
		List<InvitationRecordVo> invitationRecords = invitationRecordMapper.selectByInviterId(userVo.getId());
		if (!CollectionUtils.isEmpty(invitationRecords)) {
			invitationRecords.forEach(record -> {
				record.setStatusDesc(Enums.InvitationStatus.getByKey(record.getStatus()).getValue());
			});
		}
		return invitationRecords;
	}
}
