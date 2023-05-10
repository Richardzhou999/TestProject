package com.uwei.uwproject.view.mine.presenter.impl;

import androidx.annotation.NonNull;

import com.uwei.base.mvp.BasePresenter;
import com.uwei.manager.BasicResponse;
import com.uwei.commom.rx.DefaultBackCallBack;
import com.uwei.commom.utils.SharedPrefUtils;
import com.uwei.uwproject.R;
import com.uwei.uwproject.bean.MemberBean;
import com.uwei.uwproject.bean.MemberItemBean;
import com.uwei.uwproject.bean.MemberListBean;
import com.uwei.uwproject.view.mine.MineContract;
import com.uwei.uwproject.view.mine.MemberModel;

import java.util.ArrayList;

/**
 * @Author Charlie
 * @Date 2022/7/29 16:17
 */
public class MemberPresenter extends BasePresenter<MineContract.IMemberView, MemberModel> implements MineContract.MemberPresenter {

    @Override
    public void getUserBean() {
        getModel().getMemberBean(
                new DefaultBackCallBack<MemberBean>(getView().getViewContext()) {

                    @Override
                    public void onSuccessEmpty() {

                    }

                    @Override
                    public void onSuccess(MemberBean response) {

                        ArrayList<MemberListBean> list = loadList(response);
                        getView().loadData(response,list);
                    }

                    @Override
                    public void onError(@NonNull BasicResponse<MemberBean> response) {

                    }

                    @Override
                    public void onFailure(Throwable e, boolean netWork) throws Exception {

                    }
                },getView().getLifecycleOwner());
    }




    public ArrayList<MemberListBean> loadList(MemberBean memberBean){
        ArrayList<MemberListBean> list = new ArrayList<>();
        String phone = SharedPrefUtils.INSTANCE.get("phone","");
        if(memberBean != null){

            ArrayList<MemberItemBean> list1 = new ArrayList<>();
            ArrayList<MemberItemBean> list2 = new ArrayList<>();
            ArrayList<MemberItemBean> list3 = new ArrayList<>();
            ArrayList<MemberItemBean> list4 = new ArrayList<>();
            list1.add(new MemberItemBean(R.mipmap.icon_current_number,"当前号码",phone));
            String status = "";
            switch (memberBean.getCardState()){
                case "refunded":
                    status = "已退费";
                    break;
                case "pending":
                    status = "失效(待激活)";
                    break;
                case "activated":
                    status = "开机(已激活)";
                    break;
                case "stop":
                    status = "停机";
                    break;
                case "halfStop":
                    status = "半停机";
                    break;
                case "accountCancellation":
                    status = "销户";
                    break;
                default:break;
            }
            list1.add(new MemberItemBean(R.mipmap.icon_current_status,"当前状态",status));
            list1.add(new MemberItemBean(R.mipmap.icon_current_meal,"当前套餐",memberBean.getItemName()));

            list2.add(new MemberItemBean(R.mipmap.icon_monthly_rent,"月租",memberBean.getItemOriginalPrice()+""));
            list2.add(new MemberItemBean(R.mipmap.icon_voice_resources,"套餐内语音资源",memberBean.getVoice()));
            list2.add(new MemberItemBean(R.mipmap.icon_traffic_resources,"套餐内流量资源",memberBean.getFlow()));
            list2.add(new MemberItemBean(R.mipmap.icon_sms_resources,"套餐内短信资源",memberBean.getMessage()));

            list3.add(new MemberItemBean(R.mipmap.icon_voice_resources,"语音",memberBean.getVoicePriceDesc()));
            list3.add(new MemberItemBean(R.mipmap.icon_sms_resources,"短信",memberBean.getMessagePriceDesc()));
            list4.add(new MemberItemBean(R.mipmap.icon_traffic_resources,"流量放心用 3元/G/天",memberBean.getSubProduct()));

            list.add(new MemberListBean("",list1));
            list.add(new MemberListBean("",list2));
            list.add(new MemberListBean("套餐外资费",list3));
            list.add(new MemberListBean("附加包",list4));
        }


        return list;
    }
}
