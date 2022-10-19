package com.uottawa.felipemodesto.segproject1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class welcomeCook extends Activity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomecook);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cookLogOffID:
                cookLogOffClick(v);
                break;
        }
    }

    public void cookLogOffClick (View v) {
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }
}
