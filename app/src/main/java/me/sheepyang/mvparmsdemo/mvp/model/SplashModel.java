package me.sheepyang.mvparmsdemo.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.cookie.store.CookieStore;
import com.jess.arms.mvp.BaseModel;

import java.util.List;

import javax.inject.Inject;

import common.WEApplication;
import me.sheepyang.mvparmsdemo.mvp.contract.SplashContract;
import me.sheepyang.mvparmsdemo.mvp.model.api.Api;
import me.sheepyang.mvparmsdemo.mvp.model.api.cache.CacheManager;
import me.sheepyang.mvparmsdemo.mvp.model.api.service.ServiceManager;
import okhttp3.Cookie;
import okhttp3.HttpUrl;


/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */

/**
 * Created by SheepYang on 2017/2/28.
 */

@ActivityScope
public class SplashModel extends BaseModel<ServiceManager, CacheManager> implements SplashContract.Model {
    private Gson mGson;
    private Application mApplication;

    @Inject
    public SplashModel(ServiceManager serviceManager, CacheManager cacheManager, Gson gson, Application application) {
        super(serviceManager, cacheManager);
        this.mGson = gson;
        this.mApplication = application;
    }

    @Override
    public void onDestory() {
        super.onDestory();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public List<Cookie> getCookies() {
        //一般手动取出cookie的目的只是交给 webview 等等，非必要情况不要自己操作
        CookieStore cookieStore = ((WEApplication) mApplication).getAppComponent().cookieJarImpl().getCookieStore();
        HttpUrl httpUrl = HttpUrl.parse(Api.APP_DOMAIN);
        return cookieStore.getCookie(httpUrl);
    }

    @Override
    public boolean isLogin() {
        List<Cookie> cookies = getCookies();
        // 是否含有登陆cookies信息，判断是否需要登陆
        if (cookies != null && cookies.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public long getDelayedTime() {
        return 1000;
    }

    @Override
    public int getSplashDrawableResource() {
        return -1;
    }
}