package com.jr_4.client.app;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.jr_4.client.FriendListActivity;
import com.jr_4.client.FriendSearchActivity;
import com.jr_4.client.PortfolioActivity;
import com.jr_4.client.R;
import com.jr_4.client.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendSearchAdapter extends RecyclerView.Adapter<FriendSearchAdapter.MyViewHolder> {

    private int userId;
    private List<JSONObject> results;
    public FriendSearchAdapter(JSONArray results, int id) {

        userId = id;
        ArrayList<JSONObject> list = new ArrayList<JSONObject>();
        if (results != null) {
            for (int i=0; i < results.length(); i++){
                try {
                    list.add((JSONObject)results.get(i));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println(list.size());
        this.results = list;
    }

    @NonNull
    @Override
    public FriendSearchAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.friend_search_item, viewGroup, false);

        // Return a new holder instance
        FriendSearchAdapter.MyViewHolder viewHolder = new FriendSearchAdapter.MyViewHolder(contactView, userId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendSearchAdapter.MyViewHolder myViewHolder, int i) {
        JSONObject result = results.get(i);
        TextView username = myViewHolder.username;
        Button add = myViewHolder.add;
        try {
            username.setText(result.getString("username"));
            int user_id = userId;
            int friend_id = result.getInt("id");
            String url = Const.URL_SERVER_USER+"/addFriend?userId="+user_id+"&friendId="+friend_id;

            add.setOnClickListener(v -> {
                StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url,
                        response -> {
                            Toast.makeText(FriendSearchActivity.getmContext(),response, Toast.LENGTH_SHORT).show();
                        }, error -> {
                    Log.d("Friend search", error.toString());
                        }
                        );
//                {
//                    @Override
//                    protected Map<String, String> getParams() {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("userId", String.valueOf(user_id));
//                        params.put("friendId", String.valueOf(friend_id));
//                        return params;
//                    }
//                    @Override
//                    public Map<String, String> getHeaders() throws AuthFailureError {
//                        Log.d("Header","Does it assign headers? Yes") ;
//
//                        HashMap<String, String> headers = new HashMap<String, String>();
//                        headers.put("Content-Type", "application/json; charset=utf-8");
//
//                        return headers;
//                    }
//                    @Override
//                    public String getBodyContentType() {
//                        return "application/json; charset=utf-8";
//                    }
//
//                    @Override
//                    public byte[] getBody(){
//                        try {
//                            return body_content == null ? null : body_content.getBytes("utf-8");
//                        } catch (UnsupportedEncodingException uee) {
//                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", body_content, "utf-8");
//                            return null;
//                        }
//                    }
//
//                };
                AppController.getInstance().addToRequestQueue(jsonObjectRequest);
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView username;
        public Button add;
        public int id;
        public MyViewHolder(final View itemView, final int userId) {
            super(itemView);
            username = itemView.findViewById(R.id.friendSearchUsername);
            add = itemView.findViewById(R.id.friendSearchAdd);
            itemView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view) {
                    Intent intent = new Intent(itemView.getContext(), PortfolioActivity.class);
                    intent.putExtra("id", userId);
                    intent.putExtra("friend", true);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }
}
