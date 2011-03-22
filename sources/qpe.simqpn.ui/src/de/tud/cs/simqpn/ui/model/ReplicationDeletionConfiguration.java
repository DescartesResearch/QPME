package de.tud.cs.simqpn.ui.model;

import org.dom4j.Element;
import org.eclipse.jface.viewers.ICellEditorValidator;

import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.utils.CellValidators;

public class ReplicationDeletionConfiguration extends Configuration {
	
	private static final String SIGN_LEV_AVG_ST = "signLevAvgST";
	private static final String QUEUE_SIGN_LEV_AVG_ST = "queueSignLevAvgST";
	
	@Override
	protected String[] initAttributeNames() {
		String[] attributeNames = new String[MAX_NUM_ATTRIBUTES];
		attributeNames[0] = null;
		attributeNames[1] = STATS_LEVEL;
		attributeNames[2] = SIGN_LEV_AVG_ST;
		attributeNames[3] = null;
		attributeNames[4] = null;
		attributeNames[5] = null;
		attributeNames[6] = null;
		attributeNames[7] = null;
		attributeNames[8] = null;
		attributeNames[9] = null;
		return attributeNames;
	}
	
	@Override
	protected String[] initQueueAttributeNames() {
		String[] attributeNames = new String[MAX_NUM_ATTRIBUTES];
		attributeNames[0] = null;
		attributeNames[1] = STATS_LEVEL;
		attributeNames[2] = QUEUE_SIGN_LEV_AVG_ST;
		attributeNames[3] = null;
		attributeNames[4] = null;
		attributeNames[5] = null;
		attributeNames[6] = null;
		attributeNames[7] = null;
		attributeNames[8] = null;
		attributeNames[9] = null;
		return attributeNames;
	}
	
	public ICellEditorValidator getValidator(int colIndex) {
		switch(colIndex) {
		case 1:
			return CellValidators.newNonNegativeIntegerValidator();
		case 2:
			return CellValidators.newNonNegativeDoubleValidator();
		}
		return null;
	}
	
	@Override
	public void createMetadata(Element net, String configName) {
		if (getMetadata() == null) {
			Element metadata = NetHelper.createMetadata(net, configName);
			metadata.addAttribute("scenario", "2");
			metadata.addAttribute("number-of-runs", "100");
			metadata.addAttribute("ramp-up-length", "1000000");
			metadata.addAttribute("total-run-length", "10000000");
			metadata.addAttribute("stopping-rule", "FIXEDLEN");
			metadata.addAttribute("time-before-initial-heart-beat", "100000");
			metadata.addAttribute("seconds-between-heart-beats", "60");
			metadata.addAttribute("verbosity-level", "0");
			metadata.addAttribute("output-directory", ".");
			setMetadata(metadata);
		}
	}
	
	@Override
	public void initColorRefDepositoryMetadata(Element metaAttribute) {
		setAttributeIfNotExists(metaAttribute, SIGN_LEV_AVG_ST, "0.05");		
	}
	
	@Override
	public void initColorRefQueueMetadata(Element metaAttribute) {
		setAttributeIfNotExists(metaAttribute, QUEUE_SIGN_LEV_AVG_ST, "0.05");
	}
	
	
}
