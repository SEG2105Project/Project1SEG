package com.uottawa.felipemodesto.segproject1;

import android.content.Intent;
import android.os.Bundle;
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


public class handleComplaints extends Activity{
    List<Complaint> complaints;
    ListView listViewComplaints;
    DatabaseReference databaseComplaints;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookcomplaints);
        databaseComplaints = FirebaseDatabase.getInstance().getReference("complaint");
        complaints = new ArrayList<>();
        listViewComplaints = (ListView) findViewById(R.id.listViewComplaints);
        listViewComplaints.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Complaint complaint = complaints.get(i);
                showUpdateDeleteDialog(complaint.getId(), complaint.getCookId());
                return true;
            }
        });

    }

    protected void onStart() {
        super.onStart();

        databaseComplaints.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                complaints.clear();

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Complaint complaint = postSnapshot.getValue(Complaint.class);
                    complaints.add(complaint);
                }
                ComplaintList productAdapter = new ComplaintList(handleComplaints.this, complaints);
                listViewComplaints.setAdapter(productAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });
    }
    private void showUpdateDeleteDialog(final String complaintId, String cookId) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.suspend_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTimeOfSuspension = (EditText) dialogView.findViewById(R.id.editSuspensionTime);
        final Button buttonDismiss = (Button) dialogView.findViewById(R.id.buttonDismissComplaint);
        final Button buttonSuspendCook = (Button) dialogView.findViewById(R.id.buttonSuspendCook);

        dialogBuilder.setTitle("Complaint Id: "+complaintId);
        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss(complaintId);
                b.dismiss();
            }
        });

        buttonSuspendCook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String daysOfSuspension = editTimeOfSuspension.getText().toString().trim();
                if (!TextUtils.isEmpty(daysOfSuspension)) {
                    suspendCook(cookId, daysOfSuspension, complaintId);
                    b.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "Please Specify Days of Suspension", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void dismiss(String complaintId) {
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("complaint").child(complaintId);

        dR.removeValue();

        Toast.makeText(getApplicationContext(), "Complaint Dismissed", Toast.LENGTH_LONG).show();
    }
    private void suspendCook(String cookId, String daysOfSuspension, String complaintId) {
            Task<Void> dR = FirebaseDatabase.getInstance().getReference("cooks").child(cookId).child("suspended").setValue(true);
            Task<Void> dR2 = FirebaseDatabase.getInstance().getReference("cooks").child(cookId).child("daysOfSuspension").setValue(Integer.valueOf(daysOfSuspension));
            DatabaseReference dR3 = FirebaseDatabase.getInstance().getReference("complaint").child(complaintId);
            dR3.removeValue();
            Toast.makeText(getApplicationContext(), "Cook Suspended", Toast.LENGTH_LONG).show();
    }
}
