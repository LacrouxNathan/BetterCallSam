package fr.kounecorp.bettercallsam.AccueilAndConso;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DB_NAME = "SAMDB";

    private static final String TABLE_NAME = "ALCOOL_TABLE";
    private static final String COL1 = "IDALCOOL";
    private static final String COL2 = "NOM";
    private static final String COL3 = "DEGRE";
    private static final String TABLE2_NAME = "UTILISATEUR";
    private static final String COL21 = "IDUTIL";
    private static final String COL22 = "TAILLE";
    private static final String COL23 = "POIDS";
    private static final String COL24 = "NOM";
    private static final String TABLE3_NAME = "CONSOMMER";
    private static final String COL33 = "IDCONSO";
    private static final String COL31 = "NBVERRE";
    private static final String COL32 = "HEURE";
    private static final String TABLE4_NAME = "CONSOJOUR";
    private static final String COL41 = "IDCONSOJOUR";
    private static final String COL42 = "NBVERREHEURE";
    private static final String COL43 = "HEURECONSOJOUR";




    public DatabaseHelper(Context context) {
        super(context, DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " +TABLE_NAME +" ("+COL1 +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL2 +" VARCHAR(10), " +COL3 +" INTEGER)";
        String createTable2 = "CREATE TABLE " +TABLE2_NAME +" ("+COL21 +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL22 +" INTEGER, " + COL24 +" VARCHAR(20), " +COL23 +" INTEGER)";
        String createTable3 = "CREATE TABLE " +TABLE3_NAME +" ("+COL33 +" INTEGER PRIMARY KEY AUTOINCREMENT, " +COL1 +" INTEGER, "
                + COL21 +" INTEGER, "+ COL31 +" INTEGER, " +COL32 +" DATETIME, " + " FOREIGN KEY ("+COL21 +") REFERENCES " + TABLE2_NAME +"(" +COL21 +")," + " FOREIGN KEY ("+COL1 +") REFERENCES " + TABLE_NAME +"(" +COL1 +"))";
        String createTable4 = "CREATE TABLE " +TABLE4_NAME +" ("+COL41 +" INTEGER PRIMARY KEY AUTOINCREMENT, " +COL1 +" INTEGER, "
                + COL21 +" INTEGER, "+ COL42 +" INTEGER, " +COL43 +" DATETIME, " + " FOREIGN KEY ("+COL21 +") REFERENCES " + TABLE3_NAME +"(" +COL21 +")," + " FOREIGN KEY ("+COL1 +") REFERENCES " + TABLE3_NAME +"(" +COL1 +"))";
        db.execSQL(createTable);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
        db.execSQL(createTable4);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " +TABLE_NAME);
        db.execSQL("DROP TABLE " +TABLE2_NAME);
        db.execSQL("DROP TABLE " +TABLE3_NAME);
        onCreate(db);

    }


    public void addInfo(String nom, int degre, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Alcool.NewAlcoolInfo.NOM_ALCOOL,nom);
        contentValues.put(Alcool.NewAlcoolInfo.DEGRE_ALCOOL,degre);
        db.insert(Alcool.NewAlcoolInfo.TABLE_NAME,null,contentValues);
        Log.e("Database operations","row inserted");

    }

    public void delAllInfo(SQLiteDatabase db) {
        db.delete(TABLE_NAME,null,null);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = \'" + TABLE_NAME + "\'");

    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public Alcool getDataWhereId(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM ALCOOL_TABLE WHERE IDALCOOL = " +id;
        Cursor data = db.rawQuery(query,null);
        Alcool A = null;
        String name = "";
        if (data.moveToFirst() != false) {
            name = data.getString(1);
            int degre = data.getInt(2);
            System.out.println(name);
            System.out.println(degre);
            A = new Alcool(name,degre);
            data.close();

        }
        return A;
    }

    public void addConsomation(int IDUSER, int IDALCOOL, int NBVERRE) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        ContentValues contentValuesJour = new ContentValues();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = sdf.format(new Date());
        SimpleDateFormat sdfOnlyD = new SimpleDateFormat("yyyy-MM-dd");
        String strDateOnlyD = sdfOnlyD.format(new Date());
        Log.e("TESTTTTTT",strDateOnlyD);
        //ajout a la table consommation du jour
        contentValuesJour.put(Consommer.NewConsoInfoJour.ID_ALCOOL,IDALCOOL);
        contentValuesJour.put(Consommer.NewConsoInfoJour.ID_UTIL,IDUSER);

        contentValuesJour.put(Consommer.NewConsoInfoJour.NBVERRE,NBVERRE);
        contentValuesJour.put(Consommer.NewConsoInfoJour.HEURE,strDate);
        db.insert(Consommer.NewConsoInfoJour.TABLE_NAME,null,contentValuesJour);

        //ajout a la table consommation totale ou alors update du nombre de verres

        if (!getIfAlcoolDejaConsoAujd(IDUSER,IDALCOOL,strDateOnlyD)) {
            contentValues.put(Consommer.NewConsoInfo.ID_ALCOOL,IDALCOOL);
            contentValues.put(Consommer.NewConsoInfo.ID_UTIL,IDUSER);
            contentValues.put(Consommer.NewConsoInfo.NBVERRE,NBVERRE);
            contentValues.put(Consommer.NewConsoInfo.HEURE,strDate);
            db.insert(Consommer.NewConsoInfo.TABLE_NAME,null,contentValues);
        } else {
            Log.e("SLT","this is a test");
            updateNbVerres(IDUSER,IDALCOOL,NBVERRE,strDateOnlyD);

        }

        Log.e("Database operations","row inserted");
    }

    public boolean getIfAlcoolDejaConsoAujd(int IDUSER, int IDALCOOL, String DATEH) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM CONSOMMER WHERE CONSOMMER.IDUTIL = \'" +IDUSER +"\' AND CONSOMMER.IDALCOOL =\'" +IDALCOOL +"\' AND CONSOMMER.HEURE LIKE \'"+ DATEH +"%\'";
        Cursor data = db.rawQuery(query,null);
        boolean ret = false;
        if (data.moveToFirst() != false) {
            ret = true;

        }
        return ret;
    }

    public void updateNbVerres(int IDUSER, int IDALCOOL, int NB, String DATEH) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE CONSOMMER SET NBVERRE = NBVERRE + \'" +NB +"\' WHERE CONSOMMER.IDUTIL = \'" +IDUSER +"\' AND CONSOMMER.IDALCOOL =\'" +IDALCOOL +"\' AND CONSOMMER.HEURE LIKE \'"+ DATEH +"%\'";
        db.execSQL(query);
    }

    public Utilisateur getUserWhereName(String nom) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM UTILISATEUR WHERE UTILISATEUR.NOM = \'" +nom +"\'";
        Cursor data = db.rawQuery(query,null);
        Utilisateur U = null;
        String name = "";
        if (data.moveToFirst() != false) {
            int taille = data.getInt(1);
            int poids = data.getInt(3);
            name = data.getString(2);
            U = new Utilisateur(taille,poids,name);
            data.close();;
        }
        return U;
    }

    public String getIDWhereName(String nom) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM UTILISATEUR WHERE UTILISATEUR.NOM = \'" +nom +"\'";
        Cursor data = db.rawQuery(query,null);
        String ID = "";
        if (data.moveToFirst() != false) {
            ID = data.getString(0);
            data.close();;
        }
        return ID;
    }

    public void createUser(String name,int taille, int poids, SQLiteDatabase db) {
        String IDTEST = getIDWhereName(name);
        if (IDTEST == "") {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Utilisateur.NewUserInfo.TAILLE, taille);
            contentValues.put(Utilisateur.NewUserInfo.POIDS, poids);
            contentValues.put(Utilisateur.NewUserInfo.NOM, name);
            db.insert(Utilisateur.NewUserInfo.TABLE_NAME, null, contentValues);
            Log.e("Database operations", "row inserted");
        }



    }

    public Cursor getConsoWhereID(String IDUtil) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM CONSOMMER WHERE CONSOMMER.IDUTIL = \'" +IDUtil +"\'";
        Cursor data = db.rawQuery(query,null);
        return data;

    }



    public int getDegWhereID (String IDAlcool) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM ALCOOL_TABLE WHERE ALCOOL_TABLE.IDALCOOL = \'" +IDAlcool +"\'";
        Cursor data = db.rawQuery(query,null);
        int deg = 0;
        if (data.moveToFirst() != false) {
            deg = data.getInt(2);
            data.close();
        }
        return deg;
    }

    public Cursor getConsoWhereDateAndAlc(String datec, int IdALC) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM CONSOJOUR WHERE CONSOJOUR.HEURECONSOJOUR LIKE \'"+ datec +"%\' AND CONSOJOUR.IDALCOOL = \'"+IdALC+"\'";
        Cursor data = db.rawQuery(query,null);
        return data;

    }

    public int getIDAlcWhereIDCONSO(int IDCONSO) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM CONSOMMER WHERE CONSOMMER.IDCONSO = \'" +IDCONSO +"\'";
        Cursor data = db.rawQuery(query,null);
        int ID = 0;
        if (data.moveToFirst() != false) {
            ID = data.getInt(1);
            data.close();
        }
        return ID;

    }


    public String getDateWhereID(int idConso) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM CONSOMMER WHERE CONSOMMER.IDCONSO = \'" +idConso +"\'";
        Cursor data = db.rawQuery(query,null);
        String Date = "";
        if (data.moveToFirst() != false) {
            Date = data.getString(4);
            data.close();
        }
        return Date;
    }












}
