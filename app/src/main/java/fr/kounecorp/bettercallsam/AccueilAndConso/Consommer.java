package fr.kounecorp.bettercallsam.AccueilAndConso;

public class Consommer {


    private String nomAlcool;
    private int nbVerres;
    private String dateheure;
    private int deg;

    public Consommer(String nomAlcool,int nbVerres,String dateheure, int deg) {
        this.nomAlcool = nomAlcool;
        this.nbVerres = nbVerres;
        this.dateheure = dateheure;
        this.deg = deg;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public String getNomAlcool() {
        return nomAlcool;
    }

    public void setNomAlcool(String nomAlcool) {
        this.nomAlcool = nomAlcool;
    }

    public int getNbVerres() {
        return nbVerres;
    }

    public void setNbVerres(int nbVerres) {
        this.nbVerres = nbVerres;
    }

    public String getDateheure() {
        return dateheure;
    }

    public void setDateheure(String dateheure) {
        this.dateheure = dateheure;
    }

    public static abstract class NewConsoInfo {
        public static final String TABLE_NAME ="CONSOMMER";
        public static final String ID_ALCOOL = "IDALCOOL";
        public static final String ID_UTIL = "IDUTIL";
        public static final String NBVERRE = "NBVERRE";
        public static final String HEURE = "HEURE";
    }

    public static abstract class NewConsoInfoJour {
        public static final String TABLE_NAME ="CONSOJOUR";
        public static final String ID_ALCOOL = "IDALCOOL";
        public static final String ID_UTIL = "IDUTIL";
        public static final String NBVERRE = "NBVERREHEURE";
        public static final String HEURE = "HEURECONSOJOUR";
    }

}
