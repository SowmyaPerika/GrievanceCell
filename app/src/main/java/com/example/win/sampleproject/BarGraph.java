package com.example.win.sampleproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class BarGraph extends AppCompatActivity {
HorizontalBarChart chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_graph);

        chart=(HorizontalBarChart)findViewById(R.id.bargraph);
        setData(4,50);
    }
    private void setData(int count,int range){
        ArrayList<BarEntry> entry=new ArrayList<>();
        float barWidth=9f;
        float spaceforbar=10f;
for (int i=0;i<count;i++) {
    float value =(float)(Math.random()*range);
    entry.add(new BarEntry( i*spaceforbar,value));
}
        BarDataSet set1;
set1=new BarDataSet(entry,"Student Grievances");
        BarData data=new BarData(set1);
        data.setBarWidth(barWidth);
        chart.setData(data);

    }
}
