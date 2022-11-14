package com.uottawa.felipemodesto.segproject1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.FirebaseDatabase;

public class Home extends Activity {
    DatabaseReference databaseTest;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginID:
                loginClick(v);
                break;
            case R.id.signUpID:
                signUpClick(v);
                break;
        }
    }

    public void loginClick (View v){
        //code for login button
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }

    public void signUpClick (View v) {
        //code for signup button
        Intent i = new Intent(this, Type.class);
        startActivity(i);
    }

}