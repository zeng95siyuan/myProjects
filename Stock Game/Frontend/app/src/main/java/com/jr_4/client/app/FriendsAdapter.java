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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jr_4.client.FriendListActivity;
import com.jr_4.client.Pojos.Friend;
import com.jr_4.client.Pojos.Portfolio;
import com.jr_4.client.PortfolioActivity;
import com.jr_4.client.ProfileActivity;
import com.jr_4.client.R;
import com.jr_4.client.utils.Const;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FriendsAdapter extends ArrayAdapter<Friend> {

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
    public FriendsAdapter(Context context, int resource, ArrayList<Friend> objects) {
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

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);
        TextView Tv_username = convertView.findViewById(R.id.adapter_friend_name);
        Button remove = convertView.findViewById(R.id.adapter_friend_remove);
        ImageView avatar = convertView.findViewById(R.id.adapter_friend_avatar);
        final String url = Const.URL_SERVER_USER+"/removeFriend";

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.contains("Invalid")){
                                    System.out.println("Invalid friend ID");
                                }
                                else{
                                    System.out.println("Friend removed");
                                    Intent intent = new Intent(mContext, ProfileActivity.class);
                                    intent.putExtra("id", userId);
                                    intent.putExtra("admin", FriendListActivity.admin);
                                    mContext.startActivity(intent);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"Error: " + error
                                + "\nStatus Code " + error.networkResponse.statusCode
                                + "\nResponse Data " + new String(error.networkResponse.data)
                                + "\nCause " + error.getCause()
                                + "\nmessage" + error.getMessage());
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("userId", String.valueOf(userId));
                        params.put("friendId", String.valueOf(friendId));
                        return params;
                    }
                };
                AppController.getInstance().addToRequestQueue(stringRequest);
            }
        });

        int i = friendId%10;
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

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PortfolioActivity.class);
                intent.putExtra("id", friendId);
                intent.putExtra("friend", true);
                mContext.startActivity(intent);
            }
        });

        Tv_username.setText(friendname);

        return convertView;
    }
}
