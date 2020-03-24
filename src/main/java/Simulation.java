import java.io.IOException;
import java.util.ArrayList;
import static java.lang.Thread.sleep;

public class Simulation {

    private static Simulation simulationInstance = null;

    private Board board;

    public ArrayList <Entity> people;
    public long tickCounter = 0;


    public static Simulation getInstance() {
        if(simulationInstance == null)
            simulationInstance = new Simulation();
        return simulationInstance;
    }

    private Simulation() {
        this.initBoard();
        this.initPeople();
    }

    private void initBoard() {
        // new board object - setting latitude and longitude, init cell array
        board = Board.getInstance();
        board.setCellTypes();

        // creating exits
        board.addEmergencyExits(Params.exits);
    }

    private void initPeople() {
        people = new ArrayList <Entity> ();
        for(int i = 0; i < Params.peopleAmount; i++) {
            people.add(new Entity(Params.EntityType.PERSON));
        }
    }


    public void loop() throws InterruptedException, IOException {


        // simulation continues until every person exits the board
        while(board.isPersonOnBoard()) {
            tickCounter++;


            for(Entity person : people)
                person.runToExit();

            board.showBoard();
            board.showPeople();
            sleep(1000);

        }
    }
}