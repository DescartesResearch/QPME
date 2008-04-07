package de.tud.cs.qpe.utils;

import java.util.Random;

import org.eclipse.swt.graphics.RGB;

public class ColorHelper {
	protected static Random generator = new Random();
	
	public static RGB getRGBFromString(String code) {
		if(code == null) {
			return null;
		}
		String rHex = code.substring(1, 3);
		String gHex = code.substring(3, 5);
		String bHex = code.substring(5, 7);
		
		int rInt = Integer.valueOf(rHex, 16).intValue();
		int gInt = Integer.valueOf(gHex, 16).intValue();
		int bInt = Integer.valueOf(bHex, 16).intValue();
		
		return new RGB(rInt, gInt, bInt);
	}
	
	public static String getStringfromRGB(RGB rgb) {
		String rHex = Integer.toHexString(rgb.red);
		if(rHex.length() == 1) {
			rHex = "0" + rHex;
		}
		String gHex = Integer.toHexString(rgb.green);
		if(gHex.length() == 1) {
			gHex = "0" + gHex;
		}
		String bHex = Integer.toHexString(rgb.blue);
		if(bHex.length() == 1) {
			bHex = "0" + bHex;
		}
		return "#" + rHex + gHex + bHex;
	}
	
	public static String generateRandomColor() {
		int red = generator.nextInt() % 256;
		int green = generator.nextInt() % 256;
		int blue = generator.nextInt() % 256;
		RGB newRgb = new RGB((red >= 0) ? red : -red, (green >= 0) ? green : -green, (blue >= 0) ? blue : -blue);
		return ColorHelper.getStringfromRGB(newRgb);
	}
}
