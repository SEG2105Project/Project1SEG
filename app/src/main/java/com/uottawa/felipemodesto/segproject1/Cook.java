package com.uottawa.felipemodesto.segproject1;

public class Cook {
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String Address;
    public String description;
    public String voidCheque;
    public String id;

    public Cook( String firstName, String lastName, String email, String password, String Address, String description, String voidCheque) {

        if ( firstName == null || lastName == null || email == null || password == null || Address == null || description == null || voidCheque == null )
            throw new IllegalArgumentException( "null value" );

        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.Address = Address;
        this.description = description;
        this.voidCheque = voidCheque;
    }
    public Cook(String id, String firstName, String lastName, String email, String password, String Address, String description, String voidCheque) {

        if ( firstName == null || lastName == null || email == null || password == null || Address == null || description == null || voidCheque == null )
            throw new IllegalArgumentException( "null value" );
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.Address = Address;
        this.description = description;
        this.voidCheque = voidCheque;
        this.id = id;
    }
}
