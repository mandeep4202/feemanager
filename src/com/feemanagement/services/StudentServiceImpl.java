/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.services;

import com.feemanagement.dao.StudentDAOImpl;
import com.feemanagement.dto.Response;
import com.feemanagement.dto.Student;

/**
 *
 * @author Mandeep Singh
 */
public class StudentServiceImpl implements StudentService {

    /**
     * This method is used to forward request to DAO for persistence storage
     *
     * @param student
     * @return response
     */
    @Override
    public Response saveStudentDetail(Student student) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is used to forward request to DAO for getting Student
     * Information
     *
     * @param regdNo
     * @return response
     */
    @Override
    public Response getStudentDetail(int regdNo,int sessionId) {
        
        Response response=new StudentDAOImpl().getStudentDetail(regdNo, sessionId);
        System.out.println("response    "  + response.getData()) ;
        
        return response;
    }

    /**
     * This method is used to update the fee details
     *
     * @param student
     * @return response
     */
    @Override
    public Response updateStudentDetail(Student student) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is used to delete the fee details
     *
     * @param regdNo
     * @return response
     */
    @Override
    public Response deleteStudentDetail(int regdNo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
