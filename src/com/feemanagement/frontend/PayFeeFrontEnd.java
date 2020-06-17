/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.frontend;

import com.feemanagement.dao.FineDAOImpl;
import com.feemanagement.dto.FeeDetails;
import com.feemanagement.dto.FineDetail;
import com.feemanagement.dto.PayFee;
import com.feemanagement.dto.PaymentDetails;
import com.feemanagement.dto.Response;
import com.feemanagement.dto.SessionBean;
import com.feemanagement.dto.Student;
import com.feemanagement.dto.VoucherType;
import com.feemanagement.helper.SessionClass;
import com.feemanagement.services.FineServiceImpl;
import com.feemanagement.services.PayFeeServiceImpl;
import com.feemanagement.util.MessageConstants;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author 786
 */
public final class PayFeeFrontEnd extends javax.swing.JPanel {

    Response response = null;
    private final String pattern = "^[0-9]*$";
    private final Pattern r = Pattern.compile(pattern);
    private int totalFee = 0;
    private int totalPaidAmount = 0;
    private List<Integer> newCalulatedList = null;
    private int count = 0;
    private int rowCount = 0;
    static int paymentModeId;
    private Student student = null;
    static PaymentDetails paymentDetails123 = new PaymentDetails();
    private int individualFine;

    /**
     * Creates new form PayFeeFrontEnd12
     */
    public PayFeeFrontEnd() {
        initComponents();
        Color newColor = new Color(51, 0, 0);  // or whatever color you want
        UIManager.put("ComboBox.disabledForeground", newColor);
        setDefault_FeeDetailsTable();
        setDefault_CurrentPaymentDetailTable();
        setValueToStuSessionCombo();
        addVoucherTypeToComboBox();
        addLedgerTypeToComboBox();
        updateFineButton.setEnabled(false);
        fineIdTextField.setVisible(false);
        setValueToStuSessionCombo();

    }

