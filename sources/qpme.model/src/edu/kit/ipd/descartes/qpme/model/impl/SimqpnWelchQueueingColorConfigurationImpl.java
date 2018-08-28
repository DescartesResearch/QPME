/**
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.SimqpnWelchQueueingColorConfiguration;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simqpn Welch Queueing Color Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnWelchQueueingColorConfigurationImpl#getQueueMaxObsrv <em>Queue Max Obsrv</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnWelchQueueingColorConfigurationImpl#getQueueMinObsrv <em>Queue Min Obsrv</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SimqpnWelchQueueingColorConfigurationImpl extends SimqpnWelchColorConfigurationImpl implements SimqpnWelchQueueingColorConfiguration {
	/**
	 * The default value of the '{@link #getQueueMaxObsrv() <em>Queue Max Obsrv</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueMaxObsrv()
	 * @generated
	 * @ordered
	 */
	protected static final long QUEUE_MAX_OBSRV_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getQueueMaxObsrv() <em>Queue Max Obsrv</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueMaxObsrv()
	 * @generated
	 * @ordered
	 */
	protected long queueMaxObsrv = QUEUE_MAX_OBSRV_EDEFAULT;

	/**
	 * This is true if the Queue Max Obsrv attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean queueMaxObsrvESet;

	/**
	 * The default value of the '{@link #getQueueMinObsrv() <em>Queue Min Obsrv</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueMinObsrv()
	 * @generated
	 * @ordered
	 */
	protected static final long QUEUE_MIN_OBSRV_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getQueueMinObsrv() <em>Queue Min Obsrv</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueMinObsrv()
	 * @generated
	 * @ordered
	 */
	protected long queueMinObsrv = QUEUE_MIN_OBSRV_EDEFAULT;

	/**
	 * This is true if the Queue Min Obsrv attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean queueMinObsrvESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimqpnWelchQueueingColorConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getQueueMaxObsrv() {
		return queueMaxObsrv;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueueMaxObsrv(long newQueueMaxObsrv) {
		long oldQueueMaxObsrv = queueMaxObsrv;
		queueMaxObsrv = newQueueMaxObsrv;
		boolean oldQueueMaxObsrvESet = queueMaxObsrvESet;
		queueMaxObsrvESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_OBSRV, oldQueueMaxObsrv, queueMaxObsrv, !oldQueueMaxObsrvESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetQueueMaxObsrv() {
		long oldQueueMaxObsrv = queueMaxObsrv;
		boolean oldQueueMaxObsrvESet = queueMaxObsrvESet;
		queueMaxObsrv = QUEUE_MAX_OBSRV_EDEFAULT;
		queueMaxObsrvESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_OBSRV, oldQueueMaxObsrv, QUEUE_MAX_OBSRV_EDEFAULT, oldQueueMaxObsrvESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetQueueMaxObsrv() {
		return queueMaxObsrvESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getQueueMinObsrv() {
		return queueMinObsrv;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueueMinObsrv(long newQueueMinObsrv) {
		long oldQueueMinObsrv = queueMinObsrv;
		queueMinObsrv = newQueueMinObsrv;
		boolean oldQueueMinObsrvESet = queueMinObsrvESet;
		queueMinObsrvESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_OBSRV, oldQueueMinObsrv, queueMinObsrv, !oldQueueMinObsrvESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetQueueMinObsrv() {
		long oldQueueMinObsrv = queueMinObsrv;
		boolean oldQueueMinObsrvESet = queueMinObsrvESet;
		queueMinObsrv = QUEUE_MIN_OBSRV_EDEFAULT;
		queueMinObsrvESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_OBSRV, oldQueueMinObsrv, QUEUE_MIN_OBSRV_EDEFAULT, oldQueueMinObsrvESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetQueueMinObsrv() {
		return queueMinObsrvESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_OBSRV:
				return getQueueMaxObsrv();
			case ModelPackage.SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_OBSRV:
				return getQueueMinObsrv();
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
			case ModelPackage.SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_OBSRV:
				setQueueMaxObsrv((Long)newValue);
				return;
			case ModelPackage.SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_OBSRV:
				setQueueMinObsrv((Long)newValue);
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
			case ModelPackage.SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_OBSRV:
				unsetQueueMaxObsrv();
				return;
			case ModelPackage.SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_OBSRV:
				unsetQueueMinObsrv();
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
			case ModelPackage.SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MAX_OBSRV:
				return isSetQueueMaxObsrv();
			case ModelPackage.SIMQPN_WELCH_QUEUEING_COLOR_CONFIGURATION__QUEUE_MIN_OBSRV:
				return isSetQueueMinObsrv();
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
		result.append(" (queueMaxObsrv: ");
		if (queueMaxObsrvESet) result.append(queueMaxObsrv); else result.append("<unset>");
		result.append(", queueMinObsrv: ");
		if (queueMinObsrvESet) result.append(queueMinObsrv); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //SimqpnWelchQueueingColorConfigurationImpl
