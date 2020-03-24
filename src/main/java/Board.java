import javafx.util.Pair;

import java.util.List;

// Board can be singleton class since we have one board in our simulation :)
public class Board {

    private static Board boardInstance = null;

    private int latitude; // szerokość
    private int longitude; // wysokość
    private Cell[][] cells;

    private Board(int latitude, int longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.cells = new Cell[this.latitude][this.longitude];
    }

    public static Board getInstance() {
        if(boardInstance == null)
            boardInstance = new Board(Params.boardLatitude, Params.boardLongitude);
        return boardInstance;
    }

    public Cell getCell(int positionX, int positionY) {
        if(positionX >= 0 && positionX < latitude && positionY >= 0 && positionY < longitude)
            return cells[positionY][positionX];
        else return null;
    }

    public Cell[][] getCells() {
        return cells;
    }


    public void setCellTypes() {
        for(int r = 0; r < longitude; r++){
            for (int c = 0; c < latitude; c++){
                if(r == 0 || c == 0 || r == longitude - 1 || c == latitude - 1) {
                    cells[r][c] = new Cell(Params.CellType.WALL, r, c);
                } else {
                    cells[r][c] = new Cell(Params.CellType.FLOOR, r, c);
                }
            }
        }
    }

    public void showBoard() {
        System.out.print("\n\n\n");
        for(int r = 0; r < longitude; r++) {
            for (int c = 0; c < latitude; c++) {
                System.out.print(cells[r][c].getCellType().toString().charAt(0) + "  ");  //zeby sie ladnie wyswietlało
            }
            System.out.println();
        }
    }

    public void showPeople() {
        System.out.print("\n\n\n");
        for(int r = 0; r < longitude; r++) {
            for (int c = 0; c < latitude; c++) {
                if(cells[r][c].getEntities().stream().filter(e ->
                        e.getEntityType() == Params.EntityType.PERSON).count() > 0)
                    System.out.print("P  ");  //zeby sie ladnie wyswietlało
                else
                    System.out.print(".  ");
            }
            System.out.println();
        }

    }

    public void addEmergencyExits(List <Pair <Integer,Integer> > exits) {

        for(Pair <Integer, Integer> exit : exits) {
            // TODO: need to valid if exit is on the wall or it's not important?
            cells[exit.getKey()][exit.getValue()].setCellType(Params.CellType.EXIT);
        }
    }

    public boolean isPersonOnBoard() {
        for(int r = 0; r < longitude; r++) {
            for (int c = 0; c < latitude; c++) {
                if(cells[r][c].getEntities().stream().filter(e ->
                        e.getEntityType() == Params.EntityType.PERSON).count() > 0)
                    return true;
            }
        }
        return false;
    }
}
