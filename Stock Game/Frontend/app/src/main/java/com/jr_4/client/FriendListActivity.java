package com.jr_4.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.jr_4.client.Pojos.Friend;
import com.jr_4.client.app.AppController;
import com.jr_4.client.app.FriendsAdapter;
import com.jr_4.client.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {

    int user_id;
    public static boolean admin;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user_id= getIntent().getExtras().getInt("id");
        admin = getIntent().getExtras().getBoolean("admin");
        String url = Const.URL_SERVER_USER+"/"+user_id+"/friends";
        final ListView friendList = findViewById(R.id.friend_list);

        final String TAG = "json_array_req";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<Friend> friends_list = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                String username = response.getJSONObject(i).getString("username");
                                int friend_id = response.getJSONObject(i).getInt("id");
                                boolean isbanned = response.getJSONObject(i).getBoolean("banned");
                                Friend friend = new Friend(username, friend_id, user_id, isbanned);
                                friends_list.add(friend);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        ListAdapter adapter = new FriendsAdapter(FriendListActivity.this, R.layout.adapter_view_friend, friends_list);
                        friendList.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"Error: " + error
                        + "\nStatus Code " + error.networkResponse.statusCode
                        + "\nResponse Data " + error.networkResponse.data.toString()
                        + "\nCause " + error.getCause()
                        + "\nmessage" + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
