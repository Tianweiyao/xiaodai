package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.Login;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * created by pht on 2019/3/25 0025
 */
@Repository("LoginDao")
public interface LoginDao {

    void Register(Login login);

    List<Login> isTrue(@Param("userPhone") String userPhone);

    Login list(Login login);

    Login selectLogin(@Param("userId") int userId);


}
