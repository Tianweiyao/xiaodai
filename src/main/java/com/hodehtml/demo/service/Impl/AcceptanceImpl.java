package com.hodehtml.demo.service.Impl;/**
 * created by pht on 2019/3/27 0027
 */

import com.hodehtml.demo.dao.AcceptanceDao;
import com.hodehtml.demo.dao.ApprovalDao;
import com.hodehtml.demo.dao.LoginDao;
import com.hodehtml.demo.model.*;
import com.hodehtml.demo.model.vo.ApprovalAcceptance;
import com.hodehtml.demo.model.vo.ApprovalImage;
import com.hodehtml.demo.model.vo.LieBiao;
import com.hodehtml.demo.service.AcceptanceService;
import com.hodehtml.demo.utils.LoginUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.App;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author pht
 * @program demo
 * @date 2019/3/27 0027
 */
@Service("AcceptanceService")
public class AcceptanceImpl implements AcceptanceService {

    @Autowired
    private AcceptanceDao acceptanceDao;
    @Autowired
    private ApprovalDao approvalDao;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private LoginDao loginDao;

    private static final String TITLE = "施工详情";


    public void insertAcceptance(Acceptance acceptance) {
        Login login = loginUtil.verification();
        if (login.getType() == 1) {
            ApprovalAcceptance approvalAcceptance = new ApprovalAcceptance();
            Map<String, Object> map = new HashMap<String, Object>();
            List<ApprovalImage> list = new ArrayList<ApprovalImage>();
            List<ApprovalImage> list1 = new ArrayList<ApprovalImage>();
            ApprovalDetial approvalDetial = new ApprovalDetial();
            ApprovalDetial approvalDetial1 = new ApprovalDetial();
            List<UserApproval> list2 = approvalDao.selectId(login.getUserId(), 2);
            String[] completionImage = acceptance.getCompletionImage();
            String[] EquipmentDetailsImage = acceptance.getEquipmentDetailsImage();
            acceptanceDao.insertAcceptance(acceptance);
            approvalAcceptance.setAcceptanceId(acceptance.getId());
            approvalAcceptance.setApprovalId(acceptance.getApprovalId());
            acceptanceDao.insetApprovalAcceptance(approvalAcceptance);
            List<ApprovalImage> list7 = acceptanceDao.imageList(acceptance.getApprovalId(), 2);
            if (list7.size() > 0) {
                for (int u = 0; u < list7.size(); u++) {
                    list7.get(u).setType(0);
                    approvalDao.updateImage(acceptance.getId());
                }
            }
            Approval approval = approvalDao.selectApproval(acceptance.getApprovalId());
            if (completionImage.length > 0) {
                for (int k = 0; k < completionImage.length; k++) {
                    ApprovalImage approvalImage = new ApprovalImage();
                    approvalImage.setImage(completionImage[k]);
                    approvalImage.setApprovalId(acceptance.getId());
                    approvalImage.setType(2);
                    list.add(approvalImage);
                }
            }
            approvalDao.insertApprovalImage(list);
            if (EquipmentDetailsImage.length > 0) {
                for (int i = 0; i < EquipmentDetailsImage.length; i++) {
                    ApprovalImage approvalImage = new ApprovalImage();
                    approvalImage.setImage(EquipmentDetailsImage[i]);
                    approvalImage.setApprovalId(acceptance.getId());
                    approvalImage.setType(3);
                    list1.add(approvalImage);
                }
            }
            approvalDao.insertApprovalImage(list1);
            approvalDetial.setTitle(approval.getTitle());
            approvalDetial.setSubmitId(login.getUserId());
            approvalDetial.setApprovalProgress(6);
            approvalDetial.setTime(new Date());
            approvalDetial.setApprovalId(acceptance.getApprovalId());
            approvalDetial.setUserId(login.getUserId());
            approvalDetial.setUserName(login.getUserName());
            approvalDetial.setStatus(2);
            List<ApprovalDetial> list3 = approvalDao.selectType(approvalDetial);
            if (list3.size() > 0) {
                for (int i = 0; i < list3.size(); i++) {
                    list3.get(i).setStatus(0);
                    approvalDao.updateApprovalDetial(list3.get(i));
                }
            }
            approvalDao.updateApprovalUser(approvalDetial);
            if (list2.size() > 0) {
                for (int k = 0; k < list2.size(); k++) {
                    approvalDetial1.setTitle(approval.getTitle());
                    approvalDetial1.setSubmitId(login.getUserId());
                    approvalDetial1.setApprovalId(acceptance.getId());
                    approvalDetial1.setApprovalProgress(6);
                    approvalDetial1.setTime(new Date());
                    approvalDetial1.setApprovalIds(acceptance.getApprovalId());
                    approvalDetial1.setUserId(list2.get(k).getUserId());
                    approvalDetial1.setStatus(2);
                    Login login1 = loginDao.selectLogin(list2.get(k).getUserId());
                    approvalDetial1.setUserName(login1.getUserName());
                    List<ApprovalDetial> list5 = approvalDao.selectTypes(approvalDetial1);
                    if (list5.size() > 0) {
                        for (int i = 0; i < list5.size(); i++) {
                            list5.get(i).setStatus(0);
                            approvalDao.updateApprovalDetial(list5.get(i));
                        }
                    }
                    approvalDao.insertApprovalDetial(approvalDetial1);
                    Message message = new Message();
                    message.setApprovalDetialId(approvalDetial.getId());
                    message.setTitle("收到一条《" + approval.getTitle() + "》任务要验收,请尽快处理");
                    message.setTime(new Date());
                    message.setUserId(list2.get(k).getUserId());
                    acceptanceDao.insertMessage(message);
                }
            }
        }
    }


