package qpn.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class QPNGenerator {

	static String version = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n";
	static String netStart = "<net qpme-version=\"2.1.0.qualifier\">\n";
	static final String colorID = "_123";
	static String colorDefinition = "  <colors>\n    <color id=\"" + colorID
			+ "\" name=\"class 1\" real-color=\"#c1574f\"/>\n  </colors>\n";

	private static final int x_distance = 50;
	private static final int y_distance = 150;


	/**Parameters*/
	private static String configuration = "example_config";
	private static int numLanes = 3;
	private static String rampUpLen = "10";
	private static String runLen = "4.0E4";
	private static final int numPlacesPerLane = 10;
	private static final int numTransitionsPerLane = numPlacesPerLane; // - 1;
	static final int statsLevel = 5;

	static String netEnd = "\n  <meta-attributes>\n     <meta-attribute xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"simqpn-configuration\" id=\"_1375427783696\" scenario=\"1\" stopping-rule=\"FIXEDLEN\" time-before-initial-heart-beat=\"100000\" time-between-stop-checks=\"100000\" seconds-between-stop-checks=\"60\" seconds-between-heart-beats=\"60\" output-directory=\".\" configuration-name=\""+configuration+"\" ramp-up-length=\""+rampUpLen+"\" verbosity-level=\"1\" total-run-length=\""+runLen +"\"></meta-attribute>\n  </meta-attributes>\n</net>";

	public static void main(String[] args) {
		String path = System.getProperty("user.dir");
		path = path.split("qpme-core")[0];
		writeToFile(path+"\\generated"+numLanes+"x"+numPlacesPerLane+".qpe", generateNetString());

	}
	
	public static String generateNetString(){
		StringBuffer sb = new StringBuffer();
		sb.append(version);
		sb.append(netStart);
		sb.append(colorDefinition);
		setQueues(sb, numLanes, numPlacesPerLane);
		setPlaces(sb, numLanes, numPlacesPerLane);
		setTransitions(sb, numLanes, numPlacesPerLane);
		setConnections(sb, numLanes);
		sb.append(netEnd);
		return sb.toString();
	}

	private static void setQueues(StringBuffer sb, int numLanes,
			int numPlacesPerLane) {
		// token generator
		sb.append("  <queues>\n");
		sb.append("    <queue strategy=\"FCFS\" number-of-servers=\"1\" id=\"_q"
				+ "_generator"+ "\" name=\"generator"+ "\"/>\n");
		// lanes
		for (int i = 0; i < numLanes; i++) {
			for (int j = 0; j < numPlacesPerLane; j++) {
				int id = (i * numPlacesPerLane + j);
				sb.append("    <queue strategy=\"FCFS\" number-of-servers=\"1\" id=\"_q"
						+ id + "\" name=\"Q" + id + "\"/>\n");
			}
		}
		sb.append("  </queues>\n");
	}

	private static void setPlaces(StringBuffer sb, int numLanes,
			int numPlacesPerLane) {
		sb.append("  <places>\n");
		int x;
		int y;
		String qPlaceId;
		String qPlaceName;

		setPlace(sb, x_distance/2, y_distance/2, "_p_start", "start" + "");
		setQueuePlace(sb, 4*x_distance, y_distance/2, "_p_generator", "token generator", "_q_generator");

		for (int i = 0; i < numLanes; i++) {
			// lane places
			for (int j = 0; j < numPlacesPerLane; j++) {
				x = x_distance + i * x_distance;
				y = y_distance + 75 + j * y_distance;
				qPlaceId = "_p" + (i * numPlacesPerLane + j);
				qPlaceName = "p" + (i * numPlacesPerLane + j);
				String queueId = "_q" + (i * numPlacesPerLane + j);

				setQueuePlace(sb, x, y, qPlaceId, qPlaceName, queueId);
			}
		}
		sb.append("  </places>\n");
	}

	private static void setPlace(StringBuffer sb, int x, int y, String id,
			String name) {
		sb.append("     <place xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" id=\""
				+ id
				+ "\" departure-discipline=\"NORMAL\" xsi:type=\"ordinary-place\" name=\""
				+ name + "\">\n");
		sb.append("      <meta-attributes>\n");
		sb.append("        <meta-attribute xsi:type=\"location-attribute\" location-x=\""
				+ x + "\" location-y=\"" + y + "\"/>\n");
		sb.append("<meta-attribute xsi:type=\"simqpn-place-configuration\" id=\"_1377495250077\" statsLevel=\""+statsLevel+"\" configuration-name=\""+configuration +"\"/>\n");
		sb.append("      </meta-attributes>\n");
		sb.append("      <color-refs>\n");
		sb.append("        <color-ref color-id=\"" + colorID
				+ "\" initial-population=\"0\" maximum-capacity=\"0\" id=\""
				+ id + colorID + "\" xsi:type=\"ordinary-color-reference\">\n");
		sb.append("          <meta-attributes>\n");
		sb.append("            <meta-attribute xsi:type=\"simqpn-batch-means-color-configuration\" configuration-name=\""+configuration+"\" id=\"_1377508294766\" signLev=\"0.05\" reqAbsPrc=\"50\" reqRelPrc=\"0.05\" batchSize=\"200\" minBatches=\"60\" numBMeansCorlTested=\"50\" bucketSize=\"100.0\" maxBuckets=\"1000\"/>\n");
		sb.append("          </meta-attributes>\n");
		sb.append("        </color-ref>\n");
		sb.append("      </color-refs>\n");
		sb.append("    </place>\n");
	}

	private static void setTransitions(StringBuffer sb, int numLanes,
			int numPlaces) {
		sb.append("  <transitions>\n");
		
		setTransition(sb, 70, y_distance, "_t_distributor", "distributor");
		setTransition(sb, 2*x_distance, y_distance/2, "_t_generator", "generator");

		for (int i = 0; i < numLanes; i++) {
			for (int j = 1; j < numTransitionsPerLane; j++) {
				int x = x_distance + 10 + i * x_distance;
				int y = y_distance + j * y_distance;
				String transitionId = "_t" + (i * numTransitionsPerLane + j);
				String transitionName = "t" + (i * numTransitionsPerLane + j);
				setTransition(sb, x, y, transitionId, transitionName);
			}
		}
		sb.append("  </transitions>\n");
	}

	private static void setTransition(StringBuffer sb, int x, int y,
			String transitionId, String transitionName) {
		String inPlace;
		String outPlace;
		String mode = "_mode" + transitionId;
		sb.append("    <transition xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" id=\""
				+ transitionId
				+ "\" priority=\"0\" xsi:type=\"immediate-transition\" weight=\"1.0\" name=\""
				+ transitionName + "\">\n");
		sb.append("      <meta-attributes> \n");
		sb.append("        <meta-attribute xsi:type=\"location-attribute\" location-x=\""
				+ x + "\" location-y=\"" + y + "\"/> \n");
		sb.append("      </meta-attributes>  \n");
		sb.append("      <modes> \n");
		sb.append("         <mode name=\"new mode\" real-color=\"#018d68\" firing-weight=\"1.0\" id=\""
				+ mode + "\"/> \n");
		sb.append("       </modes>  \n");
		sb.append("       <connections> \n");
		if(transitionId == "_t_distributor"){
			inPlace = "_p_start"+colorID;
			sb.append("         <connection id=\"" + (mode + "_"+0)
					+ "\" count=\"1\" source-id=\"" + inPlace + "\" target-id=\""
					+ mode + "\"/>  \n");

			for(int k=0; k<numLanes; k++){
				outPlace = "_p" + (k*numPlacesPerLane) + colorID;
				sb.append("         <connection id=\"" + mode +"_"+(1+k)
						+ "\" count=\"1\" source-id=\"" + mode + "\" target-id=\""
						+ outPlace + "\"/>  \n");
			}

		}else if(transitionId == "_t_generator"){
			inPlace = "_p_generator"+colorID;
			outPlace = "_p_start" + colorID;
			sb.append("         <connection id=\"" + (mode + "_"+0)
					+ "\" count=\"1\" source-id=\"" + inPlace + "\" target-id=\""
					+ mode + "\"/>  \n");
			sb.append("         <connection id=\"" + mode +"_"+1
					+ "\" count=\"1\" source-id=\"" + mode + "\" target-id=\""
					+ outPlace + "\"/>  \n");
			sb.append("         <connection id=\"" + mode +"_"+2
					+ "\" count=\"1\" source-id=\"" + mode + "\" target-id=\""
					+ inPlace + "\"/>  \n");
		}else{	
			inPlace = "_p" + (Integer.parseInt(transitionId.split("t")[1]) - 1)
					+ colorID;
			outPlace = "_p" + (Integer.parseInt(transitionId.split("t")[1]))
					+ colorID;
			sb.append("         <connection id=\"" + (mode + "_" + 2)
					+ "\" count=\"1\" source-id=\"" + mode + "\" target-id=\""
					+ outPlace + "\"/>  \n");
			sb.append("         <connection id=\"" + (mode + "_" + 1)
					+ "\" count=\"1\" source-id=\"" + inPlace + "\" target-id=\""
					+ mode + "\"/>  \n");
		}
		sb.append("      </connections> \n");
		sb.append("    </transition> \n");
	}

	private static void setConnections(StringBuffer sb, int numLanes) {
		String end;
		String start;
		String p_start = "_p_start";
		String t_dist = "_t_distributor";
		String p_gen = "_p_generator";
		String t_gen = "_t_generator";
		
		sb.append("  <connections>\n");
			
		createConnection(sb, p_start, t_dist);
		createConnection(sb, t_gen, p_start);
		createConnection(sb, t_gen, p_gen);
		createConnection(sb, p_gen, t_gen);


		for (int i = 0; i < numLanes; i++) {
			start = "_t_distributor";
			end = "_p" + (i * numPlacesPerLane);
			createConnection(sb, start, end);
		}

		for (int i = 0; i < numLanes; i++) {
			int transStartID = (i * numTransitionsPerLane);
			int placeStartID = (i * numPlacesPerLane);

			// into places
			for (int j = 1; j < numTransitionsPerLane; j++) {
				sb.append("    <connection source-id=\"_t" + (transStartID + j)
						+ "\" target-id=\"_p" + (placeStartID + j)
						+ "\" id=\"_arcOut" + (transStartID + j) + "\"/>\"\n");
			}
			// into transitions
			for (int j = 0; j < numTransitionsPerLane - 1; j++) {
				sb.append("    <connection source-id=\"_p" + (placeStartID + j)
						+ "\" target-id=\"_t" + (transStartID + j + 1)
						+ "\" id=\"_arcIn" + (transStartID + j) + "\"/>\"\n");
			}
		}

		sb.append("  </connections>\n");
	}

	private static void createConnection(StringBuffer sb, String start, String end) {
		sb.append("    <connection source-id=\"" + start
				+ "\" target-id=\"" + end + "\" id=\"_arcOut" + start + end
				+ "\"/>\"\n");
	}

	private static void writeToFile(String path, String text) {
		File file = new File(path);
		System.out.println("Writing to: "+file.getAbsoluteFile());
		BufferedWriter output;
		try {
			output = new BufferedWriter(new FileWriter(file));
			output.write(text);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void setQueuePlace(StringBuffer sb, int x, int y,
			String qPlaceId, String qPlaceName, String queueId) {
		int initialTokens;
		int serviceTime;
		if(qPlaceId == "_p_generator"){
			initialTokens = 1;
			serviceTime = numPlacesPerLane;
		}else{
			initialTokens = 0;
			serviceTime = 1;
		}
		
		sb.append("    <place xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" departure-discipline=\"NORMAL\" id=\"");
		sb.append(qPlaceId);
		sb.append("\" xsi:type=\"queueing-place\" name=\"" + qPlaceName
				+ "\" queue-ref=\"" + queueId + "\">\n");
		sb.append("     <color-refs>\n");
		sb.append("       <color-ref color-id=\""
				+ colorID
				+ "\" distribution-function=\"Deterministic\" id=\""
				+ qPlaceId
				+ colorID
				+ "\" initial-population=\""+initialTokens+"\" p1=\""+serviceTime+"\" maximum-capacity=\"0\" priority=\"0\" ranking=\"0\" xsi:type=\"queueing-color-reference\">\n");
		sb.append("         <meta-attributes>\n");
		sb.append("           <meta-attribute xsi:type=\"simqpn-batch-means-queueing-color-configuration\" configuration-name=\""+configuration+"\" id=\"_1375968815668\" signLev=\"0.05\" reqAbsPrc=\"50\" reqRelPrc=\"0.05\" batchSize=\"200\" minBatches=\"60\" numBMeansCorlTested=\"50\" bucketSize=\"100.0\" maxBuckets=\"1000\" queueSignLev=\"0.05\" queueReqAbsPrc=\"50\" queueReqRelPrc=\"0.05\" queueBatchSize=\"200\" queueMinBatches=\"60\" queueNumBMeansCorlTested=\"50\" queueBucketSize=\"100.0\" queueMaxBuckets=\"1000\"/>\n");
		sb.append("         </meta-attributes>\n");
		sb.append("       </color-ref>\n");
		sb.append("     </color-refs>\n");
		sb.append("     <meta-attributes>\n");
		sb.append("       <meta-attribute location-x=\"" + x
				+ "\" location-y=\"" + y
				+ "\" xsi:type=\"location-attribute\"/>\n");
		sb.append("       <meta-attribute xsi:type=\"simqpn-place-configuration\" id=\"_1375427783701\" configuration-name=\""+configuration+"\" statsLevel=\""
				+ statsLevel + "\"/>\n");
		sb.append("     </meta-attributes>\n");
		sb.append("    </place>\n");
	}

}
