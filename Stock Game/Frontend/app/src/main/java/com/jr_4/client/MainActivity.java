package com.jr_4.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1= findViewById(R.id.goSignup);
        b2= findViewById(R.id.goLogin);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });
    }
    public void eric_test(View v){
            Intent i = new Intent(this, SocketChatServiceActivity.class);
            startActivity(i);
    }

    public void go_guestPage(View v){
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("guest", true);
        intent.putExtra("id", 999);
        intent.putExtra("admin", false);
        startActivity(intent);
    }


}
