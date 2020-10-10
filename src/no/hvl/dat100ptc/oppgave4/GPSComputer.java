package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
	
	private GPSPoint[] gpspoints;
	
	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}
	
	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}
	
	// beregn total distances (i meter)
	public double totalDistance() {

		double distance= 0;
        for(int v = 1; v < gpspoints.length; v++) {
        	distance += GPSUtils.distance(gpspoints[v-1], gpspoints[v]);
        }		
		return distance;
	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		for(int v = 1; v <gpspoints.length; v++) {
			double hoyde1 = gpspoints[v-1].getElevation();
			double hoyde2 = gpspoints[v].getElevation();
			if( hoyde1 < hoyde2) {
				elevation += hoyde2 - hoyde1;
			}
		}
		return elevation;
	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {

		int totalTid = 0;

		for(int v = 1; v <gpspoints.length; v++) {
			double tid1 = gpspoints[v-1].getTime();
			double tid2 = gpspoints[v].getTime();			
			totalTid += tid2 - tid1;
		}
		return totalTid;
	}
		
	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {
		
		double[] gjennomsnitt = new double[gpspoints.length-1];
		for(int v = 1; v < gpspoints.length; v++) {
			gjennomsnitt[v-1] = GPSUtils.speed(gpspoints[v-1], gpspoints[v]);
		}
		
        return gjennomsnitt;
	}
	
	public double maxSpeed() {
		
		double maxspeed = 0;
		double[]speeds = speeds();
		
		for(int v = 0; v<speeds.length; v++) {
			if(speeds[v] > maxspeed) {
				maxspeed = speeds[v];
			}			
		}
		return maxspeed;
	}

	public double averageSpeed() {

		double sumFart = 0;
		double antall = 0;
		double sumTid = 0;
		
		int[] tabellTid = new int[gpspoints.length-1];	
		for(int v = 1; v < gpspoints.length;v++) {
			int tid1 = gpspoints[v-1].getTime();
			int tid2 = gpspoints[v].getTime();
			tabellTid[v-1] = tid2-tid1;
		}
			
		double []speed = speeds();
		for(int v = 0; v<speed.length; v++) {
			sumFart += speed[v] * tabellTid[v];
			sumTid += tabellTid[v];
			antall++;
			}		
		double average = sumFart / sumTid;	
		return average;
	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling,
	 * general 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0
	 * bicycling, 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9
	 * mph, racing or leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph,
	 * racing/not drafting or >19 mph drafting, very fast, racing general 12.0
	 * bicycling, >20 mph, racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjores med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal = 0;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)	
		double met = 0;		
		double speedmph = speed*MS;
		double time = secs;
		
		if(speedmph < 10) {
			met = 4.0;
		}
		else if (speedmph >= 10 && speedmph < 12){
			met = 6.0;
		}
        else if (speedmph >= 12 && speedmph < 14){
        	met = 8.0;
		}
        else if (speedmph >= 14 && speedmph < 16){
        	met = 10.0;
        }
        else if (speedmph >= 16 && speedmph < 20){
        	met = 12.0;	
        }
        else if (speedmph >= 20){
        	met = 16.0;	       
         }
		
		kcal = (met * weight * time)/3600;
		return kcal;
		
	}

	public double totalKcal(double weight) {

		double totalkcal = 0 ;
		
		int[] tabellTid = new int[gpspoints.length-1];	
		for(int v = 1; v < gpspoints.length;v++) {
			int tid1 = gpspoints[v-1].getTime();
			int tid2 = gpspoints[v].getTime();
			tabellTid[v-1] = tid2-tid1;
		}
		
		double[]tabSpeed= speeds();				
		for(int v = 0; v < tabellTid.length;v++) {
			totalkcal = totalkcal + this.kcal(weight, tabellTid[v], tabSpeed[v]);
		}
		return totalkcal;
		
	}
	
	private static double WEIGHT = 80.0;
	
	public void displayStatistics() {

		System.out.println("==============================================");
		
		System.out.println("Total Time     : " + GPSUtils.formatTime(totalTime()));
		System.out.println("Total distance : " + GPSUtils.formatDouble(totalDistance()/1000) + " km");
		System.out.println("Total elevation: " + GPSUtils.formatDouble(totalElevation())+ " m");
		System.out.println("Max speed      : " + GPSUtils.formatDouble(maxSpeed())+ " km/t");
		System.out.println("Average speed  : " + GPSUtils.formatDouble(averageSpeed())+ " km/t");
		System.out.println("Energy         : " + GPSUtils.formatDouble(totalKcal(WEIGHT))+ " kcal");
		 
		

		
		
	}

}
