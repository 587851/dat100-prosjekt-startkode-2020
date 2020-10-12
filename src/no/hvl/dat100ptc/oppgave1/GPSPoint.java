package no.hvl.dat100ptc.oppgave1;

import no.hvl.dat100ptc.TODO;

public class GPSPoint {

	private int time;
	private double latitude;
	private double longitude;
	private double elevation;
	
		
	// Konstruktør for GPSPoint
	public GPSPoint(int time, double latitude, double longitude, double elevation) {
		this.time = time;
		this.latitude = latitude;
		this.longitude = longitude;
		this.elevation = elevation;		
	}
	
	
    //Get og set metoder for time, latitude, longitude, elevation
	public int getTime() {	
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public double getLatitude() {
        return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}
	
	
	//Metode som returnerer ein strengrepresentasjon av eit GPSpoint-objekt på formen 1 (2.0,3.0) 5.0\n
	public String toString() {
		return time + " (" + latitude + "," + longitude + ") " + elevation + "\n";
	}
}
