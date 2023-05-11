package com.uwei.uwproject.view.login;

import com.github.richard.core.EvictDynamicKey;
import com.github.richard.runtime.rx_cache3.internal.DynamicKey;
import com.github.richard.runtime.rx_cache3.internal.LifeCache;
import com.uwei.manager.BasicResponse;
import java.util.concurrent.TimeUnit;
import io.reactivex.rxjava3.core.Observable;


/**
 * @Author Charlie
 * @Date 2022/9/14 14:40
 */
public interface LoginCacheProviders {

    @LifeCache(duration = 1,timeUnit = TimeUnit.MINUTES)
    Observable<BasicResponse> getLogin(Observable<BasicResponse> user, DynamicKey userName, EvictDynamicKey evictDynamicKey);



}
