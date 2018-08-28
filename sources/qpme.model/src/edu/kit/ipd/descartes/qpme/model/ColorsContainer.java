/**
 */
package edu.kit.ipd.descartes.qpme.model;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Colors Container</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.ColorsContainer#getDefinitions <em>Definitions</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getColorsContainer()
 * @model extendedMetaData="name='colors-container' kind='elementOnly'"
 * @generated
 */
public interface ColorsContainer extends EObject {
	/**
	 * Returns the value of the '<em><b>Definitions</b></em>' containment reference list.
	 * The list contents are of type {@link edu.kit.ipd.descartes.qpme.model.Color}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Definitions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Definitions</em>' containment reference list.
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getColorsContainer_Definitions()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='color' namespace='##targetNamespace'"
	 * @generated
	 */
	EList<Color> getDefinitions();

} // ColorsContainer
