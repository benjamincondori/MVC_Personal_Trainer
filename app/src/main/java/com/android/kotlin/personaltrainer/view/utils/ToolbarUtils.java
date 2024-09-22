package com.android.kotlin.personaltrainer.view.utils;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.android.kotlin.personaltrainer.R;

public class ToolbarUtils {

    public static void setupToolbar(AppCompatActivity activity, Toolbar toolbar) {
        activity.setSupportActionBar(toolbar);

        // Habilita el botón de navegación hacia atrás
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

            // Cambia el color del icono de navegación
            Drawable navigationIcon = toolbar.getNavigationIcon();
            if (navigationIcon != null) {
                navigationIcon.setColorFilter(ContextCompat.getColor(activity, R.color.white), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

}
