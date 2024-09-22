package com.android.kotlin.personaltrainer.model.Ejercicio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.kotlin.personaltrainer.model.Database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MEjercicio {

    private final DatabaseHelper db;

    public MEjercicio(Context context) {
        this.db = new DatabaseHelper(context);
    }

    // Agregar un nuevo ejercicio
    public long insertarEjercicio(Ejercicio ejercicio) {
        SQLiteDatabase dbHelper = null;
        long resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.COLUMN_NOMBRE, ejercicio.getNombre());
            values.put(DatabaseHelper.COLUMN_DESCRIPCION, ejercicio.getDescripcion());
            values.put(DatabaseHelper.COLUMN_IMAGEN, ejercicio.getImagen());
            values.put(DatabaseHelper.COLUMN_URL_VIDEO, ejercicio.getUrlVideo());
            values.put(DatabaseHelper.COLUMN_ID_CATEGORIA, ejercicio.getIdCategoria());
            resultado = dbHelper.insert(DatabaseHelper.TABLE_EJERCICIO, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Actualizar un ejercicio
    public int actualizarEjercicio(Ejercicio ejercicio) {
        SQLiteDatabase dbHelper = null;
        int resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.COLUMN_NOMBRE, ejercicio.getNombre());
            values.put(DatabaseHelper.COLUMN_DESCRIPCION, ejercicio.getDescripcion());
            values.put(DatabaseHelper.COLUMN_IMAGEN, ejercicio.getImagen());
            values.put(DatabaseHelper.COLUMN_URL_VIDEO, ejercicio.getUrlVideo());
            values.put(DatabaseHelper.COLUMN_ID_CATEGORIA, ejercicio.getIdCategoria());

            String whereClause = DatabaseHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(ejercicio.getId())};

            resultado = dbHelper.update(DatabaseHelper.TABLE_EJERCICIO, values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Eliminar un ejercicio
    public int eliminarEjercicio(int id) {
        SQLiteDatabase dbHelper = null;
        int resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            String whereClause = DatabaseHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(id)};
            resultado = dbHelper.delete(DatabaseHelper.TABLE_EJERCICIO, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Obtener un ejercicio por su id
    public Ejercicio obtenerEjercicioPorId(int idEjercicio) {
        SQLiteDatabase dbHelper = null;
        Cursor cursor = null;
        Ejercicio ejercicio = null;

        try {
            dbHelper = db.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseHelper.TABLE_EJERCICIO + " WHERE " + DatabaseHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(idEjercicio)};
            cursor = dbHelper.rawQuery(query, whereArgs);

            if (cursor != null && cursor.moveToFirst()) {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE));
                String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPCION));
                String imagen = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGEN));
                String urlVideo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_URL_VIDEO));
                int idCategoria = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_CATEGORIA));

                ejercicio = new Ejercicio(idEjercicio, nombre, descripcion, imagen, urlVideo, idCategoria);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return ejercicio;
    }

    // Obtener todos los ejercicios
    public List<Ejercicio> obtenerTodosLosEjercicios() {
        SQLiteDatabase dbHelper = null;
        Cursor cursor = null;
        List<Ejercicio> listadoEjercicios = new ArrayList<>();

        try {
            dbHelper = db.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseHelper.TABLE_EJERCICIO;
            cursor = dbHelper.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE));
                    String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPCION));
                    String imagen = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_IMAGEN));
                    String urlVideo = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_URL_VIDEO));
                    int idCategoria = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_CATEGORIA));

                    Ejercicio ejercicio = new Ejercicio(id, nombre, descripcion, imagen, urlVideo, idCategoria);
                    listadoEjercicios.add(ejercicio);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return listadoEjercicios;
    }

}
