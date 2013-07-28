package jcql.querytree.logic;

import jcql.visitor.*;

public class NotOperator extends SingleOperandLogicOperator
{
	public NotOperator()
	{
		super("!");
	}

	public NotOperator(String s)
	{
		super(s);
	}

	@Override
	public void accept(Visitor v)
	{
		v.visit(this);
	}

	@Override
	public Object evaluate(Object o)
	{
		return !(Boolean) getLeft().evaluate(o);
	}
}