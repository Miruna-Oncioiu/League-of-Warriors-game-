import java.util.*;

// clasele CELL si GRID

// exceptii
class InvalidCommandException extends Exception {
    public InvalidCommandException(String message) {
        super(message);
    }
}

class ImpossibleMove extends Exception {
    public ImpossibleMove(String message) {
        super(message);
    }
}

// clasa Cell
class Cell {
    private final int x;
    private final int y;
    private CellEntityType type;
    private boolean visited;

    // enumerare
    enum CellEntityType {
        PLAYER, VOID, ENEMY, SANCTUARY, PORTAL;
    }

    // constructori si metode get si set
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.type = Cell.CellEntityType.VOID;
        this.visited = false;
    }

    public Cell(){
        this(0,0);
    }

    public boolean getVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public CellEntityType getType() {
        return this.type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setType(CellEntityType type) {
        this.type = type;
    }

    // metoda toString pentru afisarea pe tabla
    @Override
    public String toString() {
        if (this.type == CellEntityType.PLAYER) return "P";
        if (this.type == CellEntityType.ENEMY) return "E";
        if (this.type == CellEntityType.SANCTUARY) return "S";
        if (this.type == CellEntityType.PORTAL) return "F";
        // daca nu a fost vizitata pun N
        if (this.visited) return "V";
        else return "N";
    }
}

// clasa Grid
class Grid extends ArrayList<ArrayList<Cell>> {
    private final int length;
    private final int width;
    private Cell current;
    private Character character;

    // constructor
    private Grid(int l, int w) {
        this.length = l;
        this.width = w;
        // initializare harta
        for (int i = 0; i < this.length; i++) {
            // un rand nou
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < this.width; j++)
                row.add(new Cell(i, j));
            // adaug randul la harta
            this.add(row);
        }
    }

    // o folosesc in DisplayOptions
    public Cell getCurrentCell() {
        return current;
    }

    // Generare harta
    public static Grid generateGrid(int length, int width) {
        Grid grid = new Grid(length, width);
        Random random = new Random();
        // voi lua ca interval, [minimul, dublul minimului]
        int minSanctuaries = 2;
        int minEnemies = 4;
        int sanctuaries = random.nextInt(minSanctuaries + 1) + minSanctuaries;
        int enemies = random.nextInt(minEnemies + 1) + minEnemies;

        // un player
        grid.place(Cell.CellEntityType.PLAYER, length, width,1);
        // setez celula jucatorului ca pozitie curenta
        for (ArrayList<Cell> row : grid)
            for (Cell cell : row)
                if (cell.getType() == Cell.CellEntityType.PLAYER) {
                    grid.current = cell;
                    break;
                }
        // un singur portal
        grid.place(Cell.CellEntityType.PORTAL, length, width,1);
        grid.place(Cell.CellEntityType.SANCTUARY, length, width, sanctuaries);
        grid.place(Cell.CellEntityType.ENEMY, length, width, enemies);
        return grid;
    }

    private void place(Cell.CellEntityType type, int length, int width, int count) {
        Random random = new Random();
        while (count > 0) {
            int x = random.nextInt(length);
            int y = random.nextInt(width);
            Cell cell = this.get(x).get(y);
            // daca celula e libera
            if (cell.getType() == Cell.CellEntityType.VOID) {
                cell.setType(type);
                count--;
            }
        }
    }

    // metodele pentru mutari, mut player-ul sau exceptie daca nu
    public void goNorth() throws ImpossibleMove {
        if (current.getX() > 0)
            setPlayerPosition(current.getX() - 1, current.getY());
        else
            throw new ImpossibleMove("Imposibil de deplasat la NORTH!");
    }

    public void goSouth() throws ImpossibleMove {
        if (current.getX() < length - 1)
            setPlayerPosition(current.getX() + 1, current.getY());
        else
            throw new ImpossibleMove("Imposibil de deplasat la SOUTH!");
    }

    public void goWest() throws ImpossibleMove {
        if (current.getY() > 0)
            setPlayerPosition(current.getX(), current.getY() - 1);
        else
            throw new ImpossibleMove("Imposibil de deplasat la WEST!");
    }

    public void goEast() throws ImpossibleMove {
        if (current.getY() < width - 1)
            setPlayerPosition(current.getX(), current.getY() + 1);
        else
            throw new ImpossibleMove("Imposibil de deplasat la EAST!");
    }

    // setare pozitie player
    public void setPlayerPosition(int x, int y) {
        // va deveni vizitata
        //current.setVisited(true);
        current.setType(Cell.CellEntityType.VOID);
        current = get(x).get(y);
        current.setVisited(true);
        // pun player-ul pe noua pozitie
        current.setType(Cell.CellEntityType.PLAYER);
    }

    // afisare harta
    public void printGrid() {
        for (ArrayList<Cell> row : this) {
            for (Cell cell : row)
                System.out.print(cell + " ");
            System.out.println();
        }
    }
}