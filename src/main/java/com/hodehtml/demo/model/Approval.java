package com.hodehtml.demo.model;/**
 * created by pht on 2019/3/26 0026
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author pht
 * @program demo
 * @date 2019/3/26 0026
 */
@ApiModel
@Data
@ToString
public class Approval {

    private int approvalId;
    @ApiModelProperty("任务名称")
    private String title;
    @ApiModelProperty("任务类型")
    private int titleType;
    @ApiModelProperty("市")
    private String city;
    @ApiModelProperty("区")
    private String classify;
    @ApiModelProperty("详细地址")
    private String detailedAddress;
    @ApiModelProperty("设备类型")
    private int equipmentType;
    @ApiModelProperty("周边情况")
    private String peripheralCondition;
    @ApiModelProperty("施工周期")
    private int constructionPeriod;
    @ApiModelProperty("预算报价")
    private double budgetQuotation;
    @ApiModelProperty("描述")
    private String describe;
    @ApiModelProperty("图片")
    private String[] image;
    @ApiModelProperty("用户id")
    private Date time;
    private int userId;
    private int type;
    private int approvalDetialId;




}
