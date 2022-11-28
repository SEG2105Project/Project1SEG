package com.uottawa.felipemodesto.segproject1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.AutoCompleteTextView;

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
    DatabaseReference databaseAdmin;
    List<Admin> admins;
    Button buttonLogin;
    EditText editTextEmail;
    EditText editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = (EditText) findViewById(R.id.loginEmailID);
        editTextPassword = (EditText) findViewById(R.id.loginPasswordID);
        buttonLogin = (Button) findViewById(R.id.loginLoginButtonID);
        clients = new ArrayList<>();
        cooks = new ArrayList<>();
        admins = new ArrayList<>();
        databaseClient = FirebaseDatabase.getInstance().getReference("clients");
        databaseCook = FirebaseDatabase.getInstance().getReference("cooks");
        databaseAdmin = FirebaseDatabase.getInstance().getReference("Admin");

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

        databaseAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                admins.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Admin admin = postSnapshot.getValue(Admin.class);
                    admins.add(admin);
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
        boolean loginFound = false;
        for (int i=0; i<clients.size(); i++){
            if (clients.get(i).email != null && clients.get(i).password != null &&  clients.get(i).email.trim().equals(Email) && clients.get(i).password.trim().equals(Password)){
                Intent j = new Intent(this, welcomeClient.class);
                startActivity(j);
                loginFound = true;
            };
        }
        for (int i=0; i<cooks.size(); i++){
            if (cooks.get(i).email != null && cooks.get(i).password != null && cooks.get(i).email.trim().equals(Email) && cooks.get(i).password.trim().equals(Password)){
                if (cooks.get(i).suspended != null && cooks.get(i).suspended == true){
                    loginFound = true;
                    Intent j = new Intent(this, cookSuspended.class);
                    j.putExtra("days", cooks.get(i).daysOfSuspension);
                    startActivity(j);
                } else{
                    Intent j = new Intent(this, welcomeCook.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("cookId", cooks.get(i).id.trim());
                    j.putExtras(bundle);
                    startActivity(j);
                    loginFound = true;
                }
            };
        }
        for (int i=0; i<admins.size(); i++){
            if (admins.get(i).userName != null && admins.get(i).password != null && admins.get(i).userName.trim().equals(Email) && admins.get(i).password.trim().equals(Password)){
                Intent j = new Intent(this, welcomeAdmin.class);
                startActivity(j);
                loginFound = true;
            };
        }
        if (!loginFound){
            Toast.makeText(this, "Login not found", Toast.LENGTH_LONG).show();
        }

    }
}
