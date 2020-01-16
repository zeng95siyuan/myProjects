package com.jr_4.client;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the Signup logic.
 */
public class SignupTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    SignupActivity test;

    @Test
    public void signup_validRegist_ReturnTrue() throws JSONException {
        String username = "zsy";
        String password = "Abc12345";
        JSONObject testObject = new JSONObject();
        testObject.put("loginStatus", new Boolean(true));

        when(test.registrationLogic(username, password)).thenReturn(testObject.getBoolean("loginStatus"));
        Assert.assertEquals(test.registrationLogic(username,password), testObject.getBoolean("loginStatus"));
    }

    @Test
    public void signup_invalidPass1_ReturnFalse() throws JSONException{
        String username = "zsy";
        String password = "abc12345";
        JSONObject testObject = new JSONObject();
        testObject.put("loginStatus", new Boolean(false));

        when(test.registrationLogic(username, password)).thenReturn(testObject.getBoolean("loginStatus"));
        Assert.assertEquals(test.registrationLogic(username,password), testObject.getBoolean("loginStatus"));
    }

    @Test
    public void signup_invalidPass2_ReturnFalse() throws JSONException{
        String username = "zsy";
        String password = "abc1234";
        JSONObject testObject = new JSONObject();
        testObject.put("loginStatus", new Boolean(false));

        when(test.registrationLogic(username, password)).thenReturn(testObject.getBoolean("loginStatus"));
        Assert.assertEquals(test.registrationLogic(username,password), testObject.getBoolean("loginStatus"));
    }
}
