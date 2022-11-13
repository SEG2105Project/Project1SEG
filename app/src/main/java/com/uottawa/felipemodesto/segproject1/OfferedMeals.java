package com.uottawa.felipemodesto.segproject1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.widget.AdapterView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.text.TextUtils;
import android.widget.ListView;
import android.widget.Toast;


public class OfferedMeals extends Activity{
    List<Meal> meals;
    ListView listViewMeals;
    DatabaseReference databaseMenu;
    String cookId;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offeredmeals);
        Bundle bundle = getIntent().getExtras();
        cookId = bundle.getString("cookId");
        databaseMenu = FirebaseDatabase.getInstance().getReference("Menus").child("cook"+cookId);
        meals = new ArrayList<>();
        listViewMeals = (ListView) findViewById(R.id.listOfferedMeals);
        listViewMeals.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meal meal = meals.get(i);
                showUpdateDeleteDialog(meal.getId(), meal.getMealName());
                return true;
            }
        });

    }

    protected void onStart() {
        super.onStart();

        databaseMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                meals.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Meal meal = postSnapshot.getValue(Meal.class);
                    if (meal.offered){
                        meals.add(meal);
                    }
                }
                MealList mealAdapter = new MealList(OfferedMeals.this, meals);
                listViewMeals.setAdapter(mealAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
    private void showUpdateDeleteDialog(String mealId, String mealName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.remove_offered_meal_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonRemoveMealFromOfferedList = (Button) dialogView.findViewById(R.id.buttonRemoveMealFromOfferedList);

        dialogBuilder.setTitle("Meal Name: "+mealName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonRemoveMealFromOfferedList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task<Void> dR = databaseMenu.child(mealId).child("offered").setValue(false);
                b.dismiss();
            }
        });
    }

}
