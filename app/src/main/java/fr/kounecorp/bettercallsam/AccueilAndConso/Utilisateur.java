package fr.kounecorp.bettercallsam.AccueilAndConso;

public class Utilisateur {

        private int taille;
        private int poids;
        private String name;



        public Utilisateur(int taille, int poids, String name) {
            this.taille = taille;
            this.poids = poids;
            this.name = name;

        }

        public int getTaille() {
            return taille;
        }

        public void setTaille(int taille) {
            this.taille = taille;
        }

        public int getPoids() {
            return poids;
        }

        public void setPoids(int poids) {
            this.poids = poids;
        }

        public String getName() {
            return name;
        }

         public void setName(String name) {
             this.name = name;
         }



        public static abstract class NewUserInfo {
            public static final String TABLE_NAME ="UTILISATEUR";
            public static final String TAILLE = "TAILLE";
            public static final String POIDS = "POIDS";
            public static final String NOM = "NOM";
        }
    }


