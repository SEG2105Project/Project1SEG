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

public class MealList extends ArrayAdapter<Meal> {
    private Activity context;
    List<Meal> menu;

    public MealList(Activity context, List<Meal> menu) {
        super(context, R.layout.layout_meal_list, menu);
        this.context = context;
        this.menu = menu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_meal_list, null, true);

        TextView textViewMealName = (TextView) listViewItem.findViewById(R.id.textViewMealName);
        TextView textViewMealPrice = (TextView) listViewItem.findViewById(R.id.textViewMealPrice);
        TextView textViewMealDescription = (TextView) listViewItem.findViewById(R.id.textViewMealDescription);

        Meal meal = menu.get(position);
        textViewMealName.setText(meal.getMealName());
        textViewMealPrice.setText(String.valueOf("$"+meal.getPrice()));
        textViewMealDescription.setText(String.valueOf(meal.getMealDescription()));
        return listViewItem;
    }
}
