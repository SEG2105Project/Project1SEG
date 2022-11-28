package com.uottawa.felipemodesto.segproject1;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.text.TextUtils;
import android.widget.Toast;
public class SearchForMeal extends Activity {
    DatabaseReference databaseMenu;
    List<Meal> meals;
    ListView listViewMeals;
    DatabaseReference databaseCook;
    List<Cook> cooks;
    EditText editMealName;
    EditText editMealType;
    EditText editCuisineType;
    String MealName="*****";
    String MealType="*****";
    String CuisineType="*****";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchformeal);
        editMealName = (EditText) findViewById(R.id.mealNameInput);
        editMealType = (EditText) findViewById(R.id.mealTypeInput);
        editCuisineType = (EditText) findViewById(R.id.cuisineTypeInput);
        databaseMenu = FirebaseDatabase.getInstance().getReference("Menus");
        listViewMeals = findViewById(R.id.mealsList);
        databaseCook = FirebaseDatabase.getInstance().getReference("cooks");
        meals = new ArrayList<>();
        cooks = new ArrayList<>();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.findMealsBtn:
                findMealsClick(v);
                break;
        }
    }
    protected void onStart() {
        super.onStart();

        databaseMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                meals.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot postSnapshot2: postSnapshot.getChildren()){
                        Meal meal = postSnapshot2.getValue(Meal.class);
                        if (meal.cookId!=null && cookIsNotSuspended(meal.cookId) && (meal.mealName.contains(MealName) || meal.mealType.contains(MealType) || meal.cuisineType.contains(CuisineType))){
                            meals.add(meal);
                        }
                    }
                }
                MealList mealAdapter = new MealList(SearchForMeal.this, meals);
                listViewMeals.setAdapter(mealAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

        databaseCook.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cooks.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Cook cook = postSnapshot.getValue(Cook.class);
                    cooks.add(cook);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    public void findMealsClick (View v){
        MealName = editMealName.getText().toString().trim();
        MealType = editMealType.getText().toString().trim();
        CuisineType = editCuisineType.getText().toString().trim();
        if (CuisineType.equals("") && MealName.equals("") && MealType.equals("")){
            Toast.makeText(this, "Please Fill at least 1 Field", Toast.LENGTH_LONG).show();
        }
        if (MealName.equals("")){
            MealName = "******";
        }
        if (MealType.equals("")){
            MealType = "******";
        }
        if (CuisineType.equals("")){
            CuisineType = "******";
        }
        onStart();
    }

    public boolean cookIsNotSuspended(String cookId){
        for (int i=0; i<cooks.size(); i++){
            if (cookId != null && cooks.get(i).suspended != null && cooks.get(i).suspended == true && cooks.get(i).id.equals(cookId)){
                return false;
            }
        }
        return true;
    }

}
