package com.jr_4.client;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jr_4.client.Pojos.searchResult;
import com.jr_4.client.app.AppController;
import com.jr_4.client.app.SearchAdapter;
import com.jr_4.client.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private EditText searchBar;
    private TextView searchMessage;
    private ListView results;
    private Button gacha, gachaX10;
    private ArrayList<searchResult> stockList = new ArrayList<>();
    int userId;
    private boolean guest, admin;
    private Context mContext;
    private ProgressDialog dialog;
    double cash; //Siyuans work requires this to be sent so just makes life easier to get it here although not really correct way to deal with this
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext = this;

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dialog = new ProgressDialog(SearchActivity.this);
        dialog.setTitle("Searching...");
        dialog.setMessage("Loading......");

        userId = getIntent().getExtras().getInt("id");
        searchBar = findViewById(R.id.stockSearchBar);
        searchMessage = findViewById(R.id.search_resultMessage);
        results = findViewById(R.id.stockList);
        gacha = findViewById(R.id.search_gacha);
        guest = getIntent().getExtras().getBoolean("guest");
        admin = getIntent().getExtras().getBoolean("admin");

        if(!guest) {
            StringRequest strRequest = new StringRequest(Request.Method.GET, Const.URL_SERVER_USER + "/" + userId + "/cash",
                    response -> {
                        System.out.println(response);
                        cash = Double.valueOf(response);
                    }, error -> {
                    });
            AppController.getInstance().addToRequestQueue(strRequest);
        }else{
            gacha.setVisibility(View.INVISIBLE);
        }
    }
    public void search (View view) {
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(() ->
                dialog.dismiss(), 3000);
        stockList.clear();
        String index = searchBar.getText().toString();
        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                getString(R.string.url) + "/stock/search?index=" + index, null,
                response -> {
                    int size = response.length();
                    try {
                        for (int i = 0; i < size; i++) {
                            int stockId = response.getJSONObject(i).getInt("id");
                            String symbol = response.getJSONObject(i).getString("symbol");
                            String company = response.getJSONObject(i).getString("company");
                            double price = response.getJSONObject(i).getDouble("price");
                            searchResult comingStock = new searchResult(stockId, symbol, company, price);
                            stockList.add(comingStock);
                            SearchAdapter adapter = new SearchAdapter(mContext, R.layout.search_item, stockList);
                            results.setAdapter(adapter);
                            searchMessage.setText("Search Result: "+size+" results found!" );
                            results.setOnItemClickListener((parent, onClickview, position, id) -> {
                                Intent intent = new Intent(SearchActivity.this, stockInfoActivity.class);
                                intent.putExtra("stock_id", stockList.get(position).getId());
                                intent.putExtra("currentPrice", stockList.get(position).getPrice());
                                intent.putExtra("quantity", 0);
                                intent.putExtra("cash", cash);
                                intent.putExtra("id", userId);
                                intent.putExtra("admin", admin);
                                intent.putExtra("guest", guest);
                                startActivity(intent);
                            });
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                },
                error -> {
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

    public void gacha_tryOnce(View view){
        if(cash <101){
            Toast.makeText(mContext, "You don't have enough cash", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(SearchActivity.this, GachaActivity.class);
            intent.putExtra("oneOrTen", 1);
            startActivity(intent);
        }
    }

    public void gacha_tryFive(View view){
        if(cash <501){
            Toast.makeText(mContext, "You don't have enough cash", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(SearchActivity.this, GachaActivity.class);
            intent.putExtra("oneOrTen", 5);
            startActivity(intent);
        }
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
