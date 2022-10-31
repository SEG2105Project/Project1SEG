package com.uottawa.felipemodesto.segproject1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class welcomeAdmin extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomeadmin);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.adminLogOffID:
                adminLogOffClick(v);
                break;
            case R.id.complaintsButton:
                complaintsButtonClick(v);
                break;
        }
    }

    public void adminLogOffClick (View v) {
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }

    public void complaintsButtonClick (View v) {
        //Intent i = new Intent(this, complaints.class);
        //startActivity(i);
    }

}
