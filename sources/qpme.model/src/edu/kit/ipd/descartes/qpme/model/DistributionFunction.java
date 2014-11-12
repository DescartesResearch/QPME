/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Distribution Function</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getDistributionFunction()
 * @model extendedMetaData="name='distribution-function'"
 * @generated
 */
public enum DistributionFunction implements Enumerator {
	/**
	 * The '<em><b>BETA</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BETA_VALUE
	 * @generated
	 * @ordered
	 */
	BETA(0, "BETA", "Beta"),

	/**
	 * The '<em><b>BREIT WIGNER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BREIT_WIGNER_VALUE
	 * @generated
	 * @ordered
	 */
	BREIT_WIGNER(1, "BREIT_WIGNER", "BreitWigner"),

	/**
	 * The '<em><b>BREIT WIGNER SQUARE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BREIT_WIGNER_SQUARE_VALUE
	 * @generated
	 * @ordered
	 */
	BREIT_WIGNER_SQUARE(2, "BREIT_WIGNER_SQUARE", "BreitWignerMeanSquare"),

	/**
	 * The '<em><b>CHI SQUARE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CHI_SQUARE_VALUE
	 * @generated
	 * @ordered
	 */
	CHI_SQUARE(3, "CHI_SQUARE", "ChiSquare"),

	/**
	 * The '<em><b>GAMMA</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GAMMA_VALUE
	 * @generated
	 * @ordered
	 */
	GAMMA(4, "GAMMA", "Gamma"),

	/**
	 * The '<em><b>HYPERBOLIC</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #HYPERBOLIC_VALUE
	 * @generated
	 * @ordered
	 */
	HYPERBOLIC(5, "HYPERBOLIC", "Hyperbolic"),

	/**
	 * The '<em><b>EXPONENTIAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXPONENTIAL_VALUE
	 * @generated
	 * @ordered
	 */
	EXPONENTIAL(6, "EXPONENTIAL", "Exponential"),

	/**
	 * The '<em><b>EXPONENTIAL POWER</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EXPONENTIAL_POWER_VALUE
	 * @generated
	 * @ordered
	 */
	EXPONENTIAL_POWER(7, "EXPONENTIAL_POWER", "ExponentialPower"),

	/**
	 * The '<em><b>LOGARITHMIC</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LOGARITHMIC_VALUE
	 * @generated
	 * @ordered
	 */
	LOGARITHMIC(8, "LOGARITHMIC", "Logarithmic"),

	/**
	 * The '<em><b>NORMAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #NORMAL_VALUE
	 * @generated
	 * @ordered
	 */
	NORMAL(9, "NORMAL", "Normal"),

	/**
	 * The '<em><b>STUDENT T</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STUDENT_T_VALUE
	 * @generated
	 * @ordered
	 */
	STUDENT_T(10, "STUDENT_T", "StudentT"),

	/**
	 * The '<em><b>UNIFORM</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #UNIFORM_VALUE
	 * @generated
	 * @ordered
	 */
	UNIFORM(11, "UNIFORM", "Uniform"),

	/**
	 * The '<em><b>VON MISES</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VON_MISES_VALUE
	 * @generated
	 * @ordered
	 */
	VON_MISES(12, "VON_MISES", "VonMises"),

	/**
	 * The '<em><b>EMPIRICAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #EMPIRICAL_VALUE
	 * @generated
	 * @ordered
	 */
	EMPIRICAL(13, "EMPIRICAL", "Empirical"),

	/**
	 * The '<em><b>DETERMINISTIC</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DETERMINISTIC_VALUE
	 * @generated
	 * @ordered
	 */
	DETERMINISTIC(14, "DETERMINISTIC", "Deterministic");

	/**
	 * The '<em><b>BETA</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BETA</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BETA
	 * @model literal="Beta"
	 * @generated
	 * @ordered
	 */
	public static final int BETA_VALUE = 0;

	/**
	 * The '<em><b>BREIT WIGNER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BREIT WIGNER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BREIT_WIGNER
	 * @model literal="BreitWigner"
	 * @generated
	 * @ordered
	 */
	public static final int BREIT_WIGNER_VALUE = 1;

	/**
	 * The '<em><b>BREIT WIGNER SQUARE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BREIT WIGNER SQUARE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BREIT_WIGNER_SQUARE
	 * @model literal="BreitWignerMeanSquare"
	 * @generated
	 * @ordered
	 */
	public static final int BREIT_WIGNER_SQUARE_VALUE = 2;

	/**
	 * The '<em><b>CHI SQUARE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CHI SQUARE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CHI_SQUARE
	 * @model literal="ChiSquare"
	 * @generated
	 * @ordered
	 */
	public static final int CHI_SQUARE_VALUE = 3;

