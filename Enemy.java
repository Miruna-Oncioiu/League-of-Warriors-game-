import javax.swing.*;
import java.util.*;

// clasele Enemy si Spell

// clasa Enemy
public class Enemy extends Entity{
    private static final Random rand = new Random();
    private int damage;

    // constructor
    public Enemy() {
        // valori aleatorii pentru viata si mana maxima
        super(rand.nextInt(101) + 70, rand.nextInt(101)+70, false, false, false);

        // setare valori aleatorii pt viata, mana si damage
        this.currentHealth = this.maxHealth;
        this.currentMana = this.maxMana;
        this.damage = rand.nextInt(26) + 10;
        this.fireImmunity = rand.nextBoolean();
        this.iceImmunity = rand.nextBoolean();
        this.earthImmunity = rand.nextBoolean();
        generateAbilities();
    }

    // primire damage
    @Override
    public void receiveDamage(int damage) {
        int finalDamage;
        // sansa de 50% sa evite damage-ul
        if (Math.random() < 0.5) {
            finalDamage = 0;
            System.out.println("Inamicul a evitat damage-ul!");
            return;
        }
        finalDamage = damage;
        this.currentHealth -= finalDamage;
        this.currentHealth = Math.max(this.currentHealth, 0);
        System.out.println("Inamicul a primit " + finalDamage + " puncte de damage");
        System.out.println("Viata inamicului a ramas: " + currentHealth);
    }

    // dat damage
    @Override
    public int getDamage() {
        int baseDamage = damage;
        System.out.println("Jucatorul primeste " + baseDamage + " damage!");
        // Șansa de 50% ca damage-ul sa se dubleze
        if (Math.random() < 0.5) {
            baseDamage *= 2;
            System.out.println("Damage-ul s-a dublat  " + baseDamage);
        }
        return baseDamage;
    }

    public boolean hasFireImmunity() {
        return fireImmunity;
    }

    public boolean hasIceImmunity() {
        return iceImmunity;
    }

    public boolean hasEarthImmunity() {
        return earthImmunity;
    }

    public List<Spell> getAbilities() {
        return abilities;
    }
}

// clasa Spell
abstract class Spell implements Visitor<Entity> {
    protected int damage;
    protected int manaCost;
    protected SpellType type;

    public Spell(int damage, int manaCost, SpellType type) {
        this.damage = damage;
        this.manaCost = manaCost;
        this.type = type;
    }

    public int getDamage() {
        return damage;
    }

    public int getManaCost() {
        return manaCost;
    }

    public SpellType getType() {
        return type;
    }

    // afisare detalii abilitate
    @Override
    public String toString() {
        return type + " " + "Damage: " + damage + ", Mana Cost: " + manaCost;
    }

    public static ImageIcon getImageForSpell(SpellType type) {
        String path = "";
        switch (type) {
            case FIRE:
                path = "src/resources/fire.png";
                break;
            case ICE:
                path = "src/resources/ice.png";
                break;
            case EARTH:
                path = "src/resources/earth.png";
                break;
            default:
                throw new IllegalArgumentException("Nu se recunoaste aceasta abilitate: " + type);
        }
        return new ImageIcon(path);
    }
}

// enumerare pentru abilitati
enum SpellType {
    FIRE, ICE, EARTH;
}

// subclase pentru fiecare abilitate
class Fire extends Spell {
    public Fire(int damage, int manaCost) {
        super(damage, manaCost, SpellType.FIRE);
    }
    @Override
    public void visit(Entity entity) {
        if (entity.isFireImmune()) {
            System.out.println("Tinta e imuna la foc!");
        } else {
            System.out.println("Focul afecteaza tinta!");
            entity.receiveDamage(damage);
        }
    }
}

class Ice extends Spell {
    public Ice(int damage, int manaCost) {
        super(damage, manaCost, SpellType.ICE);
    }
    @Override
    public void visit(Entity entity) {
        if (entity.isIceImmune()) {
            System.out.println("Tinta e imuna la gheata!");
        } else {
            System.out.println("Gheata afecteaza tinta!");
            entity.receiveDamage(damage);
        }
    }
}

class Earth extends Spell {
    public Earth(int damage, int manaCost) {
        super(damage, manaCost, SpellType.EARTH);
    }
    @Override
    public void visit(Entity entity) {
        if (entity.isEarthImmune()) {
            System.out.println("Tinta e imuna la pamant!");
        } else {
            System.out.println("Pamantul afecteaza tinta!");
            entity.receiveDamage(damage);
        }
    }
}
