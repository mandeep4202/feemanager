/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.frontend;

import com.feemanagement.dao.FineDAOImpl;
import com.feemanagement.dto.FeeDetails;
import com.feemanagement.dto.FineDetail;
import com.feemanagement.dto.PayFee;
import com.feemanagement.dto.Response;
import com.feemanagement.dto.SessionBean;
import com.feemanagement.dto.Student;
import com.feemanagement.helper.CallingReport;
import com.feemanagement.helper.PrintButtonEditor;
import com.feemanagement.helper.PrintButtonRender;
import com.feemanagement.helper.SessionClass;
import com.feemanagement.services.PrintFeeReciptServiceImpl;
import com.feemanagement.util.PathConstants;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Mandeep Singh
 */
public final class PrintFeeReciptFrontEnd extends javax.swing.JPanel {

    private Response response = null;
    private final String pattern = "^[0-9]*$";
    private final Pattern r = Pattern.compile(pattern);
    private static Student student = null;
    private int noOfParticular;
    private final ImageIcon icon;
    private static JTable table = null;
    private JScrollPane scrollPane;
    private int totalAmount;
    private static int fineVar;
    private static int totalFeeVar;

    /**
     * Creates new form PrintFeeReciptFrontEnd12
     */
    public PrintFeeReciptFrontEnd() {
        this.icon = new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/b_edit.png"), "update");
        initComponents();
        setValueToStuSessionCombo();
       
    }

    /**
     * This method set a Session value to the sessionComboBox
     */
    public void setValueToStuSessionCombo() {
        SessionBean[] sessionBeans = SessionClass.getSessionValue();
        for (SessionBean sessionBean : sessionBeans) {
            stuSession.addItem(sessionBean);
        }
    }

