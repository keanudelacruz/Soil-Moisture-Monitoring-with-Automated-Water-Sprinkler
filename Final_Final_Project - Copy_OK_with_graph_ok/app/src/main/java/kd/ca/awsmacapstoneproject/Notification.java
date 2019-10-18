package kd.ca.awsmacapstoneproject;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class Notification extends AppCompatActivity
{

    private static final String TAG = "Notification";
    public static final int REQUEST_CODE_PERMISSION_READ_SMS = 456;
    public  static Notification Instance(){
        return instance;
    }
    public static Notification instance;
    private ListView lvSMS;
    ArrayList<String> smsMsgList = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    ActionBar actionBar;
    SearchView sv;
    Helper helper;
    SQLiteDatabase sqLiteDatabase;

    SQLiteDatabase SQLhelper;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        instance = this;
        actionBar = getSupportActionBar();
        actionBar.hide();

        //Database helper
        helper = new Helper(this);
        sqLiteDatabase=helper.getWritableDatabase();
        myDB = new DatabaseHelper(this);
        SQLhelper = myDB.getWritableDatabase();
        //Recognize listview
        lvSMS = (ListView) findViewById(R.id.lV_msg);
        lvSMS.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Notification.this,ListDialog.class);
                intent.putExtra("detail", smsMsgList.get(position));
                startActivity(intent);
            }
        });

        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_message, R.id.Inbox, smsMsgList);
        lvSMS.setAdapter(arrayAdapter);

        //SMS permission
        if (checkPermission(Manifest.permission.READ_SMS))
            {
                refreshInbox();
            //refreshInbox(lvSMS);
            }
        else
            {
            ActivityCompat.requestPermissions(Notification.this, new String[]
                    {
                    (Manifest.permission.READ_SMS)
                    }, REQUEST_CODE_PERMISSION_READ_SMS);
            }

        //Recognize Searchview
        sv = (SearchView)findViewById(R.id.etsearch) ;
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String text)
            {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String text)
            {
                arrayAdapter.getFilter().filter(text);
                return false;
            }
        });

    }

    //String Permmission
    private boolean checkPermission(String permission)
    {
        int checkPermission = ContextCompat.checkSelfPermission(this,permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }

    //On Exit Fade-Out Transition
    @Override
    protected void onPause()
    {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout );
    }

    //Update List
    public void updateList (final String msg1, final String smsMsg)
    {
        long xValue =new Date().getTime();
        SimpleDateFormat simpleDateFormat0 = new SimpleDateFormat("MM");
        int date = Integer.parseInt(simpleDateFormat0.format(new Date(xValue)));

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd");
        int date1 = Integer.parseInt(simpleDateFormat1.format(new Date(xValue)));

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy");
        String date2 = simpleDateFormat2.format(new Date(xValue));


        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("hh:mm:ss a");
        String date3 = simpleDateFormat3.format(new Date(xValue));

        String mydate="";
        mydate= date + "/" + date1 + "/" + date2 + " " + date3;

        arrayAdapter.insert(smsMsg, 0);
        arrayAdapter.notifyDataSetChanged();
        myDB.insertData(msg1,xValue);

    }
    public void updateList1 (final String smsMsg, final String msg1)
    {
        long xValue =new Date().getTime();
        SimpleDateFormat simpleDateFormat0 = new SimpleDateFormat("MM");
        int date = Integer.parseInt(simpleDateFormat0.format(new Date(xValue)));

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd");
        int date1 = Integer.parseInt(simpleDateFormat1.format(new Date(xValue)));

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy");
        String date2 = simpleDateFormat2.format(new Date(xValue));


        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("hh:mm:ss a");
        String date3 = simpleDateFormat3.format(new Date(xValue));

        String mydate="";
        mydate= date + "/" + date1 + "/" + date2 + " " + date3;;

        arrayAdapter.insert(smsMsg, 0);
        arrayAdapter.notifyDataSetChanged();
        helper.insertData(mydate, msg1);
        //Toast.makeText(MainActivity.this, "Data saved", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Notification.this, Home.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }

    public void refreshInbox(){
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"),
                null, null, null,null);

        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        int indexDate = smsInboxCursor.getColumnIndex("date");

        if(indexBody < 0  || !smsInboxCursor.moveToFirst()) return;
        //String number1 = "+639958406050";
        String number1 = "+639757750108";
        arrayAdapter.clear();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, MMM-d, yyyy, hh:mm:a");


        do{
            String address = smsInboxCursor.getString(indexAddress);
            String body = smsInboxCursor.getString(indexBody);
            String date = simpleDateFormat.format(new Date(smsInboxCursor.getLong(indexDate)));
            long xValue = new Date().getTime();
            if (number1.equals(address))
            {
                String str = "FROM:Device\n"
                        + "HUMIDITY: " + body + "\n"
                        + "DATE: " + date;
                arrayAdapter.add(str);

            }
        }while (smsInboxCursor.moveToNext());
    }
}
