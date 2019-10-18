package kd.ca.awsmacapstoneproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class RecDialog extends AppCompatActivity {

    TextView txtrec;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rec_dialog);

        Intent intent = getIntent();
        String Wlu = intent.getStringExtra("context");
        txtrec = (TextView) findViewById(R.id.drec);
        txtrec.setText(Wlu);


    }
}
