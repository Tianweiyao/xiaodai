package com.hodehtml.demo.service.Impl;/**
 * created by pht on 2019/3/26 0026
 */

import com.hodehtml.demo.dao.AcceptanceDao;
import com.hodehtml.demo.dao.ApprovalDao;
import com.hodehtml.demo.dao.LoginDao;
import com.hodehtml.demo.model.*;
import com.hodehtml.demo.model.vo.ApprovalAcceptance;
import com.hodehtml.demo.model.vo.ApprovalImage;
import com.hodehtml.demo.service.ApprovalService;
import com.hodehtml.demo.utils.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author pht
 * @program demo
 * @date 2019/3/26 0026
 */
@Service("ApprovalService")
public class ApprovalServiceImpl implements ApprovalService {

    @Autowired
    private ApprovalDao approvalDao;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private LoginDao loginDao;
    @Autowired
    private AcceptanceDao acceptanceDao;


    @Override
    public void insertApproval(Approval approval, int status) {
        Login login = loginUtil.verification();
        if (login.getType() == 1) {
            ApprovalDetial approvalDetial = new ApprovalDetial();
            ApprovalDetial approvalDetial1 = new ApprovalDetial();
            List<ApprovalImage> list = new ArrayList<ApprovalImage>();
            List<UserApproval> list1 = approvalDao.selectId(login.getUserId(), 1);
            if (approval.getType() == 1) { //新增
                approvalDao.insertApproval(approval);
            } else if (approval.getType() == 2) { //修改
                approvalDao.updateApproval(approval);
            }
            approvalDetial.setTitle(approval.getTitle());
            approvalDetial.setSubmitId(login.getUserId());
            approvalDetial.setApprovalProgress(0);
            approvalDetial.setTime(new Date());
            approvalDetial.setApprovalId(approval.getApprovalId());
            approvalDetial.setUserId(login.getUserId());
            approvalDetial.setApprovalIds(approval.getApprovalId());
            approvalDetial1.setApprovalIds(approval.getApprovalId());
            approvalDetial.setUserName(login.getUserName());
            approvalDetial.setStatus(status);
            List<ApprovalDetial> list2 = approvalDao.selectType(approvalDetial);
            if(list2.size()>0){
                for (int k=0;k<list2.size();k++){
                    list2.get(k).setStatus(0);
                    approvalDao.updateApprovalDetial(list2.get(k));
                }
            }
            List<ApprovalImage> list7 = acceptanceDao.imageList(approval.getApprovalId(),1);
            if(list7.size()>0){
              for(int u = 0;u<list7.size();u++){
                      list7.get(u).setType(0);
                  approvalDao.updateImage(approval.getApprovalId());
              }
            }
            approvalDao.insertApprovalDetial(approvalDetial);
            if (list1.size() > 0) {
                for (int k = 0; k < list1.size(); k++) {
                    approvalDetial1.setTitle(approval.getTitle());
                    approvalDetial1.setSubmitId(login.getUserId());
                    approvalDetial1.setApprovalId(approval.getApprovalId());
                    approvalDetial1.setApprovalProgress(1);
                    approvalDetial1.setTime(new Date());
                    approvalDetial1.setUserId(list1.get(k).getUserId());
                    approvalDetial1.setStatus(status);
                    Login login1 = loginDao.selectLogin(list1.get(k).getUserId());
                    approvalDetial1.setUserName(login1.getUserName());
                    List<ApprovalDetial> list3 = approvalDao.selectTypes(approvalDetial1);
                    if(list3.size()>0){
                        for (int i=0;i<list3.size();i++){
                            list3.get(i).setStatus(0);
                            approvalDao.updateApprovalDetial(list3.get(i));
                        }
                    }
                    approvalDao.insertApprovalDetial(approvalDetial1);
                    Message message = new Message();
                    message.setTime(new Date());
                    message.setApprovalDetialId(approvalDetial.getId());
                    message.setTitle("收到一条《" + approval.getTitle() + "》任务要审批,请尽快处理");
                    message.setUserId(list1.get(k).getUserId());
                    acceptanceDao.insertMessage(message);
                }
            }
            String[] imag = approval.getImage();
            for (int i = 0; i < imag.length; i++) {
                ApprovalImage approvalImage = new ApprovalImage();
                approvalImage.setApprovalId(approval.getApprovalId());
                approvalImage.setImage(imag[i]);
                approvalImage.setType(1);
                list.add(approvalImage);
            }
            approvalDao.insertApprovalImage(list);
        }
    }

