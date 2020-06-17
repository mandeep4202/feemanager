/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.services;

import com.feemanagement.dao.GroupAndClassDAOImpl;
import com.feemanagement.dto.Response;

/**
 *
 * @author Mandeep Singh
 */
public class GroupAndClassServiceImpl implements GroupAndClassService {

    /**
     * This method is used to forward request to DAO for persistence storage
     *
     * @return response
     */
    @Override
    public Response saveGroup(String groupName) {
        return new GroupAndClassDAOImpl().saveGroup(groupName);
    }

    @Override
    public Response getGroupNames() {
        return new GroupAndClassDAOImpl().getGroupNames();
    }

    /**
     * This method delete a group based on groupId
     *
     * @param groupId
     * @return
     */
    @Override
    public Response deleteGroupName(int groupId) {
        return new GroupAndClassDAOImpl().deleteGroupName(groupId);
    }

    /**
     * This method update a group
     *
     * @param groupId
     * @param groupName
     * @return
     */
    @Override
    public Response updateGroup(int groupId, String groupName) {
        return new GroupAndClassDAOImpl().updateGroup(groupId, groupName);
    }

    /**
     * save class into database
     * @param className
     * @param groupId
     * @return response
     */
    @Override
    public Response saveClass(String className, int groupId) {
        return new GroupAndClassDAOImpl().saveClass(className, groupId);
    }

    /**
     * get class details from database
     *
     * @param groupId
     * @return response
     */
    @Override
    public Response getClassDetails(int groupId) {
     return new GroupAndClassDAOImpl().getClassDetails(groupId);
    }
    
        /**
     * This method update a class details
     * @param classId
     * @param className
     * @return 
     */
    @Override
    public Response updateClassDetail(int classId, String className) {
     return new GroupAndClassDAOImpl().updateClassDetail(classId, className);
    }

     /**
     * This method delete a class details based on groupId
     * @param classId
     * @return 
     */
    @Override
    public Response deleteClassName(int classId) {
     return new GroupAndClassDAOImpl().deleteClassName(classId);
    }
}
