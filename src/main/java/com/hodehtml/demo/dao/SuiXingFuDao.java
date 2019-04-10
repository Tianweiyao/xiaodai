package com.hodehtml.demo.dao;

import com.hodehtml.demo.model.SuiXingFu;
import org.springframework.stereotype.Repository;

@Repository("SuiXingFuDao")
public interface SuiXingFuDao {

    int insertSuiXingFu (SuiXingFu suiXingFu);
}
