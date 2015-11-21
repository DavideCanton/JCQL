package jcql.builder;

import jcql.querytree.common.QueryNode;
import jcql.querytree.expression.Leaf;
import jcql.querytree.expression.SingleOperandLeafOperator;
import jcql.querytree.expression.TwoOperandsLeafOperator;
import jcql.querytree.logic.SingleOperandLogicOperator;
import jcql.querytree.logic.TwoOperandsLogicOperator;

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
