package com.maciejg;

import com.maciejg.gui.CustomBoard;
import com.maciejg.gui.DrawingBoard;
import com.maciejg.gui.LearningWindow;
import com.maciejg.network.Siec;
import com.maciejg.network.Warstwa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main extends JFrame {
    private DrawingBoard paint;
    private List<boolean[]> pixelList;
    private List<boolean[]> radiobool;
    private boolean[] pixbool;
    private int lettersCount;
    private int pixelCount;
    private Siec siec;
    private Warstwa warstwa;
    private JButton wczytaj;
    private JButton button3;
    private JButton zapisz;
    private JButton clearButton;
    private JButton rozpoznaj;
    private JButton buttonnaucz;
    private JRadioButton button_Z;
    private JRadioButton button_S;
    private JRadioButton button_2;
    private JLabel wyniktxt;
    private final int RESOLUTION = 14;
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
        int [] tab=new int [3];
        tab[0]=195;
        tab[1]=10;
        tab[2]=3;
        siec=new Siec(195,3,tab);
        radiobool = new ArrayList<>();
        pixelList = new ArrayList<>();
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
        panel4.setLayout(new GridLayout(1,2));
        JPanel panel6= new JPanel();
        panel6.setLayout(new GridLayout(1,2));
        JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayout(1,3));
        add(paint = new DrawingBoard(400, 400, RESOLUTION));
        add(panel1);
        panel1.add(panel2);
        panel2.add(panel3);
        panel2.add(panel4);
        wyniktxt = new JLabel("X",SwingConstants.CENTER);
        panel1.add(wyniktxt);
        wczytaj = new JButton("Wczytaj");
        clearButton = new JButton("Wyczysc");
        button3 = new JButton("Dodaj do ciagu");
        zapisz = new JButton("Zapisz");
        buttonnaucz = new JButton("Naucz");
        panel3.add(clearButton);
        panel3.add(panel5);
        panel5.add(wczytaj);
        panel5.add(zapisz);
        //radio
        button_S = new JRadioButton("S");
        button_Z = new JRadioButton("Z");
        button_2 = new JRadioButton("2");
        ButtonGroup grupa = new ButtonGroup();
        grupa.add(button_2);
        grupa.add(button_S);
        grupa.add(button_Z);
        panel4.add(panel6);
        panel4.add(panel7);
        panel6.add(button3);
        panel6.add(buttonnaucz);
        panel7.add(button_S);
        panel7.add(button_2);
        panel7.add(button_Z);
        rozpoznaj = new JButton("Rozpoznaj");
        panel2.add(rozpoznaj);

    }
    private void setOnClicks() {

        wczytaj.addActionListener(e -> {
            loadFile(e);
        });
        zapisz.addActionListener(e -> {
            saveFile(e);
        });
        clearButton.addActionListener(e -> {
            clearWindow();
        });
        button3.addActionListener(e -> {
            pixbool=convert(paint.returnListOfPixels().toArray());
            pixelList.add(pixbool);
            radiobool.add(radiobtab());

            System.out.println(radiobool);
            System.out.println(pixelList);
        });
        buttonnaucz.addActionListener(e -> {
            //Przetwarzanie danych zapisanych w CU z logicznych na double
            ArrayList<double[]> lettersSequenceDouble = new ArrayList<double[]>();
            ArrayList<double[]> matrixSequenceDouble = new ArrayList<double[]>();

            for(int i = 0; i< radiobool.size(); i++) {
                boolean[] lettersBool = radiobool.get(i);
                boolean[] matrixBool = pixelList.get(i);
                double[] lettersArray = new double[lettersBool.length];
                double[] matrixArray = new double[matrixBool.length];

                for (int j = 0; j < lettersArray.length; j++) {
                    lettersArray[j] = lettersBool[j] ? 1.0 : 0.0;
                }
                for (int j = 0; j < matrixArray.length; j++) {
                    matrixArray[j] = matrixBool[j] ? 1.0 : 0.0;
                }

                lettersSequenceDouble.add(lettersArray);
                matrixSequenceDouble.add(matrixArray);
            }

            siec.ucz_z_ciagu(matrixSequenceDouble, lettersSequenceDouble);

        });

        rozpoznaj.addActionListener(e -> {
            wyniktxt.setText("x");
        });
        zapisz.addActionListener(e -> {
        });
    }
    private boolean[] convert(Object[] array){
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (boolean) array[i];
        }
        return result;
    }
    public boolean[] radiobtab() {
        if(button_S.isSelected())
            return new boolean[] { true, false, false };
        else if(button_2.isSelected())
            return new boolean[] { false, true, false };
        else if(button_Z.isSelected())
            return new boolean[] { false, false, true };
        else
            return null;

    }
    private void loadFile(ActionEvent e) {
        try {
            final JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(Main.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                radiobool = new ArrayList<boolean[]>();
                pixelList = new ArrayList<boolean[]>();

                File file = fc.getSelectedFile();

                String path = file.getAbsolutePath();

                FileInputStream fstream = new FileInputStream(path);
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;

                lettersCount = Integer.parseInt(br.readLine());
                pixelCount = Integer.parseInt(br.readLine());

                while ((strLine = br.readLine()) != null) {
                    String[] tokens = strLine.split(" ");

                    if (radiobool.size() == pixelList.size()) {

                        boolean[] letter = new boolean[lettersCount];

                        for (int i = 0; i < lettersCount; i++) {
                            letter[i] = tokens[i].equals("0") ? false : true;
                        }

                        radiobool.add(letter);
                    } else {

                        boolean[] matrix = new boolean[pixelCount];

                        for (int i = 0; i < pixelCount; i++) {
                            matrix[i] = tokens[i].equals("0") ? false : true;
                        }

                        pixelList.add(matrix);
                    }
                }
                br.close();
            }

        } catch (Exception ex) {

        }
    }
    private void saveFile(ActionEvent e) {
        try {
            if(radiobool == null || pixelList == null)
                return;

            final JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(Main.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {

                File file = fc.getSelectedFile();

                String path = file.getAbsolutePath();

                PrintWriter writer = new PrintWriter(path, "UTF-8");

                writer.println(radiobool.get(0).length);
                writer.println(pixelList.get(0).length);

                for (int i = 0; i < radiobool.size(); i++) {

                    boolean[] currentLetter = radiobool.get(i);
                    boolean[] currentMatrix = pixelList.get(i);

                    StringBuilder sb = new StringBuilder();

                    for (boolean b : currentLetter) {
                        sb.append((b ? "1" : "0") + " ");
                    }

                    writer.println(sb);
                    sb = new StringBuilder();

                    for (boolean b : currentMatrix) {
                        sb.append((b ? "1" : "0") + " ");
                    }

                    writer.println(sb);
                }

                writer.close();
            }
        }
        catch(Exception ex) {

        }
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new Main();
        });
    }
}
