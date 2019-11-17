package com.maciejg.gui;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DrawingBoard extends CustomBoard implements MouseMotionListener, MouseListener {

    public DrawingBoard(int width, int height, int count) {
        super(width, height, count);

        addMouseListener(this);
        addMouseMotionListener(this);
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        paintSection(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        paintSection(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    private void paintSection(MouseEvent event) {
        //check if left mouse button is pressed
        if(SwingUtilities.isLeftMouseButton(event)) {
            for(Section s : sections) {
                if(event.getX() > s.getX() && event.getX() < s.getX() + s.getWidth() && event.getY() > s.getY() && event.getY() < s.getY() + s.getHeight()) {
                    s.setActive(true);
                }
            }
        }
        repaint();
    }

    public void clearBoard() {
        for(Section s : sections) {
            s.setActive(false);
        }
    }
}
