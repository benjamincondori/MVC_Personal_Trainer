package com.android.kotlin.personaltrainer.model.CategoriaEjercicio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.kotlin.personaltrainer.model.Database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MCategoriaEjercicio {

    private final DatabaseHelper db;

    public MCategoriaEjercicio(Context context) {
        this.db = new DatabaseHelper(context);
    }

    // Agregar una nueva categoria de ejercicio
    public long insertarCategoriaEjercicio(CategoriaEjercicio categoriaEjercicio) {
        SQLiteDatabase dbHelper = null;
        long resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.COLUMN_NOMBRE, categoriaEjercicio.getNombre());
            values.put(DatabaseHelper.COLUMN_DESCRIPCION, categoriaEjercicio.getDescripcion());
            resultado = dbHelper.insert(DatabaseHelper.TABLE_CATEGORIA_EJERCICIO, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Actualizar una categoria de ejercicio
    public int actualizarCategoriaEjercicio(CategoriaEjercicio categoriaEjercicio) {
        SQLiteDatabase dbHelper = null;
        int resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.COLUMN_NOMBRE, categoriaEjercicio.getNombre());
            values.put(DatabaseHelper.COLUMN_DESCRIPCION, categoriaEjercicio.getDescripcion());

            String whereClause = DatabaseHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(categoriaEjercicio.getId())};

            resultado = dbHelper.update(DatabaseHelper.TABLE_CATEGORIA_EJERCICIO, values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Eliminar una categoria de ejercicio
    public int eliminarCategoriaEjercicio(int id) {
        SQLiteDatabase dbHelper = null;
        int resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            String whereClause = DatabaseHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(id)};

            resultado = dbHelper.delete(DatabaseHelper.TABLE_CATEGORIA_EJERCICIO, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Obtener todos los tipos de ejercicios
    public List<CategoriaEjercicio> obtenerTodasLasCategorias() {
        SQLiteDatabase dbHelper = null;
        Cursor cursor = null;
        List<CategoriaEjercicio> listadoTiposEjercicios = new ArrayList<>();

        try {
            dbHelper = db.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseHelper.TABLE_CATEGORIA_EJERCICIO;
            cursor = dbHelper.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    CategoriaEjercicio categoriaEjercicio = new CategoriaEjercicio();
                    categoriaEjercicio.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                    categoriaEjercicio.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE)));
                    categoriaEjercicio.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPCION)));
                    listadoTiposEjercicios.add(categoriaEjercicio);
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
        return listadoTiposEjercicios;
    }

}
