/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.helper;

import com.feemanagement.util.PathConstants;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author 786
 */
public class FrameDecorator {

    public static void decorateFrame(JFrame jf) {
        ((JPanel) jf.getContentPane()).setOpaque(false);
        ImageIcon uno = new ImageIcon(jf.getClass().getResource(PathConstants.BACKGROUNG_IMAGE_PATH));
        JLabel fondo = new JLabel();
        fondo.setIcon(uno);
        jf.getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0, 0, uno.getIconWidth(), uno.getIconHeight());
    }
}
