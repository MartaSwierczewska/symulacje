package symulacje;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Simulation simulation = Simulation.getInstance();

        try {
            simulation.loop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
