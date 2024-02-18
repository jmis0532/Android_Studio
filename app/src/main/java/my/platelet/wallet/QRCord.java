package my.platelet.wallet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;



import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRCord extends AppCompatActivity {

    //TextView account_qr;
    //Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcord);

        Intent intent = getIntent();
        String account = intent.getStringExtra("account_box1");

        TextView account_qr = findViewById(R.id.account_qr);
        account_qr.setText(account);

        getCode();

        //Button btn=(Button)findViewById(R.id.button);
        //btn.setOnClickListener(this);


    }

    public void getCode(){
        ImageView ivCode=(ImageView) findViewById(R.id.imageView);
        TextView etContent=(TextView)findViewById(R.id.account_qr);
        BarcodeEncoder encoder = new BarcodeEncoder();
        try{
            Bitmap bit = encoder.encodeBitmap(etContent.getText().toString(), BarcodeFormat.QR_CODE,250,250);
            ivCode.setImageBitmap(bit);
        }catch (WriterException e){
            e.printStackTrace();
        }




    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Log.i("123", "onKeyDown: 返回");
    }



}