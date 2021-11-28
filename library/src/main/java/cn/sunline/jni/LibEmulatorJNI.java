package cn.sunline.jni;

/**
 * @Package cn.sunline.jni
 * @Author laijp
 * @Date 2019-07-29
 */
public class LibEmulatorJNI {

    static {
        // This is only valid for arm
        System.loadLibrary("findEmulator");
    }

    public native static int qemuBkpt();
}
