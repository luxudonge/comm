package com.sbnnest.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;


/**
 * 
 * 获取应用信息
 * 
 * @author Alex.Lu
 * @version V1.0
 */
public class AppUtil {

	
	/**
	 * 
	 * 获取版本名称
	 * 
	 * @param context
	 * @return
	 */
	public String getVersionName(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),0);
			String version = info.versionName;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	
	/**
	 * 
	 * 获取版本号
	 * 
	 * @param context
	 * @return
	 */
	public int getVersionCode(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),0);
			int version = info.versionCode;
			return version;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 
	 * 获取应用包名
	 * 
	 * @param context
	 * @return
	 */
	public String getPackageName(Context context) {
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = manager.getPackageInfo(context.getPackageName(),0);
			String packageName = info.packageName;
			return packageName;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
