package com.uottawa.felipemodesto.segproject1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginID:
                loginClick(v);
                break;
        }
        switch (v.getId()) {
            case R.id.signUpID:
                signUpClick(v);
                break;
        }
    }

    public void loginClick (View v){
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }
    public void signUpClick (View v){
        Intent i = new Intent(this, Type.class);
        startActivity(i);
    }
}