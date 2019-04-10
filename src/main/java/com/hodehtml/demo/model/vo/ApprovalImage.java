package com.hodehtml.demo.model.vo;/**
 * created by pht on 2019/3/26 0026
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author pht
 * @program demo
 * @date 2019/3/26 0026
 */
@ToString
@Data
@ApiModel
public class ApprovalImage {

    private int id;
    @ApiModelProperty("审批id")
    private int approvalId;
    @ApiModelProperty("图片")
    private String image;
    @ApiModelProperty("1,周边照片;2,完工照片；3,设备细节")
    private int type;
}
