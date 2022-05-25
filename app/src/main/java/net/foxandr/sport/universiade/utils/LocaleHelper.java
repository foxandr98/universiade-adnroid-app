package net.foxandr.sport.universiade.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Menu;

import androidx.core.content.ContextCompat;

import net.foxandr.sport.universiade.MainActivity;
import net.foxandr.sport.universiade.R;

import java.util.Locale;

public class LocaleHelper {

    public static String initLocale(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                "selectedLanguage",
                Context.MODE_PRIVATE);
        String languageToLoad = sharedPreferences.getString("language", "en");
        configure(context, languageToLoad);
        return languageToLoad;
    }

    public static void setLocale(Context context, String lang) {
        SharedPreferences preferences = context.getSharedPreferences(
                "selectedLanguage",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("language", lang);
//        editor.putInt("drawable_id", lang);
        editor.commit();

        configure(context, lang);

        Intent refresh = new Intent(context, MainActivity.class);
        context.startActivity(refresh);
    }

    private static void configure(Context context, String lang) {
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        res.updateConfiguration(conf, dm);
    }

    public static void setMenuLocaleIcon(Context context, Menu menu, String lang){
        int drawable_id = 0;
        switch(lang){
            case "ru":
                drawable_id = R.drawable.russia;
                break;
            case "de":
                drawable_id = R.drawable.germany;
                break;
            default:
                drawable_id = R.drawable.uk;
        }
        menu.getItem(1).setIcon(ContextCompat.getDrawable(
                context,
                drawable_id
        ));
    }

}
