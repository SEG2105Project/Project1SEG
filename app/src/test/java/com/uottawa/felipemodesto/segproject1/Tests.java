package com.uottawa.felipemodesto.segproject1;

import org.junit.Assert;
import org.junit.Test;

public class Tests {
    @Test
    public void clientSignUpTest1() {
        ClientSignUp client1 = new ClientSignUp();
        client1.addClientTestValues("ted", "san", "tsan@gmail.com", "password", "19 crescent drive", "1234 5678 2563 9002");
        Assert.assertTrue(client1.testCheckClient(client1.getTestClientSignUp()));
    }

    @Test
    public void clientSignUpTest2() {
        ClientSignUp client2 = new ClientSignUp();
        client2.addClientTestValues(null, "san", "tsan@gmail.com", "", "19 crescent drive", "1234 5678 2563 9002");
        Assert.assertFalse(client2.testCheckClient(client2.getTestClientSignUp()));
    }

    @Test
    public void cookSignUpTest1() {
        CookSignUp cook1 = new CookSignUp();
        cook1.addCookTestValues("jon", "colsan", "jcolsan@gmail.com", "password1234", "42 great road", "i cook good food", true);
        Assert.assertTrue(cook1.testCheckCook(cook1.getTestCookSignUp()));
    }

    @Test
    public void cookSignUpTest2() {
        CookSignUp cook2 = new CookSignUp();
        cook2.addCookTestValues("jon", "colsan", "jcolsan@gmail.com", "password1234", "42 great road", "i cook good food", false);
        Assert.assertFalse(cook2.testCheckCook(cook2.getTestCookSignUp()));
    }
}