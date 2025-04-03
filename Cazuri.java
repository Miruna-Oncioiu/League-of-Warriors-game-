import javax.swing.*;
import java.util.*;

// clasa in care gestionez cazurile pt Sanctuar/Portal
public class Cazuri {
    Random rand = new Random();

    // portal
    public Grid Portal(Character selectedCharacter, Account selectedAccount) {
        System.out.println("Ai ajuns la un Portal!");

        // adaugare experienta
        int experienceGained = selectedAccount.getGamesPlayed() * 5;
        selectedCharacter.experience += experienceGained;
        System.out.println("Ai castigat " + experienceGained + " puncte de experienta!");
        System.out.println("Ai in total " + selectedCharacter.experience + " puncte de experienta!");

        // regenerare mana + viata
        selectedCharacter.currentHealth = selectedCharacter.maxHealth;
        selectedCharacter.currentMana = selectedCharacter.maxMana;

        System.out.println("Viata si mana au fost resetate:");
        System.out.println("Viata actuala: " + selectedCharacter.currentHealth);
        System.out.println("Mana actuala: " + selectedCharacter.currentMana);

        // incrementare nivel cont
        selectedAccount.incrementGamesPlayed();
        System.out.println("Nivelul contului a crescut la: " + selectedAccount.getGamesPlayed());

        // incrementare nivel personaj
        boolean leveledUp = false;
        while(selectedCharacter.experience >= 150) {
            leveledUp = true;
            selectedCharacter.level++;
            selectedCharacter.strength += rand.nextInt(11)+10;
            selectedCharacter.dexterity += rand.nextInt(11)+10;
            selectedCharacter.charisma += rand.nextInt(11)+10;
            selectedCharacter.experience -= 150;
        }
        if (leveledUp) {
            System.out.println("Nivelul personajului a crescut la: " + selectedCharacter.getLevel());
            System.out.println("Ai în total " + selectedCharacter.experience + " puncte de experiență!");
            new Level(selectedCharacter);
        }
        System.out.println("Nivelul personajului a crescut la: " + selectedCharacter.getLevel());
        System.out.println("Ai in total " + selectedCharacter.experience + " puncte de experienta!");

        // regenerare harta
        int n = Math.min(rand.nextInt(8) + 3, 10); // Limitează n la maxim 10
        int m = Math.min(rand.nextInt(8) + 3, 10);
        System.out.println("********** HARTA REGENERATA **********");
        Grid newGrid = Grid.generateGrid(n, m);
        return newGrid;
    }

    // sanctuar
    public void Sanctuar(Character selectedCharacter) {
        System.out.println("Ai ajuns intr-un Sanctuar! Viata si mana se regenereaza.");
        // regenerez viata si mana
        System.out.println(selectedCharacter.currentHealth);
        System.out.println(selectedCharacter.currentMana);
        selectedCharacter.regenerateHealth(rand.nextInt(8) + 3);
        selectedCharacter.regenerateMana(rand.nextInt(8) + 3);
        System.out.println(selectedCharacter.currentHealth);
        System.out.println(selectedCharacter.currentMana);
    }
}
