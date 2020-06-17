/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.util;

/**
 *
 * @author Mandeep Singh
 */
public class SQLConstants {

    /**
     * Query to get max(id) of fee_details table
     */
    public static final String QUERY_GET_FEEDETAIL_MAXID = "select max(feeid) from fee_details";
    /**
     * Query to get max(id) of group_details table
     */
    public static final String QUERY_GET_GROUPDETAIL_MAXID = "select max(groupid) from group_details";

    /**
     * Query to get max(id) of fine_details table
     */
    public static final String QUERY_GET_FINEDETAIL_MAXID = "select max(groupid) from fine_details";

    /**
     * Query to get max(id) of group_details table
     */
    public static final String QUERY_GET_PAYMENTDETAIL_MAXID = "select max(paymentmodeid) from payment_details";

    /**
     * Query to get max(id) of group_details table
     */
    public static final String QUERY_GET_VOUCHERDETAIL_MAXID = "select max(voucherid) from voucher_details";

    /**
     * Query to get max(id) of group_details table
     */
    public static final String QUERY_GET_FEEPAY_MAXID = "select max(feepayid) from paidfee_details";

    /**
     * Query to get max(id) of fee_details table
     */
    public static final String QUERY_GET_CLASSDETAIL_MAXID = "select max(classid) from class_details";

    /**
     * insert data into fee_details table
     */
    public static final String QUERY_INSERT_FEEDETAIL = "insert into fee_details values(?,?,?,?,?,?,?,?,?,?)";

    /**
     * insert data into fee_details table
     */
    public static final String QUERY_SELECT_CLASSID = "select classid from class_details where groupid=?";

    /**
     * insert data into group_details table
     */
    public static final String QUERY_INSERT_GROUPDETAIL = "insert into group_details values(?,?,?,?,?,?)";

    /**
     * insert data into payment_details table
     */
    public static final String QUERY_INSERT_PAYMENTDETAIL = "insert into payment_details values(?,?,?,?,?,?,?,?,?,?)";

    /**
     * insert data into voucher_details table
     */
    public static final String QUERY_INSERT_VOUCHERDETAIL = "insert into voucher_details values(?,?,?,?,?,?,?,?,?)";

    /**
     * insert data into payfee_details table
     */
    public static final String QUERY_INSERT_PAYFEE = "insert into paidfee_details values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

    /**
     * insert data into group_details table
     */
    public static final String QUERY_INSERT_CLASSDETAIL = "insert into class_details values(?,?,?,?,?,?,?)";

    /**
     * fetch fee detail for Parameter group=All and Class=All
     */
    public static final String QUERY_GET_FEEDETAIL_All_All = "select fd.feeid,(select classname from class_details where classid=fd.classid)classname,fd.particularsname,fd.particularamount,\n"
            + "(select studentsession from session_details where sessionid=fd.sessionid) as session \n"
            + " from fee_Details fd where fd.classid in(select classid from class_details\n"
            + " where groupid in (select groupid from group_details)) and fd.sessionid=?";

    /**
     * fetch fee detail for Parameter group=Any and Class=All
     */
    public static final String QUERY_GET_FEEDETAIL_ANY_ALL = "select fd.feeid,(select classname from class_details where classid=fd.classid)classname,fd.particularsname,fd.particularamount,\n"
            + "(select studentsession from session_details where sessionid=fd.sessionid) as session \n"
            + " from fee_Details fd where fd.classid in(select classid from class_details\n"
            + " where groupid=?) and fd.sessionid=?";

    /**
     * fetch fee detail for Parameter group=Any and Class=Any
     */
    public static final String QUERY_GET_FEEDETAIL_ANY_ANY = "select fd.feeid,(select classname from class_details where classid=fd.classid)classname,fd.particularsname,fd.particularamount,\n"
            + "(select studentsession from session_details where sessionid=fd.sessionid) as session \n"
            + " from fee_Details fd where fd.classid=? and fd.sessionid=?";

    /**
     * query get detail of a user if user is a valid user
     */
    public static final String QUERY_CHECK_VALIDUSER = "select lastlogindate,lastlogintime from securitycheck where username=? and password=?";

    /**
     * Select data from group_details
     */
    public static final String QUERY_GET_GROUPDETAIL = "select groupid,groupname from group_details";

    /**
     * Select data from group_details
     */
    public static final String QUERY_GET_FINEDETAIL = "select f.fineid,f.fineamount,(select studentsession from session_details where sessionid=f.sessionid)session from fine_details f where f.regdno=? and f.sessionid=?";

    /**
     * Select data from paid fee_details
     */
    public static final String QUERY_GET_PAYFEEDETAIL = "select classid,sessionid,voucherid,sum(payamount),paydate from paidfee_details where classid=? and sessionid=? and regdno=?";

