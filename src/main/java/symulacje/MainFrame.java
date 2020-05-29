package symulacje;


import javax.swing.*;
import java.awt.*;


public class MainFrame extends JFrame  {

    private static MainFrame instance = null;

    JPanel rootPanel = new JPanel();
    JPanel upperPanel;
    JPanel simulationPanel;


    private MainFrame() {
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

    public static MainFrame getInstance() {
        if(instance == null)
            instance = new MainFrame();
        return instance;
    }

    private void resize() {
        int simulationPanelWidth = Params.boardLatitude * Params.cellDimension;
        int simulationPanelHeight = (Params.boardLongitude + 1) * Params.cellDimension;

        int initialPanelHeight = upperPanel.getHeight();

        this.setSize(new Dimension(simulationPanelWidth, simulationPanelHeight + initialPanelHeight));
    }

    public class UpperPanel extends JPanel {

        JTextField latitudeField;
        JTextField longitudeField;
        JTextField peopleAmountField;
        JTextField exitsAmountField;
        JTextField windSpeedField;
        JTextField windDirectionField;


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
            JLabel labelWindSpeed = new JLabel("Wind speed (km/h): ");
            JLabel labelWindDirection = new JLabel("Wind direction (degrees): ");


            latitudeField = new JTextField(20);
            longitudeField = new JTextField(20);
            peopleAmountField = new JTextField(20);
            exitsAmountField = new JTextField(20);
            windSpeedField = new JTextField(20);
            windDirectionField = new JTextField(20);
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
            initialPanel.add(labelWindSpeed, constraints);
            constraints.gridx = 1;
            initialPanel.add(windSpeedField, constraints);

            constraints.gridx = 0;
            constraints.gridy = 5;
            initialPanel.add(labelWindDirection, constraints);
            constraints.gridx = 1;
            initialPanel.add(windDirectionField, constraints);

            constraints.gridx = 0;
            constraints.gridy = 6;
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

                //for now
                Params.windDirection = Integer.parseInt(this.windDirectionField.getText());
                if( Params.windDirection==0 || Params.windDirection==180 || Params.windDirection==360) {
                    Params.windDirection *= (Math.PI / 180); //degrees to radians
                    Params.windSpeed = 0;
                }
                else{
                    Params.windDirection *= (Math.PI/180) ; //degrees to radians
                    Params.windSpeed = Integer.parseInt(this.windSpeedField.getText())*1000/3600D; //m/s
                }
                Params.xSpeed= Params.windSpeed *Math.cos(Params.windDirection);
                Params.ySpeed= Params.windSpeed*Math.sin(Params.windDirection);
                //System.out.println(Params.ySpeed + "  "+ Params.xSpeed);

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
