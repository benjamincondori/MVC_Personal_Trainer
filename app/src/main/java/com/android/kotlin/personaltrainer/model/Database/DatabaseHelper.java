package com.android.kotlin.personaltrainer.model.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EntrenadorPersonal.db";
    private static final int DATABASE_VERSION = 1;

    // Tablas
    public static final String TABLE_CLIENTE = "Cliente";
    public static final String TABLE_ESTADO_FISICO = "Estado_Fisico";
    public static final String TABLE_OBJETIVO = "Objetivo";
    public static final String TABLE_EJERCICIO = "Ejercicio";
    public static final String TABLE_DETALLE_EJERCICIO = "Detalle_Ejercicio";
    public static final String TABLE_RUTINA = "Rutina";
    public static final String TABLE_PLAN_RUTINA = "Plan_Rutina";
    public static final String TABLE_CATEGORIA_EJERCICIO = "Categoria_Ejercicio";
    public static final String TABLE_CLIENTE_OBJETIVO = "Cliente_Objetivo";
    public static final String TABLE_PLAN_RUTINA_OBJETIVO = "Plan_Rutina_Objetivo";
    public static final String TABLE_DETALLE_PLAN_RUTINA = "Detalle_Plan_Rutina";

    // Columnas En Común
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_DESCRIPCION = "descripcion";
    public static final String COLUMN_ID_CLIENTE = "id_cliente";

    // Columnas Tabla Cliente
    public static final String COLUMN_APELLIDO = "apellido";
    public static final String COLUMN_FECHA_NACIMIENTO = "fecha_nacimiento";
    public static final String COLUMN_TELEFONO = "telefono";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_GENERO = "genero";
    public static final String COLUMN_FOTO = "foto";

    // Columnas Tabla Estado Físico
    public static final String COLUMN_ESTATURA = "estatura";
    public static final String COLUMN_PESO = "peso";
    public static final String COLUMN_FECHA = "fecha";
    public static final String COLUMN_ENFERMEDADES = "enfermedades";

    // Columnas Tabla Ejercicio
    public static final String COLUMN_IMAGEN = "imagen";
    public static final String COLUMN_URL_VIDEO = "url_video";
    public static final String COLUMN_ID_CATEGORIA = "id_categoria";

    // Columnas Tabla Plan Rutina
    public static final String COLUMN_FECHA_INICIO = "fecha_inicio";
    public static final String COLUMN_FECHA_FIN = "fecha_fin";
    public static final String COLUMN_TIPO = "tipo";

    // Columnas Tabla Detalle Ejercicio
    public static final String COLUMN_SERIES = "series";
    public static final String COLUMN_REPETICIONES = "repeticiones";
    public static final String COLUMN_DESCANSO_SEGUNDOS = "descanso_segundos";
    public static final String COLUMN_ID_EJERCICIO = "id_ejercicio";
    public static final String COLUMN_ID_RUTINA = "id_rutina";

    // Columnas Tabla Cliente Objetivo
    public static final String COLUMN_ESTADO = "estado";
    public static final String COLUMN_ID_OBJETIVO = "id_objetivo";

    // Columnas Tabla Plan Rutina Objetivo
    public static final String COLUMN_ID_PLAN_RUTINA = "id_plan_rutina";

    // Columnas Tabla Detalle Plan Rutina
    public static final String COLUMN_DIA_SEMANA = "dia_semana";


    // Creación de tablas
    private static final String CREATE_TABLE_CLIENTE = "CREATE TABLE " + TABLE_CLIENTE + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NOMBRE + " TEXT NOT NULL," +
            COLUMN_APELLIDO + " TEXT NOT NULL," +
            COLUMN_FECHA_NACIMIENTO + " TEXT NOT NULL," +
            COLUMN_TELEFONO + " TEXT NOT NULL," +
            COLUMN_EMAIL + " TEXT NOT NULL UNIQUE," +
            COLUMN_GENERO + " TEXT NOT NULL," +
            COLUMN_FOTO + " TEXT" +
            ");";

    private static final String CREATE_TABLE_ESTADO_FISICO = "CREATE TABLE " + TABLE_ESTADO_FISICO + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_ESTATURA + " DECIMAL(5,2) NOT NULL," +
            COLUMN_PESO + " DECIMAL(5,2) NOT NULL," +
            COLUMN_FECHA + " TEXT NOT NULL," +
            COLUMN_ENFERMEDADES + " TEXT NOT NULL," +
            COLUMN_ID_CLIENTE + " INTEGER NOT NULL," +
            "FOREIGN KEY (" + COLUMN_ID_CLIENTE + ") REFERENCES " + TABLE_CLIENTE + "(" + COLUMN_ID + ") ON DELETE CASCADE" +
            ");";

    private static final String CREATE_TABLE_OBJETIVO = "CREATE TABLE " + TABLE_OBJETIVO + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_DESCRIPCION + " TEXT NOT NULL" +
            ");";

    private static final String CREATE_TABLE_CATEGORIA_EJERCICIO = "CREATE TABLE " + TABLE_CATEGORIA_EJERCICIO + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NOMBRE + " TEXT NOT NULL UNIQUE," +
            COLUMN_DESCRIPCION + " TEXT NOT NULL" +
            ");";

    private static final String CREATE_TABLE_EJERCICIO = "CREATE TABLE " + TABLE_EJERCICIO + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NOMBRE + " TEXT NOT NULL UNIQUE," +
            COLUMN_DESCRIPCION + " TEXT NOT NULL," +
            COLUMN_IMAGEN + " TEXT," +
            COLUMN_URL_VIDEO + " TEXT NOT NULL," +
            COLUMN_ID_CATEGORIA + " INTEGER NOT NULL," +
            "FOREIGN KEY (" + COLUMN_ID_CATEGORIA + ") REFERENCES " + TABLE_CATEGORIA_EJERCICIO + "(" + COLUMN_ID + ")" +
            ");";

    private static final String CREATE_TABLE_RUTINA = "CREATE TABLE " + TABLE_RUTINA + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NOMBRE + " TEXT NOT NULL UNIQUE," +
            COLUMN_DESCRIPCION + " TEXT NOT NULL" +
            ");";

    private static final String CREATE_TABLE_PLAN_RUTINA = "CREATE TABLE " + TABLE_PLAN_RUTINA + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_NOMBRE + " TEXT NOT NULL UNIQUE," +
            COLUMN_DESCRIPCION + " TEXT NOT NULL," +
            COLUMN_FECHA_INICIO + " TEXT NOT NULL," +
            COLUMN_FECHA_FIN + " TEXT NOT NULL," +
            COLUMN_TIPO + " TEXT NOT NULL," +
            COLUMN_ID_CLIENTE + " INTEGER NOT NULL," +
            "FOREIGN KEY (" + COLUMN_ID_CLIENTE + ") REFERENCES " + TABLE_CLIENTE + "(" + COLUMN_ID + ") ON DELETE CASCADE" +
            ");";

    private static final String CREATE_TABLE_DETALLE_EJERCICIO = "CREATE TABLE " + TABLE_DETALLE_EJERCICIO + " (" +
            COLUMN_SERIES + " INTEGER NOT NULL," +
            COLUMN_REPETICIONES + " INTEGER NOT NULL," +
            COLUMN_DESCANSO_SEGUNDOS + " INTEGER," +
            COLUMN_ID_EJERCICIO + " INTEGER," +
            COLUMN_ID_RUTINA + " INTEGER," +
            "PRIMARY KEY (" + COLUMN_ID_EJERCICIO + ", " + COLUMN_ID_RUTINA + ")," +
            "FOREIGN KEY (" + COLUMN_ID_EJERCICIO + ") REFERENCES " + TABLE_EJERCICIO + "(" + COLUMN_ID + ") ON DELETE CASCADE," +
            "FOREIGN KEY (" + COLUMN_ID_RUTINA + ") REFERENCES " + TABLE_RUTINA + "(" + COLUMN_ID + ") ON DELETE CASCADE" +
            ");";

    private static final String CREATE_TABLE_CLIENTE_OBJETIVO = "CREATE TABLE " + TABLE_CLIENTE_OBJETIVO + " (" +
            COLUMN_ESTADO + " TEXT NOT NULL," +
            COLUMN_ID_CLIENTE + " INTEGER," +
            COLUMN_ID_OBJETIVO + " INTEGER," +
            "PRIMARY KEY (" + COLUMN_ID_CLIENTE + ", " + COLUMN_ID_OBJETIVO + ")," +
            "FOREIGN KEY (" + COLUMN_ID_CLIENTE + ") REFERENCES " + TABLE_CLIENTE + "(" + COLUMN_ID + ") ON DELETE CASCADE," +
            "FOREIGN KEY (" + COLUMN_ID_OBJETIVO + ") REFERENCES " + TABLE_OBJETIVO + "(" + COLUMN_ID + ") ON DELETE CASCADE" +
            ");";

    private static final String CREATE_TABLE_PLAN_RUTINA_OBJETIVO = "CREATE TABLE " + TABLE_PLAN_RUTINA_OBJETIVO + " (" +
            COLUMN_ID_PLAN_RUTINA + " INTEGER," +
            COLUMN_ID_OBJETIVO + " INTEGER," +
            "PRIMARY KEY (" + COLUMN_ID_PLAN_RUTINA + ", " + COLUMN_ID_OBJETIVO + ")," +
            "FOREIGN KEY (" + COLUMN_ID_PLAN_RUTINA + ") REFERENCES " + TABLE_PLAN_RUTINA + "(" + COLUMN_ID + ") ON DELETE CASCADE," +
            "FOREIGN KEY (" + COLUMN_ID_OBJETIVO + ") REFERENCES " + TABLE_OBJETIVO + "(" + COLUMN_ID + ") ON DELETE CASCADE" +
            ");";

    private static final String CREATE_TABLE_DETALLE_PLAN_RUTINA = "CREATE TABLE " + TABLE_DETALLE_PLAN_RUTINA + " (" +
            COLUMN_DIA_SEMANA + " TEXT NOT NULL," +
            COLUMN_ID_PLAN_RUTINA + " INTEGER NOT NULL," +
            COLUMN_ID_RUTINA + " INTEGER NOT NULL," +
            "FOREIGN KEY (" + COLUMN_ID_PLAN_RUTINA + ") REFERENCES " + TABLE_PLAN_RUTINA + "(" + COLUMN_ID + ") ON DELETE CASCADE," +
            "FOREIGN KEY (" + COLUMN_ID_RUTINA + ") REFERENCES " + TABLE_RUTINA + "(" + COLUMN_ID + ") ON DELETE CASCADE" +
            ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CLIENTE);
        db.execSQL(CREATE_TABLE_ESTADO_FISICO);
        db.execSQL(CREATE_TABLE_OBJETIVO);
        db.execSQL(CREATE_TABLE_CATEGORIA_EJERCICIO);
        db.execSQL(CREATE_TABLE_EJERCICIO);
        db.execSQL(CREATE_TABLE_DETALLE_EJERCICIO);
        db.execSQL(CREATE_TABLE_RUTINA);
        db.execSQL(CREATE_TABLE_PLAN_RUTINA);
        db.execSQL(CREATE_TABLE_CLIENTE_OBJETIVO);
        db.execSQL(CREATE_TABLE_PLAN_RUTINA_OBJETIVO);
        db.execSQL(CREATE_TABLE_DETALLE_PLAN_RUTINA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETALLE_PLAN_RUTINA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTE_OBJETIVO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAN_RUTINA_OBJETIVO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAN_RUTINA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RUTINA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETALLE_EJERCICIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EJERCICIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIA_EJERCICIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBJETIVO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ESTADO_FISICO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTE);
        onCreate(db);
    }
}
