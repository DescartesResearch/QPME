/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simqpn Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getConfigurationDescription <em>Configuration Description</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getNumberOfRuns <em>Number Of Runs</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getOutputDirectory <em>Output Directory</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getRampUpLength <em>Ramp Up Length</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getScenario <em>Scenario</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getSecondsBetweenHeartBeats <em>Seconds Between Heart Beats</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getSecondsBetweenStopChecks <em>Seconds Between Stop Checks</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getStoppingRule <em>Stopping Rule</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTimeBeforeInitialHeartBeat <em>Time Before Initial Heart Beat</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTimeBetweenStopChecks <em>Time Between Stop Checks</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTotalRunLength <em>Total Run Length</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getVerbosityLevel <em>Verbosity Level</em>}</li>
 * </ul>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnConfiguration()
 * @model extendedMetaData="name='simqpn-configuration' kind='empty'"
 * @generated
 */
public interface SimqpnConfiguration extends SimqpnMetaAttribute, NetMetaAttribute {
	/**
	 * Returns the value of the '<em><b>Configuration Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Configuration Description</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Configuration Description</em>' attribute.
	 * @see #setConfigurationDescription(String)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnConfiguration_ConfigurationDescription()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='configuration-description' namespace='##targetNamespace'"
	 * @generated
	 */
	String getConfigurationDescription();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getConfigurationDescription <em>Configuration Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Configuration Description</em>' attribute.
	 * @see #getConfigurationDescription()
	 * @generated
	 */
	void setConfigurationDescription(String value);

