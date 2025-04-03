import java.util.*;
import javax.swing.*;

// clasele GAME, ACCOUNT, INFORMATION, CREDENTIALS

// clasa Game
public class Game {
    private ArrayList<Account> accounts;
    private Grid grid;
    private Character selectedCharacter;
    private GameWindow gameWindow;

    // pt Singleton Pattern:
    private static Game instance = null;

    private Game() {
        // doar pt a nu permite instantierea
    }

    public static Game getInstance() {
        if (instance == null)
            instance = new Game();
        return instance;
    }

    // getter pentru personajul selectat
    public Character getSelectedCharacter() {
        return selectedCharacter;
    }

    // setter pentru personajul selectat
    public void setSelectedCharacter(Character character) {
        this.selectedCharacter = character;
        if (gameWindow != null) {
            gameWindow.updatePlayerInfo(selectedCharacter, gameWindow.getCurrentAccount());
        }
    }

    // metoda run
    public void run() {
        // autentificare
        Login login = new Login();
        Account selectedAccount = login.showLogin();
        if (selectedAccount == null) {
            JOptionPane.showMessageDialog(null, "Autentificare esuata. Incercati din nou.", "Eroare", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // selectare personaj
        selectCharacter selectarePersonaj = new selectCharacter();
        selectedCharacter = selectarePersonaj.showCharacterSelection(selectedAccount);
        if (selectedCharacter == null) {
            JOptionPane.showMessageDialog(null, "Nu a fost selectat niciun personaj. Jocul se incheie.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // pt harta random
//        Random random = new Random();
//        int n = random.nextInt(3, 10);
//        int m = random.nextInt(3, 10);
//        grid = Grid.generateGrid(n, m);

        // pt harta hardcodata
        Harta_hardcodata hartaHardcodata = new Harta_hardcodata();
        grid = hartaHardcodata.creeazaHartaHardcodata();
        gameWindow = new GameWindow(grid, selectedCharacter, selectedAccount, this);
    }

    // logica jocului
    public void playGame(Character selectedCharacter, Account selectedAccount, String command) {
        try {
            Character character = Game.getInstance().getSelectedCharacter();
            grid.printGrid();
            int currentX = grid.getCurrentCell().getX();
            int currentY = grid.getCurrentCell().getY();
            grid.get(currentX).get(currentY).setVisited(true);
            // la exit ne intoarcem la selectare personaj
            if (command.equals("EXIT")) {
                System.out.println("Ai parasit jocul. Selecteaza un nou personaj.");
                // daca nu mai sunt personaje, ne intoarcem la autentificare
                if (selectedAccount.getCharacters().isEmpty()) {
                    System.out.println("Nu mai ai personaje disponibile. Jocul s-a incheiat!");
                    run();
                    return;
                }
                // selectez un nou personaj
                selectCharacter selectarePersonaj = new selectCharacter();
                Character newCharacter = selectarePersonaj.showCharacterSelection(selectedAccount);
                if (newCharacter == null) {
                    System.out.println("Nu ai selectat niciun personaj!");
                } else {
                    // setez personajul selectat si actualizez fereastra
                    setSelectedCharacter(newCharacter);
                    gameWindow.updateGrid(grid);
                }
                return;
            }

            // verific in functie de comanda data, ce este la celula unde doresc sa
            // ma mut, si o salvez
            Cell.CellEntityType cellTypeBeforeMove = null;
            if (command.equals("NORTH") && currentX > 0) {
                cellTypeBeforeMove = grid.get(currentX - 1).get(currentY).getType();
            } else if (command.equals("SOUTH") && currentX < grid.size() - 1) {
                cellTypeBeforeMove = grid.get(currentX + 1).get(currentY).getType();
            } else if (command.equals("WEST") && currentY > 0) {
                cellTypeBeforeMove = grid.get(currentX).get(currentY - 1).getType();
            } else if (command.equals("EAST") && currentY < grid.get(0).size() - 1) {
                cellTypeBeforeMove = grid.get(currentX).get(currentY + 1).getType();
            } else {
                throw new InvalidCommandException("Comanda invalida: " + command);
            }

            // verific tipul celulei inainte de mutare, si implementez cazurile in functie de acesta
            Cazuri cazuri = new Cazuri();
            if (cellTypeBeforeMove == Cell.CellEntityType.SANCTUARY) {
                // apelez metoda de sanctuar si actualizez fereastra
                cazuri.Sanctuar(character);
                gameWindow.updatePlayerInfo(character, selectedAccount);
            } else if (cellTypeBeforeMove == Cell.CellEntityType.PORTAL){
                // apelez metoda de portal si actualizez fereastra
                System.out.println("Personaj curent: " + character.getName());
                grid = cazuri.Portal(character, selectedAccount);
                // daca nu mai sunt personaje, ma intorc la autentificare
                if (selectedAccount.getCharacters().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nu mai exista personaje disponibile. Jocul s-a incheiat.", "Game Over", JOptionPane.ERROR_MESSAGE);
                    run();
                    return;
                }
                // selectez un nou personaj
                selectCharacter selectarePersonaj = new selectCharacter();
                Character newCharacter = selectarePersonaj.showCharacterSelection(selectedAccount);
                if (newCharacter == null) {
                    JOptionPane.showMessageDialog(null, "Nu a fost selectat niciun personaj. Jocul s-a incheiat.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                setSelectedCharacter(newCharacter);
                gameWindow.updateGrid(grid);
                gameWindow.updatePlayerInfo(newCharacter, selectedAccount);
            } else if (cellTypeBeforeMove == Cell.CellEntityType.VOID) {
                System.out.println("Celula pustie");
                // JOptionPane.showMessageDialog(null, "Celula pustie. Continua drumul.", "Celula Pustie", JOptionPane.INFORMATION_MESSAGE);
            } else if (cellTypeBeforeMove == Cell.CellEntityType.ENEMY) {
                Enemy enemy = new Enemy();
                enemyBattle battleUI = new enemyBattle(character, enemy, gameWindow);
                battleUI.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        if (character.currentHealth <= 0) {
                            JOptionPane.showMessageDialog(null, "Personajul tau a fost invins!", "Game Over", JOptionPane.ERROR_MESSAGE);
                            selectedAccount.getCharacters().remove(character);
                            // daca nu mai sunt personaje, ma intorc la autentificare
                            if (selectedAccount.getCharacters().isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Nu mai ai personaje disponibile. Jocul s-a incheiat!", "Game Over", JOptionPane.ERROR_MESSAGE);
                                run();
                                return;
                            }
                            // selectare personaj
                            selectCharacter selectarePersonaj = new selectCharacter();
                            Character newCharacter = selectarePersonaj.showCharacterSelection(selectedAccount);
                            if (newCharacter == null) {
                                JOptionPane.showMessageDialog(null, "Nu a fost selectat niciun personaj. Jocul s-a incheiat.", "Info", JOptionPane.INFORMATION_MESSAGE);
                                return;
                            }
                            // setez noul personaj si actulizez fereastra
                            setSelectedCharacter(newCharacter);
                            gameWindow.updateGrid(grid);
                            gameWindow.updatePlayerInfo(newCharacter, selectedAccount);
                        }
                    }
                });
            }
            // mut jucatorul
            if (command.equals("NORTH"))
                grid.goNorth();
            else if (command.equals("SOUTH"))
                grid.goSouth();
            else if (command.equals("WEST"))
                grid.goWest();
            else if (command.equals("EAST"))
                grid.goEast();

            // actualizez celula curenta si fereastra
            Cell currentCell = grid.getCurrentCell();
            currentCell.setVisited(true);
            gameWindow.updateGrid(grid);
            gameWindow.updatePlayerInfo(character, selectedAccount);
        } catch (InvalidCommandException | ImpossibleMove e) {
            System.out.println(e.getMessage());
        }
    }
}

// clasa Test
class Test {
    public static void main(String[] args) {
        // inainte de Singleton:
        // Game game = new Game();

        // dupa Singleton:
        Game game = Game.getInstance();
        game.run();
    }
}