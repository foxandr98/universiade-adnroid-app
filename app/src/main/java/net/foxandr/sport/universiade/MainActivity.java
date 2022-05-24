package net.foxandr.sport.universiade;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.foxandr.sport.universiade.databinding.ActivityMainBinding;
import net.foxandr.sport.universiade.ui.main.ViewPager2Adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TabLayoutMediator.TabConfigurationStrategy {

    private ActivityMainBinding binding;

    ViewPager2 viewPager2;
    TabLayout tabLayout;

    List<String> titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewPager2 = findViewById(R.id.view_pager2);
        tabLayout = findViewById(R.id.tabs);
        titles = Arrays.asList("Главная", "Новости", "Бюро находок");

        setViewPagerAdapter();
        new TabLayoutMediator(tabLayout, viewPager2, this).attach();
    }

    public void setViewPagerAdapter(){

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
}