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
        rootPanel.add(initialPanel, BorderLayout.SOUTH);
        setContentPane(rootPanel);
        this.pack();
    }

    private void resize() {
        int simulationPanelWidth = Params.boardLongitude * Params.cellDimension;
        int simulationPanelHeight = (Params.boardLatitude + 1) * Params.cellDimension;
        this.setSize(new Dimension(simulationPanelWidth, simulationPanelHeight));
    }

    public class InitialPanel extends JPanel {

        JTextField latitudeField;
        JTextField longitudeField;
        JTextField peopleAmountField;
        JTextField exitsAmountField;

        Button submitButton;
        Button startButton;
        Button stopButton;


        public InitialPanel() {

            this.setLayout(new GridBagLayout());

            JLabel labelLatitude = new JLabel("Latitude: ");
            JLabel labelLongitude = new JLabel("Longitude: ");
            JLabel labelPeople = new JLabel("Amount of people: ");
            JLabel labelExits = new JLabel("Amount of exits: ");

            latitudeField = new JTextField(20);
            longitudeField = new JTextField(20);
            peopleAmountField = new JTextField(20);
            exitsAmountField = new JTextField(20);
            submitButton = new Button("Submit!");

            // create a new panel with GridBagLayout manager
            JPanel newPanel = new JPanel();
            newPanel.setLayout(new GridBagLayout());

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(10, 10, 10, 10);

            // add components to the panel
            constraints.gridx = 0;
            constraints.gridy = 0;
            newPanel.add(labelLatitude, constraints);
            constraints.gridx = 1;
            newPanel.add(latitudeField, constraints);

            constraints.gridx = 0;
            constraints.gridy = 1;
            newPanel.add(labelLongitude, constraints);
            constraints.gridx = 1;
            newPanel.add(longitudeField, constraints);

            constraints.gridx = 0;
            constraints.gridy = 2;
            newPanel.add(labelPeople, constraints);
            constraints.gridx = 1;
            newPanel.add(peopleAmountField, constraints);

            constraints.gridx = 0;
            constraints.gridy = 3;
            newPanel.add(labelExits, constraints);
            constraints.gridx = 1;
            newPanel.add(exitsAmountField, constraints);

            constraints.gridx = 0;
            constraints.gridy = 4;
            constraints.gridwidth = 3;
            constraints.anchor = GridBagConstraints.CENTER;

            submitButton.addActionListener(panel -> onSubmit());
            newPanel.add(submitButton, constraints);

            // set border for the panel
            newPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Enter Simulation Parameters"));

            // add the panel to this frame
            add(newPanel);

            pack();
            setLocationRelativeTo(null);
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

//        TODO: show start and stop buttons
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
            initialPanel.setVisible(false);
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
