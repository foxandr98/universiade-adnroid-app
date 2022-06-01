package net.foxandr.sport.universiade.ui.activities;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jakewharton.threetenabp.AndroidThreeTen;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import net.foxandr.sport.universiade.R;
import net.foxandr.sport.universiade.ui.home.HomeFragment;
import net.foxandr.sport.universiade.ui.users.LoggedInUserDTO;
import net.foxandr.sport.universiade.ui.users.volunteers.VolunteerScheduleFragment;
import net.foxandr.sport.universiade.utils.ViewPager2Adapter;
import net.foxandr.sport.universiade.ui.lostfound.LostFoundFragment;
import net.foxandr.sport.universiade.ui.news.NewsFragment;
import net.foxandr.sport.universiade.utils.LocaleHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabLayoutMediator.TabConfigurationStrategy {
    String appLocale;
    Menu menu;
    List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (appLocale == null)
            appLocale = LocaleHelper.initLocale(this);
        setContentView(R.layout.activity_main);
        setTabsAndToolbar();
        AndroidThreeTen.init(this);
    }

    public void setTabsAndToolbar() {
        TabLayout tabLayout = findViewById(R.id.tabs);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager2 viewPager2View = findViewById(R.id.view_pager2);
        ViewPager2Adapter adapter = new ViewPager2Adapter(this);

        titles = new ArrayList<String>(Arrays.asList(getString(R.string.tab_home),
                getString(R.string.tab_news)
        ));

        List<Fragment> fragmentList = new ArrayList<Fragment>(Arrays.asList(
                HomeFragment.newInstance(appLocale),
                NewsFragment.newInstance(appLocale)));

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            boolean isAuthorized = arguments.getBoolean("isAuthorized", false);
            if (isAuthorized) {
                LoggedInUserDTO loggedInUserDTO = (LoggedInUserDTO) arguments.getSerializable("loggedInUserDTO");
                fragmentList.add(VolunteerScheduleFragment.newInstance(appLocale, loggedInUserDTO));
                titles.add(getString(R.string.volunteers_schedule));
            }
        } else {
            fragmentList.add(LostFoundFragment.newInstance().newInstance());
            titles.add(getString(R.string.tab_lost_found));
        }

        adapter.setData(fragmentList);
        viewPager2View.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2View, this).attach();
        getSupportActionBar().setTitle(getString(R.string.app_name));
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
            case R.id.menu_login:
                Intent loginForm = new Intent(this, LoginActivity.class);
//                activityResultLauncher.launch(loginForm);
                startActivity(loginForm);
        }

        return super.onOptionsItemSelected(item);
    }

    public void setLocaleAndMenuIcon(String locale, String message) {
        LocaleHelper.setLocale(this, locale);
        LocaleHelper.setMenuLocaleIcon(this, menu, locale);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}