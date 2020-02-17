package com.example.ympush;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ympush.adapter.MyAdapter;
import com.ydk.show.ApiManager;
import com.ydk.show.bean.AppletDataBean;
import com.ydk.show.bean.DataListBean;
import com.ydk.show.listener.AdListener;
import com.ydk.show.listener.AppletTaskListener;
import com.ydk.show.listener.QueryTaskListener;

import java.util.List;

public class AppletActivity extends BaseActivity {
    private ListView recyclerView;
    private MyAdapter taskAdapter;
    private String uid;
    private String appKey;
    private String key;
    private int status;
    private String myCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applet);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        appKey = intent.getStringExtra("app_key");
        key = intent.getStringExtra("key");
        recyclerView = findViewById(R.id.applet_task);
        taskAdapter = new MyAdapter();
        recyclerView.setAdapter(taskAdapter);
        taskAdapter.setClickListener(new MyAdapter.IsClickListener() {
            @Override
            public void click(AppletDataBean adData) {
                status = adData.getStatus();
                myCode = adData.getMycode();
               ApiManager.getInstance(AppletActivity.this).itemApplet(AppletActivity.this,adData);
            }
        });

        // request(uid, appKey);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(myCode)){
            requestSuccess(appKey);
            myCode="";
        }

        request(uid,appKey);
    }

    private void request(String uid, String appKey){
        showLoadingDialog();
        ApiManager.getInstance(this).getAppletCommonTask(uid,appKey,key ,new AppletTaskListener() {
            @Override
            public void onSuccess(List<AppletDataBean> list) {
                dismissLoadingDialog();
                if (list!=null && list.size()>0){
                    taskAdapter.setList(list,AppletActivity.this);
                    taskAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure() {
                dismissLoadingDialog();
            }
        });
    }

    private void requestSuccess(String appKey){
        ApiManager.requestTaskStatus(appKey, status, myCode, new QueryTaskListener() {
            @Override
            public void onTaskSuccess(int i) {
                Toast.makeText(AppletActivity.this, "恭喜完成小程序体验任务，+" + i, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onTaskFailure() {
                Toast.makeText(AppletActivity.this,"您体验小程序时间不够，请重新体验", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure() {

            }
        });
    }
}
