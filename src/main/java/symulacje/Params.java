package symulacje;


import java.awt.*;


public class Params {

    // modifiable properties, need to be loaded at the beginning from the GUI
    public static int boardLatitude;
    public static int boardLongitude;
    public static int peopleAmount;
    public static int exitsAmount;

    public static double windSpeed;
    public static double windDirection;
    public static double xSpeed;
    public static double ySpeed;



    // constant properties
    public static final int peopleSpeed = 1;
    public static final int fireSpeed = 4;

    public static final int sleepInterval = 1000;

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
