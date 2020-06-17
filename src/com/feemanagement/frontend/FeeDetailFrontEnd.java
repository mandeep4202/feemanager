/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.frontend;

import com.feemanagement.dto.ClassBean;
import com.feemanagement.dto.FeeDetails;
import com.feemanagement.dto.GroupAndClass;
import com.feemanagement.dto.Response;
import com.feemanagement.dto.SessionBean;
import com.feemanagement.helper.SessionClass;
import com.feemanagement.services.FeeDetailServiceImpl;
import com.feemanagement.services.GroupAndClassServiceImpl;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author 786
 */
public final class FeeDetailFrontEnd extends javax.swing.JPanel {

    int row = 0;
    int col = 1;
    //static int dynamicName = 1;
    private final List<JTextField> textFields = new ArrayList<>();
    Response response = null;
    FeeDetails feeDetails = new FeeDetails();
    String pattern = "^[0-9]*$";
    Pattern r = Pattern.compile(pattern);
    private static FeeDetailFrontEnd feeDetailFrontEnd;

    /**
     * Creates new form FeeDetailFrontEnd12
     */
    public FeeDetailFrontEnd() {
        initComponents();
        //  new MakeMoveable(this);
        setDefault_FeeAddSucessfullTable();
        feeIdText.setVisible(false);
        setValueToGroupComboBox();
        addField();
        setValueToStuSessionCombo();
        // FrameDecorator.decorateFrame((JFrame) this);
    }

