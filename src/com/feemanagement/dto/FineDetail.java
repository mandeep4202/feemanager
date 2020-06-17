/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.dto;

/**
 *
 * @author Mandeep Singh
 */
public class FineDetail {

    private int fineId, fineAmount, regdNo;
    private String studentSession;
    private String sessionId;
    private CreationDetail creationDetail;

    public int getFineId() {
        return fineId;
    }

    public void setFineId(int fineId) {
        this.fineId = fineId;
    }

    public int getFineAmount() {
        return fineAmount;
    }

    public void setFineAmount(int fineAmount) {
        this.fineAmount = fineAmount;
    }

    public int getRegdNo() {
        return regdNo;
    }

    public void setRegdNo(int regdNo) {
        this.regdNo = regdNo;
    }

    public String getStudentSession() {
        return studentSession;
    }

    public void setStudentSession(String studentSession) {
        this.studentSession = studentSession;
    }

    public CreationDetail getCreationDetail() {
        return creationDetail;
    }

    public void setCreationDetail(CreationDetail creationDetail) {
        this.creationDetail = creationDetail;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "FineDetail{" + "fineId=" + fineId + ", fineAmount=" + fineAmount + ", regdNo=" + regdNo + ", studentSession=" + studentSession + '}';
    }
    
    
    
}
