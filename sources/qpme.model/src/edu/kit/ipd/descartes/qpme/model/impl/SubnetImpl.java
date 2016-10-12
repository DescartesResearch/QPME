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
import org.eclipse.emf.ecore.impl.EObjectImpl;

import edu.kit.ipd.descartes.qpme.model.ColorsContainer;
import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnectionsContainer;
import edu.kit.ipd.descartes.qpme.model.PlacesContainer;
import edu.kit.ipd.descartes.qpme.model.Subnet;
import edu.kit.ipd.descartes.qpme.model.TransitionsContainer;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Subnet</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SubnetImpl#getColors <em>Colors</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SubnetImpl#getPlaces <em>Places</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SubnetImpl#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SubnetImpl#getConnections <em>Connections</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SubnetImpl extends EObjectImpl implements Subnet {
	/**
	 * The cached value of the '{@link #getColors() <em>Colors</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColors()
	 * @generated
	 * @ordered
	 */
	protected ColorsContainer colors;

	/**
	 * The cached value of the '{@link #getPlaces() <em>Places</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlaces()
	 * @generated
	 * @ordered
	 */
	protected PlacesContainer places;

	/**
	 * The cached value of the '{@link #getTransitions() <em>Transitions</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTransitions()
	 * @generated
	 * @ordered
	 */
	protected TransitionsContainer transitions;

	/**
	 * The cached value of the '{@link #getConnections() <em>Connections</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConnections()
	 * @generated
	 * @ordered
	 */
	protected PlaceTransitionConnectionsContainer connections;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SubnetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SUBNET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ColorsContainer getColors() {
		return colors;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetColors(ColorsContainer newColors, NotificationChain msgs) {
		ColorsContainer oldColors = colors;
		colors = newColors;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.SUBNET__COLORS, oldColors, newColors);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setColors(ColorsContainer newColors) {
		if (newColors != colors) {
			NotificationChain msgs = null;
			if (colors != null)
				msgs = ((InternalEObject)colors).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SUBNET__COLORS, null, msgs);
			if (newColors != null)
				msgs = ((InternalEObject)newColors).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SUBNET__COLORS, null, msgs);
			msgs = basicSetColors(newColors, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SUBNET__COLORS, newColors, newColors));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlacesContainer getPlaces() {
		return places;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPlaces(PlacesContainer newPlaces, NotificationChain msgs) {
		PlacesContainer oldPlaces = places;
		places = newPlaces;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.SUBNET__PLACES, oldPlaces, newPlaces);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPlaces(PlacesContainer newPlaces) {
		if (newPlaces != places) {
			NotificationChain msgs = null;
			if (places != null)
				msgs = ((InternalEObject)places).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SUBNET__PLACES, null, msgs);
			if (newPlaces != null)
				msgs = ((InternalEObject)newPlaces).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SUBNET__PLACES, null, msgs);
			msgs = basicSetPlaces(newPlaces, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SUBNET__PLACES, newPlaces, newPlaces));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TransitionsContainer getTransitions() {
		return transitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTransitions(TransitionsContainer newTransitions, NotificationChain msgs) {
		TransitionsContainer oldTransitions = transitions;
		transitions = newTransitions;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.SUBNET__TRANSITIONS, oldTransitions, newTransitions);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTransitions(TransitionsContainer newTransitions) {
		if (newTransitions != transitions) {
			NotificationChain msgs = null;
			if (transitions != null)
				msgs = ((InternalEObject)transitions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SUBNET__TRANSITIONS, null, msgs);
			if (newTransitions != null)
				msgs = ((InternalEObject)newTransitions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SUBNET__TRANSITIONS, null, msgs);
			msgs = basicSetTransitions(newTransitions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SUBNET__TRANSITIONS, newTransitions, newTransitions));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlaceTransitionConnectionsContainer getConnections() {
		return connections;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetConnections(PlaceTransitionConnectionsContainer newConnections, NotificationChain msgs) {
		PlaceTransitionConnectionsContainer oldConnections = connections;
		connections = newConnections;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.SUBNET__CONNECTIONS, oldConnections, newConnections);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConnections(PlaceTransitionConnectionsContainer newConnections) {
		if (newConnections != connections) {
			NotificationChain msgs = null;
			if (connections != null)
				msgs = ((InternalEObject)connections).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SUBNET__CONNECTIONS, null, msgs);
			if (newConnections != null)
				msgs = ((InternalEObject)newConnections).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.SUBNET__CONNECTIONS, null, msgs);
			msgs = basicSetConnections(newConnections, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SUBNET__CONNECTIONS, newConnections, newConnections));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.SUBNET__COLORS:
				return basicSetColors(null, msgs);
			case ModelPackage.SUBNET__PLACES:
				return basicSetPlaces(null, msgs);
			case ModelPackage.SUBNET__TRANSITIONS:
				return basicSetTransitions(null, msgs);
			case ModelPackage.SUBNET__CONNECTIONS:
				return basicSetConnections(null, msgs);
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
			case ModelPackage.SUBNET__COLORS:
				return getColors();
			case ModelPackage.SUBNET__PLACES:
				return getPlaces();
			case ModelPackage.SUBNET__TRANSITIONS:
				return getTransitions();
			case ModelPackage.SUBNET__CONNECTIONS:
				return getConnections();
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
			case ModelPackage.SUBNET__COLORS:
				setColors((ColorsContainer)newValue);
				return;
			case ModelPackage.SUBNET__PLACES:
				setPlaces((PlacesContainer)newValue);
				return;
			case ModelPackage.SUBNET__TRANSITIONS:
				setTransitions((TransitionsContainer)newValue);
				return;
			case ModelPackage.SUBNET__CONNECTIONS:
				setConnections((PlaceTransitionConnectionsContainer)newValue);
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
			case ModelPackage.SUBNET__COLORS:
				setColors((ColorsContainer)null);
				return;
			case ModelPackage.SUBNET__PLACES:
				setPlaces((PlacesContainer)null);
				return;
			case ModelPackage.SUBNET__TRANSITIONS:
				setTransitions((TransitionsContainer)null);
				return;
			case ModelPackage.SUBNET__CONNECTIONS:
				setConnections((PlaceTransitionConnectionsContainer)null);
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
			case ModelPackage.SUBNET__COLORS:
				return colors != null;
			case ModelPackage.SUBNET__PLACES:
				return places != null;
			case ModelPackage.SUBNET__TRANSITIONS:
				return transitions != null;
			case ModelPackage.SUBNET__CONNECTIONS:
				return connections != null;
		}
		return super.eIsSet(featureID);
	}

} //SubnetImpl
