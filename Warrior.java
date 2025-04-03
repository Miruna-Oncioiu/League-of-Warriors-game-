import java.util.*;

// clasa Warrior
public class Warrior extends Character {
    // constructor, in care am setat imunitatea si am generat nr pt atribute
    public Warrior(String name, int level, int experience) {
        super(name, level, experience);
        this.fireImmunity = true;
        this.strength = rand.nextInt(21) + 5;
        this.dexterity = rand.nextInt(21) + 5;
        this.charisma = rand.nextInt(21) + 5;
        this.profession = "Warrior";
        generateAbilities();
    }

    // primire damage
    @Override
    public void receiveDamage(int damage) {
        // sansa de 50% sa se injumatateasca damage ul
        if (Math.random() < 0.5) {
            damage /= 2;
            System.out.println(name + " a evitat o parte din damage! " + damage);
        }

        // calculez in functie de atribute
        int reduction = (int)((getDexterity()*0.8 + getCharisma()*0.9));
        damage -= reduction;

        // sa nu dea negativ
        int finalDamage = Math.max(damage, 0);
        currentHealth -= finalDamage;

        // viata sa nu fie negativa
        currentHealth = Math.max(currentHealth, 0);
        if(currentHealth < 0) currentHealth = 0;
        System.out.println(name + " a primit " + finalDamage + " puncte de damage");
    }

    // dat damage
    @Override
    public int getDamage() {
        int baseDamage = getStrength() * 2;
        // Șansa de 50% ca damage-ul să se dubleze
        System.out.println("Jucatorul a dat: " + baseDamage + " damage");
        if (Math.random() < 0.5) {
            baseDamage *= 2;
            System.out.println("S-a dublat la: " + baseDamage + " damage");
        }
        return baseDamage;
    }
}

// clasa Mage
class Mage extends Character {
    // constructor, in care am setat imunitatea si am generat nr pt atribute
    public Mage(String name, int level, int experience) {
        super(name, level, experience);
        this.iceImmunity = true;
        this.strength = rand.nextInt(21) + 5;
        this.dexterity = rand.nextInt(21) + 5;
        this.charisma = rand.nextInt(21) + 5;
        this.profession = "Mage";
        generateAbilities();
    }

    // primire damage
    @Override
    public void receiveDamage(int damage) {
        // sansa de 50% sa se injumatateasca damage ul
        if (Math.random() < 0.5) {
            damage /= 2;
            System.out.println(name + " a injumatatit damage-ul " + damage);
        }

        // calculez in functie de atribute
        int reduction = (int)((getDexterity()*0.7 + getStrength()*0.8));
        damage -= reduction;

        // sa nu dea negativ
        int finalDamage = Math.max(damage, 0);
        currentHealth -= finalDamage;
        currentHealth = Math.max(currentHealth, 0);
        // viata sa nu fie negativa
        if(currentHealth < 0) currentHealth = 0;
        System.out.println(name + " a primit " + finalDamage + " puncte de damage");
    }

    // dat damage
    @Override
    public int getDamage() {
        int baseDamage = getCharisma() * 2;
        // Șansa de 50% ca damage-ul sa se dubleze
        System.out.println("Jucatorul a dat: " + baseDamage + " damage");
        if (Math.random() < 0.5) {
            baseDamage *= 2;
            System.out.println("S-a dublat la: " + baseDamage + " damage");
        }
        return baseDamage;
    }
}

// clasa Rogue
class Rogue extends Character {
     // constructor, in care am setat imunitatea si am generat nr pt atribute
     public Rogue(String name, int level, int experience) {
         super(name, level, experience);   // Rogue are doar Dexterity
         this.earthImmunity = true;
         this.strength = rand.nextInt(21) + 5;
         this.dexterity = rand.nextInt(21) + 5;
         this.charisma = rand.nextInt(21) + 5;
         this.profession = "Rogue";
         generateAbilities();
    }

    // primire damage
    @Override
    public void receiveDamage(int damage) {
        // sansa de 50% sa se injumatateasca damage ul
        if (Math.random() < 0.5) {
            damage /= 2;
            System.out.println(name + " a evitat o parte din damage!" + damage);
        }

        // calculez in functie de atribute
        int reduction = (int)((getStrength()*0.9 + getCharisma()*0.7));
        damage -= reduction;

        // sa nu dea negativ
        int finalDamage = Math.max(damage, 0);
        currentHealth -= finalDamage;

        // viata sa nu fie negativa
        if(currentHealth < 0) currentHealth = 0;
        System.out.println(name + " a primit " + finalDamage + " puncte de damage");
    }

    // dat damage
    @Override
    public int getDamage() {
        int baseDamage = getDexterity() * 2;
        System.out.println("Jucatorul a dat: " + baseDamage + " damage");
        // Șansa de 50% ca damage-ul sa se dubleze
        if (Math.random() < 0.5) {
            baseDamage *= 2;
            System.out.println("S-a dublat la: " + baseDamage + " damage");
        }
        return baseDamage;
    }
}
