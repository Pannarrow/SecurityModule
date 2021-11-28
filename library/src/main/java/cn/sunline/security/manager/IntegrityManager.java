package cn.sunline.security.manager;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import cn.sunline.security.util.ContextUtil;

/**
 * @Package cn.sunline.security.file
 * @Author laijp
 * @Date 2019-07-30
 */
class IntegrityManager {

    private Context mContext;
    private List<String> mModifiedFiles = new ArrayList<>();

    private static class Holder {
        private static final IntegrityManager instance = new IntegrityManager();
    }

    static IntegrityManager getInstance() {
        return IntegrityManager.Holder.instance;
    }

    private IntegrityManager() {
        mContext = ContextUtil.getContext();
    }

    private JSONObject getJSONObject(String jsonStr){
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        return jsonObject;
    }

    private boolean compareCRC(JSONObject jsonObject,String fileName, String crcStr) {
        if (jsonObject == null || TextUtils.isEmpty(fileName) || TextUtils.isEmpty(crcStr)) {
            return true;
        }
        String value = jsonObject.getString(fileName);
        if (TextUtils.isEmpty(value)){
            return true ;
        }else if (!TextUtils.isEmpty(value) && !value.equals(crcStr)){
            return false;
        }
        return true;
    }

    String checkIntegrity(String crcContent){
        try {
            ZipFile zip = new ZipFile(ContextUtil.getContext().getPackageCodePath());
            // 得到zip文件集合。枚举类型
            Enumeration zipEnumeration = zip.entries();
            JSONObject jsonObject = getJSONObject(crcContent);
            mModifiedFiles.clear();
            while (zipEnumeration.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) zipEnumeration.nextElement();
                String fileName = zipEntry.getName();
                if (fileName.endsWith(".so")||fileName.endsWith(".dex")||"AndroidManifest.xml".equals(fileName)) {
                    String value = String.valueOf(zipEntry.getCrc());
                    if (!compareCRC(jsonObject,fileName,value)){
                        mModifiedFiles.add(fileName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuffer buffer = new StringBuffer();
        for (String mModifiedFile : mModifiedFiles) {
            buffer.append(mModifiedFile).append(",");
        }
        return buffer.toString();
    }

}
