/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.dao;

import com.feemanagement.dto.GroupAndClass;
import com.feemanagement.dto.PayFee;
import com.feemanagement.dto.Response;
import com.feemanagement.dto.SessionBean;
import com.feemanagement.dto.VoucherType;
import com.feemanagement.util.DBConnection;
import com.feemanagement.util.MessageConstants;
import com.feemanagement.util.MyLogger;
import com.feemanagement.util.SQLConstants;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.apache.log4j.Logger;

/**
 *
 * @author Mandeep Singh
 */
public class PrintFeeReciptDAOImpl implements PrintFeeReciptDAO {

    Logger log = MyLogger.getLogger(PrintFeeReciptDAOImpl.class);
    Response response = new Response();

    /**
     * This method get the paid fee detail of the student base on following
     * parameter
     *
     * @param regdno
     * @param classid
     * @param sessionid
     * @return Response
     */
    @Override
    public Response getAllInstallmentDetail(int regdno, int classid, int sessionid) {
        log.info("Start execution of method getAllInstallmentDetail()");
         log.info("regdno :: "+ regdno);
         log.info("classid :: "+ classid);
          log.info("sessionid :: "+ sessionid);
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.PAYFEE_DETAIL_NOT_FOUND);

        ArrayList<PayFee> payFeeList = new ArrayList<>();
        PayFee payFee = null;
        GroupAndClass groupAndClass = null;
        SessionBean sessionBean = null;
        VoucherType voucherType = null;
        Connection con = DBConnection.getConnection();
        if (con != null) {
            try {
                PreparedStatement pstmt = con.prepareStatement(SQLConstants.QUERY_GET_PAYINSTALLMENT);
                pstmt.setInt(1, classid);
                pstmt.setInt(2, sessionid);
                pstmt.setInt(3, regdno);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    response.setStatus((byte) 1);
                    response.setMessage(MessageConstants.PAYFEE_DETAIL_FOUND);
                    groupAndClass = new GroupAndClass();
                    sessionBean = new SessionBean();
                    payFee = new PayFee();
                    voucherType = new VoucherType();
                    //System.out.println("causing error "+rs.getDate(3));
                    
                    payFee.setFeePayId(rs.getInt(1));
                    groupAndClass.setClassName(rs.getString(2));
                  //  sessionBean.setSessionId(rs.getInt(3));
                    voucherType.setPaymentMode(rs.getString(4));
                    payFee.setPaymentAmount(rs.getInt(5));
                    payFee.setRemainingFee(rs.getInt(6));
                    payFee.setVoucherId(rs.getInt(7));
                    payFee.setPayDate(rs.getDate(8));
                    payFee.setPayTime(rs.getTime(9));

                    payFee.setGroupAndClass(groupAndClass);
                    payFee.setSessionBean(sessionBean);
                    payFee.setVoucherType(voucherType);
                    payFeeList.add(payFee);
                }
                response.setData(payFeeList);

            } catch (SQLException ex) {
                log.error(MessageConstants.PAYFEE_DETAIL_NOT_FOUND);
                response.setMessage(MessageConstants.PAYFEE_DETAIL_NOT_FOUND);
                log.error(ex.getMessage());
                ex.printStackTrace();
            }
        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method getAllInstallmentDetail()");
        return response;
    }
    
    
}
