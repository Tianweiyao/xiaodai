package com.hodehtml.demo.model;/**
 * created by pht on 2019/3/27 0027
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @program demo
 * @date 2019/3/27 0027
 * @author pht
 */
@Data
@ToString
@ApiModel
public class Message {

    private int id;
    @ApiModelProperty("审批详情id")
    private int approvalDetialId;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("用户id")
    private int userId;
    private Date time;

}
