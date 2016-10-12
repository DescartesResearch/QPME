/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import java.math.BigInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.SimqpnPlaceConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simqpn Place Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnPlaceConfigurationImpl#getStatsLevel <em>Stats Level</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SimqpnPlaceConfigurationImpl extends SimqpnMetaAttributeImpl implements SimqpnPlaceConfiguration {
	/**
	 * The default value of the '{@link #getStatsLevel() <em>Stats Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatsLevel()
	 * @generated
	 * @ordered
	 */
	protected static final BigInteger STATS_LEVEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStatsLevel() <em>Stats Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatsLevel()
	 * @generated
	 * @ordered
	 */
	protected BigInteger statsLevel = STATS_LEVEL_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimqpnPlaceConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SIMQPN_PLACE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigInteger getStatsLevel() {
		return statsLevel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatsLevel(BigInteger newStatsLevel) {
		BigInteger oldStatsLevel = statsLevel;
		statsLevel = newStatsLevel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_PLACE_CONFIGURATION__STATS_LEVEL, oldStatsLevel, statsLevel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.SIMQPN_PLACE_CONFIGURATION__STATS_LEVEL:
				return getStatsLevel();
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
			case ModelPackage.SIMQPN_PLACE_CONFIGURATION__STATS_LEVEL:
				setStatsLevel((BigInteger)newValue);
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
			case ModelPackage.SIMQPN_PLACE_CONFIGURATION__STATS_LEVEL:
				setStatsLevel(STATS_LEVEL_EDEFAULT);
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
			case ModelPackage.SIMQPN_PLACE_CONFIGURATION__STATS_LEVEL:
				return STATS_LEVEL_EDEFAULT == null ? statsLevel != null : !STATS_LEVEL_EDEFAULT.equals(statsLevel);
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
		result.append(" (statsLevel: ");
		result.append(statsLevel);
		result.append(')');
		return result.toString();
	}

} //SimqpnPlaceConfigurationImpl
