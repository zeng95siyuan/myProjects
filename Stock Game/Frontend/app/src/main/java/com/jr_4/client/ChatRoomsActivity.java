package com.jr_4.client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class ChatRoomsActivity extends AppCompatActivity {
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_rooms);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        i = new Intent(this, SocketChatServiceActivity.class);
        i.putExtra("id", getIntent().getExtras().getInt("id"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void toGroup1(View v){
        i.putExtra("group", 1);
        startActivity(i);
    }
    public void toGroup2(View v){
        i.putExtra("group", 2);
        startActivity(i);
    }
    public void toGroup3(View v){
        i.putExtra("group", 3);
        startActivity(i);
    }
    public void toGroup4(View v){
        i.putExtra("group", 4);
        startActivity(i);
    }
}
