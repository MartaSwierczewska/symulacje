package symulacje;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Arrays;

public class MainFrame extends JFrame  {

    JPanel rootPanel = new JPanel();
    JPanel initialPanel;
    JPanel simulationPanel;





    public MainFrame() {
        super("S Y M U L A C J E");

        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);

        rootPanel.setLayout(new BorderLayout());
        initialPanel = new InitialPanel();
        rootPanel.add(initialPanel, BorderLayout.NORTH);
        setContentPane(rootPanel);
        this.pack();
    }

    private void resize() {
        int initialPanelHeight = initialPanel.getHeight();
        int simulationPanelWidth = Params.boardLongitude * Params.cellDimension;
        int simulationPanelHeight = (Params.boardLatitude + 1) * Params.cellDimension;

        this.setSize(new Dimension(simulationPanelWidth, simulationPanelHeight + initialPanelHeight));
    }


    public class InitialPanel extends JPanel {

        JTextArea latitudeField;
        JTextArea longitudeField;
        JTextArea peopleAmountField;
        JTextArea exitsAmountField;

        Button submitButton;
        Button startButton;
        Button stopButton;


        public InitialPanel() {

//            JLabel text = new JLabel("Wprowadź parametry wejściowe:");
//            text.setBounds(10,10,1000,25);
//            text.setFont(new Font("Courier New", (Font.BOLD | Font.ITALIC), 15));
//            this.add(text);
//
//            JLabel boardLatitude  = new JLabel("Szerokość tablicy:");
//            latitudeField = new JTextArea();
//            boardLatitude.setBounds(10,45,200,25);
//            latitudeField.setBounds(10,80,150,20);
//            this.add(boardLatitude);
//            this.add(latitudeField);
//
//            JLabel boardLongitude = new JLabel("Długość tablicy:");
//            longitudeField = new JTextArea();
//            boardLongitude.setBounds(10,115,200,25);
//            longitudeField.setBounds(10,150,150,20);
//            this.add(boardLongitude);
//            this.add(longitudeField);
//
//            JLabel peopleAmount = new JLabel("Ilość ludzi:");
//            peopleAmountField = new JTextArea();
//            peopleAmount.setBounds(10,185,200,25);
//            peopleAmountField.setBounds(10,220,150,20);
//            this.add(peopleAmount);
//            this.add(peopleAmountField);

            // TODO: tylko w ramach testow
            latitudeField = new JTextArea("30");
            this.add(latitudeField);
            longitudeField = new JTextArea("30");
            this.add(longitudeField);
            peopleAmountField = new JTextArea("30");
            this.add(peopleAmountField);
            exitsAmountField = new JTextArea("5");
            this.add(exitsAmountField);

            submitButton = new Button("Submit!");
            submitButton.addActionListener(panel -> onSubmit());
            this.add(submitButton);

        }

        private void getValues() {
            try {
                Params.boardLatitude = Integer.parseInt(this.latitudeField.getText());
                Params.boardLongitude = Integer.parseInt(this.longitudeField.getText());
                Params.peopleAmount = Integer.parseInt(this.peopleAmountField.getText());
                Params.exitsAmount = Integer.parseInt(this.exitsAmountField.getText());
            } catch (NumberFormatException e) {
                System.out.println("Illegal arguments!");
                System.exit(1);
            }
        }

        private void disableInitialFields() {
            latitudeField.setEnabled(false);
            longitudeField.setEnabled(false);
            peopleAmountField.setEnabled(false);
            exitsAmountField.setEnabled(false);

            submitButton.setEnabled(false);
        }

        private void setStartStopButtons() {
            stopButton = new Button("Stop!");
            stopButton.addActionListener(panel -> onStop());
            this.add(stopButton);

            startButton = new Button("Start!");
            startButton.addActionListener(panel -> onStart());
            this.add(startButton);
        }

        private void onSubmit() {
            this.getValues();
            this.disableInitialFields();
            this.setStartStopButtons();

            Simulation.getInstance().start();

            simulationPanel = Board.getInstance();
            rootPanel.add(simulationPanel, BorderLayout.CENTER);

            MainFrame.this.resize();
        }

        private void onStop() {
            // System.out.println("Stop called!");
            Simulation.getInstance().animationThread.safeSuspend();
        }

        private void onStart() {
            // System.out.println("Start called!");
            Simulation.getInstance().animationThread.wakeup();
        }
    }

}
