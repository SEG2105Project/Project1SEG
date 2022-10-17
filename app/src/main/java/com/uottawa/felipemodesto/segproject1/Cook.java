package com.uottawa.felipemodesto.segproject1;

public class Cook {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String Address;
    private String description;
    private String voidCheque;
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
}
