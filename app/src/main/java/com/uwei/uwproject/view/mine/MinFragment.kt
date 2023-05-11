package com.uwei.brand.card.view.mine

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.uwei.base.UWBaseFragment
import com.uwei.base.viewbinding.OnClick
import com.uwei.base.viewbinding.listAdapterxx

import com.uwei.commom.utils.SharedPrefUtils
import com.uwei.commom.utils.SystemUtils.getVersionName
import com.uwei.uwproject.R
import com.uwei.uwproject.bean.MemberBean
import com.uwei.uwproject.bean.MemberListBean
import com.uwei.uwproject.databinding.FragmentMineBinding
import com.uwei.uwproject.databinding.ItemMemberBinding
import com.uwei.uwproject.view.mine.MineAdapter
import com.uwei.uwproject.view.mine.MineContract
import com.uwei.uwproject.view.mine.presenter.impl.MemberPresenter

/**
 * @Author Charlie
 * @Date 2022/7/19 11:21
 * 我的
 */
class MinFragment : UWBaseFragment<FragmentMineBinding>(), MineContract.IMemberView {
    private var presenter: MemberPresenter? = null
    private var memberAdapter: MineAdapter? = null
    private val list = ArrayList<MemberListBean>()
    override fun initData() {
        val phone = SharedPrefUtils["phone", ""]
        val address = SharedPrefUtils["address", ""]
        binding!!.txtPhone.text =
            if (TextUtils.isEmpty(phone)) getString(R.string.mine_phone) else phone
        binding!!.mineLogin.visibility =
            if (TextUtils.isEmpty(phone)) View.VISIBLE else View.GONE
        binding!!.txtAddress.visibility =
            if (TextUtils.isEmpty(phone)) View.GONE else View.VISIBLE
        binding!!.txtAddress.text = if (TextUtils.isEmpty(address)) "" else address
        binding!!.txtVersion.text = "V" + getVersionName(context!!)

        presenter!!.getUserBean()
        binding!!.rvMineItem.layoutManager = LinearLayoutManager(activity)
        memberAdapter = MineAdapter(activity)
//        listAdapterxx<MemberListBean, ItemMemberBinding>(MineAdapter.FooDiffCallback()) {
//
//        }
        binding.rvMineItem.adapter = memberAdapter


    }

    @OnClick([R.id.tv_customer_service, R.id.into_customer_service, R.id.tv_agreement, R.id.into_agreement, R.id.mine_login,R.id.btn_mine])
    fun ViewClick(v: View) {
        when (v.id) {
             R.id.btn_mine ->{

                 val newList = memberAdapter?.currentList?.toMutableList()

                 newList?.get(0)?.beans?.get(0)?.subTitle = "dangqian"
                 memberAdapter?.submitList(newList?.toMutableList())

             }

            R.id.tv_customer_service, R.id.into_customer_service -> {
                val telIntent = Intent(Intent.ACTION_DIAL)
                val data = Uri.parse("tel:" + "4008631678")
                telIntent.data = data
                startActivity(telIntent)
            }
            R.id.tv_agreement, R.id.into_agreement -> {

            }
            R.id.mine_login -> {

            }
            else -> {}
        }
    }



    override fun loadData(bean: MemberBean, list: ArrayList<MemberListBean>) {
        memberAdapter!!.refresh(list)
    }


}