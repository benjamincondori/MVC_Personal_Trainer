package com.android.kotlin.personaltrainer.model.Cliente;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.kotlin.personaltrainer.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MCliente {

    private final DatabaseHelper db;

    public MCliente(Context context) {
        this.db = new DatabaseHelper(context);
    }

    // Agregar un nuevo cliente
    public long insertarCliente(Cliente cliente) {
        SQLiteDatabase dbHelper = null;
        long resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.COLUMN_NOMBRE, cliente.getNombre());
            values.put(DatabaseHelper.COLUMN_APELLIDO, cliente.getApellido());
            values.put(DatabaseHelper.COLUMN_FECHA_NACIMIENTO, cliente.getFechaNacimiento());
            values.put(DatabaseHelper.COLUMN_TELEFONO, cliente.getTelefono());
            values.put(DatabaseHelper.COLUMN_EMAIL, cliente.getEmail());
            values.put(DatabaseHelper.COLUMN_GENERO, cliente.getGenero());
            values.put(DatabaseHelper.COLUMN_FOTO, cliente.getFoto());

            resultado = dbHelper.insert(DatabaseHelper.TABLE_CLIENTE, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Actualizar un cliente
    public int actualizarCliente(Cliente cliente) {
        SQLiteDatabase dbHelper = null;
        int resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(DatabaseHelper.COLUMN_NOMBRE, cliente.getNombre());
            values.put(DatabaseHelper.COLUMN_APELLIDO, cliente.getApellido());
            values.put(DatabaseHelper.COLUMN_FECHA_NACIMIENTO, cliente.getFechaNacimiento());
            values.put(DatabaseHelper.COLUMN_TELEFONO, cliente.getTelefono());
            values.put(DatabaseHelper.COLUMN_EMAIL, cliente.getEmail());
            values.put(DatabaseHelper.COLUMN_GENERO, cliente.getGenero());
            values.put(DatabaseHelper.COLUMN_FOTO, cliente.getFoto());

            String whereClause = DatabaseHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(cliente.getId())};

            resultado = dbHelper.update(DatabaseHelper.TABLE_CLIENTE, values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Eliminar un cliente
    public int eliminarCliente(int id) {
        SQLiteDatabase dbHelper = null;
        int resultado = -1;

        try {
            dbHelper = db.getWritableDatabase();
            String whereClause = DatabaseHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(id)};

            resultado = dbHelper.delete(DatabaseHelper.TABLE_CLIENTE, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbHelper != null) {
                dbHelper.close();
            }
        }
        return resultado;
    }

    // Obtener un cliente por su id
    public Cliente obtenerClientePorId(int idCliente) {
        SQLiteDatabase dbHelper = null;
        Cursor cursor = null;
        Cliente cliente = null;

        try {
            dbHelper = db.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseHelper.TABLE_CLIENTE + " WHERE " + DatabaseHelper.COLUMN_ID + " = ?";
            String[] whereArgs = {String.valueOf(idCliente)};
            cursor = dbHelper.rawQuery(query, whereArgs);

            if (cursor != null && cursor.moveToFirst()) {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE));
                String apellido = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_APELLIDO));
                String fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA_NACIMIENTO));
                String telefono = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TELEFONO));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));
                String genero = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENERO));
                String foto = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOTO));

                cliente = new Cliente(idCliente, nombre, apellido, fechaNacimiento, telefono, email, genero, foto);
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

    // Obtener todos los clientes
    public List<Cliente> obtenerTodosLosClientes() {
        SQLiteDatabase dbHelper = null;
        Cursor cursor = null;
        List<Cliente> listadoClientes = new ArrayList<>();

        try {
            dbHelper = db.getReadableDatabase();

            String query = "SELECT * FROM " + DatabaseHelper.TABLE_CLIENTE;
            cursor = dbHelper.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID));
                    String nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE));
                    String apellido = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_APELLIDO));
                    String fechaNacimiento = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA_NACIMIENTO));
                    String telefono = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TELEFONO));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_EMAIL));
                    String genero = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENERO));
                    String foto = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FOTO));

                    Cliente cliente = new Cliente(id, nombre, apellido, fechaNacimiento, telefono, email, genero, foto);
                    listadoClientes.add(cliente);
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
        return listadoClientes;
    }

}
