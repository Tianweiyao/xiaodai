package com.hodehtml.demo.model;/**
 * created by pht on 2019/3/26 0026
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @program demo
 * @date 2019/3/26 0026
 * @author pht
 */
@Data
@ToString
@ApiModel
public class Acceptance {

    private int id;
    @ApiModelProperty("施工单位")
    private int constructionId;
    @ApiModelProperty("施工周期")
    private int constructionDay;
        @ApiModelProperty("完工时间")
    private Date completionDate;
    @ApiModelProperty("验收说明")
    private String acceptanceSpecification;
    @ApiModelProperty("完工照片")
    private String[] completionImage;
    @ApiModelProperty("设备细节")
    private String[] equipmentDetailsImage;
    private int approvalId;
    private int userId;
    private int detialId;



}