    public Map<String, Object> findList(int acceptanceId) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<ApprovalAcceptance> approvalAcceptance = null;
        approvalAcceptance = acceptanceDao.selectAcceptance2(acceptanceId);
        if (approvalAcceptance.isEmpty() || approvalAcceptance.size() == 0) {
            approvalAcceptance = acceptanceDao.selectAcceptance1(acceptanceId);
        }
        if(approvalAcceptance != null && approvalAcceptance.size() > 0){
        Approval approval = approvalDao.selectApproval(approvalAcceptance.get(0).getApprovalId());
        Acceptance acceptance = acceptanceDao.selectAcceptance(approvalAcceptance.get(0).getAcceptanceId());
        /*Approval approval = approvalDao.selectApproval(approvalAcceptance.getApprovalId());*/
        List<ApprovalImage> sheBeilist = acceptanceDao.imageList(approvalAcceptance.get(0).getAcceptanceId(), 3);
        List<ApprovalImage> wanGonglist = acceptanceDao.imageList(approvalAcceptance.get(0).getAcceptanceId(), 2);
        List<ApprovalImage> zhouBianlist = acceptanceDao.imageList(approvalAcceptance.get(0).getApprovalId(), 1);
        /*Login login = loginDao.selectLogin(list.get(0).getSubmitId());*/
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
        if (zhouBianlist.size() > 0) {
            String[] images = new String[zhouBianlist.size()];
            for (int l = 0; l < zhouBianlist.size(); l++) {
                images[l] = zhouBianlist.get(l).getImage();
            }
            approval.setImage(images);
        }
        List<ApprovalDetial> list2 = approvalDao.selectApprovalDetiales(approval.getApprovalId());
        List<ApprovalDetial> list = null;
        if (list2.size() > 0) {
            list = approvalDao.selectApprovalDetials(list2.get(0).getApprovalIds());
        }
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getApprovalProgress() == 0) {
                    list.get(i).setApprovalProgress(list.get(i).getType());
                }
            }
        }
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Login login1 = loginDao.selectLogin(list.get(i).getUserId());
                list.get(i).setUserName(login1.getUserName());
                list.get(i).setDepartment(login1.getDepartment());
                if (list.get(i).getApprovalProgress() == 1) {
                    approval.setApprovalDetialId(list.get(i).getId());
                    list.get(i).setMessage(login1.getDepartment() + "审核中");
                } else if (list.get(i).getApprovalProgress() == 2) {
                    list.get(i).setMessage(login1.getDepartment() + "审核通过");
                } else if (list.get(i).getApprovalProgress() == 3) {
                    list.get(i).setMessage(login1.getDepartment() + "审核不通过");
                } else if (list.get(i).getApprovalProgress() == 0) {
                    list.get(i).setMessage(login1.getUserName() + "发起" + approval.getTitle() + "审批");
                } else if (list.get(i).getApprovalProgress() == 5) {
                    list.get(i).setMessage(login1.getUserName() + "发布" + approval.getTitle() + "验收未通过");
                } else if (list.get(i).getApprovalProgress() == 4) {
                    list.get(i).setMessage(login1.getUserName() + "发布" + approval.getTitle() + "验收通过");
                } else if (list.get(i).getApprovalProgress() == 7) {
                    list.get(i).setMessage(login1.getUserName() + "发布" + approval.getTitle() + "已公示");
                }
            }
        }
        map.put("acceptance", acceptance);
        map.put("approval", approval);
        map.put("list", list);
        }
        return map;
    }

    public Map<String, Object> LieBiao(LieBiao lieBiao) {
        Map<String, Object> map = new HashMap<String, Object>();
        int start = lieBiao.getPageNo();
        int size = lieBiao.getPageSize();
        lieBiao.setPageNo((start - 1) * size);
        List<ApprovalDetial> list = approvalDao.LieBiao(lieBiao);
        int count = approvalDao.LieBiaoCount(lieBiao);
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getApprovalProgress() == 0) {
                    list.get(i).setApprovalProgress(list.get(i).getType());
                }
                if (i + 1 <= (list.size() - 1) && (list.get(i).getApprovalIds() == list.get(i + 1).getApprovalIds()) && list.get(i).getApprovalProgress() == 7) {
                    list.get(i + 1).setApprovalProgress(list.get(i).getApprovalProgress());
                    list.remove(i);
                }
            }
        }
        map.put("list", list);
        map.put("count", count);
        map.put("pageNo", start);
        map.put("pageSize", size);
        return map;
    }

    public Map<String, Object> messageList(int userId, int pageNo, int pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        int start = pageNo;
        int size = pageSize;
        pageNo = (start - 1) * size;
        List<Message> list = acceptanceDao.messageList(userId, pageNo, pageSize);
        int count = acceptanceDao.messageCount(userId);
        map.put("list", list);
        map.put("count", count);
        return map;
    }

    @Override
    public void Fabu(int acceptanceId) {
        Login login1 = loginUtil.verification();
        Acceptance acceptance = acceptanceDao.selectAcceptance(acceptanceId);
        List<ApprovalAcceptance> approvalAcceptance = acceptanceDao.selectAcceptance2(acceptanceId);
        if(approvalAcceptance != null && approvalAcceptance.size() > 0){
        Approval approval = approvalDao.selectApproval(approvalAcceptance.get(0).getApprovalId());
        List<ApprovalDetial> list = approvalDao.selectApprovalDetials(approvalAcceptance.get(0).getApprovalId());
        Login login = null;
        if (list.size() > 0) {
            login = loginDao.selectLogin(list.get(0).getSubmitId());
        }
        FaBu faBu = new FaBu();
        faBu.setAcceptanceId(acceptanceId);
        if (login != null) {
            faBu.setDeptment(login.getDepartment());
        }
        faBu.setResult(acceptance.getAcceptanceSpecification());
        faBu.setTime(new Date());
        faBu.setTitle(approval.getTitle());
        acceptanceDao.insertFaBu(faBu);
        ApprovalDetial approvalDetial = new ApprovalDetial();
        approvalDetial.setApprovalIds(list.get(0).getApprovalIds());
        approvalDetial.setApprovalId(faBu.getId());
        approvalDetial.setApprovalProgress(7);
        approvalDetial.setTitle(approval.getTitle());
        approvalDetial.setTime(new Date());
        approvalDetial.setSubmitId(list.get(0).getSubmitId());
        approvalDetial.setUserId(login1.getUserId());
        approvalDetial.setUserName(login1.getUserName());
        approvalDao.insertApprovalDetial(approvalDetial);
        approvalDao.updateApprovalUser(approvalDetial);
        }
    }

    @Override
    public Map<String, Object> selectFaBu(FaBu faBu) {
        Map<String, Object> map = new HashMap<String, Object>();
        int start = faBu.getPageNo();
        int size = faBu.getPageSize();
        faBu.setPageNo((start - 1) * size);
        List<FaBu> list = acceptanceDao.selectFaBu(faBu);
        int count = acceptanceDao.selectFaBuCount();
        map.put("list", list);
        map.put("count", count);
        return map;
    }

}
