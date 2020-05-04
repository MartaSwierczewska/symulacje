package symulacje;


public class Main {
    public static void main(String[] args) {
        Params.boardLongitude=30;
        Params.boardLatitude=30;
        Params.peopleAmount=30;
        System.out.println(Params.boardLatitude+"\n"+Params.boardLatitude+"\n"+Params.peopleAmount);

        Simulation simulation = Simulation.getInstance();
        simulation.initPeople();
        simulation.start();

        MainFrame mainFrame = new MainFrame();
    }
}
