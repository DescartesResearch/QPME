/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.SimqpnConfiguration;
import edu.kit.ipd.descartes.qpme.model.SimqpnSimulationScenario;
import edu.kit.ipd.descartes.qpme.model.SimqpnStoppingRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simqpn Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnConfigurationImpl#getConfigurationDescription <em>Configuration Description</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnConfigurationImpl#getNumberOfRuns <em>Number Of Runs</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnConfigurationImpl#getOutputDirectory <em>Output Directory</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnConfigurationImpl#getRampUpLength <em>Ramp Up Length</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnConfigurationImpl#getScenario <em>Scenario</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnConfigurationImpl#getSecondsBetweenHeartBeats <em>Seconds Between Heart Beats</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnConfigurationImpl#getSecondsBetweenStopChecks <em>Seconds Between Stop Checks</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnConfigurationImpl#getStoppingRule <em>Stopping Rule</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnConfigurationImpl#getTimeBeforeInitialHeartBeat <em>Time Before Initial Heart Beat</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnConfigurationImpl#getTimeBetweenStopChecks <em>Time Between Stop Checks</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnConfigurationImpl#getTotalRunLength <em>Total Run Length</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.SimqpnConfigurationImpl#getVerbosityLevel <em>Verbosity Level</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SimqpnConfigurationImpl extends SimqpnMetaAttributeImpl implements SimqpnConfiguration {
	/**
	 * The default value of the '{@link #getConfigurationDescription() <em>Configuration Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigurationDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String CONFIGURATION_DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConfigurationDescription() <em>Configuration Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfigurationDescription()
	 * @generated
	 * @ordered
	 */
	protected String configurationDescription = CONFIGURATION_DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getNumberOfRuns() <em>Number Of Runs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfRuns()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_RUNS_EDEFAULT = 1;

	/**
	 * The cached value of the '{@link #getNumberOfRuns() <em>Number Of Runs</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfRuns()
	 * @generated
	 * @ordered
	 */
	protected int numberOfRuns = NUMBER_OF_RUNS_EDEFAULT;

	/**
	 * This is true if the Number Of Runs attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean numberOfRunsESet;

	/**
	 * The default value of the '{@link #getOutputDirectory() <em>Output Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputDirectory()
	 * @generated
	 * @ordered
	 */
	protected static final String OUTPUT_DIRECTORY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getOutputDirectory() <em>Output Directory</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutputDirectory()
	 * @generated
	 * @ordered
	 */
	protected String outputDirectory = OUTPUT_DIRECTORY_EDEFAULT;

	/**
	 * The default value of the '{@link #getRampUpLength() <em>Ramp Up Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRampUpLength()
	 * @generated
	 * @ordered
	 */
	protected static final double RAMP_UP_LENGTH_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getRampUpLength() <em>Ramp Up Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRampUpLength()
	 * @generated
	 * @ordered
	 */
	protected double rampUpLength = RAMP_UP_LENGTH_EDEFAULT;

	/**
	 * This is true if the Ramp Up Length attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean rampUpLengthESet;

	/**
	 * The default value of the '{@link #getScenario() <em>Scenario</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScenario()
	 * @generated
	 * @ordered
	 */
	protected static final SimqpnSimulationScenario SCENARIO_EDEFAULT = SimqpnSimulationScenario.BATCH_MEANS;

	/**
	 * The cached value of the '{@link #getScenario() <em>Scenario</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScenario()
	 * @generated
	 * @ordered
	 */
	protected SimqpnSimulationScenario scenario = SCENARIO_EDEFAULT;

	/**
	 * This is true if the Scenario attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean scenarioESet;

	/**
	 * The default value of the '{@link #getSecondsBetweenHeartBeats() <em>Seconds Between Heart Beats</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondsBetweenHeartBeats()
	 * @generated
	 * @ordered
	 */
	protected static final double SECONDS_BETWEEN_HEART_BEATS_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getSecondsBetweenHeartBeats() <em>Seconds Between Heart Beats</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondsBetweenHeartBeats()
	 * @generated
	 * @ordered
	 */
	protected double secondsBetweenHeartBeats = SECONDS_BETWEEN_HEART_BEATS_EDEFAULT;

	/**
	 * This is true if the Seconds Between Heart Beats attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean secondsBetweenHeartBeatsESet;

	/**
	 * The default value of the '{@link #getSecondsBetweenStopChecks() <em>Seconds Between Stop Checks</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondsBetweenStopChecks()
	 * @generated
	 * @ordered
	 */
	protected static final double SECONDS_BETWEEN_STOP_CHECKS_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getSecondsBetweenStopChecks() <em>Seconds Between Stop Checks</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSecondsBetweenStopChecks()
	 * @generated
	 * @ordered
	 */
	protected double secondsBetweenStopChecks = SECONDS_BETWEEN_STOP_CHECKS_EDEFAULT;

	/**
	 * This is true if the Seconds Between Stop Checks attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean secondsBetweenStopChecksESet;

	/**
	 * The default value of the '{@link #getStoppingRule() <em>Stopping Rule</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStoppingRule()
	 * @generated
	 * @ordered
	 */
	protected static final SimqpnStoppingRule STOPPING_RULE_EDEFAULT = SimqpnStoppingRule.ABSOLUTE_PRECISION;

	/**
	 * The cached value of the '{@link #getStoppingRule() <em>Stopping Rule</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStoppingRule()
	 * @generated
	 * @ordered
	 */
	protected SimqpnStoppingRule stoppingRule = STOPPING_RULE_EDEFAULT;

	/**
	 * This is true if the Stopping Rule attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean stoppingRuleESet;

	/**
	 * The default value of the '{@link #getTimeBeforeInitialHeartBeat() <em>Time Before Initial Heart Beat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeBeforeInitialHeartBeat()
	 * @generated
	 * @ordered
	 */
	protected static final double TIME_BEFORE_INITIAL_HEART_BEAT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getTimeBeforeInitialHeartBeat() <em>Time Before Initial Heart Beat</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeBeforeInitialHeartBeat()
	 * @generated
	 * @ordered
	 */
	protected double timeBeforeInitialHeartBeat = TIME_BEFORE_INITIAL_HEART_BEAT_EDEFAULT;

	/**
	 * This is true if the Time Before Initial Heart Beat attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean timeBeforeInitialHeartBeatESet;

	/**
	 * The default value of the '{@link #getTimeBetweenStopChecks() <em>Time Between Stop Checks</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeBetweenStopChecks()
	 * @generated
	 * @ordered
	 */
	protected static final double TIME_BETWEEN_STOP_CHECKS_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getTimeBetweenStopChecks() <em>Time Between Stop Checks</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimeBetweenStopChecks()
	 * @generated
	 * @ordered
	 */
	protected double timeBetweenStopChecks = TIME_BETWEEN_STOP_CHECKS_EDEFAULT;

	/**
	 * This is true if the Time Between Stop Checks attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean timeBetweenStopChecksESet;

	/**
	 * The default value of the '{@link #getTotalRunLength() <em>Total Run Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalRunLength()
	 * @generated
	 * @ordered
	 */
	protected static final double TOTAL_RUN_LENGTH_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getTotalRunLength() <em>Total Run Length</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTotalRunLength()
	 * @generated
	 * @ordered
	 */
	protected double totalRunLength = TOTAL_RUN_LENGTH_EDEFAULT;

	/**
	 * This is true if the Total Run Length attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean totalRunLengthESet;

	/**
	 * The default value of the '{@link #getVerbosityLevel() <em>Verbosity Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVerbosityLevel()
	 * @generated
	 * @ordered
	 */
	protected static final int VERBOSITY_LEVEL_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getVerbosityLevel() <em>Verbosity Level</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVerbosityLevel()
	 * @generated
	 * @ordered
	 */
	protected int verbosityLevel = VERBOSITY_LEVEL_EDEFAULT;

	/**
	 * This is true if the Verbosity Level attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean verbosityLevelESet;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimqpnConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.SIMQPN_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getConfigurationDescription() {
		return configurationDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConfigurationDescription(String newConfigurationDescription) {
		String oldConfigurationDescription = configurationDescription;
		configurationDescription = newConfigurationDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_CONFIGURATION__CONFIGURATION_DESCRIPTION, oldConfigurationDescription, configurationDescription));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfRuns() {
		return numberOfRuns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfRuns(int newNumberOfRuns) {
		int oldNumberOfRuns = numberOfRuns;
		numberOfRuns = newNumberOfRuns;
		boolean oldNumberOfRunsESet = numberOfRunsESet;
		numberOfRunsESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_CONFIGURATION__NUMBER_OF_RUNS, oldNumberOfRuns, numberOfRuns, !oldNumberOfRunsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetNumberOfRuns() {
		int oldNumberOfRuns = numberOfRuns;
		boolean oldNumberOfRunsESet = numberOfRunsESet;
		numberOfRuns = NUMBER_OF_RUNS_EDEFAULT;
		numberOfRunsESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_CONFIGURATION__NUMBER_OF_RUNS, oldNumberOfRuns, NUMBER_OF_RUNS_EDEFAULT, oldNumberOfRunsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetNumberOfRuns() {
		return numberOfRunsESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOutputDirectory() {
		return outputDirectory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOutputDirectory(String newOutputDirectory) {
		String oldOutputDirectory = outputDirectory;
		outputDirectory = newOutputDirectory;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_CONFIGURATION__OUTPUT_DIRECTORY, oldOutputDirectory, outputDirectory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getRampUpLength() {
		return rampUpLength;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRampUpLength(double newRampUpLength) {
		double oldRampUpLength = rampUpLength;
		rampUpLength = newRampUpLength;
		boolean oldRampUpLengthESet = rampUpLengthESet;
		rampUpLengthESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_CONFIGURATION__RAMP_UP_LENGTH, oldRampUpLength, rampUpLength, !oldRampUpLengthESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetRampUpLength() {
		double oldRampUpLength = rampUpLength;
		boolean oldRampUpLengthESet = rampUpLengthESet;
		rampUpLength = RAMP_UP_LENGTH_EDEFAULT;
		rampUpLengthESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_CONFIGURATION__RAMP_UP_LENGTH, oldRampUpLength, RAMP_UP_LENGTH_EDEFAULT, oldRampUpLengthESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetRampUpLength() {
		return rampUpLengthESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnSimulationScenario getScenario() {
		return scenario;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScenario(SimqpnSimulationScenario newScenario) {
		SimqpnSimulationScenario oldScenario = scenario;
		scenario = newScenario == null ? SCENARIO_EDEFAULT : newScenario;
		boolean oldScenarioESet = scenarioESet;
		scenarioESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_CONFIGURATION__SCENARIO, oldScenario, scenario, !oldScenarioESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetScenario() {
		SimqpnSimulationScenario oldScenario = scenario;
		boolean oldScenarioESet = scenarioESet;
		scenario = SCENARIO_EDEFAULT;
		scenarioESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_CONFIGURATION__SCENARIO, oldScenario, SCENARIO_EDEFAULT, oldScenarioESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetScenario() {
		return scenarioESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getSecondsBetweenHeartBeats() {
		return secondsBetweenHeartBeats;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSecondsBetweenHeartBeats(double newSecondsBetweenHeartBeats) {
		double oldSecondsBetweenHeartBeats = secondsBetweenHeartBeats;
		secondsBetweenHeartBeats = newSecondsBetweenHeartBeats;
		boolean oldSecondsBetweenHeartBeatsESet = secondsBetweenHeartBeatsESet;
		secondsBetweenHeartBeatsESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_CONFIGURATION__SECONDS_BETWEEN_HEART_BEATS, oldSecondsBetweenHeartBeats, secondsBetweenHeartBeats, !oldSecondsBetweenHeartBeatsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSecondsBetweenHeartBeats() {
		double oldSecondsBetweenHeartBeats = secondsBetweenHeartBeats;
		boolean oldSecondsBetweenHeartBeatsESet = secondsBetweenHeartBeatsESet;
		secondsBetweenHeartBeats = SECONDS_BETWEEN_HEART_BEATS_EDEFAULT;
		secondsBetweenHeartBeatsESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_CONFIGURATION__SECONDS_BETWEEN_HEART_BEATS, oldSecondsBetweenHeartBeats, SECONDS_BETWEEN_HEART_BEATS_EDEFAULT, oldSecondsBetweenHeartBeatsESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSecondsBetweenHeartBeats() {
		return secondsBetweenHeartBeatsESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getSecondsBetweenStopChecks() {
		return secondsBetweenStopChecks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSecondsBetweenStopChecks(double newSecondsBetweenStopChecks) {
		double oldSecondsBetweenStopChecks = secondsBetweenStopChecks;
		secondsBetweenStopChecks = newSecondsBetweenStopChecks;
		boolean oldSecondsBetweenStopChecksESet = secondsBetweenStopChecksESet;
		secondsBetweenStopChecksESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_CONFIGURATION__SECONDS_BETWEEN_STOP_CHECKS, oldSecondsBetweenStopChecks, secondsBetweenStopChecks, !oldSecondsBetweenStopChecksESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetSecondsBetweenStopChecks() {
		double oldSecondsBetweenStopChecks = secondsBetweenStopChecks;
		boolean oldSecondsBetweenStopChecksESet = secondsBetweenStopChecksESet;
		secondsBetweenStopChecks = SECONDS_BETWEEN_STOP_CHECKS_EDEFAULT;
		secondsBetweenStopChecksESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_CONFIGURATION__SECONDS_BETWEEN_STOP_CHECKS, oldSecondsBetweenStopChecks, SECONDS_BETWEEN_STOP_CHECKS_EDEFAULT, oldSecondsBetweenStopChecksESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetSecondsBetweenStopChecks() {
		return secondsBetweenStopChecksESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimqpnStoppingRule getStoppingRule() {
		return stoppingRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStoppingRule(SimqpnStoppingRule newStoppingRule) {
		SimqpnStoppingRule oldStoppingRule = stoppingRule;
		stoppingRule = newStoppingRule == null ? STOPPING_RULE_EDEFAULT : newStoppingRule;
		boolean oldStoppingRuleESet = stoppingRuleESet;
		stoppingRuleESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_CONFIGURATION__STOPPING_RULE, oldStoppingRule, stoppingRule, !oldStoppingRuleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetStoppingRule() {
		SimqpnStoppingRule oldStoppingRule = stoppingRule;
		boolean oldStoppingRuleESet = stoppingRuleESet;
		stoppingRule = STOPPING_RULE_EDEFAULT;
		stoppingRuleESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_CONFIGURATION__STOPPING_RULE, oldStoppingRule, STOPPING_RULE_EDEFAULT, oldStoppingRuleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetStoppingRule() {
		return stoppingRuleESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getTimeBeforeInitialHeartBeat() {
		return timeBeforeInitialHeartBeat;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeBeforeInitialHeartBeat(double newTimeBeforeInitialHeartBeat) {
		double oldTimeBeforeInitialHeartBeat = timeBeforeInitialHeartBeat;
		timeBeforeInitialHeartBeat = newTimeBeforeInitialHeartBeat;
		boolean oldTimeBeforeInitialHeartBeatESet = timeBeforeInitialHeartBeatESet;
		timeBeforeInitialHeartBeatESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_CONFIGURATION__TIME_BEFORE_INITIAL_HEART_BEAT, oldTimeBeforeInitialHeartBeat, timeBeforeInitialHeartBeat, !oldTimeBeforeInitialHeartBeatESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTimeBeforeInitialHeartBeat() {
		double oldTimeBeforeInitialHeartBeat = timeBeforeInitialHeartBeat;
		boolean oldTimeBeforeInitialHeartBeatESet = timeBeforeInitialHeartBeatESet;
		timeBeforeInitialHeartBeat = TIME_BEFORE_INITIAL_HEART_BEAT_EDEFAULT;
		timeBeforeInitialHeartBeatESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_CONFIGURATION__TIME_BEFORE_INITIAL_HEART_BEAT, oldTimeBeforeInitialHeartBeat, TIME_BEFORE_INITIAL_HEART_BEAT_EDEFAULT, oldTimeBeforeInitialHeartBeatESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTimeBeforeInitialHeartBeat() {
		return timeBeforeInitialHeartBeatESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getTimeBetweenStopChecks() {
		return timeBetweenStopChecks;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimeBetweenStopChecks(double newTimeBetweenStopChecks) {
		double oldTimeBetweenStopChecks = timeBetweenStopChecks;
		timeBetweenStopChecks = newTimeBetweenStopChecks;
		boolean oldTimeBetweenStopChecksESet = timeBetweenStopChecksESet;
		timeBetweenStopChecksESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_CONFIGURATION__TIME_BETWEEN_STOP_CHECKS, oldTimeBetweenStopChecks, timeBetweenStopChecks, !oldTimeBetweenStopChecksESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTimeBetweenStopChecks() {
		double oldTimeBetweenStopChecks = timeBetweenStopChecks;
		boolean oldTimeBetweenStopChecksESet = timeBetweenStopChecksESet;
		timeBetweenStopChecks = TIME_BETWEEN_STOP_CHECKS_EDEFAULT;
		timeBetweenStopChecksESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_CONFIGURATION__TIME_BETWEEN_STOP_CHECKS, oldTimeBetweenStopChecks, TIME_BETWEEN_STOP_CHECKS_EDEFAULT, oldTimeBetweenStopChecksESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTimeBetweenStopChecks() {
		return timeBetweenStopChecksESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getTotalRunLength() {
		return totalRunLength;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTotalRunLength(double newTotalRunLength) {
		double oldTotalRunLength = totalRunLength;
		totalRunLength = newTotalRunLength;
		boolean oldTotalRunLengthESet = totalRunLengthESet;
		totalRunLengthESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_CONFIGURATION__TOTAL_RUN_LENGTH, oldTotalRunLength, totalRunLength, !oldTotalRunLengthESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTotalRunLength() {
		double oldTotalRunLength = totalRunLength;
		boolean oldTotalRunLengthESet = totalRunLengthESet;
		totalRunLength = TOTAL_RUN_LENGTH_EDEFAULT;
		totalRunLengthESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_CONFIGURATION__TOTAL_RUN_LENGTH, oldTotalRunLength, TOTAL_RUN_LENGTH_EDEFAULT, oldTotalRunLengthESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTotalRunLength() {
		return totalRunLengthESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getVerbosityLevel() {
		return verbosityLevel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVerbosityLevel(int newVerbosityLevel) {
		int oldVerbosityLevel = verbosityLevel;
		verbosityLevel = newVerbosityLevel;
		boolean oldVerbosityLevelESet = verbosityLevelESet;
		verbosityLevelESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.SIMQPN_CONFIGURATION__VERBOSITY_LEVEL, oldVerbosityLevel, verbosityLevel, !oldVerbosityLevelESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetVerbosityLevel() {
		int oldVerbosityLevel = verbosityLevel;
		boolean oldVerbosityLevelESet = verbosityLevelESet;
		verbosityLevel = VERBOSITY_LEVEL_EDEFAULT;
		verbosityLevelESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.SIMQPN_CONFIGURATION__VERBOSITY_LEVEL, oldVerbosityLevel, VERBOSITY_LEVEL_EDEFAULT, oldVerbosityLevelESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetVerbosityLevel() {
		return verbosityLevelESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.SIMQPN_CONFIGURATION__CONFIGURATION_DESCRIPTION:
				return getConfigurationDescription();
			case ModelPackage.SIMQPN_CONFIGURATION__NUMBER_OF_RUNS:
				return getNumberOfRuns();
			case ModelPackage.SIMQPN_CONFIGURATION__OUTPUT_DIRECTORY:
				return getOutputDirectory();
			case ModelPackage.SIMQPN_CONFIGURATION__RAMP_UP_LENGTH:
				return getRampUpLength();
			case ModelPackage.SIMQPN_CONFIGURATION__SCENARIO:
				return getScenario();
			case ModelPackage.SIMQPN_CONFIGURATION__SECONDS_BETWEEN_HEART_BEATS:
				return getSecondsBetweenHeartBeats();
			case ModelPackage.SIMQPN_CONFIGURATION__SECONDS_BETWEEN_STOP_CHECKS:
				return getSecondsBetweenStopChecks();
			case ModelPackage.SIMQPN_CONFIGURATION__STOPPING_RULE:
				return getStoppingRule();
			case ModelPackage.SIMQPN_CONFIGURATION__TIME_BEFORE_INITIAL_HEART_BEAT:
				return getTimeBeforeInitialHeartBeat();
			case ModelPackage.SIMQPN_CONFIGURATION__TIME_BETWEEN_STOP_CHECKS:
				return getTimeBetweenStopChecks();
			case ModelPackage.SIMQPN_CONFIGURATION__TOTAL_RUN_LENGTH:
				return getTotalRunLength();
			case ModelPackage.SIMQPN_CONFIGURATION__VERBOSITY_LEVEL:
				return getVerbosityLevel();
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
			case ModelPackage.SIMQPN_CONFIGURATION__CONFIGURATION_DESCRIPTION:
				setConfigurationDescription((String)newValue);
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__NUMBER_OF_RUNS:
				setNumberOfRuns((Integer)newValue);
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__OUTPUT_DIRECTORY:
				setOutputDirectory((String)newValue);
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__RAMP_UP_LENGTH:
				setRampUpLength((Double)newValue);
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__SCENARIO:
				setScenario((SimqpnSimulationScenario)newValue);
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__SECONDS_BETWEEN_HEART_BEATS:
				setSecondsBetweenHeartBeats((Double)newValue);
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__SECONDS_BETWEEN_STOP_CHECKS:
				setSecondsBetweenStopChecks((Double)newValue);
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__STOPPING_RULE:
				setStoppingRule((SimqpnStoppingRule)newValue);
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__TIME_BEFORE_INITIAL_HEART_BEAT:
				setTimeBeforeInitialHeartBeat((Double)newValue);
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__TIME_BETWEEN_STOP_CHECKS:
				setTimeBetweenStopChecks((Double)newValue);
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__TOTAL_RUN_LENGTH:
				setTotalRunLength((Double)newValue);
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__VERBOSITY_LEVEL:
				setVerbosityLevel((Integer)newValue);
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
			case ModelPackage.SIMQPN_CONFIGURATION__CONFIGURATION_DESCRIPTION:
				setConfigurationDescription(CONFIGURATION_DESCRIPTION_EDEFAULT);
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__NUMBER_OF_RUNS:
				unsetNumberOfRuns();
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__OUTPUT_DIRECTORY:
				setOutputDirectory(OUTPUT_DIRECTORY_EDEFAULT);
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__RAMP_UP_LENGTH:
				unsetRampUpLength();
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__SCENARIO:
				unsetScenario();
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__SECONDS_BETWEEN_HEART_BEATS:
				unsetSecondsBetweenHeartBeats();
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__SECONDS_BETWEEN_STOP_CHECKS:
				unsetSecondsBetweenStopChecks();
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__STOPPING_RULE:
				unsetStoppingRule();
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__TIME_BEFORE_INITIAL_HEART_BEAT:
				unsetTimeBeforeInitialHeartBeat();
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__TIME_BETWEEN_STOP_CHECKS:
				unsetTimeBetweenStopChecks();
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__TOTAL_RUN_LENGTH:
				unsetTotalRunLength();
				return;
			case ModelPackage.SIMQPN_CONFIGURATION__VERBOSITY_LEVEL:
				unsetVerbosityLevel();
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
			case ModelPackage.SIMQPN_CONFIGURATION__CONFIGURATION_DESCRIPTION:
				return CONFIGURATION_DESCRIPTION_EDEFAULT == null ? configurationDescription != null : !CONFIGURATION_DESCRIPTION_EDEFAULT.equals(configurationDescription);
			case ModelPackage.SIMQPN_CONFIGURATION__NUMBER_OF_RUNS:
				return isSetNumberOfRuns();
			case ModelPackage.SIMQPN_CONFIGURATION__OUTPUT_DIRECTORY:
				return OUTPUT_DIRECTORY_EDEFAULT == null ? outputDirectory != null : !OUTPUT_DIRECTORY_EDEFAULT.equals(outputDirectory);
			case ModelPackage.SIMQPN_CONFIGURATION__RAMP_UP_LENGTH:
				return isSetRampUpLength();
			case ModelPackage.SIMQPN_CONFIGURATION__SCENARIO:
				return isSetScenario();
			case ModelPackage.SIMQPN_CONFIGURATION__SECONDS_BETWEEN_HEART_BEATS:
				return isSetSecondsBetweenHeartBeats();
			case ModelPackage.SIMQPN_CONFIGURATION__SECONDS_BETWEEN_STOP_CHECKS:
				return isSetSecondsBetweenStopChecks();
			case ModelPackage.SIMQPN_CONFIGURATION__STOPPING_RULE:
				return isSetStoppingRule();
			case ModelPackage.SIMQPN_CONFIGURATION__TIME_BEFORE_INITIAL_HEART_BEAT:
				return isSetTimeBeforeInitialHeartBeat();
			case ModelPackage.SIMQPN_CONFIGURATION__TIME_BETWEEN_STOP_CHECKS:
				return isSetTimeBetweenStopChecks();
			case ModelPackage.SIMQPN_CONFIGURATION__TOTAL_RUN_LENGTH:
				return isSetTotalRunLength();
			case ModelPackage.SIMQPN_CONFIGURATION__VERBOSITY_LEVEL:
				return isSetVerbosityLevel();
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
		result.append(" (configurationDescription: ");
		result.append(configurationDescription);
		result.append(", numberOfRuns: ");
		if (numberOfRunsESet) result.append(numberOfRuns); else result.append("<unset>");
		result.append(", outputDirectory: ");
		result.append(outputDirectory);
		result.append(", rampUpLength: ");
		if (rampUpLengthESet) result.append(rampUpLength); else result.append("<unset>");
		result.append(", scenario: ");
		if (scenarioESet) result.append(scenario); else result.append("<unset>");
		result.append(", secondsBetweenHeartBeats: ");
		if (secondsBetweenHeartBeatsESet) result.append(secondsBetweenHeartBeats); else result.append("<unset>");
		result.append(", secondsBetweenStopChecks: ");
		if (secondsBetweenStopChecksESet) result.append(secondsBetweenStopChecks); else result.append("<unset>");
		result.append(", stoppingRule: ");
		if (stoppingRuleESet) result.append(stoppingRule); else result.append("<unset>");
		result.append(", timeBeforeInitialHeartBeat: ");
		if (timeBeforeInitialHeartBeatESet) result.append(timeBeforeInitialHeartBeat); else result.append("<unset>");
		result.append(", timeBetweenStopChecks: ");
		if (timeBetweenStopChecksESet) result.append(timeBetweenStopChecks); else result.append("<unset>");
		result.append(", totalRunLength: ");
		if (totalRunLengthESet) result.append(totalRunLength); else result.append("<unset>");
		result.append(", verbosityLevel: ");
		if (verbosityLevelESet) result.append(verbosityLevel); else result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} //SimqpnConfigurationImpl
