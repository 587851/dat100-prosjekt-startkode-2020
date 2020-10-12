package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowRoute extends EasyGraphics {

	private static int MARGIN = 50;
	private static int MAPXSIZE = 800;
	private static int MAPYSIZE = 800;

	private GPSPoint[] gpspoints;
	private GPSComputer gpscomputer;
	
	public ShowRoute() {

		String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
		gpscomputer = new GPSComputer(filename);

		gpspoints = gpscomputer.getGPSPoints();

	}

	public static void main(String[] args) {
		launch(args);
	}

	public void run() {

		makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);

		showRouteMap(MARGIN + MAPYSIZE);
		
		showStatistics();
	}

	// antall x-pixels per lengdegrad
	public double xstep() {

		double maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
		double minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));

		double xstep = MAPXSIZE / (Math.abs(maxlon - minlon)); 

		return xstep;
	}

	// antall y-pixels per breddegrad
	public double ystep() {
	
		double ystep;
		
		double maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
		double minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));

		ystep = MAPYSIZE / (Math.abs(maxlat - minlat)); 

		return ystep;
	}

	public void showRouteMap(int ybase) {
		
		
		int[] xTab = new int[gpspoints.length];
		int[] yTab = new int[gpspoints.length];
		int minsteX = (int)( GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints))* xstep());
		int størsteY = (int)( GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints))* ystep());
		setColor(124,252,0);
		
		for(int v = 0; v < gpspoints.length; v++) {
			double breddegrad = gpspoints[v].getLatitude();
			double lengdegrad = gpspoints[v].getLongitude();
			xTab[v] = (int)(lengdegrad * xstep());
			yTab[v] = (int)(breddegrad * ystep());
			fillCircle(xTab[v] - minsteX  + MARGIN, størsteY - yTab[v] + MARGIN, 4);	
			
		}
		for(int v = 1; v < gpspoints.length; v++) {
			drawLine(xTab[v-1] - minsteX + MARGIN, størsteY - yTab[v-1] + MARGIN, xTab[v] - minsteX + MARGIN, størsteY - yTab[v] + MARGIN );
		}
	
	}

	public void showStatistics() {

		int TEXTDISTANCE = 20;

		setColor(0,0,0);
		setFont("Courier",12);
		
	    int y = TEXTDISTANCE;	
		drawString("Total Time     : " + GPSUtils.formatTime(gpscomputer.totalTime()) + " ", 25, y);
		y += TEXTDISTANCE;
		drawString("Total distance : " + GPSUtils.formatDouble(gpscomputer.totalDistance()/1000) + " km ", 25, y);
		y += TEXTDISTANCE;
		drawString("Total elevation: " + GPSUtils.formatDouble(gpscomputer.totalElevation())+ " m ", 25, y);
		y += TEXTDISTANCE;
		drawString("Max speed      : " + GPSUtils.formatDouble(gpscomputer.maxSpeed())+ " km/t ", 25, y);
		y += TEXTDISTANCE;
		drawString("Average speed  : " + GPSUtils.formatDouble(gpscomputer.averageSpeed())+ " km/t ", 25, y);
		y += TEXTDISTANCE;
		drawString("Energy         : " + GPSUtils.formatDouble(gpscomputer.totalKcal(80))+ " kcal ", 25, y);
	}

}
