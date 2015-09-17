package com.sbnnest.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class AUUtil {

	public static boolean assertPermission(Context ctx, String perm) {
		boolean is = true;
		int checkPermission = ctx.getPackageManager().checkPermission(perm,
				ctx.getPackageName());
		if (checkPermission != PackageManager.PERMISSION_GRANTED) {
			is = false;
			Toast.makeText(ctx, "Permission " + perm + " is required", Toast.LENGTH_LONG).show();
		}
		return is;
	}
}
