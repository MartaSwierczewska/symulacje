package symulacje;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {

    Board mainPanel = Board.getInstance();
    JPanel startPanel = new JPanel();

    JTextArea latitudeField;
    JTextArea longitudeField;
    JTextArea peopleAmountField;

    public MainFrame() {
        super("S Y M U L A C J E");

        this.setVisible(true);
        this.setSize(Params.boardLatitude * Params.cellDimension, (Params.boardLongitude + 1) * Params.cellDimension);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startPanel.setLayout(null);
        add(startPanel);

        JLabel text = new JLabel("Wprowadź parametry wejściowe:");
        text.setBounds(10,10,1000,25);
        text.setFont(new Font("Courier New", (Font.BOLD | Font.ITALIC), 15));
        startPanel.add(text);

        JLabel boardLatitude  = new JLabel("Szerokość tablicy:");
        latitudeField = new JTextArea();
        boardLatitude.setBounds(10,45,200,25);
        latitudeField.setBounds(10,80,150,20);
        startPanel.add(boardLatitude);
        startPanel.add(latitudeField);

        JLabel boardLongitude = new JLabel("Długość tablicy:");
        longitudeField = new JTextArea();
        boardLongitude.setBounds(10,115,200,25);
        longitudeField.setBounds(10,150,150,20);
        startPanel.add(boardLongitude);
        startPanel.add(longitudeField);

        JLabel peopleAmount = new JLabel("Ilość ludzi:");
        peopleAmountField = new JTextArea();
        peopleAmount.setBounds(10,185,200,25);
        peopleAmountField.setBounds(10,220,150,20);
        startPanel.add(peopleAmount);
        startPanel.add(peopleAmountField);

        Button button = new Button("kliknij");
        button.setBounds(10,255,100,25);
        button.addActionListener(this);
        startPanel.add(button);

        startPanel.setVisible(true);
        this.setContentPane(startPanel);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Params.boardLatitude = Integer.parseInt(this.latitudeField.getText());
        Params.boardLongitude = Integer.parseInt(this.longitudeField.getText());
        Params.peopleAmount = Integer.parseInt(this.peopleAmountField.getText());

        this.setContentPane(mainPanel);
    }
}
