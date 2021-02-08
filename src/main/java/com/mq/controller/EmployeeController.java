package com.mq.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.mq.base.BaseController;
import com.mq.model.Employee;
import com.mq.query.EmployeeQuery;
import com.mq.service.EmployeeService;
import com.mq.service.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController extends BaseController {
    protected static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    @Resource
    private EmployeeService employeeService;
    @Resource
    private MenuService menuService;

    @RequestMapping("/index")
    public String index() {
        return "employee";
    }

    @RequestMapping("/findPage")
    @ResponseBody
    public String find(HttpServletRequest request, EmployeeQuery query) {
        PageInfo<Employee> page = employeeService.findPage(query);
        return JSON.toJSONString(page);
    }

    @RequestMapping("/save")
    @ResponseBody
    public String save(@RequestParam("avatar") MultipartFile avatar, HttpServletRequest request) {
        try {
            String id = request.getParameter("id");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String employeeName = request.getParameter("employeeName");
            String birth = request.getParameter("birth");
            String gender = request.getParameter("gender");
            String mobile = request.getParameter("mobile");
            String email = request.getParameter("email");
            String openId = request.getParameter("openId");
            String profitRate = request.getParameter("profitRate");
            employeeService.save(id, username, password, employeeName, birth, gender, mobile, email, openId, profitRate, avatar);
            return success("员工信息已保存");
        } catch (Exception e) {
            logger.error(e.getMessage());
            return error("员工信息保存时出现问题");
        }
    }

    @RequestMapping("/remove")
    @ResponseBody
    public String remove(Long id) {
        try {
            employeeService.remove(id);
            return success("员工已删除");
        } catch (Exception e) {
            e.printStackTrace();
            return error("员工删除时出现问题");
        }
    }

    @RequestMapping("/selectOne")
    @ResponseBody
    public String selectOne(Long id) {
        Employee employee = employeeService.selectOneById(id);
        return JSON.toJSONString(employee);
    }

    @RequestMapping("/selectTreeView")
    @ResponseBody
    public String selectTreeView(Long id) {
        List<Map<String, Object>> treeView = menuService.selectTreeView(id);
        return JSON.toJSONString(treeView);
    }

    @RequestMapping("/saveRight")
    @ResponseBody
    public String saveRight(String ids, Long employeeId) {
        try {
            menuService.saveRight(ids, employeeId);
            return success("员工信息已保存");
        } catch (Exception e) {
            e.printStackTrace();
            return error("员工信息保存时出现问题");
        }
    }
}
