package org.gw4e.eclipse.studio.editor.outline.filter;

/*-
 * #%L
 * gw4e
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2017 gw4e-project
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import java.util.HashMap;
import java.util.Map;

import org.gw4e.eclipse.studio.model.GWEdge;
import org.gw4e.eclipse.studio.model.GraphElement;

public class WeightCriteria extends AbstractCriteria {
	String soperator ;
	String svalue;
	
	static Map<String,Operator> operators = new HashMap<String,Operator> ();
	static {
		operators.put(OutLineFilter.NO_OPERATOR, new NoOperator());
		operators.put(OutLineFilter.EQUAL_OPERATOR, new EqualsOperator());
		operators.put(OutLineFilter.NOT_EQUAL_OPERATOR,new NotEqualsOperator());
		operators.put(OutLineFilter.UPPER_OPERATOR,new UpperOperator());
		operators.put(OutLineFilter.UPPER_OR_EQUAL_OPERATOR,new UpperOrEqualsOperator());
		operators.put(OutLineFilter.LOWER_OPERATOR,new LowerOperator());
		operators.put(OutLineFilter.LOWER_OR_EQUAL_OPERATOR,new LowerOrEqualsOperator());
	}
	
	public WeightCriteria(OutLineFilter filter) {
		soperator = filter.getOperator();
		svalue = filter.getWeight();
	}

	@Override
	public GraphElement meetCriteria(GraphElement element) {
		Operator operator = operators.getOrDefault(soperator, new NoOperator());
		if (svalue==null || svalue.trim().length()==0) {
			return this.executeNext(element);
		}
		
		if (!(element instanceof GWEdge)) {
			return null;
		}

		GWEdge edge = (GWEdge) element;
		Double d = edge.getWeight();
		boolean meet;
		try {
			meet = operator.meetCriteria(d, Double.parseDouble(svalue));
		} catch (NumberFormatException e) {
			return this.executeNext(element);
		}
		if (meet) {
			return this.executeNext(element);
		}
		return null;
	}

	 
	public static interface  Operator {
		public boolean meetCriteria (Double weight, double value);
	}
	
	 public static class NoOperator implements Operator {
		@Override
		public boolean meetCriteria(Double weight, double value) {
			return true;
		}
	}
	public static class EqualsOperator implements Operator {
		@Override
		public boolean meetCriteria(Double weight, double value) {
			if (weight==null) return false;
			return value == weight;
		}
	}	
	public static class UpperOperator implements Operator {
		@Override
		public boolean meetCriteria(Double weight, double value) {
			if (weight==null) return false;
			return weight > value;
		}
	}
	public static class UpperOrEqualsOperator implements Operator {
		@Override
		public boolean meetCriteria(Double weight, double value) {
			if (weight==null) return false;
			return weight >= value;
		}
	}
	public static class LowerOperator implements Operator {
		@Override
		public boolean meetCriteria(Double weight, double value) {
			if (weight==null) return false;
			return weight < value;
		}
	}
	public static class LowerOrEqualsOperator implements Operator {
		@Override
		public boolean meetCriteria(Double weight, double value) {
			if (weight==null) return false;
			return weight <= value;
		}
	}
	public static class NotEqualsOperator implements Operator {
		@Override
		public boolean meetCriteria(Double weight, double value) {
			if (weight==null) return false;
			return weight != value;
		}
	}
}
