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

import edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnectionsContainer;
import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.ModesContainer;
import edu.kit.ipd.descartes.qpme.model.Transition;
import edu.kit.ipd.descartes.qpme.model.TransitionMetaAttributesContainer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Transition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.TransitionImpl#getModes <em>Modes</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.TransitionImpl#getConnections <em>Connections</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.TransitionImpl#getMetaAttributes <em>Meta Attributes</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.TransitionImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.TransitionImpl#getPriority <em>Priority</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class TransitionImpl extends PlaceTransitionElementImpl implements Transition {
	/**
	 * The cached value of the '{@link #getModes() <em>Modes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModes()
	 * @generated
	 * @ordered
	 */
	protected ModesContainer modes;

	/**
	 * The cached value of the '{@link #getConnections() <em>Connections</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnections()
	 * @generated
	 * @ordered
	 */
	protected IncidenceFunctionConnectionsContainer connections;

	/**
	 * The cached value of the '{@link #getMetaAttributes() <em>Meta Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetaAttributes()
	 * @generated
	 * @ordered
	 */
	protected TransitionMetaAttributesContainer metaAttributes;

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
	 * The default value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected static final long PRIORITY_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected long priority = PRIORITY_EDEFAULT;

	/**
	 * This is true if the Priority attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean priorityESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TransitionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.TRANSITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModesContainer getModes() {
		return modes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetModes(ModesContainer newModes, NotificationChain msgs) {
		ModesContainer oldModes = modes;
		modes = newModes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSITION__MODES, oldModes, newModes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModes(ModesContainer newModes) {
		if (newModes != modes) {
			NotificationChain msgs = null;
			if (modes != null)
				msgs = ((InternalEObject)modes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TRANSITION__MODES, null, msgs);
			if (newModes != null)
				msgs = ((InternalEObject)newModes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TRANSITION__MODES, null, msgs);
			msgs = basicSetModes(newModes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSITION__MODES, newModes, newModes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IncidenceFunctionConnectionsContainer getConnections() {
		return connections;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConnections(IncidenceFunctionConnectionsContainer newConnections, NotificationChain msgs) {
		IncidenceFunctionConnectionsContainer oldConnections = connections;
		connections = newConnections;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSITION__CONNECTIONS, oldConnections, newConnections);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnections(IncidenceFunctionConnectionsContainer newConnections) {
		if (newConnections != connections) {
			NotificationChain msgs = null;
			if (connections != null)
				msgs = ((InternalEObject)connections).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TRANSITION__CONNECTIONS, null, msgs);
			if (newConnections != null)
				msgs = ((InternalEObject)newConnections).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TRANSITION__CONNECTIONS, null, msgs);
			msgs = basicSetConnections(newConnections, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSITION__CONNECTIONS, newConnections, newConnections));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransitionMetaAttributesContainer getMetaAttributes() {
		return metaAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMetaAttributes(TransitionMetaAttributesContainer newMetaAttributes, NotificationChain msgs) {
		TransitionMetaAttributesContainer oldMetaAttributes = metaAttributes;
		metaAttributes = newMetaAttributes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSITION__META_ATTRIBUTES, oldMetaAttributes, newMetaAttributes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetaAttributes(TransitionMetaAttributesContainer newMetaAttributes) {
		if (newMetaAttributes != metaAttributes) {
			NotificationChain msgs = null;
			if (metaAttributes != null)
				msgs = ((InternalEObject)metaAttributes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TRANSITION__META_ATTRIBUTES, null, msgs);
			if (newMetaAttributes != null)
				msgs = ((InternalEObject)newMetaAttributes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.TRANSITION__META_ATTRIBUTES, null, msgs);
			msgs = basicSetMetaAttributes(newMetaAttributes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSITION__META_ATTRIBUTES, newMetaAttributes, newMetaAttributes));
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSITION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getPriority() {
		return priority;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPriority(long newPriority) {
		long oldPriority = priority;
		priority = newPriority;
		boolean oldPriorityESet = priorityESet;
		priorityESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.TRANSITION__PRIORITY, oldPriority, priority, !oldPriorityESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetPriority() {
		long oldPriority = priority;
		boolean oldPriorityESet = priorityESet;
		priority = PRIORITY_EDEFAULT;
		priorityESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.TRANSITION__PRIORITY, oldPriority, PRIORITY_EDEFAULT, oldPriorityESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetPriority() {
		return priorityESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.TRANSITION__MODES:
				return basicSetModes(null, msgs);
			case ModelPackage.TRANSITION__CONNECTIONS:
				return basicSetConnections(null, msgs);
			case ModelPackage.TRANSITION__META_ATTRIBUTES:
				return basicSetMetaAttributes(null, msgs);
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
			case ModelPackage.TRANSITION__MODES:
				return getModes();
			case ModelPackage.TRANSITION__CONNECTIONS:
				return getConnections();
			case ModelPackage.TRANSITION__META_ATTRIBUTES:
				return getMetaAttributes();
			case ModelPackage.TRANSITION__NAME:
				return getName();
			case ModelPackage.TRANSITION__PRIORITY:
				return getPriority();
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
			case ModelPackage.TRANSITION__MODES:
				setModes((ModesContainer)newValue);
				return;
			case ModelPackage.TRANSITION__CONNECTIONS:
				setConnections((IncidenceFunctionConnectionsContainer)newValue);
				return;
			case ModelPackage.TRANSITION__META_ATTRIBUTES:
				setMetaAttributes((TransitionMetaAttributesContainer)newValue);
				return;
			case ModelPackage.TRANSITION__NAME:
				setName((String)newValue);
				return;
			case ModelPackage.TRANSITION__PRIORITY:
				setPriority((Long)newValue);
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
			case ModelPackage.TRANSITION__MODES:
				setModes((ModesContainer)null);
				return;
			case ModelPackage.TRANSITION__CONNECTIONS:
				setConnections((IncidenceFunctionConnectionsContainer)null);
				return;
			case ModelPackage.TRANSITION__META_ATTRIBUTES:
				setMetaAttributes((TransitionMetaAttributesContainer)null);
				return;
			case ModelPackage.TRANSITION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ModelPackage.TRANSITION__PRIORITY:
				unsetPriority();
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
			case ModelPackage.TRANSITION__MODES:
				return modes != null;
			case ModelPackage.TRANSITION__CONNECTIONS:
				return connections != null;
			case ModelPackage.TRANSITION__META_ATTRIBUTES:
				return metaAttributes != null;
			case ModelPackage.TRANSITION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ModelPackage.TRANSITION__PRIORITY:
				return isSetPriority();
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
		result.append(" (name: ");
		result.append(name);
		result.append(", priority: ");
		if (priorityESet) result.append(priority); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //TransitionImpl
