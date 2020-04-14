package symulacje;


public class Main {
    public static void main(String[] args) {
        Simulation simulation = Simulation.getInstance();
        simulation.initPeople();
        simulation.start();

        MainFrame mainFrame = new MainFrame();



    }
}
