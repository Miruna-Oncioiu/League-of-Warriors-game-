import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;

import javax.naming.Name;

public class JsonInput {
    public static ArrayList<Account> deserializeAccounts(String emailInput, String passwordInput) {
        String accountPath = "C:\\Users\\Miruna\\IdeaProjects\\tema1\\src\\accounts.json";
        ArrayList<Account> accounts = new ArrayList<>();
        try {
            // citire si parsare fisier Json
            String content = new String(Files.readAllBytes(Paths.get(accountPath)));
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(content);
            JSONArray accountsArray = (JSONArray) obj.get("accounts");

            // iterez prin conturi
            for (int i = 0; i < accountsArray.size(); i++) {
                JSONObject accountJson = (JSONObject) accountsArray.get(i);

                // extrag nume, tara, jocuri finalizate
                String name = (String) accountJson.get("name");
                String country = (String) accountJson.get("country");
                Object gamesCompletedObj = accountJson.get("maps_completed");
                int gamesNumber = (gamesCompletedObj instanceof Long) ? ((Long) gamesCompletedObj).intValue() : Integer.parseInt((String) gamesCompletedObj);

                // email si parola
                JSONObject credentialsJson = (JSONObject) accountJson.get("credentials");
                String email = (String) credentialsJson.get("email");
                String password = (String) credentialsJson.get("password");

                // verific daca coincid cu cele de la utilizator
                if (email.equals(emailInput) && password.equals(passwordInput)) {
                    System.out.println("Autentificare reusita!");

                    // extrag personajele asociate contului (nume, profesie, nivel, experienta)
                    JSONArray charactersArray = (JSONArray) accountJson.get("characters");
                    ArrayList<Character> characters = new ArrayList<>();
                    for (int j = 0; j < charactersArray.size(); j++) {
                        JSONObject charJson = (JSONObject) charactersArray.get(j);
                        String charName = (String) charJson.get("name");
                        String profession = (String) charJson.get("profession");

                        Object levelObj = charJson.get("level");
                        int level = (levelObj instanceof Long) ? ((Long) levelObj).intValue() : Integer.parseInt((String) levelObj);

                        Object experienceObj = charJson.get("experience");
                        int experience = (experienceObj instanceof Long) ? ((Long) experienceObj).intValue() : Integer.parseInt((String) experienceObj);

                        // in functie de profesie, creez personaje
                        Character character = null;
                        switch (profession.toLowerCase()) {
                            case "warrior":
                                character = Factory.createCharacter(Factory.CharacterType.WARRIOR, charName, level, experience);
                                break;
                            case "mage":
                                character = Factory.createCharacter(Factory.CharacterType.MAGE, charName, level, experience);
                                break;
                            case "rogue":
                                character = Factory.createCharacter(Factory.CharacterType.ROGUE, charName, level, experience);
                                break;
                            default:
                                System.out.println("Profesie necunoscuta: " + profession);
                                break;
                        }

                        // le adaug in lista
                        if (character != null) {
                            characters.add(character);
                        }
                    }

                    // adaug contul in lista

                    // inainte de Builder:
                    // accounts.add(new Account(characters, gamesNumber, new Account.Information(new Credentials(email, password), null, name, country)));

                    // dupa Builder:
                    SortedSet<String> favoriteGames = new TreeSet<>();
                    // creez un obiect Information cu Builder
                    Account.Information information = new Account.Information.informationBuilder()
                                    .setCredentials(new Credentials(email, password))
                                    .setFavoriteGames(favoriteGames)
                                    .setName(name)
                                    .setCountry(country)
                                    .build();

                    // adaug contul in lista
                    accounts.add(new Account(characters, gamesNumber, information));
                }
            }

            // daca nu exista contul
            if (accounts.isEmpty()) {
                System.out.println("Contul nu exista.");
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}