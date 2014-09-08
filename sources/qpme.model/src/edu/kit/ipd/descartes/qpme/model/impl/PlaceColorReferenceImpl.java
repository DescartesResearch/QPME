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
import edu.kit.ipd.descartes.qpme.model.PlaceColorReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Place Color Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceColorReferenceImpl#getInitialPopulation <em>Initial Population</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceColorReferenceImpl#getMaximumCapacity <em>Maximum Capacity</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class PlaceColorReferenceImpl extends ColorReferenceImpl implements PlaceColorReference {
	/**
	 * The default value of the '{@link #getInitialPopulation() <em>Initial Population</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialPopulation()
	 * @generated
	 * @ordered
	 */
	protected static final long INITIAL_POPULATION_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getInitialPopulation() <em>Initial Population</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInitialPopulation()
	 * @generated
	 * @ordered
	 */
	protected long initialPopulation = INITIAL_POPULATION_EDEFAULT;

	/**
	 * This is true if the Initial Population attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean initialPopulationESet;

	/**
	 * The default value of the '{@link #getMaximumCapacity() <em>Maximum Capacity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaximumCapacity()
	 * @generated
	 * @ordered
	 */
	protected static final long MAXIMUM_CAPACITY_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getMaximumCapacity() <em>Maximum Capacity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMaximumCapacity()
	 * @generated
	 * @ordered
	 */
	protected long maximumCapacity = MAXIMUM_CAPACITY_EDEFAULT;

	/**
	 * This is true if the Maximum Capacity attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean maximumCapacityESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PlaceColorReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.PLACE_COLOR_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getInitialPopulation() {
		return initialPopulation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInitialPopulation(long newInitialPopulation) {
		long oldInitialPopulation = initialPopulation;
		initialPopulation = newInitialPopulation;
		boolean oldInitialPopulationESet = initialPopulationESet;
		initialPopulationESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PLACE_COLOR_REFERENCE__INITIAL_POPULATION, oldInitialPopulation, initialPopulation, !oldInitialPopulationESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetInitialPopulation() {
		long oldInitialPopulation = initialPopulation;
		boolean oldInitialPopulationESet = initialPopulationESet;
		initialPopulation = INITIAL_POPULATION_EDEFAULT;
		initialPopulationESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.PLACE_COLOR_REFERENCE__INITIAL_POPULATION, oldInitialPopulation, INITIAL_POPULATION_EDEFAULT, oldInitialPopulationESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetInitialPopulation() {
		return initialPopulationESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getMaximumCapacity() {
		return maximumCapacity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaximumCapacity(long newMaximumCapacity) {
		long oldMaximumCapacity = maximumCapacity;
		maximumCapacity = newMaximumCapacity;
		boolean oldMaximumCapacityESet = maximumCapacityESet;
		maximumCapacityESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PLACE_COLOR_REFERENCE__MAXIMUM_CAPACITY, oldMaximumCapacity, maximumCapacity, !oldMaximumCapacityESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMaximumCapacity() {
		long oldMaximumCapacity = maximumCapacity;
		boolean oldMaximumCapacityESet = maximumCapacityESet;
		maximumCapacity = MAXIMUM_CAPACITY_EDEFAULT;
		maximumCapacityESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.PLACE_COLOR_REFERENCE__MAXIMUM_CAPACITY, oldMaximumCapacity, MAXIMUM_CAPACITY_EDEFAULT, oldMaximumCapacityESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMaximumCapacity() {
		return maximumCapacityESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.PLACE_COLOR_REFERENCE__INITIAL_POPULATION:
				return getInitialPopulation();
			case ModelPackage.PLACE_COLOR_REFERENCE__MAXIMUM_CAPACITY:
				return getMaximumCapacity();
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
			case ModelPackage.PLACE_COLOR_REFERENCE__INITIAL_POPULATION:
				setInitialPopulation((Long)newValue);
				return;
			case ModelPackage.PLACE_COLOR_REFERENCE__MAXIMUM_CAPACITY:
				setMaximumCapacity((Long)newValue);
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
			case ModelPackage.PLACE_COLOR_REFERENCE__INITIAL_POPULATION:
				unsetInitialPopulation();
				return;
			case ModelPackage.PLACE_COLOR_REFERENCE__MAXIMUM_CAPACITY:
				unsetMaximumCapacity();
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
			case ModelPackage.PLACE_COLOR_REFERENCE__INITIAL_POPULATION:
				return isSetInitialPopulation();
			case ModelPackage.PLACE_COLOR_REFERENCE__MAXIMUM_CAPACITY:
				return isSetMaximumCapacity();
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
		result.append(" (initialPopulation: ");
		if (initialPopulationESet) result.append(initialPopulation); else result.append("<unset>");
		result.append(", maximumCapacity: ");
		if (maximumCapacityESet) result.append(maximumCapacity); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //PlaceColorReferenceImpl
