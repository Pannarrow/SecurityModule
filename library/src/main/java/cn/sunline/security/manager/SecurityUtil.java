package cn.sunline.security.manager;

import android.app.KeyguardManager;
import android.content.Context;
import android.text.TextUtils;

import cn.sunline.security.R;
import cn.sunline.security.entity.AntiResult;
import cn.sunline.security.util.Constant;
import cn.sunline.security.util.ContextUtil;

/**
 * @Package cn.sunline.security.util
 * @Author laijp
 * @Date 2020-02-06
 */
public class SecurityUtil {

    static AntiResult getAntiResult(String androidVer, String sha1, String crcContent){
        AntiResult antiResult = new AntiResult();
        boolean isRoot = RootCheckManager.getInstance().isDeviceRooted(androidVer);
        if (isRoot){
            return antiResult.setCode(Constant.CODE_ROOT).setResult(ContextUtil.getString(R.string.security_result_40003));
        }
        boolean isEmulator = EmulatorManager.isQEmuEnvDetectedWithoutFiles(ContextUtil.getContext());
        if (isEmulator){
            return antiResult.setCode(Constant.CODE_EMULATOR).setResult(ContextUtil.getString(R.string.security_result_40004));
        }
        boolean isScreenLock = checkLockScreen();
        if (!isScreenLock){
            return antiResult.setCode(Constant.CODE_LOCKSCREEN).setResult(ContextUtil.getString(R.string.security_result_40005));
        }
        boolean isSignatureAuth = new SignCheck(ContextUtil.getContext(),sha1).check();
        if (!isSignatureAuth){
            return antiResult.setCode(Constant.CODE_SIGNATURE).setResult(ContextUtil.getString(R.string.security_result_40001));
        }
        boolean isCheckStackTrace = AntiManager.isTaintTrackingDetected(ContextUtil.getContext());
        if (isCheckStackTrace){
            return antiResult.setCode(Constant.CODE_STACK).setResult(ContextUtil.getString(R.string.security_result_40002));
        }
        String msg = IntegrityManager.getInstance().checkIntegrity(crcContent);
        if (!TextUtils.isEmpty(msg)){
            String checkIntegrityMsg = String.format(ContextUtil.getString(R.string.security_result_40006),msg);
            return antiResult.setCode(Constant.CODE_MODIFIED).setResult(checkIntegrityMsg);
        }
        return antiResult.setCode(Constant.CODE_SUCCESS).setResult(ContextUtil.getString(R.string.security_result_40000));
    }

    static boolean checkLockScreen(){
        Context context = ContextUtil.getContext();
        KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return keyguardManager != null && keyguardManager.isKeyguardSecure();
    }

}
