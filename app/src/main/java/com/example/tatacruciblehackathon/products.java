package com.example.tatacruciblehackathon;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;


import org.jetbrains.annotations.Nullable;

public class products extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance(
                "Explore our products", "The best one at the Tata Steel", R.drawable.tata_steel, Color.rgb(0, 120, 0), Color.WHITE, Color.WHITE));
        addSlide(AppIntroFragment.newInstance(
                "Bars", "Tata Steel to sell long products", R.drawable.bars, Color.BLUE));
        addSlide(AppIntroFragment.newInstance(
                "Roof Junction", "Strong and tough", R.drawable.roof_junction, Color.rgb(150, 0, 0), Color.rgb(255, 255, 255), Color.rgb(255, 255, 255)));
        addSlide(AppIntroFragment.newInstance(
                "Tata Shaktee Ridges", "Effective steel solutions of different structures", R.drawable.tata_shaktee_ridges, Color.rgb(150, 0, 120)));
        addSlide(AppIntroFragment.newInstance(
                "Galvana", "Tata Steel introduces galvana", R.drawable.galvona, Color.rgb(120, 120, 0)));
    }
    @Override
    protected void onSkipPressed(@Nullable Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        Intent intent;
        intent= new Intent(products.this, Scanner.class);
        startActivity(intent);
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent;
        intent = new Intent(products.this, Scanner.class);
        startActivity(intent);
    }
}