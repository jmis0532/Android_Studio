package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;


public class Account extends AppCompatActivity {

    Button btn_qr;
    Button btn_scan;
    Button btn_send;

    private static final int REQUEST_CODE = 1;



    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        //super.onActivityResult(resultCode, resultCode,data);
        switch(requestCode){
            case REQUEST_CODE:
                String scan_result = data.getStringExtra("scanResult_box");

                TextView send_textview = findViewById(R.id.send_textview);
                send_textview.setText(scan_result);

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        btn_qr = findViewById(R.id.btn_qr);
        btn_scan =  findViewById(R.id.btn_scan);
        btn_send = findViewById(R.id.btn_send);


        DecimalFormat mDecimalFormat = new DecimalFormat("#,###");

        Intent intent = getIntent();
        String account = intent.getStringExtra("account_box");
        String username = intent.getStringExtra("username_box");
        String blance = intent.getStringExtra("blance_box");
        //String scan_result = intent.getStringExtra("scanResult_box");


        String blance1 = mDecimalFormat.format(Double.parseDouble(blance));


        TextView username_textview = findViewById(R.id.username_textview);
        username_textview.setText(username);


        //TextView send_textview = findViewById(R.id.send_textview);
        //send_textview.setText(scan_result);

        TextView account_textview = findViewById(R.id.account_textview);
        account_textview.setText(account);

        TextView blance_textview = findViewById(R.id.blance_textview);
        blance_textview.setText(blance1);

        btn_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),QRCord.class);
                String account1 = account;
                intent.putExtra("account_box1",account1);
                startActivity(intent);

            }
        });

        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),Scan.class);
                startActivityForResult(intent,REQUEST_CODE);

            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });



    }


}