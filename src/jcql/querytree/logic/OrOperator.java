package jcql.querytree.logic;

import jcql.visitor.*;

public class OrOperator extends TwoOperandsLogicOperator
{
	public OrOperator()
	{
		super("|"); 
	}

	public OrOperator(String s)
	{
		super(s);
	}

	@Override
	protected boolean commutative()
	{
		return true;
	}

	@Override
	public void accept(Visitor v)
	{
		v.visit(this);
	}

	@Override
	public Object evaluate(Object o)
	{		
		return (Boolean) getLeft().evaluate(o) || (Boolean) getRight().evaluate(o);
	}
}