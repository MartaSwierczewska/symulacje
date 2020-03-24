public class Main {
    public static void main(String[] args) {
        Simulation simulation = Simulation.getInstance();

        try {
            simulation.loop();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
