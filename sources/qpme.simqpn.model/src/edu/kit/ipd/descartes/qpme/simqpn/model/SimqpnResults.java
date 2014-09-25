/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model;

import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Results</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getObservedElement <em>Observed Element</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getConfigurationName <em>Configuration Name</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getDate <em>Date</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getModelFile <em>Model File</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getQpmeVersion <em>Qpme Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getSimqpnResults()
 * @model extendedMetaData="name='simqpn-results' kind='elementOnly'"
 * @generated
 */
public interface SimqpnResults extends EObject {
	/**
	 * Returns the value of the '<em><b>Observed Element</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Observed Element</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Observed Element</em>' containment reference list.
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getSimqpnResults_ObservedElement()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='observed-element' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ObservedElement> getObservedElement();

	/**
	 * Returns the value of the '<em><b>Configuration Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configuration Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configuration Name</em>' attribute.
	 * @see #setConfigurationName(String)
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getSimqpnResults_ConfigurationName()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='configuration-name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getConfigurationName();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getConfigurationName <em>Configuration Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configuration Name</em>' attribute.
	 * @see #getConfigurationName()
	 * @generated
	 */
	void setConfigurationName(String value);

	/**
	 * Returns the value of the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date</em>' attribute.
	 * @see #setDate(XMLGregorianCalendar)
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getSimqpnResults_Date()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.DateTime" required="true"
	 *        extendedMetaData="kind='attribute' name='date' namespace='##targetNamespace'"
	 * @generated
	 */
	XMLGregorianCalendar getDate();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getDate <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date</em>' attribute.
	 * @see #getDate()
	 * @generated
	 */
	void setDate(XMLGregorianCalendar value);

	/**
	 * Returns the value of the '<em><b>Model File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model File</em>' attribute.
	 * @see #setModelFile(String)
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getSimqpnResults_ModelFile()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='model-file' namespace='##targetNamespace'"
	 * @generated
	 */
	String getModelFile();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getModelFile <em>Model File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model File</em>' attribute.
	 * @see #getModelFile()
	 * @generated
	 */
	void setModelFile(String value);

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getSimqpnResults_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Qpme Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Qpme Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Qpme Version</em>' attribute.
	 * @see #setQpmeVersion(String)
	 * @see edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage#getSimqpnResults_QpmeVersion()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='qpme-version' namespace='##targetNamespace'"
	 * @generated
	 */
	String getQpmeVersion();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults#getQpmeVersion <em>Qpme Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Qpme Version</em>' attribute.
	 * @see #getQpmeVersion()
	 * @generated
	 */
	void setQpmeVersion(String value);

} // SimqpnResults
