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


public class Admin {
    public String userName;
    public String password;
    public Admin(String userName, String password) {

        if ( userName == null || password == null)
            throw new IllegalArgumentException( "null value" );

        this.userName = userName;
        this.password = password;
    }
    public Admin() {
    }
}
