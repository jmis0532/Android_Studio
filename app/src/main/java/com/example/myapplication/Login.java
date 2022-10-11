package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {



    TextInputEditText textInputEditTextUsername, textInputEditTextPassword;
    Button buttonLogin;
    TextView textViewSignUp;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditTextUsername = findViewById(R.id.account_textview);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.signUpText);
        progressBar = findViewById(R.id.progress);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
                finish();
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fullname, username, password, email;
                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());


                if (!username.equals("") && !password.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                    @Override
                    public void run() {

                            String[] field = new String[2];

                            field[0] = "username";
                            field[1] = "password";


                            String[] data = new String[2];

                            data[0] = username;
                            data[1] = password;

                            PutData putData = new PutData("http://114.32.40.112/LoginRegister/login.php", "POST", field, data);



                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE);


                                    String result1 = putData.getResult();
                                    Intent intent = null;
                                    if (result1.equals("Username or Password wrong")) {
                                        Toast.makeText(getApplicationContext(), result1, Toast.LENGTH_SHORT).show();
                                    } else if (result1.equals("Error: Database connection")) {
                                        Toast.makeText(getApplicationContext(), result1, Toast.LENGTH_SHORT).show();
                                    } else {

                                        PutData putData1 = new PutData("http://114.32.40.112/LoginRegister/GetData.php", "POST", field, data);
                                        putData1.startPut();
                                        putData1.onComplete();
                                        String result2 = putData1.getResult();

                                        intent = new Intent(getApplicationContext(), Account.class);
                                        intent.putExtra("username_box", username);
                                        intent.putExtra("account_box", result1);
                                        intent.putExtra("blance_box", result2);
                                        startActivity(intent);




                                    }

                                }

                            }

                        }//run
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();

                }

            }


        });


    }
}