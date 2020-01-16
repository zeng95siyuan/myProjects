package com.jr_4.client;

import android.app.DownloadManager;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jr_4.client.Pojos.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfileActivity extends AppCompatActivity {
    EditText firstName;
    EditText password;
    EditText lastName;
    EditText confirmPassword;
    Button confirm;
    RequestQueue queue;
    Gson gson;
    int id;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        confirm = findViewById(R.id.confirm);
        queue = Volley.newRequestQueue(this);
        gson = new GsonBuilder().create();
        id = getIntent().getExtras().getInt("id");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, getString(R.string.url) + "/users/" + id, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        User user = gson.fromJson(response.toString(), User.class);
                        if(user.getLastName() != null && !user.getFirstName().isEmpty())
                        firstName.setText(user.getFirstName());
                        if(user.getLastName() != null && !user.getFirstName().isEmpty())
                        lastName.setText(user.getLastName());
                        username = user.getUsername();
                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                    }
                });
        queue.add(jsonObjectRequest);
    }

    public void confirm(View view) throws JSONException {
        if(password.getText().toString().equals(confirmPassword.getText().toString())) {
            User user = new User(id, username, password.getText().toString().length() > 0 ? password.getText().toString() : null, firstName.getText().toString(), lastName.getText().toString());
            System.out.println(gson.toJson(user));
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.POST, getString(R.string.url) + "/users/" + id + "/editUser", new JSONObject(gson.toJson(user)), new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                        }
                    }, new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
            queue.add(jsonObjectRequest);
            Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
            intent.putExtra("id", id);
            startActivity (intent);
        }
        else {
            Toast toast = Toast.makeText(this, "passwords don't match", Toast.LENGTH_LONG);
            toast.getView().getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
            toast.show();
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
