import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame{
    private JPanel gridPanel, rightPanel, leftPanel;
    private JLabel accountNameLabel, characterNameLabel, accountLevelLabel;
    private JLabel levelLabel, manaLabel, experienceLabel, healthLabel;
    private Account currentAccount;
    private Grid grid;
    private JButton northButton, southButton, westButton, eastButton, exitButton;

    public GameWindow(Grid grid, Character character, Account account, Game game) {
        // creare fereastra
        super("GAME");
        this.currentAccount = account;
        this.grid = grid;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new BorderLayout());

        // creare panouri
        leftPanel = createLeftPanel(character, account);
        rightPanel = createRightPanel(character, account, game);
        gridPanel = createGridPanel(grid);

        // adaugare panouri in fereastra
        add(leftPanel, BorderLayout.WEST);
        add(gridPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);

        // afisare fereastra in mijlocul ecranului
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // creare panou cu informatii personaj
    private JPanel createLeftPanel(Character character, Account account) {
        // creare panou
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(200, getHeight()));
        panel.setBackground(Color.darkGray);
        panel.setBorder(BorderFactory.createTitledBorder(null, "PLAYER INFO",
                0, 0, new Font("Georgia", Font.BOLD, 14), Color.LIGHT_GRAY));

        // informatii cont
        accountNameLabel = new JLabel("Account name: " + account.getInformation().getName());
        accountNameLabel.setForeground(Color.WHITE);
        accountNameLabel.setFont(new Font("Georgia", Font.PLAIN, 14));
        accountLevelLabel = new JLabel("Maps Completed: " + account.getGamesPlayed());
        accountLevelLabel.setForeground(Color.WHITE);
        accountLevelLabel.setFont(new Font("Georgia", Font.PLAIN, 14));

        // linie goala intre informatii cont si informatii personaj
        JLabel separator = new JLabel(" ");
        separator.setPreferredSize(new Dimension(200, 10));

        // informatii caracter
        characterNameLabel = new JLabel("Character: " + character.getName());
        characterNameLabel.setForeground(Color.WHITE);
        characterNameLabel.setFont(new Font("Georgia", Font.PLAIN, 14));
        levelLabel = new JLabel("Level: " + character.getLevel());
        levelLabel.setForeground(Color.WHITE);
        levelLabel.setFont(new Font("Georgia", Font.PLAIN, 14));
        experienceLabel = new JLabel("Experience: " + character.getExperience());
        experienceLabel.setForeground(Color.WHITE);
        experienceLabel.setFont(new Font("Georgia", Font.PLAIN, 14));
        healthLabel = new JLabel("Health: " + character.currentHealth);
        healthLabel.setForeground(Color.WHITE);
        healthLabel.setFont(new Font("Georgia", Font.PLAIN, 14));
        manaLabel = new JLabel("Mana: " + character.currentMana);
        manaLabel.setForeground(Color.WHITE);
        manaLabel.setFont(new Font("Georgia", Font.PLAIN, 14));

        // adaugare informatii in panou
        panel.add(accountNameLabel);
        panel.add(accountLevelLabel);
        panel.add(separator);
        panel.add(characterNameLabel);
        panel.add(levelLabel);
        panel.add(experienceLabel);
        panel.add(healthLabel);
        panel.add(manaLabel);
        return panel;
    }

    // creare panou cu butoane pentru mutari
    private JPanel createRightPanel(Character character, Account account, Game game) {
        // creare panou
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        panel.setPreferredSize(new Dimension(200, getHeight()));
        panel.setBorder(BorderFactory.createTitledBorder(null, "COMMANDS",
                0, 0, new Font("Georgia", Font.BOLD, 14), Color.LIGHT_GRAY));

        // creare butoane
        northButton = createStyledButton("NORTH");
        southButton = createStyledButton("SOUTH");
        westButton = createStyledButton("WEST");
        eastButton = createStyledButton("EAST");
        exitButton = createStyledButton("EXIT");

        // adaugare ascultatori pentru butoane
        northButton.addActionListener(e -> game.playGame(character, account, "NORTH"));
        southButton.addActionListener(e -> game.playGame(character, account, "SOUTH"));
        westButton.addActionListener(e -> game.playGame(character, account, "WEST"));
        eastButton.addActionListener(e -> game.playGame(character, account, "EAST"));
        exitButton.addActionListener(e -> game.playGame(character, account, "EXIT"));

        // adaugare butoane in panou
        panel.add(northButton);
        panel.add(southButton);
        panel.add(westButton);
        panel.add(eastButton);
        panel.add(exitButton);
        return panel;
    }

    // creare panou cu tabla de joc
    private JPanel createGridPanel(Grid grid) {
        gridPanel = new JPanel();
        updateGrid(grid);
        return gridPanel;
    }

    // getter pentru contul curent
    public Account getCurrentAccount() {
        return currentAccount;
    }

    // actualizare tabla de joc
    public void updateGrid(Grid grid) {
        // sterg tabla veche si o refac
        this.grid = grid;
        gridPanel.removeAll();
        int rows = grid.size();
        int cols = grid.get(0).size();
        gridPanel.setLayout(new GridLayout(rows, cols));

        // pt redimensionarea tablei
        int cellSize = Math.min(800 / rows, 800 / cols);

        //actualizez butoanele disponibile
        int currentX = grid.getCurrentCell().getX();
        int currentY = grid.getCurrentCell().getY();
        updateMovement(currentX, currentY);

        // actualizez informatiile pe tabla
        for (int i = 0; i < grid.size(); i++) {
            for (int j = 0; j < grid.get(0).size(); j++) {
                Cell cell = grid.get(i).get(j);
                JPanel cellPanel = new JPanel();
                cellPanel.setPreferredSize(new Dimension(cellSize, cellSize));
                cellPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                switch (cell.getType()) {
                    case PLAYER -> {
                        JLabel playerLabel = new JLabel();
                        ImageIcon playerIcon = new ImageIcon("src/resources/player.png");
                        Image scaledImage = playerIcon.getImage().getScaledInstance(cellSize, cellSize, Image.SCALE_SMOOTH);
                        playerLabel.setIcon(new ImageIcon(scaledImage));
                        cellPanel.add(playerLabel);
                    }
                    case PORTAL -> cellPanel.setBackground(Color.WHITE);
                    case ENEMY -> cellPanel.setBackground(Color.WHITE);
                    case SANCTUARY -> cellPanel.setBackground(Color.WHITE);
                    case VOID -> cellPanel.setBackground(cell.getVisited() ? Color.LIGHT_GRAY : Color.WHITE);
                }
                gridPanel.add(cellPanel);
            }
        }

        // redimensionez tabla si o actualizez
        gridPanel.setPreferredSize(new Dimension(cellSize * cols, cellSize * rows));
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    // actualizare panou cu informatii personaj
    public void updatePlayerInfo(Character character, Account account) {
        Character currentCharacter = Game.getInstance().getSelectedCharacter();
        accountNameLabel.setText("Account name: " + account.getInformation().getName());
        accountLevelLabel.setText("Maps Completed: " + account.getGamesPlayed());
        characterNameLabel.setText("Character: " + currentCharacter.getName());
        levelLabel.setText("Level: " + currentCharacter.getLevel());
        experienceLabel.setText("Experience: " + currentCharacter.getExperience());
        healthLabel.setText("Health: " + currentCharacter.currentHealth);
        manaLabel.setText("Mana: " + currentCharacter.currentMana);
        leftPanel.revalidate();
        leftPanel.repaint();
    }

    // metoda pentru creare butoane
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Georgia", Font.BOLD, 14));
        button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        return button;
    }

    // metoda pentru verificare mutari
    private void updateMovement(int currentX, int currentY) {
        // verific unde ma pot muta
        boolean canMoveNorth = currentX > 0;
        boolean canMoveSouth = currentX < grid.size() - 1;
        boolean canMoveWest = currentY > 0;
        boolean canMoveEast = currentY < grid.get(0).size() - 1;

        // activez sau dezactivez butoanele
        northButton.setEnabled(canMoveNorth);
        southButton.setEnabled(canMoveSouth);
        westButton.setEnabled(canMoveWest);
        eastButton.setEnabled(canMoveEast);
    }
}