package com.rolandoselvera.testmvvmjava.data.local.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper instance;
    private static final String DATABASE_NAME = "PRODUCTS_DATABASE";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PRODUCTS = "products";
    public static final String SUPPLYING_ID = "idAbastecimiento";
    public static final String SUPPLYING_TYPE = "tipoAbastecimiento";
    public static final String USER_CREATION = "usuarioCreacion";
    public static final String USER_MODIFICATION = "usuarioModificacion";
    public static final String USER_DELETION = "usuarioEliminacion";
    public static final String DATE_CREATION = "fechaCreacion";
    public static final String DATE_MODIFICATION = "fechaModificacion";
    public static final String DATE_DELETION = "fechaEliminacion";
    public static final String SUPPLYING_STATUS = "estatusAbastecimiento";

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private static final String CREATE_TABLE_PRODUCTS =
            "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                    SUPPLYING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    SUPPLYING_TYPE + " TEXT," +
                    USER_CREATION + " TEXT," +
                    USER_MODIFICATION + " TEXT," +
                    USER_DELETION + " TEXT," +
                    DATE_CREATION + " TEXT," +
                    DATE_MODIFICATION + " TEXT," +
                    DATE_DELETION + " TEXT," +
                    SUPPLYING_STATUS + " TEXT" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle database updates...
    }
}
