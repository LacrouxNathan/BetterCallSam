package fr.kounecorp.bettercallsam.AccueilAndConso;

public class Alcool {

    private String nomAlc;
    private int degre;

    public Alcool(String nom,int deg) {
        this.nomAlc = nom;
        this.degre = deg;
    }

    public String getNomAlc() {
        return nomAlc;
    }

    public void setNomAlc(String nomAlc) {
        this.nomAlc = nomAlc;
    }

    public int getDegre() {
        return degre;
    }

    public void setDegre(int degre) {
        this.degre = degre;
    }



    public static abstract class NewAlcoolInfo {
        public static final String TABLE_NAME ="ALCOOL_TABLE";
        public static final String NOM_ALCOOL = "NOM";
        public static final String DEGRE_ALCOOL = "DEGRE";
    }
}
