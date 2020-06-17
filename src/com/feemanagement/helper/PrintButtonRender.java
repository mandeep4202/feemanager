/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */

package com.feemanagement.helper;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Mandeep Singh
 */
public class PrintButtonRender extends JButton implements TableCellRenderer{

    public PrintButtonRender() {
  setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        if (value == null) {
            this.setIcon((Icon) value);
        } else if (value instanceof Icon) {
            this.setText("");
            this.setIcon((Icon) value);
        } else {
            this.setText("");
        }
    
        return this;
    }
}
