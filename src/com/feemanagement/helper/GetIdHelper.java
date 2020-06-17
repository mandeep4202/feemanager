/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.feemanagement.helper;

import com.feemanagement.dao.FeeDetailDAOImpl;
import com.feemanagement.dto.Response;
import com.feemanagement.util.DBConnection;
import com.feemanagement.util.MyLogger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 *
 * @author 786
 */
public class GetIdHelper {

    static Logger log = MyLogger.getLogger(FeeDetailDAOImpl.class);
    Response response = new Response();

    /**
     * This method fetch a max id
     *
     * @param queryConstant
     * @param idInitialValue
     * @return int maxId
     */
    public static int getMaxId(String queryConstant, String idInitialValue) {
        log.info("Start execution of method getMaxId()");
        Connection con = DBConnection.getConnection();
        int setMaxId = 0;
        if (con != null) {
            try {
                PreparedStatement pstmt = con.prepareStatement(queryConstant);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    setMaxId = rs.getInt(1);
                }

                if (setMaxId == 0) {
                    setMaxId = Integer.parseInt(idInitialValue);
                } else {
                    setMaxId = setMaxId + 1;
                }
            } catch (SQLException ex) {
                log.error("Problem in getting MaxId");
            }

        }
        log.info("End of method getMaxId()");
        return setMaxId;
    }

}
