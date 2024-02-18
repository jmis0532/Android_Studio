package my.platelet.wallet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scan extends AppCompatActivity implements ZXingScannerView.ResultHandler{

    Button back_button;

    ZXingScannerView zXingScannerView;

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        //找到介面
        zXingScannerView = findViewById(R.id.ZXingScannerView_QRCode);
//取得相機權限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this
                        , Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    100);
        }else{

            openQRCamera();

    }
        back_button = findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                intent.putExtra("scanResult_box","scan nothing");
                setResult(REQUEST_CODE,intent);
                finish();

            }
        });
}
    /**開啟QRCode相機*/
    private void openQRCamera() {
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();
    }

    /**取得權限回傳*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100 && grantResults[0] ==0){
            openQRCamera();
        }else{
            Toast.makeText(this, "You don't allow camera permission!", Toast.LENGTH_SHORT).show();
        }
    }
    /**關閉QRCode相機*/
    @Override
    protected void onStop() {
        zXingScannerView.stopCamera();
        super.onStop();
    }
    /**取得QRCode掃描到的物件回傳*/
    @Override
    public void handleResult(Result rawResult) {
        TextView tvResult = findViewById(R.id.textView_Result);
        tvResult.setText(rawResult.getText());
        //ZXing相機預設掃描到物件後就會停止，以此這邊再次呼叫開啟，使相機可以為連續掃描之狀態

        String scanResult = rawResult.getText();



        Intent intent = getIntent();
        intent.putExtra("scanResult_box",scanResult);
        setResult(REQUEST_CODE,intent);
        //onStop();
        finish();
        //openQRCamera();
    }
}

