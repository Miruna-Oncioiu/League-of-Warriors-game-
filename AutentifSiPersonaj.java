import java.util.*;

public class AutentifSiPersonaj {

    private ArrayList<Account> accounts;

    // metoda pt autentificare
    public Account autentificare(String email, String password) {
        Account selectedAccount = null;
            try {
                accounts = JsonInput.deserializeAccounts(email, password);
            } catch (Exception e) {
                System.out.println("Eroare la procesarea datelor. Incercati din nou.");
                return null;
            }

            // caut contul corespunzator
            if (accounts != null && !accounts.isEmpty()) {
                for (Account account : accounts) {
                    if (account.getInformation().getCredentials().getEmail().equals(email) &&
                            account.getInformation().getCredentials().getPassword().equals(password)) {
                        selectedAccount = account;
                        break;
                    }
                }
            }

            // daca nu exista contul, reincerc autentificareaa
            if (selectedAccount == null)
                System.out.println("Email sau parola incorecte. Incercati din nou.");
        return selectedAccount;
    }
}