	/**
	 * Returns the value of the '<em><b>Number Of Runs</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number Of Runs</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Runs</em>' attribute.
	 * @see #isSetNumberOfRuns()
	 * @see #unsetNumberOfRuns()
	 * @see #setNumberOfRuns(int)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnConfiguration_NumberOfRuns()
	 * @model default="1" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
	 *        extendedMetaData="kind='attribute' name='number-of-runs' namespace='##targetNamespace'"
	 * @generated
	 */
	int getNumberOfRuns();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getNumberOfRuns <em>Number Of Runs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Runs</em>' attribute.
	 * @see #isSetNumberOfRuns()
	 * @see #unsetNumberOfRuns()
	 * @see #getNumberOfRuns()
	 * @generated
	 */
	void setNumberOfRuns(int value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getNumberOfRuns <em>Number Of Runs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetNumberOfRuns()
	 * @see #getNumberOfRuns()
	 * @see #setNumberOfRuns(int)
	 * @generated
	 */
	void unsetNumberOfRuns();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getNumberOfRuns <em>Number Of Runs</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Number Of Runs</em>' attribute is set.
	 * @see #unsetNumberOfRuns()
	 * @see #getNumberOfRuns()
	 * @see #setNumberOfRuns(int)
	 * @generated
	 */
	boolean isSetNumberOfRuns();

	/**
	 * Returns the value of the '<em><b>Output Directory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Output Directory</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Output Directory</em>' attribute.
	 * @see #setOutputDirectory(String)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnConfiguration_OutputDirectory()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String" required="true"
	 *        extendedMetaData="kind='attribute' name='output-directory' namespace='##targetNamespace'"
	 * @generated
	 */
	String getOutputDirectory();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getOutputDirectory <em>Output Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Output Directory</em>' attribute.
	 * @see #getOutputDirectory()
	 * @generated
	 */
	void setOutputDirectory(String value);

	/**
	 * Returns the value of the '<em><b>Ramp Up Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ramp Up Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ramp Up Length</em>' attribute.
	 * @see #isSetRampUpLength()
	 * @see #unsetRampUpLength()
	 * @see #setRampUpLength(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnConfiguration_RampUpLength()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='ramp-up-length' namespace='##targetNamespace'"
	 * @generated
	 */
	double getRampUpLength();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getRampUpLength <em>Ramp Up Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ramp Up Length</em>' attribute.
	 * @see #isSetRampUpLength()
	 * @see #unsetRampUpLength()
	 * @see #getRampUpLength()
	 * @generated
	 */
	void setRampUpLength(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getRampUpLength <em>Ramp Up Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRampUpLength()
	 * @see #getRampUpLength()
	 * @see #setRampUpLength(double)
	 * @generated
	 */
	void unsetRampUpLength();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getRampUpLength <em>Ramp Up Length</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Ramp Up Length</em>' attribute is set.
	 * @see #unsetRampUpLength()
	 * @see #getRampUpLength()
	 * @see #setRampUpLength(double)
	 * @generated
	 */
	boolean isSetRampUpLength();

	/**
	 * Returns the value of the '<em><b>Scenario</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.kit.ipd.descartes.qpme.model.SimqpnSimulationScenario}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scenario</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scenario</em>' attribute.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnSimulationScenario
	 * @see #isSetScenario()
	 * @see #unsetScenario()
	 * @see #setScenario(SimqpnSimulationScenario)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnConfiguration_Scenario()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='scenario' namespace='##targetNamespace'"
	 * @generated
	 */
	SimqpnSimulationScenario getScenario();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getScenario <em>Scenario</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scenario</em>' attribute.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnSimulationScenario
	 * @see #isSetScenario()
	 * @see #unsetScenario()
	 * @see #getScenario()
	 * @generated
	 */
	void setScenario(SimqpnSimulationScenario value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getScenario <em>Scenario</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetScenario()
	 * @see #getScenario()
	 * @see #setScenario(SimqpnSimulationScenario)
	 * @generated
	 */
	void unsetScenario();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getScenario <em>Scenario</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Scenario</em>' attribute is set.
	 * @see #unsetScenario()
	 * @see #getScenario()
	 * @see #setScenario(SimqpnSimulationScenario)
	 * @generated
	 */
	boolean isSetScenario();

	/**
	 * Returns the value of the '<em><b>Seconds Between Heart Beats</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Seconds Between Heart Beats</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Seconds Between Heart Beats</em>' attribute.
	 * @see #isSetSecondsBetweenHeartBeats()
	 * @see #unsetSecondsBetweenHeartBeats()
	 * @see #setSecondsBetweenHeartBeats(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnConfiguration_SecondsBetweenHeartBeats()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='seconds-between-heart-beats' namespace='##targetNamespace'"
	 * @generated
	 */
	double getSecondsBetweenHeartBeats();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getSecondsBetweenHeartBeats <em>Seconds Between Heart Beats</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Seconds Between Heart Beats</em>' attribute.
	 * @see #isSetSecondsBetweenHeartBeats()
	 * @see #unsetSecondsBetweenHeartBeats()
	 * @see #getSecondsBetweenHeartBeats()
	 * @generated
	 */
	void setSecondsBetweenHeartBeats(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getSecondsBetweenHeartBeats <em>Seconds Between Heart Beats</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSecondsBetweenHeartBeats()
	 * @see #getSecondsBetweenHeartBeats()
	 * @see #setSecondsBetweenHeartBeats(double)
	 * @generated
	 */
	void unsetSecondsBetweenHeartBeats();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getSecondsBetweenHeartBeats <em>Seconds Between Heart Beats</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Seconds Between Heart Beats</em>' attribute is set.
	 * @see #unsetSecondsBetweenHeartBeats()
	 * @see #getSecondsBetweenHeartBeats()
	 * @see #setSecondsBetweenHeartBeats(double)
	 * @generated
	 */
	boolean isSetSecondsBetweenHeartBeats();

	/**
	 * Returns the value of the '<em><b>Seconds Between Stop Checks</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Seconds Between Stop Checks</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Seconds Between Stop Checks</em>' attribute.
	 * @see #isSetSecondsBetweenStopChecks()
	 * @see #unsetSecondsBetweenStopChecks()
	 * @see #setSecondsBetweenStopChecks(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnConfiguration_SecondsBetweenStopChecks()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='seconds-between-stop-checks' namespace='##targetNamespace'"
	 * @generated
	 */
	double getSecondsBetweenStopChecks();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getSecondsBetweenStopChecks <em>Seconds Between Stop Checks</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Seconds Between Stop Checks</em>' attribute.
	 * @see #isSetSecondsBetweenStopChecks()
	 * @see #unsetSecondsBetweenStopChecks()
	 * @see #getSecondsBetweenStopChecks()
	 * @generated
	 */
	void setSecondsBetweenStopChecks(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getSecondsBetweenStopChecks <em>Seconds Between Stop Checks</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetSecondsBetweenStopChecks()
	 * @see #getSecondsBetweenStopChecks()
	 * @see #setSecondsBetweenStopChecks(double)
	 * @generated
	 */
	void unsetSecondsBetweenStopChecks();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getSecondsBetweenStopChecks <em>Seconds Between Stop Checks</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Seconds Between Stop Checks</em>' attribute is set.
	 * @see #unsetSecondsBetweenStopChecks()
	 * @see #getSecondsBetweenStopChecks()
	 * @see #setSecondsBetweenStopChecks(double)
	 * @generated
	 */
	boolean isSetSecondsBetweenStopChecks();

	/**
	 * Returns the value of the '<em><b>Stopping Rule</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.kit.ipd.descartes.qpme.model.SimqpnStoppingRule}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Stopping Rule</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Stopping Rule</em>' attribute.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnStoppingRule
	 * @see #isSetStoppingRule()
	 * @see #unsetStoppingRule()
	 * @see #setStoppingRule(SimqpnStoppingRule)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnConfiguration_StoppingRule()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='stopping-rule' namespace='##targetNamespace'"
	 * @generated
	 */
	SimqpnStoppingRule getStoppingRule();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getStoppingRule <em>Stopping Rule</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Stopping Rule</em>' attribute.
	 * @see edu.kit.ipd.descartes.qpme.model.SimqpnStoppingRule
	 * @see #isSetStoppingRule()
	 * @see #unsetStoppingRule()
	 * @see #getStoppingRule()
	 * @generated
	 */
	void setStoppingRule(SimqpnStoppingRule value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getStoppingRule <em>Stopping Rule</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetStoppingRule()
	 * @see #getStoppingRule()
	 * @see #setStoppingRule(SimqpnStoppingRule)
	 * @generated
	 */
	void unsetStoppingRule();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getStoppingRule <em>Stopping Rule</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Stopping Rule</em>' attribute is set.
	 * @see #unsetStoppingRule()
	 * @see #getStoppingRule()
	 * @see #setStoppingRule(SimqpnStoppingRule)
	 * @generated
	 */
	boolean isSetStoppingRule();

	/**
	 * Returns the value of the '<em><b>Time Before Initial Heart Beat</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time Before Initial Heart Beat</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Before Initial Heart Beat</em>' attribute.
	 * @see #isSetTimeBeforeInitialHeartBeat()
	 * @see #unsetTimeBeforeInitialHeartBeat()
	 * @see #setTimeBeforeInitialHeartBeat(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnConfiguration_TimeBeforeInitialHeartBeat()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='time-before-initial-heart-beat' namespace='##targetNamespace'"
	 * @generated
	 */
	double getTimeBeforeInitialHeartBeat();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTimeBeforeInitialHeartBeat <em>Time Before Initial Heart Beat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Before Initial Heart Beat</em>' attribute.
	 * @see #isSetTimeBeforeInitialHeartBeat()
	 * @see #unsetTimeBeforeInitialHeartBeat()
	 * @see #getTimeBeforeInitialHeartBeat()
	 * @generated
	 */
	void setTimeBeforeInitialHeartBeat(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTimeBeforeInitialHeartBeat <em>Time Before Initial Heart Beat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTimeBeforeInitialHeartBeat()
	 * @see #getTimeBeforeInitialHeartBeat()
	 * @see #setTimeBeforeInitialHeartBeat(double)
	 * @generated
	 */
	void unsetTimeBeforeInitialHeartBeat();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTimeBeforeInitialHeartBeat <em>Time Before Initial Heart Beat</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Time Before Initial Heart Beat</em>' attribute is set.
	 * @see #unsetTimeBeforeInitialHeartBeat()
	 * @see #getTimeBeforeInitialHeartBeat()
	 * @see #setTimeBeforeInitialHeartBeat(double)
	 * @generated
	 */
	boolean isSetTimeBeforeInitialHeartBeat();

	/**
	 * Returns the value of the '<em><b>Time Between Stop Checks</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Time Between Stop Checks</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Time Between Stop Checks</em>' attribute.
	 * @see #isSetTimeBetweenStopChecks()
	 * @see #unsetTimeBetweenStopChecks()
	 * @see #setTimeBetweenStopChecks(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnConfiguration_TimeBetweenStopChecks()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='time-between-stop-checks' namespace='##targetNamespace'"
	 * @generated
	 */
	double getTimeBetweenStopChecks();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTimeBetweenStopChecks <em>Time Between Stop Checks</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Time Between Stop Checks</em>' attribute.
	 * @see #isSetTimeBetweenStopChecks()
	 * @see #unsetTimeBetweenStopChecks()
	 * @see #getTimeBetweenStopChecks()
	 * @generated
	 */
	void setTimeBetweenStopChecks(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTimeBetweenStopChecks <em>Time Between Stop Checks</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTimeBetweenStopChecks()
	 * @see #getTimeBetweenStopChecks()
	 * @see #setTimeBetweenStopChecks(double)
	 * @generated
	 */
	void unsetTimeBetweenStopChecks();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTimeBetweenStopChecks <em>Time Between Stop Checks</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Time Between Stop Checks</em>' attribute is set.
	 * @see #unsetTimeBetweenStopChecks()
	 * @see #getTimeBetweenStopChecks()
	 * @see #setTimeBetweenStopChecks(double)
	 * @generated
	 */
	boolean isSetTimeBetweenStopChecks();

	/**
	 * Returns the value of the '<em><b>Total Run Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Run Length</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Run Length</em>' attribute.
	 * @see #isSetTotalRunLength()
	 * @see #unsetTotalRunLength()
	 * @see #setTotalRunLength(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnConfiguration_TotalRunLength()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double" required="true"
	 *        extendedMetaData="kind='attribute' name='total-run-length' namespace='##targetNamespace'"
	 * @generated
	 */
	double getTotalRunLength();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTotalRunLength <em>Total Run Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Run Length</em>' attribute.
	 * @see #isSetTotalRunLength()
	 * @see #unsetTotalRunLength()
	 * @see #getTotalRunLength()
	 * @generated
	 */
	void setTotalRunLength(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTotalRunLength <em>Total Run Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTotalRunLength()
	 * @see #getTotalRunLength()
	 * @see #setTotalRunLength(double)
	 * @generated
	 */
	void unsetTotalRunLength();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getTotalRunLength <em>Total Run Length</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Total Run Length</em>' attribute is set.
	 * @see #unsetTotalRunLength()
	 * @see #getTotalRunLength()
	 * @see #setTotalRunLength(double)
	 * @generated
	 */
	boolean isSetTotalRunLength();

	/**
	 * Returns the value of the '<em><b>Verbosity Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Verbosity Level</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Verbosity Level</em>' attribute.
	 * @see #isSetVerbosityLevel()
	 * @see #unsetVerbosityLevel()
	 * @see #setVerbosityLevel(int)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnConfiguration_VerbosityLevel()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int" required="true"
	 *        extendedMetaData="kind='attribute' name='verbosity-level' namespace='##targetNamespace'"
	 * @generated
	 */
	int getVerbosityLevel();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getVerbosityLevel <em>Verbosity Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Verbosity Level</em>' attribute.
	 * @see #isSetVerbosityLevel()
	 * @see #unsetVerbosityLevel()
	 * @see #getVerbosityLevel()
	 * @generated
	 */
	void setVerbosityLevel(int value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getVerbosityLevel <em>Verbosity Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetVerbosityLevel()
	 * @see #getVerbosityLevel()
	 * @see #setVerbosityLevel(int)
	 * @generated
	 */
	void unsetVerbosityLevel();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration#getVerbosityLevel <em>Verbosity Level</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Verbosity Level</em>' attribute is set.
	 * @see #unsetVerbosityLevel()
	 * @see #getVerbosityLevel()
	 * @see #setVerbosityLevel(int)
	 * @generated
	 */
	boolean isSetVerbosityLevel();

} // SimqpnConfiguration
