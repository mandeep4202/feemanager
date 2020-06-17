/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */

package com.feemanagement.dao;

import com.feemanagement.dto.Response;

/**
 *
 * @author 786
 */
public interface PrintFeeReciptDAO {
    /**
     * This method get the paid fee detail of the student base on following
     * parameter
     *
     * @param regdno
     * @param classid
     * @param sessionid
     * @return Response
     */
    public Response getAllInstallmentDetail(int regdno, int classid, int sessionid);
}
