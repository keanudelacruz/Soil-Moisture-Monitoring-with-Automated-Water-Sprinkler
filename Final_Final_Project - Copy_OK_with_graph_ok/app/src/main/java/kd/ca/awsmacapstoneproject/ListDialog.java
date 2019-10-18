package kd.ca.awsmacapstoneproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ListDialog extends AppCompatActivity {

    TextView txtcontent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_dialog);

        Intent intent = getIntent();
        String vlu = intent.getStringExtra("detail");
        txtcontent = (TextView) findViewById(R.id.content);
        txtcontent.setText(vlu);


    }
}
