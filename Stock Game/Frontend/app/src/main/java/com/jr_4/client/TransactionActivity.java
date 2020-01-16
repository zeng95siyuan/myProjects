package com.jr_4.client;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.jr_4.client.Pojos.Transaction;
import com.jr_4.client.app.AppController;
import com.jr_4.client.app.TransactionAdapter;
import com.jr_4.client.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TransactionActivity extends AppCompatActivity {
    RecyclerView transactions;
    int userId;
    String url = Const.URL_SERVER_USER;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userId = getIntent().getExtras().getInt("user_id");
        url += "/"+userId+"/transaction";
        final String TAG = "JsonArrayRequest";
        final Context mContext = this;
        final ListView mListView = (ListView)findViewById(R.id.trans_listView);
        final ArrayList<Transaction> transList = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Transaction[] transactions = new Transaction[response.length()];
                try{
                    for(int i=0; i<response.length(); i++){
                        int id = response.getJSONObject(i).getInt("id");
                        int userId = response.getJSONObject(i).getInt("userId");
                        int quantity = response.getJSONObject(i).getInt("quantity");
                        String date = response.getJSONObject(i).getString("date");
                        String symbol= response.getJSONObject(i).getString("ticker");
                        double price= response.getJSONObject(i).getDouble("price");
                        boolean buy = response.getJSONObject(i).getBoolean("buy");
                        transactions[i] = new Transaction(id, userId, quantity, symbol, date, buy, price);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                for(int i=0; i< transactions.length; i++){
                    transList.add(transactions[i]);
                }
                TransactionAdapter adapter = new TransactionAdapter(mContext, R.layout.adapter_view_transaction, transList);
                mListView.setAdapter(adapter);

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
