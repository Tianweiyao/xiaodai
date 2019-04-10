package com.hodehtml.demo.model;/**
 * created by pht on 2019/3/26 0026
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @program demo
 * @date 2019/3/26 0026
 * @author pht
 */
@ApiModel
@Data
@ToString
public class UserApproval {

    private int id;
    @ApiModelProperty("提交人id")
    private int approvalId;
    @ApiModelProperty("审批人id")
    private int userId;
    @ApiModelProperty("第几个层级")
    private int type;

}
