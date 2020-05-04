package symulacje;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

// Board can be singleton class since we have one board in our simulation :)
public class Board extends JPanel {

    private static Board boardInstance = null;
    private Cell[][] cells;

    private Board() {
        this.cells = new Cell[Params.boardLongitude][Params.boardLatitude];
        this.initCells();
        this.initExits();

    }

    public static Board getInstance() {
        if(boardInstance == null)
            boardInstance = new Board();
        return boardInstance;
    }

    private void initExits() {
        Random randomGenerator = new Random();
        int posX = 0, posY = 0;

        for(int i = 0; i < Params.exitsAmount; i++) {
            // randomly choosing on which wall exit should be
            switch (randomGenerator.nextInt(4)) {
                case 0: // north
                    posY = 0;
                    posX = randomGenerator.nextInt(Params.boardLatitude);
                    break;
                case 1: // east
                    posY = randomGenerator.nextInt(Params.boardLongitude);
                    posX = Params.boardLatitude - 1;
                    break;
                case 2: // south
                    posY = Params.boardLongitude - 1;
                    posX = randomGenerator.nextInt(Params.boardLatitude);
                    break;
                case 3: // west
                    posY = randomGenerator.nextInt(Params.boardLongitude);
                    posX = 0;
                    break;
            }
            cells[posY][posX].setCellType(Params.CellType.EXIT);
        }
    }

    private void initCells() {
        for(int r = 0; r < Params.boardLongitude; r++) {
            for (int c = 0; c < Params.boardLatitude; c++) {
                if(r == 0 || c == 0 || r == Params.boardLongitude - 1 || c == Params.boardLatitude - 1) {
                    cells[r][c] = new Cell(Params.CellType.WALL, r, c);
                } else {
                    cells[r][c] = new Cell(Params.CellType.FLOOR, r, c);
                }
            }
        }
    }


    public boolean isPersonOnBoard() {
        for(int r = 0; r < Params.boardLongitude; r++) {
            for (int c = 0; c < Params.boardLatitude; c++) {
                if(cells[r][c].getEntities().stream().anyMatch(e ->
                        e.getEntityType() == Params.EntityType.PERSON &&
                        e.isActive()))
                    return true;
            }
        }
        return false;
    }


    public int howManyBurned() {
        return (int) getCellsAsArrayList().stream().filter(c ->
                c.getEntities().stream().anyMatch(e -> e.getEntityType() == Params.EntityType.FIRE) &&
                c.getEntities().stream().anyMatch(e -> e.getEntityType() == Params.EntityType.PERSON)).count();
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        // draw cells
        getCellsAsArrayList().forEach(c -> c.draw((Graphics2D) graphics));

        // draw active entities
        getCellsAsArrayList().forEach(c -> c.getEntities().stream().filter(Entity::isActive).forEach(e -> e.draw((Graphics2D) graphics)));
    }


    public Cell getCell(int positionX, int positionY) {
        if(positionX >= 0 && positionX < Params.boardLatitude && positionY >= 0 && positionY < Params.boardLongitude)
            return cells[positionY][positionX];
        else return null;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public ArrayList <Cell> getCellsAsArrayList() {
        ArrayList <Cell> cellsArrayList = new ArrayList<>();
        for(int r = 0; r < Params.boardLongitude; r++) {
            for (int c = 0; c < Params.boardLatitude; c++) {
                cellsArrayList.add(cells[r][c]);
            }
        }
        return cellsArrayList;
    }

}
