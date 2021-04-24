package com.mq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mq.base.Enums;
import com.mq.mapper.OrderMapper;
import com.mq.model.Order;
import com.mq.query.OrderQuery;
import com.mq.service.OrderService;
import com.mq.vo.OrderVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Override
    public PageInfo<OrderVo> findPage(OrderQuery query) {
        PageHelper.startPage(query.getPage(), query.getLength());
        PageInfo<OrderVo> pageInfo = new PageInfo<>(orderMapper.selectByQuery(query));
        if (!CollectionUtils.isEmpty(pageInfo.getList())) {
            pageInfo.getList().forEach(order -> {
                order.setOrderStatus(Enums.OrderStatus.getByKey(order.getOrderStatus()).getValue());
                order.setGoodsType(Enums.PurchaseType.getByKey(order.getGoodsType()).getValue());
            });
        }
        return pageInfo;
    }

	@Override
	public Order getPaidByInvitationId(Long invitationId) {
		Order order = orderMapper.selectPaidByInvitationId(invitationId);
		return order;
	}
}
