package com.uwei.uwproject.view.mine;

import androidx.lifecycle.LifecycleOwner;

import com.uwei.base.mvp.IBaseModel;
import com.uwei.commom.rx.DefaultBackCallBack;
import com.uwei.commom.utils.SharedPrefUtils;

/**
 * @Author Charlie
 * @Date 2022/7/29 16:18
 */
public class MemberModelI implements MineContract.MemberModel, IBaseModel {

    public MemberModelI() {
        super();
    }

    @Override
    public void getMemberBean( DefaultBackCallBack callBack, LifecycleOwner owner) {
        String telphone = SharedPrefUtils.INSTANCE.get("phone","");




        //RxHelper.INSTANCE.createSubscribe(service.getMemberCenter(telphone),callBack,owner);


    }
}
