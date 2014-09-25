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
import edu.kit.ipd.descartes.qpme.model.SimqpnReplDelColorConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simqpn Repl Del Color Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnReplDelColorConfigurationImpl#getSignLevAvgST <em>Sign Lev Avg ST</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimqpnReplDelColorConfigurationImpl extends SimqpnMetaAttributeImpl implements SimqpnReplDelColorConfiguration {
	/**
	 * The default value of the '{@link #getSignLevAvgST() <em>Sign Lev Avg ST</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignLevAvgST()
	 * @generated
	 * @ordered
	 */
	protected static final double SIGN_LEV_AVG_ST_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getSignLevAvgST() <em>Sign Lev Avg ST</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSignLevAvgST()
	 * @generated
	 * @ordered
	 */
	protected double signLevAvgST = SIGN_LEV_AVG_ST_EDEFAULT;

	/**
	 * This is true if the Sign Lev Avg ST attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean signLevAvgSTESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimqpnReplDelColorConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SIMQPN_REPL_DEL_COLOR_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getSignLevAvgST() {
		return signLevAvgST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSignLevAvgST(double newSignLevAvgST) {
		double oldSignLevAvgST = signLevAvgST;
		signLevAvgST = newSignLevAvgST;
		boolean oldSignLevAvgSTESet = signLevAvgSTESet;
		signLevAvgSTESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_REPL_DEL_COLOR_CONFIGURATION__SIGN_LEV_AVG_ST, oldSignLevAvgST, signLevAvgST, !oldSignLevAvgSTESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSignLevAvgST() {
		double oldSignLevAvgST = signLevAvgST;
		boolean oldSignLevAvgSTESet = signLevAvgSTESet;
		signLevAvgST = SIGN_LEV_AVG_ST_EDEFAULT;
		signLevAvgSTESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_REPL_DEL_COLOR_CONFIGURATION__SIGN_LEV_AVG_ST, oldSignLevAvgST, SIGN_LEV_AVG_ST_EDEFAULT, oldSignLevAvgSTESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSignLevAvgST() {
		return signLevAvgSTESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.SIMQPN_REPL_DEL_COLOR_CONFIGURATION__SIGN_LEV_AVG_ST:
				return getSignLevAvgST();
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
			case ModelPackage.SIMQPN_REPL_DEL_COLOR_CONFIGURATION__SIGN_LEV_AVG_ST:
				setSignLevAvgST((Double)newValue);
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
			case ModelPackage.SIMQPN_REPL_DEL_COLOR_CONFIGURATION__SIGN_LEV_AVG_ST:
				unsetSignLevAvgST();
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
			case ModelPackage.SIMQPN_REPL_DEL_COLOR_CONFIGURATION__SIGN_LEV_AVG_ST:
				return isSetSignLevAvgST();
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
		result.append(" (signLevAvgST: ");
		if (signLevAvgSTESet) result.append(signLevAvgST); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //SimqpnReplDelColorConfigurationImpl
