package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.Approval;
import com.hodehtml.demo.model.ApprovalDetial;
import com.hodehtml.demo.model.UserApproval;
import com.hodehtml.demo.model.vo.ApprovalImage;
import com.hodehtml.demo.model.vo.LieBiao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * created by pht on 2019/3/26 0026
 */
@Repository("ApprovalDao")
public interface ApprovalDao {

    void insertApproval(Approval approval);

    void insertApprovalDetial(ApprovalDetial approvalDetial);

    void insertApprovalImage(List<ApprovalImage> list);

    List<UserApproval> selectId(@Param("userId") int userId, @Param("type") int type);

    void insertUser(UserApproval userApproval);

    List<ApprovalDetial> selectApprovalDetial(@Param("userId") int userId,@Param("status") int status);

    List<ApprovalDetial> selectApprovalUser(ApprovalDetial approvalDetial);

    ApprovalDetial selectApprovalUsers(ApprovalDetial approvalDetial);

    List<ApprovalDetial> selectApprovalDetialss(@Param("userId") int userId);

    List<ApprovalDetial> selectApprovalDetial1(@Param("userId") int userId,@Param("status") int status);

    List<ApprovalDetial> selectApprovalDetial2(@Param("userId") int userId,@Param("status") int status);

    List<UserApproval> selectUserApproval(@Param("userId") int userId);

    int selectApprova(@Param("approvalId") int approvalId);

    int approvalSucess(@Param("userId") int userId,@Param("type") int type);

    int approvalFail(@Param("userId") int userId);

    int acceptancePass(@Param("userId") int userId,@Param("type") int type);

    int acceptanceFail(@Param("userId") int userId);

    Approval selectApproval(@Param("approvalId") int approvalId);

    List<ApprovalDetial> selectApprovalDetials(@Param("approvalId") int approvalId);

    List<ApprovalDetial> selectApprovalDetiales(@Param("approvalId") int approvalId);

    List<ApprovalDetial> selectApprovalDetialAll(@Param("userId") int userId,@Param("title")String title, @Param("status") int status);

    void updateApprovalDetial(ApprovalDetial approvalDetial);

    void updateApprovalUser(ApprovalDetial approvalDetial);

    void updateApprovalUsers(ApprovalDetial approvalDetial);

    void updateApprovalUseres(ApprovalDetial approvalDetial);

    ApprovalDetial selectDetial(ApprovalDetial approvalDetial);

    void deleteApprovalDetial(@Param("submitId") int submitId,@Param("approvalId") int approvalId);

    List<ApprovalDetial> selectType(ApprovalDetial approvalDetial);

    List<ApprovalDetial> selectTypes(ApprovalDetial approvalDetial);

    void deleteModel(@Param("id") int id);

    void updateApproval(Approval approval);

    List<ApprovalDetial> LieBiao(LieBiao lieBiao);

    int LieBiaoCount(LieBiao lieBiao);

    void updateImage(@Param("approvalId") int approvalId);

}
