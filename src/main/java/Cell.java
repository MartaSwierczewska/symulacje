import java.util.ArrayList;

public class Cell {

    private Params.CellType cellType;
    private int xPosition;
    private int yPosition;
    private ArrayList <Entity> entities = new ArrayList<>();


    public Cell(Params.CellType cellType, int yPosition, int xPosition) {
        this.cellType = cellType;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public Params.CellType getCellType() {
        return cellType;
    }

    public void setCellType(Params.CellType cellType) {
        this.cellType = cellType;
    }

    public int getPositionX() {
        return xPosition; }

    public void setPositionX(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getPositionY() {
        return yPosition;
    }

    public void setPositionY(int yPosition) {
        this.yPosition = yPosition;
    }

    public void setPosition(int xPosition, int yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public void addEntity(Entity entity) {
        this.entities.add(entity);
    }

    public boolean removeEntity(Entity entity) {
        return this.entities.remove(entity);
    }

    public ArrayList <Entity> getEntities() {
        return this.entities;
    }

    public double getDistanceTo(Cell cell) {
        int posX = cell.getPositionX();
        int posY = cell.getPositionY();

        int currentPosX = this.getPositionX();
        int currentPosY = this.getPositionY();

        return Math.sqrt(Math.pow(currentPosX - posX, 2) + Math.pow(currentPosY - posY, 2));
    }

    public ArrayList <Cell> getNeighbours() {
        ArrayList <Cell> neighbours = new ArrayList<>();

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != 0 && j != 0) {
                    int rx = this.getPositionX() + i;
                    int ry = this.getPositionY() + j;

                    if (rx >= 0 && ry >= 0 && rx < Params.boardLatitude && ry < Params.boardLongitude) {
                        neighbours.add(Board.getInstance().getCell(rx, ry));
                    }
                }
            }
        }

        return neighbours;

    }

}
