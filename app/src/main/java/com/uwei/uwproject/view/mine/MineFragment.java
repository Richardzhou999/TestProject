package com.uwei.uwproject.view.mine;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.uwei.base.UWBaseFragment;
import com.uwei.base.mvp.InjectPresenter;
import com.uwei.base.viewbinding.OnClick;
import com.uwei.commom.utils.SharedPrefUtils;
import com.uwei.commom.utils.SystemUtils;
import com.uwei.uwproject.R;
import com.uwei.uwproject.bean.MemberBean;
import com.uwei.uwproject.bean.MemberItemBean;
import com.uwei.uwproject.bean.MemberListBean;
import com.uwei.uwproject.databinding.FragmentMineBinding;
import com.uwei.uwproject.view.login.mvp.LoginActivity;
import com.uwei.uwproject.view.mine.presenter.impl.MemberPresenter;

import java.util.ArrayList;

/**
 * @Author Charlie
 * @Date 2022/7/19 11:21
 * 我的
 */
public class MineFragment extends UWBaseFragment<FragmentMineBinding> implements MineContract.IMemberView {

    @InjectPresenter
    private MemberPresenter presenter;
    private MineAdapter memberAdapter;


    @Override
    protected void initData() {


        String phone = SharedPrefUtils.INSTANCE.get("phone","");
        String address = SharedPrefUtils.INSTANCE.get("address","");
        getBinding().txtPhone.setText(TextUtils.isEmpty(phone)?getString(R.string.mine_phone):phone);
        getBinding().mineLogin.setVisibility(TextUtils.isEmpty(phone)?View.VISIBLE:View.GONE);
        getBinding().txtAddress.setVisibility(TextUtils.isEmpty(phone)?View.GONE:View.VISIBLE);
        getBinding().txtAddress.setText(TextUtils.isEmpty(address)?"":address);
        getBinding().txtVersion.setText("V"+SystemUtils.INSTANCE.getVersionName(getContext()));


        presenter.getUserBean();

        getBinding().rvMineItem.setLayoutManager(new LinearLayoutManager(getActivity()));
        memberAdapter = new MineAdapter(getActivity());


        getBinding().rvMineItem.setAdapter(memberAdapter);

    }

    @OnClick({R.id.tv_customer_service,R.id.into_customer_service,R.id.tv_agreement,
            R.id.into_agreement,R.id.mine_login,R.id.btn_mine})
    public void ViewClick(View v) {
        switch (v.getId()){
            case R.id.btn_mine:
                ArrayList<MemberListBean> list = new ArrayList<>(memberAdapter.getCurrentList());
                ArrayList<MemberItemBean> beans = new ArrayList<>();
                beans.add(new MemberItemBean(R.mipmap.icon_current_status,"diangasd","xasxa"));
                MemberListBean bean = new MemberListBean("",beans);
                memberAdapter.submitList(list);
                break;
            case R.id.tv_customer_service:
            case R.id.into_customer_service:
                Intent telIntent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + "4008631678");
                telIntent.setData(data);
                startActivity(telIntent);
                break;
            case R.id.tv_agreement:
            case R.id.into_agreement:

                break;
            case R.id.mine_login:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            default:break;
        }
    }



    @Override
    public void loadData(MemberBean bean, ArrayList<MemberListBean> list) {
        memberAdapter.refresh(list);
    }

}