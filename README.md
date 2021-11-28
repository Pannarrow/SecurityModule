# 如何使用
----
## 添加maven仓库  
在根目录的build.gradle中添加maven地址  

```
allprojects {
	repositories {
		...
		maven { url 'http://p.mtiny.cn:8091/artifactory/libs-release-local' }
		...
	}
}
```
在项目的build.gradle中添加引用  
  
```
dependencies {
	...
	implementation 'cn.sunline.component:security:0.0.1@aar'
	implementation 'com.alibaba:fastjson:1.2.56'
	...
}
```  
  
## 使用
### 在application中初始化  
需要在applcation中初始化：SecurityManager.getInstance().init(content);  
  
```
public class SecurityApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //初始化
        SecurityManager.getInstance().init(this);
        //设置debug模式
        SecurityManager.getInstance().setDebug(true);
    }
}
```    
使用反调试activity  
  
```
public class MainActivity extends SecurityActivity {

    @Override
    public boolean limitScreenShot() {
    	 //是否后台模糊	
        return true;
    }

    @Override
    public boolean antiDebugged() {
    	//是否开启反调试检测
        return true;
    }

    @Override
    public void finishWithAntiDebugged() {
		//检测到调试后actvity关闭逻辑
    }

}
```   
### 开启JWDP服务

```
SecurityManager.getInstance().startJWDP();
```  
### 获取手机当前的安全情况

```
AntiResult antiResult = SecurityManager.getInstance().getAntiResult(SHA1Str, CRCJsonStr);
```  
方法getAntiResult说明：  

方法  | 说明
------------- | -------------
getAntiResult（SHA1Str, CRCJsonStr）  | SHA1Str 为证书的sha1值，CRCJsonStr为需要检测的文件的crc16值的json集合
getAntiResult（VersionStr，SHA1Str, CRCJsonStr） | VersionStr为Android版本号，如：4.4；5.0；6.0等  

返回对象AntiResult  

字段  | 类型 |说明
---- | ---- | ----
code | int | 错误code码
result | String | 错误说明  
### 其他方法  
#### 检测屏幕锁  
  
```
SecurityManager.getInstance().checkLockScreen();
```  
返回bool值，是否开启屏幕锁  

#### 检测是否root  

  
```
SecurityManager.getInstance().checkRoot(version);
```  
version为Android版本号，如：4.4；5.0；6.0等  
返回值为bool  

#### 检测是否是被bebug调试

```
SecurityManager.getInstance().antiDebugged();
```  
返回bool值  

#### 限制截图  
```
SecurityManager.getInstance().limitScreenShot(limit，activity);
``` 
参数：limit 为bool类型，是否限制；
## 错误码表  
  
错误码 | 英文说明 | 中文说明 
---- | ---- | ---- 
40001 | the app is not official package , please download official package | 打包证书被篡改，非正式包
40002 | the stack has suspicious method calls. | app正在被调式 
40003 | Jailbreak/Root device is not allowed to use Kept app due to security purpose. | 检测到安装的手机被root
40004 | Emulator is not allowed to use app due to security purpose. | 检测到运行环境为模拟器
40005 | To use this app you need to turn on lockscreen on your device setting. | 手机没有测试屏幕锁 
40006 | %s has been modified | 检测到app包的文件被篡改