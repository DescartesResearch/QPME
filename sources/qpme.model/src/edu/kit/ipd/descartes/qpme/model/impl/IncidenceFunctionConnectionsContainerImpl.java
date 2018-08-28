/**
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnection;
import edu.kit.ipd.descartes.qpme.model.IncidenceFunctionConnectionsContainer;
import edu.kit.ipd.descartes.qpme.model.ModelPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Incidence Function Connections Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.IncidenceFunctionConnectionsContainerImpl#getDefinitions <em>Definitions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class IncidenceFunctionConnectionsContainerImpl extends EObjectImpl implements IncidenceFunctionConnectionsContainer {
	/**
	 * The cached value of the '{@link #getDefinitions() <em>Definitions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDefinitions()
	 * @generated
	 * @ordered
	 */
	protected EList<IncidenceFunctionConnection> definitions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected IncidenceFunctionConnectionsContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.INCIDENCE_FUNCTION_CONNECTIONS_CONTAINER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<IncidenceFunctionConnection> getDefinitions() {
		if (definitions == null) {
			definitions = new EObjectContainmentEList<IncidenceFunctionConnection>(IncidenceFunctionConnection.class, this, ModelPackage.INCIDENCE_FUNCTION_CONNECTIONS_CONTAINER__DEFINITIONS);
		}
		return definitions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.INCIDENCE_FUNCTION_CONNECTIONS_CONTAINER__DEFINITIONS:
				return ((InternalEList<?>)getDefinitions()).basicRemove(otherEnd, msgs);
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
			case ModelPackage.INCIDENCE_FUNCTION_CONNECTIONS_CONTAINER__DEFINITIONS:
				return getDefinitions();
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
			case ModelPackage.INCIDENCE_FUNCTION_CONNECTIONS_CONTAINER__DEFINITIONS:
				getDefinitions().clear();
				getDefinitions().addAll((Collection<? extends IncidenceFunctionConnection>)newValue);
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
			case ModelPackage.INCIDENCE_FUNCTION_CONNECTIONS_CONTAINER__DEFINITIONS:
				getDefinitions().clear();
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
			case ModelPackage.INCIDENCE_FUNCTION_CONNECTIONS_CONTAINER__DEFINITIONS:
				return definitions != null && !definitions.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //IncidenceFunctionConnectionsContainerImpl
