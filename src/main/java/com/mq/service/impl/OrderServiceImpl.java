package com.mq.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mq.mapper.OrderMapper;
import com.mq.model.Order;
import com.mq.query.OrderQuery;
import com.mq.service.OrderService;
import com.mq.util.PageUtil;
import com.mq.vo.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Override
    public PageInfo<Order> findPage(OrderQuery query) {
        PageHelper.startPage(query.getPage(), query.getLength());
        PageInfo<Order> pageInfo = new PageInfo<>(orderMapper.selectByQuery(query));
        return pageInfo;
    }
}
