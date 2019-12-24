package com.example.ympush;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;

import com.ydk.show.ACManager;


public class MainActivity extends AppCompatActivity {
    private String[] permission = {Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.success).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, permission, 98);
                } else {
                    ACManager.openTask(MainActivity.this,AndroidUtil.getDeviceId(MainActivity.this),"uid","分配的appId");

                }

            }
        });


        findViewById(R.id.cummon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,CommonActivity.class));

            }
        });
        findViewById(R.id.applet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,AppletActivity.class));

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 98 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ACManager.openTask(MainActivity.this,AndroidUtil.getDeviceId(MainActivity.this),"uid","分配的appId");
        }
    }
}
