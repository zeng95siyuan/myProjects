package com.jr_4.client;

import com.jr_4.client.Pojos.FakeResponse;
import com.jr_4.client.Pojos.Portfolio;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

public class gochaTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    FakeResponse fakeResponse;

    @Test
    public void testGachaToPass() throws JSONException{
        Portfolio object = new Portfolio("AA", 1, 23.74, 2);
        ArrayList<Portfolio> mList = new ArrayList<>();
        mList.add(object);
        when(fakeResponse.getResponse()).thenReturn(mList);
        boolean result = GachaActivity.checkMainFeature(fakeResponse);
        Assert.assertTrue(result);
    }
}
