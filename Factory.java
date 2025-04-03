public class Factory {

    // enumerare cu tipuri de personaje
    public enum CharacterType {
        WARRIOR, MAGE, ROGUE
    }

    // metoda pt crearea personajelor
    public static Character createCharacter(CharacterType characterType, String name, int level, int experience) {
        switch (characterType) {
            case WARRIOR:
                return new Warrior(name, level, experience);
            case MAGE:
                return new Mage(name, level, experience);
            case ROGUE:
                return new Rogue(name, level, experience);
            default:
                throw new IllegalArgumentException("Tipul de personaj " + characterType + " nu este recunoscut");
        }
    }
}
