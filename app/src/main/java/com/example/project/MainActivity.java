package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.cuberto.liquid_swipe.LiquidPager;

public class MainActivity extends AppCompatActivity {

    //onBoarding
    LiquidPager pager;
    viewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //onBoarding initializations
        pager = findViewById(R.id.l_pager);
        //calling the constructor of viewPager class
        viewPager = new viewPager(getSupportFragmentManager(),1);
        pager.setAdapter(viewPager);

    }
}