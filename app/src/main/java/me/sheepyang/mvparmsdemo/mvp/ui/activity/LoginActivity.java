package me.sheepyang.mvparmsdemo.mvp.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.utils.UiUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import common.AppComponent;
import common.WEActivity;
import me.sheepyang.mvparmsdemo.R;
import me.sheepyang.mvparmsdemo.app.utils.AppUtils;
import me.sheepyang.mvparmsdemo.app.widget.ClearEditText;
import me.sheepyang.mvparmsdemo.di.component.DaggerLoginComponent;
import me.sheepyang.mvparmsdemo.di.module.LoginModule;
import me.sheepyang.mvparmsdemo.mvp.contract.LoginContract;
import me.sheepyang.mvparmsdemo.mvp.presenter.LoginPresenter;

import static com.jess.arms.utils.Preconditions.checkNotNull;

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

public class LoginActivity extends WEActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {
    @Nullable
    @BindView(R.id.edt_account)
    ClearEditText edtAccount;
    @Nullable
    @BindView(R.id.edt_password)
    ClearEditText edtPassword;
    @Nullable
    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @Nullable
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @Inject
    Dialog mProgressDialog;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this)) //请将LoginModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_login, null, false);
    }

    @Override
    protected boolean isFullScreen() {
        return true;
    }

    @Override
    protected void initData() {
//        edtAccount.setText("yangyuqun");
//        edtPassword.setText("123456");
        addLayoutListener(llMain, tvLogin);
    }

    /**
     * 1、获取main在窗体的可视区域
     * 2、获取main在窗体的不可视区域高度
     * 3、判断不可视区域高度
     * 1、大于150：键盘显示  获取Scroll的窗体坐标
     * 算出main需要滚动的高度，使scroll显示。
     * 2、小于150：键盘隐藏
     *
     * @param main   根布局
     * @param scroll 需要显示的最下方View
     */
    public void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                main.getWindowVisibleDisplayFrame(rect);
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                if (mainInvisibleHeight > 150) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    int srollHeight = (location[1] + scroll.getHeight() + ((LinearLayout.LayoutParams) scroll.getLayoutParams()).bottomMargin) - rect.bottom;
                    if (srollHeight > 0) {
                        main.scrollTo(0, srollHeight);
                    }
                } else {
                    main.scrollTo(0, 0);
                }
            }
        });
    }


    @Override
    public void showLoading() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoading() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        UiUtils.SnackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        UiUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public void toHomePage() {

    }

    @Override
    public void closeSoftKeyboard() {
        AppUtils.closeSoftInput(this);
    }

    @Override
    @OnClick({R.id.tv_login})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                toLogin();
                break;
            default:
                break;
        }
    }

    private void toLogin() {
        mPresenter.requestLogin(edtAccount.getText().toString().trim(), edtPassword.getText().toString().trim());
    }
}