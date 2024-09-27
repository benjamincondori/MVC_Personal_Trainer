package com.android.kotlin.personaltrainer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class UploadImage {

    public static void seledtPhotoFromGallery(Activity activity, int code) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, code);
    }

    public static void saveImage(Context context, Uri uri, long id) {
        File file = new File(context.getFilesDir(), Long.toString(id));

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Uri getImageUri(Context context, long id) {
        File file = new File(context.getFilesDir(), Long.toString(id));

        if (file.exists()) {
            return Uri.fromFile(file);
        } else {
            return Uri.parse("android.resource://com.android.kotlin.personaltrainer/drawable/ic_placeholder_image");
        }
    }

    public static void deleteImage(Context context, long id) {
        File file = new File(context.getFilesDir(), Long.toString(id));
        file.delete();
    }

}
