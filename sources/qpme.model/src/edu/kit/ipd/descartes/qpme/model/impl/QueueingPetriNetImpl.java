/**
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import edu.kit.ipd.descartes.qpme.model.ColorsContainer;
import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.NetMetaAttributesContainer;
import edu.kit.ipd.descartes.qpme.model.PlaceTransitionConnectionsContainer;
import edu.kit.ipd.descartes.qpme.model.PlacesContainer;
import edu.kit.ipd.descartes.qpme.model.ProbesContainer;
import edu.kit.ipd.descartes.qpme.model.QueueingPetriNet;
import edu.kit.ipd.descartes.qpme.model.QueuesContainer;
import edu.kit.ipd.descartes.qpme.model.TransitionsContainer;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Queueing Petri Net</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingPetriNetImpl#getColors <em>Colors</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingPetriNetImpl#getQueues <em>Queues</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingPetriNetImpl#getPlaces <em>Places</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingPetriNetImpl#getTransitions <em>Transitions</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingPetriNetImpl#getConnections <em>Connections</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingPetriNetImpl#getProbes <em>Probes</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingPetriNetImpl#getMetaAttributes <em>Meta Attributes</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingPetriNetImpl#getQpmeVersion <em>Qpme Version</em>}</li>
 * </ul>
 *
 * @generated
 */
public class QueueingPetriNetImpl extends EObjectImpl implements QueueingPetriNet {
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
	 * The cached value of the '{@link #getQueues() <em>Queues</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQueues()
	 * @generated
	 * @ordered
	 */
	protected QueuesContainer queues;

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
	 * The cached value of the '{@link #getProbes() <em>Probes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProbes()
	 * @generated
	 * @ordered
	 */
	protected ProbesContainer probes;

	/**
	 * The cached value of the '{@link #getMetaAttributes() <em>Meta Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetaAttributes()
	 * @generated
	 * @ordered
	 */
	protected NetMetaAttributesContainer metaAttributes;

