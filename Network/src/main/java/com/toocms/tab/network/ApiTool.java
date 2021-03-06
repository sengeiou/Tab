package com.toocms.tab.network;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.toocms.tab.toolkit.AppManager;
import com.toocms.tab.toolkit.ClassUtils;
import com.toocms.tab.toolkit.LogUtil;
import com.toocms.tab.toolkit.Toolkit;
import com.toocms.tab.toolkit.x;

import java.io.File;
import java.lang.reflect.Method;

/**
 * API接口调用类
 *
 * @author Zero
 * @version 4.0
 * @date 2017年03月09日
 */
public final class ApiTool<T> {

    private final Object getTag() {
        AppCompatActivity activity = AppManager.getInstance().getTopActivity();
        try {
            Object fgt = ClassUtils.invoke(activity, "getCurrentFragment", null, null);
            return null == fgt ? activity : fgt;
        } catch (Exception e) {
            e.printStackTrace();
            return activity;
        }
    }

    /**
     * 以GET方式请求数据
     *
     * @param url         设置URL，当传入url为完整地址时直接以该地址为请求地址，否则以格式为主URL+url的拼接结果为请求地址
     * @param params      请求的参数
     * @param apiListener 结果回调监听，<>里为实体类
     */
    public final void getApi(String url, HttpParams params, ApiListener<T> apiListener) {
        getApi(getTag(), url, params, apiListener);
    }

    /**
     * 以GET方式请求数据
     *
     * @param tag         请求的tag，主要用于取消对应的请求以及调用接口出现异常时的处理
     * @param url         设置URL，当传入url为完整地址时直接以该地址为请求地址，否则以格式为主URL+url的拼接结果为请求地址
     * @param params      请求的参数
     * @param apiListener 结果回调监听，<>里为实体类
     */
    public final void getApi(Object tag, String url, HttpParams params, ApiListener<T> apiListener) {
        OkGo.<T>get(Toolkit.isUrl(url) ? url : x.dataSet().getUrls().getBaseUrl() + url)
                .tag(tag)
                .params(params)
                .execute(apiListener);
    }

    /**
     * 以POST方式请求数据
     *
     * @param url         设置URL，当传入url为完整地址时直接以该地址为请求地址，否则以格式为主URL+url的拼接结果为请求地址
     * @param params      请求的参数
     * @param apiListener 结果回调监听，<>里为实体类
     */
    public final void postApi(String url, HttpParams params, ApiListener<T> apiListener) {
        postApi(getTag(), url, params, apiListener);
    }

    /**
     * 以POST方式请求数据
     *
     * @param tag         请求的tag，主要用于取消对应的请求以及调用接口出现异常时的处理
     * @param url         设置URL，当传入url为完整地址时直接以该地址为请求地址，否则以格式为主URL+url的拼接结果为请求地址
     * @param params      请求的参数
     * @param apiListener 结果回调监听，<>里为实体类
     */
    public final void postApi(Object tag, String url, HttpParams params, ApiListener<T> apiListener) {
        OkGo.<T>post(Toolkit.isUrl(url) ? url : x.dataSet().getUrls().getBaseUrl() + url)
                .tag(tag)
                .params(params)
                .execute(apiListener);
    }

    /**
     * 请求下载文件
     *
     * @param url          设置URL，当传入url为完整地址时直接以该地址为请求地址，否则以格式为主URL+url的拼接结果为请求地址
     * @param params       请求的参数
     * @param fileCallback 下载回调监听
     */
    public static final void downloadApi(String url, HttpParams params, FileCallback fileCallback) {
        OkGo.<File>get(Toolkit.isUrl(url) ? url : x.dataSet().getUrls().getBaseUrl() + url)
                .tag(url)
                .params(params)
                .execute(fileCallback);
    }

    /**
     * 请求上传文件
     *
     * @param url            设置URL，当传入url为完整地址时直接以该地址为请求地址，否则以格式为主URL+url的拼接结果为请求地址
     * @param params         请求的参数
     * @param stringCallback 下载回调监听
     */
    public static final void upLoadApi(String url, HttpParams params, StringCallback stringCallback) {
        OkGo.<String>post(Toolkit.isUrl(url) ? url : x.dataSet().getUrls().getBaseUrl() + url)
                .tag(url)
                .params(params)
                .execute(stringCallback);
    }
}
