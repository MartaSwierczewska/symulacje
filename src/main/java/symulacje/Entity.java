package symulacje;

import java.awt.*;
import java.util.Random;

public abstract class Entity {

    protected Params.EntityType entityType;
    protected Cell currentCell;

    // additional variable that helps adjust speed of entities dynamically
    // the bigger it is, the slower entity will move
    // should not be less than 1
    protected int speedFactor = 1;
    protected double[] speedToNeighCells;


    // flag that helps determine if entity still take part in simulation
    protected boolean active = false;

    
    public Entity(Params.EntityType entityType) {
        Random randomGenerator = new Random();
        int posX = randomGenerator.nextInt(Params.boardLatitude-2)+1;
        int posY = randomGenerator.nextInt(Params.boardLongitude-2)+1;
        this.currentCell = Board.getInstance().getCell(posX, posY);
        this.currentCell.addEntity(this);
        this.entityType = entityType;
        this.active = true;
        this.setSpeedFactors();

    }

    public Entity(Params.EntityType entityType, Cell startingCell) {
        this.currentCell = startingCell;
        this.currentCell.addEntity(this);
        this.entityType = entityType;
        this.active = true;
        this.setSpeedFactors();
    }

    public void draw(Graphics2D graphics2D) {
        switch(entityType) {
            case PERSON:
                graphics2D.setColor(Params.EntityColor.PERSON);
                break;
            case FIRE:
                graphics2D.setColor(Params.EntityColor.FIRE);
                break;
            case SMOKE:
                graphics2D.setColor(Params.EntityColor.SMOKE);
                break;
        }

        graphics2D.fillOval(
                currentCell.getPositionX() * Params.cellDimension,
                currentCell.getPositionY() * Params.cellDimension,
                Params.cellDimension, Params.cellDimension
        );
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Params.EntityType getEntityType() {
        return entityType;
    }

    public void setEntityType(Params.EntityType entityType) {
        this.entityType = entityType;
    }

    public int getPositionX() { return currentCell.getPositionX(); }

    public int getPositionY() {
        return currentCell.getPositionY();
    }
    
    public Cell getCell() {
        return currentCell;
    }

    public void setCell(Cell cell) {
        this.currentCell = cell;
    }

    //for fire and maybe smoke
    public void setSpeedFactors(){
        double n=1, ne=1, e=1, se=1, s=1, sw=1, w=1, nw = 1;
        double xSpeed=Params.xSpeed*0.1;
        double ySpeed=Params.ySpeed*0.1;
        double[] sF = {nw, n, ne, w, e, sw, s, se};

        if (xSpeed > 0) {
            int[] toAdd = {0, 3, 5};
            int[] toSubtract = {2, 4, 7};
            for (int i = 0; i < toAdd.length; i++) {
                int ad = toAdd[i];
                int su = toSubtract[i];
                sF[ad] += speedFactor * xSpeed;
                sF[su] -= speedFactor * xSpeed;
                }
            }


        if (xSpeed < 0) {
            int[] toAdd = {2, 4, 7};
            int[] toSubtract = {0, 3, 5};
            for (int i = 0; i < toAdd.length; i++) {
                int ad = toAdd[i];
                int su = toSubtract[i];
                sF[ad] += speedFactor * Math.abs(xSpeed);
                sF[su] -= speedFactor * Math.abs(xSpeed);
            }
        }

        if (ySpeed > 0) {
            int[] toAdd = {5, 6, 7};
            int[] toSubtract = {0, 1, 2};
            for (int i = 0; i < toAdd.length; i++) {
                int ad = toAdd[i];
                int su = toSubtract[i];
                sF[ad] += speedFactor * ySpeed;
                sF[su] -= speedFactor * ySpeed;
            }
        }

        if (ySpeed < 0) {
            int[] toAdd = {0, 1, 2};
            int[] toSubtract = {5, 6, 7};
            for (int i = 0; i < toAdd.length; i++) {
                int ad = toAdd[i];
                int su = toSubtract[i];
                sF[ad] += speedFactor * Math.abs(ySpeed);
                sF[su] -= speedFactor * Math.abs(ySpeed);
            }
        }

        this.speedToNeighCells = sF;

    }


}
