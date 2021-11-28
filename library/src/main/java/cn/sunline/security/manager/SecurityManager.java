package cn.sunline.security.manager;


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.WindowManager;

import cn.sunline.jni.LibJDWPJNI;
import cn.sunline.security.R;
import cn.sunline.security.entity.AntiResult;
import cn.sunline.security.util.Constant;
import cn.sunline.security.util.ContextUtil;
import cn.sunline.security.util.SecurityLog;

/**
 * @Package cn.sunline.security
 * @Author laijp
 * @Date 2019-07-29
 */
public class SecurityManager {

    private static class Holder {
        private static final SecurityManager instance = new SecurityManager();
    }

    public static SecurityManager getInstance() {
        return SecurityManager.Holder.instance;
    }

    private SecurityManager() {

    }

    public void init(Context context){
        ContextUtil.init(context.getApplicationContext());
    }

    public void setDebug(boolean bool){
        SecurityLog.setShowLog(bool);
    }

    /**
     *
     * @param androidVer
     * @param sha1
     * @param crcContent
     * @return
     */
    public AntiResult getAntiResult(String androidVer,String sha1,String crcContent){
        return SecurityUtil.getAntiResult(androidVer, sha1, crcContent);
    }

    /**
     *
     * @param sha1
     * @param crcContent
     * @return
     */
    public AntiResult getAntiResult(String sha1,String crcContent){
        return SecurityUtil.getAntiResult(null, sha1, crcContent);
    }

    public AntiResult getAntiReslut(){
        String sha1 = RawManager.getInstance().getData(RawManager.SHA);
        String crc = RawManager.getInstance().getData(RawManager.CRC);
        AntiResult result = SecurityUtil.getAntiResult(null,sha1,crc);
        if (result.getCode() == Constant.CODE_SUCCESS && TextUtils.isEmpty(crc)){
            String checkIntegrityMsg = String.format(ContextUtil.getString(R.string.security_result_40006), ContextUtil.getString(R.string.crc_file));
            return result.setCode(Constant.CODE_MODIFIED).setResult(checkIntegrityMsg);
        }
        return result;
    }

    public boolean checkRoot(String version){
        return RootCheckManager.getInstance().isDeviceRooted(version);
    }

    public boolean checkEmulator(boolean withFile){
        Context context = ContextUtil.getContext();
        if (withFile){
            return EmulatorManager.isQEmuEnvDetected(context);
        }else {
            return EmulatorManager.isQEmuEnvDetectedWithoutFiles(context);
        }
    }

    public boolean antiDebugged(){
        boolean bool = false;
        Context context = ContextUtil.getContext();
        try {
            bool = bool || AntiManager.isDebugged();
            bool = bool || AntiManager.isTaintTrackingDetected(context);
            bool = bool || AntiManager.isMonkeyDetected();
        }catch (NoClassDefFoundError error){
            error.fillInStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return bool;
    }

    public void startJWDP(){
        LibJDWPJNI.AntiJDWP();
    }

    public void limitScreenShot(boolean limit,Activity activity){
        if (activity != null){
            if (limit){
                activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
            }else {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_SECURE);
            }
        }
    }

}
