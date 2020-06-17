/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */

package com.feemanagement.services;

import com.feemanagement.dto.PaymentDetails;
import com.feemanagement.dto.PaymentMode;
import com.feemanagement.dto.Response;
import com.feemanagement.dto.Student;
import com.feemanagement.dto.VoucherType;

/**
 *
 * @author Mandeep Singh
 */
public interface PayFeeService {
 /**
     * This method is used to forward request to DAO for getting Student  And Student's class feeInformation
     *
     * @param regdNo
     * @param sessionId
     * @return response
     */
    public Response getStudentAndFeeDetail(int regdNo,int sessionId);
    
     /**
     * This method is used to forward request to DAO for getting Student  And Student's class feeInformation
     *
     * @param regdno
     * @param classid
     * @param sessionid
     * @return Response
     */
    public Response getPaidFeeDetails(int regdno,int classid,int sessionid);
    
    
     /**
      * This method pay the fee of the user 
     * @param student
     * @param voucherType
     * @param paymentDetails
     * @return 
      */
     public Response payFee(Student student,VoucherType voucherType,PaymentDetails paymentDetails);
}
