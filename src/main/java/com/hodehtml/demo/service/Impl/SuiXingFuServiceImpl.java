package com.hodehtml.demo.service.Impl;

import com.hodehtml.demo.dao.SuiXingFuDao;
import com.hodehtml.demo.model.SuiXingFu;
import com.hodehtml.demo.service.SuiXingFuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Slf4j
@Service("SuiXingFuService")
public class SuiXingFuServiceImpl implements SuiXingFuService{

    @Autowired(required = true)
     private SuiXingFuDao suiXingFuDao;

    @Override
    @Scheduled(cron="0/5 * * * * ? ")
    public int insertSuiXingFu(SuiXingFu suiXingFu){

        return suiXingFuDao.insertSuiXingFu(suiXingFu);
    };
}
