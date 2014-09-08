/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Net Meta Attributes Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.NetMetaAttributesContainer#getEntries <em>Entries</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getNetMetaAttributesContainer()
 * @model extendedMetaData="name='net-meta-attributes-container' kind='elementOnly'"
 * @generated
 */
public interface NetMetaAttributesContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Entries</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.ipd.descartes.qpme.model.NetMetaAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entries</em>' containment reference list.
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getNetMetaAttributesContainer_Entries()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='meta-attribute' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<NetMetaAttribute> getEntries();

} // NetMetaAttributesContainer
