/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.testing;

import com.feemanagement.dao.FineDAOImpl;
import com.feemanagement.dto.Response;

/**
 *
 * @author Mandeep Singh
 */
public class GetFine {

    public static void main(String[] args) {

        Response response = new FineDAOImpl().getFineDetail(50011, 700);
        if (response.getStatus() == 1) {
            System.out.println("date  " + response.getData());

        } else {
            System.out.println("not getting the detail ");
        }
    }
}
