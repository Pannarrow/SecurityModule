package cn.sunline.security.manager;

import android.content.Context;

import cn.sunline.security.anti.debugger.FindDebugger;
import cn.sunline.security.anti.monkey.FindMonkey;
import cn.sunline.security.anti.taint.FindTaint;
import cn.sunline.security.util.SecurityLog;

/**
 * @Project AntiModule
 * @Package cn.sunline.security
 * @Author laijp
 * @Date 2019/1/23
 */
class AntiManager{

    private final static String TAG = "AntiManger";

    static boolean isTaintTrackingDetected(Context context) {
        SecurityLog.w(TAG,"Checking for Taint tracking...");
        SecurityLog.i(TAG,"hasAppAnalysisPackage : " + FindTaint.hasAppAnalysisPackage(context.getApplicationContext()));
        SecurityLog.i(TAG,"hasTaintClass : " + FindTaint.hasTaintClass());
        SecurityLog.i(TAG,"hasTaintMemberVariables : " + FindTaint.hasTaintMemberVariables());
        if (FindTaint.hasAppAnalysisPackage(context.getApplicationContext()) || FindTaint.hasTaintClass()
                || FindTaint.hasTaintMemberVariables()) {
            SecurityLog.e(TAG,"Taint tracking was detected.");
            return true;
        } else {
            SecurityLog.i(TAG,"Taint tracking was not detected.");
            return false;
        }
    }

    static boolean isMonkeyDetected() {
        SecurityLog.w(TAG,"Checking for Monkey user...");
        if (FindMonkey.isUserAMonkey()) {
            SecurityLog.e(TAG,"Monkey user was detected.");
            return true;
        } else {
            SecurityLog.i(TAG,"Monkey user was not detected.");
            return false;
        }
    }

    static boolean isDebugged() {
        SecurityLog.e(TAG,"Checking for debuggers...");

        boolean tracer = false;
        try {
            tracer = FindDebugger.hasTracerPid();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (FindDebugger.isBeingDebugged() || tracer) {
            SecurityLog.e(TAG,"Debugger was detected");
            return true;
        } else {
            SecurityLog.i(TAG,"No debugger was detected.");
            return false;
        }
    }

    static boolean checkStackTrace() {
        try {
            throw new Exception("blah");
        } catch(Exception e) {
            int zygoteInitCallCount = 0;
            for(StackTraceElement stackTraceElement : e.getStackTrace()) {
                if(stackTraceElement.getClassName().equals("com.android.internal.os.ZygoteInit")) {
                    zygoteInitCallCount++;
                    if(zygoteInitCallCount == 2) {
                        SecurityLog.i("checkStackTrace","Substrate is active on the device.");
                        return true;
                    }
                }
                if(stackTraceElement.getClassName().equals("com.saurik.substrate.MS$2") &&
                        stackTraceElement.getMethodName().equals("invoked")) {
                    SecurityLog.i("checkStackTrace","A method on the stack trace has been hooked using Substrate.");
                    return true;
                }
                if(stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") &&
                        stackTraceElement.getMethodName().equals("main")) {
                    SecurityLog.i("checkStackTrace","Xposed is active on the device.");
                    return true;
                }
                if(stackTraceElement.getClassName().equals("de.robv.android.xposed.XposedBridge") &&
                        stackTraceElement.getMethodName().equals("handleHookedMethod")) {
                    SecurityLog.i("checkStackTrace","A method on the stack trace has been hooked using Xposed.");
                    return true;
                }
            }
        }
        SecurityLog.i("checkStackTrace","the stack has no suspicious method calls.");
        return false;
    }
}
