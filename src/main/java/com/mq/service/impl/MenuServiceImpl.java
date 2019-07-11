package com.mq.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mq.mapper.EmpMenuMapper;
import com.mq.mapper.MenuMapper;
import com.mq.model.EmpMenu;
import com.mq.model.Menu;
import com.mq.service.MenuService;
import com.mq.vo.MenuTree;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private EmpMenuMapper empMenuMapper;

    @Override
    public List<MenuTree> selectMenuTree(Long employeeId) {
        return menuMapper.selectMenuTree(employeeId);
    }

    @Override
    public List<Map<String, Object>> selectTreeView(Long employeeId) {
        List<MenuTree> menuTrees = menuMapper.selectMenuTree(null);
        List<Long> menus = empMenuMapper.selectMenusByEmpId(employeeId);
        List<Map<String, Object>> result = Lists.newArrayList();
        recursion(menuTrees, menus, result, 1);
        return result;
    }

    private void recursion(List<MenuTree> menuTrees, List<Long> menus, List<Map<String, Object>> parent, int level) {
        for (MenuTree menuTree : menuTrees) {
            Map<String, Object> item = Maps.newHashMap();
            item.put("id", menuTree.getId());
            item.put("level", level);
            item.put("text", menuTree.getmName());
            item.put("parentid", menuTree.getPid());
            item.put("icon", menuTree.getIcon());
            if (menus.contains(menuTree.getId())) {
                Map<String, Boolean> state = Maps.newHashMap();
                state.put("checked", true);
                state.put("selected", true);
                item.put("state", state);
            }
            if (menuTree.getChild() != null && menuTree.getChild().size() > 0) {
                List<Map<String, Object>> nodes = Lists.newArrayList();
                item.put("nodes", nodes);
                recursion(menuTree.getChild(), menus, nodes, ++level);
            }
            parent.add(item);
        }
    }

    @Override
    @Transactional
    public void saveRight(String ids, Long employeeId) {
        empMenuMapper.deleteByEid(employeeId);
        if (!StringUtils.isEmpty(ids)) {
            List<EmpMenu> list = Lists.newArrayList();
            for (String s : ids.split(",")) {
                EmpMenu empMenu = new EmpMenu();
                empMenu.setEid(employeeId);
                empMenu.setMid(Long.valueOf(s));
                empMenu.setCreateTime(new Date());
                empMenu.setUpdateTime(new Date());
                empMenu.setDelFlag(0);
                list.add(empMenu);
            }
            empMenuMapper.insertBatch(list);
        }
    }

    @Override
    @Transactional
    public void transaction() {
        Menu menu = new Menu();
        menu.setId(19L);
        menu.setmName("test");
        menuMapper.updateByPrimaryKeySelective(menu);
        System.err.println("已更新，等待sleep..");
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
