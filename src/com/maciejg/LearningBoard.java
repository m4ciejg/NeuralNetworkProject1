package com.maciejg;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;

public class LearningBoard {
    public static void createFrame()
    {
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame frame = new JFrame("Test");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setSize(800, 400);
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setOpaque(true);
                JPanel inputpanel = new JPanel();
                inputpanel.setLayout(new FlowLayout());
                JButton buttonLoad = new JButton("Load");
                JButton buttonLearn = new JButton("Learn");
                inputpanel.add(buttonLoad);
                inputpanel.add(buttonLearn);
                panel.add(inputpanel);
                frame.getContentPane().add(BorderLayout.CENTER, panel);
                frame.setVisible(true);
                frame.setResizable(false);
            }
        });
    }
}
