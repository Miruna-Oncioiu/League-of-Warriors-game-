import java.util.ArrayList;
import java.util.SortedSet;

// clasele Account, Information si Credentials
public class Account {
    private Information info;
    private ArrayList<Character> characters;
    private int numberGames;

    // constructor si metodele get
    public Account(ArrayList<Character> c, int g, Information i) {
        this.info = i;
        this.characters = c;
        this.numberGames = g;
    }

    public Information getInformation() {
        return this.info;
    }

    public ArrayList<Character> getCharacters() {
        return this.characters;
    }

    public int getGamesPlayed() {
        return this.numberGames;
    }

    // incrementez nivelul jocului
    public void incrementGamesPlayed() {
        this.numberGames++;
    }

    // clasa Information
    public static class Information {
        private Credentials credentials;
        private SortedSet<String> favoriteGames;
        private String name;
        private String country;

        // constructor inainte de Builder si metode get
//        public Information(Credentials cr, SortedSet<String> f, String n, String c) {
//            this.credentials = cr;
//            this.favoriteGames = f;
//            this.name = n;
//            this.country = c;
//        }

        // constructor cu builder
        private Information(informationBuilder builder) {
            this.credentials = builder.credentials;
            this.favoriteGames = builder.favoriteGames;
            this.name = builder.name;
            this.country = builder.country;
        }

        public Credentials getCredentials() {
            return this.credentials;
        }

        public String getName() {
            return this.name;
        }

        public String getCountry() {
            return this.country;
        }

        public SortedSet<String> getFavoriteGames() {
            return this.favoriteGames;
        }

        // clasa Builder
        public static class informationBuilder {
            private Credentials credentials;
            private SortedSet<String> favoriteGames;
            private String name;
            private String country;

            public informationBuilder(Credentials cr, SortedSet<String> f, String n, String c) {
                this.credentials = cr;
                this.favoriteGames = f;
                this.name = n;
                this.country = c;
            }

            public informationBuilder() {
            }

            // metode de set
            public informationBuilder setCredentials(Credentials credentials) {
                this.credentials = credentials;
                return this;
            }

            public informationBuilder setFavoriteGames(SortedSet<String> favoriteGames) {
                this.favoriteGames = favoriteGames;
                return this;
            }

            public informationBuilder setName(String name) {
                this.name = name;
                return this;
            }

            public informationBuilder setCountry(String country) {
                this.country = country;
                return this;
            }

            // construiesc obiectului Information
            public Information build() {
                return new Information(this);
            }
        }
    }
}

// clasa Credentials
class Credentials {
    private String email;
    private String password;

    // constructor si metode get
    public Credentials(String e, String p) {
        this.email = e;
        this.password = p;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}