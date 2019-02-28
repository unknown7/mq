package com.mq.base;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.mq.model.Employee;

import javax.servlet.http.HttpSession;
import java.util.Map;

public class BaseController {

    public String success() {
        return success("success");
    }

    public String success(String msg) {
        Map result = Maps.newHashMap();
        result.put("success", true);
        result.put("msg", msg);
        return JSON.toJSONString(result);
    }

    public String error() {
        return error("error");
    }

    public String error(String msg) {
        Map result = Maps.newHashMap();
        result.put("success", false);
        result.put("msg", msg);
        return JSON.toJSONString(result);
    }

    protected Employee currentEmployee(HttpSession session) {
        Employee employee = null;
        Object object = session.getAttribute(WebSecurityConfig.SESSION_KEY);
        if (object != null) {
            employee = (Employee) object;
        }
        return employee;
    }
}
