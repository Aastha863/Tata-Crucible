package com.example.tatacruciblehackathon;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Reader;

public class Scanner extends AppCompatActivity {
    CodeScanner cs;
    CodeScannerView sv;
    TextView resultdata;
    Button visit;
    public DrawerLayout mDrawerlayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    String r;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public final NavigationView.OnNavigationItemSelectedListener navListener =
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();
                    Intent intent;
                    switch (id) {

                        case R.id.generate_scan:
                            //Add prescriptions activity
                            intent = new Intent(Scanner.this, MainActivity.class);
                            startActivity(intent);
                            break;
                        case R.id.products:
                            //Add uploads activity
                            intent = new Intent(Scanner.this, products.class);
                            startActivity(intent);
                            break;
                        case R.id.previous_scans:
                            //Add uploads activity
                            intent = new Intent(Scanner.this, PreviousScan.class);
                            startActivity(intent);
                            break;
                        case R.id.about_us:
                            intent = new Intent(Scanner.this, PreviousScan.class);
                            startActivity(intent);
                            break;
                        case R.id.exit:
                            finish();
                            break;
                    }
                    return true;

                }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer);
         sv=findViewById(R.id.scannerView);
         mDrawerlayout=findViewById(R.id.drawer_layout);
         actionBarDrawerToggle=new ActionBarDrawerToggle(this,mDrawerlayout,R.string.nav_open,R.string.nav_close);
         mDrawerlayout.addDrawerListener(actionBarDrawerToggle);
         actionBarDrawerToggle.syncState();
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        cs= new CodeScanner(this,sv);
        resultdata = findViewById(R.id.url);
        visit=findViewById(R.id.visit);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(navListener);
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
                resultdata.setText("Url link");
            }
        });
        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a=5;
                CustomerModel cm = new CustomerModel(5,r);
                Connection_Helper helper = new Connection_Helper(Scanner.this);
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    helper.addOne(cm);
                    intent.setData(Uri.parse(r));

                    startActivity(intent);
                    Intent i = new Intent(Scanner.this,PreviousScan.class);
                    i.putExtra("Url",r);

                }
                catch (Exception e)
                {
                    Toast.makeText(Scanner.this, "Requested URL not found",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();
    }
    public void gallery(View view){
        //pick image from galary
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1000);
    }
    @Override

    protected void onActivityResult(int reqCode, int resultCode, Intent data) {

        super.onActivityResult(reqCode, resultCode, data);





        if (resultCode == RESULT_OK) {

            try {

                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                try {
                    Bitmap bMap = selectedImage;
                    String contents = null;
                    int[] intArray = new int[bMap.getWidth()*bMap.getHeight()];
                    bMap.getPixels(intArray, 0, bMap.getWidth(), 0, 0, bMap.getWidth(), bMap.getHeight());
                    LuminanceSource source = new RGBLuminanceSource(bMap.getWidth(), bMap.getHeight(), intArray);
                    BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                    MultiFormatReader reader = new MultiFormatReader();
                    Result result = reader.decode(bitmap);
                    contents = result.getText();
                    resultdata.setText(contents);
                    r=contents;
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(Scanner.this,"Given image is not a QR code thus can't be scanned be scanned",Toast.LENGTH_SHORT).show();
                }
                //  image_view.setImageBitmap(selectedImage);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Scanner.this, "Something went wrong", Toast.LENGTH_LONG).show();

            }



        }else {

            Toast.makeText(Scanner.this, "You haven't picked Image",Toast.LENGTH_LONG).show();

        }

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