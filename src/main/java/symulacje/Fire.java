package symulacje;


import javafx.util.Pair;

import java.util.ArrayList;
import javafx.util.Pair;

public class Fire extends Entity {


    public Fire(Params.EntityType entityType) {
        super(entityType);
    }

    public Fire(Params.EntityType entityType, Cell cell) {
        super(entityType, cell);

    }

    public void spread(){
        //neighbour's index in neighbour arraylist corresponds to index of its spreading speed direction factor in factors array
            for(Cell neighbour : this.currentCell.getNeighbours()) {
                Long burnTime = Simulation.cellsWithBurnTime.get(this.currentCell);

                if(this.currentCell.getNeighbours().size()==8) {
                    int factorIndex = this.currentCell.getNeighbours().indexOf(neighbour);
                    double x = (Simulation.getInstance().tickCounter-burnTime.longValue()) / (Params.fireSpeed * this.speedToNeighCells[factorIndex]);

                    if (neighbour.getCellType() == Params.CellType.FLOOR
                            && x >= 1) {
                        new Fire(Params.EntityType.FIRE, neighbour);
                    }

                }
                //cells on border - shorter neighbour's arraylist
                else{
                    int xCurr=currentCell.getPositionX();
                    int yCurr=currentCell.getPositionY();
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
                    double x = (Simulation.getInstance().tickCounter-burnTime.longValue()) / (Params.fireSpeed * neighFactor);

                    if (neighbour.getCellType() == Params.CellType.FLOOR
                            && x >= 1) {
                       new Fire(Params.EntityType.FIRE, neighbour);
                    }

                }
        }
    }





}
