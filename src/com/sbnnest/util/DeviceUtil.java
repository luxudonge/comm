package com.sbnnest.util;

import android.content.Context;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 
 * 
 * 获取设备信息
 * 
 * @author Alex.Lu
 * @version V1.0
 */
public class DeviceUtil {

	
	private static final String TAG = "DisplayUtil";
	
	/**
	 * 获取屏幕信息
	 * 
	 * @param context
	 * @return
	 */
	public static DisplayMetrics getDisplayMetrics(Context context){
		DisplayMetrics  dm = new DisplayMetrics(); 
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		AUConfig.LogI(TAG, "widthPixels:" + dm.widthPixels 
				+ "  heightPixels:"+dm.heightPixels 
				+ "  density:"+dm.density
				+ "  densityDpi:"+dm.densityDpi
				+ "  scaledDensity:"+dm.scaledDensity);
		return dm;
	}
	
	/**
	 * 
	 * 判断是否是自动调节亮度
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isAutomaticBrightness(Context context) {
		boolean automaticBrightness = false;
		try {
			automaticBrightness = Settings.System.getInt(
					context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
			AUConfig.LogI(TAG, "brightness automatic:" + automaticBrightness);
		} catch (SettingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AUConfig.LogE(TAG, "brightness automatic error:SettingNotFoundException");
		}
		return automaticBrightness;
	}
	
	/**
	 * 获取亮度(0~255)
	 * @param context
	 * @return
	 */
	public static int getBrightness(Context context) {
		int brightness = 255;
		try {
			brightness = Settings.System.getInt(
					context.getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS);
			AUConfig.LogI(TAG, "brightness:" + brightness);
		} catch (SettingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AUConfig.LogE(TAG, "get brightness error:SettingNotFoundException");
		}
		return brightness;
	}
	
	
	
	
}
