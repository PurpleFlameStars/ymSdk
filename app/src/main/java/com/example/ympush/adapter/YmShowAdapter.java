package com.example.ympush.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ydk.show.R;
import com.ydk.show.bean.DataListBean;
import com.ydk.show.bean.Mpbean;

import java.util.ArrayList;
import java.util.List;

public class YmShowAdapter extends RecyclerView.Adapter<YmShowAdapter.VH> {
    private List<DataListBean> list = new ArrayList<>();
    private IsClickListener clickListener;

    public void setList(List<DataListBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public YmShowAdapter.VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LinearLayout.inflate(viewGroup.getContext(), R.layout.item_ym_ad_layout, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YmShowAdapter.VH holder, final int i) {
        final DataListBean adData = list.get(i);
        holder.fansName.setText(adData.getName());
        holder.fansSign.setText(adData.getGuide());
        int price= adData.getPrice();


        holder.adHint.setText(price+"金币");
        Glide.with(holder.fansHeader.getContext())
                .load(adData.getLogo())
                //.circleCrop()
                .into(holder.fansHeader);
        holder.fansContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.click(adData);
            }
        });
    }

    public interface IsClickListener{
        void click(DataListBean data);
    }
    public void setClickListener(IsClickListener clickListener){
        this.clickListener=clickListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        private final View fansContent;
        private final ImageView fansHeader;
        private final TextView fansName;
        private final TextView adHint;
        private final TextView fansSign;


        VH(@NonNull View itemView) {
            super(itemView);
            fansContent = itemView.findViewById(R.id.fans_content);
            fansHeader = itemView.findViewById(R.id.fans_header);
            fansName = itemView.findViewById(R.id.fans_name);
            adHint = itemView.findViewById(R.id.ad_hint);
            fansSign = itemView.findViewById(R.id.fans_sign);
        }
    }
    }


