package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;
import java.util.Locale;

import java.util.Locale;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {
	
	
    //Metode som finner største tall i ein double tabell med flyttall.
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

	
	//Metode som finner minste tall i ein double tabell med flyttall.
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

	
	//Metode som tar ein tabell med GPS punkter og returnerer ein double tabell med breddegradene fra GPS-punktene
	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double[] latitudes = new double[gpspoints.length];
		
		for(int v = 0; v < gpspoints.length; v++) {
			latitudes[v] = gpspoints[v].getLatitude();
		}
		
		return latitudes;
	}

	
	//Metode som tar ein tabell med GPS punkter og returnerer ein double tabell med lengdegradene fra GPS-punktene
	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double[] longitudes = new double[gpspoints.length];
		for(int v = 0; v < gpspoints.length; v++) {
			longitudes[v] = gpspoints[v].getLongitude();
		}
		
		return longitudes;

	}
	
	
	private static int R = 6371000; // jordens radius
    
	//Metode som finner avstanden i meter mellom to GPS punkter
	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d, a, c;
		double latitude1, longitude1, latitude2, longitude2, latitudeSum, longitudeSum;
        
		latitude1 = toRadians(gpspoint1.getLatitude());
		latitude2 = toRadians(gpspoint2.getLatitude());
		longitude1 = toRadians(gpspoint1.getLongitude());
		longitude2 = toRadians(gpspoint2.getLongitude());
		latitudeSum = latitude2 - latitude1;
		longitudeSum = longitude2 - longitude1;
		
		//Haversine-formlen
		a = pow(sin(latitudeSum/2), 2) + (cos(latitude1) * cos(latitude2) * pow(sin(longitudeSum/2), 2)); 
        c = 2*atan2(sqrt(a), sqrt(1-a));
        d = (R * c);
        
        return d;
	}

	
	//Metode som finner gjennomsnitthastigheten i km/t mellom to GPS punkt
	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {
	
		double distance = distance(gpspoint1, gpspoint2);
		int sec1 = gpspoint1.getTime();
		int sec2 = gpspoint2.getTime();
		int tid = sec2-sec1;
		
		double speed = (distance/tid)*3.6;
		return speed;
              

	}

	
	//Metode som returnerer ein streng på formatet hh:mm:ss, der parameteren secs er sekunder fra midnatt. 
	public static String formatTime(int secs) {
        
		String TIMESEP = ":";
		
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
		
		
		String tidstr = "  " + timeStr + TIMESEP + minStr + TIMESEP + sekStr;
		
		return tidstr;

		

	}
	
	
	private static int TEXTWIDTH = 10;

	/*Metode som runder av eit flyttall til to desimaler, setter resultatet inn i ein streng og fyller på med mellomrom slik at 
	  lengden på strengen blir 10*/
	public static String formatDouble(double d) {

		String str = String.format(Locale.US, "%.2f", d);
		String str2 = String.format("%10s", str);
		return str2;

		
	}
}
