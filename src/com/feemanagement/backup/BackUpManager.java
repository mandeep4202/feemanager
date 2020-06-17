/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.backup;

import com.feemanagement.util.PathConstants;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author Mandeep SIngh
 */
public final class BackUpManager {

    public String DB_HOST;
    public String DB_NAME;
    public String DB_PORT;
    public String DB_USER;
    public String DB_PASS;
    public String FOLDER_NAME;
    public String DB_EXE;
    public String DESTINATION;
    public BigInteger timeNoted;
    String projectName = new String();

    public BackUpManager() {
        readConfiguration();
    }

    public void readConfiguration() {
        try {
            FileInputStream fis = new FileInputStream(PathConstants.DB_CONFIG_PATH);
            Properties prop = new Properties();
            prop.load(fis);

            DB_HOST = prop.getProperty("DB_HOST_BK");
            DB_NAME = prop.getProperty("DB_NAME_BK");
            DB_PORT = prop.getProperty("DB_PORT_BK");
            DB_USER = prop.getProperty("username");
            DB_PASS = prop.getProperty("password");
            FOLDER_NAME = prop.getProperty("DESTI_FOLDER_NAME");
            DB_EXE = prop.getProperty("DB_EXE_PATH");

            DESTINATION = prop.getProperty("DESTI_ZIP_PATH");

        } catch (IOException e) {
            System.out.println("Cannot read the property file.");
        }
    }

    public Boolean databaseBackup() {
        return new DatabaseBackup().backupDatabase(DB_HOST, DB_PORT, DB_USER, DB_PASS, DB_NAME, FOLDER_NAME, DB_EXE);
    }

    public Boolean folderBackup() throws Exception {
        if (new FolderBackup().zipFolder(FOLDER_NAME + "/" + DB_NAME, DESTINATION + "/" + DB_NAME + ".zip")) {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHms");
            Date nowtime = new Date();
            timeNoted = BigInteger.valueOf(Long.parseLong(dateFormat.format(nowtime)));
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        BackUpManager dm = new BackUpManager();
        try {
            if (dm.databaseBackup()) {
                if (dm.folderBackup()) {
                    System.out.println("Process complete.");
                } else {
                    System.out.println("Folder backup process failed.");
                }
            } else {
                System.out.println("Process failed sory.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
