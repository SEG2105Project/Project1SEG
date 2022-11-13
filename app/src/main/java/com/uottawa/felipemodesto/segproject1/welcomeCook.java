package com.uottawa.felipemodesto.segproject1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class welcomeCook extends Activity{
    String cookId;

    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        cookId = bundle.getString("cookId");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomecook);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cookLogOffID:
                cookLogOffClick(v);
                break;
            case R.id.addMealID:
                addMealClick(v);
                break;
            case R.id.viewOfferedMealsId:

        }
    }

    public void cookLogOffClick (View v) {
        Intent i = new Intent(this, Home.class);
        startActivity(i);
    }

    public void addMealClick (View v) {
        Intent i = new Intent(this, AddMeal.class);
        Bundle bundle = new Bundle();
        bundle.putString("cookId", cookId);
        i.putExtras(bundle);
        startActivity(i);
    }
    public void viewOfferedMealsClick (View v) {
        Intent i = new Intent(this, OfferedMeals.class);
        Bundle bundle = new Bundle();
        bundle.putString("cookId", cookId);
        i.putExtras(bundle);
        startActivity(i);
    }
}
