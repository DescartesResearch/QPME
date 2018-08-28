/**
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import edu.kit.ipd.descartes.qpme.model.LocationAttribute;
import edu.kit.ipd.descartes.qpme.model.ModelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Location Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.LocationAttributeImpl#getLocationX <em>Location X</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.LocationAttributeImpl#getLocationY <em>Location Y</em>}</li>
 * </ul>
 *
 * @generated
 */
public class LocationAttributeImpl extends PlaceMetaAttributeImpl implements LocationAttribute {
	/**
	 * The default value of the '{@link #getLocationX() <em>Location X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocationX()
	 * @generated
	 * @ordered
	 */
	protected static final int LOCATION_X_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLocationX() <em>Location X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocationX()
	 * @generated
	 * @ordered
	 */
	protected int locationX = LOCATION_X_EDEFAULT;

	/**
	 * This is true if the Location X attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean locationXESet;

	/**
	 * The default value of the '{@link #getLocationY() <em>Location Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocationY()
	 * @generated
	 * @ordered
	 */
	protected static final int LOCATION_Y_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getLocationY() <em>Location Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocationY()
	 * @generated
	 * @ordered
	 */
	protected int locationY = LOCATION_Y_EDEFAULT;

	/**
	 * This is true if the Location Y attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean locationYESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LocationAttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.LOCATION_ATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLocationX() {
		return locationX;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocationX(int newLocationX) {
		int oldLocationX = locationX;
		locationX = newLocationX;
		boolean oldLocationXESet = locationXESet;
		locationXESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LOCATION_ATTRIBUTE__LOCATION_X, oldLocationX, locationX, !oldLocationXESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLocationX() {
		int oldLocationX = locationX;
		boolean oldLocationXESet = locationXESet;
		locationX = LOCATION_X_EDEFAULT;
		locationXESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.LOCATION_ATTRIBUTE__LOCATION_X, oldLocationX, LOCATION_X_EDEFAULT, oldLocationXESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLocationX() {
		return locationXESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getLocationY() {
		return locationY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocationY(int newLocationY) {
		int oldLocationY = locationY;
		locationY = newLocationY;
		boolean oldLocationYESet = locationYESet;
		locationYESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.LOCATION_ATTRIBUTE__LOCATION_Y, oldLocationY, locationY, !oldLocationYESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLocationY() {
		int oldLocationY = locationY;
		boolean oldLocationYESet = locationYESet;
		locationY = LOCATION_Y_EDEFAULT;
		locationYESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.LOCATION_ATTRIBUTE__LOCATION_Y, oldLocationY, LOCATION_Y_EDEFAULT, oldLocationYESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLocationY() {
		return locationYESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.LOCATION_ATTRIBUTE__LOCATION_X:
				return getLocationX();
			case ModelPackage.LOCATION_ATTRIBUTE__LOCATION_Y:
				return getLocationY();
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
			case ModelPackage.LOCATION_ATTRIBUTE__LOCATION_X:
				setLocationX((Integer)newValue);
				return;
			case ModelPackage.LOCATION_ATTRIBUTE__LOCATION_Y:
				setLocationY((Integer)newValue);
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
			case ModelPackage.LOCATION_ATTRIBUTE__LOCATION_X:
				unsetLocationX();
				return;
			case ModelPackage.LOCATION_ATTRIBUTE__LOCATION_Y:
				unsetLocationY();
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
			case ModelPackage.LOCATION_ATTRIBUTE__LOCATION_X:
				return isSetLocationX();
			case ModelPackage.LOCATION_ATTRIBUTE__LOCATION_Y:
				return isSetLocationY();
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
		result.append(" (locationX: ");
		if (locationXESet) result.append(locationX); else result.append("<unset>");
		result.append(", locationY: ");
		if (locationYESet) result.append(locationY); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //LocationAttributeImpl
