/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.services;

import com.feemanagement.dto.Response;
import com.feemanagement.dto.User;

/**
 *
 * @author Mandeep Singh
 */
public interface SecurityService {

    /**
     * This method check whether the requested user is valid user or not
     *
     * @param user
     * @return
     */
    public Response validUser(User user);

    /**
     * This method log out the user
     *
     * @return
     */
    public Response logout();

}
