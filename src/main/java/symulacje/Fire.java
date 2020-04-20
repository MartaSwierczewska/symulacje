package symulacje;


public class Fire extends Entity {

    public Fire(Params.EntityType entityType) {
        super(entityType);
    }

    public Fire(Params.EntityType entityType, Cell cell) {
        super(entityType, cell);
    }

    public void spread(){
        if(Simulation.getInstance().tickCounter % Params.fireSpeed == 0) {
            for(Cell neighbour : currentCell.getNeighbours()) {
                if(neighbour.getCellType()== Params.CellType.FLOOR){
                    neighbour.addEntity(this);
                }
            }
        }
    }




}
