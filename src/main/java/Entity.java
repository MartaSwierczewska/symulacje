import java.util.Random;

public abstract class Entity {

    protected Params.EntityType entityType;
    protected Cell currentCell;
    protected int speed;


    
    public Entity(Params.EntityType entityType) {
        Random randomGenerator = new Random();
        int posX = randomGenerator.nextInt(Params.boardLatitude);
        int posY = randomGenerator.nextInt(Params.boardLongitude);
        this.currentCell = Board.getInstance().getCell(posX, posY);
        this.currentCell.addEntity(this);
        this.entityType = entityType;

    }

    public Entity(Params.EntityType entityType, Cell startingCell) {
        this.currentCell = startingCell;
        this.currentCell.addEntity(this);
        this.entityType = entityType;
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








}
