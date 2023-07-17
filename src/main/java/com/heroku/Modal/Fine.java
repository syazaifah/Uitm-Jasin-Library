package com.heroku.Modal;

public class Fine {
    private Integer fineid;
    private Integer amountpay;
    private String statuspayment;

    public Fine(Integer fineid, Integer amountpay, String statuspayment) {
        this.fineid = fineid;
        this.amountpay = amountpay;
        this.statuspayment= statuspayment;
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

    public String getStatuspayment() {
        return statuspayment;
    }

    public void setStatuspayment(String statuspayment) {
        this.statuspayment = statuspayment;
    }

}