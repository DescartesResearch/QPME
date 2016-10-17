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
 * A representation of the literals of the enumeration '<em><b>Simqpn Stopping Rule</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getSimqpnStoppingRule()
 * @model extendedMetaData="name='simqpn-stopping-rule'"
 * @generated
 */
public enum SimqpnStoppingRule implements Enumerator {
	/**
	 * The '<em><b>ABSOLUTE PRECISION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #ABSOLUTE_PRECISION_VALUE
	 * @generated
	 * @ordered
	 */
	ABSOLUTE_PRECISION(0, "ABSOLUTE_PRECISION", "ABSPRC"),

	/**
	 * The '<em><b>RELATIVE PRECISION</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RELATIVE_PRECISION_VALUE
	 * @generated
	 * @ordered
	 */
	RELATIVE_PRECISION(1, "RELATIVE_PRECISION", "RELPRC"),

	/**
	 * The '<em><b>FIXED LENGTH</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FIXED_LENGTH_VALUE
	 * @generated
	 * @ordered
	 */
	FIXED_LENGTH(2, "FIXED_LENGTH", "FIXEDLEN");

	/**
	 * The '<em><b>ABSOLUTE PRECISION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>ABSOLUTE PRECISION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #ABSOLUTE_PRECISION
	 * @model literal="ABSPRC"
	 * @generated
	 * @ordered
	 */
	public static final int ABSOLUTE_PRECISION_VALUE = 0;

	/**
	 * The '<em><b>RELATIVE PRECISION</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>RELATIVE PRECISION</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RELATIVE_PRECISION
	 * @model literal="RELPRC"
	 * @generated
	 * @ordered
	 */
	public static final int RELATIVE_PRECISION_VALUE = 1;

	/**
	 * The '<em><b>FIXED LENGTH</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FIXED LENGTH</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FIXED_LENGTH
	 * @model literal="FIXEDLEN"
	 * @generated
	 * @ordered
	 */
	public static final int FIXED_LENGTH_VALUE = 2;

	/**
	 * An array of all the '<em><b>Simqpn Stopping Rule</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final SimqpnStoppingRule[] VALUES_ARRAY =
		new SimqpnStoppingRule[] {
			ABSOLUTE_PRECISION,
			RELATIVE_PRECISION,
			FIXED_LENGTH,
		};

	/**
	 * A public read-only list of all the '<em><b>Simqpn Stopping Rule</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<SimqpnStoppingRule> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Simqpn Stopping Rule</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SimqpnStoppingRule get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SimqpnStoppingRule result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Simqpn Stopping Rule</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SimqpnStoppingRule getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			SimqpnStoppingRule result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Simqpn Stopping Rule</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static SimqpnStoppingRule get(int value) {
		switch (value) {
			case ABSOLUTE_PRECISION_VALUE: return ABSOLUTE_PRECISION;
			case RELATIVE_PRECISION_VALUE: return RELATIVE_PRECISION;
			case FIXED_LENGTH_VALUE: return FIXED_LENGTH;
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
	private SimqpnStoppingRule(int value, String name, String literal) {
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
	
} //SimqpnStoppingRule
