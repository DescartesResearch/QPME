/**
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.SimqpnWelchColorConfiguration;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simqpn Welch Color Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnWelchColorConfigurationImpl#getMaxObsrv <em>Max Obsrv</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnWelchColorConfigurationImpl#getMinObsrv <em>Min Obsrv</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SimqpnWelchColorConfigurationImpl extends SimqpnMetaAttributeImpl implements SimqpnWelchColorConfiguration {
	/**
	 * The default value of the '{@link #getMaxObsrv() <em>Max Obsrv</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxObsrv()
	 * @generated
	 * @ordered
	 */
	protected static final long MAX_OBSRV_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getMaxObsrv() <em>Max Obsrv</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaxObsrv()
	 * @generated
	 * @ordered
	 */
	protected long maxObsrv = MAX_OBSRV_EDEFAULT;

	/**
	 * This is true if the Max Obsrv attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean maxObsrvESet;

	/**
	 * The default value of the '{@link #getMinObsrv() <em>Min Obsrv</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinObsrv()
	 * @generated
	 * @ordered
	 */
	protected static final long MIN_OBSRV_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getMinObsrv() <em>Min Obsrv</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMinObsrv()
	 * @generated
	 * @ordered
	 */
	protected long minObsrv = MIN_OBSRV_EDEFAULT;

	/**
	 * This is true if the Min Obsrv attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean minObsrvESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimqpnWelchColorConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SIMQPN_WELCH_COLOR_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getMaxObsrv() {
		return maxObsrv;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxObsrv(long newMaxObsrv) {
		long oldMaxObsrv = maxObsrv;
		maxObsrv = newMaxObsrv;
		boolean oldMaxObsrvESet = maxObsrvESet;
		maxObsrvESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_WELCH_COLOR_CONFIGURATION__MAX_OBSRV, oldMaxObsrv, maxObsrv, !oldMaxObsrvESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMaxObsrv() {
		long oldMaxObsrv = maxObsrv;
		boolean oldMaxObsrvESet = maxObsrvESet;
		maxObsrv = MAX_OBSRV_EDEFAULT;
		maxObsrvESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_WELCH_COLOR_CONFIGURATION__MAX_OBSRV, oldMaxObsrv, MAX_OBSRV_EDEFAULT, oldMaxObsrvESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMaxObsrv() {
		return maxObsrvESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getMinObsrv() {
		return minObsrv;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinObsrv(long newMinObsrv) {
		long oldMinObsrv = minObsrv;
		minObsrv = newMinObsrv;
		boolean oldMinObsrvESet = minObsrvESet;
		minObsrvESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_WELCH_COLOR_CONFIGURATION__MIN_OBSRV, oldMinObsrv, minObsrv, !oldMinObsrvESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMinObsrv() {
		long oldMinObsrv = minObsrv;
		boolean oldMinObsrvESet = minObsrvESet;
		minObsrv = MIN_OBSRV_EDEFAULT;
		minObsrvESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_WELCH_COLOR_CONFIGURATION__MIN_OBSRV, oldMinObsrv, MIN_OBSRV_EDEFAULT, oldMinObsrvESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMinObsrv() {
		return minObsrvESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.SIMQPN_WELCH_COLOR_CONFIGURATION__MAX_OBSRV:
				return getMaxObsrv();
			case ModelPackage.SIMQPN_WELCH_COLOR_CONFIGURATION__MIN_OBSRV:
				return getMinObsrv();
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
			case ModelPackage.SIMQPN_WELCH_COLOR_CONFIGURATION__MAX_OBSRV:
				setMaxObsrv((Long)newValue);
				return;
			case ModelPackage.SIMQPN_WELCH_COLOR_CONFIGURATION__MIN_OBSRV:
				setMinObsrv((Long)newValue);
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
			case ModelPackage.SIMQPN_WELCH_COLOR_CONFIGURATION__MAX_OBSRV:
				unsetMaxObsrv();
				return;
			case ModelPackage.SIMQPN_WELCH_COLOR_CONFIGURATION__MIN_OBSRV:
				unsetMinObsrv();
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
			case ModelPackage.SIMQPN_WELCH_COLOR_CONFIGURATION__MAX_OBSRV:
				return isSetMaxObsrv();
			case ModelPackage.SIMQPN_WELCH_COLOR_CONFIGURATION__MIN_OBSRV:
				return isSetMinObsrv();
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
		result.append(" (maxObsrv: ");
		if (maxObsrvESet) result.append(maxObsrv); else result.append("<unset>");
		result.append(", minObsrv: ");
		if (minObsrvESet) result.append(minObsrv); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //SimqpnWelchColorConfigurationImpl
