/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model.impl;

import edu.kit.ipd.descartes.qpme.simqpn.model.Bucket;
import edu.kit.ipd.descartes.qpme.simqpn.model.Buckets;
import edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Buckets</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.BucketsImpl#getBucket <em>Bucket</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BucketsImpl extends EObjectImpl implements Buckets {
	/**
	 * The cached value of the '{@link #getBucket() <em>Bucket</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBucket()
	 * @generated
	 * @ordered
	 */
	protected EList<Bucket> bucket;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BucketsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SimqpnPackage.Literals.BUCKETS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Bucket> getBucket() {
		if (bucket == null) {
			bucket = new EObjectContainmentEList<Bucket>(Bucket.class, this, SimqpnPackage.BUCKETS__BUCKET);
		}
		return bucket;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SimqpnPackage.BUCKETS__BUCKET:
				return ((InternalEList<?>)getBucket()).basicRemove(otherEnd, msgs);
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
			case SimqpnPackage.BUCKETS__BUCKET:
				return getBucket();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case SimqpnPackage.BUCKETS__BUCKET:
				getBucket().clear();
				getBucket().addAll((Collection<? extends Bucket>)newValue);
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
			case SimqpnPackage.BUCKETS__BUCKET:
				getBucket().clear();
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
			case SimqpnPackage.BUCKETS__BUCKET:
				return bucket != null && !bucket.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //BucketsImpl
