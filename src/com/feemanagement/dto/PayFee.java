/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.dto;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author 786
 */
public class PayFee {

    private int feePayId;
    private GroupAndClass groupAndClass;
    private SessionBean sessionBean;
    private int voucherId;
    private int paymentAmount;
    private Date payDate;
    private Time payTime;
    private CreationDetail creationDetail;
    private VoucherType voucherType;
    private int remainingFee;

    public int getFeePayId() {
        return feePayId;
    }

    public void setFeePayId(int feePayId) {
        this.feePayId = feePayId;
    }

    public GroupAndClass getGroupAndClass() {
        return groupAndClass;
    }

    public void setGroupAndClass(GroupAndClass groupAndClass) {
        this.groupAndClass = groupAndClass;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public Date getPayDate() {
        return payDate;
    }

    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }

    public Time getPayTime() {
        return payTime;
    }

    public void setPayTime(Time payTime) {
        this.payTime = payTime;
    }

    public CreationDetail getCreationDetail() {
        return creationDetail;
    }

    public void setCreationDetail(CreationDetail creationDetail) {
        this.creationDetail = creationDetail;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(VoucherType voucherType) {
        this.voucherType = voucherType;
    }

    public int getRemainingFee() {
        return remainingFee;
    }

    public void setRemainingFee(int remainingFee) {
        this.remainingFee = remainingFee;
    }
}
