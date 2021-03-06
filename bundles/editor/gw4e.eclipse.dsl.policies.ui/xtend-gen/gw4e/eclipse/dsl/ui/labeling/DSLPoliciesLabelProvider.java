/**
 * generated by Xtext 2.12.0
 */
package gw4e.eclipse.dsl.ui.labeling;

import com.google.inject.Inject;
import gw4e.eclipse.dsl.dSLPolicies.AlgorithmType;
import gw4e.eclipse.dsl.dSLPolicies.GraphElement;
import gw4e.eclipse.dsl.dSLPolicies.GraphPolicies;
import gw4e.eclipse.dsl.dSLPolicies.Model;
import gw4e.eclipse.dsl.dSLPolicies.PathGeneratorStopCondition;
import gw4e.eclipse.dsl.dSLPolicies.Policies;
import gw4e.eclipse.dsl.dSLPolicies.Severity;
import gw4e.eclipse.dsl.dSLPolicies.StopCondition;
import java.util.Arrays;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.common.types.JvmAnnotationType;
import org.eclipse.xtext.common.types.JvmConstructor;
import org.eclipse.xtext.common.types.JvmEnumerationType;
import org.eclipse.xtext.common.types.JvmField;
import org.eclipse.xtext.common.types.JvmFormalParameter;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmOperation;
import org.eclipse.xtext.common.types.JvmTypeParameter;
import org.eclipse.xtext.xbase.XVariableDeclaration;
import org.eclipse.xtext.xbase.typesystem.override.IResolvedConstructor;
import org.eclipse.xtext.xbase.typesystem.override.IResolvedField;
import org.eclipse.xtext.xbase.typesystem.override.IResolvedOperation;
import org.eclipse.xtext.xbase.ui.labeling.XbaseLabelProvider;
import org.eclipse.xtext.xtype.XImportDeclaration;
import org.eclipse.xtext.xtype.XImportSection;

/**
 * Provides labels for EObjects.
 * 
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#label-provider
 */
@SuppressWarnings("all")
public class DSLPoliciesLabelProvider extends XbaseLabelProvider {
  @Inject
  public DSLPoliciesLabelProvider(final AdapterFactoryLabelProvider delegate) {
    super(delegate);
  }
  
  public static ImageDescriptor getDefaultImageDescriptor(final String pluginid, final String path) {
    return AbstractUIPlugin.imageDescriptorFromPlugin(pluginid, path);
  }
  
  protected ImageDescriptor _imageDescriptor(final AlgorithmType at) {
    return DSLPoliciesLabelProvider.getDefaultImageDescriptor("org.eclipse.xtext.ui", "icons/elcl16/ch_callees.png");
  }
  
  protected ImageDescriptor _imageDescriptor(final StopCondition pgsc) {
    return DSLPoliciesLabelProvider.getDefaultImageDescriptor("org.eclipse.ui.navigator", "icons/full/elcl16/filter_ps.gif");
  }
  
  protected ImageDescriptor _imageDescriptor(final PathGeneratorStopCondition pgsc) {
    return DSLPoliciesLabelProvider.getDefaultImageDescriptor("org.eclipse.xtext.ui", "icons/elcl16/goto_input.gif");
  }
  
  protected ImageDescriptor _imageDescriptor(final Severity model) {
    return DSLPoliciesLabelProvider.getDefaultImageDescriptor("org.eclipse.ui", "icons/full/etool16/editor_area.png");
  }
  
  protected ImageDescriptor _imageDescriptor(final Model pgsc) {
    return DSLPoliciesLabelProvider.getDefaultImageDescriptor("org.eclipse.platform.doc.user", "images/help_icon_book_closed.png");
  }
  
  protected ImageDescriptor _imageDescriptor(final Policies policies) {
    return DSLPoliciesLabelProvider.getDefaultImageDescriptor("org.eclipse.ui.forms", "icons/progress/ani/1.png");
  }
  
  protected ImageDescriptor _imageDescriptor(final GraphPolicies policies) {
    return DSLPoliciesLabelProvider.getDefaultImageDescriptor("org.eclipse.wst.json.ui", "icons/full/obj16/json-object.png");
  }
  
  protected String text(final StopCondition condition) {
    GraphElement gelt = condition.getGraphelement();
    if ((gelt != null)) {
      final String ge = gelt.getName();
      String _pathtype = condition.getPathtype();
      String _plus = (_pathtype + "(");
      String _plus_1 = (_plus + ge);
      return (_plus_1 + ")");
    } else {
      final String percentage = condition.getPercentage();
      if ((percentage != null)) {
        String _pathtype_1 = condition.getPathtype();
        String _plus_2 = (_pathtype_1 + "(");
        String _plus_3 = (_plus_2 + percentage);
        return (_plus_3 + ")");
      } else {
        int _value = condition.getValue();
        final String value = (Integer.valueOf(_value) + "");
        String _pathtype_2 = condition.getPathtype();
        String _plus_4 = (_pathtype_2 + "(");
        String _plus_5 = (_plus_4 + value);
        return (_plus_5 + ")");
      }
    }
  }
  
  protected String text(final GraphPolicies gp) {
    return gp.getGraphModelPolicies();
  }
  
