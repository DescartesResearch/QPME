/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model.impl;

import edu.kit.ipd.descartes.qpme.simqpn.model.Bucket;
import edu.kit.ipd.descartes.qpme.simqpn.model.Buckets;
import edu.kit.ipd.descartes.qpme.simqpn.model.Color;
import edu.kit.ipd.descartes.qpme.simqpn.model.Histogram;
import edu.kit.ipd.descartes.qpme.simqpn.model.Metric;
import edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement;
import edu.kit.ipd.descartes.qpme.simqpn.model.Percentile;
import edu.kit.ipd.descartes.qpme.simqpn.model.Percentiles;
import edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnFactory;
import edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage;
import edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults;
import edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResultsDocument;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SimqpnPackageImpl extends EPackageImpl implements SimqpnPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bucketEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bucketsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass colorEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass histogramEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metricEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass observedElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass percentileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass percentilesEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simqpnResultsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass simqpnResultsDocumentEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SimqpnPackageImpl() {
		super(eNS_URI, SimqpnFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link SimqpnPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SimqpnPackage init() {
		if (isInited) return (SimqpnPackage)EPackage.Registry.INSTANCE.getEPackage(SimqpnPackage.eNS_URI);

		// Obtain or create and register package
		SimqpnPackageImpl theSimqpnPackage = (SimqpnPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SimqpnPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SimqpnPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theSimqpnPackage.createPackageContents();

		// Initialize created meta-data
		theSimqpnPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSimqpnPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SimqpnPackage.eNS_URI, theSimqpnPackage);
		return theSimqpnPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBucket() {
		return bucketEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBucket_Value() {
		return (EAttribute)bucketEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBucket_Index() {
		return (EAttribute)bucketEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBuckets() {
		return bucketsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBuckets_Bucket() {
		return (EReference)bucketsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getColor() {
		return colorEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getColor_Metric() {
		return (EReference)colorEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getColor_Histogram() {
		return (EReference)colorEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColor_Id() {
		return (EAttribute)colorEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColor_Name() {
		return (EAttribute)colorEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getColor_RealColor() {
		return (EAttribute)colorEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getHistogram() {
		return histogramEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHistogram_Mean() {
		return (EAttribute)histogramEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHistogram_Percentiles() {
		return (EReference)histogramEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getHistogram_Buckets() {
		return (EReference)histogramEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHistogram_BucketSize() {
		return (EAttribute)histogramEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHistogram_NumBuckets() {
		return (EAttribute)histogramEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getHistogram_Type() {
		return (EAttribute)histogramEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetric() {
		return metricEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMetric_Type() {
		return (EAttribute)metricEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMetric_Value() {
		return (EAttribute)metricEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getObservedElement() {
		return observedElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObservedElement_Metric() {
		return (EReference)observedElementEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getObservedElement_Color() {
		return (EReference)observedElementEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getObservedElement_Id() {
		return (EAttribute)observedElementEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getObservedElement_Name() {
		return (EAttribute)observedElementEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getObservedElement_StatsLevel() {
		return (EAttribute)observedElementEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getObservedElement_Type() {
		return (EAttribute)observedElementEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPercentile() {
		return percentileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPercentile_Value() {
		return (EAttribute)percentileEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPercentile_For() {
		return (EAttribute)percentileEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPercentiles() {
		return percentilesEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPercentiles_Percentile() {
		return (EReference)percentilesEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimqpnResults() {
		return simqpnResultsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSimqpnResults_ObservedElement() {
		return (EReference)simqpnResultsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnResults_ConfigurationName() {
		return (EAttribute)simqpnResultsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnResults_Date() {
		return (EAttribute)simqpnResultsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnResults_ModelFile() {
		return (EAttribute)simqpnResultsEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnResults_Name() {
		return (EAttribute)simqpnResultsEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnResults_QpmeVersion() {
		return (EAttribute)simqpnResultsEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSimqpnResultsDocument() {
		return simqpnResultsDocumentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSimqpnResultsDocument_Mixed() {
		return (EAttribute)simqpnResultsDocumentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSimqpnResultsDocument_XMLNSPrefixMap() {
		return (EReference)simqpnResultsDocumentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSimqpnResultsDocument_XSISchemaLocation() {
		return (EReference)simqpnResultsDocumentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSimqpnResultsDocument_SimqpnResults() {
		return (EReference)simqpnResultsDocumentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnFactory getSimqpnFactory() {
		return (SimqpnFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		bucketEClass = createEClass(BUCKET);
		createEAttribute(bucketEClass, BUCKET__VALUE);
		createEAttribute(bucketEClass, BUCKET__INDEX);

		bucketsEClass = createEClass(BUCKETS);
		createEReference(bucketsEClass, BUCKETS__BUCKET);

		colorEClass = createEClass(COLOR);
		createEReference(colorEClass, COLOR__METRIC);
		createEReference(colorEClass, COLOR__HISTOGRAM);
		createEAttribute(colorEClass, COLOR__ID);
		createEAttribute(colorEClass, COLOR__NAME);
		createEAttribute(colorEClass, COLOR__REAL_COLOR);

		histogramEClass = createEClass(HISTOGRAM);
		createEAttribute(histogramEClass, HISTOGRAM__MEAN);
		createEReference(histogramEClass, HISTOGRAM__PERCENTILES);
		createEReference(histogramEClass, HISTOGRAM__BUCKETS);
		createEAttribute(histogramEClass, HISTOGRAM__BUCKET_SIZE);
		createEAttribute(histogramEClass, HISTOGRAM__NUM_BUCKETS);
		createEAttribute(histogramEClass, HISTOGRAM__TYPE);

		metricEClass = createEClass(METRIC);
		createEAttribute(metricEClass, METRIC__TYPE);
		createEAttribute(metricEClass, METRIC__VALUE);

		observedElementEClass = createEClass(OBSERVED_ELEMENT);
		createEReference(observedElementEClass, OBSERVED_ELEMENT__METRIC);
		createEReference(observedElementEClass, OBSERVED_ELEMENT__COLOR);
		createEAttribute(observedElementEClass, OBSERVED_ELEMENT__ID);
		createEAttribute(observedElementEClass, OBSERVED_ELEMENT__NAME);
		createEAttribute(observedElementEClass, OBSERVED_ELEMENT__STATS_LEVEL);
		createEAttribute(observedElementEClass, OBSERVED_ELEMENT__TYPE);

		percentileEClass = createEClass(PERCENTILE);
		createEAttribute(percentileEClass, PERCENTILE__VALUE);
		createEAttribute(percentileEClass, PERCENTILE__FOR);

		percentilesEClass = createEClass(PERCENTILES);
		createEReference(percentilesEClass, PERCENTILES__PERCENTILE);

		simqpnResultsEClass = createEClass(SIMQPN_RESULTS);
		createEReference(simqpnResultsEClass, SIMQPN_RESULTS__OBSERVED_ELEMENT);
		createEAttribute(simqpnResultsEClass, SIMQPN_RESULTS__CONFIGURATION_NAME);
		createEAttribute(simqpnResultsEClass, SIMQPN_RESULTS__DATE);
		createEAttribute(simqpnResultsEClass, SIMQPN_RESULTS__MODEL_FILE);
		createEAttribute(simqpnResultsEClass, SIMQPN_RESULTS__NAME);
		createEAttribute(simqpnResultsEClass, SIMQPN_RESULTS__QPME_VERSION);

		simqpnResultsDocumentEClass = createEClass(SIMQPN_RESULTS_DOCUMENT);
		createEAttribute(simqpnResultsDocumentEClass, SIMQPN_RESULTS_DOCUMENT__MIXED);
		createEReference(simqpnResultsDocumentEClass, SIMQPN_RESULTS_DOCUMENT__XMLNS_PREFIX_MAP);
		createEReference(simqpnResultsDocumentEClass, SIMQPN_RESULTS_DOCUMENT__XSI_SCHEMA_LOCATION);
		createEReference(simqpnResultsDocumentEClass, SIMQPN_RESULTS_DOCUMENT__SIMQPN_RESULTS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(bucketEClass, Bucket.class, "Bucket", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBucket_Value(), theXMLTypePackage.getInt(), "value", null, 0, 1, Bucket.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBucket_Index(), theXMLTypePackage.getInt(), "index", null, 1, 1, Bucket.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bucketsEClass, Buckets.class, "Buckets", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getBuckets_Bucket(), this.getBucket(), null, "bucket", null, 0, -1, Buckets.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(colorEClass, Color.class, "Color", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getColor_Metric(), this.getMetric(), null, "metric", null, 0, -1, Color.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getColor_Histogram(), this.getHistogram(), null, "histogram", null, 0, -1, Color.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColor_Id(), theXMLTypePackage.getString(), "id", null, 1, 1, Color.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColor_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, Color.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getColor_RealColor(), theXMLTypePackage.getString(), "realColor", null, 1, 1, Color.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(histogramEClass, Histogram.class, "Histogram", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHistogram_Mean(), theXMLTypePackage.getDouble(), "mean", null, 1, 1, Histogram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHistogram_Percentiles(), this.getPercentiles(), null, "percentiles", null, 1, 1, Histogram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHistogram_Buckets(), this.getBuckets(), null, "buckets", null, 1, 1, Histogram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHistogram_BucketSize(), theXMLTypePackage.getDouble(), "bucketSize", null, 0, 1, Histogram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHistogram_NumBuckets(), theXMLTypePackage.getLong(), "numBuckets", null, 0, 1, Histogram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHistogram_Type(), theXMLTypePackage.getString(), "type", null, 0, 1, Histogram.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metricEClass, Metric.class, "Metric", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMetric_Type(), theXMLTypePackage.getString(), "type", null, 1, 1, Metric.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMetric_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, Metric.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(observedElementEClass, ObservedElement.class, "ObservedElement", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getObservedElement_Metric(), this.getMetric(), null, "metric", null, 0, -1, ObservedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getObservedElement_Color(), this.getColor(), null, "color", null, 0, -1, ObservedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservedElement_Id(), theXMLTypePackage.getString(), "id", null, 1, 1, ObservedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservedElement_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, ObservedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservedElement_StatsLevel(), theXMLTypePackage.getInt(), "statsLevel", null, 1, 1, ObservedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getObservedElement_Type(), theXMLTypePackage.getString(), "type", null, 1, 1, ObservedElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(percentileEClass, Percentile.class, "Percentile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPercentile_Value(), theXMLTypePackage.getDouble(), "value", null, 0, 1, Percentile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPercentile_For(), theXMLTypePackage.getDouble(), "for", null, 1, 1, Percentile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(percentilesEClass, Percentiles.class, "Percentiles", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPercentiles_Percentile(), this.getPercentile(), null, "percentile", null, 0, -1, Percentiles.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simqpnResultsEClass, SimqpnResults.class, "SimqpnResults", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getSimqpnResults_ObservedElement(), this.getObservedElement(), null, "observedElement", null, 0, -1, SimqpnResults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnResults_ConfigurationName(), theXMLTypePackage.getString(), "configurationName", null, 1, 1, SimqpnResults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnResults_Date(), theXMLTypePackage.getDateTime(), "date", null, 1, 1, SimqpnResults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnResults_ModelFile(), theXMLTypePackage.getString(), "modelFile", null, 1, 1, SimqpnResults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnResults_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, SimqpnResults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSimqpnResults_QpmeVersion(), theXMLTypePackage.getString(), "qpmeVersion", null, 1, 1, SimqpnResults.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(simqpnResultsDocumentEClass, SimqpnResultsDocument.class, "SimqpnResultsDocument", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSimqpnResultsDocument_Mixed(), ecorePackage.getEFeatureMapEntry(), "mixed", null, 0, -1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSimqpnResultsDocument_XMLNSPrefixMap(), ecorePackage.getEStringToStringMapEntry(), null, "xMLNSPrefixMap", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSimqpnResultsDocument_XSISchemaLocation(), ecorePackage.getEStringToStringMapEntry(), null, "xSISchemaLocation", null, 0, -1, null, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getSimqpnResultsDocument_SimqpnResults(), this.getSimqpnResults(), null, "simqpnResults", null, 0, -1, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";	
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "qualified", "false"
		   });	
		addAnnotation
		  (bucketEClass, 
		   source, 
		   new String[] {
			 "name", "bucket",
			 "kind", "simple"
		   });	
		addAnnotation
		  (getBucket_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });	
		addAnnotation
		  (getBucket_Index(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "index",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (bucketsEClass, 
		   source, 
		   new String[] {
			 "name", "buckets",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getBuckets_Bucket(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "bucket",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (colorEClass, 
		   source, 
		   new String[] {
			 "name", "color",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getColor_Metric(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "metric",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getColor_Histogram(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "histogram",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getColor_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getColor_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getColor_RealColor(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "real-color",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (histogramEClass, 
		   source, 
		   new String[] {
			 "name", "histogram",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getHistogram_Mean(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "mean",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getHistogram_Percentiles(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "percentiles",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getHistogram_Buckets(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "buckets",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getHistogram_BucketSize(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "bucket-size",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getHistogram_NumBuckets(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "num-buckets",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getHistogram_Type(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "type",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (metricEClass, 
		   source, 
		   new String[] {
			 "name", "metric",
			 "kind", "empty"
		   });	
		addAnnotation
		  (getMetric_Type(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "type",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getMetric_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (observedElementEClass, 
		   source, 
		   new String[] {
			 "name", "observed-element",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getObservedElement_Metric(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "metric",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getObservedElement_Color(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "color",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getObservedElement_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getObservedElement_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getObservedElement_StatsLevel(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "stats-level",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getObservedElement_Type(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "type",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (percentileEClass, 
		   source, 
		   new String[] {
			 "name", "percentile",
			 "kind", "simple"
		   });	
		addAnnotation
		  (getPercentile_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });	
		addAnnotation
		  (getPercentile_For(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "for",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (percentilesEClass, 
		   source, 
		   new String[] {
			 "name", "percentiles",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getPercentiles_Percentile(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "percentile",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (simqpnResultsEClass, 
		   source, 
		   new String[] {
			 "name", "simqpn-results",
			 "kind", "elementOnly"
		   });	
		addAnnotation
		  (getSimqpnResults_ObservedElement(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "observed-element",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getSimqpnResults_ConfigurationName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "configuration-name",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getSimqpnResults_Date(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "date",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getSimqpnResults_ModelFile(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "model-file",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getSimqpnResults_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (getSimqpnResults_QpmeVersion(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "qpme-version",
			 "namespace", "##targetNamespace"
		   });	
		addAnnotation
		  (simqpnResultsDocumentEClass, 
		   source, 
		   new String[] {
			 "name", "",
			 "kind", "mixed"
		   });	
		addAnnotation
		  (getSimqpnResultsDocument_Mixed(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "name", ":mixed"
		   });	
		addAnnotation
		  (getSimqpnResultsDocument_XMLNSPrefixMap(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xmlns:prefix"
		   });	
		addAnnotation
		  (getSimqpnResultsDocument_XSISchemaLocation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "xsi:schemaLocation"
		   });	
		addAnnotation
		  (getSimqpnResultsDocument_SimqpnResults(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "simqpn-results",
			 "namespace", "##targetNamespace"
		   });
	}

} //SimqpnPackageImpl
