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
 * A representation of the model object '<em><b>Transition Meta Attributes Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.TransitionMetaAttributesContainer#getEntries <em>Entries</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getTransitionMetaAttributesContainer()
 * @model extendedMetaData="name='transition-meta-attributes-container' kind='elementOnly'"
 * @generated
 */
public interface TransitionMetaAttributesContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Entries</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.ipd.descartes.qpme.model.TransitionMetaAttribute}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entries</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entries</em>' containment reference list.
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getTransitionMetaAttributesContainer_Entries()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='meta-attribute' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<TransitionMetaAttribute> getEntries();

} // TransitionMetaAttributesContainer
