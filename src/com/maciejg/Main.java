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
    private static final int firstLayer=30;
    private static final int hidden=15;
    private List<boolean[]> pixelList;
    private List<boolean[]> radiobool;
    private ArrayList<boolean[]> testLettersSequence;
    private ArrayList<boolean[]> testMatrixSequence;
    private boolean[] pixbool;
    private int lettersCount;
    private int pixelCount;
    private int testLettersCount;
    private int testPixelCount;
    private Siec siec;
    private Warstwa warstwa;
    private JButton wczytaj;
    private JButton button3;
    private JButton zapisz;
    private JButton clearButton;
    private JButton rozpoznaj;
    private JButton buttonnaucz;
    private JButton loadtest;
    private JButton testuj;
    private JRadioButton button_Z;
    private JRadioButton button_S;
    private JRadioButton button_2;
    private JLabel wyniktxt;
    private final int RESOLUTION = 14;
    public int learnCounter=0;
    private String learnString="Ciag uczacy: ";
    private JLabel info;
    private JLabel learnlabel;
    private JLabel rozpoznana;
    private JLabel litera;
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
        tab[0]=firstLayer;
        tab[1]=hidden;
        tab[2]=3;
        if(!button_Z.isSelected() || !button_2.isSelected() || !button_S.isSelected()) button3.setEnabled(false);
        else button3.setEnabled(true);

        siec=new Siec(196,3,tab);
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
        panel3.setLayout(new GridLayout(1,4));
        JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayout(1,2));
        JPanel panel6= new JPanel();
        panel6.setLayout(new GridLayout(1,2));
        JPanel panel7 = new JPanel();
        panel7.setLayout(new GridLayout(1,3));
        JPanel panel8= new JPanel();
        panel8.setLayout(new GridLayout(1,2));

        JPanel panel9 = new JPanel();
        panel9.setLayout(new GridLayout(3,1));
        JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayout(1,2));
        JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayout(1,2));
        add(paint = new DrawingBoard(400, 400, RESOLUTION));
        add(panel1);
        panel1.add(panel2);
        panel2.add(panel3);
        panel2.add(panel4);
        panel1.add(panel9);
        wyniktxt = new JLabel("",SwingConstants.CENTER);

        wczytaj = new JButton("Wczyt CU");
        clearButton = new JButton("Czysc");
        button3 = new JButton("Dodaj");
        zapisz = new JButton("Zapisz");
        buttonnaucz = new JButton("Naucz");
        loadtest = new JButton("Wczyt CT");
        testuj = new JButton("Testuj...");
        info = new JLabel("Wczytaj lub dodaj ciąg",SwingConstants.CENTER);
        learnlabel = new JLabel("Ciag uczacy: ", SwingConstants.CENTER);
        rozpoznana = new JLabel("",SwingConstants.CENTER);
        litera = new JLabel("",SwingConstants.CENTER);
        litera.setFont(new Font("Arial", Font.BOLD, 60));
        panel3.add(clearButton);
        panel3.add(wczytaj);
        panel3.add(loadtest);
        panel3.add(zapisz);
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
        panel2.add(panel8);
        panel8.add(rozpoznaj);
        panel8.add(info);
        panel9.add(panel10);
        panel10.add(testuj);
        panel10.add(learnlabel);
        panel9.add(panel11);
        panel11.add(rozpoznana);
        panel11.add(litera);
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
            upLearnCounter();
        });
        button_S.addActionListener(e -> {
            if(button_S.isSelected()) button3.setEnabled(true);
        });
        button_Z.addActionListener(e -> {
            if(button_Z.isSelected()) button3.setEnabled(true);
        });
        button_2.addActionListener(e -> {
            if(button_2.isSelected()) button3.setEnabled(true);
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
            showLearnCounter();
            siec.ucz_z_ciagu(matrixSequenceDouble, lettersSequenceDouble);
            info.setText("Siec nauczona");
        });
        loadtest.addActionListener(e -> {
            testFile(e);
        });
        rozpoznaj.addActionListener(e -> {
                boolean[] letterBoolMatrix =convert(paint.returnListOfPixels().toArray());
                double[] inputLetterPix = boolArrayToDouble(letterBoolMatrix);
                double[] recognizedLetter = siec.oblicz_wyjscie(inputLetterPix);
                letterArrToLetter(recognizedLetter);
        });
        testuj.addActionListener(e -> {
            info.setText("Rozpoczynam testowanie...");
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

            int [] wynik = siec.testuj_z_ciagu(matrixSequenceDouble, lettersSequenceDouble);

            info.setText("Rozpoznano "+Integer.toString(wynik[0])+" z "+Integer.toString(wynik[1]));
        });
    }
    private void letterArrToLetter(double[] source) {
        int outCount = 0;
        int outIndex = -1;

        for (int i = 0; i < source.length; i++) {
            if(source[i] > 0.9) {
                outCount++;
                outIndex = i;
            }
        }

        //Tylko 1 litera mo�e by� pod�wietlona
        if(outCount != 1)
            outIndex = -1;

        switch (outIndex) {
            case 0:
                litera.setFont(new Font("Arial", Font.BOLD, 60));
                litera.setText("S");
                break;
            case 1:
                litera.setFont(new Font("Arial", Font.BOLD, 60));
                litera.setText("2");
                break;
            case 2:
                litera.setFont(new Font("Arial", Font.BOLD, 60));
                litera.setText("Z");
                break;

            default:
                litera.setFont(new Font("Arial",Font.BOLD, 15));
                litera.setText("Bledny znak");
                break;
        }
    }
    private double[] boolArrayToDouble(boolean[] source) {
        double[] result = new double[source.length];
        for (int i = 0; i < source.length; i++) {
            result[i] = source[i] ? 1 : 0;
        }

        return result;
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
        info.setText("Wybierz plik...");
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
                info.setText("Wczytywanie zakonczone");
            }

        } catch (Exception ex) {

        }
        int [] tab=new int [3];
        tab[0]=firstLayer;
        tab[1]=hidden;
        tab[2]=3;

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
        learnCounter = matrixSequenceDouble.size();
        showLearnCounter();
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
    private void testFile(ActionEvent e) {
        try {
            final JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(Main.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                testLettersSequence = new ArrayList<boolean[]>();
                testMatrixSequence = new ArrayList<boolean[]>();

                File file = fc.getSelectedFile();

                String path = file.getAbsolutePath();

                FileInputStream fstream = new FileInputStream(path);
                DataInputStream in = new DataInputStream(fstream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;

                testLettersCount = Integer.parseInt(br.readLine());
                testPixelCount = Integer.parseInt(br.readLine());

                while ((strLine = br.readLine()) != null)   {
                    String[] tokens = strLine.split(" ");

                    if(testLettersSequence.size() == testMatrixSequence.size()){

                        boolean[] letter = new boolean[testLettersCount];

                        for (int i = 0; i < testLettersCount; i++) {
                            letter[i] = tokens[i].equals("0") ? false : true;
                        }

                        testLettersSequence.add(letter);
                    }
                    else {

                        boolean[] matrix = new boolean[testPixelCount];

                        for (int i = 0; i < testPixelCount; i++) {
                            matrix[i] = tokens[i].equals("0") ? false : true;
                        }

                        testMatrixSequence.add(matrix);
                    }
                }
                br.close();
            }
        }
        catch(Exception ex) {
        }

        //Przetwarzanie danych zapisanych w CU z logicznych na double
        ArrayList<double[]> lettersSequenceDouble = new ArrayList<double[]>();
        ArrayList<double[]> matrixSequenceDouble = new ArrayList<double[]>();

        for(int i = 0; i< testLettersSequence.size(); i++) {
            boolean[] lettersBool = testLettersSequence.get(i);
            boolean[] matrixBool = testMatrixSequence.get(i);
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

        int [] wynik = siec.testuj_z_ciagu(matrixSequenceDouble, lettersSequenceDouble);
        info.setText("Rozpoznano "+wynik[0]+" z "+wynik[1]);

    }
    private void upLearnCounter() {
        learnCounter+=1;
        learnlabel.setText(learnString+Integer.toString(learnCounter));

    }



    private void showLearnCounter() {
        learnlabel.setText(learnString+Integer.toString(learnCounter));

    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            new Main();
        });
    }
}
