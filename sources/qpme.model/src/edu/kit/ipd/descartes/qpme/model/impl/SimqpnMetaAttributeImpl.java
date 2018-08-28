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
import edu.kit.ipd.descartes.qpme.model.SimqpnMetaAttribute;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simqpn Meta Attribute</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnMetaAttributeImpl#getConfigurationName <em>Configuration Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class SimqpnMetaAttributeImpl extends IdentifiableElementImpl implements SimqpnMetaAttribute {
	/**
	 * The default value of the '{@link #getConfigurationName() <em>Configuration Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigurationName()
	 * @generated
	 * @ordered
	 */
	protected static final String CONFIGURATION_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConfigurationName() <em>Configuration Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigurationName()
	 * @generated
	 * @ordered
	 */
	protected String configurationName = CONFIGURATION_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimqpnMetaAttributeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SIMQPN_META_ATTRIBUTE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getConfigurationName() {
		return configurationName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConfigurationName(String newConfigurationName) {
		String oldConfigurationName = configurationName;
		configurationName = newConfigurationName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_META_ATTRIBUTE__CONFIGURATION_NAME, oldConfigurationName, configurationName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.SIMQPN_META_ATTRIBUTE__CONFIGURATION_NAME:
				return getConfigurationName();
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
			case ModelPackage.SIMQPN_META_ATTRIBUTE__CONFIGURATION_NAME:
				setConfigurationName((String)newValue);
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
			case ModelPackage.SIMQPN_META_ATTRIBUTE__CONFIGURATION_NAME:
				setConfigurationName(CONFIGURATION_NAME_EDEFAULT);
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
			case ModelPackage.SIMQPN_META_ATTRIBUTE__CONFIGURATION_NAME:
				return CONFIGURATION_NAME_EDEFAULT == null ? configurationName != null : !CONFIGURATION_NAME_EDEFAULT.equals(configurationName);
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
		result.append(" (configurationName: ");
		result.append(configurationName);
		result.append(')');
		return result.toString();
	}

} //SimqpnMetaAttributeImpl
