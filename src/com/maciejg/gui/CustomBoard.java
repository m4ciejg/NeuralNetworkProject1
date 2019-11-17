package com.maciejg.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomBoard extends JPanel {
    public List<Section> sections;
    private int width;
    private int height;
    private int count;

    public CustomBoard(int w, int h, int count) {
        super();
        this.width = w;
        this.height = h;
        this.count = count;

        initSection();
    }

    private void initSection() {
        sections = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            for(int j =0; j < count; j++) {
                sections.add(new Section(j * (width / count), i * (height / count), width / count, height / count));
            }
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        generateSection(g);
        drawSections(g);
    }

    private void generateSection(Graphics g) {
        g.setColor(Color.BLACK);
        for(Section s : sections) {
            g.drawLine(0, s.getY(), width, s.getY());
            g.drawLine(s.getX(), 0, s.getX(), height);
        }
    }

    private void drawSections(Graphics g) {
        g.setColor(Color.BLACK);
        for(Section s : sections) {
            if(s.isActive()) {
                g.fillRect(s.getX(), s.getY(), s.getWidth(), s.getHeight());
            }
        }
    }
}
