package cn.sunline.security.manager;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

import cn.sunline.security.util.ContextUtil;

/**
 * @Package cn.sunline.security.manager
 * @Author laijp
 * @Date 2020-02-06
 */
class RawManager {
    //"\u0072\u0041\u0057";
    private final int RAW = 724157;
    //"\u0063\u0052\u0063";
    static final int CRC = 635263;
    //"\u0053\u0068\u0061";
    static final int SHA = 536861;

    private Context context;

    private static class Holder {
        private static final RawManager instance = new RawManager();
    }

    static RawManager getInstance() {
        return RawManager.Holder.instance;
    }

    private RawManager() {
        context = ContextUtil.getContext();
    }

    String getData(int unicode){
        String name = revert(unicode);
        String raw = revert(RAW);
        InputStream inputStream = null;
        try {
            int id = context.getResources().getIdentifier(name, raw, context.getPackageName());
            inputStream = context.getResources().openRawResource(id);
            int size = inputStream.available();
            byte[] bytes = new byte[size];
            inputStream.read(bytes);
            String string = new String(bytes);
            return string;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    private static String revert(int code){
        StringBuffer buffer = new StringBuffer();
        String unicode = String.valueOf(code);
        int index = String.valueOf(code).length()/2;
        for (int i = 0; i < index; i++) {
            String hex = unicode.substring(i * 2, (i + 1) * 2);
            int data = Integer.parseInt(hex, 16);
            buffer.append((char) data);
        }
        return buffer.toString().toLowerCase();
    }

//    /**
//     * unicode 转字符串
//     */
//    private String revert(String unicode) {
//        StringBuffer buffer = new StringBuffer();
//        String[] hex = unicode.split("\\\\u");
//        for (int i = 1; i < hex.length; i++) {
//            // 转换出每一个代码点
//            int data = Integer.parseInt(hex[i], 16);
//            // 追加成string
//            buffer.append((char) data);
//        }
//        return buffer.toString();
//    }

    /**
     * 字符串转换unicode
     */
//    private String convert(String string) {
//
//        StringBuffer unicode = new StringBuffer();
//
//        for (int i = 0; i < string.length(); i++) {
//
//            // 取出每一个字符
//            char c = string.charAt(i);
//
//            // 转换为unicode
//            unicode.append(String.format("\\u%04x", Integer.valueOf(c)));
//        }
//
//        return unicode.toString();
//    }

//    public static void main(String... arg){
//        int RAW = 724157;
//        String s = revert(RAW);
//        System.out.println(s);
//    }
}
