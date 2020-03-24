import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class Entity {
    private Params.EntityType entityType;
    

    private Cell currentCell;
    private Cell closestExit;
    private int speed;

    
    public Entity(Params.EntityType entityType) {
        Random randomGenerator = new Random();
        int posX = randomGenerator.nextInt(Params.boardLatitude);
        int posY = randomGenerator.nextInt(Params.boardLongitude);
        this.currentCell = Board.getInstance().getCell(posX, posY);
        this.currentCell.addEntity(this);
        this.entityType = entityType;
        this.setClosestExit();
    }

    public Entity(Params.EntityType entityType, Cell startingCell) {
        this.currentCell = startingCell;
        this.currentCell.addEntity(this);
        this.entityType = entityType;
        this.setClosestExit();
    }

    public Params.EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(Params.EntityType entityType) {
        this.entityType = entityType;
    }

    public int getPositionX() {
        return currentCell.getPositionX(); }


    public int getPositionY() {
        return currentCell.getPositionY();
    }
    
    public Cell getCell() {
        return currentCell;
    }

    public void setCell(Cell cell) {
        this.currentCell = cell;
    }

    public void runToExit() {

        if(Simulation.getInstance().tickCounter % Params.peopleSpeed == 0) {
            currentCell.removeEntity(this);
            currentCell = getNextHoop();
            currentCell.addEntity(this);
        }

        if(currentCell.getCellType() == Params.CellType.EXIT) {
            currentCell.removeEntity(this);
        }

    }

    private Cell getNextHoop() {
        Cell nextHoop = currentCell;
        double minDistance = currentCell.getDistanceTo(closestExit);

        for(Cell neighbour : currentCell.getNeighbours()) {
            if(neighbour.getDistanceTo(closestExit) < minDistance) {
                minDistance = neighbour.getDistanceTo(closestExit);
                nextHoop = neighbour;
            }
        }
        return nextHoop;
    }



    private void setClosestExit() {
        Board board = Board.getInstance();

        Cell[][] cells = board.getCells();
        ArrayList <Cell> exits = new ArrayList<>();

        for(int i = 0; i < Params.boardLongitude; i++) {
            for(int j = 0; j < Params.boardLatitude; j++) {
                if(cells[i][j].getCellType() == Params.CellType.EXIT)
                    exits.add(cells[i][j]);
            }
        }

        double minDistance = Double.MAX_VALUE;
        for(Cell e : exits) {
            double tmp = currentCell.getDistanceTo(e);

            if(minDistance > tmp) {
                minDistance = tmp;
                closestExit = e;
            }
        }
    }
}
