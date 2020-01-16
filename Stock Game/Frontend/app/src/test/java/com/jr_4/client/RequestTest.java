package com.jr_4.client;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.mockito.Mockito.when;

public class RequestTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    public ProfileActivity test;

    @Test
    public void ProfileTestWithExistID() throws JSONException, IOException{
        int id =2;

        JSONObject expectResult = new JSONObject();
        expectResult.put("ConnectStatus", true);
        when(test.checkConnection(id)).thenReturn(expectResult.getBoolean("ConnectStatus"));
        Assert.assertTrue(test.checkConnection(id));

    }

    @Test
    public void ProfileTestWithNoneExistID() throws JSONException, IOException{
        int id =99;


        JSONObject expectResult = new JSONObject();
        expectResult.put("ConnectStatus", false);
        when(test.checkConnection(id)).thenReturn(expectResult.getBoolean("ConnectStatus"));
        Assert.assertEquals(test.checkConnection(id), expectResult.getBoolean("ConnectStatus"));

    }

    @Test
    public void simpleRequest() throws IOException, InterruptedException{
        MockWebServer server = new MockWebServer();
        server.enqueue(new MockResponse().setBody("Hey, you made your first mockito test!"));
        server.start();

        HttpUrl baseURL= server.url("/mockito/hello");
        String bodyOfResponse = sendGetRequest(new OkHttpClient(), baseURL);
        Assert.assertEquals("Hey, you made your first mockito test!", bodyOfResponse);
    }

    private String sendGetRequest(OkHttpClient okHttpClient, HttpUrl base) throws IOException{
        RequestBody post = RequestBody.create(MediaType.parse("text/plain"), "this is my request to you, my server");

        okhttp3.Request request = new okhttp3.Request.Builder()
                .post(post)
                .url(base)
                .build();
        Response response = okHttpClient.newCall(request).execute();
        return response.body().string();
    }
}
