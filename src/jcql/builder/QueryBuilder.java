package jcql.builder;

import jcql.querytree.common.*;
import jcql.querytree.expression.*;
import jcql.querytree.logic.*;

public interface QueryBuilder
{
	void buildTwoOperandsLeafOperator(TwoOperandsLeafOperator o);

	void buildTwoOperandsLogicOperator(TwoOperandsLogicOperator o);

	void buildSingleOperandsLeafOperator(SingleOperandLeafOperator o);

	void buildSingleOperandsLogicOperator(SingleOperandLogicOperator o);

	void buildLeaf(Leaf l);

	QueryNode getQuery();
	
	void reset();
}
