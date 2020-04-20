package symulacje;

import java.util.ArrayList;
import java.util.List;

public class Person extends Entity {

    private Cell closestExit;
    private int personSpeed;

    public Person(Params.EntityType entityType) {
        super(entityType);
        this.setClosestExit();
        this.personSpeed=Params.peopleSpeed;
    }

    public Person(Params.EntityType entityType, Cell startingCell) {
        super(entityType, startingCell);
        this.setClosestExit();
        this.personSpeed=Params.peopleSpeed;
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

        boolean notSafe = true;

        do {
            double minDistance = Double.MAX_VALUE;
            for (Cell e : exits) {
                double tmp = currentCell.getDistanceTo(e);

                if (minDistance > tmp) {
                    minDistance = tmp;
                    closestExit = e;
                }
            }
            if(!this.isPathSafe(closestExit)){
                if(exits.contains(closestExit)) exits.remove(closestExit);
                if(exits.isEmpty()) System.out.println("Somebody will die!:(");
            } else {notSafe = false;}
        } while(notSafe && !exits.isEmpty());
    }

    public void setSpeedByNumberOfPeopleAround(){
        List<Cell> neighbours  = currentCell.getNeighbours();
        int peopleCounter=0;
        for(Cell cell : neighbours){
            if(cell.getEntities().contains(Params.EntityType.PERSON)){
                peopleCounter++;
            }
        }
        if(peopleCounter/neighbours.size()>0.2){
            personSpeed*=2;
        }
    }

    private boolean isNextHoopExit(Cell cell){
        return (cell.getCellType()== Params.CellType.EXIT);
    }

    public void runToExit() {
        setSpeedByNumberOfPeopleAround();
        if(Simulation.getInstance().tickCounter % personSpeed == 0) {
            currentCell.removeEntity(this);
            currentCell = getNextHoop();
            currentCell.addEntity(this);
        }

        if(currentCell.getCellType() == Params.CellType.EXIT) {
            currentCell.removeEntity(this);
        }
    }

    public boolean isPathSafe(Cell exit){
        ArrayList <Cell> fireCells = new ArrayList<>();
        ArrayList <Cell> newFireCells = new ArrayList<>();

        Cell position = new Cell(currentCell.getCellType(), currentCell.getPositionY(), currentCell.getPositionX());
        Cell nextHoop = new Cell(currentCell.getCellType(), currentCell.getPositionY(), currentCell.getPositionX());

        for(Fire fire : Simulation.getInstance().firePlaces) fireCells.add(fire.currentCell);

        long tickCounter = 0;
        double minDistance = currentCell.getDistanceTo(exit);

        while(true){
            tickCounter++;
            for(Cell neighbour : position.getNeighbours()) {
                if(neighbour.getDistanceTo(exit) < minDistance) {
                    minDistance = neighbour.getDistanceTo(exit);
                    nextHoop = neighbour;
                }
            }

            if(tickCounter % Params.fireSpeed == 0){
                for(Cell cell : fireCells) {
                    for (Cell neighbour : cell.getNeighbours()) {
                        if (fireCells.contains(neighbour) || newFireCells.contains(neighbour)) continue;
                        newFireCells.add(neighbour);
                    }
                }
                fireCells.addAll(newFireCells);
            }

            if( newFireCells.contains(nextHoop) || newFireCells.contains(exit)) return false;

            if(nextHoop!=exit){
                position = nextHoop;
                newFireCells.clear();
            }
            else break;
        }
        return true;
    }

    private Cell getNextHoop() {
        Cell nextHoop = currentCell;
        double minDistance = currentCell.getDistanceTo(closestExit);

        for(Cell neighbour : currentCell.getNeighbours()) {
            if(neighbour.getDistanceTo(closestExit) < minDistance &&
                    (neighbour.getCellType() == Params.CellType.FLOOR && neighbour.getEntities().size()==0) ||
                    (neighbour.getCellType() == Params.CellType.EXIT)){
                minDistance = neighbour.getDistanceTo(closestExit);
                nextHoop = neighbour;
            }
        }
        return nextHoop;
    }
}
