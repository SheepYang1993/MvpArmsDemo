package me.sheepyang.mvparmsdemo.app.utils;

import com.jess.arms.http.cookie.store.CookieStore;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * 各种杂七杂八的工具类
 * Created by SheepYang on 2017/3/2.
 */

public class MyUtils {
    public static List<Cookie> getCookies(String url, CookieStore cookieStore) {
        //一般手动取出cookie的目的只是交给 webview 等等，非必要情况不要自己操作
        HttpUrl httpUrl = HttpUrl.parse(url);
        return cookieStore.getCookie(httpUrl);
    }
}
