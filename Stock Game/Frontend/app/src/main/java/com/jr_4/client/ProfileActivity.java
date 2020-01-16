package com.jr_4.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.GsonBuilder;
import com.google.gson.*;
import com.jr_4.client.Pojos.User;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {
    TextView welcomeMessage;
    CardView userManagement, portfolioCard, stockSearch, settingCard, friendList, searchFriend, chatRoom, leaderBoard, logoutCard;
    RequestQueue queue;
    private int id;
    private boolean admin, guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        queue = Volley.newRequestQueue(this);
        welcomeMessage = findViewById(R.id.welcomeMessage);
        userManagement = findViewById(R.id.toUserManage);
        portfolioCard = findViewById(R.id.toPortfolio);
        stockSearch = findViewById(R.id.toStockSearch);
        settingCard = findViewById(R.id.editProfile);
        friendList = findViewById(R.id.toFriend);
        searchFriend = findViewById(R.id.searchFriends);
        chatRoom = findViewById(R.id.toGroupChat);

        id = getIntent().getExtras().getInt("id");
        admin = getIntent().getExtras().getBoolean("admin");
        guest = getIntent().getExtras().getBoolean("guest");
        if(admin){
            userManagement.setVisibility(View.VISIBLE);
        }
        if(guest){
            portfolioCard.setVisibility(View.INVISIBLE);
            searchFriend.setVisibility(View.INVISIBLE);
            settingCard.setVisibility(View.INVISIBLE);
            friendList.setVisibility(View.INVISIBLE);
            chatRoom.setVisibility(View.INVISIBLE);
            welcomeMessage.setText("If you find this game is useful, feel free to sign up!");
        }
        else if(!guest) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, getString(R.string.url) + "/users/" + id, null, response -> {
                        Gson gson = new GsonBuilder().create();
                        User user = gson.fromJson(response.toString(), User.class);
                        String message = getString(R.string.welcome_message, user.getUsername(),
                                profileIsComplete(user) ? "" : getString(R.string.welcome_message_incomplete)) + //Give instructions to complete profile if not already complete
                                getString(R.string.welcome_message_alerts, newMessages(),
                                        newMessages() == 1 ? "" : "s"); //make word plural if needed
                        welcomeMessage.setText(message);
                    }, error -> welcomeMessage.setText("ERROR"));
            queue.add(jsonObjectRequest);
        }
    }

    private boolean profileIsComplete(User user){
        return user.getLastName() != null && user.getFirstName() != null && !user.getLastName().isEmpty() && !user.getFirstName().isEmpty();
    }

    private int newMessages(){
        return 0;
    }

    public void goToEditProfile(View view){
        Intent intent = new Intent(this, EditProfileActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void signOut(View view){
        if(!guest) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void toPortfolio(View view){
        Intent intent = new Intent(this, PortfolioActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("friend", false);
        intent.putExtra("admin", admin);
        startActivity(intent);
    }

    public void toFriend(View view){
        Intent intent = new Intent(this, FriendListActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("admin", admin);
        startActivity(intent);
    }

    public void toSearch(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("admin", admin);
        intent.putExtra("guest", guest);
        startActivity(intent);
    }

    public void toUserManage(View view){
        Intent intent = new Intent(this, UserManagementActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    public void toGroupChat(View view){
        Intent intent = new Intent(this, ChatRoomsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);

    }
    public void toFriendSearch(View view){
        Intent i = new Intent(this, FriendSearchActivity.class);
        i.putExtra("id", id);
        i.putExtra("admin", admin);
        startActivity(i);
    }

    public void toLeaderboard(View view){
        Intent i = new Intent(this, LeaderboardsActivity.class);
        i.putExtra("id", id);
        i.putExtra("admin", admin);
        startActivity(i);
    }
    public boolean checkConnection(int id) {
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, "http://cs309-jr-4.misc.iastate.edu:8080/users" + "/users/" + id, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            String str = response.toString();
                        }
                    }, new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            welcomeMessage.setText("ERROR");
                        }
                    });
            queue.add(jsonObjectRequest);
            return true;
        }catch (Error e){
            return false;
        }catch (NullPointerException ne){
            return false;
        }
    }
}