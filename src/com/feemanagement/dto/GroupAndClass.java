/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */

package com.feemanagement.dto;

/**
 *
 * @author Mandeep Singh
 */
public class GroupAndClass {

    
    private int groupId;
    private int classId;
    private String groupName;
    private String className;
    private CreationDetail creationDetail;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public CreationDetail getCreationDetail() {
        return creationDetail;
    }

    public void setCreationDetail(CreationDetail creationDetail) {
        this.creationDetail = creationDetail;
    }



    @Override
    public String toString() {
        return groupName;
    }
 /*   
   @Override
    public int hashCode()
   {
       return classId;
    }

    @Override
    public boolean equals(Object obj) {
       if (obj == null) {
           return false;
       }
       
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GroupAndClass other = (GroupAndClass) obj;
        return this.classId == other.classId;
    }
    
    */
    
}
