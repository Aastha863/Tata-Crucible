package com.example.tatacruciblehackathon;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Connection_Helper extends SQLiteOpenHelper  {
    public static final String DATABASE_NAME = "scan.db";
    public static final String TABLE_NAME = "scan_table";
    public static final String COL_1 = "ScanID";
    public static final String COL_2 = "URL";
    String r;


    //WE WILL PROVIDE HARDCODED PARAMETERS
    //super-parent
    public Connection_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME+" (" + COL_1+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 + " TEXT ) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int n) {

    }
    public boolean addOne(CustomerModel cm)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv= new ContentValues();
        //works like an hashmap
        cv.put(COL_2, cm.getUrl());
        long insert = db.insert(TABLE_NAME, null,cv);
        if(insert==-1)
            return false;
        else
            return true;
    }
    public List<CustomerModel> getEveryone()
    {
        List<CustomerModel> returnlist = new ArrayList<>();
        //get data from database
        String queryString = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();//the database is locked
        //rawquery returns a cursor;
        Cursor cursor = db.rawQuery(queryString,null);
        if(cursor.moveToFirst()){
            //loop through the result
            do
                {
                    int scanID = cursor.getInt(0);
                    String url = cursor.getString(1);

                    CustomerModel nc = new CustomerModel(scanID,url);
                    returnlist.add(nc);
                } while(cursor.moveToNext());

        }
        else
        {
            cursor.close();
            db.close();
            return returnlist;
        }
        return returnlist;
    }
}
