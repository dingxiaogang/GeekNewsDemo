package com.example.simpleshopping;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RvMainAdapter extends RecyclerView.Adapter {
    private ArrayList<CollectBean> collectBean;
    private onClickListener clickListener;

    public RvMainAdapter(ArrayList<CollectBean> collectBean) {
        this.collectBean = collectBean;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_collect_shop_list, viewGroup, false);
        return new MainViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final MainViewHolder mainViewHolder = (MainViewHolder) viewHolder;
        final CollectBean collectBean = this.collectBean.get(i);
        mainViewHolder.tilte.setText(collectBean.getName());
        mainViewHolder.price.setText(collectBean.getPrice()+"￥/斤");
        Glide.with(viewHolder.itemView.getContext()).load(collectBean.getImagePath()).into(mainViewHolder.imageView);
        mainViewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null){
                    clickListener.onClick(v,i,mainViewHolder.checkBox.isChecked(),collectBean);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return collectBean.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        private TextView tilte,price;
        private ImageView imageView;
        private CheckBox checkBox;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            tilte = itemView.findViewById(R.id.coll_title);
            price = itemView.findViewById(R.id.coll_price);
            imageView = itemView.findViewById(R.id.coll_image);
            checkBox = itemView.findViewById(R.id.coll_checkBox);
        }
    }

    public interface onClickListener {
        void onClick(View view, int position, boolean isCheck, CollectBean collectBean);
    }
    public void setOnClickListener(onClickListener clickListener){
        this.clickListener = clickListener;
    }
}
