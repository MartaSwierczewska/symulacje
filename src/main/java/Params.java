import javafx.util.Pair;
import java.util.Arrays;
import java.util.List;

public class Params {

    public static final int boardLatitude = 10;
    public static final int boardLongitude = 10;

    public static final List <Pair <Integer, Integer> > exits = Arrays.asList(
            new Pair<Integer, Integer>(2,0),
            new Pair <Integer, Integer> (7,0),
            new Pair <Integer, Integer> (4,4)
    );

    public static final int peopleAmount = 3;

}
