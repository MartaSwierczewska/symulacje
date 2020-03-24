import javafx.util.Pair;
import java.util.Arrays;
import java.util.List;

public class Params {

    public static final int boardLatitude = 15;
    public static final int boardLongitude = 15;

    public static final List <Pair <Integer, Integer> > exits = Arrays.asList(
            new Pair <Integer, Integer> (2,0),
            new Pair <Integer, Integer> (7,0)
//            new Pair <Integer, Integer> (9,4)
    );

    public static final int peopleAmount = 3;
    public static final int peopleSpeed = 1;

    public enum CellType {
        FLOOR, WALL, EXIT
    }

    public enum EntityType {
        PERSON, FIRE, SMOKE
    }

}