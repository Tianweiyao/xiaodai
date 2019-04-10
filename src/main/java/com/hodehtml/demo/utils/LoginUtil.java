package com.hodehtml.demo.utils;/**
 * created by pht on 2019/3/26 0026
 */

import com.hodehtml.demo.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pht
 * @program demo
 * @date 2019/3/26 0026
 */
@Component
public class LoginUtil {

    private HttpSession session;
    @Autowired
    private JedisUtil jedisUtil;

    public HttpSession LoginUtil(HttpSession session){
         this.session = session;
         return session;
    }

    public Login verification() {
        String message = "";
        Map<String,Object> map = new HashMap<String, Object>();
        String token = (String) session.getAttribute("token");
        boolean token1 = jedisUtil.existsKey(token);
        Login login = null;
        if (token1 == false) {
            message = "请重新登录";
            map.put("message",message);
            return login;
        } else {
            login = (Login) session.getAttribute("user");
            jedisUtil.disableTime(token,login.toString(), 6000);
        }
        return login;
    }

}
