/**
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection;
import edu.kit.ipd.descartes.qpme.model.IncidenceFunctionElement;
import edu.kit.ipd.descartes.qpme.model.ModelPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Incidence Function Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.IncidenceFunctionElementImpl#getIncomingConnections <em>Incoming Connections</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.IncidenceFunctionElementImpl#getOutgoingConnections <em>Outgoing Connections</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class IncidenceFunctionElementImpl extends IdentifiableElementImpl implements IncidenceFunctionElement {
	/**
	 * The cached value of the '{@link #getIncomingConnections() <em>Incoming Connections</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIncomingConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<IncidenceFunctionConnection> incomingConnections;

	/**
	 * The cached value of the '{@link #getOutgoingConnections() <em>Outgoing Connections</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutgoingConnections()
	 * @generated
	 * @ordered
	 */
	protected EList<IncidenceFunctionConnection> outgoingConnections;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IncidenceFunctionElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.INCIDENCE_FUNCTION_ELEMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IncidenceFunctionConnection> getIncomingConnections() {
		if (incomingConnections == null) {
			incomingConnections = new EObjectWithInverseEList<IncidenceFunctionConnection>(IncidenceFunctionConnection.class, this, ModelPackage.INCIDENCE_FUNCTION_ELEMENT__INCOMING_CONNECTIONS, ModelPackage.INCIDENCE_FUNCTION_CONNECTION__TARGET);
		}
		return incomingConnections;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IncidenceFunctionConnection> getOutgoingConnections() {
		if (outgoingConnections == null) {
			outgoingConnections = new EObjectWithInverseEList<IncidenceFunctionConnection>(IncidenceFunctionConnection.class, this, ModelPackage.INCIDENCE_FUNCTION_ELEMENT__OUTGOING_CONNECTIONS, ModelPackage.INCIDENCE_FUNCTION_CONNECTION__SOURCE);
		}
		return outgoingConnections;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.INCIDENCE_FUNCTION_ELEMENT__INCOMING_CONNECTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getIncomingConnections()).basicAdd(otherEnd, msgs);
			case ModelPackage.INCIDENCE_FUNCTION_ELEMENT__OUTGOING_CONNECTIONS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutgoingConnections()).basicAdd(otherEnd, msgs);
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
			case ModelPackage.INCIDENCE_FUNCTION_ELEMENT__INCOMING_CONNECTIONS:
				return ((InternalEList<?>)getIncomingConnections()).basicRemove(otherEnd, msgs);
			case ModelPackage.INCIDENCE_FUNCTION_ELEMENT__OUTGOING_CONNECTIONS:
				return ((InternalEList<?>)getOutgoingConnections()).basicRemove(otherEnd, msgs);
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
			case ModelPackage.INCIDENCE_FUNCTION_ELEMENT__INCOMING_CONNECTIONS:
				return getIncomingConnections();
			case ModelPackage.INCIDENCE_FUNCTION_ELEMENT__OUTGOING_CONNECTIONS:
				return getOutgoingConnections();
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
			case ModelPackage.INCIDENCE_FUNCTION_ELEMENT__INCOMING_CONNECTIONS:
				getIncomingConnections().clear();
				getIncomingConnections().addAll((Collection<? extends IncidenceFunctionConnection>)newValue);
				return;
			case ModelPackage.INCIDENCE_FUNCTION_ELEMENT__OUTGOING_CONNECTIONS:
				getOutgoingConnections().clear();
				getOutgoingConnections().addAll((Collection<? extends IncidenceFunctionConnection>)newValue);
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
			case ModelPackage.INCIDENCE_FUNCTION_ELEMENT__INCOMING_CONNECTIONS:
				getIncomingConnections().clear();
				return;
			case ModelPackage.INCIDENCE_FUNCTION_ELEMENT__OUTGOING_CONNECTIONS:
				getOutgoingConnections().clear();
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
			case ModelPackage.INCIDENCE_FUNCTION_ELEMENT__INCOMING_CONNECTIONS:
				return incomingConnections != null && !incomingConnections.isEmpty();
			case ModelPackage.INCIDENCE_FUNCTION_ELEMENT__OUTGOING_CONNECTIONS:
				return outgoingConnections != null && !outgoingConnections.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IncidenceFunctionElementImpl
