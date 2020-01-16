package com.jr_4.client;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jr_4.client.app.AppController;
import com.jr_4.client.utils.Const;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button signIn;
    private String url;
    private String TAG = "json_log_req";
    private String str_username, str_pass;
    private int mStatusCode = 0;
    private int count = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = findViewById(R.id.username_login);
        password = findViewById(R.id.password_login);
        signIn = findViewById(R.id.log_in);


        signIn.setOnClickListener(v -> {
            str_username = username.getText().toString().trim();
            str_pass = password.getText().toString().trim();
            url = Const.URL_SERVER_LOGIN + "?username=" + str_username + "&password=" + str_pass;
            Log.d("Hit URL: ", url);
            try {
                signIn(v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void signIn(View view) {
        //Use Siyuan's service
        confirmLogin();
    }

    public void confirmLogin() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                response -> {
                    try {
                        int id = response.getInt("id");
                        boolean admin = response.getBoolean("admin");
                        Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(LoginActivity.this, ProfileActivity.class);
                        i.putExtra("id", id);
                        i.putExtra("admin", admin);
                        i.putExtra("guest", false);
                        startActivity(i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    Log.d(TAG,"Error: " + error
                    + "\nStatus Code " + error.networkResponse.statusCode
                    + "\nResponse Data " + new String(error.networkResponse.data)
                    + "\nCause " + error.getCause()
                    + "\nmessage" + error.getMessage());
        }
//                Toast.makeText(LoginActivity.this, new String(error.networkResponse.data), Toast.LENGTH_SHORT).show()
        );
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, TAG);

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


