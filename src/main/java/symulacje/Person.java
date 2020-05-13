package symulacje;

import java.util.ArrayList;
import java.util.HashMap;

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
            if(!this.isPathSafe(closestExit)) {
                if(exits.contains(closestExit))
                    exits.remove(closestExit);

                if(exits.isEmpty())
                    System.out.println("Somebody will die!:(");
            } else {
                notSafe = false;
            }
        } while(notSafe && !exits.isEmpty());
    }

    private void setSpeedFactor() {
        // for now speed factor will depend on amount of neighbors
        this.speedFactor = (int) currentCell.getNeighbours().stream().filter(cell ->
                cell.getEntities().stream().anyMatch(entity ->
                        entity.getEntityType() == Params.EntityType.PERSON)).count() + 1;
    }

    public void runToExit() {
        this.setSpeedFactor();

        if(Simulation.getInstance().tickCounter % (Params.peopleSpeed * this.speedFactor) == 0 && this.isActive()) {
            currentCell.removeEntity(this);
            currentCell = getNextHoop();
            currentCell.addEntity(this);
        }
    }

    public boolean isPathSafe(Cell c){
        ArrayList <Cell> fireCells = new ArrayList<>();
        ArrayList <Cell> newFireCells = new ArrayList<>();

        Cell exit = c;
        Cell position = new Cell(currentCell.getCellType(), currentCell.getPositionY(), currentCell.getPositionX());
        Cell nextHoop = new Cell(currentCell.getCellType(), currentCell.getPositionY(), currentCell.getPositionX());

        for(Fire fire : Simulation.getInstance().firePlaces) fireCells.add(fire.currentCell);

        long tickCounter = 0;
        double minDistance = currentCell.getDistanceTo(exit);
        HashMap< Cell, Long> tempCellsWithBurnTime = new HashMap<>();

        //predict fire spreading to return information about path safety
        while(true){
            tickCounter++;
            for(int i=0;i<fireCells.size();i++) {
                if (!tempCellsWithBurnTime.containsKey(fireCells.get(i))) tempCellsWithBurnTime.put(fireCells.get(i), new Long(tickCounter));
            }

                for(Cell neighbour : position.getNeighbours()) {
                    if(neighbour.getDistanceTo(exit) < minDistance) {
                    minDistance = neighbour.getDistanceTo(exit);
                    nextHoop = neighbour;
                }
            }

            for(Cell cell : fireCells) {
            for(Cell neighbour : cell.getNeighbours()) {
                Long burntime = tempCellsWithBurnTime.get(cell);

                if(cell.getNeighbours().size()==8) {
                    int factorIndex = cell.getNeighbours().indexOf(neighbour);
                    double x = (tickCounter-burntime.longValue()) / (Params.fireSpeed * this.speedToNeighCells[factorIndex]);

                    if (neighbour.getCellType() == Params.CellType.FLOOR
                            && x >= 1) {
                        newFireCells.add(neighbour);
                    }


                }
                //cells on border - shorter neighbour's arraylist
                else{
                    int xCurr=cell.getPositionX();
                    int yCurr=cell.getPositionY();
                    int xNeigh=neighbour.getPositionX();
                    int yNeigh=neighbour.getPositionY();
                    double neighFactor=0;
                    int pos=0;

                    if(xNeigh>xCurr){
                        if(yNeigh>yCurr) {
                            pos=7;
                        }
                        else {
                            pos=2;
                        }
                    }
                    if(xNeigh<xCurr){
                        if(yNeigh>yCurr) {
                            pos=5;
                        }
                        else {
                            pos=0;
                        }
                    }
                    if(xNeigh==xCurr){
                        if(yNeigh>yCurr) {
                            pos=6;
                        }
                        else {
                            pos=1;
                        }
                    }
                    if(yNeigh==yCurr){
                        if(xNeigh>xCurr) {
                            pos=4;
                        }
                        else {
                            pos=3;
                        }
                    }

                    neighFactor=this.speedToNeighCells[pos];


                    double x = (tickCounter-burntime.longValue()) / (Params.fireSpeed * neighFactor);

                    if (neighbour.getCellType() == Params.CellType.FLOOR
                            && x >= 1) {
                        newFireCells.add(neighbour);
                    }

                }
            }
        }

            if(newFireCells.contains(nextHoop) || newFireCells.contains(exit)) return false;

            if(nextHoop!=exit){
                fireCells.addAll(newFireCells);
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
            // ignore cells that are not exit or floor (people can't walk on walls)
            if((neighbour.getCellType() != Params.CellType.FLOOR && neighbour.getCellType() != Params.CellType.EXIT))
                continue;

            // ignore cells that are already occupied
            if(neighbour.getEntities().stream().anyMatch(e -> e.getEntityType() == Params.EntityType.PERSON && e.isActive()))
                continue;

            if(neighbour.getDistanceTo(closestExit) < minDistance) {
                minDistance = neighbour.getDistanceTo(closestExit);
                nextHoop = neighbour;
            }
        }
        return nextHoop;
    }
}