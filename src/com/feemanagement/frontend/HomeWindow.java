/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.frontend;

import com.feemanagement.helper.FrameDecorator;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 *
 * @author Mandeep Singh
 */
public final class HomeWindow extends javax.swing.JFrame {

    static int month, day, hour, minute, second;
    static String monthVar, dayVar, hourVar, minuteVar, secondVar;

    /**
     * Creates new form HomeWindow
     */
    public HomeWindow() {
        initComponents();
        loggedUserName.setText("<html> <font color=\'blue\'>    " + FeeManagement.userName + "</font> </html>");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        JFrame jf = this;
        FrameDecorator.decorateFrame((JFrame) this);
        setCurrentDate();
        youAreHereStatus.setText("<html> <font color=\'blue\'>  > Home </font> </html>"
        );

    }

    /**
     * This method add different panel form to the main Panel
     *
     * @param jPanel
     * @param evt
     */
    public void addingPanelToContainerPanel(JPanel jPanel, java.awt.event.ActionEvent evt) {
        containerPanel.removeAll();
        if (evt.getSource() instanceof JMenuItem) {
            JMenuItem child = (JMenuItem) evt.getSource();
            JPopupMenu jpm = (JPopupMenu) child.getParent();
            JMenu parent = (JMenu) jpm.getInvoker();
            String parentVar = parent.getText().concat("    ");
            String childVar = ("    ").concat(child.getText());
            youAreHereStatus.setText("<html> <font color=\'blue\'>" + parentVar + '>' + childVar + "</font> </html>"
            );
        } else if (evt.getSource() instanceof JButton) {
            youAreHereStatus.setText(
                    "<html> <font color=\'blue\'>" + ((JButton) (evt.getSource())).getToolTipText() + "</font> </html>");
        }
        containerPanel.setLayout(new GridBagLayout());
        containerPanel.add(jPanel, new GridBagConstraints());
        containerPanel.add(jPanel);
        jPanel.setOpaque(false);
        jPanel.setVisible(true);
        containerPanel.validate();
        containerPanel.repaint();
    }

