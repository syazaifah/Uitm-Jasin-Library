package com.heroku.Modal;

import java.sql.Date;


public class Fine {
    private Integer fineid;
    private Integer amountpay;
    private String statuspayment;
    private Integer borrowid;
    private String studid;
    private String libid;
    private Date actualdatereturn;

    public Fine() {
    }

    public Fine(Integer fineid,Integer borrowid,String statuspayment,String studid, Date actualdatereturn,Integer amountpay){
        this.fineid = fineid;
        this.borrowid = borrowid;
        this.statuspayment = statuspayment;
        this.studid = studid;
        this.actualdatereturn = actualdatereturn;
        this.amountpay= amountpay;
    }

    public Fine(Integer fineid, Integer amountpay, String statuspayment, Integer borrowid, String libid,
            Date actualdatereturn) {
        this.fineid = fineid;
        this.amountpay = amountpay;
        this.statuspayment = statuspayment;
        this.borrowid = borrowid;
        this.libid = libid;
        this.actualdatereturn = actualdatereturn;
    }


    public Fine(Integer fineid, Integer amountpay, Integer borrowid, Date actualdatereturn, String studid) {
        this.fineid = fineid;
        this.amountpay = amountpay;
        this.borrowid = borrowid;
        this.actualdatereturn = actualdatereturn;
        this.studid = studid;
    }


    public Integer getFineid() {
        return fineid;
    }

    public void setFineid(Integer fineid) {
        this.fineid = fineid;
    }

    public Integer getAmountpay() {
        return amountpay;
    }

    public void setAmountpay(Integer amountpay) {
        this.amountpay = amountpay;
    }

    public String getStudid() {
        return this.studid;
    }

    public void setStudid(String studid) {
        this.studid = studid;
    }


    public String getStatuspayment() {
        return statuspayment;
    }

    public void setStatuspayment(String statuspayment) {
        this.statuspayment = statuspayment;
    }

    public Integer getBorrowid() {
        return borrowid;
    }

    public void setBorrowid(Integer borrowid) {
        this.borrowid = borrowid;
    }

    public String getLibid() {
        return libid;
    }

    public void setLibid(String libid) {
        this.libid = libid;
    }

    public Date getActualdatereturn() {
        return actualdatereturn;
    }

    public void setActualdatereturn(Date actualdatereturn) {
        this.actualdatereturn = actualdatereturn;
    }
    
}
