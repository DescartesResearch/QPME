/**
 */
package edu.kit.ipd.descartes.qpme.simqpn.model.impl;

import edu.kit.ipd.descartes.qpme.simqpn.model.ObservedElement;
import edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnPackage;
import edu.kit.ipd.descartes.qpme.simqpn.model.SimqpnResults;

import java.util.Collection;

import javax.xml.datatype.XMLGregorianCalendar;

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
 * An implementation of the model object '<em><b>Results</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnResultsImpl#getObservedElement <em>Observed Element</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnResultsImpl#getConfigurationName <em>Configuration Name</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnResultsImpl#getDate <em>Date</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnResultsImpl#getModelFile <em>Model File</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnResultsImpl#getName <em>Name</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.simqpn.model.impl.SimqpnResultsImpl#getQpmeVersion <em>Qpme Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimqpnResultsImpl extends EObjectImpl implements SimqpnResults {
	/**
	 * The cached value of the '{@link #getObservedElement() <em>Observed Element</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getObservedElement()
	 * @generated
	 * @ordered
	 */
	protected EList<ObservedElement> observedElement;

	/**
	 * The default value of the '{@link #getConfigurationName() <em>Configuration Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigurationName()
	 * @generated
	 * @ordered
	 */
	protected static final String CONFIGURATION_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConfigurationName() <em>Configuration Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigurationName()
	 * @generated
	 * @ordered
	 */
	protected String configurationName = CONFIGURATION_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected static final XMLGregorianCalendar DATE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDate() <em>Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDate()
	 * @generated
	 * @ordered
	 */
	protected XMLGregorianCalendar date = DATE_EDEFAULT;

	/**
	 * The default value of the '{@link #getModelFile() <em>Model File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelFile()
	 * @generated
	 * @ordered
	 */
	protected static final String MODEL_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getModelFile() <em>Model File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelFile()
	 * @generated
	 * @ordered
	 */
	protected String modelFile = MODEL_FILE_EDEFAULT;

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
	protected SimqpnResultsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SimqpnPackage.Literals.SIMQPN_RESULTS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<ObservedElement> getObservedElement() {
		if (observedElement == null) {
			observedElement = new EObjectContainmentEList<ObservedElement>(ObservedElement.class, this, SimqpnPackage.SIMQPN_RESULTS__OBSERVED_ELEMENT);
		}
		return observedElement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getConfigurationName() {
		return configurationName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConfigurationName(String newConfigurationName) {
		String oldConfigurationName = configurationName;
		configurationName = newConfigurationName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.SIMQPN_RESULTS__CONFIGURATION_NAME, oldConfigurationName, configurationName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public XMLGregorianCalendar getDate() {
		return date;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDate(XMLGregorianCalendar newDate) {
		XMLGregorianCalendar oldDate = date;
		date = newDate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.SIMQPN_RESULTS__DATE, oldDate, date));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getModelFile() {
		return modelFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setModelFile(String newModelFile) {
		String oldModelFile = modelFile;
		modelFile = newModelFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.SIMQPN_RESULTS__MODEL_FILE, oldModelFile, modelFile));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.SIMQPN_RESULTS__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, SimqpnPackage.SIMQPN_RESULTS__QPME_VERSION, oldQpmeVersion, qpmeVersion));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case SimqpnPackage.SIMQPN_RESULTS__OBSERVED_ELEMENT:
				return ((InternalEList<?>)getObservedElement()).basicRemove(otherEnd, msgs);
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
			case SimqpnPackage.SIMQPN_RESULTS__OBSERVED_ELEMENT:
				return getObservedElement();
			case SimqpnPackage.SIMQPN_RESULTS__CONFIGURATION_NAME:
				return getConfigurationName();
			case SimqpnPackage.SIMQPN_RESULTS__DATE:
				return getDate();
			case SimqpnPackage.SIMQPN_RESULTS__MODEL_FILE:
				return getModelFile();
			case SimqpnPackage.SIMQPN_RESULTS__NAME:
				return getName();
			case SimqpnPackage.SIMQPN_RESULTS__QPME_VERSION:
				return getQpmeVersion();
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
			case SimqpnPackage.SIMQPN_RESULTS__OBSERVED_ELEMENT:
				getObservedElement().clear();
				getObservedElement().addAll((Collection<? extends ObservedElement>)newValue);
				return;
			case SimqpnPackage.SIMQPN_RESULTS__CONFIGURATION_NAME:
				setConfigurationName((String)newValue);
				return;
			case SimqpnPackage.SIMQPN_RESULTS__DATE:
				setDate((XMLGregorianCalendar)newValue);
				return;
			case SimqpnPackage.SIMQPN_RESULTS__MODEL_FILE:
				setModelFile((String)newValue);
				return;
			case SimqpnPackage.SIMQPN_RESULTS__NAME:
				setName((String)newValue);
				return;
			case SimqpnPackage.SIMQPN_RESULTS__QPME_VERSION:
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
			case SimqpnPackage.SIMQPN_RESULTS__OBSERVED_ELEMENT:
				getObservedElement().clear();
				return;
			case SimqpnPackage.SIMQPN_RESULTS__CONFIGURATION_NAME:
				setConfigurationName(CONFIGURATION_NAME_EDEFAULT);
				return;
			case SimqpnPackage.SIMQPN_RESULTS__DATE:
				setDate(DATE_EDEFAULT);
				return;
			case SimqpnPackage.SIMQPN_RESULTS__MODEL_FILE:
				setModelFile(MODEL_FILE_EDEFAULT);
				return;
			case SimqpnPackage.SIMQPN_RESULTS__NAME:
				setName(NAME_EDEFAULT);
				return;
			case SimqpnPackage.SIMQPN_RESULTS__QPME_VERSION:
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
			case SimqpnPackage.SIMQPN_RESULTS__OBSERVED_ELEMENT:
				return observedElement != null && !observedElement.isEmpty();
			case SimqpnPackage.SIMQPN_RESULTS__CONFIGURATION_NAME:
				return CONFIGURATION_NAME_EDEFAULT == null ? configurationName != null : !CONFIGURATION_NAME_EDEFAULT.equals(configurationName);
			case SimqpnPackage.SIMQPN_RESULTS__DATE:
				return DATE_EDEFAULT == null ? date != null : !DATE_EDEFAULT.equals(date);
			case SimqpnPackage.SIMQPN_RESULTS__MODEL_FILE:
				return MODEL_FILE_EDEFAULT == null ? modelFile != null : !MODEL_FILE_EDEFAULT.equals(modelFile);
			case SimqpnPackage.SIMQPN_RESULTS__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case SimqpnPackage.SIMQPN_RESULTS__QPME_VERSION:
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (configurationName: ");
		result.append(configurationName);
		result.append(", date: ");
		result.append(date);
		result.append(", modelFile: ");
		result.append(modelFile);
		result.append(", name: ");
		result.append(name);
		result.append(", qpmeVersion: ");
		result.append(qpmeVersion);
		result.append(')');
		return result.toString();
	}

} //SimqpnResultsImpl
