/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnFactory
 * @model kind="package"
 *        extendedMetaData="qualified='false'"
 * @generated
 */
public interface SimqpnPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.descartes-research.net/simqpn/model/1.0";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "simqpn";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SimqpnPackage eINSTANCE = edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl.init();

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.BucketImpl <em>Bucket</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.BucketImpl
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getBucket()
	 * @generated
	 */
	int BUCKET = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUCKET__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Index</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUCKET__INDEX = 1;

	/**
	 * The number of structural features of the '<em>Bucket</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUCKET_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.BucketsImpl <em>Buckets</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.BucketsImpl
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getBuckets()
	 * @generated
	 */
	int BUCKETS = 1;

	/**
	 * The feature id for the '<em><b>Bucket</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUCKETS__BUCKET = 0;

	/**
	 * The number of structural features of the '<em>Buckets</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUCKETS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.ColorImpl <em>Color</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.ColorImpl
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getColor()
	 * @generated
	 */
	int COLOR = 2;

	/**
	 * The feature id for the '<em><b>Metric</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR__METRIC = 0;

	/**
	 * The feature id for the '<em><b>Histogram</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR__HISTOGRAM = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR__ID = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR__NAME = 3;

	/**
	 * The feature id for the '<em><b>Real Color</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR__REAL_COLOR = 4;

	/**
	 * The number of structural features of the '<em>Color</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLOR_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.HistogramImpl <em>Histogram</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.HistogramImpl
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getHistogram()
	 * @generated
	 */
	int HISTOGRAM = 3;

	/**
	 * The feature id for the '<em><b>Mean</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTOGRAM__MEAN = 0;

	/**
	 * The feature id for the '<em><b>Percentiles</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTOGRAM__PERCENTILES = 1;

	/**
	 * The feature id for the '<em><b>Buckets</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTOGRAM__BUCKETS = 2;

	/**
	 * The feature id for the '<em><b>Bucket Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTOGRAM__BUCKET_SIZE = 3;

	/**
	 * The feature id for the '<em><b>Num Buckets</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTOGRAM__NUM_BUCKETS = 4;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTOGRAM__TYPE = 5;

	/**
	 * The number of structural features of the '<em>Histogram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTOGRAM_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.MetricImpl <em>Metric</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.MetricImpl
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getMetric()
	 * @generated
	 */
	int METRIC = 4;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Metric</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.ObservedElementImpl <em>Observed Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.ObservedElementImpl
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getObservedElement()
	 * @generated
	 */
	int OBSERVED_ELEMENT = 5;

	/**
	 * The feature id for the '<em><b>Metric</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVED_ELEMENT__METRIC = 0;

	/**
	 * The feature id for the '<em><b>Color</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVED_ELEMENT__COLOR = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVED_ELEMENT__ID = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVED_ELEMENT__NAME = 3;

	/**
	 * The feature id for the '<em><b>Stats Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVED_ELEMENT__STATS_LEVEL = 4;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVED_ELEMENT__TYPE = 5;

	/**
	 * The number of structural features of the '<em>Observed Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OBSERVED_ELEMENT_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.PercentileImpl <em>Percentile</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.PercentileImpl
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getPercentile()
	 * @generated
	 */
	int PERCENTILE = 6;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERCENTILE__VALUE = 0;

	/**
	 * The feature id for the '<em><b>For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERCENTILE__FOR = 1;

	/**
	 * The number of structural features of the '<em>Percentile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERCENTILE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.PercentilesImpl <em>Percentiles</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.PercentilesImpl
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getPercentiles()
	 * @generated
	 */
	int PERCENTILES = 7;

	/**
	 * The feature id for the '<em><b>Percentile</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERCENTILES__PERCENTILE = 0;

	/**
	 * The number of structural features of the '<em>Percentiles</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERCENTILES_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnResultsImpl <em>Results</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnResultsImpl
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getSimqpnResults()
	 * @generated
	 */
	int SIMQPN_RESULTS = 8;

	/**
	 * The feature id for the '<em><b>Observed Element</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_RESULTS__OBSERVED_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Configuration Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_RESULTS__CONFIGURATION_NAME = 1;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_RESULTS__DATE = 2;

	/**
	 * The feature id for the '<em><b>Model File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_RESULTS__MODEL_FILE = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_RESULTS__NAME = 4;

	/**
	 * The feature id for the '<em><b>Qpme Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_RESULTS__QPME_VERSION = 5;

	/**
	 * The number of structural features of the '<em>Results</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_RESULTS_FEATURE_COUNT = 6;

	/**
	 * The meta object id for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnResultsDocumentImpl <em>Results Document</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnResultsDocumentImpl
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getSimqpnResultsDocument()
	 * @generated
	 */
	int SIMQPN_RESULTS_DOCUMENT = 9;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_RESULTS_DOCUMENT__MIXED = 0;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_RESULTS_DOCUMENT__XMLNS_PREFIX_MAP = 1;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_RESULTS_DOCUMENT__XSI_SCHEMA_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Simqpn Results</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_RESULTS_DOCUMENT__SIMQPN_RESULTS = 3;

	/**
	 * The number of structural features of the '<em>Results Document</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMQPN_RESULTS_DOCUMENT_FEATURE_COUNT = 4;


	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Bucket <em>Bucket</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Bucket</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Bucket
	 * @generated
	 */
	EClass getBucket();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Bucket#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Bucket#getValue()
	 * @see #getBucket()
	 * @generated
	 */
	EAttribute getBucket_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Bucket#getIndex <em>Index</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Index</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Bucket#getIndex()
	 * @see #getBucket()
	 * @generated
	 */
	EAttribute getBucket_Index();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Buckets <em>Buckets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Buckets</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Buckets
	 * @generated
	 */
	EClass getBuckets();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Buckets#getBucket <em>Bucket</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Bucket</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Buckets#getBucket()
	 * @see #getBuckets()
	 * @generated
	 */
	EReference getBuckets_Bucket();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Color <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Color</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Color
	 * @generated
	 */
	EClass getColor();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Color#getMetric <em>Metric</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Metric</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Color#getMetric()
	 * @see #getColor()
	 * @generated
	 */
	EReference getColor_Metric();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Color#getHistogram <em>Histogram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Histogram</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Color#getHistogram()
	 * @see #getColor()
	 * @generated
	 */
	EReference getColor_Histogram();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Color#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Color#getId()
	 * @see #getColor()
	 * @generated
	 */
	EAttribute getColor_Id();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Color#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Color#getName()
	 * @see #getColor()
	 * @generated
	 */
	EAttribute getColor_Name();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Color#getRealColor <em>Real Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Real Color</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Color#getRealColor()
	 * @see #getColor()
	 * @generated
	 */
	EAttribute getColor_RealColor();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram <em>Histogram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Histogram</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Histogram
	 * @generated
	 */
	EClass getHistogram();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getMean <em>Mean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mean</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getMean()
	 * @see #getHistogram()
	 * @generated
	 */
	EAttribute getHistogram_Mean();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getPercentiles <em>Percentiles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Percentiles</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getPercentiles()
	 * @see #getHistogram()
	 * @generated
	 */
	EReference getHistogram_Percentiles();

	/**
	 * Returns the meta object for the containment reference '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getBuckets <em>Buckets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Buckets</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getBuckets()
	 * @see #getHistogram()
	 * @generated
	 */
	EReference getHistogram_Buckets();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getBucketSize <em>Bucket Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Bucket Size</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getBucketSize()
	 * @see #getHistogram()
	 * @generated
	 */
	EAttribute getHistogram_BucketSize();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getNumBuckets <em>Num Buckets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Num Buckets</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getNumBuckets()
	 * @see #getHistogram()
	 * @generated
	 */
	EAttribute getHistogram_NumBuckets();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Histogram#getType()
	 * @see #getHistogram()
	 * @generated
	 */
	EAttribute getHistogram_Type();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Metric <em>Metric</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Metric
	 * @generated
	 */
	EClass getMetric();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Metric#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Metric#getType()
	 * @see #getMetric()
	 * @generated
	 */
	EAttribute getMetric_Type();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Metric#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Metric#getValue()
	 * @see #getMetric()
	 * @generated
	 */
	EAttribute getMetric_Value();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement <em>Observed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Observed Element</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement
	 * @generated
	 */
	EClass getObservedElement();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getMetric <em>Metric</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Metric</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getMetric()
	 * @see #getObservedElement()
	 * @generated
	 */
	EReference getObservedElement_Metric();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getColor <em>Color</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Color</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getColor()
	 * @see #getObservedElement()
	 * @generated
	 */
	EReference getObservedElement_Color();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getId()
	 * @see #getObservedElement()
	 * @generated
	 */
	EAttribute getObservedElement_Id();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getName()
	 * @see #getObservedElement()
	 * @generated
	 */
	EAttribute getObservedElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getStatsLevel <em>Stats Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Stats Level</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getStatsLevel()
	 * @see #getObservedElement()
	 * @generated
	 */
	EAttribute getObservedElement_StatsLevel();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement#getType()
	 * @see #getObservedElement()
	 * @generated
	 */
	EAttribute getObservedElement_Type();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Percentile <em>Percentile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Percentile</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Percentile
	 * @generated
	 */
	EClass getPercentile();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Percentile#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Percentile#getValue()
	 * @see #getPercentile()
	 * @generated
	 */
	EAttribute getPercentile_Value();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Percentile#getFor <em>For</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>For</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Percentile#getFor()
	 * @see #getPercentile()
	 * @generated
	 */
	EAttribute getPercentile_For();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Percentiles <em>Percentiles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Percentiles</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Percentiles
	 * @generated
	 */
	EClass getPercentiles();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.simqpn.model.Percentiles#getPercentile <em>Percentile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Percentile</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.Percentiles#getPercentile()
	 * @see #getPercentiles()
	 * @generated
	 */
	EReference getPercentiles_Percentile();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults <em>Results</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Results</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults
	 * @generated
	 */
	EClass getSimqpnResults();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getObservedElement <em>Observed Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Observed Element</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getObservedElement()
	 * @see #getSimqpnResults()
	 * @generated
	 */
	EReference getSimqpnResults_ObservedElement();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getConfigurationName <em>Configuration Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Configuration Name</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getConfigurationName()
	 * @see #getSimqpnResults()
	 * @generated
	 */
	EAttribute getSimqpnResults_ConfigurationName();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getDate <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getDate()
	 * @see #getSimqpnResults()
	 * @generated
	 */
	EAttribute getSimqpnResults_Date();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getModelFile <em>Model File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Model File</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getModelFile()
	 * @see #getSimqpnResults()
	 * @generated
	 */
	EAttribute getSimqpnResults_ModelFile();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getName()
	 * @see #getSimqpnResults()
	 * @generated
	 */
	EAttribute getSimqpnResults_Name();

	/**
	 * Returns the meta object for the attribute '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getQpmeVersion <em>Qpme Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Qpme Version</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getQpmeVersion()
	 * @see #getSimqpnResults()
	 * @generated
	 */
	EAttribute getSimqpnResults_QpmeVersion();

	/**
	 * Returns the meta object for class '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResultsDocument <em>Results Document</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Results Document</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResultsDocument
	 * @generated
	 */
	EClass getSimqpnResultsDocument();

	/**
	 * Returns the meta object for the attribute list '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResultsDocument#getMixed <em>Mixed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Mixed</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResultsDocument#getMixed()
	 * @see #getSimqpnResultsDocument()
	 * @generated
	 */
	EAttribute getSimqpnResultsDocument_Mixed();

	/**
	 * Returns the meta object for the map '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResultsDocument#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XMLNS Prefix Map</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResultsDocument#getXMLNSPrefixMap()
	 * @see #getSimqpnResultsDocument()
	 * @generated
	 */
	EReference getSimqpnResultsDocument_XMLNSPrefixMap();

	/**
	 * Returns the meta object for the map '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResultsDocument#getXSISchemaLocation <em>XSI Schema Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>XSI Schema Location</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResultsDocument#getXSISchemaLocation()
	 * @see #getSimqpnResultsDocument()
	 * @generated
	 */
	EReference getSimqpnResultsDocument_XSISchemaLocation();

	/**
	 * Returns the meta object for the containment reference list '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResultsDocument#getSimqpnResults <em>Simqpn Results</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Simqpn Results</em>'.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResultsDocument#getSimqpnResults()
	 * @see #getSimqpnResultsDocument()
	 * @generated
	 */
	EReference getSimqpnResultsDocument_SimqpnResults();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SimqpnFactory getSimqpnFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.BucketImpl <em>Bucket</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.BucketImpl
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getBucket()
		 * @generated
		 */
		EClass BUCKET = eINSTANCE.getBucket();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUCKET__VALUE = eINSTANCE.getBucket_Value();

		/**
		 * The meta object literal for the '<em><b>Index</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUCKET__INDEX = eINSTANCE.getBucket_Index();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.BucketsImpl <em>Buckets</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.BucketsImpl
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getBuckets()
		 * @generated
		 */
		EClass BUCKETS = eINSTANCE.getBuckets();

		/**
		 * The meta object literal for the '<em><b>Bucket</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BUCKETS__BUCKET = eINSTANCE.getBuckets_Bucket();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.ColorImpl <em>Color</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.ColorImpl
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getColor()
		 * @generated
		 */
		EClass COLOR = eINSTANCE.getColor();

		/**
		 * The meta object literal for the '<em><b>Metric</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COLOR__METRIC = eINSTANCE.getColor_Metric();

		/**
		 * The meta object literal for the '<em><b>Histogram</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COLOR__HISTOGRAM = eINSTANCE.getColor_Histogram();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLOR__ID = eINSTANCE.getColor_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLOR__NAME = eINSTANCE.getColor_Name();

		/**
		 * The meta object literal for the '<em><b>Real Color</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLOR__REAL_COLOR = eINSTANCE.getColor_RealColor();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.HistogramImpl <em>Histogram</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.HistogramImpl
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getHistogram()
		 * @generated
		 */
		EClass HISTOGRAM = eINSTANCE.getHistogram();

		/**
		 * The meta object literal for the '<em><b>Mean</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HISTOGRAM__MEAN = eINSTANCE.getHistogram_Mean();

		/**
		 * The meta object literal for the '<em><b>Percentiles</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HISTOGRAM__PERCENTILES = eINSTANCE.getHistogram_Percentiles();

		/**
		 * The meta object literal for the '<em><b>Buckets</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HISTOGRAM__BUCKETS = eINSTANCE.getHistogram_Buckets();

		/**
		 * The meta object literal for the '<em><b>Bucket Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HISTOGRAM__BUCKET_SIZE = eINSTANCE.getHistogram_BucketSize();

		/**
		 * The meta object literal for the '<em><b>Num Buckets</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HISTOGRAM__NUM_BUCKETS = eINSTANCE.getHistogram_NumBuckets();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HISTOGRAM__TYPE = eINSTANCE.getHistogram_Type();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.MetricImpl <em>Metric</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.MetricImpl
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getMetric()
		 * @generated
		 */
		EClass METRIC = eINSTANCE.getMetric();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC__TYPE = eINSTANCE.getMetric_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC__VALUE = eINSTANCE.getMetric_Value();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.ObservedElementImpl <em>Observed Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.ObservedElementImpl
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getObservedElement()
		 * @generated
		 */
		EClass OBSERVED_ELEMENT = eINSTANCE.getObservedElement();

		/**
		 * The meta object literal for the '<em><b>Metric</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBSERVED_ELEMENT__METRIC = eINSTANCE.getObservedElement_Metric();

		/**
		 * The meta object literal for the '<em><b>Color</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OBSERVED_ELEMENT__COLOR = eINSTANCE.getObservedElement_Color();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVED_ELEMENT__ID = eINSTANCE.getObservedElement_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVED_ELEMENT__NAME = eINSTANCE.getObservedElement_Name();

		/**
		 * The meta object literal for the '<em><b>Stats Level</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVED_ELEMENT__STATS_LEVEL = eINSTANCE.getObservedElement_StatsLevel();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OBSERVED_ELEMENT__TYPE = eINSTANCE.getObservedElement_Type();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.PercentileImpl <em>Percentile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.PercentileImpl
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getPercentile()
		 * @generated
		 */
		EClass PERCENTILE = eINSTANCE.getPercentile();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERCENTILE__VALUE = eINSTANCE.getPercentile_Value();

		/**
		 * The meta object literal for the '<em><b>For</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERCENTILE__FOR = eINSTANCE.getPercentile_For();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.PercentilesImpl <em>Percentiles</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.PercentilesImpl
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getPercentiles()
		 * @generated
		 */
		EClass PERCENTILES = eINSTANCE.getPercentiles();

		/**
		 * The meta object literal for the '<em><b>Percentile</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERCENTILES__PERCENTILE = eINSTANCE.getPercentiles_Percentile();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnResultsImpl <em>Results</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnResultsImpl
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getSimqpnResults()
		 * @generated
		 */
		EClass SIMQPN_RESULTS = eINSTANCE.getSimqpnResults();

		/**
		 * The meta object literal for the '<em><b>Observed Element</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMQPN_RESULTS__OBSERVED_ELEMENT = eINSTANCE.getSimqpnResults_ObservedElement();

		/**
		 * The meta object literal for the '<em><b>Configuration Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_RESULTS__CONFIGURATION_NAME = eINSTANCE.getSimqpnResults_ConfigurationName();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_RESULTS__DATE = eINSTANCE.getSimqpnResults_Date();

		/**
		 * The meta object literal for the '<em><b>Model File</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_RESULTS__MODEL_FILE = eINSTANCE.getSimqpnResults_ModelFile();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_RESULTS__NAME = eINSTANCE.getSimqpnResults_Name();

		/**
		 * The meta object literal for the '<em><b>Qpme Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_RESULTS__QPME_VERSION = eINSTANCE.getSimqpnResults_QpmeVersion();

		/**
		 * The meta object literal for the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnResultsDocumentImpl <em>Results Document</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnResultsDocumentImpl
		 * @see edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnPackageImpl#getSimqpnResultsDocument()
		 * @generated
		 */
		EClass SIMQPN_RESULTS_DOCUMENT = eINSTANCE.getSimqpnResultsDocument();

		/**
		 * The meta object literal for the '<em><b>Mixed</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMQPN_RESULTS_DOCUMENT__MIXED = eINSTANCE.getSimqpnResultsDocument_Mixed();

		/**
		 * The meta object literal for the '<em><b>XMLNS Prefix Map</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMQPN_RESULTS_DOCUMENT__XMLNS_PREFIX_MAP = eINSTANCE.getSimqpnResultsDocument_XMLNSPrefixMap();

		/**
		 * The meta object literal for the '<em><b>XSI Schema Location</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMQPN_RESULTS_DOCUMENT__XSI_SCHEMA_LOCATION = eINSTANCE.getSimqpnResultsDocument_XSISchemaLocation();

		/**
		 * The meta object literal for the '<em><b>Simqpn Results</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SIMQPN_RESULTS_DOCUMENT__SIMQPN_RESULTS = eINSTANCE.getSimqpnResultsDocument_SimqpnResults();

	}

} //SimqpnPackage
