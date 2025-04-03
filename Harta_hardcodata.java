import java.util.*;

public class Harta_hardcodata {
    public Grid grid;

    public Grid creeazaHartaHardcodata() {
        // Inițializare grid de 5x5
        grid = Grid.generateGrid(5, 5);

        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                grid.get(i).get(j).setType(Cell.CellEntityType.VOID);

        grid.setPlayerPosition(0, 0);
        grid.get(0).get(3).setType(Cell.CellEntityType.SANCTUARY);
        grid.get(1).get(3).setType(Cell.CellEntityType.SANCTUARY);
        grid.get(2).get(0).setType(Cell.CellEntityType.SANCTUARY);
        grid.get(3).get(4).setType(Cell.CellEntityType.ENEMY);
        grid.get(4).get(3).setType(Cell.CellEntityType.SANCTUARY);
        grid.get(4).get(4).setType(Cell.CellEntityType.PORTAL);
        return grid;
    }
}
