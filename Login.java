import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// clasa pentru logare
public class Login {
    private JDialog dialog;
    private JTextField eField;
    private JPasswordField pField;
    private Account selectedAccount;

    // metoda afisare fereastra
    public Account showLogin() {
        // creare dialog(fereastra)
        dialog = new JDialog((Frame) null, "AUTENTIFICARE", true);
        dialog.setSize(400, 200);
        dialog.setLayout(new GridLayout(3, 1));
        dialog.getContentPane().setBackground(Color.BLACK);

        // email Label și TextField
        JLabel eLabel = new JLabel("Email:");
        eLabel.setForeground(Color.WHITE);
        eField = new JTextField();
        eField.setBackground(Color.DARK_GRAY);
        eField.setForeground(Color.WHITE);
        eField.setCaretColor(Color.WHITE);
        eField.setFont(new Font("Georgia", Font.PLAIN, 14));

        // panou pentru email
        JPanel ePanel = new JPanel(new BorderLayout());
        ePanel.setBackground(Color.BLACK);
        ePanel.add(eLabel, BorderLayout.WEST);
        ePanel.add(eField, BorderLayout.AFTER_LAST_LINE);

        // parola Label și PasswordField
        JLabel pLabel = new JLabel("Parola:");
        pLabel.setForeground(Color.WHITE);
        pField = new JPasswordField();
        pField.setBackground(Color.DARK_GRAY);
        pField.setForeground(Color.WHITE);
        pField.setCaretColor(Color.WHITE);
        pField.setFont(new Font("Georgia", Font.PLAIN, 14));

        // panou pentru parola
        JPanel pPanel = new JPanel(new BorderLayout());
        pPanel.setBackground(Color.BLACK);
        pPanel.add(pLabel, BorderLayout.WEST);
        pPanel.add(pField, BorderLayout.AFTER_LAST_LINE);

        // buton de autentificare
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        JButton loginButton = new JButton("AUTENTIFICARE");
        loginButton.setBackground(Color.DARK_GRAY);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Georgia", Font.BOLD, 14));

        // adaugare ascultator
        loginButton.addActionListener(new LoginAction());
        buttonPanel.add(loginButton);

        // adaugare componente in fereastra
        dialog.add(ePanel);
        dialog.add(pPanel);
        dialog.add(buttonPanel);

        // afisare fereastra in mijlocul ecranului
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        // inchidere fereastra
        dialog.dispose();
        return selectedAccount;
    }

    // ascultator pentru butonul de login
    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String email = eField.getText();
            String password = new String(pField.getPassword());
            AutentifSiPersonaj autentifSiPersonaj = new AutentifSiPersonaj();
            try {
                selectedAccount = autentifSiPersonaj.autentificare(email, password);
                if (selectedAccount != null) {
                    JOptionPane.showMessageDialog(dialog, "Autentificare reusita!");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Email sau parola gresita!", "Eroare", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Eroare la autentificare: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}