/**
 */
package edu.kit.ipd.descartes.qpme.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Probe Meta Attributes Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.ProbeMetaAttributesContainer#getEntries <em>Entries</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getProbeMetaAttributesContainer()
 * @model extendedMetaData="name='probe-meta-attributes-container' kind='elementOnly'"
 * @generated
 */
public interface ProbeMetaAttributesContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Entries</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.ipd.descartes.qpme.model.ProbeMetaAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entries</em>' containment reference list.
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getProbeMetaAttributesContainer_Entries()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='meta-attribute' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<ProbeMetaAttribute> getEntries();

} // ProbeMetaAttributesContainer
