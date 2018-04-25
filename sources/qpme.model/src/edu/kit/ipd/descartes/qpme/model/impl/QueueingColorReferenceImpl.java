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

import edu.kit.ipd.descartes.qpme.model.DistributionFunction;
import edu.kit.ipd.descartes.qpme.model.ModelPackage;
import edu.kit.ipd.descartes.qpme.model.QueueingColorReference;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Queueing Color Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getAlpha <em>Alpha</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getBeta <em>Beta</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getCut <em>Cut</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getDistributionFunction <em>Distribution Function</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getFreedom <em>Freedom</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getGamma <em>Gamma</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getInputFile <em>Input File</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getLambda <em>Lambda</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getMax <em>Max</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getMean <em>Mean</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getMin <em>Min</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getP <em>P</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getC <em>C</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getPriority <em>Priority</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getRanking <em>Ranking</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getStdDev <em>Std Dev</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getTau <em>Tau</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getOffset <em>Offset</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getScale <em>Scale</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getReplayFile <em>Replay File</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getValuesFile <em>Values File</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getProbabilitiesFile <em>Probabilities File</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getConcurrenciesFile <em>Concurrencies File</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.QueueingColorReferenceImpl#getResponsetimesFile <em>Responsetimes File</em>}</li>
 * </ul>
 *
 * @generated
 */
public class QueueingColorReferenceImpl extends PlaceColorReferenceImpl implements QueueingColorReference {
	/**
	 * The default value of the '{@link #getAlpha() <em>Alpha</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlpha()
	 * @generated
	 * @ordered
	 */
	protected static final double ALPHA_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getAlpha() <em>Alpha</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAlpha()
	 * @generated
	 * @ordered
	 */
	protected double alpha = ALPHA_EDEFAULT;

	/**
	 * This is true if the Alpha attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean alphaESet;

	/**
	 * The default value of the '{@link #getBeta() <em>Beta</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBeta()
	 * @generated
	 * @ordered
	 */
	protected static final double BETA_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getBeta() <em>Beta</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBeta()
	 * @generated
	 * @ordered
	 */
	protected double beta = BETA_EDEFAULT;

	/**
	 * This is true if the Beta attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean betaESet;

	/**
	 * The default value of the '{@link #getCut() <em>Cut</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCut()
	 * @generated
	 * @ordered
	 */
	protected static final double CUT_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getCut() <em>Cut</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCut()
	 * @generated
	 * @ordered
	 */
	protected double cut = CUT_EDEFAULT;

	/**
	 * This is true if the Cut attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean cutESet;

	/**
	 * The default value of the '{@link #getDistributionFunction() <em>Distribution Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDistributionFunction()
	 * @generated
	 * @ordered
	 */
	protected static final DistributionFunction DISTRIBUTION_FUNCTION_EDEFAULT = DistributionFunction.BETA;

	/**
	 * The cached value of the '{@link #getDistributionFunction() <em>Distribution Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDistributionFunction()
	 * @generated
	 * @ordered
	 */
	protected DistributionFunction distributionFunction = DISTRIBUTION_FUNCTION_EDEFAULT;

	/**
	 * This is true if the Distribution Function attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean distributionFunctionESet;

	/**
	 * The default value of the '{@link #getFreedom() <em>Freedom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFreedom()
	 * @generated
	 * @ordered
	 */
	protected static final double FREEDOM_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getFreedom() <em>Freedom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFreedom()
	 * @generated
	 * @ordered
	 */
	protected double freedom = FREEDOM_EDEFAULT;

	/**
	 * This is true if the Freedom attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean freedomESet;

	/**
	 * The default value of the '{@link #getGamma() <em>Gamma</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGamma()
	 * @generated
	 * @ordered
	 */
	protected static final double GAMMA_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getGamma() <em>Gamma</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGamma()
	 * @generated
	 * @ordered
	 */
	protected double gamma = GAMMA_EDEFAULT;

	/**
	 * This is true if the Gamma attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean gammaESet;

	/**
	 * The default value of the '{@link #getInputFile() <em>Input File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputFile()
	 * @generated
	 * @ordered
	 */
	protected static final String INPUT_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getInputFile() <em>Input File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInputFile()
	 * @generated
	 * @ordered
	 */
	protected String inputFile = INPUT_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getLambda() <em>Lambda</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLambda()
	 * @generated
	 * @ordered
	 */
	protected static final double LAMBDA_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getLambda() <em>Lambda</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLambda()
	 * @generated
	 * @ordered
	 */
	protected double lambda = LAMBDA_EDEFAULT;

