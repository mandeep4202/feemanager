/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.services;

import com.feemanagement.dao.FineDAOImpl;
import com.feemanagement.dto.Response;

/**
 *
 * @author Mandeep SIngh
 */
public class FineServiceImpl implements FineService {

    /**
     * This method save the individual student fine
     *
     * @param regdNo
     * @param sessionId
     * @param fineAmount
     * @return response
     */
    @Override
    public Response saveFine(int regdNo, int sessionId, int fineAmount) {

        return new FineDAOImpl().saveFine(regdNo, sessionId, fineAmount);
    }

    /**
     * This method update the fine detail of the student
     *
     * @param feeId
     * @param fineAmount
     * @return response
     */
    @Override
    public Response updateFine(int feeId, int fineAmount) {
        return new FineDAOImpl().updateFine(feeId, fineAmount);
    }

    /**
     * Get Fine detail
     *
     * @param regdNo
     * @param sessionId
     * @return
     */
    @Override
    public Response getFineDetail(int regdNo, int sessionId) {
        return new FineDAOImpl().getFineDetail(regdNo, sessionId);
    }
}