    public List<ApprovalDetial> selectApprovalDetial(int userId) {
        Login login = loginDao.selectLogin(userId);
        List<ApprovalDetial> list = null;
        if (login.getType() == 1) {
            list = approvalDao.selectApprovalDetialss(userId);
        } else if (login.getType() == 2) {
            list = approvalDao.selectApprovalDetial1(userId, 1);
        } else if (login.getType() == 3) {
            list = approvalDao.selectApprovalDetial1(userId, 2);
        }
        return list;
    }

    public Map<String, Object> approvalSucess(int userId) {
        Login login = loginDao.selectLogin(userId);
        Map<String, Object> map = new HashMap<String, Object>();
        List<UserApproval> list = approvalDao.selectUserApproval(userId);
        int approvalSucessCount = approvalDao.approvalSucess(userId, list.size());
        int approvalFailCount = approvalDao.approvalFail(userId);
        int acceptancePassCount = approvalDao.acceptancePass(userId, list.size());
        int acceptanceFailCount = approvalDao.acceptanceFail(userId);
        map.put("approvalSucessCount", approvalSucessCount);
        map.put("approvalFailCount", approvalFailCount);
        map.put("acceptancePassCount", acceptancePassCount);
        map.put("acceptanceFailCount", acceptanceFailCount);
        return map;
    }

    public Map<String, Object> selectApprovalDetials(int approvalId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Approval approval = approvalDao.selectApproval(approvalId);
        List<ApprovalDetial> list = approvalDao.selectApprovalDetials(approvalId);
        List<ApprovalImage> image = acceptanceDao.imageList(approvalId, 1);
        String[] images = null;
        if (image.size() > 0) {
            images = new String[image.size()];
            for (int i = 0; i < image.size(); i++) {
                images[i] = image.get(i).getImage();
            }
        }
        if (images != null && images.length > 0) {
            approval.setImage(images);
        }
        if (list.size() > 0) {
            for (int k = 0; k < list.size(); k++) {
                Login login1 = loginDao.selectLogin(list.get(k).getUserId());
                list.get(k).setUserName(login1.getUserName());
                list.get(k).setDepartment(login1.getDepartment());
                if (list.get(k).getApprovalProgress() == 4) {
                    list.get(k).setMessage(login1.getUserName() + "验收" + approval.getTitle() + "通过");
                } else if (list.get(k).getApprovalProgress() == 5) {
                    list.get(k).setMessage(login1.getUserName() + "发布" + approval.getTitle() + "验收未通过");
                }else if (list.get(k).getApprovalProgress() == 7) {
                    list.get(k).setMessage(login1.getUserName() + "发布" + approval.getTitle() + "已公示");
                }
                if (list.get(k).getApprovalProgress() == 1) {
                    approval.setApprovalDetialId(list.get(k).getId());
                    list.get(k).setMessage(login1.getDepartment() + "审核中");
                } else if (list.get(k).getApprovalProgress() == 2) {
                    list.get(k).setMessage(login1.getDepartment() + "审核通过");
                } else if (list.get(k).getApprovalProgress() == 3) {
                    list.get(k).setMessage(login1.getDepartment() + "审核不通过");
                } else if (list.get(k).getApprovalProgress() == 0) {
                    list.get(k).setMessage(login1.getUserName() + "发起" + approval.getTitle() + "审批");
                }
            }
        }
        map.put("approval", approval);
        map.put("list", list);
        return map;
    }

    public List<ApprovalDetial> selectApprovalDetialAll(int userId, String title) {
        Login login = loginDao.selectLogin(userId);
        List<ApprovalDetial> list = null;
        if (login.getType() == 2) {
            list = approvalDao.selectApprovalDetialAll(userId, title, 1);
        }
        return list;
    }


