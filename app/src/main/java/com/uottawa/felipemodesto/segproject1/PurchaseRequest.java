package com.uottawa.felipemodesto.segproject1;

public class PurchaseRequest {
    public String Id;
    public String CookId;
    public String ClientId;
    public String MealId;
    public String pickupTime;
    public String status;
    public boolean notificationSent;
    public PurchaseRequest(String Id, String CookId, String ClientId, String MealId, String pickupTime){
        this.Id= Id;
        this.CookId= CookId;
        this.ClientId= ClientId;
        this.MealId= MealId;
        this.pickupTime= pickupTime;
        this.status="Pending";
        this.notificationSent = false;
    }
    public PurchaseRequest(){}
}
