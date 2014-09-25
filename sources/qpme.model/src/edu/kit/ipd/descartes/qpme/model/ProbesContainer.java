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
 * A representation of the model object '<em><b>Probes Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.ProbesContainer#getDefinitions <em>Definitions</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getProbesContainer()
 * @model extendedMetaData="name='probes-container' kind='elementOnly'"
 * @generated
 */
public interface ProbesContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Definitions</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.ipd.descartes.qpme.model.Probe}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Definitions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Definitions</em>' containment reference list.
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getProbesContainer_Definitions()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='probe' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Probe> getDefinitions();

} // ProbesContainer
