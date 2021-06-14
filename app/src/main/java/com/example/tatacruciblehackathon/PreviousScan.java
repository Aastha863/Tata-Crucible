package com.example.tatacruciblehackathon;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class PreviousScan extends AppCompatActivity {
    String r;
    ListView ls;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prev);
        Intent intent=getIntent();
        r=intent.getStringExtra("Url");
        ls=findViewById(R.id.list_view);
        Connection_Helper ch= new Connection_Helper(PreviousScan.this);
        List<CustomerModel> everyone = ch.getEveryone();
        ArrayAdapter cra = new ArrayAdapter<CustomerModel>(PreviousScan.this, android.R.layout.simple_list_item_1,everyone);
        ls.setAdapter(cra);
    }

}