    /**
     * Select all installment from paid fee_details
     */
    public static final String QUERY_GET_PAYINSTALLMENT = "select feepayid,(select classname from class_details where classid=p.classid) classname,"
            + "(select studentsession from session_details where sessionid=p.sessionid )session,\n"
            + "(select paymentmode from voucher_details where voucherid=p.voucherid) paymentmode,payamount,remainingfee,voucherid,paydate,paytime\n"
            + " from paidfee_details as p where classid=? and sessionid=? and regdno=?";

    /**
     * Select data from student_details
     */
    public static final String QUERY_GET_STUDENTDETAIL = "select firstname,lastname,fatherfirstname,fatherlastname,(select classname  from class_details where classid=sd.classid) classname,\n"
            + "(select studentsession from session_details where sessionid=sd.sessionid) sessionname,(select groupname  from group_details where groupid=sd.groupid) groupname\n"
            + " ,isactive,gender,classid,groupid,sessionid from student_details as sd where regdno=?";

    /**
     * insert data into fee_details table
     */
    public static final String QUERY_GET_CLASSDETAIL = "select classid,classname,(select groupname from group_details where groupid=cd.groupid) as groupname from class_details cd where cd.groupid=?";

    /**
     * insert data into fee_details table
     */
    public static final String QUERY_GET_ALL_CLASSDETAIL = "select cd.classid,cd.classname,(select groupname from group_details where groupid=(select groupid from class_details where classid=cd.classid)) from class_details cd";

    /**
     * insert data into fee_details table
     */
    public static final String QUERY_GET_CLASSID_BASED_CLASSDETAIL = "select fd.feeid,(select classname from class_details where classid=fd.classid)classname,fd.particularsname,fd.particularamount,\n"
            + "(select studentsession from session_details where sessionid=fd.sessionid) as session \n"
            + " from fee_Details fd where fd.classid=? and fd.sessionid=? order by fd.feeid";

    /**
     * check feeDetails already exist
     */
    public static final String QUERY_FEEDETAIL_ALREADY_EXIST = "select classid from fee_details where classid=? and particularsname=? and sessionid=?";

    /**
     * check feeDetails already exist
     */
    public static final String QUERY_GROUP_ALREADY_EXIST = "select groupname from group_details where groupname=?";

    /**
     * check feeDetails already exist
     */
    public static final String QUERY_CLASS_ALREADY_EXIST = "select classname from class_details where classname=? and groupid=?";

    /**
     * update data in fee_details table
     */
    //public static final String QUERY_UPDATE_FEE_DETAILS = "update fee_details set classid=?,groupid=?,particularsname=?,particularamount=?,sessionid=?,createdBy=?,lastmodified=? where feeid=?";
    public static final String QUERY_UPDATE_FEE_DETAILS = "update fee_details set particularsname=?,particularamount=?,createdBy=?,lastmodified=? where feeid=?";

    /**
     * update data in fee_details table
     */
    public static final String QUERY_UPDATE_GROUP_DETAILS = "update group_details set groupname=?,lastmodifiedby=?,lastmodified=? where groupid=?";

    /**
     * update data in fine_details table
     */
    public static final String QUERY_UPDATE_FINE_DETAILS = "update fine_details set fineamount=?,lastmodifiedby=?,lastmodified=? where fineid=?";

    /**
     * update data in fee_details table
     */
    public static final String QUERY_UPDATE_CLASS_DETAILS = "update class_details set classname=?,lastmodifiedby=?,lastmodified=? where classid=?";

    /**
     * update data in fee_details table
     */
    public static final String QUERY_DELETE_FEE_DETAILS = "delete from fee_details where feeid=?";

    /**
     * update data in group_details table
     */
    public static final String QUERY_DELETE_GROUP_DETAILS = "delete from group_details where groupid=?";

    /**
     * update data in group_details table
     */
    public static final String QUERY_DELETE_CLASS_DETAILS = "delete from class_details where classid=?";

    /**
     * Setting initial value for feeID
     */
    public static final String INITAL_VALUE_FOR_FEE = "5000";

    /**
     * Setting initial value for Group ID
     */
    public static final String INITAL_VALUE_FOR_GROUP = "100";

    /**
     * Setting initial value for Group ID
     */
    public static final String INITAL_VALUE_FOR_FINE = "250";

    /**
     * Setting initial value for Class ID
     */
    public static final String INITAL_VALUE_FOR_CLASS = "300";

    /**
     * Setting initial value for payment mode ID
     */
    public static final String INITAL_VALUE_FOR_PAYMENT = "700";

    /**
     * Setting initial value for payment mode ID
     */
    public static final String INITAL_VALUE_FOR_VOUCHER = "555";

}
