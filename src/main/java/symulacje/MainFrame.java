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
    JPanel upperPanel;
    JPanel simulationPanel;


    public MainFrame() {
        super("S Y M U L A C J E");

        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(true);

        rootPanel.setLayout(new BorderLayout());
        upperPanel = new UpperPanel();
        rootPanel.add(upperPanel, BorderLayout.NORTH);
        setContentPane(rootPanel);
        this.pack();
    }

    private void resize() {
        int simulationPanelWidth = Params.boardLongitude * Params.cellDimension;
        int simulationPanelHeight = (Params.boardLatitude + 1) * Params.cellDimension;

        int initialPanelHeight = upperPanel.getHeight();

        this.setSize(new Dimension(simulationPanelWidth, simulationPanelHeight + initialPanelHeight));
    }

    public class UpperPanel extends JPanel {

        JTextField latitudeField;
        JTextField longitudeField;
        JTextField peopleAmountField;
        JTextField exitsAmountField;

        Button submitButton;
        Button resumeButton;
        Button pauseButton;

        JPanel initialPanel;
        JPanel controlPanel;

        public UpperPanel() {
            // at the beginning initial panel is set, then after starting simulation
            // initial panel is removed and replaced by controlPanel, it happens when onSubmit() is called
            setInitialPanel();
        }

        private void setInitialPanel() {
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
            initialPanel = new JPanel();
            initialPanel.setLayout(new GridBagLayout());

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.WEST;
            constraints.insets = new Insets(10, 10, 10, 10);

            // add components to the panel
            constraints.gridx = 0;
            constraints.gridy = 0;
            initialPanel.add(labelLatitude, constraints);
            constraints.gridx = 1;
            initialPanel.add(latitudeField, constraints);

            constraints.gridx = 0;
            constraints.gridy = 1;
            initialPanel.add(labelLongitude, constraints);
            constraints.gridx = 1;
            initialPanel.add(longitudeField, constraints);

            constraints.gridx = 0;
            constraints.gridy = 2;
            initialPanel.add(labelPeople, constraints);
            constraints.gridx = 1;
            initialPanel.add(peopleAmountField, constraints);

            constraints.gridx = 0;
            constraints.gridy = 3;
            initialPanel.add(labelExits, constraints);
            constraints.gridx = 1;
            initialPanel.add(exitsAmountField, constraints);

            constraints.gridx = 0;
            constraints.gridy = 4;
            constraints.gridwidth = 3;
            constraints.anchor = GridBagConstraints.CENTER;

            submitButton.addActionListener(panel -> onSubmit());
            initialPanel.add(submitButton, constraints);

            // set border for the panel
            initialPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Enter Simulation Parameters"));

            // add the panel to this frame
            this.add(initialPanel);

            MainFrame.this.pack();
            MainFrame.this.setLocationRelativeTo(null);
        }


        private void setControlPanel() {
            this.removeAll();
            controlPanel = new JPanel();

            pauseButton = new Button("Pause");
            pauseButton.addActionListener(panel -> onPause());
            controlPanel.add(pauseButton);

            resumeButton = new Button("Resume");
            resumeButton.addActionListener(panel -> onResume());
            controlPanel.add(resumeButton);

            this.add(controlPanel);
            this.revalidate();
            this.repaint();
            MainFrame.this.pack();
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

        private void startSimulation() {
            Simulation.getInstance().start();

            simulationPanel = Board.getInstance();
            rootPanel.add(simulationPanel, BorderLayout.CENTER);
        }

        private void onSubmit() {
            this.getValues();
            this.setControlPanel();

            this.startSimulation();

            MainFrame.this.resize();
        }

        private void onPause() {
            Simulation.getInstance().animationThread.safeSuspend();
        }

        private void onResume() {
            Simulation.getInstance().animationThread.wakeup();
        }
    }

}
