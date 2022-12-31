package com.uwei.uwproject.view.mine;

import androidx.lifecycle.LifecycleOwner;

import com.uwei.base.mvp.BaseModel;
import com.uwei.commom.rx.DefaultBackCallBack;
import com.uwei.commom.rx.RxHelper;
import com.uwei.commom.utils.SharedPrefUtils;
import com.uwei.uwproject.base.CardApplication;
import com.uwei.uwproject.constant.Constant;
import com.uwei.uwproject.network.ApiService;
import com.uwei.uwproject.view.mine.MineContract;

/**
 * @Author Charlie
 * @Date 2022/7/29 16:18
 */
public class MemberModel extends BaseModel implements MineContract.MemberModel {

    public MemberModel() {
        super();
    }

    @Override
    public void getMemberBean( DefaultBackCallBack callBack, LifecycleOwner owner) {
        String telphone = SharedPrefUtils.INSTANCE.get("phone","");

        ApiService service = RxHelper.INSTANCE.getApiServer(CardApplication.getContext(), Constant.BASE_URL
                ,"Authorization", ApiService.class);


        RxHelper.INSTANCE.createSubscribe(service.getMemberCenter(telphone),callBack,owner);


    }
}
