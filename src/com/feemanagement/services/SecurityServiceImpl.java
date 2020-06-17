/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.services;

import com.feemanagement.dao.SecurityDAOImpl;
import com.feemanagement.dto.Response;
import com.feemanagement.dto.User;

/**
 *
 * @author Mandeep Singh
 */
public class SecurityServiceImpl implements SecurityService {

    @Override
    public Response validUser(User user) {
        return new SecurityDAOImpl().validUser(user);
    }

    @Override
    public Response logout() {
        return null;
    }

}
