package jcql.builder;

import java.util.*;
import jcql.querytree.common.*;
import jcql.querytree.expression.*;
import jcql.querytree.logic.*;

public class RPNQueryBuilder implements QueryBuilder
{
	private Stack<QueryNode> stack = new Stack<QueryNode>();

	@Override
	public void buildTwoOperandsLeafOperator(TwoOperandsLeafOperator o)
	{
		if (stack.size() < 2)
			throw new IllegalStateException("Size < 2");
		o.setRight(stack.pop());
		o.setLeft(stack.pop());
		stack.push(o);
	}

	@Override
	public void buildTwoOperandsLogicOperator(TwoOperandsLogicOperator o)
	{
		if (stack.size() < 2)
			throw new IllegalStateException("Size < 2");
		o.setRight(stack.pop());
		o.setLeft(stack.pop());
		stack.push(o);
	}

	@Override
	public void buildSingleOperandsLeafOperator(SingleOperandLeafOperator o)
	{
		if (stack.size() < 1)
			throw new IllegalStateException("Size < 1");
		o.setLeft(stack.pop());
		stack.push(o);
	}

	@Override
	public void buildSingleOperandsLogicOperator(SingleOperandLogicOperator o)
	{
		if (stack.size() < 1)
			throw new IllegalStateException("Size < 1");
		o.setLeft(stack.pop());
		stack.push(o);
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
