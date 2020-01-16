package com.example.eric.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.softmoore.android.graphlib.*;

import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {
    private ArrayList<Point> points = new ArrayList<>();
    private Graph graph;
    private Graph.Builder builder = new Graph.Builder();
    private GraphView graphView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        graphView = findViewById(R.id.graph_view);
        double xMax = 5;
        double yMax = 10;
        graph = builder
                .setWorldCoordinates(-(xMax +1)/10,xMax*1.1,-(yMax +1)/10, yMax)
                .setXTicks(new double[] {xMax/5, 2*xMax/5, 3*xMax/5, 4*xMax/5, xMax})
                .setYTicks(new double[] {yMax/5, 2*yMax/5, 3*yMax/5, 4*yMax/5, yMax})
                .build();
        graphView.setGraph(graph);
        setTitle("Graph Stuff");
    }

    public void addPoint(View view){
        EditText editText = findViewById(R.id.point);
        int num = Integer.parseInt(editText.getText().toString());
        double xMax = points.size() < 5 ? 5 : points.size();
        double yMax = graph.getYMax() < num + 2 ? num + 2: graph.getYMax();
        points.add(new Point(points.size(), num));
        graph = builder.addLineGraph(points, Color.RED)
                .setWorldCoordinates(-(xMax +1)/10,xMax*1.1,-(yMax +1)/10, yMax)
                .setXTicks(new double[] {xMax/5, 2*xMax/5, 3*xMax/5, 4*xMax/5, xMax})
                .setYTicks(new double[] {yMax/5, 2*yMax/5, 3*yMax/5, 4*yMax/5, yMax})
                .build();
        graphView.setGraph(graph);

    }
}
