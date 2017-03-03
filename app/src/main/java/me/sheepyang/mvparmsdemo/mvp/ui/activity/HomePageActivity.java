package me.sheepyang.mvparmsdemo.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.jess.arms.utils.UiUtils;

import java.util.ArrayList;

import butterknife.BindView;
import common.AppComponent;
import common.WEActivity;
import me.sheepyang.mvparmsdemo.R;
import me.sheepyang.mvparmsdemo.di.component.DaggerHomePageComponent;
import me.sheepyang.mvparmsdemo.di.module.HomePageModule;
import me.sheepyang.mvparmsdemo.mvp.contract.HomePageContract;
import me.sheepyang.mvparmsdemo.mvp.model.domain.TabEntity;
import me.sheepyang.mvparmsdemo.mvp.presenter.HomePagePresenter;
import me.sheepyang.mvparmsdemo.mvp.ui.fragment.BlankFragment;
import me.sheepyang.mvparmsdemo.mvp.ui.fragment.ContactFragment;

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
 * Created by SheepYang on 2017/3/2.
 */

public class HomePageActivity extends WEActivity<HomePagePresenter> implements HomePageContract.View {

    @NonNull
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;
    @NonNull
    @BindView(R.id.common_tab_layout)
    CommonTabLayout mCommonTabLayout;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"通讯录", "签到", "我的"};
    private int[] mTabIconNormal = {R.drawable.tab_contacts_normal, R.drawable.tab_sign_normal, R.drawable.tab_mine_normal};
    private int[] mTabIconSelected = {R.drawable.tab_contacts_select, R.drawable.tab_sign_select, R.drawable.tab_mine_select};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private long mCurrentTime;

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerHomePageComponent
                .builder()
                .appComponent(appComponent)
                .homePageModule(new HomePageModule(this)) //请将HomePageModule()第一个首字母改为小写
                .build()
                .inject(this);
    }

    @Override
    protected View initView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_home_page, null, false);
    }

    @Override
    protected void initData() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mTabIconSelected[i], mTabIconNormal[i]));
//            mFragments.add(BlankFragment.newInstance(mTitles[i]));
        }
        mFragments.add(ContactFragment.newInstance());
        mFragments.add(BlankFragment.newInstance(mTitles[1]));
        mFragments.add(BlankFragment.newInstance(mTitles[2]));
        mCommonTabLayout.setTabData(mTabEntities, this, R.id.fl_content, mFragments);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public void onBackPressed() {
        if (System.currentTimeMillis() - mCurrentTime < 2000) {
            mCurrentTime = 0;
            mApplication.getAppManager().AppExit();
        } else {
            mCurrentTime = System.currentTimeMillis();
            showMessage("再次点击退出APP");
        }
    }
}