/*
 * generated by Xtext 2.12.0
 */
package gw4e.eclipse.dsl.ui.labeling

import com.google.inject.Inject
import gw4e.eclipse.dsl.dSLPolicies.AlgorithmType
import gw4e.eclipse.dsl.dSLPolicies.GraphPolicies
import gw4e.eclipse.dsl.dSLPolicies.Model
import gw4e.eclipse.dsl.dSLPolicies.PathGeneratorStopCondition
import gw4e.eclipse.dsl.dSLPolicies.Policies
import gw4e.eclipse.dsl.dSLPolicies.Severity
import gw4e.eclipse.dsl.dSLPolicies.StopCondition
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider
import org.eclipse.jface.resource.ImageDescriptor
import org.eclipse.ui.plugin.AbstractUIPlugin
import org.eclipse.xtext.xbase.ui.labeling.XbaseLabelProvider
import org.eclipse.emf.common.util.EList

/**
 * Provides labels for EObjects.
 * 
 * See https://www.eclipse.org/Xtext/documentation/304_ide_concepts.html#label-provider
 */
class DSLPoliciesLabelProvider extends XbaseLabelProvider {
	@Inject
	new(AdapterFactoryLabelProvider delegate) {
		super(delegate);
	}

	def static ImageDescriptor getDefaultImageDescriptor(String pluginid, String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin(pluginid, path);
	}

	protected def dispatch ImageDescriptor imageDescriptor(AlgorithmType at) {
		return getDefaultImageDescriptor("org.eclipse.xtext.ui", "icons/elcl16/ch_callees.png");
	}

	protected def dispatch ImageDescriptor imageDescriptor(StopCondition pgsc) {
		return getDefaultImageDescriptor("org.eclipse.ui.navigator", "icons/full/elcl16/filter_ps.gif");
	}

	protected def dispatch ImageDescriptor imageDescriptor(PathGeneratorStopCondition pgsc) {
		return getDefaultImageDescriptor("org.eclipse.xtext.ui", "icons/elcl16/goto_input.gif");
	}

	protected def dispatch ImageDescriptor imageDescriptor(Severity model) {
		return getDefaultImageDescriptor("org.eclipse.ui", "icons/full/etool16/editor_area.png");
	}

	protected def dispatch ImageDescriptor imageDescriptor(Model pgsc) {
		return getDefaultImageDescriptor("org.eclipse.platform.doc.user", "images/help_icon_book_closed.png");
	}

	protected def dispatch ImageDescriptor imageDescriptor(Policies policies) {
		return getDefaultImageDescriptor("org.eclipse.ui.forms", "icons/progress/ani/1.png");
	}

	protected def dispatch ImageDescriptor imageDescriptor(GraphPolicies policies) {
		return getDefaultImageDescriptor("org.eclipse.wst.json.ui", "icons/full/obj16/json-object.png");
	}

	protected def String text(StopCondition condition) {
		var gelt = condition.getGraphelement();
		if (gelt !== null) {
			val ge = gelt.getName();
			return condition.getPathtype() + "(" + ge + ")";
		} else {
			val percentage = condition.getPercentage();
			if (percentage !== null) {
				return condition.getPathtype() + "(" + percentage + ")";
			} else {
				val value = condition.getValue() + "";
				return condition.getPathtype() + "(" + value + ")";
			}
		}
	}

	protected def String text(GraphPolicies gp) {
		return gp.getGraphModelPolicies();
	}

	protected def String text(Severity severity) {
		if("I".equals(severity.getLevel())) return "Information Severity";
		if("W".equals(severity.getLevel())) return "Warning Severity";
		if("E".equals(severity.getLevel())) return "Error Severity";
	}

	protected def String text(Policies policies) {
		if (policies.isSync()) {
			return "sync";
		}
		if (policies.isNocheck()) {
			return "nocheck";
		}
		val EList<PathGeneratorStopCondition> list = policies.getPathgenerator();
		if (list.size()==0) {
			return "?";
		}
		if (list.size()==1) {
			return list.get(0).getAlgorithmType().getType() + "(...)";
		}
		return list.get(0).getAlgorithmType().getType() + "(...), ...";
 	}

	protected def String text(PathGeneratorStopCondition pgsc) {
		val ge = pgsc.getStopCondition().getGraphelement();
		if (ge !== null) {
			return pgsc.getAlgorithmType().getType() + "(" + pgsc.getStopCondition().getPathtype() + "(" +
				ge.getName() + ")" + ")";
		} else {
			val percentage = pgsc.getStopCondition().getPercentage() + "";
			if (percentage !== null) {
				return pgsc.getAlgorithmType().getType() + "(" + pgsc.getStopCondition().getPathtype() + "(" +
					percentage + ")" + ")";
			} else {
				val value = pgsc.getStopCondition().getValue() + "";
				return pgsc.getAlgorithmType().getType() + "(" + pgsc.getStopCondition().getPathtype() + "(" + value +
					")" + ")";
			}
		}
	}

	protected def String text(AlgorithmType at) {
		return at.getType();
	}

}
