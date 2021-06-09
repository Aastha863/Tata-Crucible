package com.example.tatacruciblehackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    ImageView im;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //method of super class
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        //  Intent signInIntent = new Intent(getApplication(), MainActivity.class);
        //                signInIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //                signInIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //                startActivity(signInIntent);
        //                finish();
        //            }
        //        }, 2000);
        im=findViewById(R.id.logo);


    }
    //view is required
    public void gotoMain(View view)
    {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
