/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.dao;

import com.feemanagement.dto.Response;

/**
 *
 * @author Mandeep Singh
 */
public interface GroupAndClassDAO {

    /**
     * save group into database
     *
     * @param groupName
     * @return
     */
    public Response saveGroup(String groupName);

    /**
     * get group from database
     *
     * @return
     */
    public Response getGroupNames();

    /**
     * This method delete a group based on groupId
     *
     * @param groupId
     * @return
     */
    public Response deleteGroupName(int groupId);

    /**
     * This method update a group
     *
     * @param groupId
     * @param groupName
     * @return
     */
    public Response updateGroup(int groupId, String groupName);

    /**
     * save class into database
     *
     * @param className
     * @param groupId
     * @return response
     */
    public Response saveClass(String className, int groupId);

    /**
     * get class details from database
     *
     * @param groupId
     * @return response
     */
    public Response getClassDetails(int groupId);

    /**
     * This method update a class details
     *
     * @param classId
     * @param className
     * @return
     */
    public Response updateClassDetail(int classId, String className);

    /**
     * This method delete a class details based on groupId
     * @param classId
     * @return Response
     */
    public Response deleteClassName(int classId);
}
