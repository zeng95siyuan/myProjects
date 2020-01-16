package com.jr_4.client.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.jr_4.client.FriendListActivity;
import com.jr_4.client.Pojos.Friend;
import com.jr_4.client.Pojos.Portfolio;
import com.jr_4.client.Pojos.Score;
import com.jr_4.client.PortfolioActivity;
import com.jr_4.client.ProfileActivity;
import com.jr_4.client.R;
import com.jr_4.client.utils.Const;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LeaderboardAdapter extends ArrayAdapter<Score> {

    private static String TAG ="FriendsAdapter";

    private Context mContext;
    private String button_onhit;
    int mResource;

    /**
     *Default constructor for the FriendsAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public LeaderboardAdapter(Context context, int resource, ArrayList<Score> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource= resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView Tv_rank = convertView.findViewById(R.id.leader_ada_rank);
        TextView Tv_score = convertView.findViewById(R.id.leader_ada_score);
        TextView Tv_username = convertView.findViewById(R.id.leader_ada_username);
        ImageView Iv_image = convertView.findViewById(R.id.leader_ada_image);
        final double score = getItem(position).getScore();
        final int rank = getItem(position).getRank();
        final int userId = getItem(position).getUserId();
        if(rank ==1){
            Tv_rank.setTextColor(mContext.getResources().getColor(R.color.Champion));
            Tv_score.setTextColor(mContext.getResources().getColor(R.color.Champion));
            Tv_username.setTextColor(mContext.getResources().getColor(R.color.Champion));
            Tv_rank.setTypeface(Typeface.DEFAULT_BOLD);
            Tv_score.setTypeface(Typeface.DEFAULT_BOLD);
            Tv_username.setTypeface(Typeface.DEFAULT_BOLD);
            Iv_image.setImageResource(R.drawable.first);
        }else if(rank ==2){
            Tv_rank.setTextColor(mContext.getResources().getColor(R.color.Bronze));
            Tv_score.setTextColor(mContext.getResources().getColor(R.color.Bronze));
            Tv_username.setTextColor(mContext.getResources().getColor(R.color.Bronze));
            Tv_rank.setTypeface(Typeface.DEFAULT_BOLD);
            Tv_score.setTypeface(Typeface.DEFAULT_BOLD);
            Tv_username.setTypeface(Typeface.DEFAULT_BOLD);
            Iv_image.setImageResource(R.drawable.second);
        }else if(rank ==3){
            Tv_rank.setTextColor(mContext.getResources().getColor(R.color.Silver));
            Tv_score.setTextColor(mContext.getResources().getColor(R.color.Silver));
            Tv_username.setTextColor(mContext.getResources().getColor(R.color.Silver));
            Iv_image.setImageResource(R.drawable.third);
        }


        Tv_rank.setText(String.valueOf(rank));
        Tv_score.setText(String.valueOf(score));
        String url = "http://cs309-jr-4.misc.iastate.edu:8080/users/"+userId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String username = response.getString("username");
                        Tv_username.setText(username);
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }, error -> {

        });
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        return convertView;
    }
}
