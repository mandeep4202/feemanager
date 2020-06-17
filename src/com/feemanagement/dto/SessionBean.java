/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.feemanagement.dto;

/**
 *
 * @author 786
 */
public class SessionBean {
    
    private int sessionId;
     private String studentSession;

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public String getStudentSession() {
        return studentSession;
    }

    public void setStudentSession(String studentSession) {
        this.studentSession = studentSession;
    }

    @Override
    public String toString() {
        return studentSession;
    }
     
     

}