	/**
	 * The default value of the '{@link #getQpmeVersion() <em>Qpme Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQpmeVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String QPME_VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQpmeVersion() <em>Qpme Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQpmeVersion()
	 * @generated
	 * @ordered
	 */
	protected String qpmeVersion = QPME_VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected QueueingPetriNetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.QUEUEING_PETRI_NET;
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_PETRI_NET__COLORS, oldColors, newColors);
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
				msgs = ((InternalEObject)colors).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.QUEUEING_PETRI_NET__COLORS, null, msgs);
			if (newColors != null)
				msgs = ((InternalEObject)newColors).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.QUEUEING_PETRI_NET__COLORS, null, msgs);
			msgs = basicSetColors(newColors, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_PETRI_NET__COLORS, newColors, newColors));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public QueuesContainer getQueues() {
		return queues;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetQueues(QueuesContainer newQueues, NotificationChain msgs) {
		QueuesContainer oldQueues = queues;
		queues = newQueues;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_PETRI_NET__QUEUES, oldQueues, newQueues);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQueues(QueuesContainer newQueues) {
		if (newQueues != queues) {
			NotificationChain msgs = null;
			if (queues != null)
				msgs = ((InternalEObject)queues).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.QUEUEING_PETRI_NET__QUEUES, null, msgs);
			if (newQueues != null)
				msgs = ((InternalEObject)newQueues).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.QUEUEING_PETRI_NET__QUEUES, null, msgs);
			msgs = basicSetQueues(newQueues, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_PETRI_NET__QUEUES, newQueues, newQueues));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_PETRI_NET__PLACES, oldPlaces, newPlaces);
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
				msgs = ((InternalEObject)places).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.QUEUEING_PETRI_NET__PLACES, null, msgs);
			if (newPlaces != null)
				msgs = ((InternalEObject)newPlaces).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.QUEUEING_PETRI_NET__PLACES, null, msgs);
			msgs = basicSetPlaces(newPlaces, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_PETRI_NET__PLACES, newPlaces, newPlaces));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_PETRI_NET__TRANSITIONS, oldTransitions, newTransitions);
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
				msgs = ((InternalEObject)transitions).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.QUEUEING_PETRI_NET__TRANSITIONS, null, msgs);
			if (newTransitions != null)
				msgs = ((InternalEObject)newTransitions).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.QUEUEING_PETRI_NET__TRANSITIONS, null, msgs);
			msgs = basicSetTransitions(newTransitions, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_PETRI_NET__TRANSITIONS, newTransitions, newTransitions));
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
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_PETRI_NET__CONNECTIONS, oldConnections, newConnections);
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
				msgs = ((InternalEObject)connections).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.QUEUEING_PETRI_NET__CONNECTIONS, null, msgs);
			if (newConnections != null)
				msgs = ((InternalEObject)newConnections).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.QUEUEING_PETRI_NET__CONNECTIONS, null, msgs);
			msgs = basicSetConnections(newConnections, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_PETRI_NET__CONNECTIONS, newConnections, newConnections));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProbesContainer getProbes() {
		return probes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProbes(ProbesContainer newProbes, NotificationChain msgs) {
		ProbesContainer oldProbes = probes;
		probes = newProbes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_PETRI_NET__PROBES, oldProbes, newProbes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProbes(ProbesContainer newProbes) {
		if (newProbes != probes) {
			NotificationChain msgs = null;
			if (probes != null)
				msgs = ((InternalEObject)probes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.QUEUEING_PETRI_NET__PROBES, null, msgs);
			if (newProbes != null)
				msgs = ((InternalEObject)newProbes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.QUEUEING_PETRI_NET__PROBES, null, msgs);
			msgs = basicSetProbes(newProbes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_PETRI_NET__PROBES, newProbes, newProbes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NetMetaAttributesContainer getMetaAttributes() {
		return metaAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMetaAttributes(NetMetaAttributesContainer newMetaAttributes, NotificationChain msgs) {
		NetMetaAttributesContainer oldMetaAttributes = metaAttributes;
		metaAttributes = newMetaAttributes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_PETRI_NET__META_ATTRIBUTES, oldMetaAttributes, newMetaAttributes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetaAttributes(NetMetaAttributesContainer newMetaAttributes) {
		if (newMetaAttributes != metaAttributes) {
			NotificationChain msgs = null;
			if (metaAttributes != null)
				msgs = ((InternalEObject)metaAttributes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.QUEUEING_PETRI_NET__META_ATTRIBUTES, null, msgs);
			if (newMetaAttributes != null)
				msgs = ((InternalEObject)newMetaAttributes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.QUEUEING_PETRI_NET__META_ATTRIBUTES, null, msgs);
			msgs = basicSetMetaAttributes(newMetaAttributes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_PETRI_NET__META_ATTRIBUTES, newMetaAttributes, newMetaAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getQpmeVersion() {
		return qpmeVersion;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQpmeVersion(String newQpmeVersion) {
		String oldQpmeVersion = qpmeVersion;
		qpmeVersion = newQpmeVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_PETRI_NET__QPME_VERSION, oldQpmeVersion, qpmeVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.QUEUEING_PETRI_NET__COLORS:
				return basicSetColors(null, msgs);
			case ModelPackage.QUEUEING_PETRI_NET__QUEUES:
				return basicSetQueues(null, msgs);
			case ModelPackage.QUEUEING_PETRI_NET__PLACES:
				return basicSetPlaces(null, msgs);
			case ModelPackage.QUEUEING_PETRI_NET__TRANSITIONS:
				return basicSetTransitions(null, msgs);
			case ModelPackage.QUEUEING_PETRI_NET__CONNECTIONS:
				return basicSetConnections(null, msgs);
			case ModelPackage.QUEUEING_PETRI_NET__PROBES:
				return basicSetProbes(null, msgs);
			case ModelPackage.QUEUEING_PETRI_NET__META_ATTRIBUTES:
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
			case ModelPackage.QUEUEING_PETRI_NET__COLORS:
				return getColors();
			case ModelPackage.QUEUEING_PETRI_NET__QUEUES:
				return getQueues();
			case ModelPackage.QUEUEING_PETRI_NET__PLACES:
				return getPlaces();
			case ModelPackage.QUEUEING_PETRI_NET__TRANSITIONS:
				return getTransitions();
			case ModelPackage.QUEUEING_PETRI_NET__CONNECTIONS:
				return getConnections();
			case ModelPackage.QUEUEING_PETRI_NET__PROBES:
				return getProbes();
			case ModelPackage.QUEUEING_PETRI_NET__META_ATTRIBUTES:
				return getMetaAttributes();
			case ModelPackage.QUEUEING_PETRI_NET__QPME_VERSION:
				return getQpmeVersion();
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
			case ModelPackage.QUEUEING_PETRI_NET__COLORS:
				setColors((ColorsContainer)newValue);
				return;
			case ModelPackage.QUEUEING_PETRI_NET__QUEUES:
				setQueues((QueuesContainer)newValue);
				return;
			case ModelPackage.QUEUEING_PETRI_NET__PLACES:
				setPlaces((PlacesContainer)newValue);
				return;
			case ModelPackage.QUEUEING_PETRI_NET__TRANSITIONS:
				setTransitions((TransitionsContainer)newValue);
				return;
			case ModelPackage.QUEUEING_PETRI_NET__CONNECTIONS:
				setConnections((PlaceTransitionConnectionsContainer)newValue);
				return;
			case ModelPackage.QUEUEING_PETRI_NET__PROBES:
				setProbes((ProbesContainer)newValue);
				return;
			case ModelPackage.QUEUEING_PETRI_NET__META_ATTRIBUTES:
				setMetaAttributes((NetMetaAttributesContainer)newValue);
				return;
			case ModelPackage.QUEUEING_PETRI_NET__QPME_VERSION:
				setQpmeVersion((String)newValue);
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
			case ModelPackage.QUEUEING_PETRI_NET__COLORS:
				setColors((ColorsContainer)null);
				return;
			case ModelPackage.QUEUEING_PETRI_NET__QUEUES:
				setQueues((QueuesContainer)null);
				return;
			case ModelPackage.QUEUEING_PETRI_NET__PLACES:
				setPlaces((PlacesContainer)null);
				return;
			case ModelPackage.QUEUEING_PETRI_NET__TRANSITIONS:
				setTransitions((TransitionsContainer)null);
				return;
			case ModelPackage.QUEUEING_PETRI_NET__CONNECTIONS:
				setConnections((PlaceTransitionConnectionsContainer)null);
				return;
			case ModelPackage.QUEUEING_PETRI_NET__PROBES:
				setProbes((ProbesContainer)null);
				return;
			case ModelPackage.QUEUEING_PETRI_NET__META_ATTRIBUTES:
				setMetaAttributes((NetMetaAttributesContainer)null);
				return;
			case ModelPackage.QUEUEING_PETRI_NET__QPME_VERSION:
				setQpmeVersion(QPME_VERSION_EDEFAULT);
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
			case ModelPackage.QUEUEING_PETRI_NET__COLORS:
				return colors != null;
			case ModelPackage.QUEUEING_PETRI_NET__QUEUES:
				return queues != null;
			case ModelPackage.QUEUEING_PETRI_NET__PLACES:
				return places != null;
			case ModelPackage.QUEUEING_PETRI_NET__TRANSITIONS:
				return transitions != null;
			case ModelPackage.QUEUEING_PETRI_NET__CONNECTIONS:
				return connections != null;
			case ModelPackage.QUEUEING_PETRI_NET__PROBES:
				return probes != null;
			case ModelPackage.QUEUEING_PETRI_NET__META_ATTRIBUTES:
				return metaAttributes != null;
			case ModelPackage.QUEUEING_PETRI_NET__QPME_VERSION:
				return QPME_VERSION_EDEFAULT == null ? qpmeVersion != null : !QPME_VERSION_EDEFAULT.equals(qpmeVersion);
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
		result.append(" (qpmeVersion: ");
		result.append(qpmeVersion);
		result.append(')');
		return result.toString();
	}

} //QueueingPetriNetImpl
