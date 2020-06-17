/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */

package com.feemanagement.services;

import com.feemanagement.dto.Response;
import com.feemanagement.dto.Student;

/**
 *
 * @author 786
 */
public interface StudentService {

      /**
     * This method is used to forward request to DAO for persistence storage
     *
     * @param student
     * @return response
     */
        public Response saveStudentDetail(Student student);
       

    /**
     * This method is used to forward request to DAO for getting Student
     * Information
     *
     * @param regdNo
     * @return response
     */
 
    public Response getStudentDetail(int regdNo,int sessionId);

    /**
     * This method is used to update the fee details
     *
     * @param student
     * @return response
     */
    public Response updateStudentDetail(Student student);

    /**
     * This method is used to delete the fee details
     *
     * @param regdNo
     * @return response
     */
    public Response deleteStudentDetail(int regdNo);
    
    
}
