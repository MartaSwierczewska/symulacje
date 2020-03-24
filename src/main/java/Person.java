import java.util.Random;

public class Person {

    private Cell currentCell;
    private int speed = 1;  // for now fixed value

    // for now let's randomize starting position of person
    public Person() {

        Random randomGenerator = new Random();
        int x = randomGenerator.nextInt(Params.boardLatitude);
        int y = randomGenerator.nextInt(Params.boardLongitude);

        // assign cell from board
        currentCell = Board.getInstance().getCell(x, y);
    }

    // another constructor in case we want spawn person on certain position
    public Person(int startPosX, int startPosY) {
        currentCell = Board.getInstance().getCell(startPosX, startPosY);
    }


}
