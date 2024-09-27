package com.android.kotlin.personaltrainer.model.PlanEntrenamiento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.kotlin.personaltrainer.database.DatabaseHelper;
import com.android.kotlin.personaltrainer.model.Cliente.Cliente;

import java.util.ArrayList;
import java.util.List;

public class MPlanEntrenamiento {

    private final DatabaseHelper db;

    public MPlanEntrenamiento(Context context) {
        this.db = new DatabaseHelper(context);
    }

    // Crear un nuevo plan de entrenamiento
    public long insertarPlanEntrenamiento(PlanEntrenamiento planEntrenamiento) {
        SQLiteDatabase dbHelper = null;
        long resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.COLUMN_NOMBRE, planEntrenamiento.getNombre());
            values.put(DatabaseHelper.COLUMN_DESCRIPCION, planEntrenamiento.getDescripcion());
            values.put(DatabaseHelper.COLUMN_FECHA_INICIO, planEntrenamiento.getFechaInicio());
            values.put(DatabaseHelper.COLUMN_FECHA_FIN, planEntrenamiento.getFechaFin());
            values.put(DatabaseHelper.COLUMN_ID_CLIENTE, planEntrenamiento.getIdCliente());
            values.put(DatabaseHelper.COLUMN_TIPO, planEntrenamiento.getTipo());
            resultado = dbHelper.insert(DatabaseHelper.TABLE_PLAN_ENTRENAMIENTO, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Actualizar un plan de entrenamiento
    public int actualizarPlanEntrenamiento(PlanEntrenamiento planEntrenamiento) {
        SQLiteDatabase dbHelper = null;
        int resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.COLUMN_NOMBRE, planEntrenamiento.getNombre());
            values.put(DatabaseHelper.COLUMN_DESCRIPCION, planEntrenamiento.getDescripcion());
            values.put(DatabaseHelper.COLUMN_FECHA_INICIO, planEntrenamiento.getFechaInicio());
            values.put(DatabaseHelper.COLUMN_FECHA_FIN, planEntrenamiento.getFechaFin());
            values.put(DatabaseHelper.COLUMN_ID_CLIENTE, planEntrenamiento.getIdCliente());
            values.put(DatabaseHelper.COLUMN_TIPO, planEntrenamiento.getTipo());

            String whereClause = DatabaseHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(planEntrenamiento.getId())};

