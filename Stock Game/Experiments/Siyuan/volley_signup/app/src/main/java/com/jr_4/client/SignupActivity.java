package com.jr_4.client;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.service.autofill.RegexValidator;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.jr_4.client.R;
import com.jr_4.client.app.AppController;
import com.jr_4.client.utils.Const;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.Manifest.permission.READ_CONTACTS;

public class SignupActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText confirmPassword;
    Button signUp;
    private String url = Const.URL_SERVER_SIGNUP;
    private String tag_json_obj ="json_obj_req";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username = findViewById(R.id.username_reg);
        password = findViewById(R.id.password_reg);
        signUp = findViewById(R.id.register);

        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String str_username = username.getText().toString().trim();
                String str_pass = password.getText().toString().trim();
                try{
                    passuser(str_username, str_pass);
                } catch (Exception e) {
                    Log.e("VolleyError", e.getMessage());
                }

            }
        });
    }

    public void passuser(final String post_username, final String post_password) throws JSONException {

        JSONObject postData = new JSONObject();
        postData.put("username", post_username);
        postData.put("password", post_password);
        postData.put("firstName", "zsy");
        postData.put("lastName", "001");

        final String body_content = postData.toString();

        /*final String postData = "{ username" +" : "+post_username+", "+"password"+" : "+post_password
                +", firstName : 001, lastname : aaa";
*/
        StringRequest jsb_req = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Data sent", Toast.LENGTH_SHORT).show();

                        System.out.println(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error+"no response", Toast.LENGTH_SHORT).show();
                System.out.println("Error");
                System.out.println(error);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return body_content == null ? null : body_content.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", body_content, "utf-8");
                    return null;
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d(tag_json_obj,"Does it assign headers?") ;

                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json; charset=utf-8");

                return headers;
            }

        };
        AppController.getInstance().addToRequestQueue(jsb_req, tag_json_obj);
    }


    public void confirmRegistration(){
        String errorText = null;
        if(!username.getText().toString().matches("[a-zA-Z0-9 ]*")){
            errorText = "Usernames cannot contain special characters!";
        }
        else if(password.getText().length() < 6){
            errorText = "Passwords must be 6 characters!";
        }
        else if(!password.getText().toString().equals(confirmPassword.getText().toString())){
            errorText = "Passwords don't match!";
        }
        if(errorText != null){
            Toast toast = Toast.makeText(this, errorText, Toast.LENGTH_LONG);
            toast.getView().getBackground().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
            toast.show();
        }
    }
}
