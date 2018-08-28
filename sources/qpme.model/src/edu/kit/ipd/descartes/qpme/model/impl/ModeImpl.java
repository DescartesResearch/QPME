/**
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import edu.kit.ipd.descartes.qpme.model.Mode;
import edu.kit.ipd.descartes.qpme.model.ModelPackage;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mode</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.ModeImpl#getFiringWeight <em>Firing Weight</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.ModeImpl#getMeanFiringDelay <em>Mean Firing Delay</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.ModeImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.ModeImpl#getRealColor <em>Real Color</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModeImpl extends IncidenceFunctionElementImpl implements Mode {
	/**
	 * The default value of the '{@link #getFiringWeight() <em>Firing Weight</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFiringWeight()
	 * @generated
	 * @ordered
	 */
	protected static final double FIRING_WEIGHT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getFiringWeight() <em>Firing Weight</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFiringWeight()
	 * @generated
	 * @ordered
	 */
	protected double firingWeight = FIRING_WEIGHT_EDEFAULT;

	/**
	 * This is true if the Firing Weight attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean firingWeightESet;

	/**
	 * The default value of the '{@link #getMeanFiringDelay() <em>Mean Firing Delay</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMeanFiringDelay()
	 * @generated
	 * @ordered
	 */
	protected static final double MEAN_FIRING_DELAY_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getMeanFiringDelay() <em>Mean Firing Delay</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMeanFiringDelay()
	 * @generated
	 * @ordered
	 */
	protected double meanFiringDelay = MEAN_FIRING_DELAY_EDEFAULT;

	/**
	 * This is true if the Mean Firing Delay attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean meanFiringDelayESet;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getRealColor() <em>Real Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRealColor()
	 * @generated
	 * @ordered
	 */
	protected static final String REAL_COLOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRealColor() <em>Real Color</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRealColor()
	 * @generated
	 * @ordered
	 */
	protected String realColor = REAL_COLOR_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ModeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.MODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getFiringWeight() {
		return firingWeight;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFiringWeight(double newFiringWeight) {
		double oldFiringWeight = firingWeight;
		firingWeight = newFiringWeight;
		boolean oldFiringWeightESet = firingWeightESet;
		firingWeightESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.MODE__FIRING_WEIGHT, oldFiringWeight, firingWeight, !oldFiringWeightESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetFiringWeight() {
		double oldFiringWeight = firingWeight;
		boolean oldFiringWeightESet = firingWeightESet;
		firingWeight = FIRING_WEIGHT_EDEFAULT;
		firingWeightESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.MODE__FIRING_WEIGHT, oldFiringWeight, FIRING_WEIGHT_EDEFAULT, oldFiringWeightESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetFiringWeight() {
		return firingWeightESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getMeanFiringDelay() {
		return meanFiringDelay;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMeanFiringDelay(double newMeanFiringDelay) {
		double oldMeanFiringDelay = meanFiringDelay;
		meanFiringDelay = newMeanFiringDelay;
		boolean oldMeanFiringDelayESet = meanFiringDelayESet;
		meanFiringDelayESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.MODE__MEAN_FIRING_DELAY, oldMeanFiringDelay, meanFiringDelay, !oldMeanFiringDelayESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMeanFiringDelay() {
		double oldMeanFiringDelay = meanFiringDelay;
		boolean oldMeanFiringDelayESet = meanFiringDelayESet;
		meanFiringDelay = MEAN_FIRING_DELAY_EDEFAULT;
		meanFiringDelayESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.MODE__MEAN_FIRING_DELAY, oldMeanFiringDelay, MEAN_FIRING_DELAY_EDEFAULT, oldMeanFiringDelayESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMeanFiringDelay() {
		return meanFiringDelayESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.MODE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRealColor() {
		return realColor;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRealColor(String newRealColor) {
		String oldRealColor = realColor;
		realColor = newRealColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.MODE__REAL_COLOR, oldRealColor, realColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.MODE__FIRING_WEIGHT:
				return getFiringWeight();
			case ModelPackage.MODE__MEAN_FIRING_DELAY:
				return getMeanFiringDelay();
			case ModelPackage.MODE__NAME:
				return getName();
			case ModelPackage.MODE__REAL_COLOR:
				return getRealColor();
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
			case ModelPackage.MODE__FIRING_WEIGHT:
				setFiringWeight((Double)newValue);
				return;
			case ModelPackage.MODE__MEAN_FIRING_DELAY:
				setMeanFiringDelay((Double)newValue);
				return;
			case ModelPackage.MODE__NAME:
				setName((String)newValue);
				return;
			case ModelPackage.MODE__REAL_COLOR:
				setRealColor((String)newValue);
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
			case ModelPackage.MODE__FIRING_WEIGHT:
				unsetFiringWeight();
				return;
			case ModelPackage.MODE__MEAN_FIRING_DELAY:
				unsetMeanFiringDelay();
				return;
			case ModelPackage.MODE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ModelPackage.MODE__REAL_COLOR:
				setRealColor(REAL_COLOR_EDEFAULT);
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
			case ModelPackage.MODE__FIRING_WEIGHT:
				return isSetFiringWeight();
			case ModelPackage.MODE__MEAN_FIRING_DELAY:
				return isSetMeanFiringDelay();
			case ModelPackage.MODE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ModelPackage.MODE__REAL_COLOR:
				return REAL_COLOR_EDEFAULT == null ? realColor != null : !REAL_COLOR_EDEFAULT.equals(realColor);
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
		result.append(" (firingWeight: ");
		if (firingWeightESet) result.append(firingWeight); else result.append("<unset>");
		result.append(", meanFiringDelay: ");
		if (meanFiringDelayESet) result.append(meanFiringDelay); else result.append("<unset>");
		result.append(", name: ");
		result.append(name);
		result.append(", realColor: ");
		result.append(realColor);
		result.append(')');
		return result.toString();
	}

} //ModeImpl
