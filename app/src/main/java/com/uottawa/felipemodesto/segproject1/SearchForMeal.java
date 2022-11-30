package com.uottawa.felipemodesto.segproject1;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

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
        listViewMeals.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meal meal = meals.get(i);
                showCookInfo(meal.cookId);
                return true;
            }
        });
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

    private void showCookInfo(String cookId) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.cookinfo_dialog, null);
        dialogBuilder.setView(dialogView);
        final TextView cookFirstName = (TextView) dialogView.findViewById(R.id.textViewCookFirstName);
        final TextView cookLastName = (TextView) dialogView.findViewById(R.id.textViewCookLastName);
        final TextView cookDescription = (TextView) dialogView.findViewById(R.id.textViewCookDescription);
        final TextView cookRating= (TextView) dialogView.findViewById(R.id.textViewCookRating);
        for (int i=0; i<cooks.size(); i++){
            if (cooks.get(i).id.equals(cookId)){
                cookFirstName.setText("\nCook First Name: " +cooks.get(i).firstName);
                cookLastName.setText("\nCook Last Name: " +cooks.get(i).lastName);
                cookDescription.setText("\nCook Description: " +cooks.get(i).description);
                cookRating.setText("\nCook Rating Out of 5: " +cooks.get(i).rating);
            }
        }

        dialogBuilder.setTitle("Cook Information");
        final AlertDialog b = dialogBuilder.create();
        b.show();

    }

}
