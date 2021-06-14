package com.example.tatacruciblehackathon;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class MainActivity extends AppCompatActivity {

    EditText qrvalue;
   Button gbtn,sbtn;
   ImageView getimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        qrvalue = findViewById(R.id.qrvlue);
        gbtn =findViewById(R.id.gen);
        sbtn=findViewById(R.id.scan);
        getimage= findViewById(R.id.cr);
        Intent intent=getIntent();

        gbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = qrvalue.getText().toString();
                QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, 10);
                //creating bitmap
                //try catch

                Bitmap qrbits = qrgEncoder.getBitmap();
                getimage.setImageBitmap(qrbits);
            } });
            sbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), Scanner.class));
                }
            });
    }

}