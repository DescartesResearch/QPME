/**
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.Queue;
import edu.kit.ipd.descartes.qpme.model.QueueingStrategy;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Queue</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueImpl#getNumberOfServers <em>Number Of Servers</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueImpl#getStrategy <em>Strategy</em>}</li>
 * </ul>
 *
 * @generated
 */
public class QueueImpl extends IdentifiableElementImpl implements Queue {
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
	 * The default value of the '{@link #getNumberOfServers() <em>Number Of Servers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfServers()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_SERVERS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getNumberOfServers() <em>Number Of Servers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfServers()
	 * @generated
	 * @ordered
	 */
	protected int numberOfServers = NUMBER_OF_SERVERS_EDEFAULT;

	/**
	 * This is true if the Number Of Servers attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean numberOfServersESet;

	/**
	 * The default value of the '{@link #getStrategy() <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrategy()
	 * @generated
	 * @ordered
	 */
	protected static final QueueingStrategy STRATEGY_EDEFAULT = QueueingStrategy.PRIO;

	/**
	 * The cached value of the '{@link #getStrategy() <em>Strategy</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStrategy()
	 * @generated
	 * @ordered
	 */
	protected QueueingStrategy strategy = STRATEGY_EDEFAULT;

	/**
	 * This is true if the Strategy attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean strategyESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected QueueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.QUEUE;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfServers() {
		return numberOfServers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfServers(int newNumberOfServers) {
		int oldNumberOfServers = numberOfServers;
		numberOfServers = newNumberOfServers;
		boolean oldNumberOfServersESet = numberOfServersESet;
		numberOfServersESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUE__NUMBER_OF_SERVERS, oldNumberOfServers, numberOfServers, !oldNumberOfServersESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetNumberOfServers() {
		int oldNumberOfServers = numberOfServers;
		boolean oldNumberOfServersESet = numberOfServersESet;
		numberOfServers = NUMBER_OF_SERVERS_EDEFAULT;
		numberOfServersESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUE__NUMBER_OF_SERVERS, oldNumberOfServers, NUMBER_OF_SERVERS_EDEFAULT, oldNumberOfServersESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetNumberOfServers() {
		return numberOfServersESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QueueingStrategy getStrategy() {
		return strategy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStrategy(QueueingStrategy newStrategy) {
		QueueingStrategy oldStrategy = strategy;
		strategy = newStrategy == null ? STRATEGY_EDEFAULT : newStrategy;
		boolean oldStrategyESet = strategyESet;
		strategyESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUE__STRATEGY, oldStrategy, strategy, !oldStrategyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetStrategy() {
		QueueingStrategy oldStrategy = strategy;
		boolean oldStrategyESet = strategyESet;
		strategy = STRATEGY_EDEFAULT;
		strategyESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUE__STRATEGY, oldStrategy, STRATEGY_EDEFAULT, oldStrategyESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetStrategy() {
		return strategyESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.QUEUE__NAME:
				return getName();
			case ModelPackage.QUEUE__NUMBER_OF_SERVERS:
				return getNumberOfServers();
			case ModelPackage.QUEUE__STRATEGY:
				return getStrategy();
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
			case ModelPackage.QUEUE__NAME:
				setName((String)newValue);
				return;
			case ModelPackage.QUEUE__NUMBER_OF_SERVERS:
				setNumberOfServers((Integer)newValue);
				return;
			case ModelPackage.QUEUE__STRATEGY:
				setStrategy((QueueingStrategy)newValue);
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
			case ModelPackage.QUEUE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ModelPackage.QUEUE__NUMBER_OF_SERVERS:
				unsetNumberOfServers();
				return;
			case ModelPackage.QUEUE__STRATEGY:
				unsetStrategy();
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
			case ModelPackage.QUEUE__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ModelPackage.QUEUE__NUMBER_OF_SERVERS:
				return isSetNumberOfServers();
			case ModelPackage.QUEUE__STRATEGY:
				return isSetStrategy();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", numberOfServers: ");
		if (numberOfServersESet) result.append(numberOfServers); else result.append("<unset>");
		result.append(", strategy: ");
		if (strategyESet) result.append(strategy); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //QueueImpl
