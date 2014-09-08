/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model.impl;

import edu.kit.ipd.descartes.qpme.simqpn.model.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SimqpnFactoryImpl extends EFactoryImpl implements SimqpnFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SimqpnFactory init() {
		try {
			SimqpnFactory theSimqpnFactory = (SimqpnFactory)EPackage.Registry.INSTANCE.getEFactory(SimqpnPackage.eNS_URI);
			if (theSimqpnFactory != null) {
				return theSimqpnFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SimqpnFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SimqpnPackage.BUCKET: return createBucket();
			case SimqpnPackage.BUCKETS: return createBuckets();
			case SimqpnPackage.COLOR: return createColor();
			case SimqpnPackage.HISTOGRAM: return createHistogram();
			case SimqpnPackage.METRIC: return createMetric();
			case SimqpnPackage.OBSERVED_ELEMENT: return createObservedElement();
			case SimqpnPackage.PERCENTILE: return createPercentile();
			case SimqpnPackage.PERCENTILES: return createPercentiles();
			case SimqpnPackage.SIMQPN_RESULTS: return createSimqpnResults();
			case SimqpnPackage.SIMQPN_RESULTS_DOCUMENT: return createSimqpnResultsDocument();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Bucket createBucket() {
		BucketImpl bucket = new BucketImpl();
		return bucket;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Buckets createBuckets() {
		BucketsImpl buckets = new BucketsImpl();
		return buckets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Color createColor() {
		ColorImpl color = new ColorImpl();
		return color;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Histogram createHistogram() {
		HistogramImpl histogram = new HistogramImpl();
		return histogram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Metric createMetric() {
		MetricImpl metric = new MetricImpl();
		return metric;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ObservedElement createObservedElement() {
		ObservedElementImpl observedElement = new ObservedElementImpl();
		return observedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Percentile createPercentile() {
		PercentileImpl percentile = new PercentileImpl();
		return percentile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Percentiles createPercentiles() {
		PercentilesImpl percentiles = new PercentilesImpl();
		return percentiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnResults createSimqpnResults() {
		SimqpnResultsImpl simqpnResults = new SimqpnResultsImpl();
		return simqpnResults;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnResultsDocument createSimqpnResultsDocument() {
		SimqpnResultsDocumentImpl simqpnResultsDocument = new SimqpnResultsDocumentImpl();
		return simqpnResultsDocument;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnPackage getSimqpnPackage() {
		return (SimqpnPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SimqpnPackage getPackage() {
		return SimqpnPackage.eINSTANCE;
	}

} //SimqpnFactoryImpl
