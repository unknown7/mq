package com.mq.mapper;

import com.mq.model.User;
import com.mq.query.UserQuery;
import com.mq.vo.UserVo;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

	User selectByOpenId(String openId);

	List<User> selectByQuery(UserQuery query);

	List<UserVo> selectVoByQuery(UserQuery query);

	Long selectNums(UserQuery query);

	User selectBySkey(String skey);
}