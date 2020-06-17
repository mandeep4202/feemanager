/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.dao;

import com.feemanagement.dto.FineDetail;
import com.feemanagement.dto.Response;
import com.feemanagement.frontend.FeeManagement;
import com.feemanagement.helper.GetIdHelper;
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
public class FineDAOImpl implements FineDAO {

    Logger log = MyLogger.getLogger(FeeDetailDAOImpl.class);
    Response response = new Response();

    /**
     * This method save the individual student fine
     *
     * @param regdNo
     * @param sessionId
     * @param fineAmount
     * @return response
     */
    @Override
    public Response saveFine(int regdNo, int sessionId, int fineAmount) {
        log.info("Start execution of method saveFine()");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.GROUP_NOT_SAVE_SUCESSFULLY);

        Connection con = DBConnection.getConnection();
        if (con != null) {
            if (regdNo != 0 && sessionId != 0) {
                if (GetIdHelper.getMaxId(SQLConstants.QUERY_GET_FINEDETAIL_MAXID, SQLConstants.INITAL_VALUE_FOR_FINE) > 0) {
                    try {
                        PreparedStatement pstmt2 = con.prepareStatement(SQLConstants.QUERY_INSERT_GROUPDETAIL);
                        pstmt2.setInt(1, GetIdHelper.getMaxId(SQLConstants.QUERY_GET_FINEDETAIL_MAXID, SQLConstants.INITAL_VALUE_FOR_FINE));
                        pstmt2.setInt(2, fineAmount);
                        pstmt2.setInt(3, regdNo);
                        pstmt2.setInt(4, sessionId);
                        pstmt2.setString(5, FeeManagement.userName);
                        pstmt2.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
                        pstmt2.setDate(7, new java.sql.Date(new java.util.Date().getTime()));
                        pstmt2.setString(8, FeeManagement.userName);

                        int i = pstmt2.executeUpdate();
                        if (i > 0) {
                            response.setStatus((byte) 1);
                            response.setMessage(MessageConstants.FINE_SAVE_SUCESSFULLY);
                        }
                    } catch (SQLException ex) {
                        log.info(MessageConstants.FINE_NOT_SAVE_SUCESSFULLY);
                        log.error(ex.getMessage());
                        response.setMessage(MessageConstants.FINE_NOT_SAVE_SUCESSFULLY);
                    }

                } else {
                    log.info("Can not get maxid  ");
                    response.setMessage(MessageConstants.FINE_NOT_SAVE_SUCESSFULLY);
                }
            } else {
                log.info("All value are not supplied ");
                response.setMessage(MessageConstants.SUPPLY_ALL_VALUE);
            }

        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }

        log.info("End of method saveFine()");
        return response;
    }

    /**
     * Get Fine detail
     *
     * @param regdNo
     * @param sessionId
     * @return
     */
    @Override
    public Response getFineDetail(int regdNo, int sessionId) {
        log.info("Start execution of method getFineDetail()");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.FINE_DETAIL_NOT_FOUND);
        FineDetail fineDetail = null;
        Connection con = DBConnection.getConnection();
        if (con != null) {
            try {
                PreparedStatement pstmt = con.prepareStatement(SQLConstants.QUERY_GET_FINEDETAIL);
                pstmt.setInt(1, regdNo);
                pstmt.setInt(2, sessionId);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    response.setStatus((byte) 1);
                    response.setMessage(MessageConstants.FINE_DETAIL_FOUND);
                    fineDetail = new FineDetail();
                    fineDetail.setFineId(rs.getInt(1));
                    fineDetail.setFineAmount(rs.getInt(2));
                    fineDetail.setStudentSession(rs.getString(3));
                }
                response.setData(fineDetail);

            } catch (SQLException ex) {
                log.info(MessageConstants.FINE_DETAIL_NOT_FOUND);
                log.error(ex.getMessage());
                response.setMessage(MessageConstants.FINE_DETAIL_NOT_FOUND);
            }
        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method getFineDetail()");
        return response;
    }

    /**
     * This method update the fine detail of the student
     *
     * @param feeId
     * @param fineAmount
     * @return response
     */
    @Override
    public Response updateFine(int feeId, int fineAmount) {
        log.info("Start execution of method updateFine");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.FINE_UPDATE_NOT_SUCESSFULL);
        Connection con = DBConnection.getConnection();
        if (con != null) {
            try {
                PreparedStatement pstmt = con.prepareStatement(SQLConstants.QUERY_UPDATE_FINE_DETAILS);
                pstmt.setInt(1, fineAmount);
                pstmt.setString(2, FeeManagement.userName);
                pstmt.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
                pstmt.setInt(4, feeId);

                int i = pstmt.executeUpdate();

                if (i != 0) {

                    response.setStatus((byte) 1);
                    response.setMessage(MessageConstants.FINE_UPDATE_SUCESSFULL);

                }
            } catch (SQLException ex) {
                log.info(MessageConstants.FINE_UPDATE_NOT_SUCESSFULL);
                log.error(ex.getMessage());
                response.setMessage(MessageConstants.FINE_UPDATE_NOT_SUCESSFULL);
            }

        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method updateFine()");
        return response;
    }
}
