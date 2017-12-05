package org.gw4e.eclipse.test.facade;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.graphwalker.core.model.Edge;
import org.graphwalker.core.model.Model;
import org.graphwalker.core.model.Model.RuntimeModel;
import org.gw4e.eclipse.studio.search.searchprovider.operator.Operators;
import org.graphwalker.core.model.Vertex;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import junit.framework.TestCase;

@RunWith(JUnit4.class)
public class  SearchTest extends TestCase {

	private RuntimeModel buildRuntimeModel () {
		Vertex v1 = new Vertex().setId("ONE");
		v1.setProperty("v1Property", "100");
	
	    Vertex v2 = new Vertex().setId("TWO");
	    v2.setProperty("v2Property", "v2PropertyValue");
	    
	    Edge e1 = new Edge().setId("THREE");
	    e1.setProperty("e1Property", "e1PropertyValue");
	    
	    Edge e2 = new Edge().setId("FOUR");
	    e2.setProperty("e2Property", "100");
	    
	    RuntimeModel model = new Model().setProperty("priority", 100).addEdge(e1.setSourceVertex(v1).setTargetVertex(v2))
	        .addEdge(e2.setSourceVertex(v1).setTargetVertex(v2)).build();
	    return model;
	}
	
	
	@Test
	public void testMultipleModelProperties3() throws Exception {
		RuntimeModel model = buildRuntimeModel();
		Operators op1 = new Operators ("priority",Operators.EQUAL_OPERATOR, "100,99");
		Operators op2 = new Operators ("priority",Operators.LOWER_OPERATOR,Arrays.asList("101").stream().collect(Collectors.toSet()));
		op1.setNextCriteria(op2);
		assertNotNull(op1.meetCriteria(model));
	}
	
	@Test
	public void testMultipleModelProperties() throws Exception {
		RuntimeModel model = buildRuntimeModel();
		Operators op1 = new Operators ("priority",Operators.EQUAL_OPERATOR,Arrays.asList("100","99").stream().collect(Collectors.toSet()));
		Operators op2 = new Operators ("priority",Operators.LOWER_OPERATOR,Arrays.asList("101").stream().collect(Collectors.toSet()));
		op1.setNextCriteria(op2);
		assertNotNull(op1.meetCriteria(model));
	}
	
	@Test
	public void testMultipleModelProperties2() throws Exception {
		RuntimeModel model = buildRuntimeModel();
		Operators op1 = new Operators ("priority",Operators.EQUAL_OPERATOR,Arrays.asList("100","99").stream().collect(Collectors.toSet()));
		Operators op2 = new Operators ("priority",Operators.LOWER_OPERATOR,Arrays.asList("90").stream().collect(Collectors.toSet()));
		op1.setNextCriteria(op2);
		assertNull(op1.meetCriteria(model));
	}
	@Test
	public void testEqualStringModelProperties() throws Exception {
		RuntimeModel model = buildRuntimeModel();
		Operators op = new Operators ("v2Property",Operators.EQUAL_OPERATOR,Arrays.asList("v2PropertyValue","For those about to rock.We salute you.").stream().collect(Collectors.toSet()));
		assertNotNull(op.meetCriteria(model));
	}
	@Test
	public void testEqualModelProperties() throws Exception {
		RuntimeModel model = buildRuntimeModel();
		Operators op = new Operators ("priority",Operators.EQUAL_OPERATOR,Arrays.asList("100","99").stream().collect(Collectors.toSet()));
		assertNotNull(op.meetCriteria(model));
	}
	@Test
	public void testEqualModelProperties2() throws Exception {
		RuntimeModel model = buildRuntimeModel();
		Operators op = new Operators ("priority",Operators.EQUAL_OPERATOR," 100 , 99 ");
		assertNotNull(op.meetCriteria(model));
	}
	@Test
	public void testNotEqualModelProperties() throws Exception {
		RuntimeModel model = buildRuntimeModel();
		Operators op = new Operators ("priority",Operators.NOT_EQUAL_OPERATOR,Arrays.asList("101").stream().collect(Collectors.toSet()));
		assertNotNull(op.meetCriteria(model));
	}
	@Test
	public void testLowerModelProperties() throws Exception {
		RuntimeModel model = buildRuntimeModel();
		Operators op = new Operators ("v1Property",Operators.LOWER_OPERATOR,Arrays.asList("101").stream().collect(Collectors.toSet()));
		assertNotNull(op.meetCriteria(model));
	}
	@Test
	public void testLowerOrEqualModelProperties() throws Exception {
		RuntimeModel model = buildRuntimeModel();
		Operators op = new Operators ("v1Property",Operators.LOWER_OR_EQUAL_OPERATOR,Arrays.asList("100").stream().collect(Collectors.toSet()));
		assertNotNull(op.meetCriteria(model));
	}
	@Test
	public void testUpperOrEqualModelProperties() throws Exception {
		RuntimeModel model = buildRuntimeModel();
		Operators op = new Operators ("priority",Operators.UPPER_OR_EQUAL_OPERATOR,Arrays.asList("100").stream().collect(Collectors.toSet()));
		assertNotNull(op.meetCriteria(model));
	}
	@Test
	public void testUpperModelProperties() throws Exception {
		RuntimeModel model = buildRuntimeModel();
		Operators op = new Operators ("e2Property",Operators.UPPER_OPERATOR,Arrays.asList("99").stream().collect(Collectors.toSet()));
		assertNotNull(op.meetCriteria(model));
	}
	@Test
	public void testNOOperatorModelProperties() throws Exception {
		RuntimeModel model = buildRuntimeModel();
		Operators op = new Operators ("e2Property",Operators.NO_OPERATOR,Arrays.asList("99").stream().collect(Collectors.toSet()));
		assertNotNull(op.meetCriteria(model));
	}
}
