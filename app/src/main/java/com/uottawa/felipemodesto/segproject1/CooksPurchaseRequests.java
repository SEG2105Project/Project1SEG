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
import android.widget.TextView;
import android.widget.Toast;


public class CooksPurchaseRequests extends Activity{
    List<PurchaseRequest> purchaseRequests;
    ListView listViewPurchaseRequests;
    DatabaseReference databasePurchaseRequest;
    String cookId;
    DatabaseReference databaseMenu;
    List<Meal> meals;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientpurchaserequests);
        TextView longPressHint = (TextView) findViewById(R.id.textView2);
        longPressHint.setText("(Long press to Approve or Reject Purchase Request)");
        Bundle bundle = getIntent().getExtras();
        cookId = bundle.getString("cookId");
        databaseMenu = FirebaseDatabase.getInstance().getReference("Menus");
        meals = new ArrayList<>();
        databasePurchaseRequest = FirebaseDatabase.getInstance().getReference("purchaseRequests");
        purchaseRequests = new ArrayList<>();
        listViewPurchaseRequests = (ListView) findViewById(R.id.listViewPurchaseRequests);
        listViewPurchaseRequests.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                PurchaseRequest purchaseRequest = purchaseRequests.get(i);
                approveOrReject(purchaseRequest.Id);
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
                            if (purchaseRequest!= null && purchaseRequest.CookId.equals(cookId) && purchaseRequest.status.equals("Pending"))
                                purchaseRequests.add(purchaseRequest);
                        }
                        CooksPSList cooksPSList = new CooksPSList(CooksPurchaseRequests.this, purchaseRequests, meals);
                        listViewPurchaseRequests.setAdapter(cooksPSList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {


                    }
                });
            }
        }, 500);


    }

    private void approveOrReject(String purchaseRequestId){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.approveorreject_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button approve = (Button) dialogView.findViewById(R.id.buttonApprovePurchaseRequest);
        final Button reject = (Button) dialogView.findViewById(R.id.buttonRejectPurchaseRequest);
        dialogBuilder.setTitle("Approve Or Reject Purchase Request");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        approve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                approvePurchaseRequest(purchaseRequestId);
                b.dismiss();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectPurchaseRequest(purchaseRequestId);
                b.dismiss();
            }
        });
    };

    private void approvePurchaseRequest(String purchaseRequestId) {
        Task<Void> dR = FirebaseDatabase.getInstance().getReference("purchaseRequests").child(purchaseRequestId).child("status").setValue("Approved");
        Toast.makeText(getApplicationContext(), "Purchase Request Approved", Toast.LENGTH_LONG).show();
    }
    private void rejectPurchaseRequest(String purchaseRequestId) {
        Task<Void> dR = FirebaseDatabase.getInstance().getReference("purchaseRequests").child(purchaseRequestId).child("status").setValue("Rejected");
        Toast.makeText(getApplicationContext(), "Purchase Request Rejected", Toast.LENGTH_LONG).show();
    }
}
