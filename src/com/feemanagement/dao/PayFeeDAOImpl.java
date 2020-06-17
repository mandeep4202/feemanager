/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.dao;

import com.feemanagement.dto.GroupAndClass;
import com.feemanagement.dto.PayFee;
import com.feemanagement.dto.PaymentDetails;
import com.feemanagement.dto.Response;
import com.feemanagement.dto.SessionBean;
import com.feemanagement.dto.Student;
import com.feemanagement.dto.VoucherType;
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
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author 786
 */
public class PayFeeDAOImpl implements PayFeeDAO {

    Logger log = MyLogger.getLogger(FeeDetailDAOImpl.class);
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
    public Response getPaidFeeDetails(int regdno, int classid, int sessionid) {
        log.info("Start execution of method getPaidFeeDetails()");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.PAYFEE_DETAIL_NOT_FOUND);

        List<PayFee> payFeeList = new ArrayList<>();
        PayFee payFee = null;
        GroupAndClass groupAndClass = null;
        SessionBean sessionBean = null;

        Connection con = DBConnection.getConnection();
        if (con != null) {
            try {
                PreparedStatement pstmt = con.prepareStatement(SQLConstants.QUERY_GET_PAYFEEDETAIL);
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

                    groupAndClass.setClassId(rs.getInt(1));
                    sessionBean.setSessionId(rs.getInt(2));

                    payFee.setVoucherId(rs.getInt(3));
                    payFee.setPaymentAmount(rs.getInt(4));
                    payFee.setPayDate(rs.getDate(5));
                    payFee.setGroupAndClass(groupAndClass);
                    payFee.setSessionBean(sessionBean);
                    payFeeList.add(payFee);
                }
                response.setData(payFeeList);

            } catch (SQLException ex) {
                log.error(MessageConstants.PAYFEE_DETAIL_NOT_FOUND);
                response.setMessage(MessageConstants.PAYFEE_DETAIL_NOT_FOUND);
                log.error(ex.getMessage());
            }
        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method getPaidFeeDetails()");
        return response;
    }

    /**
     * This method pay the fee of the user
     *
     * @param student
     * @param voucherType
     * @return
     */
    @Override
    public Response payFee(Student student, VoucherType voucherType, PaymentDetails paymentDetails) {
        log.info("Start execution of method payFee()");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.FEE_PAY_NOT_DONE_SUCESSFULLY);

        Connection con = DBConnection.getConnection();

        if (con != null) {

            if (student != null && voucherType != null && paymentDetails != null) {
                System.out.println("inside validation loop");
                try {
                    con.setAutoCommit(false);

                    int paymentDetailMaxId = GetIdHelper.getMaxId(SQLConstants.QUERY_GET_PAYMENTDETAIL_MAXID, SQLConstants.INITAL_VALUE_FOR_PAYMENT);
                    int voucherMaxId = GetIdHelper.getMaxId(SQLConstants.QUERY_GET_VOUCHERDETAIL_MAXID, SQLConstants.INITAL_VALUE_FOR_VOUCHER);
                    int payFeeMaxId = GetIdHelper.getMaxId(SQLConstants.QUERY_GET_FEEPAY_MAXID, SQLConstants.INITAL_VALUE_FOR_FEE);
                    PreparedStatement pstmt2 = null;

                    if (paymentDetailMaxId > 0) {
                        pstmt2 = con.prepareStatement(SQLConstants.QUERY_INSERT_PAYMENTDETAIL);
                        pstmt2.setInt(1, paymentDetailMaxId);
                        pstmt2.setString(2, paymentDetails.getBankName());
                        pstmt2.setInt(3, paymentDetails.getAccountNumber());
                        pstmt2.setString(4, paymentDetails.getChequeNumber());
                        pstmt2.setInt(5, paymentDetails.getTellerNumber());
                        pstmt2.setInt(6, paymentDetails.getPinNumber());
                        pstmt2.setString(7, FeeManagement.userName);
                        pstmt2.setDate(8, new java.sql.Date(new java.util.Date().getTime()));
                        pstmt2.setDate(9, new java.sql.Date(new java.util.Date().getTime()));
                        pstmt2.setString(10, FeeManagement.userName);

                        int i = pstmt2.executeUpdate();

                        if (i > 0) // if yes than next to save a voucher detail here 
                        {

                            pstmt2 = con.prepareStatement(SQLConstants.QUERY_INSERT_VOUCHERDETAIL);
                            pstmt2.setInt(1, voucherMaxId);
                            pstmt2.setString(2, voucherType.getVoucherName());
                            pstmt2.setString(3, voucherType.getLedgerType());
                            pstmt2.setString(4, voucherType.getPaymentMode());
                            pstmt2.setInt(5, paymentDetailMaxId);
                            pstmt2.setString(6, FeeManagement.userName);
                            pstmt2.setDate(7, new java.sql.Date(new java.util.Date().getTime()));
                            pstmt2.setDate(8, new java.sql.Date(new java.util.Date().getTime()));
                            pstmt2.setString(9, FeeManagement.userName);
                            int k = pstmt2.executeUpdate();

                            if (k > 0) // if yes than save a detail in a paid fee table
                            {
                                pstmt2 = con.prepareStatement(SQLConstants.QUERY_INSERT_PAYFEE);
                                pstmt2.setInt(1, payFeeMaxId);
                                pstmt2.setInt(2, student.getRegdNo());
                                pstmt2.setInt(3, student.getGroupAndClass().getClassId());
                                pstmt2.setInt(4, student.getSessionBean().getSessionId());
                                pstmt2.setInt(5, student.getGroupAndClass().getGroupId());
                                pstmt2.setInt(6, voucherMaxId);
                                pstmt2.setInt(7, paymentDetails.getPayAmount());
                                pstmt2.setInt(8, paymentDetails.getRemainingFee());

                                pstmt2.setDate(9, new java.sql.Date(new java.util.Date().getTime()));
                                pstmt2.setTimestamp(10, new java.sql.Timestamp(new java.util.Date().getTime()));

                                pstmt2.setString(11, FeeManagement.userName);
                                pstmt2.setDate(12, new java.sql.Date(new java.util.Date().getTime()));
                                pstmt2.setDate(13, new java.sql.Date(new java.util.Date().getTime()));
                                pstmt2.setString(14, FeeManagement.userName);

                                int m = pstmt2.executeUpdate();
                                if (m > 0) {

                                    System.out.println("methos work upto here");
                                    con.commit();
                                    response.setMessage(MessageConstants.FEE_PAY_DONE_SUCESSFULLY);
                                    response.setStatus((byte) 1);
                                    con.setAutoCommit(true);
                                } else {
                                    log.info(MessageConstants.FEE_PAY_NOT_DONE_SUCESSFULLY);
                                    response.setMessage(MessageConstants.FEE_PAY_NOT_DONE_SUCESSFULLY);
                                    con.rollback();
                                }
                            } else {
                                log.info(MessageConstants.VOUCHER_DETAIL_NOT_SAVE_SUCESSFULLY);
                                response.setMessage(MessageConstants.VOUCHER_DETAIL_NOT_SAVE_SUCESSFULLY);
                                con.rollback();
                            }

                        }   // End of if yes than next to save a voucher detail here 

                    } else {
                        log.info(MessageConstants.PAYMENT_DETAIL_NOT_SAVE_SUCESSFULLY);
                        response.setMessage(MessageConstants.PAYMENT_DETAIL_NOT_SAVE_SUCESSFULLY);
                        con.rollback();
                    }

                } catch (SQLException ex) {
                    log.error(MessageConstants.FEE_PAY_NOT_DONE_SUCESSFULLY);
                    response.setMessage(MessageConstants.FEE_PAY_NOT_DONE_SUCESSFULLY);
                    log.error(ex.getMessage());
                }
            } else {
                log.info("Payment detail Object is Null ");
                response.setMessage(MessageConstants.SUPPLY_ALL_VALUE);
            }

        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }

        log.info("End of method savePaymentDetail()");
        return response;

    }

}
