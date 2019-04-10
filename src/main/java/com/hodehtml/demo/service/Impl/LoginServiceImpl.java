package com.hodehtml.demo.service.Impl;

import com.hodehtml.demo.dao.LoginDao;
import com.hodehtml.demo.model.Login;
import com.hodehtml.demo.service.LoginService;
import com.hodehtml.demo.utils.MD5;
import com.hodehtml.demo.utils.ValidatorUtils;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.readers.parameter.ModelAttributeParameterExpander;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program demo
 * @date 2019/3/25 0025
 * @author pht
 */
@Service("LoginService")
public class LoginServiceImpl implements LoginService {


    @Resource
    private LoginDao loginDao;

    @Override
    public String Register(Login login) throws Exception{
        List<Login> list = loginDao.isTrue(login.getUserPhone());
        String message = null;
        if(list.size()>0){
            message = "该手机号已存在";
        }else {
            ValidatorUtils.isLoginMobile(login.getUserPhone());
            String password = MD5.md5(login.getPassword(),"utf-8");
            String token = MD5.md5(login.getPassword()+login.getUserPhone()+login.getUserName(),"utf-8");
            login.setPassword(password);
            login.setToken(token);
            loginDao.Register(login);
            message = "注册成功";
        }
       return message;
    }

    @Override
    public Map<String,Object> Login(Login login){
        Map<String,Object> map = new HashMap<String, Object>();
        try{
            ValidatorUtils.isLoginMobile(login.getUserPhone());
            String password = MD5.md5(login.getPassword(),"utf-8");
            String token = MD5.md5(login.getPassword()+login.getUserPhone()+login.getUserName(),"utf-8");
            login.setPassword(password);
            login.setToken(token);
            Login login1 = loginDao.list(login);
            map.put("login1",login1);
        }catch (Exception e){
            map.put("login1",null);
            return map;
        }
      return map;
    }

}
