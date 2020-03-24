public class Cell {
    public enum CellType {
        FLOOR, PERSON, WALL, FIRE, EXIT
    }

    private CellType type;
    private int xPosition;
    private int yPosition;

    public Cell(CellType type, int yPosition, int xPosition) {
        this.type = type;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public char getType() {
        return type.toString().charAt(0); //żeby się ładnie wyświetlało, bo FLOOR było dłuższe niż WALL
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }
}
