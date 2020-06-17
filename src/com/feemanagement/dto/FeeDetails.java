/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.feemanagement.dto;

/**
 *
 * @author Mandeep Singh
 */
public class FeeDetails {
    
    private int feeid;
    private String studentClass;
    private String particular;
    private int particularAmount;
    private String studentSession;
    private int groupId;
    private int classId;
    private int sessionId;
private CreationDetail creationDetail;
    public int getFeeid() {
        return feeid;
    }

    public void setFeeid(int feeid) {
        this.feeid = feeid;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public String getStudentSession() {
        return studentSession;
    }

    public void setStudentSession(String studentSession) {
        this.studentSession = studentSession;
    }

    public int getParticularAmount() {
        return particularAmount;
    }

    public void setParticularAmount(int particularAmount) {
        this.particularAmount = particularAmount;
    }

    public CreationDetail getCreationDetail() {
        return creationDetail;
    }

    public void setCreationDetail(CreationDetail creationDetail) {
        this.creationDetail = creationDetail;
    }

 
    
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String toString() {
        return "FeeDetails{" + "feeid=" + feeid + ", studentClass=" + studentClass + ", particular=" + particular + ", particularAmount=" + particularAmount + ", studentSession=" + studentSession + ", groupId=" + groupId + ", classId=" + classId + ", sessionId=" + sessionId + ", creationDetail=" + creationDetail + '}';
    }

  
      
}
