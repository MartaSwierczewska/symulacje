import java.util.ArrayList;

public class Simulation {

    private Board board;
    private ArrayList <Person> people;

    public long tickCounter = 0;

    public Simulation() {

        // new board object - setting latitude and longitude, init cell array
        board = Board.getInstance();
        board.setCellTypes();

        // creating exits
        board.addEmergencyExits(Params.exits);
        this.initPeople();

    }

    private void initPeople() {
        people = new ArrayList <Person> ();
        for(int i = 0; i < Params.peopleAmount; i++) {
            people.add(new Person());
        }
    }


    public void loop() {
        board.show();
        // simulation continues until every person exits the board
//        while(board.isPersonOnBoard()) {
////            tu funkcja obliczajaca najblizsze exit i przemieszczanie pozaru i zmieniajaca polozenie
////            jakies board.update()
////            i wtedy znowu board show
//
//            tickCounter++;
//            board.show();
//        }
    }
}
