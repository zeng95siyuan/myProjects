package com.example.eric.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra("message", message);
        startActivity(intent);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            String message = data.getExtras().getString("message");
            TextView textView = findViewById(R.id.number);
                textView.setText(message);
        }
    }
    public void openCounter(View view){
        startActivityForResult(new Intent(this, CountActivity.class),1);
    }

    public void openGraph(View view){
        startActivity(new Intent(this, GraphActivity.class));
    }

}
