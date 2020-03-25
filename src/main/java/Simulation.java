import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Thread.sleep;

public class Simulation {

    private static Simulation simulationInstance = null;

    private Board board;

    public ArrayList <Person> people;
    public ArrayList <Fire> firePlaces;

    public long tickCounter = 0;


    public static Simulation getInstance() {
        if(simulationInstance == null)
            simulationInstance = new Simulation();
        return simulationInstance;
    }

    private Simulation() {
        this.initBoard();
        this.initPeople();
        this.initFire();
    }

    private void initBoard() {
        // new board object - setting latitude and longitude, init cell array
        board = Board.getInstance();
        board.setCellTypes();

        // creating exits
        board.addEmergencyExits(Params.exits);
    }

    private void initPeople() {
        people = new ArrayList<>();
        for(int i = 0; i < Params.peopleAmount; i++) {
            people.add(new Person(Params.EntityType.PERSON));
        }
    }

    private void initFire(){
        firePlaces = new ArrayList<>();
        firePlaces.add(new Fire(Params.EntityType.FIRE));
    }


    private List<Fire> detectFirePlaces(){
        return board.getFirePlaces();
    }

    public void loop() throws InterruptedException, IOException {

        // simulation continues until every person exits the board
        while(board.isPersonOnBoard()) {
            tickCounter++;

            for(Person person : people)
                person.runToExit();

            for(Fire fire : this.detectFirePlaces())
                fire.spread();

            board.showPeople();




            sleep(1000);

        }
    }
}
