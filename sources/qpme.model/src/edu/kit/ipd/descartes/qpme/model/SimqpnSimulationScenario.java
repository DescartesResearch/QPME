/**
 */
package edu.kit.ipd.descartes.qpme.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Simqpn Simulation Scenario</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnSimulationScenario()
 * @model extendedMetaData="name='simqpn-simulation-scenario'"
 * @generated
 */
public enum SimqpnSimulationScenario implements Enumerator {
	/**
	 * The '<em><b>BATCH MEANS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BATCH_MEANS_VALUE
	 * @generated
	 * @ordered
	 */
	BATCH_MEANS(0, "BATCH_MEANS", "1"),

	/**
	 * The '<em><b>REPLICATION DELETION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #REPLICATION_DELETION_VALUE
	 * @generated
	 * @ordered
	 */
	REPLICATION_DELETION(1, "REPLICATION_DELETION", "2"),

	/**
	 * The '<em><b>METHOD OF WELCH</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #METHOD_OF_WELCH_VALUE
	 * @generated
	 * @ordered
	 */
	METHOD_OF_WELCH(2, "METHOD_OF_WELCH", "3");

	/**
	 * The '<em><b>BATCH MEANS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BATCH MEANS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BATCH_MEANS
	 * @model literal="1"
	 * @generated
	 * @ordered
	 */
	public static final int BATCH_MEANS_VALUE = 0;

	/**
	 * The '<em><b>REPLICATION DELETION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>REPLICATION DELETION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #REPLICATION_DELETION
	 * @model literal="2"
	 * @generated
	 * @ordered
	 */
	public static final int REPLICATION_DELETION_VALUE = 1;

	/**
	 * The '<em><b>METHOD OF WELCH</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>METHOD OF WELCH</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #METHOD_OF_WELCH
	 * @model literal="3"
	 * @generated
	 * @ordered
	 */
	public static final int METHOD_OF_WELCH_VALUE = 2;

	/**
	 * An array of all the '<em><b>Simqpn Simulation Scenario</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final SimqpnSimulationScenario[] VALUES_ARRAY =
		new SimqpnSimulationScenario[] {
			BATCH_MEANS,
			REPLICATION_DELETION,
			METHOD_OF_WELCH,
		};

	/**
	 * A public read-only list of all the '<em><b>Simqpn Simulation Scenario</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<SimqpnSimulationScenario> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Simqpn Simulation Scenario</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SimqpnSimulationScenario get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SimqpnSimulationScenario result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Simqpn Simulation Scenario</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SimqpnSimulationScenario getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SimqpnSimulationScenario result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Simqpn Simulation Scenario</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SimqpnSimulationScenario get(int value) {
		switch (value) {
			case BATCH_MEANS_VALUE: return BATCH_MEANS;
			case REPLICATION_DELETION_VALUE: return REPLICATION_DELETION;
			case METHOD_OF_WELCH_VALUE: return METHOD_OF_WELCH;
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
	private SimqpnSimulationScenario(int value, String name, String literal) {
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
	
} //SimqpnSimulationScenario
