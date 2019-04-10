package com.hodehtml.demo.service;

import com.hodehtml.demo.model.Login;

import java.util.List;
import java.util.Map;

/**
 * created by pht on 2019/3/25 0025
 */
public interface LoginService {

    public String Register(Login login)throws Exception;

    public Map<String,Object> Login(Login login);


}
