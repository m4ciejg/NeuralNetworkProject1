package com.maciejg;

        import com.maciejg.gui.DrawingBoard;
        import com.maciejg.gui.LearningWindow;
        import com.maciejg.network.Neuron;
        import com.maciejg.network.Siec;
        import com.maciejg.network.Warstwa;

        import javax.swing.*;
        import java.awt.*;
        import java.util.ArrayList;
        import java.util.List;

public class Main extends JFrame {
    private DrawingBoard paint;
    private List pixelList;
    private List wynikowa;
    private Siec siec;
    private Neuron neuron;
    private Warstwa warstwa;
    private JButton button1;
    private JButton button3;
    private JButton button4;
    private JButton clearButton;
    private JButton rozpoznaj;
    private JRadioButton button_Z;
    private JRadioButton button_S;
    private JRadioButton button_2;
    private JLabel wyniktxt;
    private List<Integer> radiobool;
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
        ustawLayout();
        setOnClicks();

        int [] tab = new int [3];
        tab[0]=784; tab[1]=10; tab[2]=3;

        siec = new Siec(784,3, tab);

        setVisible(true);
    }

    private void ustawLayout(){
        setLayout(new GridLayout(1,2));
        JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayout(2,1));
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(3,1));
        JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayout(1,2));
        JPanel panel5 = new JPanel();
        panel5.setLayout(new GridLayout(1,2));
        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(1,4));
        add(paint = new DrawingBoard(400, 400, RESOLUTION));
        add(panel1);
        panel1.add(panel2);
        panel2.add(panel3);
        panel2.add(panel4);
        wyniktxt = new JLabel("X",SwingConstants.CENTER);
        panel1.add(wyniktxt);
        button1 = new JButton("Wczytaj");
        clearButton = new JButton("Wyczysc");
        button3 = new JButton("Naucz");
        button4 = new JButton("Zapisz");
        panel3.add(clearButton);
        panel3.add(panel5);
        panel5.add(button1);
        panel5.add(button4);
        //radio
        button_S = new JRadioButton("S");
        button_Z = new JRadioButton("Z");
        button_2 = new JRadioButton("2");
        ButtonGroup grupa = new ButtonGroup();
        grupa.add(button_2);
        grupa.add(button_S);
        grupa.add(button_Z);
        panel4.add(button3);
        panel4.add(button_S);
        panel4.add(button_2);
        panel4.add(button_Z);
        rozpoznaj = new JButton("Rozpoznaj");
        panel2.add(rozpoznaj);

    }
    private void setOnClicks() {
        button1.addActionListener(e -> {
            LearningWindow learningBoard = new LearningWindow();
            learningBoard.createFrame();
        });
        clearButton.addActionListener(e -> {
            clearWindow();
        });
            button3.addActionListener(e -> {
                radiobool = new ArrayList<Integer>();
                radiobool.add(0);
                radiobool.add(0);
                radiobool.add(0);
                if(button_S.isSelected()){
                    radiobool.set(0,1);
                    radiobool.set(1,0);
                    radiobool.set(2,0);
                }
                else if(button_2.isSelected()){
                    radiobool.set(0,0);
                    radiobool.set(1,1);
                    radiobool.set(2,0);
                }
                else if(button_Z.isSelected()){
                    radiobool.set(0,0);
                    radiobool.set(1,0);
                    radiobool.set(2,1);
                }
                for(Object i: radiobool){
                    System.out.println(i+" ");
                }

                pixelList = new ArrayList<Integer>();
                pixelList = paint.returnListOfPixels();
                int licznik = 0;
                for(Object i : pixelList) {
                    licznik++;
                    if(licznik % 28 ==0) System.out.println("");
                    System.out.print(i + " ");
                }
            });
        rozpoznaj.addActionListener(e -> {


            wyniktxt.setText("Moze kiedys znajdziemy poprawny wynik xD");
        });
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new Main();
        });
    }
}