/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.backup;

/**
 *
 * @author Mandeep Singh
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Mandeep Singh
 */
public class FolderBackup {

    public Boolean zipFolder(String srcFolder, String destZipFile) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;
        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);
        addFolderToZip(srcFolder, zip);
        zip.flush();
        zip.close();
        return true;
    }

    public void addFileToZip(String srcFile, ZipOutputStream zip) throws Exception {
        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            zip.putNextEntry(new ZipEntry("/" + folder.getName()));
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
        }
    }

    public void addFolderToZip(String srcFolder, ZipOutputStream zip) throws Exception {
        File folder = new File(srcFolder);
        for (String fileName : folder.list()) {
            addFileToZip(srcFolder + "/" + fileName, zip);
        }
    }
}
