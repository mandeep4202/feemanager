/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.backup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Takes backup of database
 *
 * @author Mandeep Singh
 */
public class DatabaseBackup {

    private final int STREAM_BUFFER = 512000;

    public boolean backupDatabase(String host, String port, String user, String password, String dbname, String rootpath, String dbexepath) {

        boolean success = false;
        try {
            String dump = getServerDumpData(host, port, user, password, dbname, dbexepath);
            if (!dump.isEmpty()) {
                byte[] data = dump.getBytes();

                File file = new File(rootpath + "/" + dbname);
                if (!file.isDirectory()) {
                    file.mkdir();
                }

                DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHms");
                Date date = new Date();
                String filepath = rootpath + "/" + dbname + "/" + dbname + dateFormat.format(date) + ".sql";
                File filedst = new File(filepath);
                try (FileOutputStream dest = new FileOutputStream(filedst)) {
                    dest.write(data);
                }
                success = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return success;
    }

    public String getServerDumpData(String host, String port, String user, String password, String db, String mysqlpath) {

        StringBuilder dumpdata = new StringBuilder();
        String execline = mysqlpath;
        try {
            if (host != null && user != null && password != null && db != null) {

                String command[] = new String[]{execline,
                    "--host=" + host,
                    "--port=" + port,
                    "--user=" + user,
                    "--password=" + password,
                    "--compact",
                    "--complete-insert",
                    "--extended-insert",
                    "--skip-comments",
                    "--skip-triggers",
                    db};
                ProcessBuilder pb = new ProcessBuilder(command);
                Process process = pb.start();
                InputStream in = process.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                int count;
                char[] cbuf = new char[STREAM_BUFFER];

                while ((count = br.read(cbuf, 0, STREAM_BUFFER)) != -1) {
                    dumpdata.append(cbuf, 0, count);
                }
                br.close();
                in.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        return dumpdata.toString();
    }
}
