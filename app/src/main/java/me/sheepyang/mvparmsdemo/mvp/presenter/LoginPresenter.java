package me.sheepyang.mvparmsdemo.mvp.presenter;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.jess.arms.base.AppManager;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxUtils;
import com.jess.arms.widget.imageloader.ImageLoader;

import java.net.ConnectException;

import javax.inject.Inject;

import common.WEApplication;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.sheepyang.mvparmsdemo.R;
import me.sheepyang.mvparmsdemo.app.utils.AppUtils;
import me.sheepyang.mvparmsdemo.mvp.contract.LoginContract;
import me.sheepyang.mvparmsdemo.mvp.model.entity.BaseJson;
import me.sheepyang.mvparmsdemo.mvp.model.entity.Login;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.schedulers.Schedulers;


/**
 * 通过Template生成对应页面的MVP和Dagger代码,请注意输入框中输入的名字必须相同
 * 由于每个项目包结构都不一定相同,所以每生成一个文件需要自己导入import包名,可以在设置中设置自动导入包名
 * 请在对应包下按以下顺序生成对应代码,Contract->Model->Presenter->Activity->Module->Component
 * 因为生成Activity时,Module和Component还没生成,但是Activity中有它们的引用,所以会报错,但是不用理会
 * 继续将Module和Component生成完后,编译一下项目再回到Activity,按提示修改一个方法名即可
 * 如果想生成Fragment的相关文件,则将上面构建顺序中的Activity换为Fragment,并将Component中inject方法的参数改为此Fragment
 */


/**
 * Created by SheepYang on 2017/3/1.
 */

@ActivityScope
public class LoginPresenter extends BasePresenter<LoginContract.Model, LoginContract.View> {
    private RxErrorHandler mErrorHandler;
    private Application mApplication;
    private ImageLoader mImageLoader;
    private AppManager mAppManager;

    @Inject
    public LoginPresenter(LoginContract.Model model, LoginContract.View rootView
            , RxErrorHandler handler, Application application
            , ImageLoader imageLoader, AppManager appManager) {
        super(model, rootView);
        this.mErrorHandler = handler;
        this.mApplication = application;
        this.mImageLoader = imageLoader;
        this.mAppManager = appManager;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mAppManager = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

    public void requestLogin(String account, String passwd) {
        mRootView.closeSoftKeyboard();
        if (!AppUtils.isNetworkAvailable((Context) mRootView)) {
            mRootView.showMessage(mApplication.getString(R.string.connect_exception));
            return;
        }
        if (TextUtils.isEmpty(account)) {
            mRootView.showMessage(mApplication.getString(R.string.input_account));
            return;
        }
        if (TextUtils.isEmpty(passwd)) {
            mRootView.showMessage(mApplication.getString(R.string.input_passwd));
            return;
        }
        mModel.login(account, passwd)
                .subscribeOn(Schedulers.io())
//                .retryWhen(new RetryWithDelay(3, 2))//遇到错误时重试,第一个参数为重试几次,第二个参数为重试的间隔
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        mRootView.showLoading();//显示进度条
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        mRootView.hideLoading();//隐藏上拉刷新的进度条
                    }
                })
                .compose(RxUtils.<BaseJson<Login>>bindToLifecycle(mRootView))//使用RXlifecycle,使subscription和activity一起销毁
                .subscribe(new ErrorHandleSubscriber<BaseJson<Login>>(mErrorHandler) {
                    @Override
                    public void onNext(BaseJson<Login> result) {
                        if (result.isSuccess(true)) {
                            WEApplication.setLoginInfo(result.getData());
                            mRootView.toHomePage();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (e instanceof ConnectException) {
                            mRootView.showMessage(mApplication.getString(R.string.connect_exception));
                        }
                    }
                });
    }
}