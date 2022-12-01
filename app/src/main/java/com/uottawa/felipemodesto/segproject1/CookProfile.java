package com.uottawa.felipemodesto.segproject1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.text.TextUtils;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class CookProfile extends Activity{
    DatabaseReference databaseCook;
    List<Cook> cooks;
    String cookId;
    TextView cookName;
    TextView cookEmail;
    TextView cookAddress;
    TextView cookRating;
    TextView cookDescription;
    TextView cookPassword;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookprofile);
        Bundle bundle = getIntent().getExtras();
        cookId = bundle.getString("cookId");
        databaseCook = FirebaseDatabase.getInstance().getReference("cooks");
        cooks = new ArrayList<>();
        cookName = (TextView) findViewById(R.id.cookName);
        cookEmail = (TextView) findViewById(R.id.cookEmail);
        cookAddress = (TextView) findViewById(R.id.cookAddress);
        cookRating = (TextView) findViewById(R.id.cookRating);
        cookDescription = (TextView) findViewById(R.id.cookDescription);
        cookPassword = (TextView) findViewById(R.id.cookPassword);
    }

    protected void onStart() {
        super.onStart();

        databaseCook.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cooks.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Cook cook = postSnapshot.getValue(Cook.class);
                    cooks.add(cook);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setProfileInfo(cooks, cookId);
            }
        }, 500);
    }

    private void setProfileInfo(List<Cook> cooks, String cookId){
        for (int i=0; i<cooks.size(); i++){
            if (cooks.get(i).id.equals(cookId)){
                cookName.setText("Cook Name: " + cooks.get(i).firstName + " "+ cooks.get(i).lastName);
                cookEmail.setText("Cook Email: " + cooks.get(i).email);
                cookAddress.setText("Cook Address: " + cooks.get(i).Address);
                cookDescription.setText("Cook Description: " + cooks.get(i).description);
                cookPassword.setText("Cook Password: " + cooks.get(i).password);
                cookRating.setText("Cook Rating: " + cooks.get(i).rating +"/5");

            }
        }
    };

}
