/**
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnection;
import edu.kit.ipd.descartes.qpme.model.PlaceTransitionElement;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Place Transition Connection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceTransitionConnectionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.PlaceTransitionConnectionImpl#getTarget <em>Target</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PlaceTransitionConnectionImpl extends IdentifiableElementImpl implements PlaceTransitionConnection {
	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected PlaceTransitionElement source;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected PlaceTransitionElement target;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PlaceTransitionConnectionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.PLACE_TRANSITION_CONNECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlaceTransitionElement getSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetSource(PlaceTransitionElement newSource, NotificationChain msgs) {
		PlaceTransitionElement oldSource = source;
		source = newSource;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.PLACE_TRANSITION_CONNECTION__SOURCE, oldSource, newSource);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(PlaceTransitionElement newSource) {
		if (newSource != source) {
			NotificationChain msgs = null;
			if (source != null)
				msgs = ((InternalEObject)source).eInverseRemove(this, ModelPackage.PLACE_TRANSITION_ELEMENT__OUTGOING_CONNECTIONS, PlaceTransitionElement.class, msgs);
			if (newSource != null)
				msgs = ((InternalEObject)newSource).eInverseAdd(this, ModelPackage.PLACE_TRANSITION_ELEMENT__OUTGOING_CONNECTIONS, PlaceTransitionElement.class, msgs);
			msgs = basicSetSource(newSource, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PLACE_TRANSITION_CONNECTION__SOURCE, newSource, newSource));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlaceTransitionElement getTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(PlaceTransitionElement newTarget, NotificationChain msgs) {
		PlaceTransitionElement oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.PLACE_TRANSITION_CONNECTION__TARGET, oldTarget, newTarget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTarget(PlaceTransitionElement newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, ModelPackage.PLACE_TRANSITION_ELEMENT__INCOMING_CONNECTIONS, PlaceTransitionElement.class, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, ModelPackage.PLACE_TRANSITION_ELEMENT__INCOMING_CONNECTIONS, PlaceTransitionElement.class, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.PLACE_TRANSITION_CONNECTION__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.PLACE_TRANSITION_CONNECTION__SOURCE:
				if (source != null)
					msgs = ((InternalEObject)source).eInverseRemove(this, ModelPackage.PLACE_TRANSITION_ELEMENT__OUTGOING_CONNECTIONS, PlaceTransitionElement.class, msgs);
				return basicSetSource((PlaceTransitionElement)otherEnd, msgs);
			case ModelPackage.PLACE_TRANSITION_CONNECTION__TARGET:
				if (target != null)
					msgs = ((InternalEObject)target).eInverseRemove(this, ModelPackage.PLACE_TRANSITION_ELEMENT__INCOMING_CONNECTIONS, PlaceTransitionElement.class, msgs);
				return basicSetTarget((PlaceTransitionElement)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.PLACE_TRANSITION_CONNECTION__SOURCE:
				return basicSetSource(null, msgs);
			case ModelPackage.PLACE_TRANSITION_CONNECTION__TARGET:
				return basicSetTarget(null, msgs);
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
			case ModelPackage.PLACE_TRANSITION_CONNECTION__SOURCE:
				return getSource();
			case ModelPackage.PLACE_TRANSITION_CONNECTION__TARGET:
				return getTarget();
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
			case ModelPackage.PLACE_TRANSITION_CONNECTION__SOURCE:
				setSource((PlaceTransitionElement)newValue);
				return;
			case ModelPackage.PLACE_TRANSITION_CONNECTION__TARGET:
				setTarget((PlaceTransitionElement)newValue);
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
			case ModelPackage.PLACE_TRANSITION_CONNECTION__SOURCE:
				setSource((PlaceTransitionElement)null);
				return;
			case ModelPackage.PLACE_TRANSITION_CONNECTION__TARGET:
				setTarget((PlaceTransitionElement)null);
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
			case ModelPackage.PLACE_TRANSITION_CONNECTION__SOURCE:
				return source != null;
			case ModelPackage.PLACE_TRANSITION_CONNECTION__TARGET:
				return target != null;
		}
		return super.eIsSet(featureID);
	}

} //PlaceTransitionConnectionImpl
