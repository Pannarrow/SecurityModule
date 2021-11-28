package cn.sunline.security.manager;

import android.content.Context;

import cn.sunline.security.anti.emulator.FindEmulator;
import cn.sunline.security.util.SecurityLog;

/**
 * @Project AntiModule
 * @Package cn.sunline.security
 * @Author laijp
 * @Date 2019/1/23
 */
class EmulatorManager {

    private final static String TAG = "EmulatorManager";

    static boolean isQEmuEnvDetectedWithoutFiles(Context context) {
        boolean hasKnownDeviceId = FindEmulator.hasKnownDeviceId(context.getApplicationContext());
        boolean hasKnownPhoneNumber = FindEmulator.hasKnownPhoneNumber(context.getApplicationContext());
        boolean isOperatorNameAndroid = FindEmulator.isOperatorNameAndroid(context.getApplicationContext());
        boolean hasKnownImsi = FindEmulator.hasKnownImsi(context.getApplicationContext());
        boolean hasEmulatorBuild = FindEmulator.hasEmulatorBuild(context.getApplicationContext());
        boolean hasPipes = FindEmulator.hasPipes();
        boolean hasQEmuDriver = FindEmulator.hasQEmuDrivers();
        boolean hasEmulatorAdb = FindEmulator.hasEmulatorAdb();
        boolean checkQemuBreakpoint = FindEmulator.checkQemuBreakpoint();
        SecurityLog.w(TAG,"Checking for QEmu env...");
        SecurityLog.i(TAG,"hasKnownDeviceId : " + hasKnownDeviceId );
        SecurityLog.i(TAG,"hasKnownPhoneNumber : " + hasKnownPhoneNumber);
        SecurityLog.i(TAG,"isOperatorNameAndroid : " + isOperatorNameAndroid);
        SecurityLog.i(TAG,"hasKnownImsi : " + hasKnownImsi);
        SecurityLog.i(TAG,"hasEmulatorBuild : " + hasEmulatorBuild);
        SecurityLog.i(TAG,"hasPipes : " + hasPipes);
        SecurityLog.i(TAG,"hasQEmuDriver : " + hasQEmuDriver);
        SecurityLog.i(TAG,"hasEmulatorAdb :" + hasEmulatorAdb);
        SecurityLog.i(TAG,"hitsQemuBreakpoint : " + checkQemuBreakpoint);
        checkQemuBreakpoint = checkBreakpoint() && checkQemuBreakpoint;
        if (hasKnownDeviceId
                || hasKnownPhoneNumber
                || isOperatorNameAndroid
                || hasKnownImsi
                || hasEmulatorBuild
                || hasPipes
                || hasQEmuDriver
                || hasEmulatorAdb
                || checkQemuBreakpoint) {
            SecurityLog.e(TAG,"QEmu environment detected.");
            return true;
        } else {
            SecurityLog.i(TAG,"QEmu environment not detected.");
            return false;
        }
    }

    static boolean isQEmuEnvDetected(Context context) {
        if (!checkFile()){
            return isQEmuEnvDetectedWithoutFiles(context);
        }else {
            boolean hasQEmuFiles = FindEmulator.hasQEmuFiles();
            boolean hasGenyFiles = FindEmulator.hasGenyFiles();
            SecurityLog.i(TAG,"hasQEmuFiles : " + hasQEmuFiles);
            SecurityLog.i(TAG,"hasGenyFiles : " + hasGenyFiles);
            if (isQEmuEnvDetectedWithoutFiles(context) || hasQEmuFiles || hasGenyFiles) {
                SecurityLog.e(TAG,"QEmu environment detected.");
                return true;
            } else {
                SecurityLog.i(TAG,"QEmu environment not detected.");
                return false;
            }
        }
    }

    private static boolean checkFile(){
        if (android.os.Build.BRAND.equalsIgnoreCase("samsung")){
            return false;
        }
        return true;
    }

    private static boolean checkBreakpoint(){
        if (android.os.Build.BRAND.equalsIgnoreCase("samsung")){
            //SamSung checkQemuBreakpoint will be true;
            return false;
        }
//        if (android.os.Build.BRAND.equalsIgnoreCase("huawei")
//                && android.os.Build.VERSION.SDK_INT >= 29){
//            //HuaWei and android > 10 checkQemuBreakpoint will be true;
//            return false;
//        }
//        if (android.os.Build.BRAND.equalsIgnoreCase("redmi")
//                && android.os.Build.VERSION.SDK_INT >= 27){
//            //redmi and android > 10 checkQemuBreakpoint will be true;
//            return false;
//        }
//        if (android.os.Build.BRAND.equalsIgnoreCase("motorola")
//                && android.os.Build.VERSION.SDK_INT >= 26){
//            //motorola and android > 8.0 checkQemuBreakpoint will be true;
//            return false;
//        }
        if (android.os.Build.VERSION.SDK_INT >= 26){
            // HuaWei , redmi ,motorola
            // when android version >8.0 checkQemuBreakpoint will be true;
            return false;
        }
        return true;
    }

}