    /**
     * Validate student Registration field
     *
     * @return
     */
    public boolean validateRegistrationField() {
        boolean result = false;
        if (regdNoField.getText() != null && regdNoField.getText().trim().length() > 0) {
            if (r.matcher((regdNoField.getText())).matches()) {
                result = true;
            } else {
                JOptionPane.showMessageDialog(this, "Registration no. must be number", "Response", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Must supply a registration no", "Response", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    /**
     * This method get all the information related to the student
     */
    public void getStudentAndFeeDetail() {
        if (validateRegistrationField()) {
            response = new PrintFeeReciptServiceImpl().getStudentAndInstallmentDetail(Integer.parseInt(regdNoField.getText().trim()), ((SessionBean) stuSession.getSelectedItem()).getSessionId());
            if (response != null) {
                if (response.getStatus() == 1) {
                    student = (Student) response.getData();
                    setValueToStudentInfoPanel(student);
                    set_ValueToInstallmentTable(student);
                } else {
                    status.setText(response.getMessage());
                    statusBar.setText("");
                    reset_StudentPanel();
                    mainPanel.removeAll();
                    mainPanel.repaint();
                    mainPanel.validate();
                }
            }
        }
    }

    /**
     * This method is used to display the fee detail on the feeAddSucessfullTab
     *
     * @param student
     */
    public void setValueToStudentInfoPanel(Student student) {
        if ("male".equals(student.getGender())) {
            studentName.setText("Mr. " + student.getFirstName() + " " + student.getLastName());
        } else {
            studentName.setText("Miss. " + student.getFirstName() + " " + student.getLastName());
        }
        studentClass.setText(student.getGroupAndClass().getClassName());
        studentFatherName.setText("Mr. " + student.getFatherFirstName() + " " + student.getFatherLastName());
        studentRegdNo.setText(String.valueOf(student.getRegdNo()));
        studentStatus.setText(student.getIsActive() == 1 ? "Active" : "Inactive");
        studentStatus.setBackground(new Color(255, 255, 204));
        studentInfoPanel.repaint();
        studentInfoPanel.validate();
    }

    /**
     * Reset the student info panel
     */
    private void reset_StudentPanel() {
        studentName.setText(" ");
        studentClass.setText(" ");
        studentFatherName.setText(" ");
        studentRegdNo.setText(" ");
        studentStatus.setText(" ");
        studentInfoPanel.repaint();
        studentInfoPanel.validate();
    }

    /**
     * Set value to student pay fee detail table
     *
     * @param student
     */
    public void set_ValueToInstallmentTable(Student student) {

        int i = 0;
        totalAmount = 0;
        totalFeeVar = 0;
        mainPanel.removeAll();
        mainPanel.repaint();
        mainPanel.invalidate();
        if (student != null) {
            if (response.getStatus() == 1) {
                status.setText("No. of installment given : " + student.getPayFees().size());
                for (FeeDetails fee : student.getFeeDetailList()) {
                    totalAmount = totalAmount + fee.getParticularAmount();
                }
                Response responseTemp = new FineDAOImpl().getFineDetail(Integer.parseInt(regdNoField.getText().trim()), ((SessionBean) stuSession.getSelectedItem()).getSessionId());
                if (responseTemp.getStatus() == 1) {
                    FineDetail fineDetail = (FineDetail) responseTemp.getData();
                    if (fineDetail.getFineId() != 0) {
                        fineVar = fineDetail.getFineAmount();
                        statusBar.setText("Fee : Rs. " + totalAmount + ", Individual Fine : Rs.  " + fineVar + ",  Total to pay : Rs.  " + (totalAmount + fineVar));
                        totalFeeVar = totalAmount;
                        totalAmount = totalAmount + fineVar;
                    } else {
                        JOptionPane.showMessageDialog(this, responseTemp.getMessage(), "Response", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, responseTemp.getMessage(), "Response", JOptionPane.ERROR_MESSAGE);
                }
                Object[] columnNames = {"S.No ", "To Be Pay", "Currently pay ", "payment mode", "Remaining Fee", "Date", "Time", "Action", "feePayId"};
                table = new JTable(convertArrayListToArray(student.getPayFees()), columnNames) {
                    // creting a custom render, change the background of the table
                    @Override
                    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                        Component returnComp = super.prepareRenderer(renderer, row, column);
                        Color alternateColor = new Color(252, 242, 206);
                        Color whiteColor = Color.WHITE;
                        if (!returnComp.getBackground().equals(getSelectionBackground())) {
                            Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
                            returnComp.setBackground(bg);
                            bg = null;
                        }
                        return returnComp;

                    }
                };

                // SET CUSTOM RENDOR TO ACTION COLUMN
                table.getColumnModel().getColumn(7).setCellRenderer(new PrintButtonRender());

                //SET CUSTOM EDITOR TO ACTION COLUMN
                table.getColumnModel().getColumn(7).setCellEditor(new PrintButtonEditor(new JCheckBox()));
                //Calling the tableAlignment method which allign the table and set property of table
                tableAlignment();

                scrollPane = new JScrollPane(table);
                scrollPane.setMinimumSize(new Dimension(600, 23));
                scrollPane.setOpaque(false);
                scrollPane.getViewport().setOpaque(false);
                scrollPane.setViewportBorder(createEmptyBorder());
                scrollPane.setBorder(createEmptyBorder());

                mainPanel.add(scrollPane);
                mainPanel.setLayout(new GridLayout(0, 1, 25, 25));

                mainPanel.repaint();
                mainPanel.validate();
            } else {
                status.setText("No. of installment given : " + student.getPayFees().size());
            }
        }
    }

    /**
     * This method made the alignment of the JTable
     */
    public void tableAlignment() {
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
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(70);
        table.getColumnModel().getColumn(2).setPreferredWidth(85);
        table.getColumnModel().getColumn(3).setPreferredWidth(85);

        table.getColumnModel().getColumn(4).setPreferredWidth(80);
        table.getColumnModel().getColumn(5).setPreferredWidth(50);
        table.getColumnModel().getColumn(6).setPreferredWidth(50);
        table.getColumnModel().getColumn(7).setPreferredWidth(10);

        table.getColumnModel().getColumn(8).setMaxWidth(0);
        table.getColumnModel().getColumn(8).setMinWidth(0);
        table.getColumnModel().getColumn(8).setPreferredWidth(0);

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
     * This method convert a arrayList To 2D array
     *
     * @param payFee
     * @return Object[][]
     */
    private Object[][] convertArrayListToArray(List<PayFee> payFee) {
        Object[][] newArrayContent = new Object[payFee.size()][9];
        // totalAmount = 0;
        noOfParticular = 0;
        int reduceVar = 0;

        for (int x = 0; x < payFee.size(); x++) {
            noOfParticular++;
            totalAmount = totalAmount - reduceVar;
            PayFee currentObject = (PayFee) payFee.get(x);
            newArrayContent[x][0] = noOfParticular;
            newArrayContent[x][1] = "Rs. " + totalAmount;
            newArrayContent[x][2] = "Rs. " + currentObject.getPaymentAmount();
            newArrayContent[x][3] = currentObject.getVoucherType().getPaymentMode();
            newArrayContent[x][4] = "Rs. " + currentObject.getRemainingFee();
            newArrayContent[x][5] = currentObject.getPayDate();
            newArrayContent[x][6] = currentObject.getPayTime();
            newArrayContent[x][7] = icon;
            newArrayContent[x][8] = currentObject.getFeePayId();
            reduceVar = currentObject.getPaymentAmount();
        }
        return newArrayContent;
    }

    /**
     * Calling a jasper report, And supply a value to jasper report as a Map
     * object
     */
    public static void printingFeeReceipt() {
        HashMap parameterMap = new HashMap();
        parameterMap.put("headerImage", new PathConstants().getHeaderImage() );
        parameterMap.put("studentName", student.getFirstName() + " " + student.getLastName());//sending the report title as a parameter.
        parameterMap.put("fatherName", "Mr. " + student.getFatherFirstName() + " " + student.getFatherLastName());
        parameterMap.put("studentClass", student.getGroupAndClass().getClassName());
        parameterMap.put("studentStatus", student.getIsActive() == 1 ? "Active" : " Inactive");
        parameterMap.put("regdNo", String.valueOf(student.getRegdNo()));
        
       
        parameterMap.put("receiptNo", table.getValueAt(table.getSelectedRow(), 8).toString());
        parameterMap.put("toBePay1", table.getValueAt(table.getSelectedRow(), 1) + " /-");
        parameterMap.put("currentPay1", table.getValueAt(table.getSelectedRow(), 2) + " /-");
        parameterMap.put("remainingFee1", table.getValueAt(table.getSelectedRow(), 4) + " /-");
        parameterMap.put("date1", table.getValueAt(table.getSelectedRow(), 5));
        parameterMap.put("time1", table.getValueAt(table.getSelectedRow(), 6));
        parameterMap.put("fine1", "Rs. " + fineVar + " /-");
        parameterMap.put("fee1", "Rs. " + totalFeeVar + " /-");
        parameterMap.put("total1", "Rs. " + (fineVar + totalFeeVar) + " /-");

        String[] str = ((String) table.getValueAt(table.getSelectedRow(), 1)).split(" ");
        String[] str2 = ((String) table.getValueAt(table.getSelectedRow(), 2)).split(" ");
        int paid1 = ((fineVar + totalFeeVar) - (Integer.parseInt(str[1]))) + (Integer.parseInt(str2[1]));
        parameterMap.put("paid1", "Rs. " + paid1 + " /-");
        parameterMap.put("studentSession", student.getSessionBean().getStudentSession());
        parameterMap.put("paymentMode1", table.getValueAt(table.getSelectedRow(), 3));
               
        //CallingReport.callingReports(parameterMap, PathConstants.PRINT_RECEIPT_REPORT_PATH);
        try{
        CallingReport.callingReports(parameterMap,PathConstants.PRINT_RECEIPT_REPORT_PATH);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        regdNoField = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        stuSession = new javax.swing.JComboBox();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        go = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        studentInfoPanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        studentFatherName = new javax.swing.JLabel();
        studentRegdNo = new javax.swing.JLabel();
        studentClass = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        studentName = new javax.swing.JLabel();
        studentLastDate = new javax.swing.JLabel();
        studentStatus = new javax.swing.JLabel();
        status = new javax.swing.JLabel();
        statusBar = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jToolBar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jToolBar1.setRollover(true);
        jToolBar1.setOpaque(false);

        regdNoField.setColumns(20);
        jToolBar1.add(regdNoField);
        jToolBar1.add(jSeparator1);

        jToolBar1.add(stuSession);
        jToolBar1.add(jSeparator2);

        go.setText("   Go   ");
        go.setFocusable(false);
        go.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        go.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        go.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                goActionPerformed(evt);
            }
        });
        jToolBar1.add(go);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Student And Paid Fee Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Algerian", 2, 18))); // NOI18N
        jPanel1.setOpaque(false);

        studentInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Student Information", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century", 0, 14), new java.awt.Color(123, 6, 6))); // NOI18N
        studentInfoPanel.setOpaque(false);

        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 113, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 124, Short.MAX_VALUE)
        );

        jLabel2.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(123, 6, 6));
        jLabel2.setText("Student Name");

        jLabel3.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(123, 6, 6));
        jLabel3.setText("Registration No.");

        jLabel5.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(123, 6, 6));
        jLabel5.setText("Class ");

        studentFatherName.setBackground(new java.awt.Color(255, 255, 204));
        studentFatherName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        studentRegdNo.setBackground(new java.awt.Color(255, 255, 204));
        studentRegdNo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        studentClass.setBackground(new java.awt.Color(255, 255, 204));
        studentClass.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(123, 6, 6));
        jLabel9.setText("Last Paid Date");

        jLabel10.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(123, 6, 6));
        jLabel10.setText("Father/Guardian Name ");

        jLabel11.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(123, 6, 6));
        jLabel11.setText("Status");

        studentName.setBackground(new java.awt.Color(255, 255, 204));
        studentName.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        studentLastDate.setBackground(new java.awt.Color(255, 255, 204));
        studentLastDate.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        studentStatus.setBackground(new java.awt.Color(255, 255, 204));
        studentStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout studentInfoPanelLayout = new javax.swing.GroupLayout(studentInfoPanel);
        studentInfoPanel.setLayout(studentInfoPanelLayout);
        studentInfoPanelLayout.setHorizontalGroup(
            studentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(studentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11))
                .addGap(10, 10, 10)
                .addGroup(studentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER, false)
                    .addComponent(studentName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(studentStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studentLastDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(studentFatherName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(studentClass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(studentRegdNo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 188, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        studentInfoPanelLayout.setVerticalGroup(
            studentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentInfoPanelLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(studentInfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(studentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(studentInfoPanelLayout.createSequentialGroup()
                        .addGap(0, 5, Short.MAX_VALUE)
                        .addComponent(studentName, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(studentRegdNo, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(studentClass, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(studentFatherName, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(studentLastDate, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(studentStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, studentInfoPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11))))
        );

        status.setBackground(new java.awt.Color(255, 255, 204));
        status.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        statusBar.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        mainPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        mainPanel.setOpaque(false);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 166, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusBar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(studentInfoPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(status, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(studentInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusBar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 737, Short.MAX_VALUE)
                    .addGap(12, 12, 12)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(504, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void goActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_goActionPerformed
        getStudentAndFeeDetail();        // TODO add your handling code here:
    }//GEN-LAST:event_goActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton go;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField regdNoField;
    private javax.swing.JLabel status;
    private javax.swing.JLabel statusBar;
    private javax.swing.JComboBox stuSession;
    private javax.swing.JLabel studentClass;
    private javax.swing.JLabel studentFatherName;
    private javax.swing.JPanel studentInfoPanel;
    private javax.swing.JLabel studentLastDate;
    private javax.swing.JLabel studentName;
    private javax.swing.JLabel studentRegdNo;
    private javax.swing.JLabel studentStatus;
    // End of variables declaration//GEN-END:variables
}
