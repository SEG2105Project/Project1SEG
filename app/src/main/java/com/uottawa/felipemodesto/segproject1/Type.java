package com.uottawa.felipemodesto.segproject1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Type extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
    }

    public void onClick(View v) {
        //There are 2 types, either cook or client
        switch (v.getId()) {
            case R.id.clientTypeID:
                clientTypeClick(v);
                break;
            case R.id.cookTypeID:
                cookTypeClick(v);
                break;
        }
    }

    public void clientTypeClick (View v){
        Intent i = new Intent(this, ClientSignUp.class);
        startActivity(i);
    }

    public void cookTypeClick (View v) {
        Intent i = new Intent(this, CookSignUp.class);
        startActivity(i);
    }
}