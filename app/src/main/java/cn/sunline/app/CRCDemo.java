package cn.sunline.app;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class CRCDemo {

	public static void main(String[] args) throws IOException {
		String path = "/Users/laijp/Downloads/helloTiny-release.apk";
		ZipFile zip = new ZipFile(path);
		Enumeration zipEnumeration = zip.entries();// 得到zip文件集合。枚举类型
		while (zipEnumeration.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) zipEnumeration.nextElement();
			JSONObject jsonObject = new JSONObject();
			if (zipEntry.getName().endsWith(".so") || zipEntry.getName().endsWith(".dex")
					|| zipEntry.getName().equals("AndroidManifest.xml")) {
				jsonObject.put(zipEntry.getName(),zipEntry.getCrc());
				System.out.println(String.format("result:%s",jsonObject.toJSONString()));
			}
		}
	}

}
