import javax.swing.*;
import java.awt.*;

// clasa pentru afisare fereastra cu informatii jucator
public class Level extends JFrame {
    public Level(Character player) {
        // creare fereastra
        setTitle("INFO");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // panou pentru imaginea personajului
        JPanel image = new JPanel();
        JLabel playerImageLabel = new JLabel();
        ImageIcon playerIcon = new ImageIcon("src/resources/playerimg.jpg");
        Image playerImage = playerIcon.getImage().getScaledInstance(250, 375, Image.SCALE_SMOOTH);
        playerImageLabel.setIcon(new ImageIcon(playerImage));
        image.add(playerImageLabel);

        // creare panou pentru informatii personaj
        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        JLabel name = new JLabel("Name: " + player.getName());
        JLabel profession = new JLabel("Role: " + player.getProfession());
        JLabel level = new JLabel("Level: " + player.getLevel());
        JLabel experience = new JLabel("Experience: " + player.getExperience());
        JLabel enemiesKilled = new JLabel("Killed enemies: " + player.getEnemiesKilled());
        name.setFont(new Font("Georgia", Font.BOLD, 14));
        profession.setFont(new Font("Georgia", Font.BOLD, 14));
        level.setFont(new Font("Georgia", Font.BOLD, 14));
        experience.setFont(new Font("Georgia", Font.BOLD, 14));
        enemiesKilled.setFont(new Font("Georgia", Font.BOLD, 14));
        details.add(name);
        details.add(profession);
        details.add(level);
        details.add(experience);
        details.add(enemiesKilled);

        // creare panou pentru buton de exit
        JPanel button = new JPanel();
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> dispose());
        button.add(exitButton);

        // adaugare componente in fereastra
        add(image, BorderLayout.NORTH);
        add(details, BorderLayout.CENTER);
        add(button, BorderLayout.SOUTH);

        //afisare fereastra in mijlocul ecranului
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
