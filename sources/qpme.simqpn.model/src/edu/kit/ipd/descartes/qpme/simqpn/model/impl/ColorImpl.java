/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model.impl;

import edu.kit.ipd.descartes.qpme.simqpn.model.Color;
import edu.kit.ipd.descartes.qpme.simqpn.model.Histogram;
import edu.kit.ipd.descartes.qpme.simqpn.model.Metric;
import edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Color</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.ColorImpl#getMetric <em>Metric</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.ColorImpl#getHistogram <em>Histogram</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.ColorImpl#getId <em>Id</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.ColorImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.ColorImpl#getRealColor <em>Real Color</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ColorImpl extends EObjectImpl implements Color {
	/**
	 * The cached value of the '{@link #getMetric() <em>Metric</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetric()
	 * @generated
	 * @ordered
	 */
	protected EList<Metric> metric;

	/**
	 * The cached value of the '{@link #getHistogram() <em>Histogram</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHistogram()
	 * @generated
	 * @ordered
	 */
	protected EList<Histogram> histogram;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

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
	protected ColorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SimqpnPackage.Literals.COLOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Metric> getMetric() {
		if (metric == null) {
			metric = new EObjectContainmentEList<Metric>(Metric.class, this, SimqpnPackage.COLOR__METRIC);
		}
		return metric;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Histogram> getHistogram() {
		if (histogram == null) {
			histogram = new EObjectContainmentEList<Histogram>(Histogram.class, this, SimqpnPackage.COLOR__HISTOGRAM);
		}
		return histogram;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.COLOR__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.COLOR__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.COLOR__REAL_COLOR, oldRealColor, realColor));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SimqpnPackage.COLOR__METRIC:
				return ((InternalEList<?>)getMetric()).basicRemove(otherEnd, msgs);
			case SimqpnPackage.COLOR__HISTOGRAM:
				return ((InternalEList<?>)getHistogram()).basicRemove(otherEnd, msgs);
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
			case SimqpnPackage.COLOR__METRIC:
				return getMetric();
			case SimqpnPackage.COLOR__HISTOGRAM:
				return getHistogram();
			case SimqpnPackage.COLOR__ID:
				return getId();
			case SimqpnPackage.COLOR__NAME:
				return getName();
			case SimqpnPackage.COLOR__REAL_COLOR:
				return getRealColor();
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
			case SimqpnPackage.COLOR__METRIC:
				getMetric().clear();
				getMetric().addAll((Collection<? extends Metric>)newValue);
				return;
			case SimqpnPackage.COLOR__HISTOGRAM:
				getHistogram().clear();
				getHistogram().addAll((Collection<? extends Histogram>)newValue);
				return;
			case SimqpnPackage.COLOR__ID:
				setId((String)newValue);
				return;
			case SimqpnPackage.COLOR__NAME:
				setName((String)newValue);
				return;
			case SimqpnPackage.COLOR__REAL_COLOR:
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
			case SimqpnPackage.COLOR__METRIC:
				getMetric().clear();
				return;
			case SimqpnPackage.COLOR__HISTOGRAM:
				getHistogram().clear();
				return;
			case SimqpnPackage.COLOR__ID:
				setId(ID_EDEFAULT);
				return;
			case SimqpnPackage.COLOR__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SimqpnPackage.COLOR__REAL_COLOR:
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
			case SimqpnPackage.COLOR__METRIC:
				return metric != null && !metric.isEmpty();
			case SimqpnPackage.COLOR__HISTOGRAM:
				return histogram != null && !histogram.isEmpty();
			case SimqpnPackage.COLOR__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case SimqpnPackage.COLOR__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SimqpnPackage.COLOR__REAL_COLOR:
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (id: ");
		result.append(id);
		result.append(", name: ");
		result.append(name);
		result.append(", realColor: ");
		result.append(realColor);
		result.append(')');
		return result.toString();
	}

} //ColorImpl
