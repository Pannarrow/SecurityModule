package cn.sunline.jni;

/**
 * @Package cn.sunline.jni
 * @Author laijp
 * @Date 2019-07-29
 */
public class LibJDWPJNI {

    static {
        // This is only valid for arm
        System.loadLibrary("antiJDWP");
    }

    public static native void AntiDebug();

    public static native void AntiJDWP();

}
