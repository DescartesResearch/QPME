/**
 */
package edu.kit.ipd.descartes.qpme.model.impl;

import edu.kit.ipd.descartes.qpme.model.Color;
import edu.kit.ipd.descartes.qpme.model.ColorReference;
import edu.kit.ipd.descartes.qpme.model.ColorReferencesMetaAttributesContainer;
import edu.kit.ipd.descartes.qpme.model.ModelPackage;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Color Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.ColorReferenceImpl#getMetaAttributes <em>Meta Attributes</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.impl.ColorReferenceImpl#getColor <em>Color</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ColorReferenceImpl extends IncidenceFunctionElementImpl implements ColorReference {
	/**
	 * The cached value of the '{@link #getMetaAttributes() <em>Meta Attributes</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetaAttributes()
	 * @generated
	 * @ordered
	 */
	protected ColorReferencesMetaAttributesContainer metaAttributes;

	/**
	 * The cached value of the '{@link #getColor() <em>Color</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getColor()
	 * @generated
	 * @ordered
	 */
	protected Color color;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ColorReferenceImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.COLOR_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ColorReferencesMetaAttributesContainer getMetaAttributes() {
		return metaAttributes;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMetaAttributes(ColorReferencesMetaAttributesContainer newMetaAttributes, NotificationChain msgs) {
		ColorReferencesMetaAttributesContainer oldMetaAttributes = metaAttributes;
		metaAttributes = newMetaAttributes;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ModelPackage.COLOR_REFERENCE__META_ATTRIBUTES, oldMetaAttributes, newMetaAttributes);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetaAttributes(ColorReferencesMetaAttributesContainer newMetaAttributes) {
		if (newMetaAttributes != metaAttributes) {
			NotificationChain msgs = null;
			if (metaAttributes != null)
				msgs = ((InternalEObject)metaAttributes).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ModelPackage.COLOR_REFERENCE__META_ATTRIBUTES, null, msgs);
			if (newMetaAttributes != null)
				msgs = ((InternalEObject)newMetaAttributes).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ModelPackage.COLOR_REFERENCE__META_ATTRIBUTES, null, msgs);
			msgs = basicSetMetaAttributes(newMetaAttributes, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COLOR_REFERENCE__META_ATTRIBUTES, newMetaAttributes, newMetaAttributes));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setColor(Color newColor) {
		Color oldColor = color;
		color = newColor;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.COLOR_REFERENCE__COLOR, oldColor, color));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.COLOR_REFERENCE__META_ATTRIBUTES:
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
			case ModelPackage.COLOR_REFERENCE__META_ATTRIBUTES:
				return getMetaAttributes();
			case ModelPackage.COLOR_REFERENCE__COLOR:
				return getColor();
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
			case ModelPackage.COLOR_REFERENCE__META_ATTRIBUTES:
				setMetaAttributes((ColorReferencesMetaAttributesContainer)newValue);
				return;
			case ModelPackage.COLOR_REFERENCE__COLOR:
				setColor((Color)newValue);
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
			case ModelPackage.COLOR_REFERENCE__META_ATTRIBUTES:
				setMetaAttributes((ColorReferencesMetaAttributesContainer)null);
				return;
			case ModelPackage.COLOR_REFERENCE__COLOR:
				setColor((Color)null);
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
			case ModelPackage.COLOR_REFERENCE__META_ATTRIBUTES:
				return metaAttributes != null;
			case ModelPackage.COLOR_REFERENCE__COLOR:
				return color != null;
		}
		return super.eIsSet(featureID);
	}

} //ColorReferenceImpl
