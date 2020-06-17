/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.dao;

import com.feemanagement.dto.Response;
import com.feemanagement.dto.User;
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
public class SecurityDAOImpl implements SecurityDAO {

    Logger log = MyLogger.getLogger(SecurityDAOImpl.class);
    Response response = new Response();

    @Override
    public Response validUser(User user) {
        log.info("Start execution of method validateUser()");

        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.NOT_A_VALID_USER);

        Connection con = DBConnection.getConnection();
        if (con != null) {
            if (user != null) {
                try {
                    PreparedStatement pstmt = null;
                    ResultSet rs = null;
                    if (user.getUserName() != null && user.getPassword() != null) {
                        pstmt = con.prepareStatement(SQLConstants.QUERY_CHECK_VALIDUSER);
                        pstmt.setString(1, user.getUserName());
                        pstmt.setString(2, user.getPassword());
                        rs = pstmt.executeQuery();
                    }

                    if (rs.next()) {
                        response.setStatus((byte) 1);
                        response.setMessage(MessageConstants.VALID_USER + " : " + user.getUserName());
                        user.setLastLoginDate(rs.getDate(1));
                        user.setLastLoginTIme(rs.getTime(2));
                        user.setPassword(null);
                        log.info(MessageConstants.VALID_USER + " : " + user.getUserName());
                    }
                    response.setData(user);

                } catch (SQLException ex) {
                    log.error(MessageConstants.NOT_A_VALID_USER);
                    log.error(ex.getMessage());
                    response.setMessage(MessageConstants.NOT_A_VALID_USER);
                }

            } else {
                log.info(MessageConstants.SUPPLY_ALL_VALUE);
                response.setMessage(MessageConstants.SUPPLY_ALL_VALUE);
            }

        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method validateUser()");
        return response;
    }

    @Override
    public Response logout() {
        return null;
    }

}
