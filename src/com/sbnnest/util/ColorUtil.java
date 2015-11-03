package com.sbnnest.util;

public class ColorUtil {

	/**
	 * 
	 * 
	 * @param r (0,255)
	 * @param g (0,255)
	 * @param b (0,255)
	 * @return
	 */
	public static int RGBToColor(int r,int g,int b){
		return ARGBToColor(0xff, r, g, b);
	}
	
	/**
	 * 
	 * @param a (0,255)
	 * @param r (0,255)
	 * @param g (0,255)
	 * @param b (0,255)
	 * @return
	 */
	public static int ARGBToColor(int a,int r,int g,int b){
		return (((a * 0xff) + r ) * 0xff + g) * 0xff + b;
	}
}
