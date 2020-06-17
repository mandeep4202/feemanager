/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.dao;

import com.feemanagement.dto.ClassBean;
import com.feemanagement.dto.FeeDetails;
import com.feemanagement.dto.GroupAndClass;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author Mandeep Singh
 */
public class FeeDetailDAOImpl implements FeeDetailDAO {

    Logger log = MyLogger.getLogger(FeeDetailDAOImpl.class);
    Response response = new Response();

    /**
     * This method check where a data is already save or not
     *
     * @param feeDetails
     * @return boolean
     */
    public boolean isFeeDetailAlreadySave(FeeDetails feeDetails) {
        boolean result = true;
        log.info("Start execution of method isFeeDetailAlreadySave()");
        Connection con = DBConnection.getConnection();
        if (con != null) {
            int count = 0;
            try {
                PreparedStatement pstmt = con.prepareStatement(SQLConstants.QUERY_FEEDETAIL_ALREADY_EXIST);
                pstmt.setInt(1, feeDetails.getClassId());
                pstmt.setString(2, feeDetails.getParticular());
                pstmt.setString(3, feeDetails.getStudentSession());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }

                if (count == 0) {
                    result = false;
                }

            } catch (SQLException ex) {
                log.error("Problem in Checking data already exist or not");
                log.error(ex.getMessage());
            }

        }
        log.info("End of method isFeeDetailAlreadySave()");
        return result;
    }

    /**
     * This method is used to save fee detail in persistence store
     *
     * @param feeDetailsList
     * @return response
     */
    @Override
    public Response saveFeeDetail(List<FeeDetails> feeDetailsList) {
        log.info("Start execution of method saveFeeDeail()");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.FEE_NOT_SAVE_SUCESSFULLY);
        Connection con = DBConnection.getConnection();
        int i[] = new int[20];
        response.setData(i);
        if (con != null) {
            if (feeDetailsList != null) {
                if (!isFeeDetailAlreadySave(feeDetailsList.get(0))) {
                    int newId = GetIdHelper.getMaxId(SQLConstants.QUERY_GET_FEEDETAIL_MAXID, SQLConstants.INITAL_VALUE_FOR_FEE);
                    if (newId > 0) {
                        try {
                            PreparedStatement pstmt2 = con.prepareStatement(SQLConstants.QUERY_INSERT_FEEDETAIL);

                            for (FeeDetails feeDetails : feeDetailsList) {
                                pstmt2.setInt(1, newId);
                                pstmt2.setInt(2, feeDetails.getSessionId());
                                pstmt2.setInt(3, feeDetails.getClassId());
                                pstmt2.setInt(4, feeDetails.getGroupId());
                                pstmt2.setString(5, feeDetails.getParticular());
                                pstmt2.setInt(6, feeDetails.getParticularAmount());
                                pstmt2.setString(7, FeeManagement.userName);
                                pstmt2.setDate(8, new java.sql.Date(new java.util.Date().getTime()));
                                pstmt2.setDate(9, new java.sql.Date(new java.util.Date().getTime()));
                                pstmt2.setString(10, FeeManagement.userName);

                                newId++;

                                pstmt2.addBatch();

                            }
                            i = pstmt2.executeBatch();
                            if (i != null && i.length > 0) {
                                response.setStatus((byte) 1);
                                response.setData(i);
                                response.setMessage(MessageConstants.FEE_SAVE_SUCESSFULLY);
                            }
                        } catch (SQLException ex) {
                            log.error(MessageConstants.FEE_NOT_SAVE_SUCESSFULLY);
                            log.error(ex.getMessage());
                            response.setMessage(MessageConstants.FEE_NOT_SAVE_SUCESSFULLY);
                        }

                    } else {
                        log.info("Can not get maxid  ");
                        response.setMessage(MessageConstants.FEE_NOT_SAVE_SUCESSFULLY);
                    }

                } else {
                    log.info(MessageConstants.FEE_ALREADY_EXIST);
                    response.setMessage(MessageConstants.FEE_ALREADY_EXIST);
                }
            } else {
                log.info("Fee detail Object is Null ");
                response.setMessage(MessageConstants.SUPPLY_ALL_VALUE);
            }

        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }

        log.info("End of method saveFeeDeail()");
        return response;
    }

    /**
     * This method get full fee detail and return attaching with Response Object
     *
     * @param feeDetails
     * @return response
     */
    @Override
    public Response getFeeDetail(FeeDetails feeDetails) {
        log.info("Start execution of method getFeeDeail()");

        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.FEE_DETAIL_NOT_FOUND);
        List<FeeDetails> feeDetailList = new ArrayList<>();
        FeeDetails feeDetailObj = null;
        int ii[] = new int[20];
        Connection con = DBConnection.getConnection();
        if (con != null) {
            if (feeDetails != null) {
                try {
                    PreparedStatement pstmt = null;
                    ResultSet rs = null;

                    if (feeDetails.getGroupId() == 786786 && feeDetails.getClassId() == 786786) {
                        pstmt = con.prepareStatement(SQLConstants.QUERY_GET_FEEDETAIL_All_All);
                        pstmt.setInt(1, feeDetails.getSessionId());
                        rs = pstmt.executeQuery();
                    } else if (feeDetails.getGroupId() != 786786 && feeDetails.getClassId() == 786786) {
                        pstmt = con.prepareStatement(SQLConstants.QUERY_GET_FEEDETAIL_ANY_ALL);
                        pstmt.setInt(1, feeDetails.getGroupId());
                        pstmt.setInt(2, feeDetails.getSessionId());
                        rs = pstmt.executeQuery();
                    } else if (feeDetails.getGroupId() != 786786 && feeDetails.getClassId() != 786786) {
                        pstmt = con.prepareStatement(SQLConstants.QUERY_GET_FEEDETAIL_ANY_ANY);
                        pstmt.setInt(1, feeDetails.getClassId());
                        pstmt.setInt(2, feeDetails.getSessionId());
                        rs = pstmt.executeQuery();
                    }

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
                    response.setData(feeDetailList);

                } catch (SQLException ex) {
                    log.error(MessageConstants.FEE_NOT_SAVE_SUCESSFULLY);
                    log.error(ex.getMessage());
                    response.setMessage(MessageConstants.FEE_NOT_SAVE_SUCESSFULLY);
                }

            } else {
                log.info(MessageConstants.SUPPLY_ALL_VALUE);
                response.setMessage(MessageConstants.SUPPLY_ALL_VALUE);
            }

        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method getFeeDetail()");
        return response;
    }

    /**
     * This method contains a logic for update the fee details
     *
     * @param feeDetails
     * @return response
     */
    @Override
    public Response updateFeeDetail(FeeDetails feeDetails) {
        log.info("Start execution of method updateFeeDetail");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.FEE_UPDATE_NOT_SUCESSFULL);
        Connection con = DBConnection.getConnection();

        if (con != null) {
            try {
                PreparedStatement pstmt = con.prepareStatement(SQLConstants.QUERY_UPDATE_FEE_DETAILS);
                pstmt.setString(1, feeDetails.getParticular());
                pstmt.setInt(2, feeDetails.getParticularAmount());
                pstmt.setString(3, FeeManagement.userName);
                pstmt.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
                pstmt.setInt(5, feeDetails.getFeeid());

                int i = pstmt.executeUpdate();

                if (i != 0) {
                    response.setStatus((byte) 1);
                    response.setMessage(MessageConstants.FEE_UPDATE_SUCESSFULL);
                }
            } catch (SQLException ex) {
                log.error(MessageConstants.FEE_UPDATE_NOT_SUCESSFULL);
                log.error(ex.getMessage());
                response.setMessage(MessageConstants.FEE_UPDATE_NOT_SUCESSFULL);
            }

        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method updateFeeDetail");
        return response;
    }

    /**
     * This method contain to delete the fee details
     *
     * @param feeId
     * @return response
     */
    @Override
    public Response deleteFeeDetail(int feeId) {
        log.info("Start execution of method deleteFeeDetail");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.FEE_DETAIL_NOT_DELETE);
        Connection con = DBConnection.getConnection();
        if (con != null) {
            try {
                PreparedStatement pstmt = con.prepareStatement(SQLConstants.QUERY_DELETE_FEE_DETAILS);
                pstmt.setInt(1, feeId);

                int i = pstmt.executeUpdate();

                if (i != 0) {

                    response.setStatus((byte) 1);
                    response.setMessage(MessageConstants.FEE_DETAIL_DELETE);

                }
            } catch (SQLException ex) {
                log.error(MessageConstants.FEE_DETAIL_NOT_DELETE);
                log.error(ex.getMessage());
                response.setMessage(MessageConstants.FEE_DETAIL_NOT_DELETE);

            }

        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method deleteFeeDetaill");
        return response;
    }

    /**
     * Save the fee details if user select following combination group = All &&
     * class=All group= AnyOne && class=All
     *
     * @param feeDetailsList
     * @return Response
     */
    public Response saveFeeDetailForAll(List<FeeDetails> feeDetailsList) {
        log.info("Start execution of method saveFeeDeailForAll()");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.FEE_NOT_SAVE_SUCESSFULLY);
        int ii[] = new int[20];
        response.setData(ii);
        Connection con = DBConnection.getConnection();
        if (con != null) {
            if (feeDetailsList != null) {
                if (!isFeeDetailAlreadySave(feeDetailsList.get(0))) {
                    int newId = GetIdHelper.getMaxId(SQLConstants.QUERY_GET_FEEDETAIL_MAXID, SQLConstants.INITAL_VALUE_FOR_FEE);
                    if (newId > 0) {
                        try {
                            List<GroupAndClass> groupList = null;
                            Map<Integer, List<ClassBean>> resultMap = new HashMap<>();
                            List<ClassBean> classBeanList = null;
                            ClassBean classBean = null;

                            if (feeDetailsList.get(0).getClassId() == 786786 && feeDetailsList.get(0).getGroupId() != 786786) {
                                groupList = new ArrayList<>();
                                GroupAndClass andClass = new GroupAndClass();
                                andClass.setGroupId(feeDetailsList.get(0).getGroupId());
                                groupList.add(andClass);
                            } else if (feeDetailsList.get(0).getClassId() == 786786 && feeDetailsList.get(0).getGroupId() == 786786) {
                                Response response1 = new GroupAndClassDAOImpl().getGroupNames();
                                groupList = (List<GroupAndClass>) response1.getData();
                            }

                            for (int i = 0; i < groupList.size(); i++) {    // for loop start
                                PreparedStatement pstmt2 = con.prepareStatement(SQLConstants.QUERY_SELECT_CLASSID);
                                pstmt2.setInt(1, groupList.get(i).getGroupId());
                                ResultSet rs = pstmt2.executeQuery();

                                classBeanList = new ArrayList<>();

                                while (rs.next()) {  // while for a class id get
                                    classBean = new ClassBean();
                                    classBean.setClassId(rs.getInt(1));
                                    classBeanList.add(classBean);
                                }   // while end for a classid get
                                resultMap.put(groupList.get(i).getGroupId(), classBeanList);
                            }

                            Set<Map.Entry<Integer, List<ClassBean>>> entrySet = resultMap.entrySet();
                            PreparedStatement pstmt2 = con.prepareStatement(SQLConstants.QUERY_INSERT_FEEDETAIL);
                            for (Map.Entry<Integer, List<ClassBean>> e : entrySet) {
                                for (int k = 0; k < e.getValue().size(); k++) {

                                    for (FeeDetails feeDetails : feeDetailsList) {
                                        pstmt2.setInt(1, newId);
                                        pstmt2.setInt(2, feeDetails.getSessionId());
                                        pstmt2.setInt(3, e.getValue().get(k).getClassId());
                                        pstmt2.setInt(4, e.getKey());
                                        pstmt2.setString(5, feeDetails.getParticular());
                                        pstmt2.setInt(6, feeDetails.getParticularAmount());
                                        pstmt2.setString(7, FeeManagement.userName);
                                        pstmt2.setDate(8, new java.sql.Date(new java.util.Date().getTime()));
                                        pstmt2.setDate(9, new java.sql.Date(new java.util.Date().getTime()));
                                        pstmt2.setString(10, FeeManagement.userName);
                                        newId++;
                                        pstmt2.addBatch();
                                    }

                                }

                            }

                            ii = pstmt2.executeBatch();
                            if (ii != null && ii.length > 0) {
                                response.setStatus((byte) 1);
                                response.setData(ii);
                                response.setMessage(MessageConstants.FEE_SAVE_SUCESSFULLY);
                            }
                        } catch (SQLException ex) {
                            log.error(MessageConstants.FEE_NOT_SAVE_SUCESSFULLY);
                            log.error(ex.getMessage());
                            response.setMessage(MessageConstants.FEE_NOT_SAVE_SUCESSFULLY);
                        }

                    } else {
                        log.info("Can not get maxid  ");
                        response.setMessage(MessageConstants.FEE_NOT_SAVE_SUCESSFULLY);
                    }

                } else {
                    log.info(MessageConstants.FEE_ALREADY_EXIST);
                    response.setMessage(MessageConstants.FEE_ALREADY_EXIST);
                }
            } else {
                log.info("Fee detail Object is Null ");
                response.setMessage(MessageConstants.SUPPLY_ALL_VALUE);
            }

        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }

        log.info("End of method saveFeeDeail()");
        return response;
    }
}
