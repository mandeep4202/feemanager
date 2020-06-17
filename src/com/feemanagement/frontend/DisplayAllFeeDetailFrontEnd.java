/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.frontend;

import com.feemanagement.dto.FeeDetails;
import com.feemanagement.dto.GroupAndClass;
import com.feemanagement.dto.Response;
import com.feemanagement.dto.SessionBean;
import com.feemanagement.helper.AllFeeButtonEditor;
import com.feemanagement.helper.AllFeeButtonRenderer;
import com.feemanagement.helper.SessionClass;
import com.feemanagement.services.DisplayAllFeeDetalServiceImpl;
import com.feemanagement.services.FeeDetailServiceImpl;
import com.feemanagement.util.MyLogger;
import com.feemanagement.util.PathConstants;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.BorderFactory;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author 786
 */
public final class DisplayAllFeeDetailFrontEnd extends javax.swing.JPanel {

    org.apache.log4j.Logger logger = MyLogger.getLogger(DisplayAllFeeDetailFrontEnd.class);
    private static Response response;
    int count = 1;
    ImageIcon icon = null;
    ImageIcon icon2 = null;
    private static List<JTable> tables = null;
    private static List<JLabel> labels = null;
    private static List<JButton> printButtons = null;
    private int noOfParticular = 0;
    private int totalAmount = 0;
    private ActionListener listener;

    public JTable table = null;
    public JLabel label = null;
    public JButton printButton = null;
    JPanel addFieldPanel12 = null;
    JScrollPane scrollPane = null;
    JScrollPane mainScrollPane = null;
    static DisplayAllFeeDetailFrontEnd dp = null;

    static {
        tables = new ArrayList<>();
        labels = new ArrayList<>();
        printButtons = new ArrayList<>();
    }

    /**
     * Creates new form DisplayAllFeeDetailFrontEnd
     */
    public DisplayAllFeeDetailFrontEnd() {
        initComponents();
        setValueToStuSessionCombo();
    }

    /**
     * This method set a Session value to the sessionComboBox
     */
    public void setValueToStuSessionCombo() {
        SessionBean[] sessionBeans = SessionClass.getSessionValue();
        for (SessionBean sessionBean : sessionBeans) {
            sessionComboBox.addItem(sessionBean);
        }
    }

    /**
     * This method create the new table and add to the panel
     */
    @SuppressWarnings("empty-statement")
    public void createNewTable() {
        int i = 0;
        tables.clear();
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.validate();

        Object[] columnNames = {"S.No ", "Particulars", "Amount(Rs.)", "id", "", ""};
        icon = new javax.swing.ImageIcon(getClass().getResource(PathConstants.UPDATE_IMAGE_PATH), "update");
        icon2 = new javax.swing.ImageIcon(getClass().getResource(PathConstants.DELETE_IMAGE_PATH), "delete");

        response = new DisplayAllFeeDetalServiceImpl().displayAllFeeDetail(((SessionBean) sessionComboBox.getSelectedItem()).getSessionId());

        if (response.getStatus() == 1) {
            ////////////////////////////////////////////////////////////////////////////////////////
            Map<GroupAndClass, List<FeeDetails>> classDetailMap = (Map<GroupAndClass, List<FeeDetails>>) response.getData();
            statusMessage.setText("Display Result for Class : All, & Session : " + ((SessionBean) sessionComboBox.getSelectedItem()).getStudentSession() + ", No. of Class Found : " + classDetailMap.size());
            Set<Map.Entry<GroupAndClass, List<FeeDetails>>> entrySet = classDetailMap.entrySet();
            Iterator<Map.Entry<GroupAndClass, List<FeeDetails>>> itr = entrySet.iterator();

            while (itr.hasNext()) {
                Map.Entry<GroupAndClass, List<FeeDetails>> element = itr.next();

                // ADDING DATA AND COLUMN HEADER TO TABLE
                table = new JTable(convertArrayListToArray(element.getValue()), columnNames) {
                    // creting a custom render, change the background of the table
                    @Override
                    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                        Component returnComp = super.prepareRenderer(renderer, row, column);
                        if (returnComp instanceof JComponent) {
                            ((JComponent) returnComp).setOpaque(false);
                        }
                        return returnComp;
                    }
                };

                // Creating Label and Button
                label = new JLabel("No. of Particular :" + noOfParticular + "        Total Rs.:" + totalAmount);
                printButton = new JButton("Print");
                printButton.addActionListener(setActionListener());

                table.setName(String.valueOf(i));
                label.setName(element.getKey().getClassName() + ":" + totalAmount);
                printButton.setActionCommand(String.valueOf(i));

                // SET CUSTOM RENDOR TO ACTION COLUMN
                table.getColumnModel().getColumn(4).setCellRenderer(new AllFeeButtonRenderer());
                table.getColumnModel().getColumn(5).setCellRenderer(new AllFeeButtonRenderer());

                //SET CUSTOM EDITOR TO ACTION COLUMN
                table.getColumnModel().getColumn(4).setCellEditor(new AllFeeButtonEditor(new JCheckBox()));
                table.getColumnModel().getColumn(5).setCellEditor(new AllFeeButtonEditor(new JCheckBox()));

                //Calling the tableAllignment method which allign the table and set property of table
                tableAllignment();

                tables.add(table);
                labels.add(label);
                printButtons.add(printButton);

                label.setFont(new Font("SansSerif", Font.ITALIC, 14));
                label.setBackground(Color.red);
                GridBagLayout gridbag = new GridBagLayout();

                GridBagConstraints c = new GridBagConstraints();
                addFieldPanel12 = new JPanel(gridbag);

                c.gridwidth = GridBagConstraints.REMAINDER;
                gridbag.setConstraints(printButton, c);
                addFieldPanel12.add(printButton);

                gridbag.setConstraints(label, c);
                addFieldPanel12.add(label);

                c.gridx = 0;
                c.gridy = 0;
                gridbag.setConstraints(table, c);
                addFieldPanel12.add(table);

                // Creating a border and a font type for a EtchedBorder of the panel
                Font font = new Font("Times New Roman", Font.ITALIC, 16);
                String titleMesage = " Group Name : " + element.getKey().getGroupName() + "  Class : " + element.getKey().getClassName();
                TitledBorder titledBorder = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), titleMesage);
                titledBorder.setTitleFont(font);
                titledBorder.setTitleColor(new Color(123, 6, 6));

