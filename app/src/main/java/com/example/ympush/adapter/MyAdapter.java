package com.example.ympush.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ympush.R;
import com.ydk.show.bean.AppletDataBean;

import java.util.List;

public class MyAdapter extends BaseAdapter {
   Context context;
   List<AppletDataBean> data;    //数据源
   private IsClickListener clickListener;
   public void setList(List<AppletDataBean> data,Context context) {
      this.data = data;
      this.context=context;
   }

   @Override
   public int getCount() {
      return data == null ? 0 : data.size();
   }

   @Override
   public Object getItem(int position) {
      return data == null ? null : data.get(position);
   }

   @Override
   public long getItemId(int position) {
      return position;
   }



   @Override
   public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder viewHolder=null;
      final AppletDataBean adData=data.get(position);
      if (convertView==null) {
         viewHolder = new ViewHolder();
         convertView= LayoutInflater.from(context).inflate(R.layout.item_ym_ad_layout,null);
         viewHolder.fansContent = convertView.findViewById(R.id.fans_content);
         viewHolder. fansHeader = convertView.findViewById(R.id.fans_header);
         viewHolder.fansName = convertView.findViewById(R.id.fans_name);
         viewHolder. adHint = convertView.findViewById(R.id.ad_hint);
         viewHolder. fansSign = convertView.findViewById(R.id.fans_sign);
         convertView.setTag(viewHolder);
      }else {
         viewHolder= (ViewHolder) convertView.getTag();
      }
      viewHolder.fansName.setText(adData.getName());
      viewHolder.fansSign.setText(adData.getDescription());
      int price= adData.getPrice();
      viewHolder.adHint.setText(price+"金币");
      Glide.with(context)
              .load(adData.getLogo())
              //.circleCrop()
              .into(viewHolder.fansHeader);
      viewHolder.fansContent.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            clickListener.click(adData);
         }
      });
      return convertView;
   }
   public interface IsClickListener{
      void click(AppletDataBean adData);
   }
   public void setClickListener(IsClickListener clickListener){
      this.clickListener=clickListener;
   }
   class ViewHolder{
      public   View fansContent;
      public  ImageView fansHeader;
      public  TextView fansName;
      public  TextView adHint;
      public  TextView fansSign;
   }
}

