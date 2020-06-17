/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.helper;

import com.feemanagement.dao.FeeDetailDAOImpl;
import com.feemanagement.util.DBConnection;
import com.feemanagement.util.MyLogger;
import com.feemanagement.util.PathConstants;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPropertiesUtil;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Logger;

/**
 *
 * @author 786
 */
public class CallingReport {

    static Logger log = MyLogger.getLogger(CallingReport.class);

    public static void callingReports(HashMap parameterMap, String reportPath) {
        Connection connection = null;
        try {
            connection = DBConnection.getConnection();
            DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();
            JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.awt.igno‌​re.missing.font", "true");
            /////////////
            InputStream resourceAsStream = CallingReport.class.getResourceAsStream(reportPath);
           // System.out.println("resourceAsStream "+ resourceAsStream);
        JasperReport jr = JasperCompileManager.compileReport(resourceAsStream) ;
            ///////////
            //JasperReport jr = JasperCompileManager.compileReport(reportPath) ;
            JasperPrint jp = null;
            jp = JasperFillManager.fillReport(jr, parameterMap, connection);
            JasperViewer.viewReport(jp, false);
        } catch (JRException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
