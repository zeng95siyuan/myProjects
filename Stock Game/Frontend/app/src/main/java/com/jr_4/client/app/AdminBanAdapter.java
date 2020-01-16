package com.jr_4.client.app;

import android.content.Context;
import android.content.Intent;
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


import com.android.volley.Request;

import com.android.volley.toolbox.StringRequest;
import com.jr_4.client.Pojos.Friend;
import com.jr_4.client.PortfolioActivity;
import com.jr_4.client.ProfileActivity;
import com.jr_4.client.R;
import com.jr_4.client.utils.Const;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AdminBanAdapter extends ArrayAdapter<Friend> {
    private static String TAG ="AdminBanAdapter";

    private Context mContext;
    private String button_onhit;
    int mResource;

    /**
     *Default constructor for the FriendsAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public AdminBanAdapter(Context context, int resource, ArrayList<Friend> objects) {
        super(context, resource, objects);
        this.mContext = context;
        mResource= resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String friendname = getItem(position).getFriendname();
        final int friendId = getItem(position).getFriendId();
        final int userId = getItem(position).getUserId();
        final boolean user_banned = getItem(position).isBanned();
        if(user_banned){
            button_onhit = "UNBAN";
        }else{
            button_onhit = "BAN";
        }

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView Tv_username = convertView.findViewById(R.id.adapter_admin_name);
        Button remove = convertView.findViewById(R.id.adapter_admin_ban);
        ImageView avatar = convertView.findViewById(R.id.adapter_admin_avatar);

        remove.setText(button_onhit);
        if(button_onhit== "BAN") {
            final String url = Const.URL_SERVER_USER + "/" + userId + "/banUser";
            remove.setOnClickListener(v -> {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        response -> {
                            if (response.contains("Invalid")) {
                                Toast.makeText(mContext, "Invalid ID", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "User: "+friendname+" banned", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, ProfileActivity.class);
                                intent.putExtra("id", userId);
                                intent.putExtra("admin", true);
                                mContext.startActivity(intent);
                            }
                        }, error -> Log.d(TAG, "Error: " + error
                                + "\nStatus Code " + error.networkResponse.statusCode
                                + "\nResponse Data " + new String(error.networkResponse.data)
                                + "\nCause " + error.getCause()
                                + "\nmessage" + error.getMessage())) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("userId", String.valueOf(friendId));
                        return params;
                    }
                };
                AppController.getInstance().addToRequestQueue(stringRequest);
            });
        }
        else if(button_onhit == "UNBAN"){
            final String url = Const.URL_SERVER_USER + "/" + userId + "/unbanUser";
            remove.setOnClickListener(v ->
            {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        response -> {
                            if (response.contains("Invalid")) {
                                Toast.makeText(mContext, "Invalid ID", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "User: "+friendname+" unbanned", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(mContext, ProfileActivity.class);
                                intent.putExtra("id", userId);
                                intent.putExtra("admin", true);
                                mContext.startActivity(intent);
                            }
                        }, error ->
                        Log.d(TAG, "Error: " + error
                                + "\nStatus Code " + error.networkResponse.statusCode
                                + "\nResponse Data " + new String(error.networkResponse.data)
                                + "\nCause " + error.getCause()
                                + "\nmessage" + error.getMessage())) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("userId", String.valueOf(friendId));
                        return params;
                    }

                };
                AppController.getInstance().addToRequestQueue(stringRequest);
            });
        }

        int i = friendId %10;
        if(i ==0){
            avatar.setImageResource(R.drawable.avata1);
        }
        else if(i ==1){
            avatar.setImageResource(R.drawable.avata2);
        }
        else if(i ==2){
            avatar.setImageResource(R.drawable.avata3);
        }
        else if(i ==3){
            avatar.setImageResource(R.drawable.avata4);
        }
        else if(i ==4){
            avatar.setImageResource(R.drawable.avata5);
        }
        else if(i ==5){
            avatar.setImageResource(R.drawable.avata6);
        }
        else if(i ==6){
            avatar.setImageResource(R.drawable.avata7);
        }
        else if(i ==7){
            avatar.setImageResource(R.drawable.avata8);
        }
        else if(i ==8){
            avatar.setImageResource(R.drawable.avata9);
        }
        else if(i ==9){
            avatar.setImageResource(R.drawable.avata10);
        }

        if(avatar != null) {
            avatar.setOnClickListener(v ->
            {
                Intent intent = new Intent(mContext, PortfolioActivity.class);
                intent.putExtra("id", friendId);
                intent.putExtra("friend", true);
                intent.putExtra("admin", true);
                mContext.startActivity(intent);
            });
        }

        if(Tv_username != null)
            Tv_username.setText(friendname);

        return convertView;
    }
}
