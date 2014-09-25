/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage
 * @generated
 */
public interface SimqpnFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SimqpnFactory eINSTANCE = edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Bucket</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Bucket</em>'.
	 * @generated
	 */
	Bucket createBucket();

	/**
	 * Returns a new object of class '<em>Buckets</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Buckets</em>'.
	 * @generated
	 */
	Buckets createBuckets();

	/**
	 * Returns a new object of class '<em>Color</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Color</em>'.
	 * @generated
	 */
	Color createColor();

	/**
	 * Returns a new object of class '<em>Histogram</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Histogram</em>'.
	 * @generated
	 */
	Histogram createHistogram();

	/**
	 * Returns a new object of class '<em>Metric</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric</em>'.
	 * @generated
	 */
	Metric createMetric();

	/**
	 * Returns a new object of class '<em>Observed Element</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Observed Element</em>'.
	 * @generated
	 */
	ObservedElement createObservedElement();

	/**
	 * Returns a new object of class '<em>Percentile</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Percentile</em>'.
	 * @generated
	 */
	Percentile createPercentile();

	/**
	 * Returns a new object of class '<em>Percentiles</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Percentiles</em>'.
	 * @generated
	 */
	Percentiles createPercentiles();

	/**
	 * Returns a new object of class '<em>Results</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Results</em>'.
	 * @generated
	 */
	SimqpnResults createSimqpnResults();

	/**
	 * Returns a new object of class '<em>Results Document</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Results Document</em>'.
	 * @generated
	 */
	SimqpnResultsDocument createSimqpnResultsDocument();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SimqpnPackage getSimqpnPackage();

} //SimqpnFactory
