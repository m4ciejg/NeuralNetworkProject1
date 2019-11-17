package com.maciejg;

import com.maciejg.gui.DrawingBoard;
import com.maciejg.gui.LearningWindow;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    private DrawingBoard paint;
    private Board board;
    private JLabel jLabelZ;
    private  JLabel jLabel2;
    private JLabel jLabelS;
    private List pixelList;
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
            button1.addActionListener(e -> {
                LearningWindow learningBoard = new LearningWindow();
                learningBoard.createFrame();
            });
            JButton clearButton = new JButton("Clear");
            clearButton.addActionListener(e -> {
                clearWindow();
            });
            JButton button3 = new JButton("Train");
            button3.addActionListener(e -> {
                pixelList = new ArrayList<Integer>();
                pixelList = paint.returnListOfPixels();
                int licznik = 0;
                for(Object i : pixelList) {
                    licznik++;
                    if(licznik % 28 ==0) System.out.println("");
                    System.out.print(i + " ");
                }
            });

            add(button1);
            add(clearButton);
            add(button3);

            jLabelS = new JLabel("S: ");
            jLabel2 = new JLabel("2: ");
            jLabelZ = new JLabel("Z: ");

            add(jLabel2);
            add(jLabelS);
            add(jLabelZ);
        }
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new Main();
        });
    }
}
