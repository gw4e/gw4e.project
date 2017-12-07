/**
 * generated by Xtext 2.12.0
 */
package gw4e.eclipse.dsl.dSLPolicies.impl;

import gw4e.eclipse.dsl.dSLPolicies.AlgorithmType;
import gw4e.eclipse.dsl.dSLPolicies.DSLPoliciesPackage;
import gw4e.eclipse.dsl.dSLPolicies.PathGeneratorStopCondition;
import gw4e.eclipse.dsl.dSLPolicies.StopCondition;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Path Generator Stop Condition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link gw4e.eclipse.dsl.dSLPolicies.impl.PathGeneratorStopConditionImpl#getAlgorithmType <em>Algorithm Type</em>}</li>
 *   <li>{@link gw4e.eclipse.dsl.dSLPolicies.impl.PathGeneratorStopConditionImpl#getStopCondition <em>Stop Condition</em>}</li>
 *   <li>{@link gw4e.eclipse.dsl.dSLPolicies.impl.PathGeneratorStopConditionImpl#getStopConditionype <em>Stop Conditionype</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PathGeneratorStopConditionImpl extends MinimalEObjectImpl.Container implements PathGeneratorStopCondition
{
  /**
   * The cached value of the '{@link #getAlgorithmType() <em>Algorithm Type</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getAlgorithmType()
   * @generated
   * @ordered
   */
  protected AlgorithmType algorithmType;

  /**
   * The cached value of the '{@link #getStopCondition() <em>Stop Condition</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStopCondition()
   * @generated
   * @ordered
   */
  protected StopCondition stopCondition;

  /**
   * The cached value of the '{@link #getStopConditionype() <em>Stop Conditionype</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStopConditionype()
   * @generated
   * @ordered
   */
  protected StopCondition stopConditionype;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected PathGeneratorStopConditionImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass()
  {
    return DSLPoliciesPackage.Literals.PATH_GENERATOR_STOP_CONDITION;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public AlgorithmType getAlgorithmType()
  {
    return algorithmType;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetAlgorithmType(AlgorithmType newAlgorithmType, NotificationChain msgs)
  {
    AlgorithmType oldAlgorithmType = algorithmType;
    algorithmType = newAlgorithmType;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__ALGORITHM_TYPE, oldAlgorithmType, newAlgorithmType);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setAlgorithmType(AlgorithmType newAlgorithmType)
  {
    if (newAlgorithmType != algorithmType)
    {
      NotificationChain msgs = null;
      if (algorithmType != null)
        msgs = ((InternalEObject)algorithmType).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__ALGORITHM_TYPE, null, msgs);
      if (newAlgorithmType != null)
        msgs = ((InternalEObject)newAlgorithmType).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__ALGORITHM_TYPE, null, msgs);
      msgs = basicSetAlgorithmType(newAlgorithmType, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__ALGORITHM_TYPE, newAlgorithmType, newAlgorithmType));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StopCondition getStopCondition()
  {
    return stopCondition;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetStopCondition(StopCondition newStopCondition, NotificationChain msgs)
  {
    StopCondition oldStopCondition = stopCondition;
    stopCondition = newStopCondition;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITION, oldStopCondition, newStopCondition);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setStopCondition(StopCondition newStopCondition)
  {
    if (newStopCondition != stopCondition)
    {
      NotificationChain msgs = null;
      if (stopCondition != null)
        msgs = ((InternalEObject)stopCondition).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITION, null, msgs);
      if (newStopCondition != null)
        msgs = ((InternalEObject)newStopCondition).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITION, null, msgs);
      msgs = basicSetStopCondition(newStopCondition, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITION, newStopCondition, newStopCondition));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StopCondition getStopConditionype()
  {
    return stopConditionype;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public NotificationChain basicSetStopConditionype(StopCondition newStopConditionype, NotificationChain msgs)
  {
    StopCondition oldStopConditionype = stopConditionype;
    stopConditionype = newStopConditionype;
    if (eNotificationRequired())
    {
      ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITIONYPE, oldStopConditionype, newStopConditionype);
      if (msgs == null) msgs = notification; else msgs.add(notification);
    }
    return msgs;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setStopConditionype(StopCondition newStopConditionype)
  {
    if (newStopConditionype != stopConditionype)
    {
      NotificationChain msgs = null;
      if (stopConditionype != null)
        msgs = ((InternalEObject)stopConditionype).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITIONYPE, null, msgs);
      if (newStopConditionype != null)
        msgs = ((InternalEObject)newStopConditionype).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITIONYPE, null, msgs);
      msgs = basicSetStopConditionype(newStopConditionype, msgs);
      if (msgs != null) msgs.dispatch();
    }
    else if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITIONYPE, newStopConditionype, newStopConditionype));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs)
  {
    switch (featureID)
    {
      case DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__ALGORITHM_TYPE:
        return basicSetAlgorithmType(null, msgs);
      case DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITION:
        return basicSetStopCondition(null, msgs);
      case DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITIONYPE:
        return basicSetStopConditionype(null, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType)
  {
    switch (featureID)
    {
      case DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__ALGORITHM_TYPE:
        return getAlgorithmType();
      case DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITION:
        return getStopCondition();
      case DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITIONYPE:
        return getStopConditionype();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__ALGORITHM_TYPE:
        setAlgorithmType((AlgorithmType)newValue);
        return;
      case DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITION:
        setStopCondition((StopCondition)newValue);
        return;
      case DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITIONYPE:
        setStopConditionype((StopCondition)newValue);
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
  public void eUnset(int featureID)
  {
    switch (featureID)
    {
      case DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__ALGORITHM_TYPE:
        setAlgorithmType((AlgorithmType)null);
        return;
      case DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITION:
        setStopCondition((StopCondition)null);
        return;
      case DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITIONYPE:
        setStopConditionype((StopCondition)null);
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
  public boolean eIsSet(int featureID)
  {
    switch (featureID)
    {
      case DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__ALGORITHM_TYPE:
        return algorithmType != null;
      case DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITION:
        return stopCondition != null;
      case DSLPoliciesPackage.PATH_GENERATOR_STOP_CONDITION__STOP_CONDITIONYPE:
        return stopConditionype != null;
    }
    return super.eIsSet(featureID);
  }

} //PathGeneratorStopConditionImpl