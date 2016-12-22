package com.pulkit.buyhatke.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.pulkit.buyhatke.R;
import com.pulkit.buyhatke.fragment.MessageFragment;

/**
 * @author pulkitmital
 * @date 22-12-2016
 */

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setupToolbar();
        initFragment();
    }


    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * Add toolbar to layout
     */

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * Add Message Fragment
     */

    private void initFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, MessageFragment.newInstance()).commit();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!getSupportFragmentManager().popBackStackImmediate())
                finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
