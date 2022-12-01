package com.uottawa.felipemodesto.segproject1;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import android.os.Looper;
import android.os.Handler;

public class welcomeClient extends Activity{
    String clientId;
    Button viewPurchaseRequests;
    List<PurchaseRequest> purchaseRequests;
    DatabaseReference databasePurchaseRequest;
    TextView Notification;
    TextView NotificationText;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomeclient);
        Notification = (TextView) findViewById(R.id.textViewNotificationText);
        Notification.setVisibility(View.INVISIBLE);
        NotificationText = (TextView) findViewById(R.id.textViewNotification);
        NotificationText.setVisibility(View.INVISIBLE);
        Bundle bundle = getIntent().getExtras();
        clientId = bundle.getString("clientId");
        viewPurchaseRequests = (Button) findViewById(R.id.viewPurchaseRequests);
        databasePurchaseRequest = FirebaseDatabase.getInstance().getReference("purchaseRequests");

        purchaseRequests = new ArrayList<>();
        viewPurchaseRequests.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                viewPurchaseRequestsClick(view);
            }
        });
    }
    protected void onStart() {
        super.onStart();

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sendNotification(purchaseRequests);
            }
        }, 1500);

    }
    public void sendNotification(List<PurchaseRequest> purchaseRequests){
        for (int i=0; i<purchaseRequests.size(); i++){
            if (!purchaseRequests.get(i).status.equals("Pending") && purchaseRequests.get(i).notificationSent == false){
                Task<Void> dR = FirebaseDatabase.getInstance().getReference("purchaseRequests").child(purchaseRequests.get(i).Id).child("notificationSent").setValue(true);
                if (purchaseRequests.get(i).status.equals("Approved")){
                    NotificationText.setText("A Purchase Request Has been Approved!\nClick View Purchase Requests Button");
                } else if (purchaseRequests.get(i).status.equals("Rejected")){
                    NotificationText.setText("A Purchase Request Has been Rejected!\nClick View Purchase Requests Button");
                }
                final Handler handler = new Handler();
                Notification.setVisibility(View.VISIBLE);
                NotificationText.setVisibility(View.VISIBLE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Notification.setVisibility(View.INVISIBLE);
                        NotificationText.setVisibility(View.INVISIBLE);
                    }
                }, 8000);
            }
        }
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clientLogOffID:
                clientLogOffClick(v);
                break;
            case R.id.searchID:
                searchClick(v);
                break;
        }
    }

    public void clientLogOffClick (View v) {
        Intent i = new Intent(this, Home.class);

        startActivity(i);
    }

    public void searchClick (View v) {
        Intent i = new Intent(this, SearchForMeal.class);
        Bundle bundle = new Bundle();
        bundle.putString("clientId", clientId);
        i.putExtras(bundle);
        startActivity(i);
    }

    public void viewPurchaseRequestsClick (View v) {
        Intent j = new Intent(this, PurchaseRequestStatus.class);
        Bundle bundle = new Bundle();
        bundle.putString("clientId", clientId);
        j.putExtras(bundle);
        startActivity(j);
    }

}
