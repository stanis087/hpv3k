package com.android.projecte.townportal;

import android.annotation.SuppressLint;
import android.util.Log;

public class ThemeUtilities {

	private static int textColor = R.color.beach_theme_text, bgColor = R.color.beach_theme_bg;
	public final static int THEME_BEACH = 0;
	public final static int THEME_FSU = 1;
	public final static int THEME_ANDROID = 2;

	
	@SuppressLint("ResourceAsColor")
	public static void setTheme(int themeID){
		switch(themeID)
		{
		case THEME_BEACH:
			setTextColor(R.color.beach_theme_text);
			setBgColor(R.color.beach_theme_bg);
			
			break;
		case THEME_FSU:
			setTextColor(R.color.fsu_theme_text);
			setBgColor(R.color.fsu_theme_bg);
			Log.i("ThemeUtilities", "Fsu theme selected");
			break;
		case THEME_ANDROID:
			break;
		default:
			break;
		}
	}
	

	public static int getTextColor() {
		return textColor;
	}

	public static void setTextColor(int textColor) {
		ThemeUtilities.textColor = textColor;
	}

	public static int getBgColor() {
		return bgColor;
	}

	public static void setBgColor(int bgColor) {
		ThemeUtilities.bgColor = bgColor;
	}
}
