import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

// clasa cu logica bataliei
public class enemyBattle extends JFrame {
    private JLabel playerHealthLabel, playerManaLabel, playerImageLabel;
    private JLabel enemyHealthLabel, enemyManaLabel, enemyImageLabel;
    private JButton attackButton, abilityButton;
    private Character player;
    private Enemy enemy;
    private GameWindow gameWindow;
    private Random rand = new Random();

    public enemyBattle(Character player, Enemy enemy, GameWindow gameWindow) {
        this.player = player;
        this.enemy = enemy;
        this.gameWindow = gameWindow;

        // configurez fereastra
        setTitle("BATALIE");
        setSize(720, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // panoul cu informatiile jucatorului
        JPanel playerPanel = createPlayerPanel();

        // panoul cu informatiile inamicului
        JPanel enemyPanel = createEnemyPanel();

        // panoul pentru butoane
        JPanel actionPanel = createActionPanel();

        // adaug panourile in fereastra
        add(playerPanel, BorderLayout.WEST);
        add(enemyPanel, BorderLayout.EAST);
        add(actionPanel, BorderLayout.CENTER);

        // afisez fereastra in mijlocul ecranului
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // panou pentru jucator
    private JPanel createPlayerPanel() {
        // creez panoul
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // adaug imaginea jucatorului(redimensionata)
        playerImageLabel = new JLabel();
        ImageIcon playerIcon = new ImageIcon("src/resources/playerimg.jpg");
        Image playerImg = playerIcon.getImage().getScaledInstance(250, 375, Image.SCALE_SMOOTH);
        playerImageLabel.setIcon(new ImageIcon(playerImg));

        // adaug viata si mana
        playerHealthLabel = new JLabel("Viata ta: " + player.currentHealth + "/" + player.maxHealth);
        playerManaLabel = new JLabel("Mana ta: " + player.currentMana + "/" + player.maxMana);

        // adaug componentele in panou
        panel.add(playerImageLabel);
        panel.add(playerHealthLabel);
        panel.add(playerManaLabel);
        return panel;
    }

    // panou pentru inamic
    private JPanel createEnemyPanel() {
        // creez panoul
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // adaug imaginea inamicului(redimensionata)
        enemyImageLabel = new JLabel();
        ImageIcon enemyIcon = new ImageIcon("src/resources/enemy.jpg");
        Image enemyImg = enemyIcon.getImage().getScaledInstance(250, 375, Image.SCALE_SMOOTH);
        enemyImageLabel.setIcon(new ImageIcon(enemyImg));

        // adaug viata si mana
        enemyHealthLabel = new JLabel("Viața inamic: " + enemy.currentHealth + "/" + enemy.maxHealth);
        enemyManaLabel = new JLabel("Mana inamic: " + enemy.currentMana + "/" + enemy.maxMana);

        // adaug componentele in panou
        panel.add(enemyImageLabel);
        panel.add(enemyHealthLabel);
        panel.add(enemyManaLabel);
        return panel;
    }

    // panou pentru butoane
    private JPanel createActionPanel() {
        // creez panoul
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        // creez butoanele pentru actiuni
        attackButton = new JButton("Ataca");
        abilityButton = new JButton("Foloseste abilitate");

        // adaug ascultatori de evenimente
        attackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerAttack();
            }
        });

        abilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.abilities.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Nu ai abilitati disponibile!");
                } else {
                    useAbility();
                }
            }
        });

        // adaug componentele in panou
        panel.add(attackButton);
        panel.add(abilityButton);
        return panel;
    }

    // metoda pentru atac normal
    private void playerAttack() {
        // inamicul primeste damage
        int damage = player.getDamage();
        enemy.receiveDamage(damage);
        updateUI();

        // jucatorul castiga
        if (enemy.currentHealth <= 0) {
            JOptionPane.showMessageDialog(null, "Ai invins inamicul!");
            enemyDefeated();
        } else {
            enemyTurn();
        }
    }

    // metoda pentru folosire abilitate
    private void useAbility() {
        // daca nu am abilitate
        if (player.abilities.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nu ai abilități disponibile!");
            return;
        }

        // afisare fereastra pentru selectarea abilitatii
        new abilityWindow(player.abilities, e -> {
            // folosire abilitate, inamicul primeste damage
            Spell selectedAbility = (Spell) e.getSource();
            // daca nu am mana, efectuez atac normal
            if (player.currentMana < selectedAbility.getManaCost()) {
                JOptionPane.showMessageDialog(null, "Nu ai suficienta mana pentru aceasta abilitate! Foloseste atac normal.");
                playerAttack();
            } else {
                player.useAbility(selectedAbility, enemy);
                updateUI();
            }
            // jucatorul castiga
            if (enemy.currentHealth <= 0) {
                JOptionPane.showMessageDialog(null, "Ai invins inamicul!");
                enemyDefeated();
            } else {
                enemyTurn();
            }
        });
    }

    // tura inamicului
    private void enemyTurn() {
        // 0-atac normal, 1-abilitate
        int enemyChoice = rand.nextInt(2);
        if (enemyChoice == 0) {
            // jucatorul primeste damage
            int damage = enemy.getDamage();
            player.receiveDamage(damage);
        } else {
            // folosire abilitate
            if (!enemy.abilities.isEmpty()) {
                // jucatorul primeste damage
                Spell chosenAbility = enemy.abilities.get(rand.nextInt(enemy.abilities.size()));
                enemy.useAbility(chosenAbility, player);
            } else {
                // daca nu am abilitati, atac normal
                int damage = enemy.getDamage();
                player.receiveDamage(damage);
            }
        }
        updateUI();
        // jucatorul a pierdut
        if (player.currentHealth <= 0) {
            JOptionPane.showMessageDialog(null, "Ai fost invins de inamic!");
            dispose();
        }
    }

    // metoda pentru infrangere inamic
    private void enemyDefeated() {
        player.incrementEnemiesKilled();

        // regenerare viata si mana
        player.currentHealth = Math.min(player.currentHealth * 2, player.maxHealth);
        player.currentMana = player.maxMana;

        // adaugare experienta
        int experienceGained = rand.nextInt(101) + 10;
        player.experience += experienceGained;

        // crestere nivel personaj
        boolean leveledUp = false;
        while (player.experience >= 150) {
            leveledUp = true;
            player.level++;
            // adaugare valori random la atribute
            player.strength += rand.nextInt(11) + 10;
            player.dexterity += rand.nextInt(11) + 10;
            player.charisma += rand.nextInt(11) + 10;
            player.experience -= 150;
        }

        // afisare fereastra cu detalii jucator
        if (leveledUp)
            new Level(player);

        // actualzare fereastra
        gameWindow.updatePlayerInfo(player, gameWindow.getCurrentAccount());
        dispose();
    }

    // se actualizeaza fereastra
    private void updateUI() {
        playerHealthLabel.setText("Viata ta: " + player.currentHealth + "/" + player.maxHealth);
        playerManaLabel.setText("Mana ta: " + player.currentMana + "/" + player.maxMana);
        enemyHealthLabel.setText("Viata inamic: " + enemy.currentHealth + "/" + enemy.maxHealth);
        enemyManaLabel.setText("Mana inamic: " + enemy.currentMana + "/" + enemy.maxMana);
    }
}