	/**
	 * This is true if the Lambda attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean lambdaESet;

	/**
	 * The default value of the '{@link #getMax() <em>Max</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMax()
	 * @generated
	 * @ordered
	 */
	protected static final double MAX_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getMax() <em>Max</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMax()
	 * @generated
	 * @ordered
	 */
	protected double max = MAX_EDEFAULT;

	/**
	 * This is true if the Max attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean maxESet;

	/**
	 * The default value of the '{@link #getMean() <em>Mean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMean()
	 * @generated
	 * @ordered
	 */
	protected static final double MEAN_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getMean() <em>Mean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMean()
	 * @generated
	 * @ordered
	 */
	protected double mean = MEAN_EDEFAULT;

	/**
	 * This is true if the Mean attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean meanESet;

	/**
	 * The default value of the '{@link #getMin() <em>Min</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMin()
	 * @generated
	 * @ordered
	 */
	protected static final double MIN_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getMin() <em>Min</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMin()
	 * @generated
	 * @ordered
	 */
	protected double min = MIN_EDEFAULT;

	/**
	 * This is true if the Min attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean minESet;

	/**
	 * The default value of the '{@link #getP() <em>P</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getP()
	 * @generated
	 * @ordered
	 */
	protected static final double P_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getP() <em>P</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getP()
	 * @generated
	 * @ordered
	 */
	protected double p = P_EDEFAULT;

	/**
	 * This is true if the P attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean pESet;

	/**
	 * The default value of the '{@link #getC() <em>C</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getC()
	 * @generated
	 * @ordered
	 */
	protected static final double C_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getC() <em>C</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getC()
	 * @generated
	 * @ordered
	 */
	protected double c = C_EDEFAULT;

	/**
	 * This is true if the C attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean cESet;

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
	 * The default value of the '{@link #getRanking() <em>Ranking</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRanking()
	 * @generated
	 * @ordered
	 */
	protected static final long RANKING_EDEFAULT = 0L;

	/**
	 * The cached value of the '{@link #getRanking() <em>Ranking</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRanking()
	 * @generated
	 * @ordered
	 */
	protected long ranking = RANKING_EDEFAULT;

	/**
	 * This is true if the Ranking attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean rankingESet;

	/**
	 * The default value of the '{@link #getStdDev() <em>Std Dev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStdDev()
	 * @generated
	 * @ordered
	 */
	protected static final double STD_DEV_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getStdDev() <em>Std Dev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStdDev()
	 * @generated
	 * @ordered
	 */
	protected double stdDev = STD_DEV_EDEFAULT;

	/**
	 * This is true if the Std Dev attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean stdDevESet;

	/**
	 * The default value of the '{@link #getTau() <em>Tau</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTau()
	 * @generated
	 * @ordered
	 */
	protected static final double TAU_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getTau() <em>Tau</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTau()
	 * @generated
	 * @ordered
	 */
	protected double tau = TAU_EDEFAULT;

	/**
	 * This is true if the Tau attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean tauESet;

	/**
	 * The default value of the '{@link #getOffset() <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffset()
	 * @generated
	 * @ordered
	 */
	protected static final double OFFSET_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getOffset() <em>Offset</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOffset()
	 * @generated
	 * @ordered
	 */
	protected double offset = OFFSET_EDEFAULT;

	/**
	 * This is true if the Offset attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean offsetESet;

	/**
	 * The default value of the '{@link #getScale() <em>Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScale()
	 * @generated
	 * @ordered
	 */
	protected static final double SCALE_EDEFAULT = 0.0;

	/**
	 * The cached value of the '{@link #getScale() <em>Scale</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getScale()
	 * @generated
	 * @ordered
	 */
	protected double scale = SCALE_EDEFAULT;

