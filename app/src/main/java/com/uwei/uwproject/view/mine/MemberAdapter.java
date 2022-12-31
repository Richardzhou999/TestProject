package com.uwei.uwproject.view.mine;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.uwei.uwproject.R;
import com.uwei.uwproject.bean.MemberListBean;

import java.util.ArrayList;

/**
 * @Author Charlie
 * @Date 2022/7/29 18:05
 */
public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<MemberListBean> list;

    public MemberAdapter(Context mContext, ArrayList<MemberListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void refresh(ArrayList<MemberListBean> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_member,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(TextUtils.isEmpty(list.get(position).getTitle())){
            holder.title.setVisibility(View.GONE);
        }else {
            holder.title.setText(list.get(position).getTitle());
        }
        LinearLayoutManager manager = new LinearLayoutManager(mContext){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        holder.item.setLayoutManager(manager);
        holder.item.setAdapter(new MemberItemAdapter(mContext,list.get(position).getBeans()));
    }

    @Override
    public int getItemCount() {
        return list.size() != 0?list.size():0;
    }



    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private RecyclerView item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.item_title);
            item = itemView.findViewById(R.id.rv_item_item);

        }
    }
}
