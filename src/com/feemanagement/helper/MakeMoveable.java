/*
 *  Copyright (c) 2015, FeeManagement and/or its affiliates. All rights reserved.
 */
package com.feemanagement.helper;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 *
 * @author 786
 */
public class MakeMoveable {

    private static final Point point = new Point();

    public MakeMoveable(JDialog jfObject) {
        final JDialog jf = jfObject;
        jf.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });
        jf.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point p = jf.getLocation();
                jf.setLocation(p.x + e.getX() - point.x,
                        p.y + e.getY() - point.y);
            }
        });

    }

    public void moveWindow(JFrame jfObject) {
        final JFrame jf = jfObject;
        jf.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                point.x = e.getX();
                point.y = e.getY();
            }
        });
        jf.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = jf.getLocation();
                jf.setLocation(p.x + e.getX() - point.x,
                        p.y + e.getY() - point.y);
            }
        });

    }
}
