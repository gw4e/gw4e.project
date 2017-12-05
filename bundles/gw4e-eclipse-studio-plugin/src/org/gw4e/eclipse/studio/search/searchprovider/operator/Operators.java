package org.gw4e.eclipse.studio.search.searchprovider.operator;

import java.util.ArrayList;
import java.util.Arrays;

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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.graphwalker.core.model.Model.RuntimeModel;
import org.gw4e.eclipse.facade.GraphWalkerFacade;
import org.gw4e.eclipse.facade.ResourceManager;
import org.gw4e.eclipse.studio.search.searchprovider.ModelSearchQueryDefinition;

public class Operators extends AbstractCriteria {
	public void setProperty(String property) {
		this.property = property;
	}

	public void setSoperator(String soperator) {
		this.soperator = soperator;
	}

	public void setChallengedValues(Set<String> challengedValues) {
		this.challengedValues = challengedValues;
	}

	public static String NO_OPERATOR = "";
	public static String EQUAL_OPERATOR = "=";
	public static String NOT_EQUAL_OPERATOR = "!=";
	public static String UPPER_OPERATOR = ">";
	public static String UPPER_OR_EQUAL_OPERATOR = ">=";
	public static String LOWER_OPERATOR = "<";
	public static String LOWER_OR_EQUAL_OPERATOR = "<=";

	public static List<String> getAvailableOperators() {
		List<String> operators = new ArrayList<String>();
		operators.add(EQUAL_OPERATOR);
		operators.add(NOT_EQUAL_OPERATOR);
		operators.add(UPPER_OPERATOR);
		operators.add(UPPER_OR_EQUAL_OPERATOR);
		operators.add(LOWER_OPERATOR);
		operators.add(LOWER_OR_EQUAL_OPERATOR);
		operators.add(NO_OPERATOR);
		return operators;
	}

	String soperator;
	public String getSoperator() {
		return soperator;
	}

	public String getProperty() {
		return property;
	}

	public Set<String> getChallengedValues() {
		return challengedValues;
	}

	String property;
	Set<String> challengedValues;
	 
	static Map<String, Operator> OPERATORS = new HashMap<String, Operator>();
	static {
		OPERATORS.put(NO_OPERATOR, new NoOperator());
		OPERATORS.put(EQUAL_OPERATOR, new EqualsOperator());
		OPERATORS.put(NOT_EQUAL_OPERATOR, new NotEqualsOperator());
		OPERATORS.put(UPPER_OPERATOR, new UpperOperator());
		OPERATORS.put(UPPER_OR_EQUAL_OPERATOR, new UpperOrEqualsOperator());
		OPERATORS.put(LOWER_OPERATOR, new LowerOperator());
		OPERATORS.put(LOWER_OR_EQUAL_OPERATOR, new LowerOrEqualsOperator());
	}
	
	 
	
	static List<Operators> operators = new ArrayList<Operators>();
	
	public static void clear () {
		operators.clear();
	}
	
	public static void addDefault () {
		Operators op = new Operators (  ModelSearchQueryDefinition.getDefaultPropertyName(),ModelSearchQueryDefinition.getDefaultOperator(),ModelSearchQueryDefinition.getDefaultValue());
		 add (op);
	}
	
	public static void add (Operators op) {
		operators.add(op);
	}
	
	public static void remove (Operators op) {
		operators.remove(op);
	}	
	
	public static List<Operators> getAll () {
		return operators;
	}
	
	public boolean equals(Object o){        
	     if (!(o instanceof Operators)) return false;
	     Operators other = (Operators)o;
	     return this.challengedValues.equals(other.challengedValues) &&
	    		 this.property.equals(other.property) &&
	    		 this.soperator.equals(other.soperator);
	}
	
	private Operators(  String property, String soperator) {
		this.property = property;
		this.soperator = soperator;
		 
	}

	public Operators( String property, String soperator, Set<String> values) {
		this(property, soperator);
		challengedValues = values;

	}

	public Operators( String property, String soperator, String values) {
		this( property, soperator);
		challengedValues = new HashSet<String>(Arrays.asList(values.split(",")).stream().map(elt -> elt.trim()).collect(Collectors.toList()));
	}

	@Override
	public RuntimeModel meetCriteria(RuntimeModel element) {

		Operator operator = OPERATORS.getOrDefault(soperator, new NoOperator());

		Set<String> propertValues = GraphWalkerFacade.getPropertiesValue(element, property);
		boolean meet = false;
		try {
			for (String propertValue : propertValues) {
				meet = operator.meetCriteria(propertValue, challengedValues);
				if (meet)
					break;
			}

		} catch (NumberFormatException e) {
			return null;
		}
		if (meet) {
			return this.executeNext(element);
		}
		return null;
	}

