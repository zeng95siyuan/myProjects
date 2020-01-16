package com.jr_4.client;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jr_4.client.app.AppController;
import com.jr_4.client.app.FriendSearchAdapter;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class FriendSearchActivity extends AppCompatActivity {

    EditText searchBar;
    RecyclerView results;
    RequestQueue requestQueue;
    Button submit;
    int userId;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_search);
        mContext = this;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            userId = getIntent().getExtras().getInt("id");
        }
        catch(Exception e){};
        searchBar = findViewById(R.id.friendSearchBar);
        results = findViewById(R.id.results);
    }

    public void search (View view) {
        final String index = searchBar.getText().toString();
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                getString(R.string.url) + "/users/friendsSearch?index=" + index, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        FriendSearchAdapter adapter = new FriendSearchAdapter(response, userId);
                        results.setAdapter(adapter);
                        results.addItemDecoration(new DividerItemDecoration(FriendSearchActivity.this,
                                DividerItemDecoration.VERTICAL));
                        results.setLayoutManager(new LinearLayoutManager(FriendSearchActivity.this));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                })
        {

            public Map<String, String> getHeaders() {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json;charset=UTF-8");
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static Context getmContext(){
        return mContext;
    }
}
