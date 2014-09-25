/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.SimqpnReplDelQueueingColorConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simqpn Repl Del Queueing Color Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnReplDelQueueingColorConfigurationImpl#getQueueSignLevAvgST <em>Queue Sign Lev Avg ST</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimqpnReplDelQueueingColorConfigurationImpl extends SimqpnReplDelColorConfigurationImpl implements SimqpnReplDelQueueingColorConfiguration {
	/**
	 * The default value of the '{@link #getQueueSignLevAvgST() <em>Queue Sign Lev Avg ST</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueSignLevAvgST()
	 * @generated
	 * @ordered
	 */
	protected static final double QUEUE_SIGN_LEV_AVG_ST_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getQueueSignLevAvgST() <em>Queue Sign Lev Avg ST</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueueSignLevAvgST()
	 * @generated
	 * @ordered
	 */
	protected double queueSignLevAvgST = QUEUE_SIGN_LEV_AVG_ST_EDEFAULT;

	/**
	 * This is true if the Queue Sign Lev Avg ST attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean queueSignLevAvgSTESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimqpnReplDelQueueingColorConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getQueueSignLevAvgST() {
		return queueSignLevAvgST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueueSignLevAvgST(double newQueueSignLevAvgST) {
		double oldQueueSignLevAvgST = queueSignLevAvgST;
		queueSignLevAvgST = newQueueSignLevAvgST;
		boolean oldQueueSignLevAvgSTESet = queueSignLevAvgSTESet;
		queueSignLevAvgSTESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV_AVG_ST, oldQueueSignLevAvgST, queueSignLevAvgST, !oldQueueSignLevAvgSTESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetQueueSignLevAvgST() {
		double oldQueueSignLevAvgST = queueSignLevAvgST;
		boolean oldQueueSignLevAvgSTESet = queueSignLevAvgSTESet;
		queueSignLevAvgST = QUEUE_SIGN_LEV_AVG_ST_EDEFAULT;
		queueSignLevAvgSTESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV_AVG_ST, oldQueueSignLevAvgST, QUEUE_SIGN_LEV_AVG_ST_EDEFAULT, oldQueueSignLevAvgSTESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetQueueSignLevAvgST() {
		return queueSignLevAvgSTESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV_AVG_ST:
				return getQueueSignLevAvgST();
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
			case ModelPackage.SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV_AVG_ST:
				setQueueSignLevAvgST((Double)newValue);
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
			case ModelPackage.SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV_AVG_ST:
				unsetQueueSignLevAvgST();
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
			case ModelPackage.SIMQPN_REPL_DEL_QUEUEING_COLOR_CONFIGURATION__QUEUE_SIGN_LEV_AVG_ST:
				return isSetQueueSignLevAvgST();
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (queueSignLevAvgST: ");
		if (queueSignLevAvgSTESet) result.append(queueSignLevAvgST); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //SimqpnReplDelQueueingColorConfigurationImpl
