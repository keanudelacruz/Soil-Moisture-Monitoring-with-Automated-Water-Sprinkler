package kd.ca.awsmacapstoneproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Listing extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private static final String TAG = "Notification";
    private ListView lvREC;
    Button sort;
    Helper helper;
    String test1="";
    SQLiteDatabase sqLiteDatabase;
    ArrayList<String> recordList = new ArrayList<>();
    ArrayAdapter aAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //class database helper
        helper = new Helper(this);
        sqLiteDatabase=helper.getWritableDatabase();

        //listview record
        lvREC = (ListView) findViewById(R.id.listrecord);
        lvREC.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Listing.this,RecDialog.class);
                intent.putExtra("context", recordList.get(position));
                startActivity(intent);
            }
        });
        aAdapter = new ArrayAdapter<String>(this, R.layout.listing_divider,R .id.txt, recordList);

        //aAdapter = new ArrayAdapter<String>(this, R.layout.list_record, R.id.reclist,R.layout.listing_divider,R.id.txt, recordList);
        lvREC.setAdapter(aAdapter);

        //Sort using Button Datepicker Dialog
        sort= (Button)findViewById(R.id.calendar);
        sort.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog
                (       //Theme_Holo_Dialog_MinWidth
                        Listing.this,
                        android.R.style.Theme_Holo_Light_Dialog,
                        mDateSetListener,
                        year, month, day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }

        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                Log.d(TAG, "onDataSet: mm/dd/yyyy: " + month + "/" + day + "/" + year);
                test1 =String.valueOf(month) + "/" + String.valueOf(day) + "/" + String.valueOf(year);
                String x = helper.selectData(test1);
                if (x.isEmpty()){
                    Toast.makeText(Listing.this,"No record found", Toast.LENGTH_SHORT).show();
                    String yp= "DATE: "+ test1.toString();
                    sort.setText(yp);
                    aAdapter.clear();
                }else{
                    aAdapter.clear();

                    String yp= "DATE: "+ test1.toString();
                    sort.setText(yp);
                    //aAdapter.clear();
                    aAdapter.add(x);
                }

            }
        };
    }

    @Override
    public void onBackPressed()
    {
        Intent accountsIntent = new Intent(Listing.this,Home.class);
        startActivity(accountsIntent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {}

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}

