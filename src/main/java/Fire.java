
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
                neighbour.addEntity(this);
                Simulation.getInstance().firePlaces.add(new Fire(Params.EntityType.FIRE, neighbour));
            }
        }
    }




}
