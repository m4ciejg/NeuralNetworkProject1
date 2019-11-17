package com.maciejg.gui;

import com.maciejg.utils.FileUtils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class LearningWindow extends JPanel {
    public static final String IMAGE_FILE_EXT = "idx3-ubyte";
    private JButton buttonLoad;
    private JButton buttonLearn;
    private JProgressBar trainingProgressBar;

    public LearningWindow() {
        initComponent();
        initListener();
    }

    //open additional window to train network
    public void createFrame()
    {
        EventQueue.invokeLater(() -> {
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
            inputpanel.add(buttonLoad);
            inputpanel.add(buttonLearn);
            inputpanel.add(trainingProgressBar);
            trainingProgressBar.setMinimum(0);
            trainingProgressBar.setMaximum(100);
            panel.add(inputpanel);
            frame.getContentPane().add(BorderLayout.CENTER, panel);
            frame.setVisible(true);
            frame.setResizable(false);
        });
    }

    private void initComponent() {
        buttonLoad = new JButton("Load");
        buttonLearn = new JButton("Learn");
        trainingProgressBar = new JProgressBar();
    }

    public void initListener() {
        buttonLoad.addActionListener(e -> {
            File f = FileUtils.chooseFile(LearningWindow.this,"XD", IMAGE_FILE_EXT);
            if(f != null) {
                System.out.println("dupsko");
            }
        });
    }
}
