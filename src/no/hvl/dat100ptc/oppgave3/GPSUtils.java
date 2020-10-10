package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;
import java.util.Locale;

import java.util.Locale;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min; 
		
		min = da[0];
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		
		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] latitudes = new double[gpspoints.length];
		for(int v = 0; v < gpspoints.length; v++) {
			latitudes[v] = gpspoints[v].getLatitude();
		}
		
		return latitudes;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] longitudes = new double[gpspoints.length];
		for(int v = 0; v < gpspoints.length; v++) {
			longitudes[v] = gpspoints[v].getLongitude();
		}
		
		return longitudes;

	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d, a, c;
		double latitude1, longitude1, latitude2, longitude2, latitudeSum, longitudeSum;
        
		latitude1 = Math.toRadians(gpspoint1.getLatitude());
		latitude2 = Math.toRadians(gpspoint2.getLatitude());
		longitude1 = Math.toRadians(gpspoint1.getLongitude());
		longitude2 = Math.toRadians(gpspoint2.getLongitude());
		latitudeSum = latitude2 - latitude1;
		longitudeSum = longitude2 - longitude1;
		
		a = Math.pow(Math.sin(latitudeSum/2), 2) + (Math.cos(latitude1) * Math.cos(latitude2) * Math.pow(Math.sin(longitudeSum/2), 2)); 
        c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        d = (R * c);
        return d;
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double distance = distance(gpspoint1, gpspoint2);
		int sec1 = gpspoint1.getTime();
		int sec2 = gpspoint2.getTime();
		int tid = sec2-sec1;
		
		double speed = (distance/tid)*3.6;
		return speed;
              

	}

	public static String formatTime(int secs) {
        
		int time = secs/3600;
		int min = (secs%3600)/60;
		int sek = (secs%3600)%60;
		String nul = "0";
		String timeStr = Integer.toString(time);
		String minStr = Integer.toString(min);
		String sekStr = Integer.toString(sek);
		if (timeStr.length() == 1) {
			timeStr = nul + timeStr;
		}
		if (minStr.length() == 1) {
			minStr = nul + minStr;
		}
		if (sekStr.length() == 1) {
			sekStr = nul + sekStr;
		}
		
		String kolon = ":";
		
		String tidstr = "  " + timeStr + kolon + minStr + kolon + sekStr;
		
		return tidstr;

		

	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str = String.format(Locale.US, "%.2f", d);
		String str2 = String.format("%10s", str);
		return str2;

		
	}
}
