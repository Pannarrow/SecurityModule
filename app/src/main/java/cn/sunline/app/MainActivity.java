package cn.sunline.app;

import android.os.Bundle;
import android.util.Log;

import cn.sunline.security.activity.SecurityActivity;
import cn.sunline.security.entity.AntiResult;
import cn.sunline.security.manager.SecurityManager;

public class MainActivity extends SecurityActivity {

    private final String SHA1 = "6E:8D:D7:28:4B:7B:3A:66:B2:69:6C:54:80:17:A9:87:24:AF:5F:48";

    @Override
    public boolean limitScreenShot() {
        return true;
    }

    @Override
    public boolean antiDebugged() {
        return true;
    }

    @Override
    public void finishWithAntiDebugged() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SecurityManager.getInstance().startJWDP();
        AntiResult antiResult = SecurityManager.getInstance().getAntiResult(SHA1, "");
        Log.e("laijp", String.format("AntiResult code:%s msg:%s", antiResult.getCode(), antiResult.getResult()));
    }
}
