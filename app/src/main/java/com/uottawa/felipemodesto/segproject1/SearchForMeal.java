package com.uottawa.felipemodesto.segproject1;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class SearchForMeal extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchformeal);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginID:
                findMealsClick(v);
                break;
        }
    }


    public void findMealsClick (View v){
        //code for login button
        Intent i = new Intent(this, Login.class);
        startActivity(i);
    }


}
