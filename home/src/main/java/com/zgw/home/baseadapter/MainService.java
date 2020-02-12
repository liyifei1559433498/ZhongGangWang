package com.zgw.home.baseadapter;

import com.zgw.base.model.TestBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Tsinling on 2017/10/17 11:06.
 * description:
 */

public interface MainService {

    @GET("api/PushMessages/PushMessagesList")
    Observable<TestBean> test(
            @Query("MessageType") String MessageType,
            @Query("PageIndex") String PageIndex,
            @Query("PageSize") String PageSize,
            @Query("Status") String Status,
            @Query("UserId") String UserId
    );
}
