/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.helper;

import com.feemanagement.dto.Response;
import com.feemanagement.frontend.PrintFeeReciptFrontEnd;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

/**
 *
 * @author Mandeep Singh
 */
public class PrintButtonEditor extends DefaultCellEditor {

    protected JButton editButton;
    private String label;
    private boolean clicked;
    private JTable jTableVar = null;
    Response response;

    public PrintButtonEditor(JCheckBox jCheckBox) {
        super(jCheckBox);

        editButton = new JButton();
        editButton.setBounds(15, 15, 15, 15);
        editButton.setPreferredSize(new Dimension(3, 3));
        editButton.setOpaque(true);
        editButton.setContentAreaFilled(false); //to make the content area transparent
        editButton.setBorderPainted(false);
        // When button is clicked
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
            }
        });
    }

    //override a couple of method
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : "hii  ";
        // set text to button, set clicked to true, then return the button object
        if (value != null) {
            if (value instanceof Icon) {
                editButton.setName("print");
                editButton.setIcon((Icon) value);

            } else {
                editButton.setText("");
            }
        } else {
            editButton.setIcon((Icon) value);
        }
        jTableVar = table;
        clicked = true;
        return editButton;
    }

    //If button cell value change, if ckicked that is
    @Override
    public Object getCellEditorValue() {
        if (clicked) {
            String clickTable = "2";
            if (jTableVar != null) {
                clickTable = jTableVar.getName();

                if (editButton.getName().equalsIgnoreCase("print")) {
                    JOptionPane.showMessageDialog(editorComponent, "Report generation may take some time.");
                    PrintFeeReciptFrontEnd.printingFeeReceipt();
                }
            }
        }
        //set it to false now that its clicked
        clicked = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        // set clicked to false first
        clicked = false;
        return super.stopCellEditing(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped(); //To change body of generated methods, choose Tools | Templates.
    }
}
