package symulacje;


public class Fire extends Entity {
    public long birthTic;

    public Fire(Params.EntityType entityType) {
        super(entityType);
        this.birthTic=Simulation.getInstance().tickCounter;
    }

    public Fire(Params.EntityType entityType, Cell cell) {
        super(entityType, cell);
        this.birthTic=Simulation.getInstance().tickCounter;
    }

    public void spread(){
        //neighbour's index in neighbour arraylist corresponds to index of its spreading speed direction factor in factors array
            for(Cell neighbour : currentCell.getNeighbours()) {

                if(currentCell.getNeighbours().size()==8) {
                    int factorIndex = currentCell.getNeighbours().indexOf(neighbour);
                    double x = (Simulation.getInstance().tickCounter-this.birthTic) / (Params.fireSpeed * this.speedToNeighCells[factorIndex]);

                    if (neighbour.getCellType() == Params.CellType.FLOOR
                            && x >= 1) {
                        neighbour.addEntity(this);
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


                    double x = (Simulation.getInstance().tickCounter-this.birthTic) / (Params.fireSpeed * neighFactor);

                    if (neighbour.getCellType() == Params.CellType.FLOOR
                            && x >= 1) {
                        neighbour.addEntity(this);
                    }

                }
        }
    }





}