  protected String text(final Severity severity) {
    boolean _equals = "I".equals(severity.getLevel());
    if (_equals) {
      return "Information Severity";
    }
    boolean _equals_1 = "W".equals(severity.getLevel());
    if (_equals_1) {
      return "Warning Severity";
    }
    boolean _equals_2 = "E".equals(severity.getLevel());
    if (_equals_2) {
      return "Error Severity";
    }
    return null;
  }
  
  protected String text(final Policies policies) {
    boolean _isSync = policies.isSync();
    if (_isSync) {
      return "sync";
    }
    boolean _isNocheck = policies.isNocheck();
    if (_isNocheck) {
      return "nocheck";
    }
    final EList<PathGeneratorStopCondition> list = policies.getPathgenerator();
    int _size = list.size();
    boolean _equals = (_size == 0);
    if (_equals) {
      return "?";
    }
    int _size_1 = list.size();
    boolean _equals_1 = (_size_1 == 1);
    if (_equals_1) {
      String _type = list.get(0).getAlgorithmType().getType();
      return (_type + "(...)");
    }
    String _type_1 = list.get(0).getAlgorithmType().getType();
    return (_type_1 + "(...), ...");
  }
  
  protected String text(final PathGeneratorStopCondition pgsc) {
    final GraphElement ge = pgsc.getStopCondition().getGraphelement();
    if ((ge != null)) {
      String _type = pgsc.getAlgorithmType().getType();
      String _plus = (_type + "(");
      String _pathtype = pgsc.getStopCondition().getPathtype();
      String _plus_1 = (_plus + _pathtype);
      String _plus_2 = (_plus_1 + "(");
      String _name = ge.getName();
      String _plus_3 = (_plus_2 + _name);
      String _plus_4 = (_plus_3 + ")");
      return (_plus_4 + ")");
    } else {
      String _percentage = pgsc.getStopCondition().getPercentage();
      final String percentage = (_percentage + "");
      if ((percentage != null)) {
        String _type_1 = pgsc.getAlgorithmType().getType();
        String _plus_5 = (_type_1 + "(");
        String _pathtype_1 = pgsc.getStopCondition().getPathtype();
        String _plus_6 = (_plus_5 + _pathtype_1);
        String _plus_7 = (_plus_6 + "(");
        String _plus_8 = (_plus_7 + percentage);
        String _plus_9 = (_plus_8 + ")");
        return (_plus_9 + ")");
      } else {
        int _value = pgsc.getStopCondition().getValue();
        final String value = (Integer.valueOf(_value) + "");
        String _type_2 = pgsc.getAlgorithmType().getType();
        String _plus_10 = (_type_2 + "(");
        String _pathtype_2 = pgsc.getStopCondition().getPathtype();
        String _plus_11 = (_plus_10 + _pathtype_2);
        String _plus_12 = (_plus_11 + "(");
        String _plus_13 = (_plus_12 + value);
        String _plus_14 = (_plus_13 + 
          ")");
        return (_plus_14 + ")");
      }
    }
  }
  
  protected String text(final AlgorithmType at) {
    return at.getType();
  }
  
  protected ImageDescriptor imageDescriptor(final Object at) {
    if (at instanceof JvmConstructor) {
      return _imageDescriptor((JvmConstructor)at);
    } else if (at instanceof JvmOperation) {
      return _imageDescriptor((JvmOperation)at);
    } else if (at instanceof JvmAnnotationType) {
      return _imageDescriptor((JvmAnnotationType)at);
    } else if (at instanceof JvmEnumerationType) {
      return _imageDescriptor((JvmEnumerationType)at);
    } else if (at instanceof JvmField) {
      return _imageDescriptor((JvmField)at);
    } else if (at instanceof JvmGenericType) {
      return _imageDescriptor((JvmGenericType)at);
    } else if (at instanceof JvmTypeParameter) {
      return _imageDescriptor((JvmTypeParameter)at);
    } else if (at instanceof JvmFormalParameter) {
      return _imageDescriptor((JvmFormalParameter)at);
    } else if (at instanceof XVariableDeclaration) {
      return _imageDescriptor((XVariableDeclaration)at);
    } else if (at instanceof AlgorithmType) {
      return _imageDescriptor((AlgorithmType)at);
    } else if (at instanceof GraphPolicies) {
      return _imageDescriptor((GraphPolicies)at);
    } else if (at instanceof Model) {
      return _imageDescriptor((Model)at);
    } else if (at instanceof PathGeneratorStopCondition) {
      return _imageDescriptor((PathGeneratorStopCondition)at);
    } else if (at instanceof Policies) {
      return _imageDescriptor((Policies)at);
    } else if (at instanceof Severity) {
      return _imageDescriptor((Severity)at);
    } else if (at instanceof StopCondition) {
      return _imageDescriptor((StopCondition)at);
    } else if (at instanceof IResolvedConstructor) {
      return _imageDescriptor((IResolvedConstructor)at);
    } else if (at instanceof IResolvedOperation) {
      return _imageDescriptor((IResolvedOperation)at);
    } else if (at instanceof XImportDeclaration) {
      return _imageDescriptor((XImportDeclaration)at);
    } else if (at instanceof XImportSection) {
      return _imageDescriptor((XImportSection)at);
    } else if (at instanceof IResolvedField) {
      return _imageDescriptor((IResolvedField)at);
    } else if (at != null) {
      return _imageDescriptor(at);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(at).toString());
    }
  }
}
