package me.sheepyang.mvparmsdemo.di.module;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import me.sheepyang.mvparmsdemo.mvp.model.api.service.CommonService;
import retrofit2.Retrofit;

/**
 * Created by zhiyicx on 2016/3/30.
 */
@Module
public class ServiceModule {

    @Singleton
    @Provides
    CommonService provideCommonService(Retrofit retrofit) {
        return retrofit.create(CommonService.class);
    }

}
