package com.hodehtml.demo.model;/**
 * created by pht on 2019/4/3 0003
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @program demo
 * @date 2019/4/3 0003
 * @author pht
 */
@ApiModel
@ToString
@Data
public class FaBu {

    private int id;
    private int acceptanceId;
    private String title;
    private String deptment;
    private String result;
    private Date time;
    private int pageNo;
    @ApiModelProperty("")
    private int pageSize;



}
