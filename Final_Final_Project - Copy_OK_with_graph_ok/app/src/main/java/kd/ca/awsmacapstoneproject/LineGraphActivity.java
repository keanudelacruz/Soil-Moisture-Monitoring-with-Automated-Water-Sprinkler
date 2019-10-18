package kd.ca.awsmacapstoneproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LineGraphActivity extends AppCompatActivity {


    DatabaseHelper myDB;
    SQLiteDatabase SQLhelper;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd a");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);

        myDB = new DatabaseHelper(this);
        SQLhelper = myDB.getWritableDatabase();

        GraphView graphView = (GraphView) findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(getDataPoint());
        graphView.addSeries(series);


        series.resetData(getDataPoint());

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(){
            @Override
            public String formatLabel(double value, boolean isValueX) {

                if(isValueX){
                    return sdf.format(new Date((long)value));

                }else
                {
                    return super.formatLabel(value, isValueX);
                }

            }
        });

        graphView.getViewport().setXAxisBoundsManual(true);
        // graphView.getViewport().setMinX(0);
        //graphView.getViewport().setMaxX(100);

        // graphView.getViewport().setBorderColor(Color.GRAY);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0);
        graphView.getViewport().setMaxY(100);

        graphView.getViewport().setScalable(true);
        //graphView.getViewport().setScalable(true);



        series.setColor(Color.RED);
        series.setDrawBackground(true);
        series.setBackgroundColor(Color.rgb(168,223,246));
        series.setThickness(10);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(15);





        graphView.getLegendRenderer().setVisible(true);
        graphView.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graphView.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(Color.BLACK);
        series.setTitle("Soil Humidity");


        graphView.getSecondScale().setMinY(0);
        graphView.getSecondScale().setMaxY(100);

        //graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(LineGraphActivity.this));
        graphView.getGridLabelRenderer().setHumanRounding(false);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(3);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {

                String msg = "Soil Humidity: " + dataPoint.getY();

                Toast.makeText(LineGraphActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private DataPoint[] getDataPoint(){
        String[] columns ={"HUMIDITY","DATE_TIME"};
        Cursor cursor = SQLhelper.query("tbl_record",columns,null,null,null,null,null);

        DataPoint[] dp = new DataPoint[cursor.getCount()];

        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToNext();
            dp[i] = new DataPoint(cursor.getLong(1),cursor.getInt(0));

        }

        return (dp);

    }
    @Override
    public void onBackPressed()
    {
        Intent accountsIntent = new Intent(LineGraphActivity.this,Home.class);
        startActivity(accountsIntent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout );
    }

    }

