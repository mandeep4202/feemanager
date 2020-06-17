/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.frontend;

import com.feemanagement.dto.GroupAndClass;
import com.feemanagement.dto.Response;
import com.feemanagement.services.GroupAndClassServiceImpl;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import static javax.swing.BorderFactory.createEmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Mandeep Singh
 */
public class GroupAndClassFrontEnd extends javax.swing.JPanel {

    private final GroupAndClass groupAndClass;
    Response response = new Response();

    /**
     * Creates new form GroupAndClassFrontEnd12
     */
    public GroupAndClassFrontEnd() {
        this.groupAndClass = new GroupAndClass();
        initComponents();
        classIdField.setVisible(false);
        groupIdField.setVisible(false);
        setDefault_ClassTable();
        setDefault_GroupTable();
        displayGroupNames();
        setValueToComboBox();
    }

    /**
     * Set a group table to defined value.method called from the constructor
     */
    public void setDefault_GroupTable() {

        groupTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    " S.No ", " Group ", "groupId"
                }
        ));
        // table header background
        groupTable.getTableHeader().setBackground(new Color(123, 6, 6));
        groupTable.getTableHeader().setForeground(Color.white);
        groupTable.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 14));

        groupTable.getColumnModel().getColumn(0).setPreferredWidth(25);
        groupTable.getColumnModel().getColumn(1).setPreferredWidth(42);
        groupTable.getColumnModel().getColumn(2).setMaxWidth(0);
        groupTable.getColumnModel().getColumn(2).setMinWidth(0);
        groupTable.getColumnModel().getColumn(2).setPreferredWidth(0);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment((int) CENTER_ALIGNMENT);

        for (int i = 0; i < groupTable.getColumnCount(); i++) {
            groupTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        groupTable.setRowSelectionAllowed(true);

    }

    /**
     * Set a class table to defined value.method called from the constructor
     */
    public void setDefault_ClassTable() {
        classTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    " S.No ", " Group ", " Class ", "classId"
                }
        ));

        classTable.getTableHeader().setBackground(new Color(123, 6, 6));
        classTable.getTableHeader().setForeground(Color.white);
        classTable.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 14));

        classTable.getColumnModel().getColumn(0).setPreferredWidth(25);

        classTable.getColumnModel().getColumn(1).setPreferredWidth(42);

        classTable.getColumnModel().getColumn(2).setPreferredWidth(42);

        classTable.getColumnModel().getColumn(3).setMaxWidth(0);
        classTable.getColumnModel().getColumn(3).setMinWidth(0);
        classTable.getColumnModel().getColumn(3).setPreferredWidth(0);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment((int) CENTER_ALIGNMENT);

        for (int i = 0; i < classTable.getColumnCount(); i++) {
            classTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        classTable.setRowSelectionAllowed(true);

    }

    /**
     *
     * @return
     */
    public String getAndValidateGroupField() {
        String groupName = "";
        if (groupField.getText() != null && groupField.getText().trim().length() > 0) {
            groupName = groupField.getText().trim();
        } else {
            JOptionPane.showMessageDialog(this, "Must Supply Group", "Response", JOptionPane.WARNING_MESSAGE);
        }
        return groupName;
    }

    /**
     * this method reset Group field
     */
    private void resetGroupField() {
        groupField.setText("");
    }

    /**
     * save group detail
     */
    public void saveGroup() {
        response = new GroupAndClassServiceImpl().saveGroup(getAndValidateGroupField());
        if (response != null) {
            if (response.getStatus() == 1) {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.INFORMATION_MESSAGE);
//                    statusMessage.setBackground(Color.BLUE);
//                    resetAddFeeDetailField();
//                    statusMessage.setText("Display Result for parameter Class= " + feeDetails.getStudentClass() + " particular  " + feeDetails.getParticular());
            } else {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.WARNING_MESSAGE);
//                    resetAddFeeDetailField();
//                    statusMessage.setForeground(Color.red);
//                    statusMessage.setText("Data already exist for parameter Class= " + feeDetails.getStudentClass() + " particular  " + feeDetails.getParticular());
            }
            resetGroupField();
            displayGroupNames();
            setValueToComboBox();

        }
    }

    /**
     * set the class related fields to default value
     */
    public void resetClassFields() {
        classField.setText("");
        //  selectGroupField.setSelectedIndex(0);

    }

    /**
     * display the groupName on the table
     */
    public void displayGroupNames() {
        response = new GroupAndClassServiceImpl().getGroupNames();
        setDefault_GroupTable();
        int i = 0;
        if (response.getStatus() == 1) {

            DefaultTableModel groupAndClassTableVariable = (DefaultTableModel) groupTable.getModel();
            ArrayList<GroupAndClass> groupAndClassList = (ArrayList<GroupAndClass>) response.getData();

            for (GroupAndClass group1 : groupAndClassList) {
                i++;
                groupAndClassTableVariable.addRow(
                        new Object[]{
                            i, group1.getGroupName(), group1.getGroupId()
                        });
            }
        }
        status.setText("No. of Group found  " + i);
    }

    /**
     * Fetch the value for update from the groupTable
     *
     * @return groupAndClass
     */
    public void getGroupTableValueForUpdate() {
        groupAndClass.setGroupId((int) groupTable.getValueAt(groupTable.getSelectedRow(), 2));
        groupAndClass.setGroupName((String) groupTable.getValueAt(groupTable.getSelectedRow(), 1));

        groupField.setText(groupAndClass.getGroupName());
        groupIdField.setText(String.valueOf(groupAndClass.getGroupId()));

    }

    /**
     *
     */
    private void deleteGroup() {
        if (groupTable.getSelectedColumnCount() > 0) {
            response = new GroupAndClassServiceImpl().deleteGroupName(Integer.parseInt(groupIdField.getText()));

            if (response.getStatus() == 1) {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.INFORMATION_MESSAGE);
                resetGroupField();

            } else {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.WARNING_MESSAGE);
                resetGroupField();
            }
            displayGroupNames();
            setValueToComboBox();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to perform delete operation", "Response", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * this method set the group value taken from
     */
    public void setValueToComboBox() {
        GroupAndClass gac = new GroupAndClass();
        gac.setGroupId(786);
        gac.setGroupName("-Group-");
        selectGroupField.removeAllItems();

        response = new GroupAndClassServiceImpl().getGroupNames();
        if (response.getStatus() == 1) {
            ArrayList<GroupAndClass> groupAndClassList = (ArrayList<GroupAndClass>) response.getData();

            groupAndClassList.add(0, gac);
            selectGroupField.setModel(new DefaultComboBoxModel(groupAndClassList.toArray()));

        }
    }

    /**
     * This method update the feeDetail
     */
    public void updateGroupDetails() {
        if (groupTable.getSelectedColumnCount() > 0) {
            response = new GroupAndClassServiceImpl().updateGroup(Integer.parseInt(groupIdField.getText()), getAndValidateGroupField());
            if (response.getStatus() == 1) {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.INFORMATION_MESSAGE);
                resetGroupField();

            } else {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.WARNING_MESSAGE);
                resetGroupField();
            }
            displayGroupNames();
            setValueToComboBox();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to perform a update operation", "Response", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * save group detail
     */
    public void saveClass() {
        GroupAndClass groupAndClass12 = getAndValidateClassField();
        if (groupAndClass12 != null) {
            response = new GroupAndClassServiceImpl().saveClass(groupAndClass12.getClassName(), groupAndClass12.getGroupId());
            if (response != null) {
                if (response.getStatus() == 1) {
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.INFORMATION_MESSAGE);
                    displayClassDetail();
                } else {
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.WARNING_MESSAGE);
                }
                resetClassFields();
                //setValueToComboBox();
            }
        } else {

        }
    }

    /**
     *
     * @return
     */
    public GroupAndClass getAndValidateClassField() {
        GroupAndClass groupAndClass1 = (GroupAndClass) selectGroupField.getSelectedItem();

        GroupAndClass andClass = null;
        if (!groupAndClass1.getGroupName().equals("-Group-")) {

            if (classField.getText() != null && classField.getText().trim().length() > 0) {
                andClass = new GroupAndClass();
                andClass.setClassName(classField.getText().trim());
                andClass.setGroupId(groupAndClass1.getGroupId());

            } else {
                JOptionPane.showMessageDialog(this, "Must supply a class", "Response", JOptionPane.WARNING_MESSAGE);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please select group name", "Response", JOptionPane.WARNING_MESSAGE);
        }
        return andClass;
    }

    /**
     * Display the classDetailsToClass
     */
    public void displayClassDetail() {
        GroupAndClass groupAndClass1 = (GroupAndClass) selectGroupField.getSelectedItem();
        if (!groupAndClass1.getGroupName().equalsIgnoreCase("-Group-")) {
            response = new GroupAndClassServiceImpl().getClassDetails(groupAndClass1.getGroupId());
            setDefault_ClassTable();
            int i = 0;
            if (response.getStatus() == 1) {
                DefaultTableModel groupAndClassTableVariable = (DefaultTableModel) classTable.getModel();
                ArrayList<GroupAndClass> groupAndClassList = (ArrayList<GroupAndClass>) response.getData();
                for (GroupAndClass group1 : groupAndClassList) {
                    i++;
                    groupAndClassTableVariable.addRow(
                            new Object[]{
                                i, group1.getGroupName(), group1.getClassName(), group1.getClassId()
                            });
                }
            }
            classRecordFound.setText("No. of Class record found  " + i);
            System.out.println("yeh it reach here");
            classRecordFound.setForeground(new Color(153, 153, 0));
        } else {
            classRecordFound.setText("No. of Class record found  " + 0);
            System.out.println("yeh it also reach here");
            classRecordFound.setForeground(Color.red);
            setDefault_ClassTable();
        }
    }

    /**
     * method get the value of selected roe of the table
     *
     */
    public void getClassTableValueForUpdate() {
        classField.setText(String.valueOf(classTable.getValueAt(classTable.getSelectedRow(), 2)));
        classIdField.setText(String.valueOf(classTable.getValueAt(classTable.getSelectedRow(), 3)));
    }

    /**
     * This method update the class detail
     */
    public void updateClassDetails() {
        if (classTable.getSelectedColumnCount() > 0) {

            if (classField.getText() != null && classField.getText().trim().length() > 0) {
                response = new GroupAndClassServiceImpl().updateClassDetail(Integer.parseInt(classIdField.getText()), classField.getText());
                if (response.getStatus() == 1) {
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.INFORMATION_MESSAGE);
                    displayClassDetail();
                    resetClassFields();

                } else {
                    JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.WARNING_MESSAGE);
                    resetClassFields();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Enter class to update", "Response", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select row before update", "Response", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * used to delete a class detail from database
     */
    public void deleteClassDetails() {
        if (classTable.getSelectedColumnCount() > 0) {
            response = new GroupAndClassServiceImpl().deleteClassName(Integer.parseInt(classIdField.getText()));

            if (response.getStatus() == 1) {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.INFORMATION_MESSAGE);
                displayClassDetail();
                resetClassFields();

            } else {
                JOptionPane.showMessageDialog(this, response.getMessage(), "Response", JOptionPane.WARNING_MESSAGE);
                displayClassDetail();
                resetClassFields();
            }

        } else {
            JOptionPane.showMessageDialog(this, "Please select a row to perform delete operation", "Response", JOptionPane.WARNING_MESSAGE);
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

        jPanel5 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        groupTable = new javax.swing.JTable(){
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
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        jPanel1 = new javax.swing.JPanel();
        groupField = new javax.swing.JTextField();
        saveGroup = new javax.swing.JButton();
        updateGroup = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        deleteGroup = new javax.swing.JButton();
        resetGroup = new javax.swing.JButton();
        groupIdField = new javax.swing.JTextField();
        status = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        classTable = new javax.swing.JTable(){

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
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        jPanel2 = new javax.swing.JPanel();
        classField = new javax.swing.JTextField();
        saveClassDetail = new javax.swing.JButton();
        updateClass = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        selectGroupField = new javax.swing.JComboBox();
        deleteClass = new javax.swing.JButton();
        resetClassField = new javax.swing.JButton();
        classIdField = new javax.swing.JTextField();
        classRecordFound = new javax.swing.JLabel();

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 51)));
        jPanel5.setOpaque(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "GROUP WIZARD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Adobe Arabic", 2, 18), new java.awt.Color(123, 6, 6))); // NOI18N
        jPanel3.setOpaque(false);

        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setViewportBorder(createEmptyBorder());
        jScrollPane1.setBorder(createEmptyBorder());

        groupTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(123, 6, 6)));
        groupTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        groupTable.setToolTipText("All avaliable group");
        groupTable.setGridColor(new java.awt.Color(123, 6, 6));
        groupTable.setName("Group Details"); // NOI18N
        groupTable.setOpaque(false);
        groupTable.setRowHeight(20);
        groupTable.setRowMargin(2);
        groupTable.getTableHeader().setReorderingAllowed(false);
        groupTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                groupTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(groupTable);
        groupTable.getTableHeader().setBackground(new Color(123,6,6));
        groupTable.getTableHeader().setForeground(Color.white);
        groupTable.getTableHeader().setFont(new Font("SansSerif", Font.ITALIC, 14));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ADD GROUP", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 14), new java.awt.Color(123, 6, 6))); // NOI18N
        jPanel1.setOpaque(false);

        groupField.setBackground(new java.awt.Color(255, 255, 204));

        saveGroup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/save.png"))); // NOI18N
        saveGroup.setText("Save");
        saveGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveGroupActionPerformed(evt);
            }
        });

        updateGroup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/pencil-icon24.png"))); // NOI18N
        updateGroup.setText("Update");
        updateGroup.setEnabled(false);
        updateGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateGroupActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(123, 6, 6));
        jLabel1.setText("Group ");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        deleteGroup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/Delete-icon24.png"))); // NOI18N
        deleteGroup.setText("Delete");
        deleteGroup.setEnabled(false);
        deleteGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteGroupActionPerformed(evt);
            }
        });

        resetGroup.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/renew-icon24.png"))); // NOI18N
        resetGroup.setText("Reset");
        resetGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetGroupActionPerformed(evt);
            }
        });

        groupIdField.setEditable(false);
        groupIdField.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(groupIdField, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(groupField)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(saveGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(updateGroup)
                            .addComponent(resetGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(24, 24, 24))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupField, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(resetGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(saveGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(deleteGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(updateGroup, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addComponent(groupIdField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );

        status.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(status, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 11, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CLASS WIZARD", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Adobe Arabic", 2, 18), new java.awt.Color(123, 6, 6))); // NOI18N
        jPanel4.setOpaque(false);

        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
        jScrollPane2.setViewportBorder(createEmptyBorder());
        jScrollPane2.setBorder(createEmptyBorder());

        classTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(123, 6, 6)));
        classTable.setModel(new javax.swing.table.DefaultTableModel(
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
        classTable.setGridColor(new java.awt.Color(123, 6, 6));
        classTable.getTableHeader().setReorderingAllowed(false);
        classTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                classTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(classTable);
        classTable.setGridColor(new java.awt.Color(123, 6, 6));

        classTable.setOpaque(false);

        classTable.setRowHeight(19);

        classTable.setRowMargin(2);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ADD CLASS", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Times New Roman", 2, 14), new java.awt.Color(123, 6, 6))); // NOI18N
        jPanel2.setOpaque(false);

        classField.setBackground(new java.awt.Color(255, 255, 204));

        saveClassDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/save.png"))); // NOI18N
        saveClassDetail.setText("Save");
        saveClassDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveClassDetailActionPerformed(evt);
            }
        });

        updateClass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/pencil-icon24.png"))); // NOI18N
        updateClass.setText("Update");
        updateClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateClassActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("Group ");

        jLabel3.setFont(new java.awt.Font("Century", 2, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("Class");

        selectGroupField.setToolTipText("Select group for a class");
        selectGroupField.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                selectGroupFieldItemStateChanged(evt);
            }
        });

        deleteClass.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/Delete-icon24.png"))); // NOI18N
        deleteClass.setText("Delete");
        deleteClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteClassActionPerformed(evt);
            }
        });

        resetClassField.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/renew-icon24.png"))); // NOI18N
        resetClassField.setText("Reset");
        resetClassField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetClassFieldActionPerformed(evt);
            }
        });

        classIdField.setEditable(false);
        classIdField.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(classIdField, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(saveClassDetail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(deleteClass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(updateClass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(resetClassField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(selectGroupField, 0, 212, Short.MAX_VALUE)
                            .addComponent(classField))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(selectGroupField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(classField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(saveClassDetail, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(updateClass, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(classIdField, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(resetClassField, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteClass, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        classRecordFound.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(classRecordFound, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 394, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(classRecordFound, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 814, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(11, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 539, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void groupTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_groupTableMouseClicked
        getGroupTableValueForUpdate();
        updateGroup.setEnabled(true);
        deleteGroup.setEnabled(true);
    }//GEN-LAST:event_groupTableMouseClicked

    private void saveGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveGroupActionPerformed
        saveGroup();
    }//GEN-LAST:event_saveGroupActionPerformed

    private void updateGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateGroupActionPerformed
        updateGroupDetails();        // TODO add your handling code here:
    }//GEN-LAST:event_updateGroupActionPerformed

    private void deleteGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteGroupActionPerformed
        deleteGroup();
    }//GEN-LAST:event_deleteGroupActionPerformed

    private void resetGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetGroupActionPerformed
        resetGroupField();        // TODO add your handling code here:
    }//GEN-LAST:event_resetGroupActionPerformed

    private void classTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_classTableMouseClicked
        getClassTableValueForUpdate();           // TODO add your handling code here:
    }//GEN-LAST:event_classTableMouseClicked

    private void saveClassDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveClassDetailActionPerformed
        saveClass();
    }//GEN-LAST:event_saveClassDetailActionPerformed

    private void updateClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateClassActionPerformed
        updateClassDetails();
    }//GEN-LAST:event_updateClassActionPerformed

    private void selectGroupFieldItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectGroupFieldItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            displayClassDetail();
        }
    }//GEN-LAST:event_selectGroupFieldItemStateChanged

    private void deleteClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteClassActionPerformed
        deleteClassDetails();
    }//GEN-LAST:event_deleteClassActionPerformed

    private void resetClassFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetClassFieldActionPerformed
        resetClassFields();        // TODO add your handling code here:;
    }//GEN-LAST:event_resetClassFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField classField;
    private javax.swing.JTextField classIdField;
    private javax.swing.JLabel classRecordFound;
    private javax.swing.JTable classTable;
    private javax.swing.JButton deleteClass;
    private javax.swing.JButton deleteGroup;
    private javax.swing.JTextField groupField;
    private javax.swing.JTextField groupIdField;
    private javax.swing.JTable groupTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton resetClassField;
    private javax.swing.JButton resetGroup;
    private javax.swing.JButton saveClassDetail;
    private javax.swing.JButton saveGroup;
    private javax.swing.JComboBox selectGroupField;
    private javax.swing.JLabel status;
    private javax.swing.JButton updateClass;
    private javax.swing.JButton updateGroup;
    // End of variables declaration//GEN-END:variables
}
