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

public class PurchaseRequestList extends ArrayAdapter<PurchaseRequest> {
    private Activity context;
    List<PurchaseRequest> purchaseRequests;
    List<Cook> cooks = new ArrayList<>();
    List<Meal> meals = new ArrayList<>();
    String cookId;
    String MealId;
    public PurchaseRequestList(Activity context, List<PurchaseRequest> purchaseRequests, List<Cook> cooks, List<Meal> meals) {
        super(context, R.layout.layout_purchaserequest_list, purchaseRequests);
        this.context = context;
        this.purchaseRequests = purchaseRequests;
        this.cooks = cooks;
        this.meals = meals;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_purchaserequest_list, null, true);

        TextView textViewMealName = (TextView) listViewItem.findViewById(R.id.textViewMealName);
        TextView textViewMealPrice = (TextView) listViewItem.findViewById(R.id.textViewMealPrice);
        TextView textViewMealDescription = (TextView) listViewItem.findViewById(R.id.textViewMealDescription);
        TextView textViewMealCook = (TextView) listViewItem.findViewById(R.id.textViewMealCook);
        TextView textViewPickUpTime = (TextView) listViewItem.findViewById(R.id.textViewPickUpTime);
        TextView textViewPurchaseRequestStatus = (TextView) listViewItem.findViewById(R.id.textViewPurchaseRequestStatus);

        PurchaseRequest purchaseRequest = purchaseRequests.get(position);
        cookId = purchaseRequest.CookId;
        MealId = purchaseRequest.MealId;
        String MealName ="";
        String MealPrice="";
        String MealDescription="";
        String cookName="";
        for (int i=0; i<meals.size(); i++){
            if (meals.get(i).id.equals(MealId)) {
                MealName =  meals.get(i).mealName;
                MealPrice =  meals.get(i).price;
                MealDescription =  meals.get(i).mealDescription;
            }
        }
        for (int i=0; i<cooks.size(); i++){
            if (cooks.get(i).id.equals(cookId)) {
                cookName =  cooks.get(i).firstName + " " +cooks.get(i).lastName;
            }
        }
        textViewMealName.setText("Meal Name: " + MealName);
        textViewMealPrice.setText("Meal Price: $" + MealPrice);
        textViewMealDescription.setText("Meal Description: " + MealDescription);
        textViewMealCook.setText("Cook Name: " + cookName);
        textViewPickUpTime.setText("Pick Up Time: " + purchaseRequest.pickupTime);
        textViewPurchaseRequestStatus.setText("Status: " + purchaseRequest.status);

        return listViewItem;
    }
}
