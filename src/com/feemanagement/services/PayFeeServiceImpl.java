/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.services;

import com.feemanagement.dao.FeeDetailDAOImpl;
import com.feemanagement.dao.PayFeeDAOImpl;
import com.feemanagement.dao.StudentDAOImpl;
import com.feemanagement.dto.FeeDetails;
import com.feemanagement.dto.PayFee;
import com.feemanagement.dto.PaymentDetails;
import com.feemanagement.dto.Response;
import com.feemanagement.dto.Student;
import com.feemanagement.dto.VoucherType;
import java.util.List;

/**
 *
 * @author Mandeep Singh
 */
public class PayFeeServiceImpl implements PayFeeService {

    /**
     * This method is used to forward request to DAO for getting Student And
     * Student class feeInformation
     *
     * @param regdNo
     * @param sessionId
     * @return response
     */
    @Override
    public Response getStudentAndFeeDetail(int regdNo, int sessionId) {
        Response response = new StudentDAOImpl().getStudentDetail(regdNo, sessionId);
        Student student = null;
        if (response.getStatus() == 1) {
            FeeDetails feeDetails = new FeeDetails();
            student = (Student) response.getData();

            feeDetails.setClassId(student.getGroupAndClass().getClassId());
            feeDetails.setSessionId(sessionId);

            // calling a method to get fee detail
            response = new FeeDetailDAOImpl().getFeeDetail(feeDetails);
            //  response.setStatus((byte) 1);
            //checking for if fee detail found or not
            response.setStatus((byte) 1);
            if (response.getStatus() == 1) {
                student.setRegdNo(regdNo);
                student.setFeeDetailList((List<FeeDetails>) response.getData());

                response.setData(student);

                //response.setStatus((byte) 1);
                response = new PayFeeDAOImpl().getPaidFeeDetails(regdNo, student.getGroupAndClass().getClassId(), sessionId);
                response.setStatus((byte) 1);
                // if this if condition is true than we add a pay fee detail to the student object
                if (response.getStatus() == 1) {
                    response.setStatus((byte) 1);
                    //adding the pay detail list to student object
                    student.setPayFees((List<PayFee>) response.getData());
                    // attaching new modified student object to the response object
                    response.setData(student);
                    response.setStatus((byte) 1);
                } else {
                    // response.setStatus((byte) 1);
                }
            } else {
                // response.setStatus((byte) 1);
                response.setMessage("Fee Detail Not Found ");
            }
        }
        return response;
    }

    /**
     * This method is used to forward request to DAO for getting Student And
     * Student's class feeInformation
     *
     * @param regdno
     * @param classid
     * @param sessionid
     */
    @Override
    public Response getPaidFeeDetails(int regdno, int classid, int sessionid) {

        return null;
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
        return new PayFeeDAOImpl().payFee(student, voucherType, paymentDetails);
    }
}
