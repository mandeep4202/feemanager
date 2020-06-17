/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */

package com.feemanagement.services;

import com.feemanagement.dto.Response;

/**
 *
 * @author Mandeep Singh
 */
public interface PrintFeeReciptService {
    
        /**
     * This method is used to forward request to DAO for getting Student And
     * Installment Information
     *
     * @param regdNo
     * @param sessionId
     * @return response
     */
 public Response getStudentAndInstallmentDetail(int regdNo, int sessionId);
}
