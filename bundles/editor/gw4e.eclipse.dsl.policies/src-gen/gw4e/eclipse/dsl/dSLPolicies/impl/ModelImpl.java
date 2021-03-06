/**
 * generated by Xtext 2.12.0
 */
package gw4e.eclipse.dsl.dSLPolicies.impl;

import gw4e.eclipse.dsl.dSLPolicies.DSLPoliciesPackage;
import gw4e.eclipse.dsl.dSLPolicies.GraphPolicies;
import gw4e.eclipse.dsl.dSLPolicies.Model;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link gw4e.eclipse.dsl.dSLPolicies.impl.ModelImpl#getGraphPolicies <em>Graph Policies</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ModelImpl extends MinimalEObjectImpl.Container implements Model
{
  /**
   * The cached value of the '{@link #getGraphPolicies() <em>Graph Policies</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getGraphPolicies()
   * @generated
   * @ordered
   */
  protected EList<GraphPolicies> graphPolicies;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ModelImpl()
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
    return DSLPoliciesPackage.Literals.MODEL;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<GraphPolicies> getGraphPolicies()
  {
    if (graphPolicies == null)
    {
      graphPolicies = new EObjectContainmentEList<GraphPolicies>(GraphPolicies.class, this, DSLPoliciesPackage.MODEL__GRAPH_POLICIES);
    }
    return graphPolicies;
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
      case DSLPoliciesPackage.MODEL__GRAPH_POLICIES:
        return ((InternalEList<?>)getGraphPolicies()).basicRemove(otherEnd, msgs);
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
      case DSLPoliciesPackage.MODEL__GRAPH_POLICIES:
        return getGraphPolicies();
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
  public void eSet(int featureID, Object newValue)
  {
    switch (featureID)
    {
      case DSLPoliciesPackage.MODEL__GRAPH_POLICIES:
        getGraphPolicies().clear();
        getGraphPolicies().addAll((Collection<? extends GraphPolicies>)newValue);
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
      case DSLPoliciesPackage.MODEL__GRAPH_POLICIES:
        getGraphPolicies().clear();
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
      case DSLPoliciesPackage.MODEL__GRAPH_POLICIES:
        return graphPolicies != null && !graphPolicies.isEmpty();
    }
    return super.eIsSet(featureID);
  }

} //ModelImpl
