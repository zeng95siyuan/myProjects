package com.jr_4.client;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.jr_4.client.Pojos.FakeResponse;
import com.jr_4.client.Pojos.Portfolio;
import com.jr_4.client.app.AppController;
import com.jr_4.client.app.GachaAdapter;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Random;

public class GachaActivity extends AppCompatActivity {
    private ArrayList<Portfolio> gachaList;
    private Context mContext;
    private ListView mList;
    private TextView gachaQuantity, gachaPrice, gachaResult, gachaSymbol;
    private int totalQuantity, oneOrTen;
    private double totalPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gacha);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = this;
        gachaPrice = findViewById(R.id.gacha_allPrice);
        gachaQuantity = findViewById(R.id.gacha_allQuantity);
        gachaResult = findViewById(R.id.gacha_result);
        gachaSymbol = findViewById(R.id.gacha_symbol);
        oneOrTen = getIntent().getExtras().getInt("oneOrTen");
        totalPrice =0.00;
        totalQuantity=0;

        Random random = new Random();
        int stockId= random.nextInt(8700)+1;
        int numQuantity = random.nextInt(5)+1;

        String url = "http://cs309-jr-4.misc.iastate.edu:8080/users/11/portfolio/draw?stockIdArr="+stockId+"&numArr="+numQuantity;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url,
                null, response -> {
            try{
                String symbol = response.getJSONObject(0).getString("symbol");
                int quantity = response.getJSONObject(0).getInt("quantity");
                double price = response.getJSONObject(0).getDouble("price");
                gachaQuantity.setText(String.valueOf(quantity));
                gachaPrice.setText(String.valueOf(price));
                gachaSymbol.setText(symbol);
                totalPrice+=price * quantity;

                if(totalPrice-100.00>0 && totalPrice-100<20)
                    gachaResult.setText("C");
                else if(totalPrice -100>20 && totalPrice-100 <50)
                    gachaResult.setText("B");

                else if(totalPrice -100>50 && totalPrice-100 <150)
                    gachaResult.setText("A");

                else if(totalPrice -100>150)
                    gachaResult.setText("S");

                else
                    gachaResult.setText("D");

            }catch (JSONException e){
                e.printStackTrace();
            }
        }, error -> {

        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);


    }

    public static boolean checkMainFeature(FakeResponse fakeResponse){
        ArrayList<Portfolio> list = new ArrayList<>();
        list = fakeResponse.getResponse();
        if(list != null){
            return true;
        }
        else
            return false;
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