    /**
     * displaying the current date on menu bar
     */
    public void setCurrentDate() {

        Thread clock = new Thread() {
            @Override
            public void run() {
                for (;;) {
                    Calendar calendar = new GregorianCalendar();
                    month = calendar.get(Calendar.MONTH) + 1;
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                    monthVar = (month <= 9) ? ("0" + month) : ("" + month);
                    dayVar = (day <= 9) ? ("0" + day) : ("" + day);
                    //  date12.setText(calendar.get(Calendar.YEAR) + "-" + monthVar + "-" + dayVar);

                    date12.setText("<html> <font color=\'blue\'>" + calendar.get(Calendar.YEAR) + '-' + monthVar + '-' + dayVar + "</font> </html>"
                    );

                    hour = calendar.get(Calendar.HOUR);
                    minute = calendar.get(Calendar.MINUTE);
                    second = calendar.get(Calendar.SECOND);

                    hourVar = (hour <= 9) ? ("0" + hour) : (" " + hour);
                    minuteVar = (minute <= 9) ? ("0" + minute) : ("" + minute);
                    secondVar = (second <= 9) ? ("0" + second) : ("" + second);

                    //time12.setText("<html> < body >" + hourVar + ":" + minuteVar + ":" + secondVar + " <  / body > < / html >");
                    time12.setText(hourVar + ":" + minuteVar + ":" + secondVar);
                    time12.setText("<html> <font color=\'blue\'>" + hourVar + ':' + minuteVar + ':' + secondVar + "</font> </html>"
                    );

                    try {
                        sleep(1000);
                    } catch (Exception e) {
                    }
                }
            }
        };
        clock.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        containerPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        payFeeButton = new javax.swing.JButton();
        feeDetailButton = new javax.swing.JButton();
        allFeeDetailButton = new javax.swing.JButton();
        printButton = new javax.swing.JButton();
        logoutButton = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jLabel2 = new javax.swing.JLabel();
        date12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        time12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        loggedUserName = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        youAreHereStatus = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        groupAndClass = new javax.swing.JMenu();
        openGroup = new javax.swing.JMenuItem();
        openClass = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        openAddFeeDetail = new javax.swing.JMenuItem();
        allFeeDetails = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        openPayFee = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        openPrintReceipt = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Welcome To Fee Manager");

        containerPanel.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 3), javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 0, 0), null)));
        containerPanel.setForeground(new java.awt.Color(123, 6, 6));
        containerPanel.setName("Print Receipt"); // NOI18N
        containerPanel.setOpaque(false);

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/Calculator-Investment-700.jpg"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Century", 2, 48)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Fee Manager 1.0.0");

        javax.swing.GroupLayout containerPanelLayout = new javax.swing.GroupLayout(containerPanel);
        containerPanel.setLayout(containerPanelLayout);
        containerPanelLayout.setHorizontalGroup(
            containerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(containerPanelLayout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addGroup(containerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        containerPanelLayout.setVerticalGroup(
            containerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(68, 68, 68))
        );

        jToolBar1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);
        jToolBar1.setToolTipText("Logout");

        payFeeButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/payment-icon.png"))); // NOI18N
        payFeeButton.setToolTipText("Pay Fee");
        payFeeButton.setFocusable(false);
        payFeeButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        payFeeButton.setMaximumSize(new java.awt.Dimension(70, 55));
        payFeeButton.setPreferredSize(new java.awt.Dimension(70, 55));
        payFeeButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        payFeeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payFeeButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(payFeeButton);

        feeDetailButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/Windows-View-Detail-icon.png"))); // NOI18N
        feeDetailButton.setToolTipText("Fee Detail");
        feeDetailButton.setFocusable(false);
        feeDetailButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        feeDetailButton.setMaximumSize(new java.awt.Dimension(70, 55));
        feeDetailButton.setPreferredSize(new java.awt.Dimension(70, 55));
        feeDetailButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        feeDetailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                feeDetailButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(feeDetailButton);

        allFeeDetailButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/Actions-view-fullscreen-icon.png"))); // NOI18N
        allFeeDetailButton.setToolTipText("All Fee Details");
        allFeeDetailButton.setFocusable(false);
        allFeeDetailButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        allFeeDetailButton.setMaximumSize(new java.awt.Dimension(70, 55));
        allFeeDetailButton.setMinimumSize(new java.awt.Dimension(70, 55));
        allFeeDetailButton.setPreferredSize(new java.awt.Dimension(70, 55));
        allFeeDetailButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        allFeeDetailButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allFeeDetailButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(allFeeDetailButton);

        printButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/printer-icon (2).png"))); // NOI18N
        printButton.setToolTipText("Print Receipt");
        printButton.setFocusable(false);
        printButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        printButton.setMaximumSize(new java.awt.Dimension(70, 55));
        printButton.setPreferredSize(new java.awt.Dimension(70, 55));
        printButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(printButton);
        printButton.getAccessibleContext().setAccessibleName("Print Receipt");

        logoutButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/feemanagement/images/Logout-icon 48.png"))); // NOI18N
        logoutButton.setToolTipText("Logout");
        logoutButton.setFocusable(false);
        logoutButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        logoutButton.setMaximumSize(new java.awt.Dimension(70, 55));
        logoutButton.setPreferredSize(new java.awt.Dimension(70, 55));
        logoutButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });
        jToolBar1.add(logoutButton);

        jToolBar2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jToolBar2.setRollover(true);
        jToolBar2.setToolTipText("");
        jToolBar2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jToolBar2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel2.setText("  Date : ");
        jToolBar2.add(jLabel2);

        date12.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        date12.setForeground(new java.awt.Color(123, 6, 6));
        date12.setText("                            ");
        date12.setMaximumSize(new java.awt.Dimension(75, 14));
        date12.setPreferredSize(new java.awt.Dimension(75, 12));
        jToolBar2.add(date12);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel1.setText("  Time : ");
        jToolBar2.add(jLabel1);

        time12.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        time12.setForeground(new java.awt.Color(123, 6, 6));
        time12.setText("                            ");
        time12.setMaximumSize(new java.awt.Dimension(75, 14));
        time12.setPreferredSize(new java.awt.Dimension(75, 12));
        jToolBar2.add(time12);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel3.setText("  Username :  ");
        jToolBar2.add(jLabel3);

        loggedUserName.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        loggedUserName.setForeground(new java.awt.Color(123, 6, 6));
        loggedUserName.setText("                               ");
        loggedUserName.setMaximumSize(new java.awt.Dimension(75, 14));
        loggedUserName.setPreferredSize(new java.awt.Dimension(75, 14));
        jToolBar2.add(loggedUserName);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel4.setText("  You are here :  ");
        jToolBar2.add(jLabel4);

        youAreHereStatus.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        youAreHereStatus.setForeground(new java.awt.Color(123, 6, 6));
        youAreHereStatus.setText("                         ");
        youAreHereStatus.setMaximumSize(new java.awt.Dimension(200, 14));
        youAreHereStatus.setMinimumSize(new java.awt.Dimension(10, 14));
        youAreHereStatus.setPreferredSize(new java.awt.Dimension(200, 14));
        jToolBar2.add(youAreHereStatus);

        jMenuBar1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jMenuBar1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jMenuBar1.setPreferredSize(new java.awt.Dimension(88, 30));

        groupAndClass.setText("GroupAndClass");
        groupAndClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                groupAndClassActionPerformed(evt);
            }
        });

        openGroup.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.ALT_MASK));
        openGroup.setText("Add Group");
        openGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openGroupActionPerformed(evt);
            }
        });
        groupAndClass.add(openGroup);

        openClass.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.ALT_MASK));
        openClass.setText("Add Classes");
        openClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openClassActionPerformed(evt);
            }
        });
        groupAndClass.add(openClass);

        jMenuBar1.add(groupAndClass);

        jMenu1.setText("Fee Detail");

        openAddFeeDetail.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_MASK));
        openAddFeeDetail.setText("Add Fee Detail");
        openAddFeeDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openAddFeeDetailActionPerformed(evt);
            }
        });
        jMenu1.add(openAddFeeDetail);

        allFeeDetails.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        allFeeDetails.setText("All Fee Details");
        allFeeDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allFeeDetailsActionPerformed(evt);
            }
        });
        jMenu1.add(allFeeDetails);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Pay Fee");

        openPayFee.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK));
        openPayFee.setText("Pay Fee");
        openPayFee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openPayFeeActionPerformed(evt);
            }
        });
        jMenu2.add(openPayFee);

        jMenuBar1.add(jMenu2);

        jMenu4.setText("Print");

        openPrintReceipt.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.ALT_MASK | java.awt.event.InputEvent.SHIFT_MASK));
        openPrintReceipt.setText("Print Receipt");
        openPrintReceipt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openPrintReceiptActionPerformed(evt);
            }
        });
        jMenu4.add(openPrintReceipt);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(containerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(containerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE))
                .addContainerGap())
        );

        containerPanel.getAccessibleContext().setAccessibleName("");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void openAddFeeDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openAddFeeDetailActionPerformed
        addingPanelToContainerPanel(new FeeDetailFrontEnd(), evt);
    }//GEN-LAST:event_openAddFeeDetailActionPerformed

    private void openClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openClassActionPerformed
        addingPanelToContainerPanel(new GroupAndClassFrontEnd(), evt);
    }//GEN-LAST:event_openClassActionPerformed

    private void openGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openGroupActionPerformed
        addingPanelToContainerPanel(new GroupAndClassFrontEnd(), evt);
    }//GEN-LAST:event_openGroupActionPerformed

    private void openPayFeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openPayFeeActionPerformed
        addingPanelToContainerPanel(new PayFeeFrontEnd(), evt);
    }//GEN-LAST:event_openPayFeeActionPerformed

    private void openPrintReceiptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openPrintReceiptActionPerformed
        addingPanelToContainerPanel(new PrintFeeReciptFrontEnd(), evt);
    }//GEN-LAST:event_openPrintReceiptActionPerformed

    private void allFeeDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allFeeDetailsActionPerformed
        addingPanelToContainerPanel(new DisplayAllFeeDetailFrontEnd(), evt);
    }//GEN-LAST:event_allFeeDetailsActionPerformed

    private void groupAndClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_groupAndClassActionPerformed
        addingPanelToContainerPanel(new GroupAndClassFrontEnd(), evt);
    }//GEN-LAST:event_groupAndClassActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed

        if (JOptionPane.showConfirmDialog(rootPane, "Logout Confirmation") == 0) {
            FeeManagement.userName = "";
            this.dispose();
            /* Create and display the dialog */
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    LoginInterfaceFrontEnd dialog = new LoginInterfaceFrontEnd(new javax.swing.JFrame(), true);
                    dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e) {
                            System.exit(0);
                        }
                    });
                    dialog.setVisible(true);
                }
            });
        }
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButtonActionPerformed
        addingPanelToContainerPanel(new PrintFeeReciptFrontEnd(), evt);

    }//GEN-LAST:event_printButtonActionPerformed

    private void feeDetailButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_feeDetailButtonActionPerformed
        addingPanelToContainerPanel(new FeeDetailFrontEnd(), evt);
    }//GEN-LAST:event_feeDetailButtonActionPerformed

    private void payFeeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payFeeButtonActionPerformed
        addingPanelToContainerPanel(new PayFeeFrontEnd(), evt);
    }//GEN-LAST:event_payFeeButtonActionPerformed

    private void allFeeDetailButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allFeeDetailButtonActionPerformed
        addingPanelToContainerPanel(new DisplayAllFeeDetailFrontEnd(), evt);
    }//GEN-LAST:event_allFeeDetailButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(HomeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(HomeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(HomeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(HomeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HomeWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton allFeeDetailButton;
    private javax.swing.JMenuItem allFeeDetails;
    private javax.swing.JPanel containerPanel;
    private javax.swing.JLabel date12;
    private javax.swing.JButton feeDetailButton;
    private javax.swing.JMenu groupAndClass;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JLabel loggedUserName;
    private javax.swing.JButton logoutButton;
    private javax.swing.JMenuItem openAddFeeDetail;
    private javax.swing.JMenuItem openClass;
    private javax.swing.JMenuItem openGroup;
    private javax.swing.JMenuItem openPayFee;
    private javax.swing.JMenuItem openPrintReceipt;
    private javax.swing.JButton payFeeButton;
    private javax.swing.JButton printButton;
    private javax.swing.JLabel time12;
    private javax.swing.JLabel youAreHereStatus;
    // End of variables declaration//GEN-END:variables
}
