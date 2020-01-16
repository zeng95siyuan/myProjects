package com.jr_4.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jr_4.client.app.AppController;
import com.jr_4.client.utils.Const;

import java.util.HashMap;
import java.util.Map;

public class buyActivity extends AppCompatActivity {
    private double cash, price, cost;
    private TextView view_price, view_cash, view_cost, view_quantity;
    private boolean admin;
    private Button view_check, view_buy;
    private int quantity;
    private int user_id, stock_id;
    private String url;
    private final String TAG = "buy_str_req";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user_id=getIntent().getExtras().getInt("id");
        stock_id=getIntent().getExtras().getInt("stock_id");
        admin = getIntent().getExtras().getBoolean("admin");
        cost=0;
        quantity=0;
        url = Const.URL_SERVER_USER+"/"+user_id+"/portfolio/buy";


        view_cash=findViewById(R.id.buy_cash);
        view_price=findViewById(R.id.buy_price);
        view_cost=findViewById(R.id.buy_total);
        view_quantity=findViewById(R.id.buy_quantity);
        view_check= findViewById(R.id.buy_check);
        view_buy= findViewById(R.id.buy_Buy);

        cash=getIntent().getExtras().getDouble("cash");
        price = getIntent().getExtras().getDouble("price");

        view_cash.setText("Cash: $"+cash);
        view_price.setText("Price: $"+price);

        view_check.setOnClickListener(v -> {
            quantity= Integer.valueOf(view_quantity.getText().toString().trim());
            cost= price * quantity;
            view_cost.setText("Total Cost: $"+String.valueOf(cost));
        });

        view_buy.setOnClickListener(v -> {
            if( checkBuy(cost, cash) ){
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        response -> {
                    if(!response.contains("Invalid")){
                        Toast.makeText(buyActivity.this, response, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(buyActivity.this, ProfileActivity.class);
                        intent.putExtra("id", user_id);
                        intent.putExtra("admin", admin);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(buyActivity.this, "Purchase failed.", Toast.LENGTH_LONG).show();
                }, error ->
                        Log.d(TAG,"Error: " + error
                        + "\nStatus Code " + error.networkResponse.statusCode
                        + "\nResponse Data " + error.networkResponse.data.toString()
                        + "\nCause " + error.getCause()
                        + "\nmessage" + error.getMessage())){
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("stockId", String.valueOf(stock_id));
                        params.put("num", String.valueOf(quantity));
                        return params;
                    }
                };
                AppController.getInstance().addToRequestQueue(stringRequest);
            }
            else
                Toast.makeText(buyActivity.this, "You don't have enough cash.", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkBuy(double cost, double cash){
        boolean result = false;
        if(cost<cash)
            result=true;
        return result;
    }
}
