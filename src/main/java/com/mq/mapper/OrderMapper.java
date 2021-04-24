package com.mq.mapper;

import com.mq.model.Order;
import com.mq.query.OrderQuery;
import com.mq.vo.OrderVo;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

	List<OrderVo> selectByQuery(OrderQuery query);

	Long selectNums(OrderQuery query);

	Order selectByOrderNo(String orderNo);

	Order selectPaidByInvitationId(Long invitationId);
}