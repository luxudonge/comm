package com.sbnnest.util;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;


/**
 * 
 * 获取手机手机状态
 * 
 * @author Alex.Lu
 * @version V1.0
 */
public class PhoneStateUtil {

	private static final String TAG = "PhoneStateUtil";

	/**
	 * 
	 * 获取手机号 如果不可用会返回null 判断是否有READ_PHONE_STATE权限
	 * 
	 * @param context
	 * @return
	 */
	public static String getPhoneNumber(Context context) {
		String phoneNumber = null;
		if (AUUtil.assertPermission(context,Manifest.permission.READ_PHONE_STATE)) {
			TelephonyManager telephonyMgr = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			phoneNumber = telephonyMgr.getLine1Number();
		}
		return phoneNumber;
	}

	/**
	 * 获取手机的IMEI/MEID/ESN号
	 * 
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context) {
		String deviceId = null;
		if (AUUtil.assertPermission(context,Manifest.permission.READ_PHONE_STATE)) {
			TelephonyManager telephonyMgr = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			deviceId = telephonyMgr.getDeviceId();
		}
		return deviceId;
	}
	
	/**
	 * 中国为460
	 * 中国移动系统使用00、02、07，中国联通GSM系统使用01、06，中国电信CDMA系统使用03、05、电信4G使用11，中国铁通系统使用20
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context){
		String subscriberId = null;
		if (AUUtil.assertPermission(context,Manifest.permission.READ_PHONE_STATE)) {
			TelephonyManager telephonyMgr = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			subscriberId = telephonyMgr.getSubscriberId(); 
		}
		return subscriberId;
	}
	
	/**
	 * 
	 * 前六位运营商代码：中国移动的为：898600；898602 ，中国联通的为：898601，中国电信898603
	 * 
	 * @return
	 */
	public static String getICCID(Context context){
		String simSerialNumber = null;
		if(AUUtil.assertPermission(context,Manifest.permission.READ_PHONE_STATE)){
			TelephonyManager telephonyMgr = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			simSerialNumber = telephonyMgr.getSubscriberId(); 
		}
		return simSerialNumber;
	}

	/********************************************************************************
	 * 
	 * 获取手机唯一识别
	 * 
	 * @param ctx
	 * @param ignoreBuggyAndroidID
	 * @return
	 * @throws DeviceIDException
	 */
	public static String getDeviceIdentifier(Context ctx,
			boolean ignoreBuggyAndroidID) throws DeviceIDException {
		String result = uuid;
		AUUtil.assertPermission(ctx,Manifest.permission.READ_PHONE_STATE);
		AUUtil.assertPermission(ctx,Manifest.permission.ACCESS_WIFI_STATE); // I guess
		AUUtil.assertPermission(ctx,Manifest.permission.BLUETOOTH);
		if (result == null || "".equals(result)) {
			synchronized (PhoneStateUtil.class) {
				result = uuid;
				if (result == null || "".equals(result)) {
					for (IDs id : IDs.values()) {
						try {
							result = uuid = id.getId(ctx);
						} catch (DeviceIDNotUniqueException e) {
							if (!ignoreBuggyAndroidID)
								throw new DeviceIDException(e);
						}
						if (result != null && (!"".equals(result)))
							return result;
					}
					throw new DeviceIDException();
				}
			}
		}
		return result;
	}

	private static enum IDs {
		TELEPHONY_ID {

			@Override
			String getId(Context ctx) {
				// TODO : add a SIM based mechanism ? tm.getSimSerialNumber();
				final TelephonyManager tm = (TelephonyManager) ctx
						.getSystemService(Context.TELEPHONY_SERVICE);
				if (tm == null) {
					Log.w(TAG,"Telephony Manager not available");
					return null;
				}
				return tm.getDeviceId();
			}
		},
		ANDROID_ID {

			@Override
			String getId(Context ctx) throws DeviceIDException {
				// no permission needed !
				final String andoidId = Secure.getString(
						ctx.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
				if (BUGGY_ANDROID_ID.equals(andoidId)) {
					Log.w(TAG,ANDROID_ID_BUG_MSG);
					throw new DeviceIDNotUniqueException();
				}
				return andoidId;
			}
		},
		WIFI_MAC {

			@Override
			String getId(Context ctx) {
				WifiManager wm = (WifiManager) ctx
						.getSystemService(Context.WIFI_SERVICE);
				if (wm == null) {
					Log.w(TAG,"Wifi Manager not available");
					return null;
				}
				// getMacAddress() has no java doc !!!
				return wm.getConnectionInfo().getMacAddress();
			}
		},
		BLUETOOTH_MAC {

			@Override
			String getId(Context ctx) {
				BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
				if (ba == null) {
					Log.w(TAG,"Bluetooth Adapter not available");
					return null;
				}
				
				return ba.getAddress();
			}
		}
		// TODO PSEUDO_ID
		// http://www.pocketmagic.net/2011/02/android-unique-device-id/
		;

		static final String BUGGY_ANDROID_ID = "9774d56d682e549c";

		abstract String getId(Context ctx) throws DeviceIDException;

	}

	/** @see http://code.google.com/p/android/issues/detail?id=10603 */
	private static final String ANDROID_ID_BUG_MSG = "The device suffers from "
			+ "the Android ID bug - its ID is the emulator ID : "
			+ IDs.BUGGY_ANDROID_ID;
	private static volatile String uuid; // volatile needed - see EJ item 71

	// =========================================================================
	// Exceptions
	// =========================================================================
	public static class DeviceIDException extends Exception {

		private static final long serialVersionUID = -8083699995384519417L;
		private static final String NO_ANDROID_ID = "Could not retrieve a "
				+ "device ID";

		public DeviceIDException(Throwable throwable) {
			super(NO_ANDROID_ID, throwable);
		}

		public DeviceIDException(String detailMessage) {
			super(detailMessage);
		}

		public DeviceIDException() {
			super(NO_ANDROID_ID);
		}
	}

	public static final class DeviceIDNotUniqueException extends
			DeviceIDException {

		private static final long serialVersionUID = -8940090896069484955L;

		public DeviceIDNotUniqueException() {
			super(ANDROID_ID_BUG_MSG);
		}
	}

}
