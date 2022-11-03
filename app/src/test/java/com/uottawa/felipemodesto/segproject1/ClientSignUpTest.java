package com.uottawa.felipemodesto.segproject1;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class ClientSignUpTest extends TestCase {
    @Test
    public void shouldCreateClient1() {
        ClientSignUp client1 = new ClientSignUp();
        client1.testAddClient("ted", "san", "tsan@gmail.com", "password", "19 crescent", "1234 5678 2563 9002");
        Assert.assertFalse(client1.getTestClientSignUp().isEmpty());
        Assert.assertEquals(1, client1.getTestClientSignUp().size());
    }
}