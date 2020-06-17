/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.testing;

/**
 *
 * @author 786
 */
import com.feemanagement.util.DBConnection;
import java.sql.Connection;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.exception.DRException;

public class SimpleReportExample {

    public static void main(String[] args) {
        Connection connection = null;
        connection = DBConnection.getConnection();

        JasperReportBuilder report = DynamicReports.report();//a new report
        JasperReportBuilder report1 = DynamicReports.report();
        for (int i = 307; i < 308; i++) {
            report
                    .columns(
                            Columns.column("Customer Id", "feeid", DataTypes.integerType()),
                            Columns.column("First Name", "sessionid", DataTypes.integerType()),
                            Columns.column("Last Name", "groupid", DataTypes.integerType()),
                            Columns.column("Date", "classid", DataTypes.integerType()))
                    .title(//title of the report
                            Components.text("SimpleReportExample")
                            .setHorizontalAlignment(HorizontalAlignment.CENTER))
                    .pageFooter(Components.pageXofY())//show page number on the page footer
                    .setDataSource("SELECT feeid,sessionid,groupid,classid FROM libmanagement.fee_details where classid=308",
                            connection);
            report1
                    .columns(
                            Columns.column("Customer Id", "feeid", DataTypes.integerType()),
                            Columns.column("First Name", "sessionid", DataTypes.integerType()),
                            Columns.column("Last Name", "groupid", DataTypes.integerType()),
                            Columns.column("Date", "classid", DataTypes.integerType()))
                    .title(//title of the report
                            Components.text("SimpleReportExample")
                            .setHorizontalAlignment(HorizontalAlignment.CENTER))
                    .pageFooter(Components.pageXofY())//show page number on the page footer
                    .setDataSource("SELECT feeid,sessionid,groupid,classid FROM libmanagement.fee_details where classid=307",
                            connection);
        }

        try {
            //show the report
            report.show();
            report1.show();

                //export the report to a pdf file
            //report.toPdf(new FileOutputStream("c:/report.pdf"));
        } catch (DRException e) {
            e.printStackTrace();
        }
    }
}
