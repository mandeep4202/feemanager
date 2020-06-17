/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 *
 * @author Mandeep SIngh
 */
public class PathConstants {

    /**
     * specify a path to database related property file i.e. dbConfig.properties
     */
    public static final String DB_CONFIG_PATH = "src/com/feemanagement/property/dbConfig.properties";
    /**
     * specify a path to log4j related property file i.e. log4j.properties
     */
    public static final String log4j_CONFIG_PATH = "src/com/feemanagement/property/log4j.properties";
    /**
     * specify the path for background image
     */
    public static final String BACKGROUNG_IMAGE_PATH = "/com/feemanagement/images/379375.jpg";
    /**
     * Specify path for a delete image
     */
    public static final String DELETE_IMAGE_PATH = "/com/feemanagement/images/b_drop.png";
    /**
     * Specify path for a update image
     */
    public static final String UPDATE_IMAGE_PATH = "/com/feemanagement/images/b_edit.png";
    /**
     * Specify path of print receipt jasper Report
     */
    // public static final String PRINT_RECEIPT_REPORT_PATH = "C:\\Users\\mandeep\\Desktop\\Printing12\\PrintReceipt.jrxml";
    //G:\\netbean\\FeeManager\\src\\com\\feemanagement\\util\\PrintReceipt.jrxml

    public static final String PRINT_RECEIPT_REPORT_PATH = "/com/feemanagement/util/PrintReceipt.jrxml";

//    public String getPrintReceiptJRXML() {
//        return getUTF8DecodedPath(printReceiptJRXML);
//    }

    // public static final String headerImage="C:\\Users\\mandeep\\Desktop\\Printing12\\finalLong.jpg";
    public static final String headerImage = "/com/feemanagement/images/finalLong.jpg";

    public String getHeaderImage() {
        return getUTF8DecodedPath(headerImage);
    }

    public String getUTF8DecodedPath(String path){
        
        String relativePath=getClass().getResource(headerImage).getPath();
        String decodedPath=null;
        if(relativePath!=null){
            decodedPath=relativePath.replaceAll("%20", " ");
        }
       return decodedPath;  
    } 
    
}
