package com.mq.service;

import com.mq.model.Order;
import com.mq.query.OrderQuery;
import com.mq.vo.Page;

public interface OrderService {
    Page<Order> findPage(OrderQuery query);
}
