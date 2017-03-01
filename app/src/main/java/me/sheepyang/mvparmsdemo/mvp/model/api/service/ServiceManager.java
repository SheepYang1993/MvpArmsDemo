package me.sheepyang.mvparmsdemo.mvp.model.api.service;

import com.jess.arms.http.BaseServiceManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by jess on 8/5/16 13:01
 * contact with jess.yan.effort@gmail.com
 */
@Singleton
public class ServiceManager implements BaseServiceManager {
    private LoginService mLoginService;
    private CommonService mCommonService;

    /**
     * 如果需要添加service只需在构造方法中添加对应的service,在提供get方法返回出去,只要在ServiceModule提供了该service
     * Dagger2会自行注入
     *
     * @param commonService
     */
    @Inject
    public ServiceManager(CommonService commonService, LoginService loginService) {
        this.mCommonService = commonService;
        this.mLoginService = loginService;
    }

    public CommonService getCommonService() {
        return mCommonService;
    }

    public LoginService getLoginService() {
        return mLoginService;
    }

    /**
     * 这里可以释放一些资源(注意这里是单例，即不需要在activity的生命周期调用)
     */
    @Override
    public void onDestory() {

    }
}