            resultado = dbHelper.update(DatabaseHelper.TABLE_PLAN_ENTRENAMIENTO, values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Eliminar un plan de entrenamiento
    public int eliminarPlanEntrenamiento(int id) {
        SQLiteDatabase dbHelper = null;
        int resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            String whereClause = DatabaseHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(id)};
            resultado = dbHelper.delete(DatabaseHelper.TABLE_PLAN_ENTRENAMIENTO, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Obtener un plan de entrenamiento por su id
    public PlanEntrenamiento obtenerPlanEntrenamiento(int id) {
        SQLiteDatabase dbHelper = null;
        PlanEntrenamiento planEntrenamiento = null;

        try {
            dbHelper = db.getReadableDatabase();
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_PLAN_ENTRENAMIENTO + " WHERE " + DatabaseHelper.COLUMN_ID + " = ?";
            String[] selectionArgs = {String.valueOf(id)};
            Cursor cursor = dbHelper.rawQuery(query, selectionArgs);

            if (cursor.moveToFirst()) {
                planEntrenamiento = new PlanEntrenamiento();
                planEntrenamiento.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                planEntrenamiento.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE)));
                planEntrenamiento.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPCION)));
                planEntrenamiento.setFechaInicio(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA_INICIO)));
                planEntrenamiento.setFechaFin(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA_FIN)));
                planEntrenamiento.setIdCliente(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_CLIENTE)));
                planEntrenamiento.setTipo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIPO)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return planEntrenamiento;
    }

    // Obtener todos los planes de entrenamiento
    public List<PlanEntrenamiento> obtenerTodosLosPlanesEntrenamiento() {
        SQLiteDatabase dbHelper = null;
        Cursor cursor = null;
        List<PlanEntrenamiento> listadoPlanesEntrenamiento = new ArrayList<>();

        try {
            dbHelper = db.getReadableDatabase();
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_PLAN_ENTRENAMIENTO;
            cursor = dbHelper.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    PlanEntrenamiento planEntrenamiento = new PlanEntrenamiento();
                    planEntrenamiento.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                    planEntrenamiento.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE)));
                    planEntrenamiento.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPCION)));
                    planEntrenamiento.setFechaInicio(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA_INICIO)));
                    planEntrenamiento.setFechaFin(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA_FIN)));
                    planEntrenamiento.setIdCliente(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_CLIENTE)));
                    planEntrenamiento.setTipo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIPO)));
                    listadoPlanesEntrenamiento.add(planEntrenamiento);
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
        return listadoPlanesEntrenamiento;
    }

    // Obtener todos los planes de entrenamiento de un cliente
    public List<PlanEntrenamiento> obtenerPlanesEntrenamientoCliente(int idCliente) {
        SQLiteDatabase dbHelper = null;
        Cursor cursor = null;
        List<PlanEntrenamiento> listadoPlanesEntrenamiento = new ArrayList<>();

        try {
            dbHelper = db.getReadableDatabase();
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_PLAN_ENTRENAMIENTO + " WHERE " + DatabaseHelper.COLUMN_ID_CLIENTE + " = ?";
            String[] selectionArgs = {String.valueOf(idCliente)};
            cursor = dbHelper.rawQuery(query, selectionArgs);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    PlanEntrenamiento planEntrenamiento = new PlanEntrenamiento();
                    planEntrenamiento.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                    planEntrenamiento.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE)));
                    planEntrenamiento.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPCION)));
                    planEntrenamiento.setFechaInicio(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA_INICIO)));
                    planEntrenamiento.setFechaFin(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA_FIN)));
                    planEntrenamiento.setIdCliente(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_CLIENTE)));
                    planEntrenamiento.setTipo(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIPO)));
                    listadoPlanesEntrenamiento.add(planEntrenamiento);
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
        return listadoPlanesEntrenamiento;
    }

    // Agregar detalle a un plan de entrenamiento
    public long insertarDetallePlanEntrenamiento(DetallePlanEntrenamiento detallePlanEntrenamiento) {
        SQLiteDatabase dbHelper = null;
        long resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.COLUMN_ID_PLAN_ENTRENAMIENTO, detallePlanEntrenamiento.getIdPlanEntrenamiento());
            values.put(DatabaseHelper.COLUMN_ID_RUTINA, detallePlanEntrenamiento.getIdRutina());
            values.put(DatabaseHelper.COLUMN_DIA, detallePlanEntrenamiento.getDia());
            resultado = dbHelper.insert(DatabaseHelper.TABLE_DETALLE_PLAN_ENTRENAMIENTO, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Eliminar una rutina del detalle de un plan de entrenamiento
    public int eliminarRutinaDetallePlanEntrenamiento(int idPlanEntrenamiento, int idRutina) {
        SQLiteDatabase dbHelper = null;
        int resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            String whereClause = DatabaseHelper.COLUMN_ID_PLAN_ENTRENAMIENTO + " = ? AND " + DatabaseHelper.COLUMN_ID_RUTINA + " = ?";
            String[] whereArgs = {String.valueOf(idPlanEntrenamiento), String.valueOf(idRutina)};
            resultado = dbHelper.delete(DatabaseHelper.TABLE_DETALLE_PLAN_ENTRENAMIENTO, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Obtener detalle de un plan de entrenamiento
    public List<DetallePlanEntrenamiento> obtenerDetallePlanEntrenamiento(int idPlanEntrenamiento) {
        SQLiteDatabase dbHelper = null;
        Cursor cursor = null;
        List<DetallePlanEntrenamiento> listadoDetallePlanEntrenamiento = new ArrayList<>();

        try {
            dbHelper = db.getReadableDatabase();
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_DETALLE_PLAN_ENTRENAMIENTO + " WHERE " + DatabaseHelper.COLUMN_ID_PLAN_ENTRENAMIENTO + " = ?";
            String[] selectionArgs = {String.valueOf(idPlanEntrenamiento)};
            cursor = dbHelper.rawQuery(query, selectionArgs);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    DetallePlanEntrenamiento detallePlanEntrenamiento = new DetallePlanEntrenamiento();
                    detallePlanEntrenamiento.setIdPlanEntrenamiento(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_PLAN_ENTRENAMIENTO)));
                    detallePlanEntrenamiento.setIdRutina(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_RUTINA)));
                    detallePlanEntrenamiento.setDia(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DIA)));
                    listadoDetallePlanEntrenamiento.add(detallePlanEntrenamiento);
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
        return listadoDetallePlanEntrenamiento;
    }

   // Obtener el cliente al que le pertence un plan de entrenamiento
    public Cliente obtenerClientePlanEntrenamiento(int idCliente) {
        SQLiteDatabase dbHelper = null;
        Cursor cursor = null;
        Cliente cliente = null;

        try {
            dbHelper = db.getReadableDatabase();
            String query = "SELECT * FROM " + DatabaseHelper.TABLE_CLIENTE + " WHERE " + DatabaseHelper.COLUMN_ID + " = ?";
            String[] selectionArgs = {String.valueOf(idCliente)};
            cursor = dbHelper.rawQuery(query, selectionArgs);

            if (cursor != null && cursor.moveToFirst()) {
                cliente = new Cliente();
                cliente.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID)));
                cliente.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE)));
                cliente.setApellido(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_APELLIDO)));
                cliente.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL)));
                cliente.setGenero(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENERO)));
                cliente.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TELEFONO)));
                cliente.setFechaNacimiento(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA_NACIMIENTO)));
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
        return cliente;
    }

}