	/**
	 * This is true if the Scale attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	protected boolean scaleESet;

	/**
	 * The default value of the '{@link #getReplayFile() <em>Replay File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReplayFile()
	 * @generated
	 * @ordered
	 */
	protected static final String REPLAY_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getReplayFile() <em>Replay File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReplayFile()
	 * @generated
	 * @ordered
	 */
	protected String replayFile = REPLAY_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getValuesFile() <em>Values File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValuesFile()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUES_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getValuesFile() <em>Values File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValuesFile()
	 * @generated
	 * @ordered
	 */
	protected String valuesFile = VALUES_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getProbabilitiesFile() <em>Probabilities File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProbabilitiesFile()
	 * @generated
	 * @ordered
	 */
	protected static final String PROBABILITIES_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProbabilitiesFile() <em>Probabilities File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProbabilitiesFile()
	 * @generated
	 * @ordered
	 */
	protected String probabilitiesFile = PROBABILITIES_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getConcurrenciesFile() <em>Concurrencies File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConcurrenciesFile()
	 * @generated
	 * @ordered
	 */
	protected static final String CONCURRENCIES_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getConcurrenciesFile() <em>Concurrencies File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConcurrenciesFile()
	 * @generated
	 * @ordered
	 */
	protected String concurrenciesFile = CONCURRENCIES_FILE_EDEFAULT;

