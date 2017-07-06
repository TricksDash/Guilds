package com.elderrealm.main.utils;

public class TimeUtil {

	public static String formatIntoHHMMSS(long secondsLeft) {
		long remainder = secondsLeft % 3600;
		long minutes = remainder / 60;
		long seconds = remainder % 60;

		return new StringBuilder().append(minutes).append(":").append(seconds < 10 ? "0" : "").append(seconds)
				.toString();
	}

}
