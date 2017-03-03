package me.sheepyang.mvparmsdemo.mvp.model.api.service;

import me.sheepyang.mvparmsdemo.mvp.model.api.Api;
import me.sheepyang.mvparmsdemo.mvp.model.entity.BaseJson;
import me.sheepyang.mvparmsdemo.mvp.model.entity.Login;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by SheepYang on 2017/3/1.
 */

public interface KeAiService {
    @POST(Api.LOGIN)
    @FormUrlEncoded
    Observable<BaseJson<Login>> login(@Field("account") String account, @Field("passwd") String passwd);

    @POST(Api.CUSTOMER_LIST)
    @FormUrlEncoded
    Observable<BaseJson> getContactList(@Field("storeId") String storeId, @Field("page") int page, @Field("rows") int rows);
}
