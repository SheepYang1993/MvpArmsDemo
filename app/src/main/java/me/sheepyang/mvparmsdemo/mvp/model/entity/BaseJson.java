package me.sheepyang.mvparmsdemo.mvp.model.entity;

import android.text.TextUtils;

import com.jess.arms.utils.UiUtils;

import java.io.Serializable;

import me.sheepyang.mvparmsdemo.mvp.model.api.Api;


/**
 * 如果你服务器返回的数据固定为这种方式(字段名可根据服务器更改)
 * 替换范型即可重用BaseJson
 * Created by jess on 26/09/2016 15:19
 * Contact with jess.yan.effort@gmail.com
 */

public class BaseJson<T> implements Serializable {
    private T data;
    private String status;
    private String msg;

    public T getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isSuccess() {
        return isSuccess(false);
    }

    /**
     * 请求是否成功
     *
     * @return
     */
    public boolean isSuccess(boolean showMsg) {
        if (status.equals(Api.RequestSuccess)) {
            return true;
        } else {
            if (showMsg && !TextUtils.isEmpty(msg)) {
                UiUtils.SnackbarText(msg);
            }
            return false;
        }
    }
}
