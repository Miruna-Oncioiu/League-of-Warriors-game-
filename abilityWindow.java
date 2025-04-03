import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class abilityWindow extends JFrame {
    private Spell selectedAbility;

    public abilityWindow(List<Spell> abilities, ActionListener onAbilitySelected) {
        setTitle("Selecteaza o abilitate");
        setSize(800, 400);
        setLayout(new GridLayout(1, abilities.size()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // creez un panel cu abilitati
        for (Spell ability : abilities) {
            JPanel abilityPanel = createAbilityPanel(ability, onAbilitySelected);
            add(abilityPanel);
        }

        // afisez fereastra in mijlocul ecranului
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createAbilityPanel(Spell ability, ActionListener onAbilitySelected) {
        // creare panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // imagine abilitate
        JLabel abilityImageLabel = new JLabel();
        ImageIcon abilityIcon = Spell.getImageForSpell(ability.getType());
        Image abilityImage = abilityIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        abilityImageLabel.setIcon(new ImageIcon(abilityImage));

        // adaugare nume, mana, damage
        JLabel nameLabel = new JLabel("Name: " + ability.getType());
        JLabel manaLabel = new JLabel("Mana cost: " + ability.getManaCost());
        JLabel damageLabel = new JLabel("Damage: " + ability.getDamage());

        // buton
        JButton selectButton = new JButton("SELECT");
        // ascultator pentru buton
        selectButton.addActionListener(e -> {
            selectedAbility = ability;
            onAbilitySelected.actionPerformed(new ActionEvent(selectedAbility, ActionEvent.ACTION_PERFORMED, null));
            dispose();
        });

        // adaugare componente in panou
        panel.add(abilityImageLabel);
        panel.add(nameLabel);
        panel.add(manaLabel);
        panel.add(damageLabel);
        panel.add(selectButton);
        return panel;
    }
}
