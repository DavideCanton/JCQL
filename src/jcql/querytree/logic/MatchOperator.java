package jcql.querytree.logic;

import jcql.querytree.*;
import jcql.visitor.*;

public class MatchOperator extends ComparisonStringOperator
{
	public MatchOperator()
	{
		super("|="); 
	}

	public MatchOperator(String s)
	{
		super(s);
	}

	@Override
	protected boolean commutative()
	{
		return false;
	}

	@Override
	public void accept(Visitor v)
	{
		v.visit(this);
	}

	@Override
	public Object evaluate(Object o)
	{		
		Object o1 = getLeft().evaluate(o);
		Object o2 = getRight().evaluate(o);
		Validator.string(getSymbol(), o1);
		Validator.string(getSymbol(), o2);
		return o1.toString().matches(o2.toString());
	}
}
