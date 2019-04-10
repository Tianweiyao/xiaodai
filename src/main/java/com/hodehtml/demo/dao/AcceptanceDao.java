package com.hodehtml.demo.dao;/**
 * created by pht on 2019/3/27 0027
 */

import com.hodehtml.demo.model.Acceptance;
import com.hodehtml.demo.model.FaBu;
import com.hodehtml.demo.model.Message;
import com.hodehtml.demo.model.vo.ApprovalAcceptance;
import com.hodehtml.demo.model.vo.ApprovalImage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program demo
 * @date 2019/3/27 0027
 * @author pht
 */
@Repository("AcceptanceDao")
public interface AcceptanceDao {

    void insertAcceptance(Acceptance acceptance);

    void insetApprovalAcceptance(ApprovalAcceptance approvalAcceptance);

    Acceptance selectAcceptance(@Param("acceptanceId") int acceptanceId);

    List<ApprovalAcceptance> selectAcceptance1(@Param("acceptanceId") int acceptanceId);

    List<ApprovalAcceptance> selectAcceptance2(@Param("acceptanceId") int acceptanceId);

    List<ApprovalImage> imageList(@Param("acceptanceId") int acceptanceId,@Param("type") int type);

    void insertMessage(Message message);

    List<Message> messageList(@Param("userId") int userId,@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize);

    int messageCount(@Param("userId") int userId);

    void insertFaBu(FaBu faBu);

    List<FaBu> selectFaBu(FaBu faBu);

    int selectFaBuCount();

}
