/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.dao;

import com.feemanagement.dto.ClassBean;
import com.feemanagement.dto.FeeDetails;
import com.feemanagement.dto.GroupAndClass;
import com.feemanagement.dto.Response;
import com.feemanagement.util.DBConnection;
import com.feemanagement.util.MessageConstants;
import com.feemanagement.util.MyLogger;
import com.feemanagement.util.SQLConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author 786
 */
public class DisplayAllFeeDetailDAOImpl implements DisplayAllFeeDetailDAO {

    Logger log = MyLogger.getLogger(FeeDetailDAOImpl.class);
    Response response = new Response();

    /**
     * get all classes from database
     *
     * @return response
     */
    @Override
    public Response getAllClasses() {
        log.info("Start execution of method getAllClasses()");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.CLASS_DETAIL_NOT_FOUND);
        List<GroupAndClass> groupAndClassList = new ArrayList<>();
        GroupAndClass groupAndClass = null;
        Connection con = DBConnection.getConnection();
        if (con != null) {
            try {

                PreparedStatement pstmt = con.prepareStatement(SQLConstants.QUERY_GET_ALL_CLASSDETAIL);

                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    response.setStatus((byte) 1);
                    response.setMessage(MessageConstants.CLASS_DETAIL_FOUND);
                    groupAndClass = new GroupAndClass();

                    groupAndClass.setClassId(rs.getInt(1));
                    groupAndClass.setClassName(rs.getString(2));
                    groupAndClass.setGroupName(rs.getString(3));

                    groupAndClassList.add(groupAndClass);
                }
                response.setData(groupAndClassList);
            } catch (SQLException ex) {
                log.error(MessageConstants.CLASS_DETAIL_NOT_FOUND);
                response.setMessage(MessageConstants.CLASS_DETAIL_NOT_FOUND);

            }
        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method getAllClasses()");
        return response;

    }

    /**
     * get all particulars based on the classes
     *
     * @param classList
     * @param sessionId
     * @return response
     */
    @Override
    public Response getParticularBasedOnClass(List<GroupAndClass> classList,int sessionId) {
        log.info("Start execution of method getParticularBasedOnClass()");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.PARTICULARS_NOT_FOUND);
       // List<GroupAndClass> groupAndClassList = new ArrayList<>();
       // GroupAndClass groupAndClass = null;
        Connection con = DBConnection.getConnection();
        if (con != null) {
            try {
                 Map<GroupAndClass, List<FeeDetails>> resultMap = new HashMap<>();
             FeeDetails feeDetailObj=null;
              List<FeeDetails> feeDetailList = null;
                 for(GroupAndClass toGetClassId:classList)
                {
                PreparedStatement pstmt = con.prepareStatement(SQLConstants.QUERY_GET_CLASSID_BASED_CLASSDETAIL);
                pstmt.setInt(1, toGetClassId.getClassId());
                pstmt.setInt(2, sessionId);
                ResultSet rs = pstmt.executeQuery();
  feeDetailList = new ArrayList<>();
              while (rs.next()) {
                        response.setStatus((byte) 1);
                        response.setMessage(MessageConstants.FEE_DETAIL_FOUND);
                        feeDetailObj = new FeeDetails();
                        feeDetailObj.setFeeid(rs.getInt(1));
                        feeDetailObj.setStudentClass(rs.getString(2));
                        feeDetailObj.setParticular(rs.getString(3));
                        feeDetailObj.setParticularAmount(rs.getInt(4));
                        feeDetailObj.setStudentSession(rs.getString(5));
                               
                        feeDetailList.add(feeDetailObj);
                    }
                   // System.out.println("to get  " + toGetClassId.getClassName() + toGetClassId.getGroupName());
                         resultMap.put(toGetClassId,feeDetailList);            
}
                   response.setData(resultMap);
            
            }
                catch (SQLException ex) {
                log.error(MessageConstants.PARTICULARS_NOT_FOUND);
                response.setMessage(MessageConstants.PARTICULARS_NOT_FOUND);

            }
        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method getParticularBasedOnClass()");
        return response;

    }
}
