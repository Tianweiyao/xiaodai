package com.hodehtml.demo.controller;/**
 * created by pht on 2019/3/25 0025
 */

import com.hodehtml.demo.model.Login;
import com.hodehtml.demo.service.LoginService;
import com.hodehtml.demo.utils.Code;
import com.hodehtml.demo.utils.JedisUtil;
import com.hodehtml.demo.utils.LoginUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pht
 * @program demo
 * @date 2019/3/25 0025
 */
@Controller
@RequestMapping("Login")
public class LoginController {


    @Autowired
    private LoginService loginService;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private JedisUtil jedisUtil;

    @ApiOperation(value = "注册")
    @ResponseBody
    @RequestMapping(value = "/zhuCe", method = RequestMethod.POST)
    public String zhuCe(@RequestBody Login login) throws Exception {

        String message = loginService.Register(login);
        return message;
    }

    @ApiOperation(value = "登录")
    @ResponseBody
    @RequestMapping(value = "/Register", method = RequestMethod.POST)
    public Map<String, Object> Register(HttpServletRequest request, @RequestBody Login login) throws Exception {
        HttpSession session = request.getSession();
        Map<String, Object> map = loginService.Login(login);
        Login login1 = (Login) map.get("login1");
        if (login1 != null) {
            session.setAttribute("token", login.getToken());
            session.setAttribute("user", login1);
            session.setMaxInactiveInterval(20 * 60);//20分钟失效
            loginUtil.LoginUtil(session);
            jedisUtil.addObject(login.getToken(),login);
            jedisUtil.disableTime(login.getToken(),login.toString(),6000);
            System.out.println("session" + session.getAttribute("token"));
            map.put("message", "登录成功");
            map.put("code", Code.successCode);
        } else if (login1 == null) {
            map.put("message", "手机号或密码不能正确");
            map.put("code", Code.failCode);
        }
        return map;
    }

    @ApiOperation(value = "移除登录")
    @ResponseBody
    @RequestMapping(value = "/LoginOut", method = RequestMethod.POST)
    public String LoginOut(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute("token");
        String message = "已退出";
        return message;
    }


}
