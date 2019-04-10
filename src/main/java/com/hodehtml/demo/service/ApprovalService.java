package com.hodehtml.demo.service;

import com.hodehtml.demo.model.Acceptance;
import com.hodehtml.demo.model.Approval;
import com.hodehtml.demo.model.ApprovalDetial;
import com.hodehtml.demo.model.vo.ApprovalImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * created by pht on 2019/3/26 0026
 */
public interface ApprovalService {

    void insertApproval(Approval approval,int status);

    public List<ApprovalDetial> selectApprovalDetial(int userId);

    Map<String,Object> approvalSucess(int userId);

    Map<String,Object> selectApprovalDetials(int approvalId);

    List<ApprovalDetial> selectApprovalDetialAll(int userId,String title);

    void shenPi(ApprovalDetial approvalDetial,int status);

    Approval Release(ApprovalDetial approvalDetial);

    void deleteModel(int id);

    Acceptance Release1(ApprovalDetial approvalDetial);

}