    /**
     * This method set the properties of FessAddSucessfull table of
     * FeeDetailFrontEnds window to custom value at startup like column
     * name,column width
     */
    private void setDefault_FeeAddSucessfullTable() {
        feeAddSucessfullTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "S.No ", "Class", "Particulars", "Amount(Rs.)", "Session", "feeid"
                }
        )
        );

        feeAddSucessfullTable.getTableHeader().setBackground(new Color(123, 6, 6));
        feeAddSucessfullTable.getTableHeader().setForeground(Color.white);
        feeAddSucessfullTable.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 14));

        feeAddSucessfullTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        feeAddSucessfullTable.getColumnModel().getColumn(1).setPreferredWidth(20);
        feeAddSucessfullTable.getColumnModel().getColumn(2).setPreferredWidth(55);
        feeAddSucessfullTable.getColumnModel().getColumn(3).setPreferredWidth(45);
        feeAddSucessfullTable.getColumnModel().getColumn(4).setPreferredWidth(100);

        feeAddSucessfullTable.getColumnModel().getColumn(5).setMaxWidth(0);
        feeAddSucessfullTable.getColumnModel().getColumn(5).setMinWidth(0);
        feeAddSucessfullTable.getColumnModel().getColumn(5).setPreferredWidth(0);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment((int) CENTER_ALIGNMENT);

        for (int i = 0; i < feeAddSucessfullTable.getColumnCount(); i++) {
            feeAddSucessfullTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        feeAddSucessfullTable.setRowSelectionAllowed(true);
    }

    /**
     * validate the ComboBox
     *
     * @return
     */
    public boolean validateTheComboBox() {

        boolean result = false;
        GroupAndClass gac = (GroupAndClass) groupIdComboBox.getSelectedItem();
        ClassBean cb = (ClassBean) stuClass.getSelectedItem();

        if (!(gac.getGroupName() != null && gac.getGroupName().equalsIgnoreCase("-Group-"))) {
            if (!(cb.getClassName() != null && cb.getClassName().equalsIgnoreCase("No Data"))) {

                result = true;

            } else {
                JOptionPane.showMessageDialog(this, "Sory no class available", "Response", JOptionPane.ERROR_MESSAGE);
            }
        } else {

            JOptionPane.showMessageDialog(this, "Please first select a group", "Response", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    /**
     * Fetch and forward fee detail to AddFee to FeeDetailFrontEndsDAO And
     * refresh the FeeAddSucessfullTable
     *
     */
    public void saveFeeDetail() {
        if (validateTheComboBox()) {
            List<FeeDetails> feeList = getValueFromDynamicFields(textFields.size());
            if (feeList != null) {
                response = new FeeDetailServiceImpl().saveFeeDetail(feeList);
                if (response != null) {
                    if (response.getStatus() == 1) {
                        JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.INFORMATION_MESSAGE);
                        statusMessage.setBackground(Color.BLUE);
                        resetAddFeeDetailField();
                        statusMessage.setText("Display Result for parameter Class= " + feeDetails.getStudentClass() + " particular  " + feeDetails.getParticular());
                    } else {
                        JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.ERROR_MESSAGE);
                        resetAddFeeDetailField();
                        statusMessage.setForeground(Color.red);
                        statusMessage.setText("Data already exist for supplied value ");
                    }
                    // calling the displayFeeDetail method for dispay fee detail on table
                    displayFeeDetail(feeList.get(0));
                }

            }
        }
    }

    /**
     * This method is used to display the fee detail on the
     * feeAddSucessfullTable
     *
     * @param feeDetails
     */
    public void displayFeeDetail(FeeDetails feeDetails) {
        int totalFee = 0;
        int i = 0;
        // if (validateTheComboBox()) {
        //      List<FeeDetails> feeList = getValueFromDynamicFields(textFields.size());
        if (feeDetails != null) {

            response = new FeeDetailServiceImpl().getFeeDetail(feeDetails);
            setDefault_FeeAddSucessfullTable();

            if (response.getStatus() == 1) {

                DefaultTableModel feeAddSucessfullTableVariable = (DefaultTableModel) feeAddSucessfullTable.getModel();
                ArrayList<FeeDetails> feeDetailList = (ArrayList<FeeDetails>) response.getData();

                for (FeeDetails fee : feeDetailList) {
                    i++;
                    totalFee = fee.getParticularAmount() + totalFee;
                    feeAddSucessfullTableVariable.addRow(
                            new Object[]{
                                i, fee.getStudentClass(), fee.getParticular(), fee.getParticularAmount(), fee.getStudentSession(), fee.getFeeid()
                            }
                    );
                }

            }
        }

        recordCount.setText("No. of record found  " + i + "         Total Fee = Rs. " + totalFee);
    }

    /**
     * This method display fee detail on itemstatechange
     */
    public void responseOnItemStateChange() {

        GroupAndClass gac = (GroupAndClass) groupIdComboBox.getSelectedItem();
        ClassBean cb = (ClassBean) stuClass.getSelectedItem();
        SessionBean sb = (SessionBean) stuSession.getSelectedItem();

        if (!(gac.getGroupName() != null && gac.getGroupName().equalsIgnoreCase("-Group-"))) {
            statusMessage.setText("");
            feeDetails.setClassId(cb.getClassId());
            feeDetails.setSessionId(sb.getSessionId());
            feeDetails.setGroupId(gac.getGroupId());
            displayFeeDetail(feeDetails);

        } else {
            setDefault_FeeAddSucessfullTable();
            statusMessage.setText("Please select a group first");
            statusMessage.setForeground(Color.red);
            recordCount.setText("No. of record found  " + 0 + "         Total Fee = Rs. " + 0);
        }

    }

    /**
     * This method reset the field of FeeDetailFrontEndField
     */
    public void resetAddFeeDetailField() {
        for (JTextField textFieldLocalVar : textFields) {
            textFieldLocalVar.setText("");
        }
    }

    /**
     * This method get value from the selected column of the table
     *
     * @return
     */
    public FeeDetails getTableValueForUpdate() {

        feeDetails.setFeeid((int) feeAddSucessfullTable.getValueAt(feeAddSucessfullTable.getSelectedRow(), 5));
        feeDetails.setParticular((String) feeAddSucessfullTable.getValueAt(feeAddSucessfullTable.getSelectedRow(), 2));
        feeDetails.setParticularAmount((int) feeAddSucessfullTable.getValueAt(feeAddSucessfullTable.getSelectedRow(), 3));
        feeDetails.setStudentClass((String) feeAddSucessfullTable.getValueAt(feeAddSucessfullTable.getSelectedRow(), 1));
        feeDetails.setStudentSession((String) feeAddSucessfullTable.getValueAt(feeAddSucessfullTable.getSelectedRow(), 4));

        stuClass.setSelectedItem(feeDetails.getStudentClass());
        stuSession.setSelectedItem(feeDetails.getStudentSession());

        textFields.get(0).setText(feeDetails.getParticular());
        textFields.get(1).setText(String.valueOf(feeDetails.getParticularAmount()));

        feeIdText.setText(String.valueOf(feeDetails.getFeeid()));
        return feeDetails;
    }

    /**
     * This method is used to get values from dynamic created text field and
     * static created text field and store in a List<FeeDetails>
     *
     * @param maxLimit
     * @return List<FeeDetails>
     */
    public List<FeeDetails> getValueFromDynamicFields(int maxLimit) {
        FeeDetails feeDetails2 = null;
        // These variable is use as a indexno to take value from the List into the feeDetails Variable
        List<FeeDetails> feeDetailsList = new ArrayList<>();
        feeDetails2 = new FeeDetails();
        for (int i = 0; i < maxLimit; i++) {

            // System.out.println("  textfield  "  + textFields.get(0).getText());
            if (i % 2 == 0) {

                if (textFields.get(i).getText() != null && textFields.get(i).getText().trim().length() > 0) {
                    feeDetails2.setParticular(textFields.get(i).getText());

                } else {
                    feeDetailsList = null;
                    JOptionPane.showMessageDialog(this, "Must supply all value", "Empty Particular Error", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            } // logic for a particular amount
            else {

                if (textFields.get(i).getText() != null && textFields.get(i).getText().trim().length() > 0) {
                    if (r.matcher((textFields.get(i).getText())).matches()) {

                        feeDetails2.setParticularAmount(Integer.parseInt(textFields.get(i).getText()));
                    } else {
                        feeDetailsList = null;
                        JOptionPane.showMessageDialog(this, "Particular amount must be number", "Empty Particular Error", JOptionPane.ERROR_MESSAGE);
                        break;
                    }

                } else {
                    feeDetailsList = null;
                    JOptionPane.showMessageDialog(this, "Must supply all value", "Empty Particular Error", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }

            if (i % 2 != 0) {

                //  System.out.println("  grouIdCombo Box    "+  ((GroupAndClass) groupIdComboBox.getSelectedItem()).getGroupId());
                feeDetails2.setGroupId(((GroupAndClass) groupIdComboBox.getSelectedItem()).getGroupId());
                feeDetails2.setClassId(((ClassBean) stuClass.getSelectedItem()).getClassId());
                feeDetails2.setSessionId(((SessionBean) stuSession.getSelectedItem()).getSessionId());

                feeDetailsList.add(feeDetails2);

                feeDetails2 = new FeeDetails();
            }

        }
        return feeDetailsList;
    }

    /**
     * This method update the feeDetail
     */
    public void updateFeeDetails() {
        if (feeAddSucessfullTable.getSelectedColumnCount() > 0) {
            //call the getValueFromDynamicFields() . here 2 specify only validate a first two text fields
            List<FeeDetails> feeList = getValueFromDynamicFields(2);

            if (feeList != null) {
                FeeDetails feeDetailsVar = feeList.get(0);
                feeDetailsVar.setFeeid(Integer.parseInt(feeIdText.getText()));
                response = new FeeDetailServiceImpl().updateFeeDetail(feeDetailsVar);
                if (response.getStatus() == 1) {
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.INFORMATION_MESSAGE);
                    resetAddFeeDetailField();

                } else {
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.ERROR_MESSAGE);
                    resetAddFeeDetailField();
                }
                displayFeeDetail(feeDetails);
            } else {
                // JOptionPane.showMessageDialog(this, "All required value not available", "Response", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to perform update operation", "Response", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * This method delete the fee detail
     */
    public void deleteFeeDetail() {
        response = new FeeDetailServiceImpl().deleteFeeDetail(Integer.parseInt(feeIdText.getText()));

        if (response.getStatus() == 1) {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.INFORMATION_MESSAGE);
            resetAddFeeDetailField();

        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.ERROR_MESSAGE);
            resetAddFeeDetailField();
        }
        displayFeeDetail(feeDetails);
    }

    /**
     * This method contains a logic to add a fields dynamically
     */
    public final void addField() {

        JTextField dynamicTextField;
        if (textFields.size() <= 26) {
            int i = 0;
            row++;
            col++;
            while (i < 2) {
                dynamicTextField = new JTextField(10);
                addFieldPanel.add(dynamicTextField);
                dynamicTextField.setBounds(15, 236, 375, 27);
                dynamicTextField.setPreferredSize(new Dimension(6, 23));
                dynamicTextField.setBackground(new Color(255, 255, 204));
                textFields.add(dynamicTextField);
                addFieldPanel.setLayout(new GridLayout(row, col, 7, 7));
                addFieldPanel.setOpaque(false);

                mainContainer.repaint();
                mainContainer.validate();

                i++;
            }
        }
    }

    /**
     * This method contain a logic to remove a text field from panel at runtime
     */
    public void removeField() {
        int i = 0;
        if ((textFields.size()) > 2) {
            row--;
            col--;
            while (i < 2) {
                //removing text fields from panel as well as from List i.e. textFields
                addFieldPanel.remove(textFields.get(textFields.size() - 1));
                textFields.remove(textFields.get(textFields.size() - 1));
                addFieldPanel.setLayout(new GridLayout(row, col, 6, 6));
                mainContainer.repaint();
                mainContainer.validate();
                i++;
            }

        }
    }

    /**
     * This method check whether value enter inside the dynamic text fields are
     * Number or not
     *
     * @return boolean
     */
    public boolean checkingForNumber() {
        boolean result = true;
        int particularAmountVar = 1;
        for (int i = 0; i < textFields.size() / 2; i++) {
            if (!r.matcher((textFields.get(particularAmountVar).getText())).matches()) {
                result = false;
                break;
            }
            particularAmountVar = particularAmountVar + 2;
        }
        return result;
    }

    /**
     * This method setValueToGroupComboBox
     */
    public void setValueToGroupComboBox() {
        GroupAndClass gac = new GroupAndClass();
        gac.setGroupId(786);
        gac.setGroupName("-Group-");

        GroupAndClass gac1 = new GroupAndClass();
        gac1.setGroupId(786786);
        gac1.setGroupName("ALL");

        groupIdComboBox.removeAllItems();
        response = new GroupAndClassServiceImpl().getGroupNames();
        if (response.getStatus() == 1) {
            ArrayList<GroupAndClass> groupAndClassList = (ArrayList<GroupAndClass>) response.getData();
            groupAndClassList.add(0, gac);
            groupAndClassList.add(1, gac1);
//            for (GroupAndClass group1 : groupAndClassList) {
//                groupIdComboBox.addItem(group1);
//            }
            groupIdComboBox.setModel(new DefaultComboBoxModel(groupAndClassList.toArray()));

        }
    }

    /**
     * This method setValueToClassComboBox
     */
    public void setValueToClassComboBox() {
        GroupAndClass groupAndClass1 = (GroupAndClass) groupIdComboBox.getSelectedItem();

        ClassBean cb1 = new ClassBean();
        cb1.setClassId(786);
        cb1.setClassName("-Class-");

        ClassBean cb2 = new ClassBean();
        cb2.setClassId(786786);
        cb2.setClassName("ALL");

        ClassBean cb3 = new ClassBean();
        cb3.setClassId(7);
        cb3.setClassName("No Data");

        if (!groupAndClass1.getGroupName().equalsIgnoreCase("All")) {
            if (!groupAndClass1.getGroupName().equals("-Group-")) {

                stuClass.removeAllItems();

                response = new GroupAndClassServiceImpl().getClassDetails(groupAndClass1.getGroupId());
                //  System.out.println("hello        " + groupAndClass1.getGroupId());

                if (response.getStatus() == 1) {

                    ArrayList<GroupAndClass> groupAndClassList = (ArrayList<GroupAndClass>) response.getData();

                    ArrayList<ClassBean> classList = new ArrayList<>();

                    int i = 0;
                    for (GroupAndClass group1 : groupAndClassList) {
                        cb3 = new ClassBean();

                        cb3.setClassId(group1.getClassId());
                        cb3.setClassName(group1.getClassName());

                        classList.add(i, cb3);
                        i++;
                    }
                    classList.add(0, cb2);
                    for (ClassBean group1 : classList) {
                        stuClass.addItem(group1);
                    }

                } else {
                    stuClass.removeAllItems();
                    stuClass.addItem(cb3);
                }

            } else {
                stuClass.removeAllItems();
                stuClass.addItem(cb1);
            }

        } else {
            stuClass.removeAllItems();
            stuClass.addItem(cb2);
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainContainer = new javax.swing.JPanel() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        }
        ;
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        stuClass = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        stuSession = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        groupIdComboBox = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        statusMessage = new javax.swing.JLabel();
        recordCount = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        feeAddSucessfullTable = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

            public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
                Component returnComp = super.prepareRenderer(renderer, row, column);
                Color alternateColor = new Color(252,242,206);
                Color whiteColor = Color.WHITE;
                if (!returnComp.getBackground().equals(getSelectionBackground())){
                    Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
                    returnComp.setBackground(bg);
                    //bg = null;
                }
                //        if( returnComp instanceof JComponent )
                //        ((JComponent)returnComp).setOpaque(false);
                return returnComp;
            }

        };
        addFieldPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        updateFeeDetail = new javax.swing.JButton();
        resetAddFeeFields = new javax.swing.JButton();
        deleteFeeDetail = new javax.swing.JButton();
        saveFeeDetail = new javax.swing.JButton();
        addRow = new javax.swing.JButton();
        deleteRow = new javax.swing.JButton();
        feeIdText = new javax.swing.JTextField();

        setOpaque(false);

        mainContainer.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(123, 6, 6)));
        mainContainer.setOpaque(false);

        jLabel4.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(123, 6, 6));
        jLabel4.setText("Amount");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add Fee", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Algerian", 2, 18), new java.awt.Color(123, 6, 6))); // NOI18N
        jPanel1.setOpaque(false);

        stuClass.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                stuClassItemStateChanged(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(123, 6, 6));
        jLabel1.setText("Class ");

        stuSession.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                stuSessionItemStateChanged(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(123, 6, 6));
        jLabel2.setText("Session ");

        jLabel5.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(123, 6, 6));
        jLabel5.setText("Group");

        groupIdComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                groupIdComboBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(groupIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(stuClass, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(stuSession, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(groupIdComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(stuClass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stuSession, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Information Panel", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Algerian", 2, 18), new java.awt.Color(123, 6, 6))); // NOI18N
        jPanel3.setOpaque(false);

        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setViewportBorder(createEmptyBorder());
        jScrollPane1.setBorder(createEmptyBorder());

        feeAddSucessfullTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        feeAddSucessfullTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        feeAddSucessfullTable.setGridColor(new java.awt.Color(123, 6, 6));
        feeAddSucessfullTable.setOpaque(false);
        feeAddSucessfullTable.setRowHeight(19);
        feeAddSucessfullTable.setRowMargin(2);
        feeAddSucessfullTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                feeAddSucessfullTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(feeAddSucessfullTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 550, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(recordCount, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(statusMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(recordCount, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        addFieldPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout addFieldPanelLayout = new javax.swing.GroupLayout(addFieldPanel);
        addFieldPanel.setLayout(addFieldPanelLayout);
        addFieldPanelLayout.setHorizontalGroup(
            addFieldPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 242, Short.MAX_VALUE)
        );
        addFieldPanelLayout.setVerticalGroup(
            addFieldPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 33, Short.MAX_VALUE)
        );

        jLabel3.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(123, 6, 6));
        jLabel3.setText("Particulars");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Controls", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Algerian", 2, 18), new java.awt.Color(123, 6, 6))); // NOI18N
        jPanel2.setOpaque(false);

        updateFeeDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/pencil-icon24.png"))); // NOI18N
        updateFeeDetail.setText("Update");
        updateFeeDetail.setEnabled(false);
        updateFeeDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateFeeDetailActionPerformed(evt);
            }
        });

        resetAddFeeFields.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/renew-icon24.png"))); // NOI18N
        resetAddFeeFields.setText("Reset");
        resetAddFeeFields.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetAddFeeFieldsActionPerformed(evt);
            }
        });

        deleteFeeDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/Delete-icon24.png"))); // NOI18N
        deleteFeeDetail.setText("Delete");
        deleteFeeDetail.setEnabled(false);
        deleteFeeDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteFeeDetailActionPerformed(evt);
            }
        });

        saveFeeDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/save.png"))); // NOI18N
        saveFeeDetail.setText("Save");
        saveFeeDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFeeDetailActionPerformed(evt);
            }
        });

        addRow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/Add Row2.png"))); // NOI18N
        addRow.setText("Add Row");
        addRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRowActionPerformed(evt);
            }
        });

        deleteRow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/Deleterow.png"))); // NOI18N
        deleteRow.setText("Delete Row");
        deleteRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteRowActionPerformed(evt);
            }
        });

        feeIdText.setEditable(false);
        feeIdText.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(deleteFeeDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(updateFeeDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resetAddFeeFields, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveFeeDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(deleteRow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addRow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(feeIdText, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addRow, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteRow, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveFeeDetail, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(resetAddFeeFields, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(updateFeeDetail, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteFeeDetail, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(feeIdText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(196, 196, 196))
        );

        javax.swing.GroupLayout mainContainerLayout = new javax.swing.GroupLayout(mainContainer);
        mainContainer.setLayout(mainContainerLayout);
        mainContainerLayout.setHorizontalGroup(
            mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContainerLayout.createSequentialGroup()
                .addGroup(mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainContainerLayout.createSequentialGroup()
                        .addGroup(mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainContainerLayout.createSequentialGroup()
                                .addGap(103, 103, 103)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(44, 44, 44)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainContainerLayout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(addFieldPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(mainContainerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        mainContainerLayout.setVerticalGroup(
            mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(mainContainerLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addGroup(mainContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(8, 8, 8)
                        .addComponent(addFieldPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1161, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(mainContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 621, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(mainContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void stuClassItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_stuClassItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            responseOnItemStateChange();
            resetAddFeeDetailField();
        }
    }//GEN-LAST:event_stuClassItemStateChanged

    private void stuSessionItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_stuSessionItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            responseOnItemStateChange();
            resetAddFeeDetailField();
        }
    }//GEN-LAST:event_stuSessionItemStateChanged

    private void groupIdComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_groupIdComboBoxItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            setValueToClassComboBox();
        }
    }//GEN-LAST:event_groupIdComboBoxItemStateChanged

    private void feeAddSucessfullTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_feeAddSucessfullTableMouseClicked
        getTableValueForUpdate();
        updateFeeDetail.setEnabled(true);
        deleteFeeDetail.setEnabled(true);
    }//GEN-LAST:event_feeAddSucessfullTableMouseClicked

    private void updateFeeDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateFeeDetailActionPerformed

        updateFeeDetails();
    }//GEN-LAST:event_updateFeeDetailActionPerformed

    private void resetAddFeeFieldsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetAddFeeFieldsActionPerformed
        resetAddFeeDetailField();
        if (stuClass.getItemCount() > 0) {
            stuClass.setSelectedIndex(0);
        }
        stuSession.setSelectedIndex(0);
    }//GEN-LAST:event_resetAddFeeFieldsActionPerformed

    private void deleteFeeDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteFeeDetailActionPerformed
        deleteFeeDetail();
    }//GEN-LAST:event_deleteFeeDetailActionPerformed

    private void saveFeeDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveFeeDetailActionPerformed
        //  validateTheComboBox();
        saveFeeDetail();
    }//GEN-LAST:event_saveFeeDetailActionPerformed

    private void addRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRowActionPerformed
        addField();        // TODO add your handling code here:
    }//GEN-LAST:event_addRowActionPerformed

    private void deleteRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteRowActionPerformed
        removeField();        // TODO add your handling code here:
    }//GEN-LAST:event_deleteRowActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel addFieldPanel;
    private javax.swing.JButton addRow;
    private javax.swing.JButton deleteFeeDetail;
    private javax.swing.JButton deleteRow;
    private javax.swing.JTable feeAddSucessfullTable;
    private javax.swing.JTextField feeIdText;
    private javax.swing.JComboBox groupIdComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainContainer;
    private javax.swing.JLabel recordCount;
    private javax.swing.JButton resetAddFeeFields;
    private javax.swing.JButton saveFeeDetail;
    private javax.swing.JLabel statusMessage;
    private javax.swing.JComboBox stuClass;
    private javax.swing.JComboBox stuSession;
    private javax.swing.JButton updateFeeDetail;
    // End of variables declaration//GEN-END:variables
}
