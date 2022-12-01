package com.uottawa.felipemodesto.segproject1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uottawa.felipemodesto.segproject1.Complaint;
import com.uottawa.felipemodesto.segproject1.R;

import java.util.ArrayList;
import java.util.List;

public class CooksPSList extends ArrayAdapter<PurchaseRequest> {
    private Activity context;
    List<PurchaseRequest> purchaseRequests;
    List<Meal> meals;
    String cookId;
    String MealId;
    public CooksPSList(Activity context, List<PurchaseRequest> purchaseRequests, List<Meal> meals) {
        super(context, R.layout.layout_purchaserequest_list, purchaseRequests);
        this.context = context;
        this.purchaseRequests = purchaseRequests;
        this.meals = meals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_cookpurchaserequest_list, null, true);

        TextView textViewMealName = (TextView) listViewItem.findViewById(R.id.textViewMealName);
        TextView textViewPickUpTime = (TextView) listViewItem.findViewById(R.id.textViewPickUpTime);
        TextView textViewPurchaseRequestStatus = (TextView) listViewItem.findViewById(R.id.textViewPurchaseRequestStatus);

        PurchaseRequest purchaseRequest = purchaseRequests.get(position);
        MealId = purchaseRequest.MealId;
        String MealName ="";
        for (int i=0; i<meals.size(); i++){
            if (meals.get(i).id.equals(MealId)) {
                MealName =  meals.get(i).mealName;
            }
        }

        textViewMealName.setText("Meal Name: " + MealName);
        textViewPickUpTime.setText("Pick Up Time: " + purchaseRequest.pickupTime);
        textViewPurchaseRequestStatus.setText("Status: " + purchaseRequest.status);

        return listViewItem;
    }
}
