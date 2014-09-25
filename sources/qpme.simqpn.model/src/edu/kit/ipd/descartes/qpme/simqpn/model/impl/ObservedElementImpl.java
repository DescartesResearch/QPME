/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model.impl;

import edu.kit.ipd.descartes.qpme.simqpn.model.Color;
import edu.kit.ipd.descartes.qpme.simqpn.model.Metric;
import edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement;
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
 * An implementation of the model object '<em><b>Observed Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.ObservedElementImpl#getMetric <em>Metric</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.ObservedElementImpl#getColor <em>Color</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.ObservedElementImpl#getId <em>Id</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.ObservedElementImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.ObservedElementImpl#getStatsLevel <em>Stats Level</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.ObservedElementImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ObservedElementImpl extends EObjectImpl implements ObservedElement {
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
	 * The cached value of the '{@link #getColor() <em>Color</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected EList<Color> color;

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
	 * The default value of the '{@link #getStatsLevel() <em>Stats Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatsLevel()
	 * @generated
	 * @ordered
	 */
	protected static final int STATS_LEVEL_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getStatsLevel() <em>Stats Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatsLevel()
	 * @generated
	 * @ordered
	 */
	protected int statsLevel = STATS_LEVEL_EDEFAULT;

	/**
	 * This is true if the Stats Level attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean statsLevelESet;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final String TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected String type = TYPE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ObservedElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SimqpnPackage.Literals.OBSERVED_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Metric> getMetric() {
		if (metric == null) {
			metric = new EObjectContainmentEList<Metric>(Metric.class, this, SimqpnPackage.OBSERVED_ELEMENT__METRIC);
		}
		return metric;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Color> getColor() {
		if (color == null) {
			color = new EObjectContainmentEList<Color>(Color.class, this, SimqpnPackage.OBSERVED_ELEMENT__COLOR);
		}
		return color;
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
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.OBSERVED_ELEMENT__ID, oldId, id));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.OBSERVED_ELEMENT__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getStatsLevel() {
		return statsLevel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatsLevel(int newStatsLevel) {
		int oldStatsLevel = statsLevel;
		statsLevel = newStatsLevel;
		boolean oldStatsLevelESet = statsLevelESet;
		statsLevelESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.OBSERVED_ELEMENT__STATS_LEVEL, oldStatsLevel, statsLevel, !oldStatsLevelESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetStatsLevel() {
		int oldStatsLevel = statsLevel;
		boolean oldStatsLevelESet = statsLevelESet;
		statsLevel = STATS_LEVEL_EDEFAULT;
		statsLevelESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, SimqpnPackage.OBSERVED_ELEMENT__STATS_LEVEL, oldStatsLevel, STATS_LEVEL_EDEFAULT, oldStatsLevelESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetStatsLevel() {
		return statsLevelESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(String newType) {
		String oldType = type;
		type = newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.OBSERVED_ELEMENT__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SimqpnPackage.OBSERVED_ELEMENT__METRIC:
				return ((InternalEList<?>)getMetric()).basicRemove(otherEnd, msgs);
			case SimqpnPackage.OBSERVED_ELEMENT__COLOR:
				return ((InternalEList<?>)getColor()).basicRemove(otherEnd, msgs);
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
			case SimqpnPackage.OBSERVED_ELEMENT__METRIC:
				return getMetric();
			case SimqpnPackage.OBSERVED_ELEMENT__COLOR:
				return getColor();
			case SimqpnPackage.OBSERVED_ELEMENT__ID:
				return getId();
			case SimqpnPackage.OBSERVED_ELEMENT__NAME:
				return getName();
			case SimqpnPackage.OBSERVED_ELEMENT__STATS_LEVEL:
				return getStatsLevel();
			case SimqpnPackage.OBSERVED_ELEMENT__TYPE:
				return getType();
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
			case SimqpnPackage.OBSERVED_ELEMENT__METRIC:
				getMetric().clear();
				getMetric().addAll((Collection<? extends Metric>)newValue);
				return;
			case SimqpnPackage.OBSERVED_ELEMENT__COLOR:
				getColor().clear();
				getColor().addAll((Collection<? extends Color>)newValue);
				return;
			case SimqpnPackage.OBSERVED_ELEMENT__ID:
				setId((String)newValue);
				return;
			case SimqpnPackage.OBSERVED_ELEMENT__NAME:
				setName((String)newValue);
				return;
			case SimqpnPackage.OBSERVED_ELEMENT__STATS_LEVEL:
				setStatsLevel((Integer)newValue);
				return;
			case SimqpnPackage.OBSERVED_ELEMENT__TYPE:
				setType((String)newValue);
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
			case SimqpnPackage.OBSERVED_ELEMENT__METRIC:
				getMetric().clear();
				return;
			case SimqpnPackage.OBSERVED_ELEMENT__COLOR:
				getColor().clear();
				return;
			case SimqpnPackage.OBSERVED_ELEMENT__ID:
				setId(ID_EDEFAULT);
				return;
			case SimqpnPackage.OBSERVED_ELEMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SimqpnPackage.OBSERVED_ELEMENT__STATS_LEVEL:
				unsetStatsLevel();
				return;
			case SimqpnPackage.OBSERVED_ELEMENT__TYPE:
				setType(TYPE_EDEFAULT);
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
			case SimqpnPackage.OBSERVED_ELEMENT__METRIC:
				return metric != null && !metric.isEmpty();
			case SimqpnPackage.OBSERVED_ELEMENT__COLOR:
				return color != null && !color.isEmpty();
			case SimqpnPackage.OBSERVED_ELEMENT__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case SimqpnPackage.OBSERVED_ELEMENT__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SimqpnPackage.OBSERVED_ELEMENT__STATS_LEVEL:
				return isSetStatsLevel();
			case SimqpnPackage.OBSERVED_ELEMENT__TYPE:
				return TYPE_EDEFAULT == null ? type != null : !TYPE_EDEFAULT.equals(type);
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
		result.append(", statsLevel: ");
		if (statsLevelESet) result.append(statsLevel); else result.append("<unset>");
		result.append(", type: ");
		result.append(type);
		result.append(')');
		return result.toString();
	}

} //ObservedElementImpl
