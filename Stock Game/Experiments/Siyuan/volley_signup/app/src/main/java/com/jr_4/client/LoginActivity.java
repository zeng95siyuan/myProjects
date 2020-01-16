package com.jr_4.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.jr_4.client.app.AppController;
import com.jr_4.client.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    Button signIn;
    private String url = Const.URL_SERVER_LOGIN;
    private String tag_str_log ="str_log_req";
    private boolean log_perm = false;
    private String str_username, str_pass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username_login);
        password = findViewById(R.id.password_login);
        signIn = findViewById(R.id.log_in);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_username = username.getText().toString().trim();
                str_pass = password.getText().toString().trim();
                System.out.println("Username Entered: "+str_username);
                System.out.println("Password Entered: "+str_pass);
                try{
                    signIn(v);
                } catch (Exception e) {
                    Log.e("VolleyError", e.getMessage());
                }
            }
        });
    }

    public void signIn(View view) {
        //Use Siyuan's service
        if (confirmLogin() ) {
            Intent intent = new Intent(this, ProfileActivity.class);
            //add user info
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Login error.", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean confirmLogin() {

        /*       JSONObject postData = new JSONObject();
        try{
            postData.put("username", str_username);
            postData.put("password", str_pass);
        } catch(JSONException e){
            Log.e("VolleyError", e.getMessage());
        }*/

        StringRequest login_req = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(getApplicationContext(), "Login request sent", Toast.LENGTH_SHORT).show();
                            System.out.println(response);
                            log_perm = true;
                        } catch(Exception e) {
                            Log.e("VolleyError", e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println("Error");
                System.out.println(error);
                System.out.println(error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new LinkedHashMap<String, String>();
                header.put("Content-Type", "application/x-www-form-urlencoded");
                return super.getHeaders();
            }
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", str_username);
                params.put("password", str_pass);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(login_req, tag_str_log);


        return log_perm;
    }
}


