package com.uottawa.felipemodesto.segproject1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.text.TextUtils;
import android.widget.Toast;


public class ClientSignUp extends Activity{

    EditText editTextLastName;
    EditText editTextFirstName;
    EditText editTextEmail;
    EditText editTextPassword;
    EditText editTextAddress;
    EditText editTextCreditCard;

    Button buttonClientSignUp;

    List<Client> clients;
    DatabaseReference databaseClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientsignup);

        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextCreditCard = (EditText) findViewById(R.id.editTextCreditCardNumber);
        buttonClientSignUp = (Button) findViewById(R.id.buttonClientSignUp);
        clients = new ArrayList<>();
        databaseClient = FirebaseDatabase.getInstance().getReference("clients");
        //adding an onclicklistener to button
        buttonClientSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                addClient();
            }
        });
    }

    @Override
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
    }

    private void addClient() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String creditCardNumber = editTextCreditCard.getText().toString().trim();
        //FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef2 = database.getReference("First Name");
        //myRef2.setValue( editTextFirstName.getText().toString().trim());

        if(!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(creditCardNumber)){
            String id = databaseClient.push().getKey();

            Client client = new Client(id, firstName, lastName, email, password, address, creditCardNumber);

            databaseClient.child(id).setValue(client);

            editTextFirstName.setText("");
            editTextLastName.setText("");
            editTextEmail.setText("");
            editTextPassword.setText("");
            editTextAddress.setText("");
            editTextCreditCard.setText("");

            Toast.makeText(this, "Sign up Successful", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Please Fill all fields", Toast.LENGTH_LONG).show();
        }

    }
}
