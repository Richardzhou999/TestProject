package com.uwei.uwproject.view.mine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.uwei.uwproject.R;
import com.uwei.uwproject.bean.MemberItemBean;

import java.util.ArrayList;

/**
 * @Author Charlie
 * @Date 2022/7/29 18:05
 */
public class MemberItemAdapter extends RecyclerView.Adapter<MemberItemAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MemberItemBean> list;

    public MemberItemAdapter(Context mContext, ArrayList<MemberItemBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_item_member,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(list.get(position).getImageUrl());
        holder.subTitle.setText(list.get(position).getSubTitle());
        holder.content.setText(list.get(position).getContent());
        if(list.size()-1 == position){
            holder.line.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() != 0?list.size():0;
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageView;
        private TextView subTitle, content;
        private View line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.item_icon);
            subTitle = itemView.findViewById(R.id.item_sub_title);
            content = itemView.findViewById(R.id.item_content);
            line = itemView.findViewById(R.id.line_item);
        }
    }
}
