package me.sheepyang.mvparmsdemo.app.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import me.sheepyang.mvparmsdemo.R;


/**
 * Created by SheepYang on 2017/2/15.
 */

public class DialogUtils {

    public static Dialog createLoadingDialog(Context context) {
        return createLoadingDialog(context, true);
    }

    public static Dialog createLoadingDialog(Context context, boolean isCancelable) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view;
        LinearLayout layout;
        if (android.os.Build.VERSION.SDK_INT > 22) {//android 6.0替换clip的加载动画
            view = inflater.inflate(R.layout.layout_loading_dialog, null); // 得到加载view
            layout = (LinearLayout) view.findViewById(R.id.dialog_view); // 加载布局
        } else {
            view = inflater.inflate(R.layout.layout_loading_clip_dialog, null);
            layout = (LinearLayout) view.findViewById(R.id.dialog_view);
        }
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog); // 创建自定义样式dialog
        loadingDialog.setCancelable(isCancelable); // 不可以用"返回键"取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return loadingDialog;
    }
}
