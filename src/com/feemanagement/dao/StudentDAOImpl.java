/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */

package com.feemanagement.dao;

import com.feemanagement.dto.GroupAndClass;
import com.feemanagement.dto.Response;
import com.feemanagement.dto.SessionBean;
import com.feemanagement.dto.Student;
import com.feemanagement.util.DBConnection;
import com.feemanagement.util.MessageConstants;
import com.feemanagement.util.MyLogger;
import com.feemanagement.util.SQLConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author Mandeep Singh
 */
public class StudentDAOImpl implements StudentDAO {

    Logger log = MyLogger.getLogger(StudentDAOImpl.class);
    Response response = new Response();

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
    public Response getStudentDetail(int regdNo, int sessionId) {
        log.info("Start execution of method getStudentDeail()");
         response.setStatus((byte) 0);
        response.setMessage(MessageConstants.STUDENT_DETAIL_NOT_FOUND);
        Student student = null;
        GroupAndClass andClass = null;
        SessionBean sessionBean = null;
        Connection con = DBConnection.getConnection();
        if (con != null) {
            if (regdNo != 0 && sessionId != 0) {
                try {
                    PreparedStatement pstmt = con.prepareStatement(SQLConstants.QUERY_GET_STUDENTDETAIL);
                    pstmt.setInt(1, regdNo);
                    ResultSet rs = pstmt.executeQuery();
                    
                    if (rs.next()) {
                        response.setStatus((byte) 1);
                        response.setMessage(MessageConstants.STUDENT_DETAIL_FOUND);
                        student = new Student();
                        andClass = new GroupAndClass();
                        sessionBean = new SessionBean();
                        
                        student.setFirstName(rs.getString(1));
                        student.setLastName(rs.getString(2));
                        student.setFatherFirstName(rs.getString(3));
                        student.setFatherLastName(rs.getString(4));
                        
                        andClass.setClassName(rs.getString(5));
                        sessionBean.setStudentSession(rs.getString(6));
                        andClass.setGroupName(rs.getString(7));
                        andClass.setGroupId(rs.getInt(11));
                        sessionBean.setSessionId(rs.getInt(12));
                        
                        student.setIsActive(rs.getInt(8));
                        student.setGender(rs.getString(9));
                        andClass.setClassId(rs.getInt(10));
                        
                        student.setGroupAndClass(andClass);
                        student.setSessionBean(sessionBean);
                        response.setData(student);
                    }
                    
                } catch (SQLException ex) {
                    log.error(MessageConstants.STUDENT_DETAIL_NOT_FOUND);
                    log.error(ex.getMessage());
                    response.setMessage(MessageConstants.STUDENT_DETAIL_NOT_FOUND);
                }
                
            } else {
                log.info(MessageConstants.SUPPLY_ALL_VALUE);
                response.setMessage(MessageConstants.SUPPLY_ALL_VALUE);
            }
            
        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method getStudentDetail()");
        return response;
    }

    /**
     * This method is used to update the Student Detail
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
