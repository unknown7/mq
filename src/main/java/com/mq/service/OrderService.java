package com.mq.service;

import com.github.pagehelper.PageInfo;
import com.mq.model.Order;
import com.mq.query.OrderQuery;
import com.mq.vo.OrderVo;

public interface OrderService {

    PageInfo<OrderVo> findPage(OrderQuery query);

    Order getByInvitationId(Long invitationId);
}
