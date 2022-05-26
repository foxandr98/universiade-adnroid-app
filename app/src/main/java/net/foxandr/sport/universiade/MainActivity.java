package net.foxandr.sport.universiade;

import android.content.res.Resources;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import net.foxandr.sport.universiade.ui.main.ViewPager2Adapter;
import net.foxandr.sport.universiade.ui.home.HomeFragment;
import net.foxandr.sport.universiade.ui.lostfound.LostFoundFragment;
import net.foxandr.sport.universiade.ui.news.NewsFragment;
import net.foxandr.sport.universiade.utils.LocaleHelper;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabLayoutMediator.TabConfigurationStrategy {

    //    private ActivityMainBinding binding;
    String appLocale;

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    Menu menu;

    List<String> titles;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (appLocale == null)
            appLocale = LocaleHelper.initLocale(this);
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
                HomeFragment.newInstance(appLocale),
                NewsFragment.newInstance(appLocale, appLocale),
                LostFoundFragment.newInstance(appLocale, appLocale)
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
        LocaleHelper.setMenuLocaleIcon(this, menu, appLocale);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ru:
                finish();
                setLocaleAndMenuIcon("ru", "Выбран русский язык");
                break;
            case R.id.menu_en:
                finish();
                setLocaleAndMenuIcon("en", "English language selected");
                break;
            case R.id.menu_de:
                finish();
                setLocaleAndMenuIcon("de", "Ausgewählte deutsche Sprache");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setLocaleAndMenuIcon(String locale, String message){
        LocaleHelper.setLocale(this,locale);
        LocaleHelper.setMenuLocaleIcon(this, menu, locale);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}