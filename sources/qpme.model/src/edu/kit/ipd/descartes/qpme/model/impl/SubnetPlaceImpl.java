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

import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.Subnet;
import edu.kit.ipd.descartes.qpme.model.SubnetPlace;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subnet Place</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SubnetPlaceImpl#getSubnet <em>Subnet</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SubnetPlaceImpl extends PlaceImpl implements SubnetPlace {
	/**
	 * The cached value of the '{@link #getSubnet() <em>Subnet</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSubnet()
	 * @generated
	 * @ordered
	 */
	protected Subnet subnet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubnetPlaceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SUBNET_PLACE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Subnet getSubnet() {
		return subnet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSubnet(Subnet newSubnet, NotificationChain msgs) {
		Subnet oldSubnet = subnet;
		subnet = newSubnet;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.SUBNET_PLACE__SUBNET, oldSubnet, newSubnet);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSubnet(Subnet newSubnet) {
		if (newSubnet != subnet) {
			NotificationChain msgs = null;
			if (subnet != null)
				msgs = ((InternalEObject)subnet).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SUBNET_PLACE__SUBNET, null, msgs);
			if (newSubnet != null)
				msgs = ((InternalEObject)newSubnet).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SUBNET_PLACE__SUBNET, null, msgs);
			msgs = basicSetSubnet(newSubnet, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SUBNET_PLACE__SUBNET, newSubnet, newSubnet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.SUBNET_PLACE__SUBNET:
				return basicSetSubnet(null, msgs);
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
			case ModelPackage.SUBNET_PLACE__SUBNET:
				return getSubnet();
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
			case ModelPackage.SUBNET_PLACE__SUBNET:
				setSubnet((Subnet)newValue);
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
			case ModelPackage.SUBNET_PLACE__SUBNET:
				setSubnet((Subnet)null);
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
			case ModelPackage.SUBNET_PLACE__SUBNET:
				return subnet != null;
		}
		return super.eIsSet(featureID);
	}

} //SubnetPlaceImpl
