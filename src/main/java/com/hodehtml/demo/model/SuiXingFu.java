package com.hodehtml.demo.model;

import java.util.Date;

public class SuiXingFu {

   private Integer id;
   private String sno;
   private double bl;
   private String evttime;
   private String tcdName;
   private double cune;
   private double que;
   private Date charuTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public double getBl() {
        return bl;
    }

    public void setBl(double bl) {
        this.bl = bl;
    }

    public String getEvttime() {
        return evttime;
    }

    public void setEvttime(String evttime) {
        this.evttime = evttime;
    }

    public String getTcdName() {
        return tcdName;
    }

    public void setTcdName(String tcdName) {
        this.tcdName = tcdName;
    }

    public double getCune() {
        return cune;
    }

    public void setCune(double cune) {
        this.cune = cune;
    }

    public double getQue() {
        return que;
    }

    public void setQue(double que) {
        this.que = que;
    }

    public Date getCharuTime() {
        return charuTime;
    }

    public void setCharuTime(Date charuTime) {
        this.charuTime = charuTime;
    }
}
