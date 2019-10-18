package kd.ca.awsmacapstoneproject;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

public class Home extends AppCompatActivity {

    private LinearLayout cardNotification, cardTotalIrrigate, cardVisualizer;
    public Dialog mDialog;
    public Button mDialogyes,mDialogno;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        actionBar = getSupportActionBar();
        actionBar.hide();

        createDialog();

        cardTotalIrrigate = (LinearLayout) findViewById(R.id.card_totalirri);
        cardTotalIrrigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Listing.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });

        cardVisualizer = (LinearLayout) findViewById(R.id.card_visualizer);
        cardVisualizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, LineGraphActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });



        cardNotification = (LinearLayout) findViewById(R.id.card_inbox);
        cardNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Notification.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });



    }


    protected void createDialog() {
        mDialog = new Dialog(this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_exit);
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(false);
        mDialogyes = (Button) mDialog.findViewById(R.id.yes);
        mDialogno = (Button) mDialog.findViewById(R.id.No);
        mDialogyes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                finish();
                System.exit(0);
            }
        });

        mDialogno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        mDialog.show();
    }
}
