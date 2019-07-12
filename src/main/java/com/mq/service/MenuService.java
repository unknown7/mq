package com.mq.service;

import com.mq.vo.MenuTree;

import java.util.List;
import java.util.Map;

public interface MenuService {

    List<MenuTree> selectMenuTree(Long employeeId);

    List<Map<String, Object>> selectTreeView(Long employeeId);

    void saveRight(String ids, Long employeeId);

    void transaction(int i);
}
