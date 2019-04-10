package com.hodehtml.demo.controller;/**
 * created by pht on 2019/3/26 0026
 */

import com.aliyun.oss.OSSClient;
import com.hodehtml.demo.model.Approval;
import com.hodehtml.demo.model.ApprovalDetial;
import com.hodehtml.demo.model.Login;
import com.hodehtml.demo.service.ApprovalService;
import com.hodehtml.demo.utils.Code;
import com.hodehtml.demo.utils.LoginUtil;
import com.hodehtml.demo.utils.OssFileUntil;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author pht
 * @program demo
 * @date 2019/3/26 0026
 */
@Controller
@RequestMapping("Approval")
public class ApprovalController {

    @Autowired
    private ApprovalService approvalService;
    @Autowired
    private LoginUtil loginUtil;
    private List<String> list;
    @Autowired
    private OssFileUntil ossFileUntil;

    private static final int status = 1;

    @ApiOperation(value = "提交审批")
    @ResponseBody
    @RequestMapping(value = "/submitApproval", method = RequestMethod.POST)
    public Map<String, Object> submitApproval(@RequestBody Approval approval) {
        Map<String, Object> map = new HashMap<String, Object>();
        Login login = loginUtil.verification();
        if (login == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            approval.setImage(approval.getImage());
            approvalService.insertApproval(approval, status);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation(value = "查询审批记录")
    @ResponseBody
    @RequestMapping(value = "/selectApprovalDetial", method = RequestMethod.GET)
    public Map<String, Object> selectApproval(@RequestParam("userId") int userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Login login = loginUtil.verification();
        if (login == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            List<ApprovalDetial> list = approvalService.selectApprovalDetial(userId);
            map.put("message", "success");
            map.put("list", list);
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation(value = "统计数据")
    @ResponseBody
    @RequestMapping(value = "/selectCount", method = RequestMethod.GET)
    public Map<String, Object> selectCount(@RequestParam("userId") int userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Login login = loginUtil.verification();
        if (login == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            map = approvalService.approvalSucess(userId);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation(value = "查看详情")
    @ResponseBody
    @RequestMapping(value = "/selectApprovalDetials", method = RequestMethod.GET)
    public Map<String, Object> selectApprovalDetials(@RequestParam("approvalId") int approvalId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Login login = loginUtil.verification();
        if (login == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            map = approvalService.selectApprovalDetials(approvalId);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation(value = "待审批列表")
    @ResponseBody
    @RequestMapping(value = "/selectApprovalDetialAll", method = RequestMethod.GET)
    public Map<String, Object> selectApprovalDetialAll(@RequestParam("userId") int userId, @RequestParam("title") String title) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<ApprovalDetial> list = approvalService.selectApprovalDetialAll(userId, title);
        map.put("list", list);
        map.put("message", "success");
        map.put("code", Code.successCode);
        return map;
    }

    @ApiOperation(value = "审批")
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
            Approval approval = approvalService.Release(approvalDetial);
            map.put("approval", approval);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation(value = "上传图片")
    @ResponseBody
    @RequestMapping(value = "/fileUpload2", method = RequestMethod.POST)
    public String upload(MultipartFile file) throws Exception {
        if (file == null || file.getSize() <= 0) {
            throw new Exception("图片不能为空");
        }
        String name = ossFileUntil.uploadImg2Oss(file);
        String imgUrl = ossFileUntil.getImgUrl(name);
        /* list.add(imgUrl);*/
        return imgUrl;
    }


    @ApiOperation(value = "删除")
    @ResponseBody
    @RequestMapping(value = "/deleteModel", method = RequestMethod.GET)
    public void deleteModel(@Param("id") int id){

        approvalService.deleteModel(id);
    }


}
