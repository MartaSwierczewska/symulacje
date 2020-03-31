package symulacje;

import javax.swing.*;

public class MainFrame extends JFrame {

    Board mainPanel = Board.getInstance();

    public MainFrame() {
        super("S Y M U L A C J E");

        this.setVisible(true);
        this.setSize(Params.boardLatitude * Params.cellDimension, (Params.boardLongitude + 1) * Params.cellDimension);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setContentPane(mainPanel);
    }

}
