/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */

package com.feemanagement.services;

import com.feemanagement.dto.Response;

/**
 *
 * @author Mandeep Singh
 */
public interface FineService {
  /**
     * This method save the individual student fine 
     * @param regdNo
     * @param sessionId
     * @param fineAmount
     * @return response
     */
    public Response saveFine(int regdNo,int sessionId,int fineAmount);

    /**
     * This method update the fine detail of the student 
     * @param feeId
     * @param fineAmount
     * @return response
     */
    public Response updateFine(int feeId,int fineAmount);
  
       /**
     * Get Fine detail
     * @param regdNo
     * @param sessionId
     * @return 
     */
    public Response getFineDetail(int regdNo,int sessionId);
}
