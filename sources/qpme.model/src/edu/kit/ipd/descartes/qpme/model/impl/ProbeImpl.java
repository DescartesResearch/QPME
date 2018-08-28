/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import edu.kit.ipd.descartes.qpme.model.ColorReferencesContainer;
import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.Place;
import edu.kit.ipd.descartes.qpme.model.Probe;
import edu.kit.ipd.descartes.qpme.model.ProbeMetaAttributesContainer;
import edu.kit.ipd.descartes.qpme.model.ProbeTrigger;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Probe</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.ProbeImpl#getColorReferences <em>Color References</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.ProbeImpl#getMetaAttributes <em>Meta Attributes</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.ProbeImpl#getEndPlace <em>End Place</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.ProbeImpl#getEndTrigger <em>End Trigger</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.ProbeImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.ProbeImpl#getStartPlace <em>Start Place</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.ProbeImpl#getStartTrigger <em>Start Trigger</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProbeImpl extends IdentifiableElementImpl implements Probe {
	/**
	 * The cached value of the '{@link #getColorReferences() <em>Color References</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColorReferences()
	 * @generated
	 * @ordered
	 */
	protected ColorReferencesContainer colorReferences;

	/**
	 * The cached value of the '{@link #getMetaAttributes() <em>Meta Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetaAttributes()
	 * @generated
	 * @ordered
	 */
	protected ProbeMetaAttributesContainer metaAttributes;

	/**
	 * The cached value of the '{@link #getEndPlace() <em>End Place</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndPlace()
	 * @generated
	 * @ordered
	 */
	protected Place endPlace;

	/**
	 * The default value of the '{@link #getEndTrigger() <em>End Trigger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndTrigger()
	 * @generated
	 * @ordered
	 */
	protected static final ProbeTrigger END_TRIGGER_EDEFAULT = ProbeTrigger.ENTRY;

	/**
	 * The cached value of the '{@link #getEndTrigger() <em>End Trigger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEndTrigger()
	 * @generated
	 * @ordered
	 */
	protected ProbeTrigger endTrigger = END_TRIGGER_EDEFAULT;

	/**
	 * This is true if the End Trigger attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean endTriggerESet;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getStartPlace() <em>Start Place</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartPlace()
	 * @generated
	 * @ordered
	 */
	protected Place startPlace;

	/**
	 * The default value of the '{@link #getStartTrigger() <em>Start Trigger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartTrigger()
	 * @generated
	 * @ordered
	 */
	protected static final ProbeTrigger START_TRIGGER_EDEFAULT = ProbeTrigger.ENTRY;

	/**
	 * The cached value of the '{@link #getStartTrigger() <em>Start Trigger</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStartTrigger()
	 * @generated
	 * @ordered
	 */
	protected ProbeTrigger startTrigger = START_TRIGGER_EDEFAULT;

	/**
	 * This is true if the Start Trigger attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean startTriggerESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProbeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.PROBE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ColorReferencesContainer getColorReferences() {
		return colorReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetColorReferences(ColorReferencesContainer newColorReferences, NotificationChain msgs) {
		ColorReferencesContainer oldColorReferences = colorReferences;
		colorReferences = newColorReferences;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.PROBE__COLOR_REFERENCES, oldColorReferences, newColorReferences);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setColorReferences(ColorReferencesContainer newColorReferences) {
		if (newColorReferences != colorReferences) {
			NotificationChain msgs = null;
			if (colorReferences != null)
				msgs = ((InternalEObject)colorReferences).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PROBE__COLOR_REFERENCES, null, msgs);
			if (newColorReferences != null)
				msgs = ((InternalEObject)newColorReferences).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PROBE__COLOR_REFERENCES, null, msgs);
			msgs = basicSetColorReferences(newColorReferences, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROBE__COLOR_REFERENCES, newColorReferences, newColorReferences));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbeMetaAttributesContainer getMetaAttributes() {
		return metaAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMetaAttributes(ProbeMetaAttributesContainer newMetaAttributes, NotificationChain msgs) {
		ProbeMetaAttributesContainer oldMetaAttributes = metaAttributes;
		metaAttributes = newMetaAttributes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.PROBE__META_ATTRIBUTES, oldMetaAttributes, newMetaAttributes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetaAttributes(ProbeMetaAttributesContainer newMetaAttributes) {
		if (newMetaAttributes != metaAttributes) {
			NotificationChain msgs = null;
			if (metaAttributes != null)
				msgs = ((InternalEObject)metaAttributes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PROBE__META_ATTRIBUTES, null, msgs);
			if (newMetaAttributes != null)
				msgs = ((InternalEObject)newMetaAttributes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PROBE__META_ATTRIBUTES, null, msgs);
			msgs = basicSetMetaAttributes(newMetaAttributes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROBE__META_ATTRIBUTES, newMetaAttributes, newMetaAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Place getEndPlace() {
		return endPlace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndPlace(Place newEndPlace) {
		Place oldEndPlace = endPlace;
		endPlace = newEndPlace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROBE__END_PLACE, oldEndPlace, endPlace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbeTrigger getEndTrigger() {
		return endTrigger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndTrigger(ProbeTrigger newEndTrigger) {
		ProbeTrigger oldEndTrigger = endTrigger;
		endTrigger = newEndTrigger == null ? END_TRIGGER_EDEFAULT : newEndTrigger;
		boolean oldEndTriggerESet = endTriggerESet;
		endTriggerESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROBE__END_TRIGGER, oldEndTrigger, endTrigger, !oldEndTriggerESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetEndTrigger() {
		ProbeTrigger oldEndTrigger = endTrigger;
		boolean oldEndTriggerESet = endTriggerESet;
		endTrigger = END_TRIGGER_EDEFAULT;
		endTriggerESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.PROBE__END_TRIGGER, oldEndTrigger, END_TRIGGER_EDEFAULT, oldEndTriggerESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetEndTrigger() {
		return endTriggerESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROBE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Place getStartPlace() {
		return startPlace;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartPlace(Place newStartPlace) {
		Place oldStartPlace = startPlace;
		startPlace = newStartPlace;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROBE__START_PLACE, oldStartPlace, startPlace));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbeTrigger getStartTrigger() {
		return startTrigger;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartTrigger(ProbeTrigger newStartTrigger) {
		ProbeTrigger oldStartTrigger = startTrigger;
		startTrigger = newStartTrigger == null ? START_TRIGGER_EDEFAULT : newStartTrigger;
		boolean oldStartTriggerESet = startTriggerESet;
		startTriggerESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PROBE__START_TRIGGER, oldStartTrigger, startTrigger, !oldStartTriggerESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetStartTrigger() {
		ProbeTrigger oldStartTrigger = startTrigger;
		boolean oldStartTriggerESet = startTriggerESet;
		startTrigger = START_TRIGGER_EDEFAULT;
		startTriggerESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.PROBE__START_TRIGGER, oldStartTrigger, START_TRIGGER_EDEFAULT, oldStartTriggerESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetStartTrigger() {
		return startTriggerESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.PROBE__COLOR_REFERENCES:
				return basicSetColorReferences(null, msgs);
			case ModelPackage.PROBE__META_ATTRIBUTES:
				return basicSetMetaAttributes(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.PROBE__COLOR_REFERENCES:
				return getColorReferences();
			case ModelPackage.PROBE__META_ATTRIBUTES:
				return getMetaAttributes();
			case ModelPackage.PROBE__END_PLACE:
				return getEndPlace();
			case ModelPackage.PROBE__END_TRIGGER:
				return getEndTrigger();
			case ModelPackage.PROBE__NAME:
				return getName();
			case ModelPackage.PROBE__START_PLACE:
				return getStartPlace();
			case ModelPackage.PROBE__START_TRIGGER:
				return getStartTrigger();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.PROBE__COLOR_REFERENCES:
				setColorReferences((ColorReferencesContainer)newValue);
				return;
			case ModelPackage.PROBE__META_ATTRIBUTES:
				setMetaAttributes((ProbeMetaAttributesContainer)newValue);
				return;
			case ModelPackage.PROBE__END_PLACE:
				setEndPlace((Place)newValue);
				return;
			case ModelPackage.PROBE__END_TRIGGER:
				setEndTrigger((ProbeTrigger)newValue);
				return;
			case ModelPackage.PROBE__NAME:
				setName((String)newValue);
				return;
			case ModelPackage.PROBE__START_PLACE:
				setStartPlace((Place)newValue);
				return;
			case ModelPackage.PROBE__START_TRIGGER:
				setStartTrigger((ProbeTrigger)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelPackage.PROBE__COLOR_REFERENCES:
				setColorReferences((ColorReferencesContainer)null);
				return;
			case ModelPackage.PROBE__META_ATTRIBUTES:
				setMetaAttributes((ProbeMetaAttributesContainer)null);
				return;
			case ModelPackage.PROBE__END_PLACE:
				setEndPlace((Place)null);
				return;
			case ModelPackage.PROBE__END_TRIGGER:
				unsetEndTrigger();
				return;
			case ModelPackage.PROBE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ModelPackage.PROBE__START_PLACE:
				setStartPlace((Place)null);
				return;
			case ModelPackage.PROBE__START_TRIGGER:
				unsetStartTrigger();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelPackage.PROBE__COLOR_REFERENCES:
				return colorReferences != null;
			case ModelPackage.PROBE__META_ATTRIBUTES:
				return metaAttributes != null;
			case ModelPackage.PROBE__END_PLACE:
				return endPlace != null;
			case ModelPackage.PROBE__END_TRIGGER:
				return isSetEndTrigger();
			case ModelPackage.PROBE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ModelPackage.PROBE__START_PLACE:
				return startPlace != null;
			case ModelPackage.PROBE__START_TRIGGER:
				return isSetStartTrigger();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (endTrigger: ");
		if (endTriggerESet) result.append(endTrigger); else result.append("<unset>");
		result.append(", name: ");
		result.append(name);
		result.append(", startTrigger: ");
		if (startTriggerESet) result.append(startTrigger); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //ProbeImpl
