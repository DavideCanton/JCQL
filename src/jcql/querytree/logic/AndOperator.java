package jcql.querytree.logic;

import jcql.visitor.*;

public class AndOperator extends TwoOperandsLogicOperator
{
	public AndOperator()
	{
		super("&"); 
	}

	public AndOperator(String s)
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
		return (Boolean) getLeft().evaluate(o) && (Boolean) getRight().evaluate(o);
	}
}
