package com.jr_4.client;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jr_4.client.R;

import java.util.Random;

public class PortfolioGraphActivity extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series = new LineGraphSeries<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /**
         * Let me try again to fix the merge conflicts.git
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_graph);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GraphView graphView = findViewById(R.id.portGraph_graph);
        int x;
        double y=100000;
        int fakeData;
        double fakeSmallData;
        boolean fakeIncomeType;
        Random random = new Random();

        for(int i=0; i<20; i++) {
            fakeData = random.nextInt(1000);
            fakeSmallData = random.nextDouble();
            fakeIncomeType = random.nextBoolean();
            x=i;
            if(fakeIncomeType){
                y = y+fakeData+fakeSmallData;
            }
            else{
                y = y-fakeData-fakeSmallData;
            }
            series.appendData(new DataPoint(x,y), true, 20);
        }

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(0);
        graphView.getViewport().setMaxX(20);
        graphView.addSeries(series);


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
