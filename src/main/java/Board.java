import javafx.util.Pair;

import java.util.ArrayList;
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
                System.out.print(cells[r][c].getCellType().toString().charAt(0) + "  ");
            }
            System.out.println();
        }
    }

    public void showPeople() {
        System.out.print("\n\n\n");
        for(int r = 0; r < longitude; r++) {
            for (int c = 0; c < latitude; c++) {
                if(cells[r][c].getEntities().stream().anyMatch(e ->
                        e.getEntityType() == Params.EntityType.PERSON))
                    System.out.print("P  ");
                else if(cells[r][c].getCellType() == Params.CellType.EXIT)
                    System.out.print("E  ");
                else if(cells[r][c].getEntities().stream().anyMatch(e ->
                        e.getEntityType() == Params.EntityType.FIRE))
                    System.out.print("F  ");
                else
                    System.out.print(".  ");
            }
            System.out.println();
        }

    }

    public void addEmergencyExits(List <Pair <Integer,Integer> > exits) {
        for(Pair <Integer, Integer> exit : exits) {
            if(exit.getValue()==0 || exit.getValue()==Params.boardLatitude-1){
                cells[exit.getKey()][exit.getValue()].setCellType(Params.CellType.EXIT);
            } else{
                System.out.println("Exit is not on the edge of plan");
                System.exit(1);
            }
        }
    }

    public boolean isPersonOnBoard() {
        for(int r = 0; r < longitude; r++) {
            for (int c = 0; c < latitude; c++) {
                if(cells[r][c].getEntities().stream().anyMatch(e ->
                        e.getEntityType() == Params.EntityType.PERSON))
                    return true;
            }
        }
        return false;
    }

    public List<Fire> getFirePlaces() {
        List<Fire> fireplaces=new ArrayList<>();
        for(int r = 0; r < longitude; r++) {
            for (int c = 0; c < latitude; c++) {
                if (cells[r][c].getEntities().stream().anyMatch(e ->
                        e.getEntityType() == Params.EntityType.FIRE)) {
                    fireplaces.add(new Fire(Params.EntityType.FIRE, cells[r][c]));
                }
            }
        }
        return fireplaces;
    }
}
