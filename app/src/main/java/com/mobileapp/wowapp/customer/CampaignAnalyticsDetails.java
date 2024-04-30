package com.mobileapp.wowapp.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.gson.Gson;
import com.mobileapp.wowapp.BaseActivity;
import com.mobileapp.wowapp.R;
import com.mobileapp.wowapp.customer.utils.Converter;
import com.mobileapp.wowapp.interations.IResultData;
import com.mobileapp.wowapp.network.APIList;
import com.mobileapp.wowapp.network.APIResult;
import com.mobileapp.wowapp.network.APIResultSingle;
import com.mobileapp.wowapp.network.GraphResponse;
import com.mobileapp.wowapp.network.NetworkManager;
import com.mobileapp.wowapp.utils.VerticalTextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class CampaignAnalyticsDetails extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_analytics_details);
        TextView tvTitle=findViewById(R.id.tv_title);
        ImageView imgRight=findViewById(R.id.img_right);
        imgRight.setImageResource(R.drawable.ic_refresh);
        tvTitle.setText("Analytics");
        ImageView imgBack=findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Animatoo.INSTANCE.animateSlideRight(CampaignAnalyticsDetails.this);
            }
        });

        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setupGraph();
            }
        });
        setupGraph();

    }

    public void setupGraph()
    {
        NetworkManager manager=NetworkManager.getInstance(this);
        LineChart lineChart=findViewById(R.id.line_chart);
        VerticalTextView tvLabelType=findViewById(R.id.label_type);
        int position=getIntent().getIntExtra("position",0);
        HashMap<String,Object>map=new HashMap<>();
        Calendar calendar=Calendar.getInstance();
        map.put("to", Converter.getShortDate(calendar.getTimeInMillis()));
        calendar.add(Calendar.MONTH,-2);
        map.put("from", Converter.getShortDate(calendar.getTimeInMillis()));
        showLoading();
        manager.postRequest(APIList.ANALYTICS_CUSTOMER, map, new IResultData() {
            @Override
            public void notifyResult(String response)
            {
                hideLoading();
                GraphResponse result=new Gson().fromJson(response,GraphResponse.class);
                ArrayList<Entry> entries = new ArrayList<>();
                List<GraphResponse.GraphPoints> pointsList=result.getData().getAnalytics();
                List<String>dates=new ArrayList<>();
                String desc="Impressions";
                if(position==0)
                {
                    desc="Kms";
                    tvLabelType.setText(desc);
                    for(int i=0;i<pointsList.size();i++)
                    {
                        GraphResponse.GraphPoints point=pointsList.get(i);
                        entries.add(new Entry(i, point.getKms()));
                        dates.add(point.getDate());
                    }
                }
                else
                {
                    desc="Impressions";
                    for(int i=0;i<pointsList.size();i++)
                    {
                        GraphResponse.GraphPoints point=pointsList.get(i);
                        entries.add(new Entry(i, point.getImpressions()));
                        dates.add(point.getDate());
                    }
                }

                tvLabelType.setText(desc);
                LineDataSet dataSet = new LineDataSet(entries, desc);
                dataSet.setFillColor(Color.parseColor("#86dcaa"));
                dataSet.setHighlightEnabled(false);
                dataSet.setDrawFilled(true);
                dataSet.setValueTextSize(8f);
                dataSet.setValueTextColor(Color.BLACK);
                XAxis xAxis = lineChart.getXAxis();
                xAxis.setDrawGridLines(false);
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setTextSize(12f);
                xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
                lineChart.getAxisLeft().setDrawGridLines(false);
                xAxis.setValueFormatter(new ValueFormatter() {
                    @Override
                    public String getAxisLabel(float value, AxisBase axis)
                    {
                        int index = (int) value;
                        return dates.get(index);
                    }
                });

                // Customize Y-axis
                YAxis yAxisRight = lineChart.getAxisRight();
                yAxisRight.setEnabled(false);

                YAxis yAxisLeft = lineChart.getAxisLeft();
                yAxisLeft.setTextColor(Color.BLACK);
                yAxisLeft.setTextSize(12f);
                // Create a LineData object and add the dataset to it
                LineData lineData = new LineData(dataSet);

                Description description = new Description();
                description.setText("");
                description.setPosition(0,0);
                lineChart.setDescription(description);


                // Set the data and refresh the chart
                lineChart.setData(lineData);

                lineChart.invalidate();

            }
        });
    }

}