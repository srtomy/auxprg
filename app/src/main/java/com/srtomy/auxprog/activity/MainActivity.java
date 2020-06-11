package com.srtomy.auxprog.activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.srtomy.auxprog.TabAnotacaoFragment;
import com.srtomy.auxprog.adapter.TabAdapter;
import com.srtomy.auxprog.R;
import com.srtomy.auxprog.fragment.TabIssueFragment;

public class MainActivity extends AppCompatActivity {
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabAnotacaoFragment(), "ANOTAÇÕES");
        adapter.addFragment(new TabIssueFragment(), "PROBLEMA/SOLUÇÃO");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}