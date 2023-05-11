package com.uwei.uwproject.network;

import com.uwei.commom.network.BasicResponse;
import com.uwei.uwproject.bean.ActivationBean;
import com.uwei.uwproject.bean.DetailBean;
import com.uwei.uwproject.bean.LoginBean;
import com.uwei.uwproject.bean.MemberBean;
import com.uwei.uwproject.bean.PayDetailBean;
import com.uwei.uwproject.bean.VersionBean;

import java.math.BigDecimal;
import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @Author Charlie
 * @Date 2022/7/19 14:49
 *
 * 接口管理
 */
public interface ApiService {

    /**
     * 查询该卡是否在库
     * 查询是否有订单
     */
    @GET("api/zjcards/check/{iccId}")
    Observable<BasicResponse<ActivationBean>> checkLibrary(@Path("iccId") String iccId);

    /**
     *  套餐详情
     */
    @GET("api/bagitems/bagId={bagId}")
    Observable<BasicResponse<DetailBean>> getMealDetail(@Path("bagId") int bagId);


    /**
     * 付款详情
     */
    @GET("api/items/{id}")
    Observable<BasicResponse<PayDetailBean>> getPaymentDetail(@Path("id") int id);

    /**
     * 减免
     */
    @GET("transaction/getZJTXDeductionAmount/{id}")
    Observable<BasicResponse<BigDecimal>> getReduction(@Path("id") int id);

    /**
     * 付款
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("transaction/buyZjCard")
    Observable<BasicResponse<String>> payment(@Body RequestBody body);

    /**
     * 存储用户信息
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("api/zjcards/activateBack")
    Observable<BasicResponse<String>> saveUser(@Header("calSign") String calSin,
            @Header("timestamp") long time,@Body RequestBody body);

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("passport/accesstokens?grantType=zjCardVerificationCode")
    Observable<BasicResponse<LoginBean>> login(@Field("cellphone") String cellphone,
                                               @Field("verificationCode") String verificationCode);

    /**
     * 登录
     */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("ppr/app/login/pub/login")
    Observable<BasicResponse<LoginBean>> V2Login(@Body RequestBody body);


    /**
     * 获取验证码
     */
    @FormUrlEncoded
    @POST("passport/verificationcodes")
    Observable<BasicResponse> getVerificationCode(@Field("cellphone") String cellphone);


    /**
     * 充值
     */
    @FormUrlEncoded
    @POST("api/zjcards/prepaidRefill")
    Observable<BasicResponse<String>> prepaidRefill(@FieldMap Map<String,Object> map);

    /**
     * 会员中心
     */
    @GET("api/zjcards/userCenter/{telPhone}")
    Observable<BasicResponse<MemberBean>> getMemberCenter(@Path("telPhone") String telPhone);

    /**
     * 保存套餐Id
     */
    @GET("api/zjcards/getOperatorProductId/{iccid}")
    Observable<BasicResponse<String>> getOperatorProductId(@Path("iccid") String iccid);

    /**
     * 版本更新
     * @return
     */
    @GET("api/apppacks/appId=zjzt/latest")
    Observable<BasicResponse<VersionBean>> getVersion();


}
