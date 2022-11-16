package com.uottawa.felipemodesto.segproject1;

import org.junit.Assert;
import org.junit.Test;

public class Tests {
    @Test
    public void clientSignUpTest1() {
        ClientSignUp firstclient = new ClientSignUp();
        firstclient.addClientTestValues("ted", "san", "tsan@gmail.com", "password", "19 crescent drive", "1234 5678 2563 9002");
        Assert.assertTrue(firstclient.testCheckClient(firstclient.getTestClientSignUp()));
    }

    @Test
    public void clientSignUpTest2() {
        ClientSignUp secondclient = new ClientSignUp();
        secondclient.addClientTestValues(null, "san", "tsan@gmail.com", "", "19 crescent drive", "1234 5678 2563 9002");
        Assert.assertFalse(secondclient.testCheckClient(secondclient.getTestClientSignUp()));
    }

    @Test
    public void cookSignUpTest1() {
        CookSignUp firstcook = new CookSignUp();
        firstcook.addCookTestValues("jon", "colsan", "jcolsan@gmail.com", "password1234", "42 great road", "i cook good food", true);
        Assert.assertTrue(firstcook.testCheckCook(firstcook.getTestCookSignUp()));
    }

    @Test
    public void cookSignUpTest2() {
        CookSignUp secondcook = new CookSignUp();
        secondcook.addCookTestValues("jon", "colsan", "jcolsan@gmail.com", "password1234", "42 great road", "i cook good food", false);
        Assert.assertFalse(secondcook.testCheckCook(secondcook.getTestCookSignUp()));
    }
}