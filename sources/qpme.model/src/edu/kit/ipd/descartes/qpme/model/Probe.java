/**
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Probe</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Probe#getColorReferences <em>Color References</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Probe#getMetaAttributes <em>Meta Attributes</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Probe#getEndPlace <em>End Place</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Probe#getEndTrigger <em>End Trigger</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Probe#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Probe#getStartPlace <em>Start Place</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.Probe#getStartTrigger <em>Start Trigger</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getProbe()
 * @model extendedMetaData="name='probe' kind='elementOnly'"
 * @generated
 */
public interface Probe extends IdentifiableElement {
	/**
	 * Returns the value of the '<em><b>Color References</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Color References</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Color References</em>' containment reference.
	 * @see #setColorReferences(ColorReferencesContainer)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getProbe_ColorReferences()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='color-refs' namespace='##targetNamespace'"
	 * @generated
	 */
	ColorReferencesContainer getColorReferences();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Probe#getColorReferences <em>Color References</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Color References</em>' containment reference.
	 * @see #getColorReferences()
	 * @generated
	 */
	void setColorReferences(ColorReferencesContainer value);

	/**
	 * Returns the value of the '<em><b>Meta Attributes</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Meta Attributes</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Meta Attributes</em>' containment reference.
	 * @see #setMetaAttributes(ProbeMetaAttributesContainer)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getProbe_MetaAttributes()
	 * @model containment="true"
	 *        extendedMetaData="kind='element' name='meta-attributes' namespace='##targetNamespace'"
	 * @generated
	 */
	ProbeMetaAttributesContainer getMetaAttributes();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Probe#getMetaAttributes <em>Meta Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Meta Attributes</em>' containment reference.
	 * @see #getMetaAttributes()
	 * @generated
	 */
	void setMetaAttributes(ProbeMetaAttributesContainer value);

	/**
	 * Returns the value of the '<em><b>End Place</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Place</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Place</em>' reference.
	 * @see #setEndPlace(Place)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getProbe_EndPlace()
	 * @model resolveProxies="false" required="true"
	 *        extendedMetaData="kind='attribute' name='end-place-id' namespace='##targetNamespace'"
	 * @generated
	 */
	Place getEndPlace();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Probe#getEndPlace <em>End Place</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Place</em>' reference.
	 * @see #getEndPlace()
	 * @generated
	 */
	void setEndPlace(Place value);

	/**
	 * Returns the value of the '<em><b>End Trigger</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.kit.ipd.descartes.qpme.model.ProbeTrigger}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Trigger</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Trigger</em>' attribute.
	 * @see edu.kit.ipd.descartes.qpme.model.ProbeTrigger
	 * @see #isSetEndTrigger()
	 * @see #unsetEndTrigger()
	 * @see #setEndTrigger(ProbeTrigger)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getProbe_EndTrigger()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='end-trigger' namespace='##targetNamespace'"
	 * @generated
	 */
	ProbeTrigger getEndTrigger();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Probe#getEndTrigger <em>End Trigger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Trigger</em>' attribute.
	 * @see edu.kit.ipd.descartes.qpme.model.ProbeTrigger
	 * @see #isSetEndTrigger()
	 * @see #unsetEndTrigger()
	 * @see #getEndTrigger()
	 * @generated
	 */
	void setEndTrigger(ProbeTrigger value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Probe#getEndTrigger <em>End Trigger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetEndTrigger()
	 * @see #getEndTrigger()
	 * @see #setEndTrigger(ProbeTrigger)
	 * @generated
	 */
	void unsetEndTrigger();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.Probe#getEndTrigger <em>End Trigger</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>End Trigger</em>' attribute is set.
	 * @see #unsetEndTrigger()
	 * @see #getEndTrigger()
	 * @see #setEndTrigger(ProbeTrigger)
	 * @generated
	 */
	boolean isSetEndTrigger();

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
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getProbe_Name()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='name' namespace='##targetNamespace'"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Probe#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Start Place</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Place</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Place</em>' reference.
	 * @see #setStartPlace(Place)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getProbe_StartPlace()
	 * @model resolveProxies="false" required="true"
	 *        extendedMetaData="kind='attribute' name='start-place-id' namespace='##targetNamespace'"
	 * @generated
	 */
	Place getStartPlace();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Probe#getStartPlace <em>Start Place</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Place</em>' reference.
	 * @see #getStartPlace()
	 * @generated
	 */
	void setStartPlace(Place value);

	/**
	 * Returns the value of the '<em><b>Start Trigger</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.kit.ipd.descartes.qpme.model.ProbeTrigger}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Trigger</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Trigger</em>' attribute.
	 * @see edu.kit.ipd.descartes.qpme.model.ProbeTrigger
	 * @see #isSetStartTrigger()
	 * @see #unsetStartTrigger()
	 * @see #setStartTrigger(ProbeTrigger)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getProbe_StartTrigger()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='start-trigger' namespace='##targetNamespace'"
	 * @generated
	 */
	ProbeTrigger getStartTrigger();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Probe#getStartTrigger <em>Start Trigger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Trigger</em>' attribute.
	 * @see edu.kit.ipd.descartes.qpme.model.ProbeTrigger
	 * @see #isSetStartTrigger()
	 * @see #unsetStartTrigger()
	 * @see #getStartTrigger()
	 * @generated
	 */
	void setStartTrigger(ProbeTrigger value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.Probe#getStartTrigger <em>Start Trigger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetStartTrigger()
	 * @see #getStartTrigger()
	 * @see #setStartTrigger(ProbeTrigger)
	 * @generated
	 */
	void unsetStartTrigger();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.Probe#getStartTrigger <em>Start Trigger</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Start Trigger</em>' attribute is set.
	 * @see #unsetStartTrigger()
	 * @see #getStartTrigger()
	 * @see #setStartTrigger(ProbeTrigger)
	 * @generated
	 */
	boolean isSetStartTrigger();

} // Probe
