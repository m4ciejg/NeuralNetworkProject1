package com.maciejg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Main extends JFrame {
    Paint paint;
    Board board;

    public class Paint extends JPanel implements MouseListener{
        private int x,y;
        private ArrayList<Point> points = new ArrayList<Point>();


        public Paint() {
            addMouseListener(this);
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            points.add(new Point(x, y));
            repaint();
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
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
            g2d.setColor(Color.BLACK);
            drawRectangles(g2d);
        }

        private void drawRectangles(Graphics2D g2d) {
            int x, y;
            for (Point p : points) {
                x = (int) p.getX();
                y = (int) p.getY();
                g2d.fillRect(x, y, 10, 10);
            }
        }

    }


    public class Board extends JPanel {

        public Board() {
            JButton button1 = new JButton("Uczenie");
            JButton button2 = new JButton("Dupa");
            JButton button3 = new JButton("Dupsko");
            add(button1);
            add(button2);
            add(button3);
        }
    }


    public Main() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,600);
        setLayout(new GridLayout(1,2));
        add(paint = new Paint());
        add(board = new Board());
        System.out.println("dupa");
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new Main();
        });

    }
}
