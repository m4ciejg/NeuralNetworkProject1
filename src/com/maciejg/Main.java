package com.maciejg;

import com.maciejg.gui.DrawingBoard;
import com.maciejg.gui.LearningWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Main extends JFrame {
    private DrawingBoard paint;
    private Board board;
    private ArrayList<Point> points = new ArrayList<Point>();
    private ArrayList<Point> clearBoard = new ArrayList<>();
    private final int RESOLUTION = 28;

    private void clearWindow() {
        paint.clearBoard();
        repaint();
    }

    public Main() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800,400);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1,2));
        add(paint = new DrawingBoard(400, 400, RESOLUTION));
        add(board = new Board());
        setVisible(true);
    }

    public class Board extends JPanel {
        public Board() {
            JButton button1 = new JButton("Uczenie");
            JButton clearButton = new JButton("Clear");
            clearButton.addActionListener(e -> {
                clearWindow();
            });
            JButton button3 = new JButton("Dupsko");
            button1.addActionListener(e -> {
                LearningWindow learningBoard = new LearningWindow();
                learningBoard.createFrame();
            });
            add(button1);
            add(clearButton);
            add(button3);
        }
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new Main();
        });

    }
}
