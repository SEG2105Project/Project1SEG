package com.uottawa.felipemodesto.segproject1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class cookSuspended extends Activity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooksuspended);

        Bundle b = getIntent().getExtras();
        int id = b.getInt("days");

        TextView tv = (TextView)findViewById(R.id.textView7);

        if (id != -666) {
            tv.setText("This account has been suspended for "+ id +" Days");
        }
        else {
            tv.setText("This account has been permanently suspended");
        }
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
