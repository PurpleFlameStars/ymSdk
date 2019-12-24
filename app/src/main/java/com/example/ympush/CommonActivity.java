package com.example.ympush;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ympush.adapter.YmShowAdapter;
import com.ydk.show.ACManager;
import com.ydk.show.ApiManager;
import com.ydk.show.bean.DataListBean;
import com.ydk.show.listener.AdListener;

import java.util.List;

public class CommonActivity extends BaseActivity {
    private YmShowAdapter ymShowAdapter;
    private String[] permission = {Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(CommonActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(CommonActivity.this, permission, 98);
        } else {
            ApiManager.getInstance(CommonActivity.this).init(CommonActivity.this,AndroidUtil.getDeviceId(CommonActivity.this),"1333","LdJk9ZC1@IxMHzYI");
        }

        setContentView(R.layout.activity_common);
        RecyclerView viewById = findViewById(R.id.show);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        viewById.setLayoutManager(linearLayoutManager);
        ymShowAdapter = new YmShowAdapter();
        viewById.setAdapter(ymShowAdapter);
        ymShowAdapter.setClickListener(new YmShowAdapter.IsClickListener() {
            @Override
            public void click(DataListBean adData) {
               ApiManager.getInstance(CommonActivity.this).item(CommonActivity.this,adData.getName());
            }
        });
        request();
    }

    private void request() {
        showLoadingDialog();
        ApiManager.getInstance(CommonActivity.this).getCommonTask(CommonActivity.this, new AdListener() {
            @Override
            public void onSuccess(final List<DataListBean> data) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (data!=null && data.size()>0){
                            dismissLoadingDialog();
                            ymShowAdapter.setList(data);
                            ymShowAdapter.notifyDataSetChanged();
                        }

                    }
                });

            }

            @Override
            public void onFailure() {
            dismissLoadingDialog();
            }

            @Override
            public void onEmpty() {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 98 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            ApiManager.getInstance(CommonActivity.this).init(CommonActivity.this,AndroidUtil.getDeviceId(CommonActivity.this),"1333","LdJk9ZC1@IxMHzYI");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ApiManager.getInstance(CommonActivity.this).onDestroy(CommonActivity.this);
    }
}
