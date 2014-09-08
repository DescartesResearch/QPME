/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model.impl;

import edu.kit.ipd.descartes.qpme.simqpn.model.Percentile;
import edu.kit.ipd.descartes.qpme.simqpn.model.Percentiles;
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
 * An implementation of the model object '<em><b>Percentiles</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.PercentilesImpl#getPercentile <em>Percentile</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PercentilesImpl extends EObjectImpl implements Percentiles {
	/**
	 * The cached value of the '{@link #getPercentile() <em>Percentile</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPercentile()
	 * @generated
	 * @ordered
	 */
	protected EList<Percentile> percentile;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PercentilesImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SimqpnPackage.Literals.PERCENTILES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Percentile> getPercentile() {
		if (percentile == null) {
			percentile = new EObjectContainmentEList<Percentile>(Percentile.class, this, SimqpnPackage.PERCENTILES__PERCENTILE);
		}
		return percentile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SimqpnPackage.PERCENTILES__PERCENTILE:
				return ((InternalEList<?>)getPercentile()).basicRemove(otherEnd, msgs);
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
			case SimqpnPackage.PERCENTILES__PERCENTILE:
				return getPercentile();
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
			case SimqpnPackage.PERCENTILES__PERCENTILE:
				getPercentile().clear();
				getPercentile().addAll((Collection<? extends Percentile>)newValue);
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
			case SimqpnPackage.PERCENTILES__PERCENTILE:
				getPercentile().clear();
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
			case SimqpnPackage.PERCENTILES__PERCENTILE:
				return percentile != null && !percentile.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PercentilesImpl
