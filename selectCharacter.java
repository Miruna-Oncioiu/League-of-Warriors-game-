import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// clasa pentru selectare personaj
public class selectCharacter {
    private JDialog dialog;
    private Character selectedCharacter;

    // metoda pentru afisare fereastra de selectare personaj
    public Character showCharacterSelection(Account selectedAccount) {
        // creez un dialog
        dialog = new JDialog((Frame) null, "SELECTEAZA PERSONAJUL", true);
        dialog.setSize(500, 300);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(Color.BLACK);

        // adaug text in dialog
        JLabel instrLabel = new JLabel("Alegeti un personaj:", SwingConstants.CENTER);
        instrLabel.setFont(new Font("Georgia", Font.BOLD, 16));
        instrLabel.setForeground(Color.WHITE);
        dialog.add(instrLabel, BorderLayout.NORTH);

        // panou pentru lista de butoane
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(0, 1));
        buttonsPanel.setBackground(Color.BLACK);

        // creare butoane pentru fiecare personaj
        ArrayList<Character> characters = selectedAccount.getCharacters();
        if (characters.isEmpty()) {
            JLabel noCharactersLabel = new JLabel("Nu mai exista personaje disponibile!", SwingConstants.CENTER);
            noCharactersLabel.setFont(new Font("Georgia", Font.BOLD, 14));
            noCharactersLabel.setForeground(Color.WHITE);
            dialog.add(noCharactersLabel, BorderLayout.CENTER);
        } else {
            for (Character character : characters) {
                JButton characterButton = new JButton(character.getName());
                characterButton.setFont(new Font("Georgia", Font.PLAIN, 14));
                characterButton.setBackground(Color.DARK_GRAY);
                characterButton.setForeground(Color.WHITE);
                characterButton.setFocusPainted(false);
                characterButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
                characterButton.addActionListener(new SelectAction(character));
                buttonsPanel.add(characterButton);
            }
        }
        // adaug panoul in fereastra
        JScrollPane scrollPane = new JScrollPane(buttonsPanel);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // afisez fereastra (dialogul) in mijlocul ecranului
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        return selectedCharacter;
    }

    // ascultator pentru buton
    private class SelectAction implements ActionListener {
        private Character character;
        public SelectAction(Character character) {
            this.character = character;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            selectedCharacter = character;
            dialog.dispose();
        }
    }
}