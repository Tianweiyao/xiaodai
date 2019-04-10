package com.hodehtml.demo.controller;

import com.hodehtml.demo.model.SuiXingFu;
import com.hodehtml.demo.service.SuiXingFuService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Controller
public class SuiXingFuController {

    @Autowired
    private SuiXingFuService suiXingFuService;


}
