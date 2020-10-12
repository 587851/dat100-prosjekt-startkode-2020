package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	
	/*Konstruktør som skal opprette ein tabell av GPS punkter med størrelsen gitt ved parameteren n 
	  og antall er 0(Første element skal inn på posisjon 0*/
	public GPSData(int n) {
         antall = 0;
         gpspoints	 = new GPSPoint[n];
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	
	//Setter inn et GPS punkt i gpspoints-tabellen hvis det er plass
	protected boolean insertGPS(GPSPoint gpspoint) {

		boolean inserted = false;

		if (antall < gpspoints.length) {
			gpspoints[antall] = gpspoint;			
			inserted = true;
			antall++;
		}
		return inserted;
	}

	//Metoden konverterer data, oppretter eit nytt GPSpoint-objekt og setter det inn i ein tabell.
	public boolean insert(String time, String latitude, String longitude, String elevation) {

		GPSPoint gpspoint;
        gpspoint = GPSDataConverter.convert(time, latitude, longitude, elevation);
		boolean instert = insertGPS(gpspoint);
		return instert;
	}
	
	
	
    //Metode for å skrive ut all data i ein gpspoints-tabell
	public void print() {

		System.out.println("====== Konvertert GPS Data - START ======");

		for(int i = 0; i<gpspoints.length; i++) {
			String punkt = gpspoints[i].toString();
			System.out.print(punkt);
		}
		
		System.out.println("====== Konvertert GPS Data - SLUTT ======");

	}
}