	/**
	 * The '<em><b>GAMMA</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>GAMMA</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GAMMA
	 * @model literal="Gamma"
	 * @generated
	 * @ordered
	 */
	public static final int GAMMA_VALUE = 4;

	/**
	 * The '<em><b>HYPERBOLIC</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>HYPERBOLIC</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #HYPERBOLIC
	 * @model literal="Hyperbolic"
	 * @generated
	 * @ordered
	 */
	public static final int HYPERBOLIC_VALUE = 5;

	/**
	 * The '<em><b>EXPONENTIAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>EXPONENTIAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EXPONENTIAL
	 * @model literal="Exponential"
	 * @generated
	 * @ordered
	 */
	public static final int EXPONENTIAL_VALUE = 6;

	/**
	 * The '<em><b>EXPONENTIAL POWER</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>EXPONENTIAL POWER</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EXPONENTIAL_POWER
	 * @model literal="ExponentialPower"
	 * @generated
	 * @ordered
	 */
	public static final int EXPONENTIAL_POWER_VALUE = 7;

	/**
	 * The '<em><b>LOGARITHMIC</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>LOGARITHMIC</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LOGARITHMIC
	 * @model literal="Logarithmic"
	 * @generated
	 * @ordered
	 */
	public static final int LOGARITHMIC_VALUE = 8;

	/**
	 * The '<em><b>NORMAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>NORMAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #NORMAL
	 * @model literal="Normal"
	 * @generated
	 * @ordered
	 */
	public static final int NORMAL_VALUE = 9;

	/**
	 * The '<em><b>STUDENT T</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>STUDENT T</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STUDENT_T
	 * @model literal="StudentT"
	 * @generated
	 * @ordered
	 */
	public static final int STUDENT_T_VALUE = 10;

	/**
	 * The '<em><b>UNIFORM</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>UNIFORM</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #UNIFORM
	 * @model literal="Uniform"
	 * @generated
	 * @ordered
	 */
	public static final int UNIFORM_VALUE = 11;

	/**
	 * The '<em><b>VON MISES</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>VON MISES</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #VON_MISES
	 * @model literal="VonMises"
	 * @generated
	 * @ordered
	 */
	public static final int VON_MISES_VALUE = 12;

	/**
	 * The '<em><b>EMPIRICAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>EMPIRICAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #EMPIRICAL
	 * @model literal="Empirical"
	 * @generated
	 * @ordered
	 */
	public static final int EMPIRICAL_VALUE = 13;

	/**
	 * The '<em><b>DETERMINISTIC</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>DETERMINISTIC</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DETERMINISTIC
	 * @model literal="Deterministic"
	 * @generated
	 * @ordered
	 */
	public static final int DETERMINISTIC_VALUE = 14;

	/**
	 * An array of all the '<em><b>Distribution Function</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final DistributionFunction[] VALUES_ARRAY =
		new DistributionFunction[] {
			BETA,
			BREIT_WIGNER,
			BREIT_WIGNER_SQUARE,
			CHI_SQUARE,
			GAMMA,
			HYPERBOLIC,
			EXPONENTIAL,
			EXPONENTIAL_POWER,
			LOGARITHMIC,
			NORMAL,
			STUDENT_T,
			UNIFORM,
			VON_MISES,
			EMPIRICAL,
			DETERMINISTIC,
		};

	/**
	 * A public read-only list of all the '<em><b>Distribution Function</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<DistributionFunction> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Distribution Function</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DistributionFunction get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DistributionFunction result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Distribution Function</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DistributionFunction getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			DistributionFunction result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Distribution Function</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static DistributionFunction get(int value) {
		switch (value) {
			case BETA_VALUE: return BETA;
			case BREIT_WIGNER_VALUE: return BREIT_WIGNER;
			case BREIT_WIGNER_SQUARE_VALUE: return BREIT_WIGNER_SQUARE;
			case CHI_SQUARE_VALUE: return CHI_SQUARE;
			case GAMMA_VALUE: return GAMMA;
			case HYPERBOLIC_VALUE: return HYPERBOLIC;
			case EXPONENTIAL_VALUE: return EXPONENTIAL;
			case EXPONENTIAL_POWER_VALUE: return EXPONENTIAL_POWER;
			case LOGARITHMIC_VALUE: return LOGARITHMIC;
			case NORMAL_VALUE: return NORMAL;
			case STUDENT_T_VALUE: return STUDENT_T;
			case UNIFORM_VALUE: return UNIFORM;
			case VON_MISES_VALUE: return VON_MISES;
			case EMPIRICAL_VALUE: return EMPIRICAL;
			case DETERMINISTIC_VALUE: return DETERMINISTIC;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private DistributionFunction(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
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
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //DistributionFunction