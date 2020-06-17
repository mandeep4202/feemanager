/**
 * Copyright (c) 2015,  FeeManagement and/or its affiliates. All rights reserved.
 */

package com.feemanagement.dao;

import com.feemanagement.dto.FeeDetails;
import com.feemanagement.dto.Response;
import java.util.List;

/**
 *
 * @author Mandeep SIngh
 */
public interface FeeDetailDAO {

      /**
     * This method is used to save fee detail  in persistence storage
     *
     * @param feeDetailsList
     * @return response
     */
    public Response saveFeeDetail(List<FeeDetails> feeDetailsList);

      /**
     * This method is used to get Full fee detail from database based on parameter
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
     * This method contain to delete the fee details
     *
     * @param feeId
     * @return response
     */
public Response deleteFeeDetail(int feeId);
}
