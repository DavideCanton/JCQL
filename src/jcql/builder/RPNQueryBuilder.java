package jcql.builder;

import jcql.querytree.common.Operator;
import jcql.querytree.common.QueryNode;
import jcql.querytree.expression.Leaf;
import jcql.querytree.expression.SingleOperandLeafOperator;
import jcql.querytree.expression.TwoOperandsLeafOperator;
import jcql.querytree.logic.SingleOperandLogicOperator;
import jcql.querytree.logic.TwoOperandsLogicOperator;

import java.util.LinkedList;

public class RPNQueryBuilder implements QueryBuilder
{
    private LinkedList<QueryNode> stack = new LinkedList<>();

    private void buildTwoOperandsOperator(Operator o)
    {
        if (stack.size() < 2)
            throw new IllegalStateException("Size < 2");
        o.setRight(stack.pop());
        o.setLeft(stack.pop());
        stack.push(o);
    }

    @Override
    public void buildTwoOperandsLeafOperator(TwoOperandsLeafOperator o)
    {
        buildTwoOperandsOperator(o);
    }

    @Override
    public void buildTwoOperandsLogicOperator(TwoOperandsLogicOperator o)
    {
        buildTwoOperandsOperator(o);

    }

    private void buildSingleOperandOperator(Operator o)
    {
        if (stack.size() < 1)
            throw new IllegalStateException("Size < 1");
        o.setLeft(stack.pop());
        stack.push(o);
    }

    @Override
    public void buildSingleOperandsLeafOperator(SingleOperandLeafOperator o)
    {
        buildSingleOperandOperator(o);
    }

    @Override
    public void buildSingleOperandsLogicOperator(SingleOperandLogicOperator o)
    {
        buildSingleOperandOperator(o);
    }

    @Override
    public void buildLeaf(Leaf l)
    {
        stack.push(l);
    }

    @Override
    public QueryNode getQuery()
    {
        if (stack.size() != 1)
            throw new IllegalStateException("size != 1");
        return stack.pop();
    }

    @Override
    public void reset()
    {
        stack.clear();
    }
}
