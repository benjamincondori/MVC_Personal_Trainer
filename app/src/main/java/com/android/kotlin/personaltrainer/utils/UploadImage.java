package com.android.kotlin.personaltrainer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import com.android.kotlin.personaltrainer.R;

public class UploadImage {

    public static void seledtPhotoFromGallery(Activity activity, int code) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, code);
    }

    public static String saveImageToStorage(Bitmap bitmap, Context context) {
        // Nombre del archivo
        String fileName = "profile_image_" + System.currentTimeMillis() + ".jpg";

        // Directorio en el almacenamiento interno
        File directory = context.getFilesDir();
        File file = new File(directory, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            // Comprimir el bitmap y guardarlo como archivo JPEG
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            return file.getAbsolutePath(); // Retorna la ruta donde se guardó la imagen
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void eliminarImagenFisica(String rutaImagen) {
        if (rutaImagen != null) {
            File archivoImagen = new File(rutaImagen);
            if (archivoImagen.exists()) {
                archivoImagen.delete(); // Elimina el archivo físicamente
            }
        }
    }

    public static void cargarImagen(String rutaImagen, ImageView imageView, Context context) {
        File imgFile = new File(rutaImagen);
        if (imgFile.exists()) {
            // Usa Glide para cargar la imagen desde la ruta del archivo
            Glide.with(context)
                    .load(imgFile)  // Carga la imagen desde el archivo
                    .placeholder(R.drawable.ic_placeholder_image)  // Imagen temporal mientras carga
//                    .error(R.drawable.error_image)  // Imagen si falla la carga
                    .into(imageView);  // Cargar en el ImageView
        }
    }

    private static int obtenerOrientacionImagen(String rutaImagen) {
        try {
            ExifInterface exif = new ExifInterface(rutaImagen);
            int orientacion = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            return orientacion;
        } catch (IOException e) {
            e.printStackTrace();
            return ExifInterface.ORIENTATION_NORMAL;
        }
    }

    public static String obtenerRutaImagenDesdeUri(Uri uri, Context context) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(projection[0]);
            String rutaImagen = cursor.getString(columnIndex);
            cursor.close();
            return rutaImagen;
        }
        return null;
    }


    public static Bitmap rotarImagenSegunOrientacion(Bitmap bitmap, String rutaImagen) {
        int orientacion = obtenerOrientacionImagen(rutaImagen);

        Matrix matrix = new Matrix();
        switch (orientacion) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270);
                break;
            default:
                return bitmap; // No necesita rotación
        }

        // Aplica la rotación al bitmap
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


}
