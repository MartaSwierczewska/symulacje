package symulacje;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
        for(Pair <Integer, Integer> exit : Params.exits) {
            if(exit.getValue() == 0 || exit.getValue() == Params.boardLatitude - 1) {
                cells[exit.getKey()][exit.getValue()].setCellType(Params.CellType.EXIT);
            } else {
                System.out.println("Exit is not on the edge of plan");
                System.exit(1);
            }
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


    public boolean isPersonBurning() {
        if(getCellsAsArrayList().stream().filter(c ->
                c.getEntities().stream().anyMatch(e -> e.getEntityType() == Params.EntityType.FIRE) &&
                c.getEntities().stream().anyMatch(e -> e.getEntityType() == Params.EntityType.PERSON)).count() > 0)
            return true;
        return false;
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        getCellsAsArrayList().stream().forEach(c -> c.draw((Graphics2D) graphics));
        getCellsAsArrayList().stream().forEach(c -> c.getEntities().stream().forEach(e -> e.draw((Graphics2D) graphics)));
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
