/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Results Document</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResultsDocument#getMixed <em>Mixed</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResultsDocument#getXMLNSPrefixMap <em>XMLNS Prefix Map</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResultsDocument#getXSISchemaLocation <em>XSI Schema Location</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResultsDocument#getSimqpnResults <em>Simqpn Results</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getSimqpnResultsDocument()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface SimqpnResultsDocument extends EObject {
	/**
	 * Returns the value of the '<em><b>Mixed</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mixed</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mixed</em>' attribute list.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getSimqpnResultsDocument_Mixed()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='elementWildcard' name=':mixed'"
	 * @generated
	 */
	FeatureMap getMixed();

	/**
	 * Returns the value of the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XMLNS Prefix Map</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XMLNS Prefix Map</em>' map.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getSimqpnResultsDocument_XMLNSPrefixMap()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xmlns:prefix'"
	 * @generated
	 */
	EMap<String, String> getXMLNSPrefixMap();

	/**
	 * Returns the value of the '<em><b>XSI Schema Location</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XSI Schema Location</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XSI Schema Location</em>' map.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getSimqpnResultsDocument_XSISchemaLocation()
	 * @model mapType="org.eclipse.emf.ecore.EStringToStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>" transient="true"
	 *        extendedMetaData="kind='attribute' name='xsi:schemaLocation'"
	 * @generated
	 */
	EMap<String, String> getXSISchemaLocation();

	/**
	 * Returns the value of the '<em><b>Simqpn Results</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Simqpn Results</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Simqpn Results</em>' containment reference list.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getSimqpnResultsDocument_SimqpnResults()
	 * @model containment="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='simqpn-results' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<SimqpnResults> getSimqpnResults();

} // SimqpnResultsDocument
