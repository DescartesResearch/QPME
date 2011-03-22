package de.tud.cs.simqpn.ui.model;

import org.dom4j.Element;
import org.dom4j.tree.DefaultElement;
import org.eclipse.jface.viewers.ICellEditorValidator;

import de.tud.cs.qpe.model.DocumentManager;
import de.tud.cs.qpe.model.NetHelper;
import de.tud.cs.qpe.utils.CellValidators;

public class BatchMeansConfiguration extends Configuration {
	
	private static final String SIGN_LEV = "signLev";
	private static final String REQ_ABS_PRC = "reqAbsPrc";
	private static final String REQ_REL_PRC = "reqRelPrc";
	private static final String BATCH_SIZE = "batchSize";
	private static final String MIN_BATCHES = "minBatches";
	private static final String NUM_BMEANS_CORL_TESTED = "numBMeansCorlTested";
	private static final String BUCKET_SIZE = "bucketSize";
	private static final String MAX_BUCKETS = "maxBuckets";
	
	private static final String QUEUE_SIGN_LEV = "queueSignLev";
	private static final String QUEUE_REQ_ABS_PRC = "queueReqAbsPrc";
	private static final String QUEUE_REQ_REL_PRC = "queueReqRelPrc";
	private static final String QUEUE_BATCH_SIZE = "queueBatchSize";
	private static final String QUEUE_MIN_BATCHES = "queueMinBatches";
	private static final String QUEUE_NUM_BMEANS_CORL_TESTED = "queueNumBMeansCorlTested";
	private static final String QUEUE_BUCKET_SIZE = "queueBucketSize";
	private static final String QUEUE_MAX_BUCKETS = "queueMaxBuckets";
	
	@Override
	protected String[] initAttributeNames() {
		String[] attributeNames = new String[MAX_NUM_ATTRIBUTES];		
		attributeNames[0] = null;
		attributeNames[1] = STATS_LEVEL;
		attributeNames[2] = SIGN_LEV;
		attributeNames[3] = REQ_ABS_PRC;
		attributeNames[4] = REQ_REL_PRC;
		attributeNames[5] = BATCH_SIZE;
		attributeNames[6] = MIN_BATCHES;
		attributeNames[7] = NUM_BMEANS_CORL_TESTED;
		attributeNames[8] = BUCKET_SIZE;
		attributeNames[9] = MAX_BUCKETS;
		return attributeNames;
	}
	
	@Override
	protected String[] initQueueAttributeNames() {
		String[] attributeNames = new String[MAX_NUM_ATTRIBUTES];		
		attributeNames[0] = null;
		attributeNames[1] = STATS_LEVEL;
		attributeNames[2] = QUEUE_SIGN_LEV;
		attributeNames[3] = QUEUE_REQ_ABS_PRC;
		attributeNames[4] = QUEUE_REQ_REL_PRC;
		attributeNames[5] = QUEUE_BATCH_SIZE;
		attributeNames[6] = QUEUE_MIN_BATCHES;
		attributeNames[7] = QUEUE_NUM_BMEANS_CORL_TESTED;
		attributeNames[8] = QUEUE_BUCKET_SIZE;
		attributeNames[9] = QUEUE_MAX_BUCKETS;
		return attributeNames;
	}
	
	@Override
	public ICellEditorValidator getValidator(int colIndex) {
		switch(colIndex) {
		case 1:
			return CellValidators.newNonNegativeIntegerValidator();
		case 2:
			return CellValidators.newPositiveDoubleValidator(); 
		case 3:
			return CellValidators.newPositiveDoubleValidator();
		case 4:
			return CellValidators.newPositiveDoubleValidator();
		case 5:
			return CellValidators.newGreaterThanOrEqualIntegerValidator(10);
		case 6:
			return CellValidators.newNonNegativeIntegerValidator();
		case 7:
			return CellValidators.newNonNegativeEvenIntegerValidator();
		case 8:
			return CellValidators.newNonNegativeDoubleValidator();
		case 9:
			return CellValidators.newNonNegativeIntegerValidator();
		}
		return null;
	}
	
	@Override
	public void createMetadata(Element net, String configName) {
		if (getMetadata() == null) {
			Element metadata = NetHelper.createMetadata(net, configName);
			metadata.addAttribute("scenario", "1");
			metadata.addAttribute("ramp-up-length", "1000000");
			metadata.addAttribute("total-run-length", "10000000");
			metadata.addAttribute("stopping-rule", "FIXEDLEN");
			metadata.addAttribute("time-before-initial-heart-beat", "100000");
			metadata.addAttribute("time-between-stop-checks", "100000");
			metadata.addAttribute("seconds-between-stop-checks", "60");
			metadata.addAttribute("seconds-between-heart-beats", "60");
			metadata.addAttribute("verbosity-level", "0");
			metadata.addAttribute("output-directory", ".");
			setMetadata(metadata);
		}
	}

	@Override
	public void initColorRefDepositoryMetadata(Element metaAttribute) {
		setAttributeIfNotExists(metaAttribute, SIGN_LEV, "0.05");
		setAttributeIfNotExists(metaAttribute, REQ_ABS_PRC, "50");
		setAttributeIfNotExists(metaAttribute, REQ_REL_PRC, "0.05");
		setAttributeIfNotExists(metaAttribute, BATCH_SIZE, "200");
		setAttributeIfNotExists(metaAttribute, MIN_BATCHES, "60");
		setAttributeIfNotExists(metaAttribute, NUM_BMEANS_CORL_TESTED, "50");
		setAttributeIfNotExists(metaAttribute, BUCKET_SIZE, "100.0");
		setAttributeIfNotExists(metaAttribute, MAX_BUCKETS, "1000");
	}
	
	@Override
	public void initColorRefQueueMetadata(Element metaAttribute) {
		setAttributeIfNotExists(metaAttribute, QUEUE_SIGN_LEV, "0.05");
		setAttributeIfNotExists(metaAttribute, QUEUE_REQ_ABS_PRC, "50");
		setAttributeIfNotExists(metaAttribute, QUEUE_REQ_REL_PRC, "0.05");
		setAttributeIfNotExists(metaAttribute, QUEUE_BATCH_SIZE, "200");
		setAttributeIfNotExists(metaAttribute, QUEUE_MIN_BATCHES, "60");
		setAttributeIfNotExists(metaAttribute, QUEUE_NUM_BMEANS_CORL_TESTED, "50");
		setAttributeIfNotExists(metaAttribute, QUEUE_BUCKET_SIZE, "100.0");
		setAttributeIfNotExists(metaAttribute, QUEUE_MAX_BUCKETS, "1000");
	}

}
