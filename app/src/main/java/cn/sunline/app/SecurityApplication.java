package cn.sunline.app;

import android.app.Application;

import cn.sunline.security.manager.SecurityManager;

/**
 * @Package cn.sunline.app
 * @Author laijp
 * @Date 2019-08-27
 */
public class SecurityApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        SecurityManager.getInstance().init(this);
        SecurityManager.getInstance().setDebug(true);
    }
}
