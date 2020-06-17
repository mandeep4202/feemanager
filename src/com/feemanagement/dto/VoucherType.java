/**
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */

package com.feemanagement.dto;

/**
 *
 * @author Mandeep Singh
 */
public class VoucherType {

    private int voucherTypeId;
    private String voucherName;
    private String ledgerType;
    private String paymentMode;
    private PaymentDetails paymentDetails;

    public int getVoucherTypeId() {
        return voucherTypeId;
    }

    public void setVoucherTypeId(int voucherTypeId) {
        this.voucherTypeId = voucherTypeId;
    }

    public String getLedgerType() {
        return ledgerType;
    }

    public void setLedgerType(String ledgerType) {
        this.ledgerType = ledgerType;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    
    
}
