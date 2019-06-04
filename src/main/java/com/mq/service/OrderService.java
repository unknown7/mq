package com.mq.service;

import com.github.pagehelper.PageInfo;
import com.mq.model.Order;
import com.mq.query.OrderQuery;
import com.mq.vo.Page;

public interface OrderService {
    PageInfo<Order> findPage(OrderQuery query);
}
