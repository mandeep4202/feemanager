/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.testing;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilder;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.DynamicReportBuilder;
import ar.com.fdvs.dj.domain.builders.StyleBuilder;
import ar.com.fdvs.dj.domain.constants.Border;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;
import ar.com.fdvs.dj.domain.entities.columns.AbstractColumn;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author 786
 */
public class EmployeeReport {
    
    private final Collection<Employee> list = new ArrayList<>();
    
    public EmployeeReport(Collection<Employee> c) {
        list.addAll(c);
    }
    
    public JasperPrint getReport() throws ColumnBuilderException, JRException, ClassNotFoundException {
        Style headerStyle = createHeaderStyle();
        Style detailTextStyle = createDetailTextStyle();
        Style detailNumberStyle = createDetailNumberStyle();
        DynamicReport dynaReport = getReport(headerStyle, detailTextStyle, detailNumberStyle);
        JasperPrint jp = DynamicJasperHelper.generateJasperPrint(dynaReport, new ClassicLayoutManager(), new JRBeanCollectionDataSource(list));
        return jp;
    }
    
    private Style createHeaderStyle() {
        StyleBuilder sb = new StyleBuilder(true);
        sb.setFont(Font.ARIAL_MEDIUM_BOLD);
        sb.setBorder(Border.THIN());
        sb.setBorderBottom(Border.PEN_2_POINT());
        sb.setBorderColor(Color.BLACK);
        sb.setBackgroundColor(Color.LIGHT_GRAY);
        sb.setTextColor(Color.BLUE);
        sb.setHorizontalAlign(HorizontalAlign.CENTER);
        sb.setVerticalAlign(VerticalAlign.MIDDLE);
        sb.setTransparency(ar.com.fdvs.dj.domain.constants.Transparency.OPAQUE);
        return sb.build();
    }
    
    private Style createDetailTextStyle() {
        StyleBuilder sb = new StyleBuilder(true);
        sb.setFont(Font.VERDANA_MEDIUM);
        sb.setBorder(Border.DOTTED());
        sb.setBorderColor(Color.BLACK);
        sb.setTextColor(Color.BLACK);
        sb.setHorizontalAlign(HorizontalAlign.LEFT);
        sb.setVerticalAlign(VerticalAlign.MIDDLE);
        sb.setPaddingLeft(5);
        return sb.build();
    }
    
    private Style createDetailNumberStyle() {
        StyleBuilder sb = new StyleBuilder(true);
        sb.setFont(Font.VERDANA_MEDIUM);
        sb.setBorder(Border.DOTTED());
        sb.setBorderColor(Color.BLACK);
        sb.setTextColor(Color.BLACK);
        sb.setHorizontalAlign(HorizontalAlign.RIGHT);
        sb.setVerticalAlign(VerticalAlign.MIDDLE);
        sb.setPaddingRight(5);
        return sb.build();
    }
    
    private AbstractColumn createColumn(String property, Class type,
            String title, int width, Style headerStyle, Style detailStyle)
            throws ColumnBuilderException {
        AbstractColumn columnState;
        columnState = ColumnBuilder.getNew().setColumnProperty(property, type.getName()).setTitle(title).setWidth(Integer.valueOf(width))
                .setStyle(detailStyle).setHeaderStyle(headerStyle).build();
         return columnState;
    }
    
    private DynamicReport getReport(Style headerStyle, Style detailTextStyle, Style detailNumStyle) throws ColumnBuilderException, ClassNotFoundException {
        
        DynamicReportBuilder report = new DynamicReportBuilder();
        
        AbstractColumn columnEmpNo = createColumn("empNo", Integer.class, "Employee Number", 30, headerStyle, detailTextStyle);
        AbstractColumn columnName = createColumn("name", String.class, "Name", 30, headerStyle, detailTextStyle);
        AbstractColumn columnSalary = createColumn("salary", Integer.class, "Salary", 30, headerStyle, detailNumStyle);
        AbstractColumn columnCommission = createColumn("commission", Float.class, "Commission", 30, headerStyle, detailNumStyle);
        AbstractColumn friend = createColumn("ex", Extra.class, "Extra Work", 30, headerStyle, detailNumStyle);
        report.addColumn(columnEmpNo)
                .addColumn(columnName).addColumn(columnSalary).addColumn(columnCommission).addColumn(friend);
        
        StyleBuilder titleStyle = new StyleBuilder(true);
        titleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        titleStyle.setFont(new Font(20, Font._FONT_COMIC_SANS, true));
        titleStyle.setTextColor(Color.RED);
        
        StyleBuilder subTitleStyle = new StyleBuilder(true);
        subTitleStyle.setHorizontalAlign(HorizontalAlign.CENTER);
        subTitleStyle.setFont(new Font(20, Font._FONT_GEORGIA, true));
        
        report.setTitle("Target-!! Education Group");
        report.setTitleStyle(titleStyle.build());
        report.setSubtitle("Commission received by Employee");
        report.setSubtitleStyle(subTitleStyle.build());
        report.setUseFullPageWidth(true);
        return report.build();
    }
}
