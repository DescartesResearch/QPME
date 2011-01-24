package de.tud.cs.simqpn.rt.framework.stats;

import java.util.ArrayList;
import java.util.List;


public class PlaceStatistics extends Statistics {
	
	public String type;
	private List<Statistics> colorStats = new ArrayList<Statistics>();
	
	public PlaceStatistics(String name, String type) {
		super(name);
		this.type = type;
	}
	
	public void addColorStats(Statistics stats) {
		colorStats.add(stats);
	}
	
	public int getColorCount() {
		return colorStats.size();
	}
	
	public Statistics getColorStats(int index) {
		return colorStats.get(index);
	}
	
	public List<Statistics> getColorStats() {
		return colorStats;
	}
	
	public String getType() {
		return type;
	}
	
	public static String getId(String type, String name) {
		return type + ":" + name;
	}
		
}
