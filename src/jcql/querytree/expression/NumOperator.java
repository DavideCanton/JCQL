package jcql.querytree.expression;

import jcql.visitor.*;

public class NumOperator extends SingleOperandLeafOperator
{
	public NumOperator()
	{
		super("num");
	}

	@Override
	public void accept(Visitor v)
	{
		v.visit(this);
	}

	@Override
	public Object evaluate(Object o)
	{		
		return Double.parseDouble(String.valueOf(getLeft().evaluate(o)));
	}
}
