import java.util.*;

// clasele Entity si Character

// interfata Battle
interface Battle {
    void receiveDamage(int damage);
    int getDamage();
}

// clasa Entity
public abstract class Entity implements Element<Entity> {
    protected List<Spell> abilities;
    protected int currentHealth;
    protected int maxHealth;
    protected int currentMana;
    protected int maxMana;
    protected boolean fireImmunity;
    protected boolean iceImmunity;
    protected boolean earthImmunity;

    // constructor
    public Entity(int maxHealth, int maxMana, boolean immuneFire, boolean immuneIce, boolean immuneEarth) {
        this.abilities = new ArrayList<>();
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.fireImmunity = immuneFire;
        this.iceImmunity = immuneIce;
        this.earthImmunity = immuneEarth;
    }

    // regenerare viata
    public void regenerateHealth(int amount) {
        if(this.currentHealth + amount < this.maxHealth)
            this.currentHealth = this.currentHealth + amount;
        else
            this.currentHealth = this.maxHealth;
    }

    // regenerare mana
    public void regenerateMana(int amount) {
        if(this.currentMana + amount < this.maxMana)
            this.currentMana = this.currentMana + amount;
        else
            this.currentMana = this.maxMana;
    }

    // folosire abilitate
    public boolean useAbility(Spell spell, Entity enemy) {
        // verific daca jucatorul are mana pt abilitate
        if (currentMana < spell.getManaCost()) {
            System.out.println("Nu ai suficienta mana pentru abilitate " + spell.getClass().getSimpleName());
            return false;
        }

//        // verific daca tinta e imuna la abilitate
//        if ((spell instanceof Fire && enemy.isFireImmune()) ||
//                (spell instanceof Ice && enemy.isIceImmune()) ||
//                (spell instanceof Earth && enemy.isEarthImmune())) {
//            System.out.println("Tinta e imuna la " + spell.getClass().getSimpleName());
//            abilities.remove(spell);
//            return false;
//        }
        // Aplic abilitatea asupra țintei prin Visitor Pattern
        enemy.accept(spell);
        // scad mana folosita
        currentMana -= spell.getManaCost();
        // aplic demage-ul tintei
       //  enemy.receiveDamage(spell.getDamage());
        // sterg abilitatea
        abilities.remove(spell);
        System.out.println("Abilitatea " + spell.getClass().getSimpleName() + " a fost utilizata");
        return true;
    }

    // verific imunitatile
    public boolean isFireImmune() {
        return fireImmunity;
    }

    public boolean isIceImmune() {
        return iceImmunity;
    }

    public boolean isEarthImmune() {
        return earthImmunity;
    }

    // generare 3-6 abilitati random
    protected void generateAbilities() {
        Random rand = new Random();
        // creez abilitati, cu damage si mana alese
        Fire fireAbility = new Fire(rand.nextInt(11) + 15, rand.nextInt(11) + 20);
        Ice iceAbility = new Ice(rand.nextInt(11) + 25, rand.nextInt(11) + 30);
        Earth earthAbility = new Earth(rand.nextInt(11) + 20, rand.nextInt(11) + 40);

        // adaug cate o abilitate de fiecare tip
        this.abilities.add(fireAbility);
        this.abilities.add(iceAbility);
        this.abilities.add(earthAbility);

        // generez un nr intre 0 si 3
        int addAbilities = rand.nextInt(4);
        for (int i = 0; i < addAbilities; i++) {
            // generez alte numere pentru a alege ce abilitate sa am
            int spellType = rand.nextInt(3);
            if (spellType == 2)
                this.abilities.add(fireAbility);
            else if (spellType == 1)
                this.abilities.add(iceAbility);
            else
                this.abilities.add(earthAbility);
        }
    }

    // metode abstracte
    public abstract void receiveDamage(int damage);
    public abstract int getDamage();

    // metoda accept pt Visitor
    @Override
    public void accept(Visitor<Entity> visitor) {
        visitor.visit(this);
    }
}

abstract class Character extends Entity {
    private int enemiesKilled = 0;
    protected String name;
    protected int experience;
    protected int level;
    protected String profession;
    // atribute
    protected int strength;
    protected int charisma;
    protected int dexterity;
    Random rand = new Random();

    // constructori si metode de get
    public Character(String name, int maxHealth, int maxMana, int strength, int charisma, int dexterity, String profession) {
        super(maxHealth, maxMana, false, false, false);
        this.name = name;
        this.strength = strength;
        this.charisma = charisma;
        this.dexterity = dexterity;
        this.profession = profession;
    }

    public Character(String name, int level, int experience) {
        super(100, 100, false, false, false);
        this.name = name;
        this.level = level;
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getCharisma() {
        return charisma;
    }

    public int getStrength() {
        return strength;
    }

    public String getProfession() {
        return profession;
    }

    // incrementare inamici ucisi
    public void incrementEnemiesKilled() {
        this.enemiesKilled++;
        System.out.println("Numar actualizat de inamici ucisi: " + this.enemiesKilled);
    }

    public int getEnemiesKilled() {
        return this.enemiesKilled;
    }

    // metode abstracte
    public abstract int getDamage();
    public abstract void receiveDamage(int damage);
}