    /**
     * This method set the properties of feeDetailsTable Table of PayFeeFrontEnd
     * window to custom value at startup like column name,column width
     */
    private void setDefault_FeeDetailsTable() {
        feeDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "S.No ", "Particulars", "Amount(Rs.)"
                }
        )
        );

        feeDetailsTable.getTableHeader().setBackground(new Color(123, 6, 6));
        feeDetailsTable.getTableHeader().setForeground(Color.white);
        feeDetailsTable.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 14));

        feeDetailsTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        feeDetailsTable.getColumnModel().getColumn(1).setPreferredWidth(55);
        feeDetailsTable.getColumnModel().getColumn(2).setPreferredWidth(45);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment((int) CENTER_ALIGNMENT);

        for (int i = 0; i < feeDetailsTable.getColumnCount(); i++) {
            feeDetailsTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        feeDetailsTable.setRowSelectionAllowed(true);
    }

    /**
     * This method set the properties of currentPaymentDetail Table of
     * PayFeeFrontEnd window to custom value at startup like column name,column
     * width
     */
    private void setDefault_CurrentPaymentDetailTable() {
        currentPaymentDetailTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "S.No ", "Particulars", "Remaining Fee", "Amount(Rs.)", "feeid"
                }
        )
        );

        currentPaymentDetailTable.getTableHeader().setBackground(new Color(123, 6, 6));
        currentPaymentDetailTable.getTableHeader().setForeground(Color.white);
        currentPaymentDetailTable.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 14));

        currentPaymentDetailTable.getColumnModel().getColumn(0).setPreferredWidth(10);

        currentPaymentDetailTable.getColumnModel().getColumn(1).setPreferredWidth(55);
        currentPaymentDetailTable.getColumnModel().getColumn(2).setPreferredWidth(45);
        currentPaymentDetailTable.getColumnModel().getColumn(3).setPreferredWidth(45);

        currentPaymentDetailTable.getColumnModel().getColumn(4).setMaxWidth(0);
        currentPaymentDetailTable.getColumnModel().getColumn(4).setMinWidth(0);
        currentPaymentDetailTable.getColumnModel().getColumn(4).setPreferredWidth(0);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment((int) CENTER_ALIGNMENT);

        for (int i = 0; i < currentPaymentDetailTable.getColumnCount(); i++) {
            currentPaymentDetailTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        currentPaymentDetailTable.setRowSelectionAllowed(true);
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
            response = new PayFeeServiceImpl().getStudentAndFeeDetail(Integer.parseInt(regdNoField.getText().trim()), ((SessionBean) stuSession.getSelectedItem()).getSessionId());
            if (response != null) {
                if (response.getStatus() == 1) {
                    student = (Student) response.getData();
                    setValueToStudentInfoPanel(student);
                    resetPayfeeFields();
                    setFeeDetailsTable(student.getFeeDetailList());
                    setCurrentPaymentDetailTable(student.getFeeDetailList());
                    calculatePaidByPayee(student.getPayFees());
                    calculateFeePaidByPayee(student.getFeeDetailList());
                    currentPayment.setEditable(true);

                } else {
                    topBarStatus.setText(response.getMessage());
                    resetPayfeeFields();
                    reset_StudentPanel();
                    setDefault_FeeDetailsTable();
                    setDefault_CurrentPaymentDetailTable();
                    classForParameter.setText("");
                    classForParameter.setText("No fee detail found because student does not exist");
                    recordFound.setText("");
                    currentPaymentStatus.setText("");
                    currentPayment.setEditable(false);

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
        studentStatus.setText(student.getIsActive() == 1 ? "Active" : "Not Active");
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
     * This method is used to display the fee detail on the feeAddSucessfullTab
     *
     * @param feeDetailList
     */
    public void setFeeDetailsTable(List<FeeDetails> feeDetailList) {
        setDefault_FeeDetailsTable();
        if (feeDetailList.size() > 0) {
            int totalFee = 0;
            int i = 0;
            DefaultTableModel feeAddSucessfullTableVariable = (DefaultTableModel) feeDetailsTable.getModel();
            for (FeeDetails fee : feeDetailList) {
                i++;
                totalFee = fee.getParticularAmount() + totalFee;
                feeAddSucessfullTableVariable.addRow(
                        new Object[]{
                            i, fee.getParticular(), fee.getParticularAmount()
                        }
                );
            }

            classForParameter.setText(" Fee Detail for Class : " + feeDetailList.get(0).getStudentClass() + " and Session : " + feeDetailList.get(0).getStudentSession());
            recordFound.setText("  No. of record found  " + i + "   Total Fee = Rs. " + totalFee);
        } else {
            classForParameter.setText("Fee Detail not available");
            classForParameter.setForeground(Color.red);
            recordFound.setText("");
        }
    }

    /**
     * This method set the values related to current payment to
     * setCurrentPaymentDetailTable
     *
     * @param feeDetailList
     */
    public void setCurrentPaymentDetailTable(List<FeeDetails> feeDetailList) {
        setDefault_CurrentPaymentDetailTable();
        totalFee = 0;
        individualFine = 0;
        // adding fine to the table and total amount to pay
        response = new FineDAOImpl().getFineDetail(Integer.parseInt(regdNoField.getText().trim()), ((SessionBean) stuSession.getSelectedItem()).getSessionId());
        if (response.getStatus() == 1) {
            FineDetail fineDetail = (FineDetail) response.getData();

            if ( 0 != fineDetail.getFineId()) {
                FeeDetails fd = new FeeDetails();
                fineIdTextField.setText(String.valueOf(fineDetail.getFineId()));

                fd.setFeeid(000);
                System.out.println(" Testing :"+feeDetailList.get(1).getStudentClass());
                fd.setStudentClass(feeDetailList.get(1).getStudentClass());
                fd.setParticular(MessageConstants.FINE_PARTICULAR_NAME);
                fd.setParticularAmount(fineDetail.getFineAmount());
                fd.setStudentSession(fineDetail.getStudentSession());
                feeDetailList.add(fd);
                fineTextField.setText(String.valueOf(fineDetail.getFineAmount()));
                fineTextField.setEditable(true);
                individualFine = fineDetail.getFineAmount();
            } else {
                fineTextField.setEditable(false);
            }
        }
        if (feeDetailList.size() > 0) {
            int i = 0;
            DefaultTableModel currentPaymentDetailTableVariable = (DefaultTableModel) currentPaymentDetailTable.getModel();

            for (FeeDetails fee : feeDetailList) {
                i++;
                totalFee = fee.getParticularAmount() + totalFee;
                currentPaymentDetailTableVariable.addRow(
                        new Object[]{
                            i, fee.getParticular(), fee.getParticularAmount()
                        }
                );

            }
        } else {
            currentPaymentStatus.setText("Fee Detail not available");
            currentPaymentStatus.setForeground(Color.red);
        }

        count = currentPaymentDetailTable.getRowCount();
    }

    /**
     * This method prepare a list that represent a descriptive fee paid by
     * payee,, and set to the current payment detail table,, set value inside
     * the last column of the current payment table
     *
     * @param feeDetailList
     */
    public void calculateFeePaidByPayee(List<FeeDetails> feeDetailList) {
        int result = totalPaidAmount;
        newCalulatedList = new ArrayList<>();

        int index = 0;
        for (FeeDetails feeDetails : feeDetailList) {
            {
                result = feeDetails.getParticularAmount() - result;
                if (result < 0) {
                    newCalulatedList.add(index, 0);
                    result = -(result);
                } else {
                    newCalulatedList.add(index, result);
                    result = 0;
                }
                index++;
            }
        }
        int i = 0;
        for (Integer newList : newCalulatedList) {
            currentPaymentDetailTable.setValueAt(newList, i, 2);
            i++;
            currentPaymentDetailTable.validate();
            currentPaymentDetailTable.repaint();
        }
        rowCount = currentPaymentDetailTable.getRowCount();
    }

    /**
     * method calculate and set Fee to be pay by payee and set that fee to
     * remaining fee table
     *
     * @param payFeeList
     */
    public void calculatePaidByPayee(List<PayFee> payFeeList) {
        remainingFeeField.setText("");
        for (PayFee payFee : payFeeList) {
            totalPaidAmount = payFee.getPaymentAmount();
        }
        remainingFeeField.setText(String.valueOf(totalFee - totalPaidAmount));
        currentPaymentStatus.setText("Total amount to be pay including individual fine Rs. " + String.valueOf(totalFee - totalPaidAmount));
    }

    /**
     * This method prepare a list that represent a descriptive fee paid by
     * payee,, and set to the current payment detail table
     */
    public void calculateCurretnFeePayByPayee() {
        if (currentPayment.getText() != null && currentPayment.getText().trim().length() > 0) {
            if (r.matcher((currentPayment.getText())).matches()) {
                int result = Integer.parseInt(currentPayment.getText());

                if (((Integer.parseInt(remainingFeeField.getText())) - (Integer.parseInt(currentPayment.getText()))) >= 0) {
                    remainingAfterThis.setText(String.valueOf((Integer.parseInt(remainingFeeField.getText())) - (Integer.parseInt(currentPayment.getText()))));
                    currentPayment.setForeground(Color.BLACK);
                    payFeeButton.setEnabled(true);
                } else {
                    remainingAfterThis.setText("");
                    currentPayment.setForeground(Color.red);
                    JOptionPane.showMessageDialog(this, "Payment can't be more than a remaining fee", "Response", JOptionPane.ERROR_MESSAGE);
                    payFeeButton.setEnabled(false);
                }

                List<Integer> feePaybyPayee = new ArrayList<>();
                int index = 0;
                int count = 0;

                if (newCalulatedList.size() < currentPaymentDetailTable.getRowCount()) {
                    newCalulatedList.add(newCalulatedList.size(), Integer.parseInt((String) currentPaymentDetailTable.getValueAt(currentPaymentDetailTable.getRowCount() - 1, 2)));

                }

                // for (Integer calculatedList : newCalulatedList) {
                for (int k = 0; k < newCalulatedList.size(); k++) {
                    {
                        result = newCalulatedList.get(k) - result;
                        if (result <= 0) {
                            //System.out.println("if statement working    " + result);
                            feePaybyPayee.add(index, newCalulatedList.get(k));
                            result = -(result);
                        } else if (result > 0) {
                            //System.out.println("Else statement working  "+ result);
                            if (count == 0) {
                                feePaybyPayee.add(index, newCalulatedList.get(k) - result);
                                count++;
                            } else {
                                feePaybyPayee.add(index, 0);
                                count++;
                            }

                            try {
                                result = newCalulatedList.get(k + 1) - 1;
                            } catch (IndexOutOfBoundsException ex) {
                                result = newCalulatedList.get(k) - 1;
                            }

                        }
                        index++;
                    }   // end of for loop
                }

                if (((Integer.parseInt(remainingFeeField.getText())) - (Integer.parseInt(currentPayment.getText()))) == 0) {
                    amountGivenByPayee.setEditable(true);
                } else {
                    amountGivenByPayee.setEditable(false);
                    amountGivenByPayee.setText("");
                    returnAmount.setText("");
                }

                int i = 0;
                for (Integer newList : feePaybyPayee) {

                    currentPaymentDetailTable.setValueAt(newList, i, 3);
                    i++;
                    currentPaymentDetailTable.validate();
                    currentPaymentDetailTable.repaint();
                }
            } else {
                remainingAfterThis.setText("");
                payFeeButton.setEnabled(false);
            }
        } else {
            remainingAfterThis.setText("");
            payFeeButton.setEnabled(false);
        }
    }

    /**
     * This method setValueToStuSessionComboBox
     */
    public void setValueToStuSessionCombo() {
        SessionBean[] sessionBeans = SessionClass.getSessionValue();
        for (SessionBean sessionBean : sessionBeans) {
            stuSession.addItem(sessionBean);
        }
    }

    /**
     * this method reset the field of pay fee panel fields
     */
    public void resetPayfeeFields() {
        currentPayment.setText("");
        fineTextField.setText("");
        amountGivenByPayee.setText("");
        remainingFeeField.setText("");
        remainingAfterThis.setText("");
        returnAmount.setText("");
    }

    /**
     * this method set amount to be return to the payee
     */
    public void returnAmount() {
        if (amountGivenByPayee.getText() != null && amountGivenByPayee.getText().trim().length() > 0) {
            if (r.matcher((amountGivenByPayee.getText())).matches()) {
                amountGivenByPayee.setForeground(Color.BLACK);
                int amountBypayeeVar = Integer.parseInt(amountGivenByPayee.getText());
                int currentPaymentVar = Integer.parseInt(currentPayment.getText());
                if (amountBypayeeVar >= currentPaymentVar) {

                    returnAmount.setText(String.valueOf(amountBypayeeVar - currentPaymentVar));

                } else {
                    returnAmount.setText("0");
                }
            } else {
                amountGivenByPayee.setForeground(Color.red);
                returnAmount.setText("");
            }
        } else {
            returnAmount.setText("");
        }
    }

    /**
     * This method validate and call the addingFineRowLogic method
     */
    public void addFineRow() {

        if (remainingFeeField.getText() != null && remainingFeeField.getText().trim().length() > 0) {
            if (fineTextField.getText() != null && fineTextField.getText().trim().length() > 0) {
                if (r.matcher((fineTextField.getText())).matches()) {
                    if (currentPaymentDetailTable.getRowCount() > count) {
                        for (int i = 0; i < (currentPaymentDetailTable.getRowCount() - count); i++) {
                            DefaultTableModel feeAddSucessfullTableVariable = (DefaultTableModel) currentPaymentDetailTable.getModel();
                            feeAddSucessfullTableVariable.removeRow(currentPaymentDetailTable.getRowCount() - 1);
                        }
                        addingFineRowLogic();
                        calculateCurretnFeePayByPayee();
                    } else {

                        addingFineRowLogic();
                        calculateCurretnFeePayByPayee();
                    }

                } else {
                    DefaultTableModel feeAddSucessfullTableVariable = (DefaultTableModel) currentPaymentDetailTable.getModel();
                    feeAddSucessfullTableVariable.removeRow(rowCount);
                    currentPaymentStatus.setText("Total amount to be pay  Rs. " + String.valueOf(totalFee - totalPaidAmount));
                    calculateCurretnFeePayByPayee();
                }
            } else {
                DefaultTableModel feeAddSucessfullTableVariable = (DefaultTableModel) currentPaymentDetailTable.getModel();
                feeAddSucessfullTableVariable.removeRow(rowCount);
                currentPaymentStatus.setText("Total amount to be pay  Rs. " + String.valueOf(totalFee - totalPaidAmount));
                calculateCurretnFeePayByPayee();
            }
        } else {
            currentPaymentStatus.setText("Please enter a student detail first ");
            currentPaymentStatus.setForeground(Color.red);
        }
    }

    /**
     * Add a new row i.e. fine row to the to the current payment status table
     */
    public void addingFineRowLogic() {
        int i = currentPaymentDetailTable.getRowCount() + 1;
        String particular = fineTextField.getText();
        DefaultTableModel feeAddSucessfullTableVariable = (DefaultTableModel) currentPaymentDetailTable.getModel();
        feeAddSucessfullTableVariable.addRow(
                new Object[]{
                    i, "fine", particular,}
        );
        currentPaymentStatus.setText("Total amount to be pay  Rs. " + String.valueOf((totalFee - totalPaidAmount) + Integer.parseInt(particular)));
        remainingFeeField.setText(String.valueOf((totalFee - totalPaidAmount) + Integer.parseInt(particular)));
    }

    /**
     * Add Voucher to the VoucherCombo box
     */
    public void addVoucherTypeToComboBox() {
        String[] voucherName = {"-Select-", "Receipt ( paidin )", "Credit Note ( paidin )", "Debit Note ( paidout )", "Journal ( paidout )", "Payment ( paidout )", "Purchase ( paidout )", "Purchase Order ( paidout )", "Contra ( paidout )", "Receipt Note ( paidin )", "Rejection In ( paidout )", "Rejection Out ( paidin )", "Sales ( paidin )", "Sales Order ( paidin )"};
        for (String vtype : voucherName) {
            voucherTypeCombo.addItem(vtype);
        }
    }

    /**
     * Add Ledger to the combo Box
     */
    public void addLedgerTypeToComboBox() {
        String[] ledgerName = {"-Select-", "Admission", "Examination", "Teaching material", "Salary", "Allowance", "Expense In", "Expense Out", "Consumable II", "Non Consumable", "Transport"};
        for (String ltype : ledgerName) {
            ledgerTypeCombo.addItem(ltype);
        }

    }

    /**
     * This method set the voucher entry field disable if the payment detail
     * save successfully
     */
    public void setAllFieldDisable() {
        System.out.println("this is a se all field disable method ");
        if (paymentDetails123 != null && paymentDetails123.getBankName() != null && paymentDetails123.getBankName().trim().length() > 0) {
            voucherTypeCombo.setEnabled(false);
            ledgerTypeCombo.setEnabled(false);
            paymentMode.setEnabled(false);
            getStudentRecord.setEnabled(false);
            currentPayment.setEditable(false);
            fineTextField.setEditable(false);
            payFeeButton.setEnabled(true);
        } else {
            voucherTypeCombo.setEnabled(true);
            ledgerTypeCombo.setEnabled(true);
            paymentMode.setEnabled(true);
            getStudentRecord.setEnabled(true);
            currentPayment.setEditable(true);
            fineTextField.setEditable(true);
            payFeeButton.setEnabled(false);
        }

    }

    /**
     * validate voucher type detail before opening the Payment Detail Form
     */
    public void validateAndOpenPaymentForm() {
        if (voucherTypeCombo.getSelectedItem() != null && !(((String) voucherTypeCombo.getSelectedItem()).equalsIgnoreCase("-Select-"))) {
            if (ledgerTypeCombo.getSelectedItem() != null && !(((String) ledgerTypeCombo.getSelectedItem()).equalsIgnoreCase("-Select-"))) {
                if (currentPayment.getText() != null && currentPayment.getText().trim().length() > 0 && r.matcher((currentPayment.getText())).matches()) {
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            PaymentDetailFrontEnd dialog = new PaymentDetailFrontEnd(new javax.swing.JFrame(), true);
                            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                                @Override
                                public void windowClosing(java.awt.event.WindowEvent e) {
                                    setAllFieldDisable();
                                }
                            });
                            dialog.setVisible(true);
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(this, "please enter a valid amount first", "Empty Field Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please first select voucher type", "Empty Field Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please first select the Ledger type", "Emplty Field Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * get the detail to save value
     */
    public void getValueToSaveFeeDetail() {
        if (student != null) {
            VoucherType voucherType = new VoucherType();

            voucherType.setVoucherName(((String) voucherTypeCombo.getSelectedItem()).trim());
            voucherType.setLedgerType(((String) ledgerTypeCombo.getSelectedItem()).trim());
            voucherType.setPaymentMode(((String) paymentMode.getSelectedItem()).trim());
            paymentDetails123.setPayAmount(Integer.parseInt(currentPayment.getText().trim()));
            paymentDetails123.setRemainingFee(Integer.parseInt(remainingAfterThis.getText().trim()));

            response = new PayFeeServiceImpl().payFee(student, voucherType, paymentDetails123);

            if (response.getStatus() == 1) {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.INFORMATION_MESSAGE);
                voucherTypeCombo.setEnabled(true);
                ledgerTypeCombo.setEnabled(true);
                paymentMode.setEnabled(true);
                getStudentRecord.setEnabled(true);
                currentPayment.setEditable(true);
                fineTextField.setEditable(true);
                payFeeButton.setEnabled(false);
            } else {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.ERROR_MESSAGE);
                getStudentRecord.setEnabled(true);
                payFeeButton.setEnabled(false);
            }
        }
    }

    /**
     * validate and save the fee
     */
    public void payFee() {
        if (paymentMode.getSelectedItem() != null && !(((String) paymentMode.getSelectedItem()).equalsIgnoreCase("-Select-"))) {
            if (voucherTypeCombo.getSelectedItem() != null && !(((String) voucherTypeCombo.getSelectedItem()).equalsIgnoreCase("-Select-"))) {
                if (ledgerTypeCombo.getSelectedItem() != null && !(((String) ledgerTypeCombo.getSelectedItem()).equalsIgnoreCase("-Select-"))) {
                    if (currentPayment.getText() != null && currentPayment.getText().trim().length() > 0 && r.matcher((currentPayment.getText())).matches()) {
                        getValueToSaveFeeDetail();
                    } else {
                        JOptionPane.showMessageDialog(this, "please enter a valid amount first", "Empty Field Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Please first select voucher type", "Empty Field Error", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(this, "Please first select the Ledger type", "Emplty Field Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please first select the payment mode", "Emplty Field Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method update the individual fine detail
     */
    public void updateFineDetail() {
        if (validateRegistrationField()) {
            if (fineIdTextField.getText().trim().length() > 0 && fineIdTextField.getText() != null) {
                response = new FineServiceImpl().updateFine(Integer.parseInt(fineIdTextField.getText().trim()), Integer.parseInt(fineTextField.getText().trim()));
                if (response.getStatus() == 1) {
                    getStudentAndFeeDetail();
                    updateFineButton.setEnabled(false);
                    payFeeButton.setEnabled(true);
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.ERROR_MESSAGE);
                    payFeeButton.setEnabled(false);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Fine Id not available ", "Error", JOptionPane.ERROR_MESSAGE);
                payFeeButton.setEnabled(false);
            }

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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        regdNoField = new javax.swing.JTextField();
        getStudentRecord = new javax.swing.JButton();
        stuSession = new javax.swing.JComboBox();
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
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        feeDetailsTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                Component returnComp = super.prepareRenderer(renderer, row, column);
                Color alternateColor = new Color(252,242,206);
                Color whiteColor = Color.WHITE;
                if (!returnComp.getBackground().equals(getSelectionBackground())){
                    Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
                    returnComp .setBackground(bg);
                    bg = null;
                }
                return returnComp;
            }
        }
        ;
        classForParameter = new javax.swing.JLabel();
        recordFound = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        currentPayment = new javax.swing.JTextField();
        fineTextField = new javax.swing.JTextField();
        remainingFeeField = new javax.swing.JTextField();
        remainingAfterThis = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        amountGivenByPayee = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        returnAmount = new javax.swing.JTextField();
        updateFineButton = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        currentPaymentDetailTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                Component returnComp = super.prepareRenderer(renderer, row, column);
                Color alternateColor = new Color(252,242,206);
                Color whiteColor = Color.WHITE;
                if (!returnComp.getBackground().equals(getSelectionBackground())){
                    Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
                    returnComp .setBackground(bg);
                    bg = null;
                }
                return returnComp;
            }
        };
        currentPaymentStatus = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        voucherTypeCombo = new javax.swing.JComboBox();
        ledgerTypeCombo = new javax.swing.JComboBox();
        paymentMode = new javax.swing.JComboBox();
        paymentModeFormOpener = new javax.swing.JButton();
        payFeeButton = new javax.swing.JButton();
        fineIdTextField = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel4 = new javax.swing.JLabel();
        topBarStatus = new javax.swing.JLabel();

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setOpaque(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jPanel1.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel1.setText("Registration No. : ");

        regdNoField.setBackground(new java.awt.Color(255, 255, 204));

        getStudentRecord.setText("Go");
        getStudentRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getStudentRecordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(regdNoField, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stuSession, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(getStudentRecord, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(regdNoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stuSession, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(getStudentRecord))
                .addContainerGap())
        );

        studentInfoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Student Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Algerian", 2, 18), new java.awt.Color(123, 6, 6))); // NOI18N
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
        studentFatherName.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        studentRegdNo.setBackground(new java.awt.Color(255, 255, 204));
        studentRegdNo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        studentClass.setBackground(new java.awt.Color(255, 255, 204));
        studentClass.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

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
        studentName.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        studentLastDate.setBackground(new java.awt.Color(255, 255, 204));
        studentLastDate.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        studentStatus.setBackground(new java.awt.Color(255, 255, 204));
        studentStatus.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

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
                .addGroup(studentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(studentName, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studentStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studentLastDate, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studentFatherName, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studentClass, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(studentRegdNo, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        studentInfoPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {studentClass, studentFatherName, studentLastDate, studentName, studentRegdNo, studentStatus});

        studentInfoPanelLayout.setVerticalGroup(
            studentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(studentInfoPanelLayout.createSequentialGroup()
                .addGroup(studentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(studentInfoPanelLayout.createSequentialGroup()
                        .addContainerGap(20, Short.MAX_VALUE)
                        .addGroup(studentInfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(studentInfoPanelLayout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11))
                            .addGroup(studentInfoPanelLayout.createSequentialGroup()
                                .addComponent(studentName, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(studentRegdNo, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(studentClass, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(studentFatherName, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(studentLastDate, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(studentStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(23, 23, 23))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Fee Details", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Algerian", 2, 18), new java.awt.Color(123, 6, 6))); // NOI18N
        jPanel5.setOpaque(false);

        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setOpaque(false);
        jScrollPane1.setBorder(createEmptyBorder());

        feeDetailsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        feeDetailsTable.setOpaque(false);
        jScrollPane1.setViewportView(feeDetailsTable);

        classForParameter.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        classForParameter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        recordFound.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        recordFound.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(classForParameter, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(recordFound, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(classForParameter, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(recordFound, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PAY FEE", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Algerian", 2, 18))); // NOI18N
        jPanel6.setOpaque(false);

        jLabel14.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(123, 6, 6));
        jLabel14.setText("Amount : Rs.");

        jLabel15.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(123, 6, 6));
        jLabel15.setText("Fine :   Rs. ");

        jLabel16.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(123, 6, 6));
        jLabel16.setText("Remaining Fees including Fine :    Rs.");

        jLabel17.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(123, 6, 6));
        jLabel17.setText("Fee Remaining after this payment : Rs.");

        currentPayment.setEditable(false);
        currentPayment.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                currentPaymentKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                currentPaymentKeyReleased(evt);
            }
        });

        fineTextField.setEditable(false);
        fineTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fineTextFieldKeyReleased(evt);
            }
        });

        remainingFeeField.setEditable(false);

        remainingAfterThis.setEditable(false);

        jLabel18.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(123, 6, 6));
        jLabel18.setText("Amount given by payee :");

        amountGivenByPayee.setEditable(false);
        amountGivenByPayee.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                amountGivenByPayeeKeyReleased(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(123, 6, 6));
        jLabel19.setText("Amount to be return");

        returnAmount.setEditable(false);

        updateFineButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/edit-small.png"))); // NOI18N
        updateFineButton.setToolTipText("Click to update fine");
        updateFineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateFineButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(currentPayment, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(fineTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(updateFineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, Short.MAX_VALUE)))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(remainingFeeField, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(remainingAfterThis, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(amountGivenByPayee, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(returnAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(30, 30, 30))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(currentPayment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(remainingFeeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel15)
                        .addComponent(fineTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel17)
                    .addComponent(remainingAfterThis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updateFineButton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel18)
                        .addComponent(amountGivenByPayee, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(returnAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel19)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Current Payment Detail", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Algerian", 2, 18))); // NOI18N
        jPanel7.setOpaque(false);

        jScrollPane2.getViewport().setOpaque(false);
        jScrollPane2.setOpaque(false);
        jScrollPane2.setBorder(createEmptyBorder());

        currentPaymentDetailTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        currentPaymentDetailTable.setOpaque(false);
        jScrollPane2.setViewportView(currentPaymentDetailTable);

        currentPaymentStatus.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(currentPaymentStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 512, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(currentPaymentStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Voucher Entry", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Algerian", 2, 18))); // NOI18N
        jPanel9.setAutoscrolls(true);
        jPanel9.setOpaque(false);

        jLabel6.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel6.setText("Voucher Type : ");

        jLabel20.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel20.setText("Ledger Type : ");

        jLabel21.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel21.setText("Payment Mode : ");

        paymentMode.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-Select-", "Cash", "Cheque", "DD" }));
        paymentMode.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                paymentModeItemStateChanged(evt);
            }
        });

        paymentModeFormOpener.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/Add Row2.png"))); // NOI18N
        paymentModeFormOpener.setEnabled(false);
        paymentModeFormOpener.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paymentModeFormOpenerActionPerformed(evt);
            }
        });

        payFeeButton.setText("Pay Fee");
        payFeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payFeeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(voucherTypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ledgerTypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel21)
                .addGap(18, 18, 18)
                .addComponent(paymentMode, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(paymentModeFormOpener, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92)
                .addComponent(payFeeButton)
                .addGap(18, 18, 18)
                .addComponent(fineIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addComponent(paymentModeFormOpener, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(voucherTypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(ledgerTypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(paymentMode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(payFeeButton)
                .addComponent(fineIdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(studentInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(studentInfoPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToolBar1.setBorder(null);
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setOpaque(false);

        jLabel4.setText("Status :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(291, 291, 291)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(topBarStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(topBarStatus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void getStudentRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getStudentRecordActionPerformed
        getStudentAndFeeDetail();        // TODO add your handling code here:
    }//GEN-LAST:event_getStudentRecordActionPerformed

    private void currentPaymentKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_currentPaymentKeyTyped
        //   calculateCurretnFeePayByPayee();
    }//GEN-LAST:event_currentPaymentKeyTyped

    private void currentPaymentKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_currentPaymentKeyReleased
        if (currentPaymentDetailTable.getRowCount() > 0) {
            calculateCurretnFeePayByPayee();
        }
    }//GEN-LAST:event_currentPaymentKeyReleased

    private void fineTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fineTextFieldKeyReleased
        updateFineButton.setEnabled(true);
        payFeeButton.setEnabled(false);
    }//GEN-LAST:event_fineTextFieldKeyReleased

    private void amountGivenByPayeeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amountGivenByPayeeKeyReleased
        returnAmount();
    }//GEN-LAST:event_amountGivenByPayeeKeyReleased

    private void updateFineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateFineButtonActionPerformed
        updateFineDetail();
    }//GEN-LAST:event_updateFineButtonActionPerformed

    private void paymentModeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_paymentModeItemStateChanged
        paymentModeId = 0;
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            if (!(String.valueOf(paymentMode.getSelectedItem())).equalsIgnoreCase("cash") && !(String.valueOf(paymentMode.getSelectedItem())).equalsIgnoreCase("-Select-")) {
                paymentModeFormOpener.setEnabled(true);
                payFeeButton.setEnabled(false);
            } else {
                paymentModeFormOpener.setEnabled(false);
                payFeeButton.setEnabled(true);
            }
        }
    }//GEN-LAST:event_paymentModeItemStateChanged

    private void paymentModeFormOpenerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paymentModeFormOpenerActionPerformed
        validateAndOpenPaymentForm();
    }//GEN-LAST:event_paymentModeFormOpenerActionPerformed

    private void payFeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payFeeButtonActionPerformed
        payFee();
    }//GEN-LAST:event_payFeeButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField amountGivenByPayee;
    private javax.swing.JLabel classForParameter;
    private javax.swing.JTextField currentPayment;
    private javax.swing.JTable currentPaymentDetailTable;
    private javax.swing.JLabel currentPaymentStatus;
    private javax.swing.JTable feeDetailsTable;
    private javax.swing.JTextField fineIdTextField;
    private javax.swing.JTextField fineTextField;
    private javax.swing.JButton getStudentRecord;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JComboBox ledgerTypeCombo;
    private javax.swing.JButton payFeeButton;
    private javax.swing.JComboBox paymentMode;
    private javax.swing.JButton paymentModeFormOpener;
    private javax.swing.JLabel recordFound;
    private javax.swing.JTextField regdNoField;
    private javax.swing.JTextField remainingAfterThis;
    private javax.swing.JTextField remainingFeeField;
    private javax.swing.JTextField returnAmount;
    private javax.swing.JComboBox stuSession;
    private javax.swing.JLabel studentClass;
    private javax.swing.JLabel studentFatherName;
    private javax.swing.JPanel studentInfoPanel;
    private javax.swing.JLabel studentLastDate;
    private javax.swing.JLabel studentName;
    private javax.swing.JLabel studentRegdNo;
    private javax.swing.JLabel studentStatus;
    private javax.swing.JLabel topBarStatus;
    private javax.swing.JButton updateFineButton;
    private javax.swing.JComboBox voucherTypeCombo;
    // End of variables declaration//GEN-END:variables
}
