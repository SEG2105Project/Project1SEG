package com.uottawa.felipemodesto.segproject1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CookSignUp extends AppCompatActivity{

    EditText editTextLastName2;
    EditText editTextFirstName2;
    EditText editTextEmail2;
    EditText editTextPassword2;
    EditText editTextAddress2;
    EditText editTextDescription;
    //EditText editTextVoidCheque;

    Button buttonCookSignUp;

    List<Cook> cooks;
    DatabaseReference databaseCook;

    Button btn2;
    ImageView imageView;
    int SELECT_IMAGE_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_cooksignup);
//
        editTextFirstName2 = (EditText) findViewById(R.id.editTextFirstName2);
        editTextLastName2 = (EditText) findViewById(R.id.editTextLastName2);
        editTextEmail2 = (EditText) findViewById(R.id.editTextEmail2);
        editTextPassword2 = (EditText) findViewById(R.id.editTextPassword2);
        editTextAddress2 = (EditText) findViewById(R.id.editTextAddress2);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        //editTextVoidCheque = (EditText) findViewById(R.id.editTextVoidCheque);
        buttonCookSignUp = (Button) findViewById(R.id.buttonCookSignUp);

        btn2 = findViewById(R.id.voidbtn);
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Title"), SELECT_IMAGE_CODE);

            }
        });

        cooks = new ArrayList<>();
        databaseCook = FirebaseDatabase.getInstance().getReference("cooks");
        //adding an onclicklistener to button
        buttonCookSignUp.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                addCook();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resutltCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resutltCode, data);

        if(requestCode == 1){

            try{
                Uri uri = data.getData();
                btn2.setText("Image Uploaded");
            }
            catch (Exception e){
                btn2.setText("Image not Uploaded, try again");
            }
        }

    }

    private void addCook() {
        String firstName = editTextFirstName2.getText().toString().trim();
        String lastName = editTextLastName2.getText().toString().trim();
        String email = editTextEmail2.getText().toString().trim();
        String password = editTextPassword2.getText().toString().trim();
        String address = editTextAddress2.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if(!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(description)){
            String id = databaseCook.push().getKey();
            String voidCheque ="test";
            Cook cook = new Cook(id, firstName, lastName, email, password, address, description, voidCheque);

            databaseCook.child(id).setValue(cook);

            editTextFirstName2.setText("");
            editTextLastName2.setText("");
            editTextEmail2.setText("");
            editTextPassword2.setText("");
            editTextAddress2.setText("");
            editTextDescription.setText("");

            Toast.makeText(this, "Sign up Successful", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, Login.class);
            startActivity(i);
        }
        else {
            Toast.makeText(this, "Please Fill all fields", Toast.LENGTH_LONG).show();
        }

    }
}
