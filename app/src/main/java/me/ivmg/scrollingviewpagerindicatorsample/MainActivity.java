package me.ivmg.scrollingviewpagerindicatorsample;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import me.ivmg.scrollingviewpagerindicator.ScrollViewPagerIndicator;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager viewPager = findViewById(R.id.viewPager);
        final ScrollViewPagerIndicator scrollViewPagerIndicator = findViewById(R.id.scrollView);

        viewPager.setAdapter(new CustomPagerAdapter(this));

        scrollViewPagerIndicator.attachViewPager(viewPager);
    }
}
