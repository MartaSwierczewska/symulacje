package symulacje;

import javafx.util.Pair;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Params {

    public static final int boardLatitude = 30;
    public static final int boardLongitude = 30;

    public static final List <Pair <Integer, Integer> > exits = Arrays.asList(
            new Pair<>(2,0),
            new Pair<>(7, 0),
            new Pair<>(9, boardLatitude - 1),
            new Pair<>(12, boardLatitude - 1)
    );

    public static final int peopleAmount = 80;
    public static final int peopleSpeed = 1;
    public static final int fireSpeed = 4;

    public static final int sleepInterval = 500;

    public enum CellType {
        FLOOR, WALL, EXIT
    }

    public static class CellColor {
        public static final Color FLOOR = Color.LIGHT_GRAY;
        public static final Color EXIT = Color.RED;
        public static final Color WALL = Color.DARK_GRAY;
    }

    public static final int cellDimension = 20;

    public enum EntityType {
        PERSON, FIRE, SMOKE
    }

    public static class EntityColor {
        public static final Color PERSON = Color.GREEN;
        public static final Color FIRE = Color.ORANGE;
        public static final Color SMOKE = Color.BLACK;
    }

}
