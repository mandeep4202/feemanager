/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.services;

import com.feemanagement.dao.FeeDetailDAOImpl;
import com.feemanagement.dto.FeeDetails;
import com.feemanagement.dto.Response;
import java.util.List;

/**
 *
 * @author Mandeep Singh
 */
public class FeeDetailServiceImpl implements FeeDetailService {

    /**
     * This method is used to process fee detail data and forward request to DAO
     * for persistence storage
     *
     * else
     *
     * @return response
     */
    @Override
    public Response saveFeeDetail(List<FeeDetails> feeDetailsList) {

        Response response = null;

        if (feeDetailsList.get(0).getGroupId() == 786786 || feeDetailsList.get(0).getClassId() == 786786) {
            response = new FeeDetailDAOImpl().saveFeeDetailForAll(feeDetailsList);
        } else {
            response = new FeeDetailDAOImpl().saveFeeDetail(feeDetailsList);
        }

        int[] responseArray = (int[]) response.getData();
        System.out.println("int  array size   " + responseArray.length);
        int count = 0;
        for (int itr : responseArray) {
            if (itr == 0) {
                count++;
            }
        }

        if (count == responseArray.length) {
            response.setMessage("Data not save due to duplicate values");
        } else if (count == 0) {
            response.setMessage("Data save sucessfully");
        } else {
            response.setMessage("Some of the data no save due to duplicate values");
        }
        return response;
    }

    /**
     * This method is used to forward request to DAO for getting Full fee Detail
     *
     * @param feeDetails
     * @return response
     */
    @Override
    public Response getFeeDetail(FeeDetails feeDetails) {
        return new FeeDetailDAOImpl().getFeeDetail(feeDetails);
    }

    /**
     * This method is used to update the fee details
     *
     * @param feeDetails
     * @return response
     */
    @Override
    public Response updateFeeDetail(FeeDetails feeDetails) {
          return new FeeDetailDAOImpl().updateFeeDetail(feeDetails);
    }

    /**
     * This method is used to delete the fee details
     *
     * @param feeId
     * @return response
     */
    @Override
    public Response deleteFeeDetail(int feeId) {
        return new FeeDetailDAOImpl().deleteFeeDetail(feeId);
    }

}
