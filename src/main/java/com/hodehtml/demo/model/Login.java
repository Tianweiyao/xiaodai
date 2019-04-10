package com.hodehtml.demo.model;/**
 * created by pht on 2019/3/25 0025
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @program demo
 * @date 2019/3/25 0025
 * @author pht
 */
@ToString
@Data
@ApiModel("用户登录注册")
public class Login {

    private int userId;
    @ApiModelProperty(value = "名称")
    private String userName;
    @ApiModelProperty(value = "电话")
    private String userPhone;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "token")
    private String token;
    @ApiModelProperty(value = "头像")
    private String image;
    @ApiModelProperty("1,发起;2,审批;3验收;")
    private int type;
    private String department;




}
