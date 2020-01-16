package com.jr_4.client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    TextView welcomeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        welcomeMessage = findViewById(R.id.welcomeMessage);
        String message = getString(R.string.welcome_message, "username",
                profileIsComplete() ? "" : getString(R.string.welcome_message_incomplete)) + //Give instructions to complete profile if not already complete
                getString(R.string.welcome_message_alerts, newMessages(),
                        newMessages() == 1 ? "" :"s"); //make word plural if needed
        welcomeMessage.setText(message);
    }

    private boolean profileIsComplete(){
        return false;
    }

    private int newMessages(){
        return 0;
    }

}