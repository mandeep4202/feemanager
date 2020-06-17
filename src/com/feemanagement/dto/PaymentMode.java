/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.dto;

/**
 *
 * @author 786
 */
public class PaymentMode {

    private int paymentModeId;
    private String paymentMode;

    public int getPaymentModeId() {
        return paymentModeId;
    }

    public void setPaymentModeId(int paymentModeId) {
        this.paymentModeId = paymentModeId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    @Override
    public String toString() {
        return paymentMode;
    }

}
