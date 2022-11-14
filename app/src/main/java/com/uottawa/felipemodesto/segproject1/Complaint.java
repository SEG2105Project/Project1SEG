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


public class Complaint {
    //complaint info
    public String id;
    public String description;
    public String cookFirstName;
    public String cookLastName;
    public String cookId;
    public Complaint(String id, String description, String cookFirstName, String cookLastName, String cookId) {

        if (id == null || description == null || cookFirstName == null || cookLastName == null || cookId == null)
            throw new IllegalArgumentException( "null value" );
        this.id = id;
        this.description = description;
        this.cookFirstName = cookFirstName;
        this.cookLastName = cookLastName;
        this.cookId = cookId;
    }
    public Complaint() {
    }
    public String getId() {
        return id;
    }
    public String getCookId() {
        return cookId;
    }
    public String getDescription() {
        return description;
    }
    public String getCookFirstName() {
        return cookFirstName;
    }
    public String getCookLastName() {
        return cookLastName;
    }
}
