package com.android.kotlin.personaltrainer.model.EstadoFisico;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.kotlin.personaltrainer.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MEstadoFisico {

    private final DatabaseHelper db;

    public MEstadoFisico(Context context) {
        this.db = new DatabaseHelper(context);
    }

    // Agregar un nuevo estado fisico
    public long insertarEstadoFisico(EstadoFisico estadoFisico) {
        SQLiteDatabase dbHelper = null;
        long resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.COLUMN_PESO, estadoFisico.getPeso());
            values.put(DatabaseHelper.COLUMN_ESTATURA, estadoFisico.getEstatura());
            values.put(DatabaseHelper.COLUMN_FECHA, estadoFisico.getFecha());
            values.put(DatabaseHelper.COLUMN_ENFERMEDADES, estadoFisico.getEnfermedades());
            values.put(DatabaseHelper.COLUMN_ID_CLIENTE, estadoFisico.getIdCliente());

            resultado = dbHelper.insert(DatabaseHelper.TABLE_ESTADO_FISICO, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Actualizar un estado fisico
    public int actualizarEstadoFisico(EstadoFisico estadoFisico) {
        SQLiteDatabase dbHelper = null;
        int resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.COLUMN_PESO, estadoFisico.getPeso());
            values.put(DatabaseHelper.COLUMN_ESTATURA, estadoFisico.getEstatura());
            values.put(DatabaseHelper.COLUMN_FECHA, estadoFisico.getFecha());
            values.put(DatabaseHelper.COLUMN_ENFERMEDADES, estadoFisico.getEnfermedades());
            values.put(DatabaseHelper.COLUMN_ID_CLIENTE, estadoFisico.getIdCliente());

            String whereClause = DatabaseHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(estadoFisico.getId())};

            resultado = dbHelper.update(DatabaseHelper.TABLE_ESTADO_FISICO, values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Eliminar un estado fisico
    public int eliminarEstadoFisico(int id) {
        SQLiteDatabase dbHelper = null;
        int resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            String whereClause = DatabaseHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(id)};

            resultado = dbHelper.delete(DatabaseHelper.TABLE_ESTADO_FISICO, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Obtener un estado fisico
    public EstadoFisico obtenerEstadoFisico(int id) {
        SQLiteDatabase dbHelper = null;
        Cursor cursor = null;
        EstadoFisico estadoFisico = null;

        try {
            dbHelper = db.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseHelper.TABLE_ESTADO_FISICO + " WHERE " + DatabaseHelper.COLUMN_ID + " = ?";
            String[] selectionArgs = {String.valueOf(id)};

            cursor = dbHelper.rawQuery(query, selectionArgs);

            if (cursor != null && cursor.moveToFirst()) {
                estadoFisico = new EstadoFisico();
                estadoFisico.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                estadoFisico.setPeso(cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PESO)));
                estadoFisico.setEstatura(cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ESTATURA)));
                estadoFisico.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA)));
                estadoFisico.setEnfermedades(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ENFERMEDADES)));
                estadoFisico.setIdCliente(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_CLIENTE)));
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
        return estadoFisico;
    }

    // Obtener todos los estados fisicos
    public List<EstadoFisico> obtenerTodosLosEstadosFisicos() {
        SQLiteDatabase dbHelper = null;
        Cursor cursor = null;
        List<EstadoFisico> listadoEstadoFisico = new ArrayList<>();;

        try {
            dbHelper = db.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseHelper.TABLE_ESTADO_FISICO;
            cursor = dbHelper.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    EstadoFisico estadoFisico = new EstadoFisico();
                    estadoFisico.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                    estadoFisico.setPeso(cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PESO)));
                    estadoFisico.setEstatura(cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ESTATURA)));
                    estadoFisico.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA)));
                    estadoFisico.setEnfermedades(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ENFERMEDADES)));
                    estadoFisico.setIdCliente(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_CLIENTE)));

                    listadoEstadoFisico.add(estadoFisico);
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

        return listadoEstadoFisico;
    }

    // Obtener todos los estados fisicos de un usuario
    public List<EstadoFisico> obtenerEstadosFisicosCliente(int idCliente) {
        SQLiteDatabase dbHelper = null;
        Cursor cursor = null;
        List<EstadoFisico> listadoEstadoFisico = null;

        try {
            dbHelper = db.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseHelper.TABLE_ESTADO_FISICO + " WHERE " + DatabaseHelper.COLUMN_ID_CLIENTE + " = ?";
            String[] selectionArgs = {String.valueOf(idCliente)};

            cursor = dbHelper.rawQuery(query, selectionArgs);

            if (cursor != null && cursor.moveToFirst()) {
                listadoEstadoFisico = new ArrayList<>();
                do {
                    EstadoFisico estadoFisico = new EstadoFisico();
                    estadoFisico.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                    estadoFisico.setPeso(cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PESO)));
                    estadoFisico.setEstatura(cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ESTATURA)));
                    estadoFisico.setFecha(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA)));
                    estadoFisico.setEnfermedades(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ENFERMEDADES)));
                    estadoFisico.setIdCliente(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_CLIENTE)));

                    listadoEstadoFisico.add(estadoFisico);
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

        return listadoEstadoFisico;
    }


}
