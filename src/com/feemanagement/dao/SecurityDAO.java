/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.dao;

import com.feemanagement.dto.Response;
import com.feemanagement.dto.User;

/**
 *
 * @author Mandeep Singh
 */
public interface SecurityDAO {

    /**
     * This method check whether the requested user is valid user or not
     *
     * @param user
     */
    public Response validUser(User user);

    /**
     * This method log out the user
     */
    public Response logout();

}
