/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.services;

import com.feemanagement.dto.FeeDetails;
import com.feemanagement.dto.Response;
import java.util.List;

/**
 *
 * @author Mandeep Singh
 */
public interface FeeDetailService {

    /**
     * This method is used to forward request to DAO for persistence storage
     * @param feeDetailsList
     * @return response
     */
    public Response saveFeeDetail(List<FeeDetails> feeDetailsList);
    
     /**
     * This method is used to forward request to DAO for getting Full fee Detail
     *
     * @param feeDetails
     * @return response
     */
    public Response getFeeDetail(FeeDetails feeDetails);
    
     /**
     * This method is used to update the fee details
     *
     * @param feeDetails
     * @return response
     */
    public Response updateFeeDetail(FeeDetails feeDetails);
    
      /**
     * This method is used to delete the fee details
     *
     * @param feeId
     * @return response
     */
public Response deleteFeeDetail(int feeId);
}
