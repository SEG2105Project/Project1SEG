package com.uottawa.felipemodesto.segproject1;

public class Meal {
    public String mealName;
    public String mealType;
    public String cuisineType;
    public String listOfIngredients;
    public String Allergens;
    public String price;
    public String mealDescription;
    public String cookId;
    public String id;
    public boolean offered;

    public Meal(String mealName, String mealType, String cuisineType, String listOfIngredients, String Allergens, String Price, String mealDescription, String cookId, boolean offered) {

        if ( mealName == null || mealType == null || cuisineType == null || listOfIngredients == null || Allergens == null || Price == null || mealDescription == null || cookId == null)
            throw new IllegalArgumentException( "null value" );

        this.mealName = mealName;
        this.mealType = mealType;
        this.cuisineType = cuisineType;
        this.listOfIngredients = listOfIngredients;
        this.Allergens = Allergens;
        this.price = Price;
        this.mealDescription = mealDescription;
        this.cookId = cookId;
        this.offered = offered;
    }
    public Meal(){};
    public Meal(String id,  String mealName, String mealType, String cuisineType, String listOfIngredients, String Allergens, String Price, String mealDescription, String cookId, boolean offered) {

        if (id==null || mealName == null || mealType == null || cuisineType == null || listOfIngredients == null || Allergens == null || Price == null || mealDescription == null || cookId == null)
            throw new IllegalArgumentException( "null value" );

        this.mealName = mealName;
        this.mealType = mealType;
        this.cuisineType = cuisineType;
        this.listOfIngredients = listOfIngredients;
        this.Allergens = Allergens;
        this.price = Price;
        this.mealDescription = mealDescription;
        this.cookId = cookId;
        this.id = id;
        this.offered = offered;
    }
    public String getId() {
        return id;
    }
    public String getMealName() {
        return mealName;
    }
    public String getPrice() {
        return price;
    }
    public String getMealDescription() {
        return mealDescription;
    }
    public Boolean getOffered() {
        return offered;
    }
}
