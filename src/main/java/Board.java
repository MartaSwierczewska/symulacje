
public class Board {
    private int latitude; //szerokość
    private int longitude; //wysokość
    private Cell[][] boardWithCells;

    public Board(int latitude, int longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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

    public void create(){
        this.boardWithCells = new Cell[this.longitude][this.latitude];
    }

    public void fillWithValues(){
        Cell[][] board = this.boardWithCells;
        int rMax = longitude, cMax = latitude;

        for(int r=0;r<rMax;r++){
            for (int c=0;c<cMax;c++){
                if(r==0 || c==0 || r==rMax-1 || c==cMax-1){
                    board[r][c] = new Cell(Cell.CellType.WALL, r, c);
                } else{
                    board[r][c] = new Cell(Cell.CellType.FLOOR, r, c);
                }
            }
        }
    }

    public void show(){
        Cell[][] board = this.boardWithCells;
        int rMax = longitude, cMax = latitude;

        for(int r=0;r<rMax;r++) {
            for (int c = 0; c < cMax; c++) {
                System.out.print(board[r][c].getType()+" ");
            }
            System.out.println();
        }
    }
}
