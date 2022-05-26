package net.foxandr.sport.universiade.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Menu;
import androidx.appcompat.content.res.AppCompatResources;
import net.foxandr.sport.universiade.MainActivity;
import net.foxandr.sport.universiade.R;

import java.util.Locale;
import java.util.Map;

public class LocaleHelper {

    public static final Map<String, Integer> LOCALE_IMAGES = Map.ofEntries(
            Map.entry("en", R.drawable.uk),
            Map.entry("ru", R.drawable.russia),
            Map.entry("de", R.drawable.germany)
    );

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

        editor.apply();

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

    public static void setMenuLocaleIcon(Context context, Menu menu, String lang) {
        try {
            menu.getItem(1).setIcon(AppCompatResources.getDrawable(
                    context,
                    LOCALE_IMAGES.get(lang)
            ));
        } catch (NullPointerException ex) {
            System.out.println("Несуществующий язык");
        }
    }
}
