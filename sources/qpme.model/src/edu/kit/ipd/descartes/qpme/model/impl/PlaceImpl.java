/**
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import edu.kit.ipd.descartes.qpme.model.ColorReferencesContainer;
import edu.kit.ipd.descartes.qpme.model.DepartureDiscipline;
import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.Place;
import edu.kit.ipd.descartes.qpme.model.PlaceMetaAttributesContainer;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Place</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceImpl#getColorReferences <em>Color References</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceImpl#getMetaAttributes <em>Meta Attributes</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceImpl#getDepartureDiscipline <em>Departure Discipline</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceImpl#isLocked <em>Locked</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class PlaceImpl extends PlaceTransitionElementImpl implements Place {
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
	protected PlaceMetaAttributesContainer metaAttributes;

	/**
	 * The default value of the '{@link #getDepartureDiscipline() <em>Departure Discipline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDepartureDiscipline()
	 * @generated
	 * @ordered
	 */
	protected static final DepartureDiscipline DEPARTURE_DISCIPLINE_EDEFAULT = DepartureDiscipline.NORMAL;

	/**
	 * The cached value of the '{@link #getDepartureDiscipline() <em>Departure Discipline</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDepartureDiscipline()
	 * @generated
	 * @ordered
	 */
	protected DepartureDiscipline departureDiscipline = DEPARTURE_DISCIPLINE_EDEFAULT;

	/**
	 * This is true if the Departure Discipline attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean departureDisciplineESet;

	/**
	 * The default value of the '{@link #isLocked() <em>Locked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLocked()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LOCKED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isLocked() <em>Locked</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLocked()
	 * @generated
	 * @ordered
	 */
	protected boolean locked = LOCKED_EDEFAULT;

	/**
	 * This is true if the Locked attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean lockedESet;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PlaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.PLACE;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.PLACE__COLOR_REFERENCES, oldColorReferences, newColorReferences);
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
				msgs = ((InternalEObject)colorReferences).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PLACE__COLOR_REFERENCES, null, msgs);
			if (newColorReferences != null)
				msgs = ((InternalEObject)newColorReferences).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PLACE__COLOR_REFERENCES, null, msgs);
			msgs = basicSetColorReferences(newColorReferences, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PLACE__COLOR_REFERENCES, newColorReferences, newColorReferences));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlaceMetaAttributesContainer getMetaAttributes() {
		return metaAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMetaAttributes(PlaceMetaAttributesContainer newMetaAttributes, NotificationChain msgs) {
		PlaceMetaAttributesContainer oldMetaAttributes = metaAttributes;
		metaAttributes = newMetaAttributes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.PLACE__META_ATTRIBUTES, oldMetaAttributes, newMetaAttributes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetaAttributes(PlaceMetaAttributesContainer newMetaAttributes) {
		if (newMetaAttributes != metaAttributes) {
			NotificationChain msgs = null;
			if (metaAttributes != null)
				msgs = ((InternalEObject)metaAttributes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PLACE__META_ATTRIBUTES, null, msgs);
			if (newMetaAttributes != null)
				msgs = ((InternalEObject)newMetaAttributes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.PLACE__META_ATTRIBUTES, null, msgs);
			msgs = basicSetMetaAttributes(newMetaAttributes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PLACE__META_ATTRIBUTES, newMetaAttributes, newMetaAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DepartureDiscipline getDepartureDiscipline() {
		return departureDiscipline;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDepartureDiscipline(DepartureDiscipline newDepartureDiscipline) {
		DepartureDiscipline oldDepartureDiscipline = departureDiscipline;
		departureDiscipline = newDepartureDiscipline == null ? DEPARTURE_DISCIPLINE_EDEFAULT : newDepartureDiscipline;
		boolean oldDepartureDisciplineESet = departureDisciplineESet;
		departureDisciplineESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PLACE__DEPARTURE_DISCIPLINE, oldDepartureDiscipline, departureDiscipline, !oldDepartureDisciplineESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDepartureDiscipline() {
		DepartureDiscipline oldDepartureDiscipline = departureDiscipline;
		boolean oldDepartureDisciplineESet = departureDisciplineESet;
		departureDiscipline = DEPARTURE_DISCIPLINE_EDEFAULT;
		departureDisciplineESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.PLACE__DEPARTURE_DISCIPLINE, oldDepartureDiscipline, DEPARTURE_DISCIPLINE_EDEFAULT, oldDepartureDisciplineESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDepartureDiscipline() {
		return departureDisciplineESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocked(boolean newLocked) {
		boolean oldLocked = locked;
		locked = newLocked;
		boolean oldLockedESet = lockedESet;
		lockedESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PLACE__LOCKED, oldLocked, locked, !oldLockedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLocked() {
		boolean oldLocked = locked;
		boolean oldLockedESet = lockedESet;
		locked = LOCKED_EDEFAULT;
		lockedESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.PLACE__LOCKED, oldLocked, LOCKED_EDEFAULT, oldLockedESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLocked() {
		return lockedESet;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PLACE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.PLACE__COLOR_REFERENCES:
				return basicSetColorReferences(null, msgs);
			case ModelPackage.PLACE__META_ATTRIBUTES:
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
			case ModelPackage.PLACE__COLOR_REFERENCES:
				return getColorReferences();
			case ModelPackage.PLACE__META_ATTRIBUTES:
				return getMetaAttributes();
			case ModelPackage.PLACE__DEPARTURE_DISCIPLINE:
				return getDepartureDiscipline();
			case ModelPackage.PLACE__LOCKED:
				return isLocked();
			case ModelPackage.PLACE__NAME:
				return getName();
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
			case ModelPackage.PLACE__COLOR_REFERENCES:
				setColorReferences((ColorReferencesContainer)newValue);
				return;
			case ModelPackage.PLACE__META_ATTRIBUTES:
				setMetaAttributes((PlaceMetaAttributesContainer)newValue);
				return;
			case ModelPackage.PLACE__DEPARTURE_DISCIPLINE:
				setDepartureDiscipline((DepartureDiscipline)newValue);
				return;
			case ModelPackage.PLACE__LOCKED:
				setLocked((Boolean)newValue);
				return;
			case ModelPackage.PLACE__NAME:
				setName((String)newValue);
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
			case ModelPackage.PLACE__COLOR_REFERENCES:
				setColorReferences((ColorReferencesContainer)null);
				return;
			case ModelPackage.PLACE__META_ATTRIBUTES:
				setMetaAttributes((PlaceMetaAttributesContainer)null);
				return;
			case ModelPackage.PLACE__DEPARTURE_DISCIPLINE:
				unsetDepartureDiscipline();
				return;
			case ModelPackage.PLACE__LOCKED:
				unsetLocked();
				return;
			case ModelPackage.PLACE__NAME:
				setName(NAME_EDEFAULT);
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
			case ModelPackage.PLACE__COLOR_REFERENCES:
				return colorReferences != null;
			case ModelPackage.PLACE__META_ATTRIBUTES:
				return metaAttributes != null;
			case ModelPackage.PLACE__DEPARTURE_DISCIPLINE:
				return isSetDepartureDiscipline();
			case ModelPackage.PLACE__LOCKED:
				return isSetLocked();
			case ModelPackage.PLACE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(" (departureDiscipline: ");
		if (departureDisciplineESet) result.append(departureDiscipline); else result.append("<unset>");
		result.append(", locked: ");
		if (lockedESet) result.append(locked); else result.append("<unset>");
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //PlaceImpl
