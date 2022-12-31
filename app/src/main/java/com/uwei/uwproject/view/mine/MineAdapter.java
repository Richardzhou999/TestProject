package com.uwei.uwproject.view.mine;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uwei.base.viewbinding.BindingViewHolder;
import com.uwei.uwproject.bean.MemberListBean;
import com.uwei.uwproject.databinding.ItemMemberBinding;

import java.util.ArrayList;

/**
 * @Author Charlie
 * @Date 2022/11/25 10:06
 */
public class MineAdapter extends BaseAdapter<MemberListBean, ItemMemberBinding>{

    private Context mContext;

    public MineAdapter(Context mContext) {
        super(new FooDiffCallback());
        this.mContext = mContext;
    }

    public void refresh(ArrayList<MemberListBean> list){
        this.submitList(list);
    }


    @Override
    public void convert(@NonNull BindingViewHolder<ItemMemberBinding> holder, @NonNull MemberListBean item) {
        if(TextUtils.isEmpty(item.getTitle())){
            holder.getBinding().itemTitle.setVisibility(View.GONE);
        }else {
            holder.getBinding().itemTitle.setVisibility(View.VISIBLE);
            holder.getBinding().itemTitle.setText(item.getTitle());
        }
        LinearLayoutManager manager = new LinearLayoutManager(mContext){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        holder.getBinding().rvItemItem.setLayoutManager(manager);
        holder.getBinding().rvItemItem.setAdapter(new MemberItemAdapter(mContext,item.getBeans()));
    }



    static class FooDiffCallback extends DiffUtil.ItemCallback<MemberListBean>{

        @Override
        public boolean areItemsTheSame(@NonNull MemberListBean oldItem, @NonNull MemberListBean newItem) {
            return TextUtils.equals(oldItem.getTitle(), newItem.getTitle());
        }

        @Override
        public boolean areContentsTheSame(@NonNull MemberListBean oldItem, @NonNull MemberListBean newItem) {
            return TextUtils.equals(oldItem.getBeans().get(0).getSubTitle(), newItem.getBeans().get(0).getSubTitle());
        }

        @Nullable
        @Override
        public Object getChangePayload(@NonNull MemberListBean oldItem, @NonNull MemberListBean newItem) {
            return super.getChangePayload(oldItem, newItem);
        }
    }

}
