package com.sbnnest.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VerifyUtil {

	
	public static String checkPhoneNumberType(String input) {
		 String result = "";
		 String yidongRegex = "1(3[4-9]|47|5[0-247-9]|8[23478]|78)[0-9]{8}";//移动
		 String liantongRegex = "1(3[0-2]|5[56]|8[56]|76)[0-9]{8}";//联通
		 String dianxinRegex = "1([35]3|8[019]|77)[0-9]{8}";//电信
		 if(Pattern.compile(yidongRegex).matcher(input).matches()) {
			 result = "cmcc";
		 } else if(Pattern.compile(liantongRegex).matcher(input).matches()) {
			 result = "uni";
		 } else if(Pattern.compile(dianxinRegex).matcher(input).matches()) {
			 result = "ctc";
		 } else {
			 result = "other";
		 }
		 return result;
	 }
	
	// 判断手机格式是否正确

	public boolean isMobileNO(String mobiles) {

		Pattern p = Pattern

		//.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		.compile("^(1(([35][0-9])|(47)|(84)|[78][01256789]))\\d{8}$");

		Matcher m = p.matcher(mobiles);

		return m.matches();

	}

	// 判断是否全是数字

	public boolean isNumeric(String str) {

		Pattern pattern = Pattern.compile("[0-9]*");

		Matcher isNum = pattern.matcher(str);

		if (!isNum.matches()) {

			return false;

		}

		return true;

	}

}
