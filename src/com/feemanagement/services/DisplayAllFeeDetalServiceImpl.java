/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */

package com.feemanagement.services;

import com.feemanagement.dao.DisplayAllFeeDetailDAOImpl;
import com.feemanagement.dto.FeeDetails;
import com.feemanagement.dto.GroupAndClass;
import com.feemanagement.dto.Response;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Mandeep Singh
 */
public class DisplayAllFeeDetalServiceImpl implements DisplayAllFeeDetailService{
   
    /**
     * get all the fee details for all classes
     *
     * @param sessionId
     * @return response
     */
    @Override
    public Response displayAllFeeDetail(int sessionId) {
        
        Response response=new DisplayAllFeeDetailDAOImpl().getAllClasses();
        
        if(response.getStatus()==1)
        {
           response= new DisplayAllFeeDetailDAOImpl().getParticularBasedOnClass( (List<GroupAndClass>) response.getData(),sessionId);
            
         if(response.getStatus()==1)
         {
          
     response.setStatus((byte)1);
         }
         else{
             response.setStatus((byte)0);
         }
            
            
        }
        else
        {
            System.out.println("sorry no class found ");     
        }
        
        return response;
    }

}
