package de.tud.cs.qpe.editors.query.model;

/**
 * These are the available metrics for visualization.
 * 
 * Each metric has a human readable name and and an abbreviated type name
 * by which it is identified for example inside an XML document.
 * 
 *  @author Frederik Zipp
 */
public enum Metric {
	QUEUE_UTIL_QP1("Queue utilization due to this place", "queueUtilQPl"),
	TK_OCP("Token Occupancy", "tkOcp", true),
	ARRIV_THR_PUT("Arrival Throughput", "arrivThrPut"),
	DEPT_THR_PUT("Departure Throughput", "deptThrPut"),
	MIN_TK_POP("Minimum Token Population", "minTkPop"),
	MAX_TK_POP("Maximum Token Population", "maxTkPop"),
	MEAN_TK_POP("Mean Token Population", "meanTkPop"),
	TK_COL_OCP("Token Color Occupancy", "tkColOcp"),
	MEAN_ST("Mean Token Residence Time", "meanST"),
	ST_DEV_ST("Standard Deviation Token Residence Time", "stDevST"),
	MIN_ST("Minimum Token Residence Time", "minST"),
	MAX_ST("Maximum Token Residence Time", "maxST"),
	CONFIDENCE_INTERVAL("Confidence Interval", "stdStateMeanST"),
	TOT_ARRIV_THR_PUT("Total Arrival Throughput", "totArrivThrPut", true),
	TOT_DEPT_THR_PUT("Total Departure Throughput", "totDeptThrPut", true),
	MEAN_TOT_TK_POP("Mean Total Token Population", "meanTotTkPop", true),
	QUEUE_UTIL("Queue Utilization", "queueUtil", true);
	
	private final String name;
	private final String type;
	private final boolean direct;

	private Metric(String name, String id) {
		this(name, id, false);
	}

	private Metric(String name, String id, boolean direct) {
		this.name = name;
		this.type = id;
		this.direct = direct;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getType() {
		return this.type;
	}
	
	public boolean isDirect() {
		return this.direct;
	}
	
	public static Metric fromType(String type) {
		for (Metric metric : values()) {
			if (metric.type.equals(type)) {
				return metric;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
