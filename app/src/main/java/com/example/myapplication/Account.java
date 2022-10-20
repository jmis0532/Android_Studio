package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.text.DecimalFormat;


public class Account extends AppCompatActivity {

    //TextInputEditText textInputEditTextpay;
    //TextInputEditText textInputEditTextsendadds;

    Button btn_qr;
    Button btn_scan;
    Button btn_send;

    private static final int REQUEST_CODE = 1;



    protected void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(resultCode, resultCode,data);
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


        TextView pay_textview = findViewById(R.id.pay_textview);


        btn_qr = findViewById(R.id.btn_qr);
        btn_scan =  findViewById(R.id.btn_scan);
        btn_send = findViewById(R.id.btn_send);


        DecimalFormat mDecimalFormat = new DecimalFormat("#,###");

        Intent intent = getIntent();
        String account = intent.getStringExtra("account_box");
        String username = intent.getStringExtra("username_box");
        String blance = intent.getStringExtra("blance_box");
        //String scan_result = intent.getStringExtra("scanResult_box");


        String blance1 = mDecimalFormat.format(Double.parseDouble(blance));//======金錢小數點


        TextView username_textview = findViewById(R.id.username_textview);
        username_textview.setText(username);


        TextView send_textview = findViewById(R.id.send_textview);
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

                Handler handler = new Handler();
                handler.post(new Runnable() {
                @Override
                public void run() {

                    TextView response_textview = findViewById(R.id.response_textview);

                    String my_account1 = String.valueOf(account_textview.getText());
                    String send_account1 = String.valueOf(send_textview.getText());
                    String value3 = String.valueOf(pay_textview.getText());


                    if (!send_account1.equals("") && !value3.equals("")) {

                        //String q = String.valueOf(payment_int);
                        //response_textview.setText(send_account1);

                        String value1 = blance_textview.getText().toString();
                        int blance1_int = Integer.parseInt(blance);
                        String value2 = pay_textview.getText().toString();
                        int payment_int = Integer.parseInt(value2);

                        if ((payment_int != 0 && blance1_int != 0)) {

                            if (payment_int <= blance1_int) {


                                String[] chk_send_account_data = new String[2];
                                chk_send_account_data[0] = "account";
                                chk_send_account_data[1] = "payment";

                                String[] chk_send_account_data1 = new String[2];
                                chk_send_account_data1[0] = send_account1;
                                chk_send_account_data1[1] = value2;

                                PutData putData5 = new PutData("http://114.32.40.112/LoginRegister/chk_sed_account.php", "POST", chk_send_account_data, chk_send_account_data1);
                                putData5.startPut();
                                putData5.onComplete();
                                String result5 = putData5.getResult();


                                //Toast.makeText(getApplicationContext(), result5, Toast.LENGTH_SHORT).show();

                                    if(!result5.equals("")) {


                                        //String account5 =  my_account1;
                                        String[] my_account_data = new String[2];
                                        my_account_data[0] = "account";
                                        my_account_data[1] = "payment";

                                        String[] my_account_data1 = new String[2];
                                        my_account_data1[0] = my_account1;
                                        my_account_data1[1] = value2;


                                        String[] send_account_data = new String[2];
                                        send_account_data[0] = "account";
                                        send_account_data[1] = "payment";

                                        String[] send_account_data1 = new String[2];
                                        send_account_data1[0] = send_account1;
                                        send_account_data1[1] = value2;

                                        PutData putData3 = new PutData("http://114.32.40.112/LoginRegister/Payout.php", "POST", my_account_data, my_account_data1);

                                        putData3.startPut();
                                        putData3.onComplete();
                                        String result3 = putData3.getResult();

                                        String result3_d = mDecimalFormat.format(Double.parseDouble(result3));

                                        blance_textview.setText(result3_d);

                                        PutData putData4 = new PutData("http://114.32.40.112/LoginRegister/GetPayment.php", "POST", send_account_data, send_account_data1);

                                        putData4.startPut();
                                        putData4.onComplete();
                                        String result4 = putData4.getResult();

                                        send_textview.setText("");
                                        pay_textview.setText("");


                                        //Toast.makeText(getApplicationContext(), result4, Toast.LENGTH_SHORT).show();
                                        response_textview.setText(result4);


                                    }else {
                                        response_textview.setText("send account error!");
                                    }
                            } else {

                                response_textview.setText("payment cant > blance!");

                            }

                        } else {
                            response_textview.setText("payment or blance cant be 0!");
                        }

                    } else {
                        response_textview.setText("send account or payment cant be empty!");
                    }
                    //Intent intent = new Intent(getApplicationContext(),Account.class);
                    //startActivity(intent);

                    //PutData putData = new PutData("http://114.32.40.112/LoginRegister/Payout.php", "POST", account_data, send_account1);


                    //Toast.makeText(getApplicationContext(), (int) q, Toast.LENGTH_SHORT).show();
                }//run

                });

                }//onClick(View v)





        });



    }


}