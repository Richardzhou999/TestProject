package com.uwei.uwproject.view.mine;

import androidx.lifecycle.LifecycleOwner;

import com.uwei.base.mvp.IBasePresenter;
import com.uwei.commom.rx.DefaultBackCallBack;
import com.uwei.manager.IBaseView;
import com.uwei.uwproject.bean.MemberBean;
import com.uwei.uwproject.bean.MemberListBean;

import java.util.ArrayList;

/**
 * @Author Charlie
 * @Date 2022/12/28 15:23
 */
public interface MineContract {

    interface MemberPresenter extends IBasePresenter{

        void getUserBean();

    }

    interface MemberModel  {

        void getMemberBean(DefaultBackCallBack callBack, LifecycleOwner owner);

    }


    interface IMemberView extends IBaseView {

        void loadData(MemberBean bean, ArrayList<MemberListBean> list);


    }


}
