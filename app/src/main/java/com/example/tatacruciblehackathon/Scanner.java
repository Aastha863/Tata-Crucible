package com.example.tatacruciblehackathon;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class Scanner extends AppCompatActivity {
    CodeScanner cs;
    CodeScannerView sv;
    TextView resultdata;
    Button visit;
    String r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        sv=findViewById(R.id.scannerView);
        cs= new CodeScanner(this,sv);
        resultdata = findViewById(R.id.url);
        visit=findViewById(R.id.visit);
        // for multiple call backs
        cs.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                //make a new thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        resultdata.setText(result.getText());
                        r=result.getText();
                    }
                });
            }
        });

        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cs.startPreview();
            }
        });
        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);

                intent.setData(Uri.parse(r));

                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();
    }

    private void requestForCamera() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                cs.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(Scanner.this,"Camera Permission Required",Toast.LENGTH_LONG);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                //keeps on asking permission
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

}