package com.maciejg.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

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

    private void paintSection(MouseEvent e) {
        //check if left mouse button is pressed
        if(SwingUtilities.isLeftMouseButton(e)) {
            for(Section s : sections) {
                if (e.getX() > s.getX() && e.getX() < s.getX() + s.getWidth() && e.getY() > s.getY() && e.getY() < s.getY() + s.getHeight()) {
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

    public List returnListOfPixels() {
        List<Boolean> tempList = new ArrayList<>();
        for(Section s : sections) {
            if(s.isActive()) tempList.add(true);
            else tempList.add(false);
        }
        return tempList;
    }

    @Override
    public String toString() {
        return "DrawingBoard{" +
                "sections=" + sections +
                '}';
    }
}
