/**
 * Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author Mandeep Singh
 */
public class DBConnection {

    /**
     * getting logger
     */
    static final Logger log = MyLogger.getLogger(com.feemanagement.util.DBConnection.class);

    /**
     * This field holds the connection with database
     */
    public static Connection connection;

    /**
     * Block get database connection and store connection reference in
     * connection static variable
     */
    static {
        try {
            log.info("Start of Database Connection creating static block");
            /**
             * loading the dbconfig file to get required info to create database
             * connection
             */
            FileInputStream fis = new FileInputStream(PathConstants.DB_CONFIG_PATH);
            Properties properties = new Properties();
            properties.load(fis);

            Class.forName(properties.getProperty("classname"));
            connection = DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
            log.info(MessageConstants.CONNECTION_SUCCESSFULLY_CREATED);
        } catch (FileNotFoundException ex) {
            log.error(MessageConstants.DBPROPERTY_NOT_FOUND);
        } catch (ClassNotFoundException ex) {
            log.error(MessageConstants.CLASS_NOT_FOUND);
        } catch (SQLException ex) {
            log.error(MessageConstants.CONNECTION_ERROR);
            ex.printStackTrace();
        } catch (IOException ex) {
            log.error(MessageConstants.DBPROPERTY_NOT_FOUND);
        }
        log.info("End of the connection creating  static block");
    }

    public static Connection getConnection() {
        return connection;
    }
}
