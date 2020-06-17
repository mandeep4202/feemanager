/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.dto;

import java.util.List;

/**
 *
 * @author Mandeep Singh
 */
public class Student {
    private int regdNo,isActive;
    private String firstName,lastName,fatherFirstName,fatherLastName,gender;
    private CreationDetail creationDetail;
    private GroupAndClass groupAndClass;
    private SessionBean sessionBean;
    private List<FeeDetails> feeDetailList;
    private List<PayFee> payFees;
    

    public int getRegdNo() {
        return regdNo;
    }

    public void setRegdNo(int regdNo) {
        this.regdNo = regdNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFatherFirstName() {
        return fatherFirstName;
    }

    public void setFatherFirstName(String fatherFirstName) {
        this.fatherFirstName = fatherFirstName;
    }

    public String getFatherLastName() {
        return fatherLastName;
    }

    public void setFatherLastName(String fatherLastName) {
        this.fatherLastName = fatherLastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public CreationDetail getCreationDetail() {
        return creationDetail;
    }

    public void setCreationDetail(CreationDetail creationDetail) {
        this.creationDetail = creationDetail;
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

    public List<FeeDetails> getFeeDetailList() {
        return feeDetailList;
    }

    public void setFeeDetailList(List<FeeDetails> feeDetailList) {
        this.feeDetailList = feeDetailList;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public List<PayFee> getPayFees() {
        return payFees;
    }

    public void setPayFees(List<PayFee> payFees) {
        this.payFees = payFees;
    }

    @Override
    public String toString() {
        return "Student{" + "regdNo=" + regdNo + ", isActive=" + isActive + ", firstName=" + firstName + ", lastName=" + lastName + ", fatherFirstName=" + fatherFirstName + ", fatherLastName=" + fatherLastName + ", gender=" + gender + ", creationDetail=" + creationDetail + ", groupAndClass=" + groupAndClass + ", sessionBean=" + sessionBean + '}';
    }


    

}
