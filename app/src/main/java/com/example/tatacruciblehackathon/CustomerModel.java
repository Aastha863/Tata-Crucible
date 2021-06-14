package com.example.tatacruciblehackathon;

import android.content.Intent;
import android.widget.Button;

public class CustomerModel {
     public String url;
     public int scanid;
     public CustomerModel(int scanid, String url) {
          this.url = url;
          this.scanid = scanid;
     }

     public String getUrl() {
          return url;
     }

     public int getScanid() {
          return scanid;
     }
     public String toString() {
          return scanid + " The requested URL was :" +
                  url ;
     }
     public void setUrl(String url) {
          this.url = url;
     }

     public void setScanid(int scanid) {
          this.scanid = scanid;
     }
}