	public static interface Operator {
		public boolean meetCriteria(String value, Set<String> challengedValue);
	}

	public static class NoOperator implements Operator {
		@Override
		public boolean meetCriteria(String value, Set<String> challengedValue) {
			return false;
		}
	}

	public static class EqualsOperator implements Operator {
		@Override
		public boolean meetCriteria(String value, Set<String> challengedValues) {
			if (value == null)
				return false;
			if (challengedValues == null)
				return false;
			long count = challengedValues.stream().filter(challengedValue -> {
				try {
					Double v1 = Double.parseDouble(value);
					Double v2 = Double.parseDouble(challengedValue);
					boolean b = ((v1 - v2) == 0);
					ResourceManager.logInfo(null, v1 + "=" + v2 + "-->" + b);
					return b;
				} catch (NumberFormatException e) {
					boolean b = String.valueOf(value).equalsIgnoreCase(String.valueOf(challengedValue));
					ResourceManager.logInfo(null, String.valueOf(value) + "=" + String.valueOf(challengedValue) + "-->" + b);
					return b;
				}
			}).count();
			return count > 0;
		}
	}

	public static class UpperOperator implements Operator {
		@Override
		public boolean meetCriteria(String value, Set<String> challengedValue) {
			if (value == null)
				return false;
			if (challengedValue == null)
				return false;
			if (challengedValue.size() != 1)
				return false;
			Double v1 = Double.parseDouble(value);
			Double v2 = Double.parseDouble(challengedValue.stream().findFirst().get());
			boolean b = Double.parseDouble(value) > Double.parseDouble(challengedValue.stream().findFirst().get());
			ResourceManager.logInfo(null, v1 + ">" + v2 + "-->" + b);
			return b;
		}
	}

	public static class UpperOrEqualsOperator implements Operator {
		@Override
		public boolean meetCriteria(String value, Set<String> challengedValue) {
			if (value == null)
				return false;
			if (challengedValue == null)
				return false;
			if (challengedValue.size() != 1)
				return false;
			Double v1 = Double.parseDouble(value);
			Double v2 = Double.parseDouble(challengedValue.stream().findFirst().get());
			boolean b = Double.parseDouble(value) >= Double.parseDouble(challengedValue.stream().findFirst().get());
			ResourceManager.logInfo(null, v1 + ">=" + v2 + "-->" + b);
			return b;
		}
	}

	public static class LowerOperator implements Operator {
		@Override
		public boolean meetCriteria(String value, Set<String> challengedValue) {
			if (value == null)
				return false;
			if (challengedValue == null)
				return false;
			if (challengedValue.size() != 1)
				return false;
			Double v1 = Double.parseDouble(value);
			Double v2 = Double.parseDouble(challengedValue.stream().findFirst().get());
			boolean b = Double.parseDouble(value) < Double.parseDouble(challengedValue.stream().findFirst().get());
			ResourceManager.logInfo(null, v1 + "<" + v2 + "-->" + b);
			return b;
		}
	}

	public static class LowerOrEqualsOperator implements Operator {
		@Override
		public boolean meetCriteria(String value, Set<String> challengedValue) {
			if (value == null)
				return false;
			if (challengedValue == null)
				return false;
			if (challengedValue.size() != 1)
				return false;
			Double v1 = Double.parseDouble(value);
			Double v2 = Double.parseDouble(challengedValue.stream().findFirst().get());
			boolean b = Double.parseDouble(value) <= Double.parseDouble(challengedValue.stream().findFirst().get());
			ResourceManager.logInfo(null, v1 + "<=" + v2 + "-->" + b);
			return b;
		}
	}

	public static class NotEqualsOperator implements Operator {
		@Override
		public boolean meetCriteria(String value, Set<String> challengedValues) {
			if (value == null)
				return false;
			if (challengedValues == null)
				return false;
			long count = challengedValues.stream().filter(challengedValue -> {
				try {
					Double v1 = Double.parseDouble(value);
					Double v2 = Double.parseDouble(challengedValue);
					boolean b = ((v1 - v2) != 0);
					ResourceManager.logInfo(null, v1 + "!=" + v2 + "-->" + b);
					return b;
				} catch (NumberFormatException e) {
					boolean b = !(String.valueOf(value).equalsIgnoreCase(String.valueOf(challengedValue)));
					ResourceManager.logInfo(null, String.valueOf(value) + "!=" + String.valueOf(challengedValue) + "-->" + b);
					return b;
				}
			}).count();
			return count > 0;			 

		}
	}
}
