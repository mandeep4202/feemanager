/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.feemanagement.dao;

import com.feemanagement.dto.PaymentDetails;
import com.feemanagement.dto.PaymentMode;
import com.feemanagement.dto.Response;
import com.feemanagement.dto.Student;
import com.feemanagement.dto.VoucherType;

/**
 *
 * @author 786
 */
public interface PayFeeDAO {

    /**
     * This method get the paid fee detail of the student base on following parameter
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
