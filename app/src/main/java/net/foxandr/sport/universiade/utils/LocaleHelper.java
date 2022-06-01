package net.foxandr.sport.universiade.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;

import androidx.appcompat.content.res.AppCompatResources;

import net.foxandr.sport.universiade.ui.activities.MainActivity;
import net.foxandr.sport.universiade.R;

import java.util.Locale;
import java.util.Map;

public class LocaleHelper {

    public static final Map<String, Integer> LOCALE_IMAGES = Map.ofEntries(
            Map.entry("en", R.drawable.menu_uk),
            Map.entry("ru", R.drawable.menu_russia),
            Map.entry("de", R.drawable.menu_germany)
    );

    public static String initLocale(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(
                "selectedLanguage",
                Context.MODE_PRIVATE);
        String languageToLoad = sharedPreferences.getString("language", "en");
        configure(activity, languageToLoad);
        return languageToLoad;
    }

    public static void setLocale(Activity activity, String lang) {
        SharedPreferences preferences = activity.getSharedPreferences(
                "selectedLanguage",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("language", lang);
        editor.apply();
        configure(activity, lang);

        Bundle args = activity.getIntent().getExtras();
        Intent refresh = new Intent(activity, MainActivity.class);
        refresh.putExtras(args);
        activity.startActivity(refresh);
    }

    private static void configure(Activity activity, String lang) {
        Locale myLocale = new Locale(lang);
        Locale.setDefault(myLocale);
        Resources res = activity.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        res.updateConfiguration(conf, dm);
    }

    public static void setMenuLocaleIcon(Activity activity, Menu menu, String lang) {
        try {
            menu.getItem(2).setIcon(AppCompatResources.getDrawable(
                    activity,
                    LOCALE_IMAGES.get(lang)
            ));
        } catch (NullPointerException ex) {
            System.out.println("Несуществующий язык");
        }
    }
}
