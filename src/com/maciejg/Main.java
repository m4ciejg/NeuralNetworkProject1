package com.maciejg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Main extends JFrame {
    Paint paint;
    Board board;
    private ArrayList<Point> points = new ArrayList<Point>();

    public void clearWindow() {
        points.clear();
        repaint();
    }

    public class Paint extends JPanel implements MouseMotionListener {
        private int x,y;


        public Paint() {
            addMouseMotionListener(this);
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            points.add(new Point(x, y));
            repaint();
            System.out.println("X, Y: " + x + " " + y);
        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
            g2d.setColor(Color.BLACK);
            drawRectangles(g2d);
            drawLines(g2d);
        }

        private void drawRectangles(Graphics2D g2d) {
            int x, y;
            for (Point p : points) {
                x = (int) p.getX();
                y = (int) p.getY();
                g2d.fillRect(x, y, 10, 10);
            }
        }

        private void drawLines(Graphics2D g2d) {
            int w = getWidth();
            int h = getHeight();
            g2d.setColor(Color.RED);
            for(int i = 0; i < 8; i++){
                g2d.drawLine(w*i/8, 0, w*i/8,h);
                g2d.drawLine(0, h*i/8 ,w, h*i/8);
            }
        }
    }


    public class Board extends JPanel {

        public Board() {
            JButton button1 = new JButton("Uczenie");
            JButton button2 = new JButton("Clear");
            JButton button3 = new JButton("Dupsko");
            button2.addActionListener(e -> {
                clearWindow();
            });
            add(button1);
            add(button2);
            add(button3);
        }
    }


    public Main() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,400);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1,2));
        add(paint = new Paint());
        add(board = new Board());
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new Main();
        });

    }
}
