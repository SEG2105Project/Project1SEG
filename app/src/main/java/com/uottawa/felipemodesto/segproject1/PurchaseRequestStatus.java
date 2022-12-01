package com.uottawa.felipemodesto.segproject1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;

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


public class PurchaseRequestStatus extends Activity{
    List<PurchaseRequest> purchaseRequests;
    ListView listViewPurchaseRequests;
    DatabaseReference databasePurchaseRequest;
    DatabaseReference databaseMenu;
    List<Meal> meals;
    DatabaseReference databaseCook;
    List<Cook> cooks;
    String clientId;
    Integer numberOfRatings =- 1;
    Integer CurrentRating =- 1;
    String cookFirstName ="";
    String cookLastName ="";
    DatabaseReference databaseComplaints;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientpurchaserequests);
        Bundle bundle = getIntent().getExtras();
        clientId = bundle.getString("clientId");
        databasePurchaseRequest = FirebaseDatabase.getInstance().getReference("purchaseRequests");
        databaseMenu = FirebaseDatabase.getInstance().getReference("Menus");
        databaseCook = FirebaseDatabase.getInstance().getReference("cooks");
        databaseComplaints = FirebaseDatabase.getInstance().getReference("complaint");
        purchaseRequests = new ArrayList<>();
        meals = new ArrayList<>();
        cooks = new ArrayList<>();
        listViewPurchaseRequests = (ListView) findViewById(R.id.listViewPurchaseRequests);
        listViewPurchaseRequests.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                PurchaseRequest purchaseRequest = purchaseRequests.get(i);
                if (purchaseRequest.status.equals("Approved")){
                    rateOrComplainCook(purchaseRequest.CookId);
                } else{
                    Toast.makeText(getApplicationContext(), "Purchase Request Must Be Approved To Submit Complaint or Rate Cook", Toast.LENGTH_LONG).show();
                }
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

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot postSnapshot2: postSnapshot.getChildren()){
                        Meal meal = postSnapshot2.getValue(Meal.class);
                        meals.add(meal);
                    }
                }
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
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                databasePurchaseRequest.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        purchaseRequests.clear();

                        for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                            PurchaseRequest purchaseRequest = postSnapshot.getValue(PurchaseRequest.class);
                            if (purchaseRequest!= null && purchaseRequest.ClientId.equals(clientId)){
                                purchaseRequests.add(purchaseRequest);
                            }
                        }
                        PurchaseRequestList purchaseRequestAdapter = new PurchaseRequestList(PurchaseRequestStatus.this, purchaseRequests, cooks, meals);
                        listViewPurchaseRequests.setAdapter(purchaseRequestAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                    }
                });
            }
        }, 500);



    }

    private void rateOrComplainCook(String cookId){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.rateorcomplain_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editCookRating= (EditText) dialogView.findViewById(R.id.editRating);
        final EditText editComplaintDescription = (EditText) dialogView.findViewById(R.id.editComplaintDescription);
        final Button submitComplaint = (Button) dialogView.findViewById(R.id.submitComplaint);
        final Button submitRating = (Button) dialogView.findViewById(R.id.submitRating);
        dialogBuilder.setTitle("Rate or Suspend ");
        final AlertDialog b = dialogBuilder.create();
        b.show();
        for (int i=0; i<cooks.size(); i++){
            if (cooks.get(i).id.equals(cookId)){
                cookFirstName = cooks.get(i).firstName;
                cookLastName = cooks.get(i).lastName;
                numberOfRatings = cooks.get(i).totalRatings;
                CurrentRating = cooks.get(i).rating;
            }
        }
        submitRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rating = editCookRating.getText().toString().trim();
                updateRating(cookId, rating, numberOfRatings, CurrentRating);
                b.dismiss();
            }
        });
        submitComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = editComplaintDescription.getText().toString().trim();
                submitComplaint(description, cookFirstName, cookLastName, cookId);
                b.dismiss();
            }
        });
    };

    private void submitComplaint(String description, String cookFirstName, String cookLastName, String cookId) {
        try{
            if(!TextUtils.isEmpty(description) && !TextUtils.isEmpty(cookFirstName) && !TextUtils.isEmpty(cookLastName)){
                String id = databaseComplaints.push().getKey();

                Complaint complaint = new Complaint(id,description, cookFirstName, cookLastName, cookId);

                databaseComplaints.child(id).setValue(complaint);

                Toast.makeText(this, "Complaint Successfully made", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Please Enter a Complaint Description", Toast.LENGTH_LONG).show();
            }
        }
         catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Ensure all Fields have valid input", Toast.LENGTH_LONG).show();
        }
    }
    private void updateRating(String cookId, String rating, Integer numberOfRatings, Integer currentRating) {
        try{
            Integer intRating = Integer.valueOf(rating);
            if (intRating>=1 && intRating<=5){
                if (currentRating == null){
                    currentRating=0;
                }
                if (numberOfRatings == null){
                    numberOfRatings=0;
                }
                Integer newRating = ((currentRating*numberOfRatings)+intRating)/(numberOfRatings+1);
                Task<Void> dR = FirebaseDatabase.getInstance().getReference("cooks").child(cookId).child("rating").setValue(newRating);
                Integer totalNumberOfRatings =Integer.valueOf(numberOfRatings + 1);
                Task<Void> dR2 = FirebaseDatabase.getInstance().getReference("cooks").child(cookId).child("totalRatings").setValue(totalNumberOfRatings);
                Toast.makeText(getApplicationContext(), "Rating Submitted Successfully", Toast.LENGTH_LONG).show();
                onStart();
            } else {
                Toast.makeText(getApplicationContext(), "Please Enter a Rating From 1-5", Toast.LENGTH_LONG).show();
            }
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Ensure Rating input is an Integer", Toast.LENGTH_LONG).show();
        }

    }
}
