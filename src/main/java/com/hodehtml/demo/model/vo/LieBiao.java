package com.hodehtml.demo.model.vo;/**
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
public class LieBiao {

    @ApiModelProperty("用户id")
    private int userId;
    @ApiModelProperty("")
    private int status;
    @ApiModelProperty("")
    private Date startTime;
    @ApiModelProperty("")
    private Date endTime;
    @ApiModelProperty("")
    private String title;
    @ApiModelProperty("")
    private int pageNo;
    @ApiModelProperty("")
    private int pageSize;




}