    public void shenPi(ApprovalDetial approvalDetial, int status) {
        Login login = loginUtil.verification();
        Approval approval = approvalDao.selectApproval(approvalDetial.getApprovalId());
        if (login.getType() == 2) {//审批
            approvalDetial.setApprovalProgress(approvalDetial.getApprovalProgress());
            approvalDetial.setApprovalSuggestion(approvalDetial.getApprovalSuggestion());
            approvalDetial.setTime(new Date());
            approvalDetial.setStatus(status);
            /*approvalDetial.setId(approvalDetial.getId() + 1);*/
            approvalDetial.setId(approvalDetial.getId());
            approvalDao.updateApprovalDetial(approvalDetial);
            approvalDao.updateApprovalUser(approvalDetial);
            List<ApprovalDetial> userApproval = approvalDao.selectApprovalUser(approvalDetial);
            Message message = new Message();
            if (approvalDetial.getApprovalProgress() == 2) {
                message.setApprovalDetialId(approvalDetial.getId());
                message.setTitle("恭喜你,你的《" + approval.getTitle() + "》审批通过了,请施工完成后发起验收");
                message.setUserId(approvalDetial.getSubmitId());
                message.setTime(new Date());
                acceptanceDao.insertMessage(message);
            } else if (approvalDetial.getApprovalProgress() == 3) {
                message.setApprovalDetialId(approvalDetial.getId());
                message.setTitle("你的《" + approval.getTitle() + "》审批未通过,请修改后重新发起");
                message.setTime(new Date());
                message.setUserId(approvalDetial.getSubmitId());
                acceptanceDao.insertMessage(message);
            }
        } else if (login.getType() == 3) {//验收
            /*approvalDetial.setId(approvalDetial.getId() + 2);*/
            approvalDetial.setId(approvalDetial.getId());
            approvalDetial.setApprovalProgress(approvalDetial.getApprovalProgress());
            approvalDetial.setApprovalSuggestion(approvalDetial.getApprovalSuggestion());
            approvalDetial.setTime(new Date());
            approvalDetial.setStatus(2);
            approvalDao.updateApprovalDetial(approvalDetial);
            List<ApprovalDetial> userApproval = approvalDao.selectApprovalUser(approvalDetial);
            List<ApprovalDetial> list = approvalDao.selectApprovalDetials(approvalDetial.getApprovalId());
            int id = approvalDao.selectApprova(approvalDetial.getApprovalId());
            approvalDetial.setId(id);
            ApprovalDetial approvalDetial1 = approvalDao.selectApprovalUsers(approvalDetial);
            approvalDao.updateApprovalUseres(approvalDetial);
             /*ApprovalAcceptance approvalAcceptance = null;
           if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getStatus() == 2) {
                        approvalAcceptance = acceptanceDao.selectAcceptance1(list.get(i).getApprovalId());
                        approvalDetial.setApprovalId(approvalAcceptance.getApprovalId());
                        approvalDao.updateApprovalUsers(approvalDetial);
                    }
                }
            }*/
            Message message = new Message();
            if (approvalDetial.getApprovalProgress() == 4) {
                message.setApprovalDetialId(approvalDetial.getId());
                message.setTitle("恭喜你,你的《" + approval.getTitle() + userApproval.get(0).getTime() + "》验收通过了");
                message.setUserId(approvalDetial.getSubmitId());
                message.setTime(new Date());
                acceptanceDao.insertMessage(message);
            } else if (approvalDetial.getApprovalProgress() == 3) {
                message.setApprovalDetialId(approvalDetial.getId());
                message.setTitle("你的《" + approval.getTitle() + userApproval.get(0).getTime() + "》验收未通过,请修改后重新发起");
                message.setUserId(approvalDetial.getSubmitId());
                message.setTime(new Date());
                acceptanceDao.insertMessage(message);
            }
        }
    }

    public Approval Release(ApprovalDetial approvalDetial) {

        Approval approval = approvalDao.selectApproval(approvalDetial.getApprovalId());
        List<ApprovalImage> list = acceptanceDao.imageList(approvalDetial.getApprovalId(),1);
        String[] images = null;
        if (list.size() > 0) {
            images = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                images[i] = list.get(i).getImage();
            }
        }
        approval.setImage(images);

        return approval;
    }

    public Acceptance Release1(ApprovalDetial approvalDetial) {

        List<ApprovalAcceptance>  approvalAcceptance = acceptanceDao.selectAcceptance1(approvalDetial.getApprovalId());
        Acceptance acceptance = null;
        if(approvalAcceptance != null && approvalAcceptance.size() > 0){
        acceptance  = acceptanceDao.selectAcceptance(approvalAcceptance.get(0).getAcceptanceId());
        List<ApprovalImage> sheBeilist = acceptanceDao.imageList(approvalAcceptance.get(0).getAcceptanceId(), 3);
        List<ApprovalImage> wanGonglist = acceptanceDao.imageList(approvalAcceptance.get(0).getAcceptanceId(), 2);
        if (sheBeilist.size() > 0) {
            String[] images = new String[sheBeilist.size()];
            for (int i = 0; i < sheBeilist.size(); i++) {
                images[i] = sheBeilist.get(i).getImage();
            }
            acceptance.setEquipmentDetailsImage(images);
        }
        if (wanGonglist.size() > 0) {
            String[] images = new String[wanGonglist.size()];
            for (int k = 0; k < wanGonglist.size(); k++) {
                images[k] = wanGonglist.get(k).getImage();
            }
            acceptance.setCompletionImage(images);
        }
        }
        return acceptance;
    }


    public void deleteModel(int id){

        approvalDao.deleteModel(id);
    }

}
