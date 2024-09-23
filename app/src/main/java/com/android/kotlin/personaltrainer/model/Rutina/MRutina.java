package com.android.kotlin.personaltrainer.model.Rutina;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.kotlin.personaltrainer.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MRutina {

    private final DatabaseHelper db;

    public MRutina(Context context) {
        this.db = new DatabaseHelper(context);
    }

    // Agregar una nueva rutina
    public long insertarRutina(Rutina rutina) {
        SQLiteDatabase dbHelper = null;
        long resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.COLUMN_NOMBRE, rutina.getNombre());
            values.put(DatabaseHelper.COLUMN_DESCRIPCION, rutina.getDescripcion());
            resultado = dbHelper.insert(DatabaseHelper.TABLE_RUTINA, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Actualizar una rutina
    public int actualizarRutina(Rutina rutina) {
        SQLiteDatabase dbHelper = null;
        int resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.COLUMN_NOMBRE, rutina.getNombre());
            values.put(DatabaseHelper.COLUMN_DESCRIPCION, rutina.getDescripcion());

            String whereClause = DatabaseHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(rutina.getId())};

            resultado = dbHelper.update(DatabaseHelper.TABLE_RUTINA, values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Eliminar una rutina
    public int eliminarRutina(int id) {
        SQLiteDatabase dbHelper = null;
        int resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            String whereClause = DatabaseHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(id)};
            resultado = dbHelper.delete(DatabaseHelper.TABLE_RUTINA, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Obtener todas las rutinas
    public List<Rutina> obtenerTodasLasRutinas() {
        SQLiteDatabase dbHelper = null;
        Cursor cursor = null;
        List<Rutina> listadoRutinas = new ArrayList<>();

        try {
            dbHelper = db.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseHelper.TABLE_RUTINA;
            cursor = dbHelper.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Rutina rutina = new Rutina();
                    rutina.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                    rutina.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE)));
                    rutina.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPCION)));
                    listadoRutinas.add(rutina);
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
        return listadoRutinas;
    }

    // Guardar detalle de rutina
    public long insertarDetalleRutina(DetalleRutinaEjercicio detalle) {
        SQLiteDatabase dbHelper = null;
        long resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.COLUMN_ID_RUTINA, detalle.getIdRutina());
            values.put(DatabaseHelper.COLUMN_ID_EJERCICIO, detalle.getIdEjercicio());
            values.put(DatabaseHelper.COLUMN_SERIES, detalle.getSeries());
            values.put(DatabaseHelper.COLUMN_REPETICIONES, detalle.getRepeticiones());
            values.put(DatabaseHelper.COLUMN_DESCANSO, detalle.getDescanso());
            resultado = dbHelper.insert(DatabaseHelper.TABLE_DETALLE_RUTINA_EJERCICIO, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Eliminar un ejercicio del detalle de una rutina
    public int eliminarDetalleRutinaEjercicio(int idRutina, int idEjercicio) {
        SQLiteDatabase dbHelper = null;
        int resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            String whereClause = DatabaseHelper.COLUMN_ID_RUTINA + " = ? AND " + DatabaseHelper.COLUMN_ID_EJERCICIO + " = ?";
            String[] whereArgs = {String.valueOf(idRutina), String.valueOf(idEjercicio)};
            resultado = dbHelper.delete(DatabaseHelper.TABLE_DETALLE_RUTINA_EJERCICIO, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Eliminar detalle de rutina
//    public int eliminarDetalleRutina(int idRutina) {
//        SQLiteDatabase dbHelper = null;
//        int resultado = -1;
//
//        try {
//            dbHelper = db.getWritableDatabase();
//            String whereClause = DatabaseHelper.COLUMN_ID_RUTINA + " = ?";
//            String[] whereArgs = {String.valueOf(idRutina)};
//            resultado = dbHelper.delete(DatabaseHelper.TABLE_DETALLE_RUTINA_EJERCICIO, whereClause, whereArgs);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (dbHelper != null) {
//                dbHelper.close();
//            }
//        }
//        return resultado;
//    }

    // Obtener detalle de rutina
    public List<DetalleRutinaEjercicio> obtenerDetalleRutina(int idRutina) {
        SQLiteDatabase dbHelper = null;
        Cursor cursor = null;
        List<DetalleRutinaEjercicio> listadoDetalle = new ArrayList<>();

        try {
            dbHelper = db.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseHelper.TABLE_DETALLE_RUTINA_EJERCICIO +
                    " WHERE " + DatabaseHelper.COLUMN_ID_RUTINA + " = ?";
            String[] selectionArgs = {String.valueOf(idRutina)};
            cursor = dbHelper.rawQuery(query, selectionArgs);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    DetalleRutinaEjercicio detalle = new DetalleRutinaEjercicio();
                    detalle.setIdRutina(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_RUTINA)));
                    detalle.setIdEjercicio(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_EJERCICIO)));
                    detalle.setSeries(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_SERIES)));
                    detalle.setRepeticiones(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_REPETICIONES)));
                    detalle.setDescanso(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCANSO)));
                    listadoDetalle.add(detalle);
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
        return listadoDetalle;
    }

}
