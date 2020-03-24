import java.util.ArrayList;

public class Person extends Entity {

    private Cell closestExit;

    public Person(Params.EntityType entityType) {
        super(entityType);
        this.setClosestExit();
    }

    public Person(Params.EntityType entityType, Cell startingCell) {
        super(entityType, startingCell);
        this.setClosestExit();
    }


    private void setClosestExit() {
        Board board = Board.getInstance();

        Cell[][] cells = board.getCells();
        ArrayList<Cell> exits = new ArrayList<>();

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
}
