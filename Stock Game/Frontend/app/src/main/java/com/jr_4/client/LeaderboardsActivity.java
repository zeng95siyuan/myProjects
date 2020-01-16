package com.jr_4.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.jr_4.client.Pojos.Score;
import com.jr_4.client.app.AppController;
import com.jr_4.client.app.LeaderboardAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

public class LeaderboardsActivity extends AppCompatActivity {

    private ListView leaderboard;
    private TextView title;
    private int userId;
    private ArrayList<Score> scoreList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        leaderboard = findViewById(R.id.leaderboard);
        title = findViewById(R.id.leader_title);
        userId = getIntent().getExtras().getInt("id");

        LocalDate date = java.time.LocalDate.now();
        date = date.minusDays(1);
        String str_date= date.toString();
        String url = "http://cs309-jr-4.misc.iastate.edu:8080/scores/getAllByDate?date=" + str_date;
        System.out.println("leaderboard URL hit: "+url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                int scoreId = response.getJSONObject(i).getInt("id");
                                int userId = response.getJSONObject(i).getInt("userId");
                                int rank = response.getJSONObject(i).getInt("rank");
                                String resDate = response.getJSONObject(i).getString("date");
                                double score = response.getJSONObject(i).getDouble("score");
                                Score singleScore = new Score(scoreId,userId,rank, resDate, score);
                                scoreList.add(singleScore);
                            }
                            LeaderboardAdapter adapter = new LeaderboardAdapter(LeaderboardsActivity.this,
                                    R.layout.leader_item, scoreList);
                            leaderboard.setAdapter(adapter);
                            title.setText("Leaderboard\n"+scoreList.get(0).getDate());

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley Error","Error: " + error
                        + "\nStatus Code " + error.networkResponse.statusCode
                        + "\nResponse Data " + new String(error.networkResponse.data)
                        + "\nCause " + error.getCause()
                        + "\nmessage" + error.getMessage());
            }
        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        System.out.println(scoreList.toString());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
