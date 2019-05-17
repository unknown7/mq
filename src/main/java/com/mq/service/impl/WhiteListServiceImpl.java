package com.mq.service.impl;

import com.mq.mapper.WhiteListMapper;
import com.mq.model.WhiteList;
import com.mq.service.WhiteListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WhiteListServiceImpl implements WhiteListService {
    @Resource
    private WhiteListMapper whiteListMapper;

    @Override
    public List<WhiteList> findAll() {
        List<WhiteList> all = whiteListMapper.findAll();
        return all;
    }
}
