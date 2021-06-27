package com.example.expensemanager;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.example.expensemanager.ui.main.SectionsPagerAdapter;

import java.util.ArrayList;

public class All_transaction extends AppCompatActivity {
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        window=this.getWindow();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transaction);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        if(Build.VERSION.SDK_INT>=21) {
            window.setStatusBarColor(this.getResources().getColor(R.color.transstatusbar));
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
}