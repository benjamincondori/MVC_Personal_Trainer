package com.android.kotlin.personaltrainer.model.PlanEntrenamiento;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.android.kotlin.personaltrainer.database.DatabaseHelper;

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
        return null;
    }

    // Obtener todos los planes de entrenamiento
    public List<PlanEntrenamiento> obtenerPlanesEntrenamiento() {
        return null;
    }

    // Obtener todos los planes de entrenamiento de un cliente
    public List<PlanEntrenamiento> obtenerPlanesEntrenamientoCliente(int idCliente) {
        return null;
    }

    // Agregar detalle a un plan de entrenamiento
    public long insertarDetallePlanEntrenamiento(DetallePlanEntrenamiento detallePlanEntrenamiento) {
        return 0;
    }

    // Eliminar una rutina del detalle de un plan de entrenamiento
    public int eliminarRutinaDetallePlanEntrenamiento(int idPlanEntrenamiento, int idRutina) {
        return 0;
    }

    // Obtener detalle de un plan de entrenamiento
    public List<DetallePlanEntrenamiento> obtenerDetallePlanEntrenamiento(int idPlanEntrenamiento) {
        return null;
    }

}
