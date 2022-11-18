package com.uottawa.felipemodesto.segproject1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.android.gms.tasks.Task;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddMeal extends Activity {
    List<Meal> meals;
    ListView listViewMeals;
    DatabaseReference databaseMenu;
    Button buttonAddMealToMenu;
    EditText editMealName;
    EditText editMealType;
    EditText editCuisineType;
    EditText editListOfIngredients;
    EditText editAllergens;
    EditText editPrice;
    EditText editMealDescription;
    String cookId;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmeal);
        Bundle bundle = getIntent().getExtras();
        cookId = bundle.getString("cookId");
        editMealName = (EditText) findViewById(R.id.editMealName);
        editMealType = (EditText) findViewById(R.id.editMealType);
        editCuisineType = (EditText) findViewById(R.id.editCuisineType);
        editListOfIngredients = (EditText) findViewById(R.id.editListOfIngredients);
        editAllergens = (EditText) findViewById(R.id.editAllergens);
        editPrice = (EditText) findViewById(R.id.editPrice);
        editMealDescription = (EditText) findViewById(R.id.editMealDescription);
        meals = new ArrayList<>();
        listViewMeals = findViewById(R.id.listViewMeals);
        buttonAddMealToMenu = (Button) findViewById(R.id.addMealButton);
        databaseMenu = FirebaseDatabase.getInstance().getReference("Menus").child("cook"+cookId);
        listViewMeals.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meal meal = meals.get(i);
                showUpdateDeleteDialog(meal.getId(), meal.getMealName(), meal.getOffered());
                return true;
            }
        });
        buttonAddMealToMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                addMeal();
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
                    meals.add(meal);
                }
                MealList mealAdapter = new MealList(AddMeal.this, meals);
                listViewMeals.setAdapter(mealAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }

    private void showUpdateDeleteDialog(String mealId, String mealName, Boolean offered) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.deletemeal_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonAddMealToOfferedList = (Button) dialogView.findViewById(R.id.buttonAddMealToOfferedList);
        final Button buttonDeleteMealFromMenu = (Button) dialogView.findViewById(R.id.buttonDeleteMealFromMenu);

        dialogBuilder.setTitle("Meal Name: "+mealName);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonAddMealToOfferedList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMealToOfferedList(mealId, offered);
                b.dismiss();
            }
        });

        buttonDeleteMealFromMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (offered){
                    Toast.makeText(getApplicationContext(), "Cannot delete Meal as it is Currently Offered", Toast.LENGTH_LONG).show();
                } else{
                    deleteMealFromMenu(mealId);
                }

                b.dismiss();

            }
        });
    }

    private void addMealToOfferedList(String mealId, boolean offered) {
        if (offered){
            Toast.makeText(getApplicationContext(), "This Meal is Already Offered", Toast.LENGTH_LONG).show();
        } else{
            Task<Void> dR = databaseMenu.child(mealId).child("offered").setValue(true);
            Toast.makeText(getApplicationContext(), "Meal is Now Offered", Toast.LENGTH_LONG).show();
        }

    }
    private void deleteMealFromMenu(String mealId) {
        //method to delete meal from menu
        DatabaseReference dR = databaseMenu.child(mealId);

        dR.removeValue();

        Toast.makeText(getApplicationContext(), "Meal Removed From Menu", Toast.LENGTH_LONG).show();
    }
    private void addMeal() {
        //add a meal to menu
        String mealName = editMealName.getText().toString().trim();
        String mealType = editMealType.getText().toString().trim();
        String cuisineType = editCuisineType.getText().toString().trim();
        String listOfIngredients = editListOfIngredients.getText().toString().trim();
        String Allergens = editAllergens.getText().toString().trim();
        String Price = editPrice.getText().toString().trim();
        String MealDescription = editMealDescription.getText().toString().trim();

        if(!TextUtils.isEmpty(mealName) && !TextUtils.isEmpty(mealType) && !TextUtils.isEmpty(cuisineType) && !TextUtils.isEmpty(listOfIngredients) && !TextUtils.isEmpty(Allergens) && !TextUtils.isEmpty(Price) && !TextUtils.isEmpty(MealDescription)){
            String id = databaseMenu.push().getKey();

            Meal meal = new Meal(id, mealName, mealType, cuisineType, listOfIngredients, Allergens, Price, MealDescription, cookId, false);

            databaseMenu.child(id).setValue(meal);

            editMealName.setText("");
            editMealType.setText("");
            editCuisineType.setText("");
            editListOfIngredients.setText("");
            editAllergens.setText("");
            editPrice.setText("");
            editMealDescription.setText("");

            Toast.makeText(this, "Meal Added to Menu", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Please Fill all fields", Toast.LENGTH_LONG).show();
        }

    }
}
