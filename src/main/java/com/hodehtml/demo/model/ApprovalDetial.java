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
@ApiModel
@ToString
public class ApprovalDetial {

    private int id;
    @ApiModelProperty("审批标题")
    private String title;
    @ApiModelProperty("0,提交者;1,审批中;2,审核通过;3,审核未通过;4,验收通过;5验收未通过;6待验收")
    private int approvalProgress;
    @ApiModelProperty("审批时间")
    private Date time;
    @ApiModelProperty("审批id")
    private int approvalId;
    private int approvalId1;
    @ApiModelProperty("审批建议")
    private String approvalSuggestion;
    @ApiModelProperty("审批人id")
    private int userId;
    @ApiModelProperty("审批人名称")
    private String userName;
    @ApiModelProperty("第几个层级")
    private int type;
    @ApiModelProperty("提交人id")
    private int submitId;
    @ApiModelProperty("1,审批;2,验收")
    private int status;
    private String message;
    private String department;
    private int approvalIds;



}
