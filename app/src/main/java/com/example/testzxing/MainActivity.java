package com.example.testzxing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.skateboard.zxinglib.CaptureActivity;
import com.skateboard.zxinglib.help.CheckPermission;
import com.skateboard.zxinglib.help.Constant;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startBtn=findViewById(R.id.startBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckPermission.checkPermission(MainActivity.this,Constant.SAN_PERMISSION_CODE)){
                    Intent intent=new Intent(MainActivity.this, CaptureActivity.class);
                    MainActivity.this.startActivityForResult(intent, Constant.SCAN_RESULT_CODE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== Constant.SCAN_RESULT_CODE && resultCode== Activity.RESULT_OK) {
            String result=data.getStringExtra(CaptureActivity.KEY_DATA);
            Toast.makeText(this, "qrcode result is "+result, Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constant.SAN_PERMISSION_CODE){
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1){
                    Toast.makeText(MainActivity.this,"权限授予失败",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            Intent intent=new Intent(MainActivity.this, CaptureActivity.class);
            MainActivity.this.startActivityForResult(intent, Constant.SCAN_RESULT_CODE);
        }
    }
}
