package symulacje;

import javafx.util.Pair;
import java.util.Arrays;
import java.util.List;

public class Params {

    public static final int boardLatitude = 15;
    public static final int boardLongitude = 15;

    public static final List <Pair <Integer, Integer> > exits = Arrays.asList(
            new Pair <> (2,0),
            new Pair<>(7, 0),
            new Pair<>(9, boardLatitude - 1),
            new Pair<>(12, boardLatitude - 1)
    );

    public static final int peopleAmount = 6;
    public static final int peopleSpeed = 1;
    public static final int fireSpeed = 3;

    public enum CellType {
        FLOOR, WALL, EXIT
    }

    public enum EntityType {
        PERSON, FIRE, SMOKE
    }

}