	/**
	 * The default value of the '{@link #getResponsetimesFile() <em>Responsetimes File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResponsetimesFile()
	 * @generated
	 * @ordered
	 */
	protected static final String RESPONSETIMES_FILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getResponsetimesFile() <em>Responsetimes File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getResponsetimesFile()
	 * @generated
	 * @ordered
	 */
	protected String responsetimesFile = RESPONSETIMES_FILE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected QueueingColorReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.QUEUEING_COLOR_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getAlpha() {
		return alpha;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAlpha(double newAlpha) {
		double oldAlpha = alpha;
		alpha = newAlpha;
		boolean oldAlphaESet = alphaESet;
		alphaESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__ALPHA, oldAlpha, alpha, !oldAlphaESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetAlpha() {
		double oldAlpha = alpha;
		boolean oldAlphaESet = alphaESet;
		alpha = ALPHA_EDEFAULT;
		alphaESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__ALPHA, oldAlpha, ALPHA_EDEFAULT, oldAlphaESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetAlpha() {
		return alphaESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getBeta() {
		return beta;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBeta(double newBeta) {
		double oldBeta = beta;
		beta = newBeta;
		boolean oldBetaESet = betaESet;
		betaESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__BETA, oldBeta, beta, !oldBetaESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetBeta() {
		double oldBeta = beta;
		boolean oldBetaESet = betaESet;
		beta = BETA_EDEFAULT;
		betaESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__BETA, oldBeta, BETA_EDEFAULT, oldBetaESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetBeta() {
		return betaESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getCut() {
		return cut;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCut(double newCut) {
		double oldCut = cut;
		cut = newCut;
		boolean oldCutESet = cutESet;
		cutESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__CUT, oldCut, cut, !oldCutESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetCut() {
		double oldCut = cut;
		boolean oldCutESet = cutESet;
		cut = CUT_EDEFAULT;
		cutESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__CUT, oldCut, CUT_EDEFAULT, oldCutESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetCut() {
		return cutESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DistributionFunction getDistributionFunction() {
		return distributionFunction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDistributionFunction(DistributionFunction newDistributionFunction) {
		DistributionFunction oldDistributionFunction = distributionFunction;
		distributionFunction = newDistributionFunction == null ? DISTRIBUTION_FUNCTION_EDEFAULT : newDistributionFunction;
		boolean oldDistributionFunctionESet = distributionFunctionESet;
		distributionFunctionESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__DISTRIBUTION_FUNCTION, oldDistributionFunction, distributionFunction, !oldDistributionFunctionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetDistributionFunction() {
		DistributionFunction oldDistributionFunction = distributionFunction;
		boolean oldDistributionFunctionESet = distributionFunctionESet;
		distributionFunction = DISTRIBUTION_FUNCTION_EDEFAULT;
		distributionFunctionESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__DISTRIBUTION_FUNCTION, oldDistributionFunction, DISTRIBUTION_FUNCTION_EDEFAULT, oldDistributionFunctionESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetDistributionFunction() {
		return distributionFunctionESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getFreedom() {
		return freedom;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFreedom(double newFreedom) {
		double oldFreedom = freedom;
		freedom = newFreedom;
		boolean oldFreedomESet = freedomESet;
		freedomESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__FREEDOM, oldFreedom, freedom, !oldFreedomESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetFreedom() {
		double oldFreedom = freedom;
		boolean oldFreedomESet = freedomESet;
		freedom = FREEDOM_EDEFAULT;
		freedomESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__FREEDOM, oldFreedom, FREEDOM_EDEFAULT, oldFreedomESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetFreedom() {
		return freedomESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getGamma() {
		return gamma;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGamma(double newGamma) {
		double oldGamma = gamma;
		gamma = newGamma;
		boolean oldGammaESet = gammaESet;
		gammaESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__GAMMA, oldGamma, gamma, !oldGammaESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetGamma() {
		double oldGamma = gamma;
		boolean oldGammaESet = gammaESet;
		gamma = GAMMA_EDEFAULT;
		gammaESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__GAMMA, oldGamma, GAMMA_EDEFAULT, oldGammaESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetGamma() {
		return gammaESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getInputFile() {
		return inputFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInputFile(String newInputFile) {
		String oldInputFile = inputFile;
		inputFile = newInputFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__INPUT_FILE, oldInputFile, inputFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getLambda() {
		return lambda;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLambda(double newLambda) {
		double oldLambda = lambda;
		lambda = newLambda;
		boolean oldLambdaESet = lambdaESet;
		lambdaESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__LAMBDA, oldLambda, lambda, !oldLambdaESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetLambda() {
		double oldLambda = lambda;
		boolean oldLambdaESet = lambdaESet;
		lambda = LAMBDA_EDEFAULT;
		lambdaESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__LAMBDA, oldLambda, LAMBDA_EDEFAULT, oldLambdaESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetLambda() {
		return lambdaESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getMax() {
		return max;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMax(double newMax) {
		double oldMax = max;
		max = newMax;
		boolean oldMaxESet = maxESet;
		maxESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__MAX, oldMax, max, !oldMaxESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMax() {
		double oldMax = max;
		boolean oldMaxESet = maxESet;
		max = MAX_EDEFAULT;
		maxESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__MAX, oldMax, MAX_EDEFAULT, oldMaxESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMax() {
		return maxESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getMean() {
		return mean;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMean(double newMean) {
		double oldMean = mean;
		mean = newMean;
		boolean oldMeanESet = meanESet;
		meanESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__MEAN, oldMean, mean, !oldMeanESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMean() {
		double oldMean = mean;
		boolean oldMeanESet = meanESet;
		mean = MEAN_EDEFAULT;
		meanESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__MEAN, oldMean, MEAN_EDEFAULT, oldMeanESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMean() {
		return meanESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getMin() {
		return min;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMin(double newMin) {
		double oldMin = min;
		min = newMin;
		boolean oldMinESet = minESet;
		minESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__MIN, oldMin, min, !oldMinESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetMin() {
		double oldMin = min;
		boolean oldMinESet = minESet;
		min = MIN_EDEFAULT;
		minESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__MIN, oldMin, MIN_EDEFAULT, oldMinESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetMin() {
		return minESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getP() {
		return p;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setP(double newP) {
		double oldP = p;
		p = newP;
		boolean oldPESet = pESet;
		pESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__P, oldP, p, !oldPESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetP() {
		double oldP = p;
		boolean oldPESet = pESet;
		p = P_EDEFAULT;
		pESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__P, oldP, P_EDEFAULT, oldPESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetP() {
		return pESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getC() {
		return c;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setC(double newC) {
		double oldC = c;
		c = newC;
		boolean oldCESet = cESet;
		cESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__C, oldC, c, !oldCESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetC() {
		double oldC = c;
		boolean oldCESet = cESet;
		c = C_EDEFAULT;
		cESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__C, oldC, C_EDEFAULT, oldCESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetC() {
		return cESet;
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
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__PRIORITY, oldPriority, priority, !oldPriorityESet));
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
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__PRIORITY, oldPriority, PRIORITY_EDEFAULT, oldPriorityESet));
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
	public long getRanking() {
		return ranking;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRanking(long newRanking) {
		long oldRanking = ranking;
		ranking = newRanking;
		boolean oldRankingESet = rankingESet;
		rankingESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__RANKING, oldRanking, ranking, !oldRankingESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetRanking() {
		long oldRanking = ranking;
		boolean oldRankingESet = rankingESet;
		ranking = RANKING_EDEFAULT;
		rankingESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__RANKING, oldRanking, RANKING_EDEFAULT, oldRankingESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetRanking() {
		return rankingESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getStdDev() {
		return stdDev;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStdDev(double newStdDev) {
		double oldStdDev = stdDev;
		stdDev = newStdDev;
		boolean oldStdDevESet = stdDevESet;
		stdDevESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__STD_DEV, oldStdDev, stdDev, !oldStdDevESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetStdDev() {
		double oldStdDev = stdDev;
		boolean oldStdDevESet = stdDevESet;
		stdDev = STD_DEV_EDEFAULT;
		stdDevESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__STD_DEV, oldStdDev, STD_DEV_EDEFAULT, oldStdDevESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetStdDev() {
		return stdDevESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getTau() {
		return tau;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTau(double newTau) {
		double oldTau = tau;
		tau = newTau;
		boolean oldTauESet = tauESet;
		tauESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__TAU, oldTau, tau, !oldTauESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetTau() {
		double oldTau = tau;
		boolean oldTauESet = tauESet;
		tau = TAU_EDEFAULT;
		tauESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__TAU, oldTau, TAU_EDEFAULT, oldTauESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetTau() {
		return tauESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getOffset() {
		return offset;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOffset(double newOffset) {
		double oldOffset = offset;
		offset = newOffset;
		boolean oldOffsetESet = offsetESet;
		offsetESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__OFFSET, oldOffset, offset, !oldOffsetESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetOffset() {
		double oldOffset = offset;
		boolean oldOffsetESet = offsetESet;
		offset = OFFSET_EDEFAULT;
		offsetESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__OFFSET, oldOffset, OFFSET_EDEFAULT, oldOffsetESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetOffset() {
		return offsetESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getScale() {
		return scale;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScale(double newScale) {
		double oldScale = scale;
		scale = newScale;
		boolean oldScaleESet = scaleESet;
		scaleESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__SCALE, oldScale, scale, !oldScaleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void unsetScale() {
		double oldScale = scale;
		boolean oldScaleESet = scaleESet;
		scale = SCALE_EDEFAULT;
		scaleESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, ModelPackage.QUEUEING_COLOR_REFERENCE__SCALE, oldScale, SCALE_EDEFAULT, oldScaleESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSetScale() {
		return scaleESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getReplayFile() {
		return replayFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setReplayFile(String newReplayFile) {
		String oldReplayFile = replayFile;
		replayFile = newReplayFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__REPLAY_FILE, oldReplayFile, replayFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getValuesFile() {
		return valuesFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValuesFile(String newValuesFile) {
		String oldValuesFile = valuesFile;
		valuesFile = newValuesFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__VALUES_FILE, oldValuesFile, valuesFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getProbabilitiesFile() {
		return probabilitiesFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProbabilitiesFile(String newProbabilitiesFile) {
		String oldProbabilitiesFile = probabilitiesFile;
		probabilitiesFile = newProbabilitiesFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__PROBABILITIES_FILE, oldProbabilitiesFile, probabilitiesFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getConcurrenciesFile() {
		return concurrenciesFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConcurrenciesFile(String newConcurrenciesFile) {
		String oldConcurrenciesFile = concurrenciesFile;
		concurrenciesFile = newConcurrenciesFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__CONCURRENCIES_FILE, oldConcurrenciesFile, concurrenciesFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getResponsetimesFile() {
		return responsetimesFile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setResponsetimesFile(String newResponsetimesFile) {
		String oldResponsetimesFile = responsetimesFile;
		responsetimesFile = newResponsetimesFile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.QUEUEING_COLOR_REFERENCE__RESPONSETIMES_FILE, oldResponsetimesFile, responsetimesFile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.QUEUEING_COLOR_REFERENCE__ALPHA:
				return getAlpha();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__BETA:
				return getBeta();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__CUT:
				return getCut();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__DISTRIBUTION_FUNCTION:
				return getDistributionFunction();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__FREEDOM:
				return getFreedom();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__GAMMA:
				return getGamma();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__INPUT_FILE:
				return getInputFile();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__LAMBDA:
				return getLambda();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__MAX:
				return getMax();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__MEAN:
				return getMean();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__MIN:
				return getMin();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__P:
				return getP();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__C:
				return getC();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__PRIORITY:
				return getPriority();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__RANKING:
				return getRanking();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__STD_DEV:
				return getStdDev();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__TAU:
				return getTau();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__OFFSET:
				return getOffset();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__SCALE:
				return getScale();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__REPLAY_FILE:
				return getReplayFile();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__VALUES_FILE:
				return getValuesFile();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__PROBABILITIES_FILE:
				return getProbabilitiesFile();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__CONCURRENCIES_FILE:
				return getConcurrenciesFile();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__RESPONSETIMES_FILE:
				return getResponsetimesFile();
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
			case ModelPackage.QUEUEING_COLOR_REFERENCE__ALPHA:
				setAlpha((Double)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__BETA:
				setBeta((Double)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__CUT:
				setCut((Double)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__DISTRIBUTION_FUNCTION:
				setDistributionFunction((DistributionFunction)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__FREEDOM:
				setFreedom((Double)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__GAMMA:
				setGamma((Double)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__INPUT_FILE:
				setInputFile((String)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__LAMBDA:
				setLambda((Double)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__MAX:
				setMax((Double)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__MEAN:
				setMean((Double)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__MIN:
				setMin((Double)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__P:
				setP((Double)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__C:
				setC((Double)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__PRIORITY:
				setPriority((Long)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__RANKING:
				setRanking((Long)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__STD_DEV:
				setStdDev((Double)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__TAU:
				setTau((Double)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__OFFSET:
				setOffset((Double)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__SCALE:
				setScale((Double)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__REPLAY_FILE:
				setReplayFile((String)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__VALUES_FILE:
				setValuesFile((String)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__PROBABILITIES_FILE:
				setProbabilitiesFile((String)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__CONCURRENCIES_FILE:
				setConcurrenciesFile((String)newValue);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__RESPONSETIMES_FILE:
				setResponsetimesFile((String)newValue);
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
			case ModelPackage.QUEUEING_COLOR_REFERENCE__ALPHA:
				unsetAlpha();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__BETA:
				unsetBeta();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__CUT:
				unsetCut();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__DISTRIBUTION_FUNCTION:
				unsetDistributionFunction();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__FREEDOM:
				unsetFreedom();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__GAMMA:
				unsetGamma();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__INPUT_FILE:
				setInputFile(INPUT_FILE_EDEFAULT);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__LAMBDA:
				unsetLambda();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__MAX:
				unsetMax();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__MEAN:
				unsetMean();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__MIN:
				unsetMin();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__P:
				unsetP();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__C:
				unsetC();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__PRIORITY:
				unsetPriority();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__RANKING:
				unsetRanking();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__STD_DEV:
				unsetStdDev();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__TAU:
				unsetTau();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__OFFSET:
				unsetOffset();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__SCALE:
				unsetScale();
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__REPLAY_FILE:
				setReplayFile(REPLAY_FILE_EDEFAULT);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__VALUES_FILE:
				setValuesFile(VALUES_FILE_EDEFAULT);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__PROBABILITIES_FILE:
				setProbabilitiesFile(PROBABILITIES_FILE_EDEFAULT);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__CONCURRENCIES_FILE:
				setConcurrenciesFile(CONCURRENCIES_FILE_EDEFAULT);
				return;
			case ModelPackage.QUEUEING_COLOR_REFERENCE__RESPONSETIMES_FILE:
				setResponsetimesFile(RESPONSETIMES_FILE_EDEFAULT);
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
			case ModelPackage.QUEUEING_COLOR_REFERENCE__ALPHA:
				return isSetAlpha();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__BETA:
				return isSetBeta();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__CUT:
				return isSetCut();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__DISTRIBUTION_FUNCTION:
				return isSetDistributionFunction();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__FREEDOM:
				return isSetFreedom();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__GAMMA:
				return isSetGamma();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__INPUT_FILE:
				return INPUT_FILE_EDEFAULT == null ? inputFile != null : !INPUT_FILE_EDEFAULT.equals(inputFile);
			case ModelPackage.QUEUEING_COLOR_REFERENCE__LAMBDA:
				return isSetLambda();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__MAX:
				return isSetMax();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__MEAN:
				return isSetMean();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__MIN:
				return isSetMin();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__P:
				return isSetP();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__C:
				return isSetC();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__PRIORITY:
				return isSetPriority();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__RANKING:
				return isSetRanking();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__STD_DEV:
				return isSetStdDev();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__TAU:
				return isSetTau();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__OFFSET:
				return isSetOffset();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__SCALE:
				return isSetScale();
			case ModelPackage.QUEUEING_COLOR_REFERENCE__REPLAY_FILE:
				return REPLAY_FILE_EDEFAULT == null ? replayFile != null : !REPLAY_FILE_EDEFAULT.equals(replayFile);
			case ModelPackage.QUEUEING_COLOR_REFERENCE__VALUES_FILE:
				return VALUES_FILE_EDEFAULT == null ? valuesFile != null : !VALUES_FILE_EDEFAULT.equals(valuesFile);
			case ModelPackage.QUEUEING_COLOR_REFERENCE__PROBABILITIES_FILE:
				return PROBABILITIES_FILE_EDEFAULT == null ? probabilitiesFile != null : !PROBABILITIES_FILE_EDEFAULT.equals(probabilitiesFile);
			case ModelPackage.QUEUEING_COLOR_REFERENCE__CONCURRENCIES_FILE:
				return CONCURRENCIES_FILE_EDEFAULT == null ? concurrenciesFile != null : !CONCURRENCIES_FILE_EDEFAULT.equals(concurrenciesFile);
			case ModelPackage.QUEUEING_COLOR_REFERENCE__RESPONSETIMES_FILE:
				return RESPONSETIMES_FILE_EDEFAULT == null ? responsetimesFile != null : !RESPONSETIMES_FILE_EDEFAULT.equals(responsetimesFile);
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
		result.append(" (alpha: ");
		if (alphaESet) result.append(alpha); else result.append("<unset>");
		result.append(", beta: ");
		if (betaESet) result.append(beta); else result.append("<unset>");
		result.append(", cut: ");
		if (cutESet) result.append(cut); else result.append("<unset>");
		result.append(", distributionFunction: ");
		if (distributionFunctionESet) result.append(distributionFunction); else result.append("<unset>");
		result.append(", freedom: ");
		if (freedomESet) result.append(freedom); else result.append("<unset>");
		result.append(", gamma: ");
		if (gammaESet) result.append(gamma); else result.append("<unset>");
		result.append(", inputFile: ");
		result.append(inputFile);
		result.append(", lambda: ");
		if (lambdaESet) result.append(lambda); else result.append("<unset>");
		result.append(", max: ");
		if (maxESet) result.append(max); else result.append("<unset>");
		result.append(", mean: ");
		if (meanESet) result.append(mean); else result.append("<unset>");
		result.append(", min: ");
		if (minESet) result.append(min); else result.append("<unset>");
		result.append(", p: ");
		if (pESet) result.append(p); else result.append("<unset>");
		result.append(", c: ");
		if (cESet) result.append(c); else result.append("<unset>");
		result.append(", priority: ");
		if (priorityESet) result.append(priority); else result.append("<unset>");
		result.append(", ranking: ");
		if (rankingESet) result.append(ranking); else result.append("<unset>");
		result.append(", stdDev: ");
		if (stdDevESet) result.append(stdDev); else result.append("<unset>");
		result.append(", tau: ");
		if (tauESet) result.append(tau); else result.append("<unset>");
		result.append(", offset: ");
		if (offsetESet) result.append(offset); else result.append("<unset>");
		result.append(", scale: ");
		if (scaleESet) result.append(scale); else result.append("<unset>");
		result.append(", replayFile: ");
		result.append(replayFile);
		result.append(", valuesFile: ");
		result.append(valuesFile);
		result.append(", probabilitiesFile: ");
		result.append(probabilitiesFile);
		result.append(", concurrenciesFile: ");
		result.append(concurrenciesFile);
		result.append(", responsetimesFile: ");
		result.append(responsetimesFile);
		result.append(')');
		return result.toString();
	}

} //QueueingColorReferenceImpl
