/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.dao;

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
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Mandeep Singh
 */
public class GroupAndClassDAOImpl implements GroupAndClassDAO {

    Logger log = MyLogger.getLogger(FeeDetailDAOImpl.class);
    Response response = new Response();

    /**
     * check whether enter group already save or not
     *
     * @param groupName
     * @param className
     * @param groupId
     * @return
     */
    public boolean isGroupAndClassAlreadySave(String groupName, String className, int groupId) {
        boolean result = true;
        log.info("Start execution of method isGroupAlreadySave(()");
        Connection con = DBConnection.getConnection();
        if (con != null) {
            int count = 0;
            try {
                PreparedStatement pstmt = null;
                if (groupName != null && groupName.trim().length() > 0) {
                    pstmt = con.prepareStatement(SQLConstants.QUERY_GROUP_ALREADY_EXIST);
                    pstmt.setString(1, groupName);
                } else {
                    pstmt = con.prepareStatement(SQLConstants.QUERY_CLASS_ALREADY_EXIST);
                    pstmt.setString(1, className);
                    pstmt.setInt(2, groupId);
                }

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }

                if (count == 0) {
                    result = false;
                }

            } catch (SQLException ex) {
                log.error("Problem in Checking data already exist or not");
            }

        }
        log.info("End of method isGroupAlreadySave()");
        return result;
    }

    /**
     * save group into database
     *
     * @param groupName
     * @return
     */
    @Override
    public Response saveGroup(String groupName) {
        log.info("Start execution of method saveGroup()");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.GROUP_NOT_SAVE_SUCESSFULLY);

        String className = " ";
        int groupId = 0;

        Connection con = DBConnection.getConnection();
        if (con != null) {
            if (groupName != null) {
                if (!isGroupAndClassAlreadySave(groupName, className, groupId)) {
                    if (GetIdHelper.getMaxId(SQLConstants.QUERY_GET_GROUPDETAIL_MAXID, SQLConstants.INITAL_VALUE_FOR_GROUP) > 0) {
                        try {
                            PreparedStatement pstmt2 = con.prepareStatement(SQLConstants.QUERY_INSERT_GROUPDETAIL);
                            pstmt2.setInt(1, GetIdHelper.getMaxId(SQLConstants.QUERY_GET_GROUPDETAIL_MAXID, SQLConstants.INITAL_VALUE_FOR_GROUP));
                            pstmt2.setString(2, groupName);
                            pstmt2.setString(3, FeeManagement.userName);
                            pstmt2.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
                            pstmt2.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
                            pstmt2.setString(6, FeeManagement.userName);

                            int i = pstmt2.executeUpdate();
                            if (i > 0) {
                                response.setStatus((byte) 1);
                                response.setMessage(MessageConstants.GROUP_SAVE_SUCESSFULLY);
                            }
                        } catch (SQLException ex) {
                            log.info(MessageConstants.GROUP_NOT_SAVE_SUCESSFULLY);
                            log.error(ex.getMessage());
                            response.setMessage(MessageConstants.GROUP_NOT_SAVE_SUCESSFULLY);
                        }
                    } else {
                        log.info("Can not get maxid  ");
                        response.setMessage(MessageConstants.GROUP_NOT_SAVE_SUCESSFULLY);
                    }
                } else {
                    log.info(MessageConstants.GROUP_ALREADY_EXIST);
                    response.setMessage(MessageConstants.GROUP_ALREADY_EXIST);
                }
            } else {
                log.info("Fee detail Object is Null ");
                response.setMessage(MessageConstants.SUPPLY_ALL_VALUE);
            }
        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method saveGroup()");
        return response;
    }

    /**
     * Display the Available group
     */
    @Override
    public Response getGroupNames() {
        log.info("Start execution of method getGroupNames()");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.GROUP_DETAIL_NOT_FOUND);
        List<GroupAndClass> groupAndClassList = new ArrayList<>();
        GroupAndClass groupAndClass = null;
        Connection con = DBConnection.getConnection();
        if (con != null) {
            try {
                PreparedStatement pstmt = con.prepareStatement(SQLConstants.QUERY_GET_GROUPDETAIL);
                ResultSet rs = pstmt.executeQuery();
                while (rs.next()) {
                    response.setStatus((byte) 1);
                    response.setMessage(MessageConstants.GROUP_DETAIL_FOUND);
                    groupAndClass = new GroupAndClass();
                    groupAndClass.setGroupId(rs.getInt(1));
                    groupAndClass.setGroupName(rs.getString(2));
                    groupAndClassList.add(groupAndClass);
                }
                response.setData(groupAndClassList);

            } catch (SQLException ex) {
                log.info(MessageConstants.GROUP_DETAIL_NOT_FOUND);
                log.error(ex.getMessage());
                response.setMessage(MessageConstants.GROUP_DETAIL_NOT_FOUND);
            }
        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method getGroupNames");
        return response;
    }

    /**
     * This method delete a group based on groupId
     *
     * @param groupId
     * @return
     */
    @Override
    public Response deleteGroupName(int groupId) {
        log.info("Start execution of method deleteGroupName");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.GROUP_DETAIL_NOT_DELETE);
        Connection con = DBConnection.getConnection();
        if (con != null) {

            try {
                PreparedStatement pstmt = con.prepareStatement(SQLConstants.QUERY_DELETE_GROUP_DETAILS);
                pstmt.setInt(1, groupId);
                int i = pstmt.executeUpdate();
                if (i != 0) {
                    response.setStatus((byte) 1);
                    response.setMessage(MessageConstants.GROUP_DETAIL_DELETE);
                }
            } catch (SQLException ex) {
                log.info(MessageConstants.GROUP_DETAIL_NOT_DELETE);
                log.error(ex.getMessage());
                response.setMessage(MessageConstants.GROUP_DETAIL_NOT_DELETE);
            }
        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method deleteGroupName");
        return response;
    }

    /**
     * This method update a group
     *
     * @param groupId
     * @param groupName
     * @return
     */
    @Override
    public Response updateGroup(int groupId, String groupName) {
        log.info("Start execution of method updateGroup(int groupId,String groupName)");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.GROUP_UPDATE_NOT_SUCESSFULL);
        Connection con = DBConnection.getConnection();
        if (con != null) {
            try {
                PreparedStatement pstmt = con.prepareStatement(SQLConstants.QUERY_UPDATE_GROUP_DETAILS);
                pstmt.setString(1, groupName);
                pstmt.setString(2, FeeManagement.userName);
                pstmt.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
                pstmt.setInt(4, groupId);

                int i = pstmt.executeUpdate();

                if (i != 0) {

                    response.setStatus((byte) 1);
                    response.setMessage(MessageConstants.GROUP_UPDATE_SUCESSFULL);

                }
            } catch (SQLException ex) {
                log.info(MessageConstants.GROUP_UPDATE_NOT_SUCESSFULL);
                log.error(ex.getMessage());
                response.setMessage(MessageConstants.GROUP_UPDATE_NOT_SUCESSFULL);
            }

        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method updateGroup(int groupId,String groupName)");
        return response;
    }

    /**
     * save class into database
     *
     * @param className
     * @param groupId
     * @return response
     */
    @Override
    public Response saveClass(String className, int groupId) {
        log.info("Start execution of method saveClass");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.CLASS_NOT_SAVE_SUCESSFULLY);
        String groupName = " ";
        Connection con = DBConnection.getConnection();
        if (con != null) {
            if (className != null && className.trim().length() > 0 && groupId != 0) {
                if (!isGroupAndClassAlreadySave(groupName, className, groupId)) {
                    if (GetIdHelper.getMaxId(SQLConstants.QUERY_GET_GROUPDETAIL_MAXID, SQLConstants.INITAL_VALUE_FOR_GROUP) > 0) {
                        try {

                            PreparedStatement pstmt2 = con.prepareStatement(SQLConstants.QUERY_INSERT_CLASSDETAIL);

                            pstmt2.setInt(1, GetIdHelper.getMaxId(SQLConstants.QUERY_GET_CLASSDETAIL_MAXID, SQLConstants.INITAL_VALUE_FOR_CLASS));
                            pstmt2.setString(2, className);
                            pstmt2.setInt(3, groupId);
                            pstmt2.setString(4, FeeManagement.userName);
                            pstmt2.setDate(5, new java.sql.Date(new java.util.Date().getTime()));
                            pstmt2.setDate(6, new java.sql.Date(new java.util.Date().getTime()));
                            pstmt2.setString(7, FeeManagement.userName);

                            int i = pstmt2.executeUpdate();
                            if (i > 0) {
                                response.setStatus((byte) 1);
                                response.setMessage(MessageConstants.CLASS_SAVE_SUCESSFULLY);
                            }
                        } catch (SQLException ex) {
                            log.info(MessageConstants.CLASS_NOT_SAVE_SUCESSFULLY);
                            log.error(ex.getMessage());
                            response.setMessage(MessageConstants.CLASS_NOT_SAVE_SUCESSFULLY);
                        }

                    } else {
                        log.info("Can not get maxid  ");
                        response.setMessage(MessageConstants.CLASS_NOT_SAVE_SUCESSFULLY);
                    }

                } else {
                    log.info(MessageConstants.CLASS_ALREADY_EXIST);
                    response.setMessage(MessageConstants.CLASS_ALREADY_EXIST);
                }
            } else {
                log.info("Fee detail Object is Null ");
                response.setMessage(MessageConstants.SUPPLY_ALL_VALUE);
            }

        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }

        log.info("End of method saveClass");
        return response;
    }

    /**
     * get class details from database
     *
     * @param groupId
     * @return response
     */
    @Override
    public Response getClassDetails(int groupId) {
        log.info("Start execution of method getClassDetails()");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.CLASS_DETAIL_NOT_FOUND);
        List<GroupAndClass> groupAndClassList = new ArrayList<>();
        GroupAndClass groupAndClass = null;
        Connection con = DBConnection.getConnection();
        if (con != null) {
            try {

                PreparedStatement pstmt = con.prepareStatement(SQLConstants.QUERY_GET_CLASSDETAIL);
                pstmt.setInt(1, groupId);
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
                log.info(MessageConstants.CLASS_DETAIL_NOT_FOUND);
                log.error(ex.getMessage());
                response.setMessage(MessageConstants.CLASS_DETAIL_NOT_FOUND);
            }
        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method getClassDetails()");
        return response;
    }

    /**
     * This method update a class details
     *
     * @param classId
     * @param className
     * @return
     */
    @Override
    public Response updateClassDetail(int classId, String className) {
        log.info("Start execution of method updateClassDetail");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.CLASS_UPDATE_NOT_SUCESSFULL);
        Connection con = DBConnection.getConnection();
        if (con != null) {
            try {
                PreparedStatement pstmt = con.prepareStatement(SQLConstants.QUERY_UPDATE_CLASS_DETAILS);
                pstmt.setString(1, className);
                pstmt.setString(2, FeeManagement.userName);
                pstmt.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
                pstmt.setInt(4, classId);

                int i = pstmt.executeUpdate();

                if (i != 0) {
                    System.out.println("Classs iod  " + classId + " classs Name   " + className);
                    response.setStatus((byte) 1);
                    response.setMessage(MessageConstants.CLASS_UPDATE_SUCESSFULL);
                }
            } catch (SQLException ex) {
                log.info(MessageConstants.CLASS_UPDATE_NOT_SUCESSFULL);
                log.error(ex.getMessage());
                response.setMessage(MessageConstants.CLASS_UPDATE_NOT_SUCESSFULL);
            }

        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method updateClassDetail");
        return response;

    }

    /**
     * This method delete a class details based on groupId
     *
     * @return Response
     */
    @Override
    public Response deleteClassName(int classId) {
        log.info("Start execution of method deleteGroupName");
        response.setStatus((byte) 0);
        response.setMessage(MessageConstants.CLASS_DETAIL_NOT_DELETE);
        Connection con = DBConnection.getConnection();
        if (con != null) {

            try {
                PreparedStatement pstmt = con.prepareStatement(SQLConstants.QUERY_DELETE_CLASS_DETAILS);
                pstmt.setInt(1, classId);

                int i = pstmt.executeUpdate();

                if (i != 0) {

                    response.setStatus((byte) 1);
                    response.setMessage(MessageConstants.CLASS_DETAIL_DELETE);

                }
            } catch (SQLException ex) {
                log.info(MessageConstants.CLASS_DETAIL_NOT_DELETE);
                log.error(ex.getMessage());
                response.setMessage(MessageConstants.CLASS_DETAIL_NOT_DELETE);

            }

        } else {
            log.info(MessageConstants.CONNECTION_ERROR);
            response.setMessage(MessageConstants.CONNECTION_ERROR);
        }
        log.info("End of method deleteGroupName");
        return response;
    }
}
