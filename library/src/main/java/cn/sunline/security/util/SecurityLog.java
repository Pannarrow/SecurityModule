package cn.sunline.security.util;

/**
 * @Package cn.sunline.security.util
 * @Author laijp
 * @Date 2019-07-30
 */

import android.util.Log;

public class SecurityLog{

    private static boolean showLog = true;

    public static boolean isShowLog() {
        return showLog;
    }

    public static void setShowLog(boolean log) {
        showLog = log;
    }

    public static void i(String tag,String message){
        if (isShowLog()){
            Log.i(tag, message);
        }
    }

    public static void v(String tag,String message){
        if (isShowLog()){
            Log.v(tag, message);
        }
    }

    public static void w(String tag,String message){
        if (isShowLog()){
            Log.w(tag, message);
        }
    }

    public static void e(String tag,String message){
        if (isShowLog()){
            Log.e(tag, message);
        }
    }

    public static void d(String tag,String message){
        if (isShowLog()){
            Log.d(tag, message);
        }
    }
}
