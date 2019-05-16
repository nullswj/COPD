package com.swj.copd;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.swj.copd.fragment.PMFragment;
import com.swj.copd.fragment.TemperatureFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.MenuItem;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    private TextView mTextMessage;

    BottomNavigationView navView;

    private Fragment temperatureFragment;

    private Fragment pmFragment;

    private Fragment[] fragments;

    private int lastFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    if(lastFragment != 0)
                    {
                        switchFragment(lastFragment,0);
                        lastFragment = 0;
                    }
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    if(lastFragment != 1)
                    {
                        switchFragment(lastFragment,1);
                        lastFragment = 1;
                    }
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        initUI();
    }

    private void initUI()
    {
        navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        temperatureFragment = TemperatureFragment.newInstance();
        pmFragment = PMFragment.newInstance();
        fragments = new Fragment[]{temperatureFragment,pmFragment};

        switchFragment(lastFragment,0);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void switchFragment(int lastfragment, int index)
    {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.hide(fragments[lastfragment]);//隐藏上个Fragment

        if(!fragments[index].isAdded())
        {
            transaction.add(R.id.fragment,fragments[index]);
        }
        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

}
