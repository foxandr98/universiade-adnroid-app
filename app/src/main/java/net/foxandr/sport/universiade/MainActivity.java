package net.foxandr.sport.universiade;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import net.foxandr.sport.universiade.databinding.ActivityMainBinding;
import net.foxandr.sport.universiade.ui.main.ViewPager2Adapter;
import net.foxandr.sport.universiade.utils.LocaleHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TabLayoutMediator.TabConfigurationStrategy {

    //    private ActivityMainBinding binding;
    String initialLocale;

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    Menu menu;

    List<String> titles;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initialLocale == null)
            initialLocale = LocaleHelper.initLocale(this);
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        viewPager2 = findViewById(R.id.view_pager2);
        tabLayout = findViewById(R.id.tabs);
        toolbar = findViewById(R.id.toolbar);

        Resources res = getResources();
        titles = Arrays.asList(res.getString(R.string.tab_home),
                res.getString(R.string.tab_news),
                res.getString(R.string.tab_lost_found)
        );
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(res.getString(R.string.app_name));
        setViewPagerAdapter();
        new TabLayoutMediator(tabLayout, viewPager2, this).attach();
//        setContentView(binding.getRoot());
    }

    public void setViewPagerAdapter() {
        ViewPager2Adapter adapter = new ViewPager2Adapter(this);
        List<Fragment> fragmentList = Arrays.asList(
                new HomeFragment(),
                new NewsFragment(),
                new LostFoundFragment()
        );
        adapter.setData(fragmentList);
        viewPager2.setAdapter(adapter);
    }

    @Override
    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
        tab.setText(titles.get(position));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        LocaleHelper.setMenuLocaleIcon(this, menu, initialLocale);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ru:
                finish();
                LocaleHelper.setLocale(this,"ru");
                item.getIcon();
                LocaleHelper.setMenuLocaleIcon(this, menu, "ru");
                Toast.makeText(this, "Выбран русский язык", Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                break;
            case R.id.menu_en:
                finish();
                LocaleHelper.setLocale(this, "en");
                LocaleHelper.setMenuLocaleIcon(this, menu, "en");
                Toast.makeText(this, "English language selected", Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                break;
            case R.id.menu_de:
                finish();
                LocaleHelper.setLocale(this,"de");
                LocaleHelper.setMenuLocaleIcon(this, menu, "de");
                Toast.makeText(this, "Ausgewählte deutsche Sprache", Toast.LENGTH_SHORT).show();
                item.setChecked(true);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}