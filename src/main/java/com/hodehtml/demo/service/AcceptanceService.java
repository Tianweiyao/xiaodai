package com.hodehtml.demo.service;

import com.hodehtml.demo.model.Acceptance;
import com.hodehtml.demo.model.FaBu;
import com.hodehtml.demo.model.Message;
import com.hodehtml.demo.model.vo.LieBiao;
import java.util.List;
import java.util.Map;

/**
 * created by pht on 2019/3/27 0027
 */
public interface AcceptanceService {


    void insertAcceptance(Acceptance acceptance);

    Map<String,Object> findList(int acceptanceId);

    Map<String,Object> LieBiao(LieBiao lieBiao);

    Map<String,Object> messageList(int userId,int pageNo,int pageSize);

    void Fabu(int acceptanceId);

    Map<String,Object> selectFaBu(FaBu faBu);

}
