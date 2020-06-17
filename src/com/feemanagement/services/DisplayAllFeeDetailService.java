/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.services;

import com.feemanagement.dto.Response;

/**
 *
 * @author Mandeep Singh
 */
public interface DisplayAllFeeDetailService {

    /**
     * get all the fee details for all classes
     *
     * @return response
     */
    public Response displayAllFeeDetail(int sessionId);

}
