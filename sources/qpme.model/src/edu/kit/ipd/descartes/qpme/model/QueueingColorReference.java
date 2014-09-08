/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package edu.kit.ipd.descartes.qpme.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Queueing Color Reference</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getAlpha <em>Alpha</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getBeta <em>Beta</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getCut <em>Cut</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getDistributionFunction <em>Distribution Function</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getFreedom <em>Freedom</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getGamma <em>Gamma</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getInputFile <em>Input File</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getLambda <em>Lambda</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMax <em>Max</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMean <em>Mean</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMin <em>Min</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getP <em>P</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getC <em>C</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getPriority <em>Priority</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getRanking <em>Ranking</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getStdDev <em>Std Dev</em>}</li>
 *   <li>{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getTau <em>Tau</em>}</li>
 * </ul>
 * </p>
 *
 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference()
 * @model extendedMetaData="name='queueing-color-reference' kind='elementOnly'"
 * @generated
 */
public interface QueueingColorReference extends PlaceColorReference {
	/**
	 * Returns the value of the '<em><b>Alpha</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alpha</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alpha</em>' attribute.
	 * @see #isSetAlpha()
	 * @see #unsetAlpha()
	 * @see #setAlpha(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_Alpha()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='alpha' namespace='##targetNamespace'"
	 * @generated
	 */
	double getAlpha();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getAlpha <em>Alpha</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Alpha</em>' attribute.
	 * @see #isSetAlpha()
	 * @see #unsetAlpha()
	 * @see #getAlpha()
	 * @generated
	 */
	void setAlpha(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getAlpha <em>Alpha</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetAlpha()
	 * @see #getAlpha()
	 * @see #setAlpha(double)
	 * @generated
	 */
	void unsetAlpha();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getAlpha <em>Alpha</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Alpha</em>' attribute is set.
	 * @see #unsetAlpha()
	 * @see #getAlpha()
	 * @see #setAlpha(double)
	 * @generated
	 */
	boolean isSetAlpha();

	/**
	 * Returns the value of the '<em><b>Beta</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Beta</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Beta</em>' attribute.
	 * @see #isSetBeta()
	 * @see #unsetBeta()
	 * @see #setBeta(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_Beta()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='beta' namespace='##targetNamespace'"
	 * @generated
	 */
	double getBeta();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getBeta <em>Beta</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Beta</em>' attribute.
	 * @see #isSetBeta()
	 * @see #unsetBeta()
	 * @see #getBeta()
	 * @generated
	 */
	void setBeta(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getBeta <em>Beta</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetBeta()
	 * @see #getBeta()
	 * @see #setBeta(double)
	 * @generated
	 */
	void unsetBeta();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getBeta <em>Beta</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Beta</em>' attribute is set.
	 * @see #unsetBeta()
	 * @see #getBeta()
	 * @see #setBeta(double)
	 * @generated
	 */
	boolean isSetBeta();

	/**
	 * Returns the value of the '<em><b>Cut</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cut</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cut</em>' attribute.
	 * @see #isSetCut()
	 * @see #unsetCut()
	 * @see #setCut(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_Cut()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='cut' namespace='##targetNamespace'"
	 * @generated
	 */
	double getCut();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getCut <em>Cut</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cut</em>' attribute.
	 * @see #isSetCut()
	 * @see #unsetCut()
	 * @see #getCut()
	 * @generated
	 */
	void setCut(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getCut <em>Cut</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetCut()
	 * @see #getCut()
	 * @see #setCut(double)
	 * @generated
	 */
	void unsetCut();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getCut <em>Cut</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Cut</em>' attribute is set.
	 * @see #unsetCut()
	 * @see #getCut()
	 * @see #setCut(double)
	 * @generated
	 */
	boolean isSetCut();

	/**
	 * Returns the value of the '<em><b>Distribution Function</b></em>' attribute.
	 * The literals are from the enumeration {@link edu.kit.ipd.descartes.qpme.model.DistributionFunction}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Distribution Function</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Distribution Function</em>' attribute.
	 * @see edu.kit.ipd.descartes.qpme.model.DistributionFunction
	 * @see #isSetDistributionFunction()
	 * @see #unsetDistributionFunction()
	 * @see #setDistributionFunction(DistributionFunction)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_DistributionFunction()
	 * @model unsettable="true" required="true"
	 *        extendedMetaData="kind='attribute' name='distribution-function' namespace='##targetNamespace'"
	 * @generated
	 */
	DistributionFunction getDistributionFunction();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getDistributionFunction <em>Distribution Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Distribution Function</em>' attribute.
	 * @see edu.kit.ipd.descartes.qpme.model.DistributionFunction
	 * @see #isSetDistributionFunction()
	 * @see #unsetDistributionFunction()
	 * @see #getDistributionFunction()
	 * @generated
	 */
	void setDistributionFunction(DistributionFunction value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getDistributionFunction <em>Distribution Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDistributionFunction()
	 * @see #getDistributionFunction()
	 * @see #setDistributionFunction(DistributionFunction)
	 * @generated
	 */
	void unsetDistributionFunction();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getDistributionFunction <em>Distribution Function</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Distribution Function</em>' attribute is set.
	 * @see #unsetDistributionFunction()
	 * @see #getDistributionFunction()
	 * @see #setDistributionFunction(DistributionFunction)
	 * @generated
	 */
	boolean isSetDistributionFunction();

	/**
	 * Returns the value of the '<em><b>Freedom</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Freedom</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Freedom</em>' attribute.
	 * @see #isSetFreedom()
	 * @see #unsetFreedom()
	 * @see #setFreedom(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_Freedom()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='freedom' namespace='##targetNamespace'"
	 * @generated
	 */
	double getFreedom();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getFreedom <em>Freedom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Freedom</em>' attribute.
	 * @see #isSetFreedom()
	 * @see #unsetFreedom()
	 * @see #getFreedom()
	 * @generated
	 */
	void setFreedom(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getFreedom <em>Freedom</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetFreedom()
	 * @see #getFreedom()
	 * @see #setFreedom(double)
	 * @generated
	 */
	void unsetFreedom();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getFreedom <em>Freedom</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Freedom</em>' attribute is set.
	 * @see #unsetFreedom()
	 * @see #getFreedom()
	 * @see #setFreedom(double)
	 * @generated
	 */
	boolean isSetFreedom();

	/**
	 * Returns the value of the '<em><b>Gamma</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gamma</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gamma</em>' attribute.
	 * @see #isSetGamma()
	 * @see #unsetGamma()
	 * @see #setGamma(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_Gamma()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='gamma' namespace='##targetNamespace'"
	 * @generated
	 */
	double getGamma();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getGamma <em>Gamma</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gamma</em>' attribute.
	 * @see #isSetGamma()
	 * @see #unsetGamma()
	 * @see #getGamma()
	 * @generated
	 */
	void setGamma(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getGamma <em>Gamma</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetGamma()
	 * @see #getGamma()
	 * @see #setGamma(double)
	 * @generated
	 */
	void unsetGamma();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getGamma <em>Gamma</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Gamma</em>' attribute is set.
	 * @see #unsetGamma()
	 * @see #getGamma()
	 * @see #setGamma(double)
	 * @generated
	 */
	boolean isSetGamma();

	/**
	 * Returns the value of the '<em><b>Input File</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Input File</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Input File</em>' attribute.
	 * @see #setInputFile(String)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_InputFile()
	 * @model dataType="org.eclipse.emf.ecore.xml.type.String"
	 *        extendedMetaData="kind='attribute' name='input-file' namespace='##targetNamespace'"
	 * @generated
	 */
	String getInputFile();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getInputFile <em>Input File</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Input File</em>' attribute.
	 * @see #getInputFile()
	 * @generated
	 */
	void setInputFile(String value);

	/**
	 * Returns the value of the '<em><b>Lambda</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lambda</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lambda</em>' attribute.
	 * @see #isSetLambda()
	 * @see #unsetLambda()
	 * @see #setLambda(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_Lambda()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='lambda' namespace='##targetNamespace'"
	 * @generated
	 */
	double getLambda();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getLambda <em>Lambda</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lambda</em>' attribute.
	 * @see #isSetLambda()
	 * @see #unsetLambda()
	 * @see #getLambda()
	 * @generated
	 */
	void setLambda(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getLambda <em>Lambda</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetLambda()
	 * @see #getLambda()
	 * @see #setLambda(double)
	 * @generated
	 */
	void unsetLambda();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getLambda <em>Lambda</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Lambda</em>' attribute is set.
	 * @see #unsetLambda()
	 * @see #getLambda()
	 * @see #setLambda(double)
	 * @generated
	 */
	boolean isSetLambda();

	/**
	 * Returns the value of the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max</em>' attribute.
	 * @see #isSetMax()
	 * @see #unsetMax()
	 * @see #setMax(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_Max()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='max' namespace='##targetNamespace'"
	 * @generated
	 */
	double getMax();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMax <em>Max</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max</em>' attribute.
	 * @see #isSetMax()
	 * @see #unsetMax()
	 * @see #getMax()
	 * @generated
	 */
	void setMax(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMax <em>Max</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMax()
	 * @see #getMax()
	 * @see #setMax(double)
	 * @generated
	 */
	void unsetMax();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMax <em>Max</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Max</em>' attribute is set.
	 * @see #unsetMax()
	 * @see #getMax()
	 * @see #setMax(double)
	 * @generated
	 */
	boolean isSetMax();

	/**
	 * Returns the value of the '<em><b>Mean</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mean</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mean</em>' attribute.
	 * @see #isSetMean()
	 * @see #unsetMean()
	 * @see #setMean(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_Mean()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='mean' namespace='##targetNamespace'"
	 * @generated
	 */
	double getMean();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMean <em>Mean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mean</em>' attribute.
	 * @see #isSetMean()
	 * @see #unsetMean()
	 * @see #getMean()
	 * @generated
	 */
	void setMean(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMean <em>Mean</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMean()
	 * @see #getMean()
	 * @see #setMean(double)
	 * @generated
	 */
	void unsetMean();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMean <em>Mean</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Mean</em>' attribute is set.
	 * @see #unsetMean()
	 * @see #getMean()
	 * @see #setMean(double)
	 * @generated
	 */
	boolean isSetMean();

	/**
	 * Returns the value of the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min</em>' attribute.
	 * @see #isSetMin()
	 * @see #unsetMin()
	 * @see #setMin(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_Min()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='min' namespace='##targetNamespace'"
	 * @generated
	 */
	double getMin();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMin <em>Min</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min</em>' attribute.
	 * @see #isSetMin()
	 * @see #unsetMin()
	 * @see #getMin()
	 * @generated
	 */
	void setMin(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMin <em>Min</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMin()
	 * @see #getMin()
	 * @see #setMin(double)
	 * @generated
	 */
	void unsetMin();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getMin <em>Min</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Min</em>' attribute is set.
	 * @see #unsetMin()
	 * @see #getMin()
	 * @see #setMin(double)
	 * @generated
	 */
	boolean isSetMin();

	/**
	 * Returns the value of the '<em><b>P</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>P</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>P</em>' attribute.
	 * @see #isSetP()
	 * @see #unsetP()
	 * @see #setP(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_P()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='p' namespace='##targetNamespace'"
	 * @generated
	 */
	double getP();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getP <em>P</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>P</em>' attribute.
	 * @see #isSetP()
	 * @see #unsetP()
	 * @see #getP()
	 * @generated
	 */
	void setP(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getP <em>P</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetP()
	 * @see #getP()
	 * @see #setP(double)
	 * @generated
	 */
	void unsetP();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getP <em>P</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>P</em>' attribute is set.
	 * @see #unsetP()
	 * @see #getP()
	 * @see #setP(double)
	 * @generated
	 */
	boolean isSetP();

	/**
	 * Returns the value of the '<em><b>C</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>C</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>C</em>' attribute.
	 * @see #isSetC()
	 * @see #unsetC()
	 * @see #setC(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_C()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='c' namespace='##targetNamespace'"
	 * @generated
	 */
	double getC();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getC <em>C</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>C</em>' attribute.
	 * @see #isSetC()
	 * @see #unsetC()
	 * @see #getC()
	 * @generated
	 */
	void setC(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getC <em>C</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetC()
	 * @see #getC()
	 * @see #setC(double)
	 * @generated
	 */
	void unsetC();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getC <em>C</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>C</em>' attribute is set.
	 * @see #unsetC()
	 * @see #getC()
	 * @see #setC(double)
	 * @generated
	 */
	boolean isSetC();

	/**
	 * Returns the value of the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Priority</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Priority</em>' attribute.
	 * @see #isSetPriority()
	 * @see #unsetPriority()
	 * @see #setPriority(long)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_Priority()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt" required="true"
	 *        extendedMetaData="kind='attribute' name='priority' namespace='##targetNamespace'"
	 * @generated
	 */
	long getPriority();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getPriority <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Priority</em>' attribute.
	 * @see #isSetPriority()
	 * @see #unsetPriority()
	 * @see #getPriority()
	 * @generated
	 */
	void setPriority(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getPriority <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetPriority()
	 * @see #getPriority()
	 * @see #setPriority(long)
	 * @generated
	 */
	void unsetPriority();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getPriority <em>Priority</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Priority</em>' attribute is set.
	 * @see #unsetPriority()
	 * @see #getPriority()
	 * @see #setPriority(long)
	 * @generated
	 */
	boolean isSetPriority();

	/**
	 * Returns the value of the '<em><b>Ranking</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ranking</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ranking</em>' attribute.
	 * @see #isSetRanking()
	 * @see #unsetRanking()
	 * @see #setRanking(long)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_Ranking()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.UnsignedInt" required="true"
	 *        extendedMetaData="kind='attribute' name='ranking' namespace='##targetNamespace'"
	 * @generated
	 */
	long getRanking();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getRanking <em>Ranking</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ranking</em>' attribute.
	 * @see #isSetRanking()
	 * @see #unsetRanking()
	 * @see #getRanking()
	 * @generated
	 */
	void setRanking(long value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getRanking <em>Ranking</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetRanking()
	 * @see #getRanking()
	 * @see #setRanking(long)
	 * @generated
	 */
	void unsetRanking();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getRanking <em>Ranking</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Ranking</em>' attribute is set.
	 * @see #unsetRanking()
	 * @see #getRanking()
	 * @see #setRanking(long)
	 * @generated
	 */
	boolean isSetRanking();

	/**
	 * Returns the value of the '<em><b>Std Dev</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Std Dev</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Std Dev</em>' attribute.
	 * @see #isSetStdDev()
	 * @see #unsetStdDev()
	 * @see #setStdDev(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_StdDev()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='stdDev' namespace='##targetNamespace'"
	 * @generated
	 */
	double getStdDev();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getStdDev <em>Std Dev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Std Dev</em>' attribute.
	 * @see #isSetStdDev()
	 * @see #unsetStdDev()
	 * @see #getStdDev()
	 * @generated
	 */
	void setStdDev(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getStdDev <em>Std Dev</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetStdDev()
	 * @see #getStdDev()
	 * @see #setStdDev(double)
	 * @generated
	 */
	void unsetStdDev();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getStdDev <em>Std Dev</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Std Dev</em>' attribute is set.
	 * @see #unsetStdDev()
	 * @see #getStdDev()
	 * @see #setStdDev(double)
	 * @generated
	 */
	boolean isSetStdDev();

	/**
	 * Returns the value of the '<em><b>Tau</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tau</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tau</em>' attribute.
	 * @see #isSetTau()
	 * @see #unsetTau()
	 * @see #setTau(double)
	 * @see edu.kit.ipd.descartes.qpme.model.ModelPackage#getQueueingColorReference_Tau()
	 * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Double"
	 *        extendedMetaData="kind='attribute' name='tau' namespace='##targetNamespace'"
	 * @generated
	 */
	double getTau();

	/**
	 * Sets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getTau <em>Tau</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tau</em>' attribute.
	 * @see #isSetTau()
	 * @see #unsetTau()
	 * @see #getTau()
	 * @generated
	 */
	void setTau(double value);

	/**
	 * Unsets the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getTau <em>Tau</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetTau()
	 * @see #getTau()
	 * @see #setTau(double)
	 * @generated
	 */
	void unsetTau();

	/**
	 * Returns whether the value of the '{@link edu.kit.ipd.descartes.qpme.model.QueueingColorReference#getTau <em>Tau</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Tau</em>' attribute is set.
	 * @see #unsetTau()
	 * @see #getTau()
	 * @see #setTau(double)
	 * @generated
	 */
	boolean isSetTau();

} // QueueingColorReference
