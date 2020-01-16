package com.jr_4.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import org.json.JSONObject;

public class SocketChatServiceActivity extends AppCompatActivity {

    Button b2;
    EditText e2;
    TextView t1;
    private WebSocketClient cc;
    int group;
    RequestQueue requestQueue;
    String text = "";
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_chat_service);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        group = getIntent().getExtras().getInt("group");
        b2 = findViewById(R.id.bt2);
        e2 = findViewById(R.id.et2);
        t1 = findViewById(R.id.tx1);
        int id = getIntent().getExtras().getInt("id");
        requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "http://cs309-jr-4.misc.iastate.edu:8080/users/" + id, null,
                        response -> {
                            try {
                                username = response.getString("username");
                                connect();
                            } catch(Exception e){
                                System.out.println("Get User: "+e.toString());
                            }
                        }, error ->
                        Toast.makeText(SocketChatServiceActivity.this, error.toString(), Toast.LENGTH_SHORT).show());
        requestQueue.add(jsonObjectRequest);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cc.send(e2.getText().toString());
                    e2.setText("");
                } catch (Exception e) {
                }
            }
        });
    }
    public void connect(){

        Draft[] drafts = {new Draft_6455()};
        String w = getString(R.string.url) + "/livechat/" + group + "/" + username;
        try {
            cc = new WebSocketClient(new URI(w), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    String s = t1.getText().toString();
                    text = s + message + "\n";
                    t1.post(new Runnable(){
                        @Override
                        public void run() {
                            t1.setText(username+text);
                        }
                    });
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    System.out.println("Connected");
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println("Close Reason: "+reason);
                }

                @Override
                public void onError(Exception e) {
                    System.out.println(e.toString());
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        cc.connect();
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