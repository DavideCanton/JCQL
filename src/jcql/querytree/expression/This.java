package jcql.querytree.expression;

import jcql.visitor.*;

public class This extends Leaf
{

	@Override
	public void accept(Visitor v)
	{
		v.visit(this);
	}

	@Override
	public Object evaluate(Object o)
	{
		return o;
	}

	@Override
	public String toString()
	{
		return "this";
	}

	@Override
	public boolean isBound()
	{
		return false;
	}
}
