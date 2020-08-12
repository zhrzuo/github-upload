package dsa_assignment3;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class PLTreeNodeTest
{
	private static final Logger logger        = Logger.getLogger(PLTreeNodeTest.class);

	@Rule
	public Timeout              globalTimeout = new Timeout(2000, TimeUnit.MILLISECONDS);

	@Test
	public void testCheckStudentIdentification()
	{
		assertNotEquals("Please update the studentID field in PLTreeNode.java with your student id number", //
				"MY STUDENT ID", PLTreeNode.getStudentID());
		assertNotEquals("Please update the studentName field in PLTreeNode.java with your name", //
				"MY NAME", PLTreeNode.getStudentName());
	}


	/**
	 * This is not a true JUnit Test method: it merely gives examples of calling
	 * the PLTree methods and obtaining output.
	 * 
	 * As part of this exercise, you should devise your OWN tests to check that
	 * your code is performing as the JavaDoc and the assignment hand-out says it
	 * should. You are strongly advised to use the "Coverage" tool to check for
	 * parts of your code that are not executed as part of your tests. The coverage
	 * tool can be invoked from the "Run" menu item in Eclipse or from the button
	 * beside the "Run" and "Debug" buttons in Eclipse
	 */
	@Test
	public void testPLTree()
	{
		NodeType[] typeList = { NodeType.R, NodeType.P, NodeType.OR, NodeType.TRUE, NodeType.Q, NodeType.NOT, NodeType.AND,
				NodeType.IMPLIES };
		logger.debug("typeList: " + Arrays.toString(typeList));

		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

		assertNotNull("PLTree construction failed when using: " + typeList, pltree);
		logger.debug("Constructed: " + pltree.toStringPrefix());
		logger.debug("Constructed: " + pltree.toStringInfix());
		NodeType[] typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		Map<NodeType, Boolean> bindings = new HashMap<>();
		bindings.put(NodeType.P, true);
		bindings.put(NodeType.R, false);

		pltree.applyVarBindings(bindings);
		logger.debug(String.format("Applied bindings : %s to get: %s", bindings, pltree.toStringPrefix()));
		logger.debug(String.format("Applied bindings : %s to get: %s", bindings, pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		//Boolean b = pltree.evaluateConstantSubtrees();
		//logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringPrefix()));
		//logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringInfix()));

		pltree.replaceImplies();
		logger.debug(String.format("Replace Implies: %s", pltree.toStringPrefix()));
		logger.debug(String.format("Replace Implies: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.pushNotDown();
		logger.debug(String.format("pushNotDown: %s", pltree.toStringPrefix()));
		logger.debug(String.format("pushNotDown: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.pushOrBelowAnd();
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringPrefix()));
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.makeAndOrRightDeep();
		logger.debug(String.format("makeAndOrRightDeep: %s", pltree.toStringPrefix()));
		logger.debug(String.format("makeAndOrRightDeep: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.evaluateConstantSubtrees();
		logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringPrefix()));
		logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		//==========================

		typeList = new NodeType[] { NodeType.R, NodeType.P, NodeType.IMPLIES, NodeType.S, NodeType.IMPLIES, NodeType.NOT, NodeType.Q,
				NodeType.IMPLIES };
		logger.debug("typeList: " + Arrays.toString(typeList));

		pltree = PLTreeNode.reversePolishBuilder(typeList);
		logger.debug("Constructed: " + pltree.toStringPrefix());
		logger.debug("Constructed: " + pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.reduceToCNF();
		logger.debug(String.format("ReduceToCNF to get: %s", pltree.toStringPrefix()));
		logger.debug(String.format("ReduceToCNF to get: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.evaluateConstantSubtrees();
		logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringPrefix()));
		logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		//==========================

		typeList = new NodeType[] { NodeType.A, NodeType.B, NodeType.AND, NodeType.C, NodeType.OR, NodeType.D, NodeType.OR, NodeType.E,
				NodeType.OR, NodeType.F, NodeType.OR, NodeType.G, NodeType.OR, NodeType.H, NodeType.OR };
		logger.debug("typeList: " + Arrays.toString(typeList));
		pltree = PLTreeNode.reversePolishBuilder(typeList);
		logger.debug("Constructed: " + pltree.toStringPrefix());
		logger.debug("Constructed: " + pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.pushOrBelowAnd();
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringPrefix()));
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));
		
		
		//===========================

		logger.debug("Extra tests of pushOrBelowAnd()");
		typeList = new NodeType[] { NodeType.A, NodeType.B, NodeType.AND, NodeType.C, NodeType.AND, NodeType.D, NodeType.OR, NodeType.E, NodeType.OR};
		logger.debug("typeList: " + Arrays.toString(typeList));
		pltree = PLTreeNode.reversePolishBuilder(typeList);
		logger.debug("Constructed: " + pltree.toStringPrefix());
		logger.debug("Constructed: " + pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.pushOrBelowAnd();
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringPrefix()));
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

	}
	/*
	 To assist you in writing your tests, the log output from the solution to
	 the exercise for the above code is included below. You may find it useful
	 to copy and paste some of the strings below into some of your tests.
			
     typeList: [R, P, OR, TRUE, Q, NOT, AND, IMPLIES]
     Constructed: implies(or(R,P),and(true,not(Q)))
     Constructed: ((R鈭≒)鈫�(鈯も埀卢Q))
     typeListReturned: [R, P, OR, TRUE, Q, NOT, AND, IMPLIES]
     Applied bindings : {P=true, R=false} to get: implies(or(false,true),and(true,not(Q)))
     Applied bindings : {P=true, R=false} to get: ((鈯モ埁鈯�)鈫�(鈯も埀卢Q))
     typeListReturned: [FALSE, TRUE, OR, TRUE, Q, NOT, AND, IMPLIES]
     Replace Implies: or(not(or(false,true)),and(true,not(Q)))
     Replace Implies: (卢(鈯モ埁鈯�)鈭�(鈯も埀卢Q))
     typeListReturned: [FALSE, TRUE, OR, NOT, TRUE, Q, NOT, AND, OR]
     pushNotDown: or(and(not(false),not(true)),and(true,not(Q)))
     pushNotDown: ((卢鈯モ埀卢鈯�)鈭�(鈯も埀卢Q))
     typeListReturned: [FALSE, NOT, TRUE, NOT, AND, TRUE, Q, NOT, AND, OR]
     pushOrBelowAnd: and(and(or(not(false),true),or(not(false),not(Q))),and(or(not(true),true),or(not(true),not(Q))))
     pushOrBelowAnd: (((卢鈯モ埁鈯�)鈭�(卢鈯モ埁卢Q))鈭�((卢鈯も埁鈯�)鈭�(卢鈯も埁卢Q)))
     typeListReturned: [FALSE, NOT, TRUE, OR, FALSE, NOT, Q, NOT, OR, AND, TRUE, NOT, TRUE, OR, TRUE, NOT, Q, NOT, OR, AND, AND]
     makeAndOrRightDeep: and(or(not(false),true),and(or(not(false),not(Q)),and(or(not(true),true),or(not(true),not(Q)))))
     makeAndOrRightDeep: ((卢鈯モ埁鈯�)鈭�((卢鈯モ埁卢Q)鈭�((卢鈯も埁鈯�)鈭�(卢鈯も埁卢Q))))
     typeListReturned: [FALSE, NOT, TRUE, OR, FALSE, NOT, Q, NOT, OR, TRUE, NOT, TRUE, OR, TRUE, NOT, Q, NOT, OR, AND, AND, AND]
     Evaluate constant subtrees to get: not(Q)
     Evaluate constant subtrees to get: 卢Q
     typeListReturned: [Q, NOT]
     typeList: [R, P, IMPLIES, S, IMPLIES, NOT, Q, IMPLIES]
     Constructed: implies(not(implies(implies(R,P),S)),Q)
     Constructed: (卢((R鈫扨)鈫扴)鈫扱)
     typeListReturned: [R, P, IMPLIES, S, IMPLIES, NOT, Q, IMPLIES]
     ReduceToCNF to get: and(or(R,or(S,Q)),or(not(P),or(S,Q)))
     ReduceToCNF to get: ((R鈭�(S鈭≦))鈭�(卢P鈭�(S鈭≦)))
     typeListReturned: [R, S, Q, OR, OR, P, NOT, S, Q, OR, OR, AND]
     Evaluate constant subtrees to get: and(or(R,or(S,Q)),or(not(P),or(S,Q)))
     Evaluate constant subtrees to get: ((R鈭�(S鈭≦))鈭�(卢P鈭�(S鈭≦)))
     typeListReturned: [R, S, Q, OR, OR, P, NOT, S, Q, OR, OR, AND]
     typeList: [A, B, AND, C, OR, D, OR, E, OR, F, OR, G, OR, H, OR]
     Constructed: or(or(or(or(or(or(and(A,B),C),D),E),F),G),H)
     Constructed: (((((((A鈭)鈭–)鈭―)鈭‥)鈭‵)鈭℅)鈭℉)
     typeListReturned: [A, B, AND, C, OR, D, OR, E, OR, F, OR, G, OR, H, OR]
     pushOrBelowAnd: and(or(or(or(or(or(or(A,C),D),E),F),G),H),or(or(or(or(or(or(B,C),D),E),F),G),H))
     pushOrBelowAnd: (((((((A鈭–)鈭―)鈭‥)鈭‵)鈭℅)鈭℉)鈭�((((((B鈭–)鈭―)鈭‥)鈭‵)鈭℅)鈭℉))
     typeListReturned: [A, C, OR, D, OR, E, OR, F, OR, G, OR, H, OR, B, C, OR, D, OR, E, OR, F, OR, G, OR, H, OR, AND]
     Extra tests of pushOrBelowAnd()
     typeList: [A, B, AND, C, AND, D, OR, E, OR]
     Constructed: or(or(and(and(A,B),C),D),E)
     Constructed: ((((A鈭)鈭)鈭―)鈭‥)
     typeListReturned: [A, B, AND, C, AND, D, OR, E, OR]
     pushOrBelowAnd: and(and(or(or(A,D),E),or(or(B,D),E)),or(or(C,D),E))
     pushOrBelowAnd: ((((A鈭―)鈭‥)鈭�((B鈭―)鈭‥))鈭�((C鈭―)鈭‥))
     typeListReturned: [A, D, OR, E, OR, B, D, OR, E, OR, AND, C, D, OR, E, OR, AND]


	 */
	
	// The following are simple examples using the first results of the sample output above.

	@Test
	public void testPLTreeConstruction()
	{
		NodeType[] typeList = { NodeType.R, NodeType.P, NodeType.OR, NodeType.TRUE, NodeType.Q, NodeType.NOT, NodeType.AND,	NodeType.IMPLIES };

		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

		assertNotNull("PLTree construction failed when using: " + typeList, pltree);
		
		NodeType[] newTypeList = pltree.getReversePolish();
		
		// The hamcrest matcher that follows generates better error messages automatically when comparing arrays than assertArrayEquals().
		// Try uncommenting the following line to see what the error message looks like if the arrays don't match
		//		newTypeList[1] = NodeType.A;
		assertThat(newTypeList, equalTo(typeList));
	}	

	@Test
	public void testPLTreeToPrefixString()
	{
		NodeType[] typeList = { NodeType.R, NodeType.P, NodeType.OR, NodeType.TRUE, NodeType.Q, NodeType.NOT, NodeType.AND,	NodeType.IMPLIES };

		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

		// Always check that the tree generated is not null before any other tests:
		// if it is, it means that you have an error in your input typeList
		
		assertNotNull("PLTree construction failed when using: " + typeList, pltree);
		
		// Again, the Hamcrest matcher generates suitable error messages automatically:
		assertThat(pltree.toStringPrefix(), equalTo("implies(or(R,P),and(true,not(Q)))"));		
	}	

}
