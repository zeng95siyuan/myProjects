package com.jr_4.client;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.jr_4.client.Pojos.Portfolio;
import com.jr_4.client.app.AppController;
import com.jr_4.client.app.PortfolioAdapter;
import com.jr_4.client.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PortfolioActivity extends AppCompatActivity {
    private int user_id;
    private int stockNum;
    private double net=0;
    private boolean admin;
    private Button button;
    TextView cash_value;
    TextView invest_value;
    TextView net_value;
    ArrayList<Portfolio> portfolioList = new ArrayList<>();
    ListView indi_stocks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user_id = getIntent().getExtras().getInt("id");
        admin = getIntent().getExtras().getBoolean("admin");
        final boolean friend_page = getIntent().getExtras().getBoolean("friend");

        cash_value = findViewById(R.id.port_cash);
        invest_value = findViewById(R.id.port_investValue);
        net_value = findViewById(R.id.port_netValue);
        indi_stocks = findViewById(R.id.listView);
        button= findViewById(R.id.port_toTransactions);
        if(friend_page && !admin){
            button.setVisibility(View.INVISIBLE);
        }

        final Context mContext = this;


        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, "http://cs309-jr-4.misc.iastate.edu:8080/users/" + user_id + "/portfolio", null,
                        response -> {
                            double all_invest=0;
                            ListView mListView = findViewById(R.id.listView);
                            final Portfolio[] portfolios = new Portfolio[response.length()];
                            try {
                                for (int i = 0; i < response.length(); i++) {

                                    int res_quan= Integer.valueOf(response.getJSONObject(i).getString("quantity"));
                                    int res_id = Integer.valueOf(response.getJSONObject(i).getString("id"));
                                    String res_name = response.getJSONObject(i).getString("symbol");
                                    Double res_price = Double.valueOf(response.getJSONObject(i).getString("price"));
                                    portfolios[i]= new Portfolio(res_name, res_quan, res_price, res_id);

                                }
                            }catch(JSONException e) {
                                e.printStackTrace();
                            }

                            stockNum = portfolios.length;

                            for(int i = 0; i < stockNum; i++) {
                                portfolioList.add(portfolios[i]);
                                all_invest+= portfolios[i].getCurrentValue()*portfolios[i].getQuantity();
                            }
                            net += all_invest;
                            invest_value.setText("Investment \n"+all_invest);
                            PortfolioAdapter adapter = new PortfolioAdapter(mContext, R.layout.adapter_view_portfolio, portfolioList);
                            mListView.setAdapter(adapter);

                            try{
                                StringRequest strRequest = new StringRequest(Request.Method.GET, Const.URL_SERVER_USER + "/" + user_id + "/cash",
                                        response1 -> {
                                            cash_value.setText("Cash \n"+ response1);
                                            net +=Double.valueOf(response1);
                                            net_value.setText("Net Value\n"+net);
                                        }, error ->
                                        Toast.makeText(PortfolioActivity.this, error.toString(), Toast.LENGTH_SHORT).show());

                                if( friend_page == false) {
                                    indi_stocks.setOnItemClickListener((parent, view, position, id) -> {
                                        Intent intent = new Intent(PortfolioActivity.this, stockInfoActivity.class);
                                        intent.putExtra("stock_id", portfolios[position].getStockId());
                                        intent.putExtra("currentPrice", portfolios[position].getCurrentValue());
                                        intent.putExtra("quantity", portfolios[position].getQuantity());
                                        intent.putExtra("cash", net);
                                        intent.putExtra("id", user_id);
                                        intent.putExtra("admin", admin);
                                        startActivity(intent);
                                    });
                                }

                                AppController.getInstance().addToRequestQueue(strRequest);
                            } catch (Exception e){
                                e.printStackTrace();
                            }


                        }, error ->
                        Toast.makeText(PortfolioActivity.this, error.toString(), Toast.LENGTH_SHORT).show());
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void toPortfolioGraph(View view){
        Intent intent = new Intent(PortfolioActivity.this, PortfolioGraphActivity.class);
        intent.putExtra("user_id", user_id);
        startActivity(intent);
    }

    public void toTransaction(View view){
        Intent intent = new Intent(PortfolioActivity.this, TransactionActivity.class);
        intent.putExtra("user_id", user_id);
        startActivity (intent);
    }

    public static boolean tryShowPortfolio(int userId){
        boolean result  = false;
        try{
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://cs309-jr-4.misc.iastate.edu:8080/users/" + userId + "/portfolio", null,
                    response -> {

                    }, error -> {

                    });
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
