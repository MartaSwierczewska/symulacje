import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Board board = new Board(5,10);
        board.create();
        board.fillWithValues();


        List<Pair<Integer, Integer>> emergencyExits = Arrays.asList( new Pair<>(2,0), new Pair<>(7,0), new Pair<>(4,4));
        board.addEmergencyExit(emergencyExits);



        board.show();
    }
}
