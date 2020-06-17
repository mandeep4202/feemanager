/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */

package com.feemanagement.dao;

import com.feemanagement.dto.GroupAndClass;
import com.feemanagement.dto.Response;
import java.util.List;

/**
 *
 * @author Mandeep Singh
 */
public interface DisplayAllFeeDetailDAO {
     /**
     * get all classes from database
     *
     * @return response
     */
    public Response getAllClasses();
    
    
      /**
     * get all particulars based on the classes 
     *
     * @param classList
     * @return response
     */
    public Response getParticularBasedOnClass(List<GroupAndClass> classList,int sessionId);
}
