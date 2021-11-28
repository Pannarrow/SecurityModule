package cn.sunline.security.util;

import android.content.Context;

import androidx.annotation.StringRes;

/**
 * @Package cn.sunline.security.util
 * @Author laijp
 * @Date 2019-07-29
 */
public class ContextUtil {


    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static void init(Context c){
        context = c.getApplicationContext();
    }

    public static String getString(@StringRes int resId) {
        if (context == null) {
            return null;
        }
        return context.getString(resId);
    }
}

