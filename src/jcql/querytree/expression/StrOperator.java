package jcql.querytree.expression;

import jcql.visitor.*;

public class StrOperator extends SingleOperandLeafOperator
{
	public StrOperator()
	{
		super("str");
	}

	public void accept(Visitor v)
	{
		v.visit(this);
	}

	@Override
	public Object evaluate(Object o)
	{
		return String.valueOf(getLeft().evaluate(o));
	}
}
