package de.tud.cs.simqpn.ui.model;

import org.dom4j.Element;
import org.eclipse.jface.viewers.ICellEditorValidator;

import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.utils.CellValidators;

public class WelchConfiguration extends Configuration {

	private static final String MIN_OBSRV = "minObsrv";
	private static final String MAX_OBSRV = "maxObsrv";
	private static final String QUEUE_MIN_OBSRV = "queueMinObsrv";
	private static final String QUEUE_MAX_OBSRV = "queueMaxObsrv";
	
	@Override
	protected String[] initAttributeNames() {
		String[] attributeNames = new String[MAX_NUM_ATTRIBUTES];
		attributeNames[0] = null;
		attributeNames[1] = STATS_LEVEL;
		attributeNames[2] = MIN_OBSRV;
		attributeNames[3] = MAX_OBSRV;
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
		attributeNames[1] = null;
		attributeNames[2] = QUEUE_MIN_OBSRV;
		attributeNames[3] = QUEUE_MAX_OBSRV;
		attributeNames[4] = null;
		attributeNames[5] = null;
		attributeNames[6] = null;
		attributeNames[7] = null;
		attributeNames[8] = null;
		attributeNames[9] = null;
		return attributeNames;
	}
	
	@Override
	public ICellEditorValidator getValidator(int colIndex) {
		switch(colIndex) {
		case 2:
			return CellValidators.newNonNegativeIntegerValidator();
		case 3:
			return CellValidators.newNonNegativeIntegerValidator();
		}
		return null;
	}
	
	@Override
	public void createMetadata(Element net, String configName) {
		if (getMetadata() == null) {
			Element metadata = NetHelper.createMetadata(net, configName);
			metadata.addAttribute("scenario", "3");
			metadata.addAttribute("number-of-runs", "100");
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
		setAttributeIfNotExists(metaAttribute, MIN_OBSRV, "500");
		setAttributeIfNotExists(metaAttribute, MAX_OBSRV, "10000");
	}
	
	@Override
	public void initColorRefQueueMetadata(Element metaAttribute) {
		setAttributeIfNotExists(metaAttribute, QUEUE_MIN_OBSRV, "500");
		setAttributeIfNotExists(metaAttribute, QUEUE_MAX_OBSRV, "10000");
	}
}
