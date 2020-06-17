/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author Mandeep Singh
 */
public class MyLogger {

    static {
        /**
         * This statement is used to configure the log4j property file
         */
        PropertyConfigurator.configure(PathConstants.log4j_CONFIG_PATH);
    }

    /**
     * This factory method provide the logger having log4j properties file
     * configuration
     * @param class Class object
     * @return Logger object
     */
    public static Logger getLogger(Class c) {
        Logger log = Logger.getLogger(c);
        return log;
    }

}
