/**
 * generated by Xtext 2.12.0
 */
package gw4e.eclipse.dsl.ui.outline;

import gw4e.eclipse.dsl.dSLPolicies.Policies;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.ui.editor.outline.IOutlineNode;
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider;

/**
 * Customization of the default outline structure.
 * 
 * See https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#outline
 */
@SuppressWarnings("all")
public class DSLPoliciesOutlineTreeProvider extends DefaultOutlineTreeProvider {
  @Override
  public void _createChildren(final IOutlineNode parentNode, final EObject modelElement) {
    if ((modelElement instanceof Policies)) {
      this.createNode(parentNode, ((Policies)modelElement).getPathgenerator());
      this.createNode(parentNode, ((Policies)modelElement).getSeverity());
    } else {
      super._createChildren(parentNode, modelElement);
    }
  }
}