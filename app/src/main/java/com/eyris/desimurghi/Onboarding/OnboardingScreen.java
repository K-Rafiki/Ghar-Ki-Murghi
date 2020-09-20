package com.eyris.desimurghi.Onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.eyris.desimurghi.Adapters.OnBoardingAdapter;
import com.eyris.desimurghi.Models.OnBoardItem;
import com.eyris.desimurghi.R;

import java.util.ArrayList;
import java.util.List;

public class OnboardingScreen extends AppCompatActivity {
    private OnBoardingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);
        setupOnboarding();

        ViewPager2 viewPager2 = findViewById(R.id.viewpager);
        viewPager2.setAdapter(adapter);

    }

    private void setupOnboarding() {
        List<OnBoardItem> list = new ArrayList<>();

        OnBoardItem item = new OnBoardItem(R.drawable.ic_first,"Healthy Chickens","Our chickens are raised in a healthy open farm. We make sure they are in their natural habitat");
        list.add(item);

        OnBoardItem item2 = new OnBoardItem(R.drawable.ic_second,"Healthy Meat","Desi Chickens have protein rich healthy meat");
        list.add(item2);

        OnBoardItem item3 = new OnBoardItem(R.drawable.ic_third,"Healthy Eggs","Desi Chicken eggs are proven to have vital nutrients and health benefits");
        list.add(item3);

        adapter = new OnBoardingAdapter(list);
    }

}