                addFieldPanel12.setBorder(titledBorder);
                addFieldPanel12.setOpaque(false);
                scrollPane = new JScrollPane(table);
                scrollPane.setMinimumSize(new Dimension(600, 23));

                addFieldPanel12.add(scrollPane);
                setVisible(true);
                mainPanel.add(addFieldPanel12);
                mainPanel.setLayout(new GridLayout(0, 2, 25, 25));

                scrollPane.setOpaque(false);
                scrollPane.getViewport().setOpaque(false);
                scrollPane.setViewportBorder(createEmptyBorder());
                scrollPane.setBorder(createEmptyBorder());

                mainPanel.repaint();
                mainPanel.validate();
                count++;
                i++;
            } // response status check finish
            dp = this;
        } else {
            statusMessage.setText("No class found for session : " + ((SessionBean) sessionComboBox.getSelectedItem()).getStudentSession());
            statusMessage.setForeground(Color.blue);
        }
    }

    /**
     * This method convert a arrayList To 2D array
     *
     * @param feeList
     * @return Object[][]
     */
    private Object[][] convertArrayListToArray(List<FeeDetails> feeList) {
        Object[][] newArrayContent = new Object[feeList.size()][6];
        totalAmount = 0;
        noOfParticular = 0;
        for (int x = 0; x < feeList.size(); x++) {
            noOfParticular++;
            FeeDetails currentObject = (FeeDetails) feeList.get(x);
            newArrayContent[x][0] = noOfParticular;
            newArrayContent[x][1] = currentObject.getParticular();
            newArrayContent[x][2] = currentObject.getParticularAmount();
            newArrayContent[x][3] = currentObject.getFeeid();
            newArrayContent[x][4] = icon;
            newArrayContent[x][5] = icon2;
            totalAmount = totalAmount + currentObject.getParticularAmount();
        }
        return newArrayContent;
    }

    /**
     * This method get the fee id of selected column from table
     *
     * @param tableNo
     */
    public static void deleteFeeFromTable(int tableNo) {
        response = new FeeDetailServiceImpl().deleteFeeDetail((int) tables.get(tableNo).getValueAt(tables.get(tableNo).getSelectedRow(), 3));
        if (response.getStatus() == 1) {
            JOptionPane.showMessageDialog(dp, response.getMessage() + ". To make effect please click on refresh button", "Response", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(dp, response.getMessage(), "Response", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * This message get for update
     *
     * @param tableNo
     */
    public static void updateFeeDetail(int tableNo) {
        JTable tb = tables.get(tableNo);
        FeeDetails feeDetails = new FeeDetails();
        feeDetails.setParticular((String) tb.getValueAt(tables.get(tableNo).getSelectedRow(), 1));
        feeDetails.setParticularAmount(Integer.parseInt(String.valueOf(tb.getValueAt(tables.get(tableNo).getSelectedRow(), 2))));
        feeDetails.setFeeid((int) tb.getValueAt(tables.get(tableNo).getSelectedRow(), 3));

        response = new FeeDetailServiceImpl().updateFeeDetail(feeDetails);
        if (response.getStatus() == 1) {
            JOptionPane.showMessageDialog(dp, response.getMessage() + ". To make effect please click on refresh button", "Response", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(dp, response.getMessage(), "Response", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method made the allignment of the JTable
     */
    public void tableAllignment() {
        table.setRowHeight(18);
        table.setRowMargin(2);
        table.setDragEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setBackground(new Color(123, 6, 6));
        table.getTableHeader().setForeground(Color.white);
        table.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 14));
        table.setShowGrid(false);

        table.setPreferredScrollableViewportSize(new Dimension(400, 150));
        table.setFillsViewportHeight(true);

        // set the width of the column of
        table.getColumnModel().getColumn(0).setPreferredWidth(45);
        table.getColumnModel().getColumn(1).setPreferredWidth(145);
        table.getColumnModel().getColumn(2).setPreferredWidth(145);
        table.getColumnModel().getColumn(3).setMaxWidth(0);
        table.getColumnModel().getColumn(3).setMinWidth(0);
        table.getColumnModel().getColumn(3).setPreferredWidth(0);
        table.getColumnModel().getColumn(4).setPreferredWidth(6);
        table.getColumnModel().getColumn(5).setPreferredWidth(6);
        table.setRowSelectionAllowed(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment((int) CENTER_ALIGNMENT);

        for (int i = 0; i < table.getColumnCount() - 2; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        table.setRowSelectionAllowed(true);
        table.setShowGrid(false);
        table.setOpaque(false);
    }

    /**
     * Assign a action listener to the Print Button
     *
     * @return
     */
    public ActionListener setActionListener() {
        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String[] labelArray = (labels.get(Integer.parseInt(ae.getActionCommand())).getName()).split(":");
                MessageFormat header = new MessageFormat("Class:" + labelArray[0] + " / Total Rs." + labelArray[1] + " / " + "01/03/2014 to 28/02/2015");
                MessageFormat footer = new MessageFormat("Page{0,number,integer}");

                System.out.println("  array value " + labelArray[0] + labelArray[1]);

                try {
                    (tables.get(Integer.parseInt(ae.getActionCommand()))).print(JTable.PrintMode.NORMAL, header, footer);
                } catch (NumberFormatException | PrinterException e) {
                    e.printStackTrace();
                }

            }
        };
        return listener;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        topMostPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        mainPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        sessionComboBox = new javax.swing.JComboBox();
        go = new javax.swing.JButton();
        refresh = new javax.swing.JButton();
        printAll = new javax.swing.JButton();
        pdfReport = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        statusMessage = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));

        topMostPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        topMostPanel.setOpaque(false);

        jScrollPane1.setOpaque(false);

        mainPanel.setBackground(new java.awt.Color(255, 255, 0));
        mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fee Details of all classes", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Algerian", 2, 18), new java.awt.Color(123, 6, 6))); // NOI18N
        mainPanel.setAutoscrolls(true);
        mainPanel.setOpaque(false);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1001, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 603, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(mainPanel);

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jPanel1.setOpaque(false);

        go.setText("Go");
        go.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goActionPerformed(evt);
            }
        });

        refresh.setText("Refresh");
        refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshActionPerformed(evt);
            }
        });

        printAll.setText("Print All Indivisual Fee Detail");
        printAll.setEnabled(false);
        printAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printAllActionPerformed(evt);
            }
        });

        pdfReport.setText("PDF Report");
        pdfReport.setEnabled(false);

        jButton2.setText("Close");

        statusMessage.setFont(new java.awt.Font("Century", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel1.setText("Status  :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sessionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(go)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refresh)
                .addGap(18, 18, 18)
                .addComponent(printAll)
                .addGap(18, 18, 18)
                .addComponent(pdfReport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(statusMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(125, 125, 125))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(statusMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(pdfReport)
                    .addComponent(printAll)
                    .addComponent(refresh)
                    .addComponent(go)
                    .addComponent(sessionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        javax.swing.GroupLayout topMostPanelLayout = new javax.swing.GroupLayout(topMostPanel);
        topMostPanel.setLayout(topMostPanelLayout);
        topMostPanelLayout.setHorizontalGroup(
            topMostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topMostPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topMostPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 945, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        topMostPanelLayout.setVerticalGroup(
            topMostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topMostPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane1 .setOpaque(false);
        jScrollPane1 .getViewport().setOpaque(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topMostPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topMostPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void goActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goActionPerformed
        createNewTable();
    }//GEN-LAST:event_goActionPerformed

    private void refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshActionPerformed
        createNewTable();
    }//GEN-LAST:event_refreshActionPerformed

    private void printAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printAllActionPerformed

        MessageFormat header = null;
        MessageFormat footer = null;

        for (int i = 0; i < tables.size() - 1; i++) {
            String[] labelArray = (labels.get(i)).getName().split(":");
            header = new MessageFormat("Class:" + labelArray[0] + " / Total Rs." + labelArray[1] + " / " + "01/03/2014 to 28/02/2015");
            footer = new MessageFormat("Page{0,number,integer}");
            try {
                (tables.get(i)).print(JTable.PrintMode.NORMAL, header, footer);
            } catch (NumberFormatException | PrinterException ex) {
                logger.error(ex.getMessage());
            }
        }
    }//GEN-LAST:event_printAllActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton go;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton pdfReport;
    private javax.swing.JButton printAll;
    private javax.swing.JButton refresh;
    private javax.swing.JComboBox sessionComboBox;
    private javax.swing.JLabel statusMessage;
    private javax.swing.JPanel topMostPanel;
    // End of variables declaration//GEN-END:variables
}
