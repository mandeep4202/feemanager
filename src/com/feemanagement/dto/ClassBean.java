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
public class ClassBean {

    private int classId;
    private String className;

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override
    public String toString() {
        return className;
    }
    
//    @Override
//    public String toString() {
//        return "{" + "classId=" + classId + '}';
//    }
//    
    
}
