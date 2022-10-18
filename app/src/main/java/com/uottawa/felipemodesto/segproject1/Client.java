package com.uottawa.felipemodesto.segproject1;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Client {
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String Address;
    public String creditCardNumber;
    public String id;
    public Client( String firstName, String lastName, String email, String password, String Address, String creditCardNumber) {

        if ( firstName == null || lastName == null || email == null || password == null || Address == null || creditCardNumber == null)
            throw new IllegalArgumentException( "null value" );

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.Address = Address;
        this.creditCardNumber = creditCardNumber;
    }
    public Client() {
    }
    public Client(String id, String firstName, String lastName, String email, String password, String Address, String creditCardNumber) {

        if ( firstName == null || lastName == null || email == null || password == null || Address == null || creditCardNumber == null)
            throw new IllegalArgumentException( "null value" );

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.Address = Address;
        this.creditCardNumber = creditCardNumber;
    }
}
