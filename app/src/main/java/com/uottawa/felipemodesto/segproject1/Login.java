package com.uottawa.felipemodesto.segproject1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Login extends Activity {
    DatabaseReference databaseClient;
    List<Client> clients;
    DatabaseReference databaseCook;
    List<Cook> cooks;
    Button buttonLogin;
    EditText editTextEmail;
    EditText editTextPassword;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = (EditText) findViewById(R.id.loginEmailID);
        editTextPassword = (EditText) findViewById(R.id.loginPasswordID);
        buttonLogin = (Button) findViewById(R.id.loginLoginButtonID);
        clients = new ArrayList<>();
        cooks = new ArrayList<>();
        databaseClient = FirebaseDatabase.getInstance().getReference("clients");
        databaseCook = FirebaseDatabase.getInstance().getReference("cooks");

        buttonLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                checkExistingUser();
            }
        });
    }

    protected void onStart() {
        super.onStart();
        databaseClient.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clients.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Client client = postSnapshot.getValue(Client.class);
                    clients.add(client);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

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
    }
    protected void checkExistingUser(){

        String Email = editTextEmail.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Entered Password").setValue(Password);
        mDatabase.child("Entered Email").setValue(Email);
        mDatabase.child("DB Password").setValue(clients.get(0).password.trim());
        mDatabase.child("DB Email").setValue(clients.get(0).email.trim());
        boolean loginFound = false;
        for (int i=0; i<clients.size(); i++){
            if (clients.get(i).email.trim().equals(Email) && clients.get(i).password.trim().equals(Password)){
                Intent j = new Intent(this, welcomeClient.class);
                startActivity(j);
                loginFound = true;
            };
        }
        for (int i=0; i<cooks.size(); i++){
            if (cooks.get(i).email.trim().equals(Email) && cooks.get(i).password.trim().equals(Password)){
                Intent j = new Intent(this, welcomeCook.class);
                startActivity(j);
                loginFound = true;
            };
        }
        if (!loginFound){
            Toast.makeText(this, "Login not found", Toast.LENGTH_LONG).show();
        }

    }
}
