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

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }


    public Cell getCell(int positionX, int positionY) {
        if(positionX >= 0 && positionX < latitude && positionY >= 0 && positionY < longitude)
            return cells[positionX][positionY];
        else return null;
    }


    public void setCellTypes() {
        int rMax = longitude, cMax = latitude;

        for(int r = 0; r < rMax; r++){
            for (int c = 0; c < cMax; c++){
                if(r == 0 || c == 0 || r == rMax - 1 || c == cMax - 1) {
                    cells[r][c] = new Cell(Cell.CellType.WALL, r, c);
                } else {
                    cells[r][c] = new Cell(Cell.CellType.FLOOR, r, c);
                }
            }
        }
    }

    public void show(){
        int rMax = longitude, cMax = latitude;

        for(int r = 0; r < rMax; r++) {
            for (int c = 0; c < cMax; c++) {
                System.out.print(cells[r][c].getType().toString().charAt(0) + "  ");  //zeby sie ladnie wyswietlało
            }
            System.out.println();
        }
    }

    public void addEmergencyExits(List <Pair <Integer,Integer> > exits){

        for(Pair <Integer, Integer> exit : exits) {
            // need to valid if exit is on the wall?
            cells[exit.getKey()][exit.getValue()].setType(Cell.CellType.EXIT);
        }
    }

    public boolean isPersonOnBoard(){
        for(int r = 0; r < longitude - 1; r++) {
            for (int c = 0; c < latitude - 1; c++) {
                if(cells[r][c].getType()== Cell.CellType.PERSON){
                    return true;
                }
            }
        }
        return false;
    }
}
