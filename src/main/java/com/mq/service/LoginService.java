package com.mq.service;

import javax.servlet.http.HttpSession;

public interface LoginService {

    boolean loginAuth(String username, String password, HttpSession session) throws Exception;
}
