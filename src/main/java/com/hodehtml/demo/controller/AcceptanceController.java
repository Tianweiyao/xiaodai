package com.hodehtml.demo.controller;/**
 * created by pht on 2019/3/27 0027
 */

import com.hodehtml.demo.model.*;
import com.hodehtml.demo.model.vo.LieBiao;
import com.hodehtml.demo.service.AcceptanceService;
import com.hodehtml.demo.service.ApprovalService;
import com.hodehtml.demo.utils.Code;
import com.hodehtml.demo.utils.LoginUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


/**
 * @author pht
 * @program demo
 * @date 2019/3/27 0027
 */
@Controller
@RequestMapping("Acceptance")
public class AcceptanceController {

    @Autowired
    private AcceptanceService acceptanceService;
    @Autowired
    private ApprovalService approvalService;
    @Autowired
    Code code;
    @Autowired
    private LoginUtil loginUtil;

    private static final int status = 2;


    @ApiOperation(value = "验收提交")
    @ResponseBody
    @RequestMapping(value = "/insertAcceptance", method = RequestMethod.POST)
    public Map<String, Object> insertAcceptance(@RequestBody Acceptance acceptance) {
        Login login = loginUtil.verification();
        Map<String, Object> map = new HashMap<String, Object>();
        if (login == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
            return map;
        } else {
            acceptanceService.insertAcceptance(acceptance);
            map.put("message", "success");
            map.put("code", Code.successCode);
            return map;
        }
    }

    @ApiOperation(value = "查看验收详情")
    @ResponseBody
    @RequestMapping(value = "/selectDetial", method = RequestMethod.GET)
    public Map<String, Object> selectDetial(@RequestParam("approvalId") int acceptanceId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Login login = loginUtil.verification();
        if (login == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            map = acceptanceService.findList(acceptanceId);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation(value = "条件查询列表")
    @ResponseBody
    @RequestMapping(value = "/LieBiao", method = RequestMethod.POST)
    public Map<String, Object> LieBiao(@RequestBody LieBiao lieBiao) {
        Map<String, Object> map = new HashMap<String, Object>();
        Login login = loginUtil.verification();
        if (login == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            map = acceptanceService.LieBiao(lieBiao);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation(value = "验收审批")
    @ResponseBody
    @RequestMapping(value = "/shenPi", method = RequestMethod.POST)
    public Map<String, Object> shenPi(@RequestBody ApprovalDetial approvalDetial) {
        Map<String, Object> map = new HashMap<String, Object>();
        Login login = loginUtil.verification();
        if (login == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            approvalService.shenPi(approvalDetial, status);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation(value = "重新发布")
    @ResponseBody
    @RequestMapping(value = "/Release", method = RequestMethod.POST)
    public Map<String, Object> Release(@RequestBody ApprovalDetial approvalDetial) {
        Map<String, Object> map = new HashMap<String, Object>();
        Login login = loginUtil.verification();
        if (login == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            Acceptance acceptance = approvalService.Release1(approvalDetial);
            map.put("acceptance",acceptance);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation(value = "获取消息List")
    @ResponseBody
    @RequestMapping(value = "/messageList", method = RequestMethod.GET)
    public Map<String, Object> messageList(@RequestParam("userId") int userId,@RequestParam("pageNo") int pageNo,@RequestParam("pageSize") int pageSize) {
        Map<String,Object> map = null;
        Login login = loginUtil.verification();
        if (login == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            map = acceptanceService.messageList(userId,pageNo,pageSize);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation(value = "发布公示")
    @ResponseBody
    @RequestMapping(value = "/FaBu", method = RequestMethod.GET)
    public Map<String,Object> FaBu(@RequestParam("acceptanceId") int acceptanceId){
        Map<String,Object> map = new HashMap<String, Object>();
        acceptanceService.Fabu(acceptanceId);
        map.put("message", "success");
        map.put("code", Code.successCode);
        return map;
    }

    @ApiOperation(value = "查找公示")
    @ResponseBody
    @RequestMapping(value = "/selectFaBu", method = RequestMethod.POST)
    public Map<String,Object> selectFaBu(@RequestBody FaBu faBu){
        Map<String,Object> map = acceptanceService.selectFaBu(faBu);
        map.put("message", "success");
        map.put("code", Code.successCode);
        return map;
    }



}
