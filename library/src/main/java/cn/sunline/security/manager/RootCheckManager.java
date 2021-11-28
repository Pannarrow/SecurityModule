package cn.sunline.security.manager;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import cn.sunline.security.rootbeer.RootBeer;
import cn.sunline.security.util.ContextUtil;

/**
 * @Package cn.sunline
 * @Author laijp
 * @Date 2019-07-29
 */
class RootCheckManager {

    private Context mContext;

    private static class Holder {
        private static final RootCheckManager instance = new RootCheckManager();
    }

    static RootCheckManager getInstance() {
        return RootCheckManager.Holder.instance;
    }

    private RootCheckManager() {
        mContext = ContextUtil.getContext();
    }

    private String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    boolean isDeviceRooted(String version) {
        RootBeer rootBeer =new RootBeer(mContext);
        int sdk_int = getBuildVersion(version);
        if (sdk_int == Integer.MAX_VALUE){
            return rootBeer.isRootedWithSu();
        } else if (Build.VERSION.SDK_INT <= sdk_int){
            return rootBeer.isRootedWithoutBusyBoxCheck();
        }else if (needCheckBusyBoxBrand(getDeviceBrand()) || Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
            return rootBeer.isRootedWithoutBusyBoxCheck();
        }
        return rootBeer.isRooted();
    }

    private boolean needCheckBusyBoxBrand(String brand){
        boolean b = brand.equalsIgnoreCase("oppe");
        b = b || brand.equalsIgnoreCase("vivo");
        b = b || brand.equalsIgnoreCase("smartisan");
        b = b || brand.equalsIgnoreCase("xiaomi");
        b = b || brand.equalsIgnoreCase("huawei");
        return b;
    }

    private int getBuildVersion(String version){
        int sdk_int = -1;
        if (!TextUtils.isEmpty(version)){
            version = version.toLowerCase();
            switch (version){
                case "4.0":
                    sdk_int = Build.VERSION_CODES.JELLY_BEAN;
                    break;
                case "4.4":
                    sdk_int = Build.VERSION_CODES.KITKAT;
                    break;
                case "5.0":
                    sdk_int = Build.VERSION_CODES.LOLLIPOP;
                    break;
                case "6.0":
                    sdk_int = Build.VERSION_CODES.M;
                    break;
                case "7.0":
                    sdk_int = Build.VERSION_CODES.N;
                    break;
                case "8.0":
                    sdk_int = Build.VERSION_CODES.O;
                    break;
                case "9.0":
                    sdk_int = 28;
                    break;
                case "both":
                case "all":
                    sdk_int = Integer.MAX_VALUE;
                    break;
            }
        }
        return sdk_int;
    }
}

