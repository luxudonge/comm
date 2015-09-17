package com.sbnnest.util;

import android.util.Log;

public class AUConfig {

	public static boolean DEBUG = true;
	
	public static void LogI(String tag,String msg){
		if(DEBUG)
			Log.i(tag, msg);
	}
	
	public static void LogE(String tag,String msg){
		if(DEBUG)
			Log.e(tag, msg);
	}
}
