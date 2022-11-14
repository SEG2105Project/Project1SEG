package com.uottawa.felipemodesto.segproject1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uottawa.felipemodesto.segproject1.Complaint;
import com.uottawa.felipemodesto.segproject1.R;

import java.util.List;

public class ComplaintList extends ArrayAdapter<Complaint> {
    private Activity context;
    List<Complaint> complaints;

    public ComplaintList(Activity context, List<Complaint> complaints) {
        //List of complaints
        super(context, R.layout.layout_complaint_list, complaints);
        this.context = context;
        this.complaints = complaints;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_complaint_list, null, true);

        TextView textViewComplaintId = (TextView) listViewItem.findViewById(R.id.textViewComplaintId);
        TextView textViewComplaintDescription = (TextView) listViewItem.findViewById(R.id.textViewComplaintDescription);
        TextView textViewComplaintCook = (TextView) listViewItem.findViewById(R.id.textViewComplaintCook);

        Complaint complaint = complaints.get(position);
        textViewComplaintId.setText(complaint.getId());
        textViewComplaintDescription.setText(String.valueOf(complaint.getDescription()));
        textViewComplaintCook.setText(String.valueOf(complaint.getCookFirstName() + " " +complaint.getCookLastName()));
        return listViewItem;
    }
}
