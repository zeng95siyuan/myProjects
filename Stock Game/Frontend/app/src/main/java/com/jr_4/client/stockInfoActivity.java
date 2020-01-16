package com.jr_4.client;

import android.content.Context;
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
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;
import com.google.gson.*;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jr_4.client.Pojos.User;
import com.jr_4.client.app.AppController;
import com.jr_4.client.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class stockInfoActivity extends AppCompatActivity {
    private int stock_id, user_id, quantity;
    private boolean admin, guest;
    private double currPrice, cash;
    private TextView symbol, pExchange,cPrice,cRate,compName,changePrice;
    private Button buy, sell;
    private Context mContext;
    private final String TAG = "str_log_req";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stockinfo);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        stock_id = getIntent().getExtras().getInt("stock_id");
        user_id = getIntent().getExtras().getInt("id");
        currPrice=getIntent().getExtras().getDouble("currentPrice");
        cash=getIntent().getExtras().getDouble("cash");
        quantity = getIntent().getExtras().getInt("quantity");
        admin= getIntent().getExtras().getBoolean("admin");
        guest=getIntent().getExtras().getBoolean("guest");

        symbol = findViewById(R.id.stif_name);
        pExchange= findViewById(R.id.stif_pExchange);
        cPrice = findViewById(R.id.stif_currentPrice);
        cRate = findViewById(R.id.stif_changeRate);
        changePrice= findViewById(R.id.stif_changePrice);
        compName = findViewById(R.id.stif_compName);
        final JSONObject[] jsons = new JSONObject[30];

        buy=findViewById(R.id.stif_buy);
        sell=findViewById(R.id.stif_sell);

        if(guest){
            buy.setVisibility(View.INVISIBLE);
            sell.setVisibility(View.INVISIBLE);
        }

        mContext=stockInfoActivity.this;

        String url=Const.URL_SERVER_STOCK+"?stockId="+stock_id;


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null
                , response -> {
                    try{
                        symbol.setText(response.getString("symbol")  );
                        compName.setText("Company Name: "+response.getString("companyName"));
                        pExchange.setText("Primary Exchange: "+response.getString("primaryExchange").toString());
                        cRate.setText("Change Rate: "+String.valueOf(response.getDouble("changePercent")*100)+"%");
                        changePrice.setText("Price Change: "+String.valueOf(response.getDouble("change")));
                        stockInfoActivity.this.currPrice = response.getDouble("latestPrice"); // in case we got here before making this call
                        cPrice.setText("Current Price: "+String.valueOf(currPrice));
                    } catch(JSONException e){
                        e.printStackTrace();
                    }

                }, error ->
                Log.d(TAG,"Error: " + error
                        + "\nStatus Code " + error.networkResponse.statusCode
                        + "\nResponse Data " + error.networkResponse.data.toString()
                        + "\nCause " + error.getCause()
                        + "\nmessage" + error.getMessage()))
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new LinkedHashMap<String, String>();
                header.put("Content-Type", "text/plain;charset=UTF-8");
                return super.getHeaders();
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

        String histDataURL= Const.URL_SERVER_STOCK+"/historicData/1m?stockId="+stock_id;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, histDataURL, null,
                response -> {

                    int size = response.length();
                    try {
                        for (int i = 0; i < Math.min(size,30); i++) {
                            jsons[i] = response.getJSONObject(i);
                        }
                        System.out.println("Json objects got are: "+jsons.toString());
                        GraphView graph = findViewById(R.id.graph);

                        int x=0;
                        double y = 0.00;
                        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

                        for(int i=0; i<Math.min(size,30); i++){
                            x = i;
                            y = jsons[i].getDouble("close");
                            series.appendData(new DataPoint(x,y), true, size);
                        }
                        series.setAnimated(true);
                        graph.getViewport().setXAxisBoundsManual(true);
                        graph.getViewport().setMinX(0);
                        graph.getViewport().setMaxX(size);
                        graph.addSeries(series);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }, error -> {

                });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        buy.setOnClickListener(v -> {
            Intent intent = new Intent(stockInfoActivity.this, buyActivity.class);
            intent.putExtra("price", currPrice);
            intent.putExtra("cash", cash);
            intent.putExtra("stock_id", stock_id);
            intent.putExtra("id", user_id);
            intent.putExtra("quantity", quantity);
            intent.putExtra("admin", admin);
            startActivity(intent);
        });

        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(stockInfoActivity.this, sellActivity.class);
                intent.putExtra("price", currPrice);
                intent.putExtra("cash", cash);
                intent.putExtra("stock_id", stock_id);
                intent.putExtra("id", user_id);
                intent.putExtra("quantity", quantity);
                intent.putExtra("admin", admin);
                startActivity(intent);
            }
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
}
