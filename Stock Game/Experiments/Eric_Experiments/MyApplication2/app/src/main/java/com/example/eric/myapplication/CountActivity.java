package com.example.eric.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class CountActivity extends AppCompatActivity {
    private int number = 0;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        textView = findViewById(R.id.textView2);
        textView.setText(number + "");

        final Button button = findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                countUp();
            }
        });
        final Button button2 = findViewById(R.id.button4);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                countDown();
            }
        });
    }

    public void returnToMain(View view){
        Intent intent = new Intent();
        intent.putExtra("message", "Your number was: " + number);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void countUp(){
        number++;
        textView.setText(number + "");
    }

    public void countDown(){
        number--;
        textView.setText(number + "");
    }
}