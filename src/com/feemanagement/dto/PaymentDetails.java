/**
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */

package com.feemanagement.dto;

/**
 *
 * @author 786
 */
public class PaymentDetails {

    private int paymentModeId;
    private String bankName,chequeNumber;
    private int accountNumber,tellerNumber,pinNumber,payAmount,remainingFee;

    public int getRemainingFee() {
        return remainingFee;
    }

    public void setRemainingFee(int remainingFee) {
        this.remainingFee = remainingFee;
    }

    public int getPaymentModeId() {
        return paymentModeId;
    }

    public void setPaymentModeId(int paymentModeId) {
        this.paymentModeId = paymentModeId;
    }

   
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getTellerNumber() {
        return tellerNumber;
    }

    public void setTellerNumber(int tellerNumber) {
        this.tellerNumber = tellerNumber;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }
    
    
    
}
