package jcql.querytree.logic;

import jcql.visitor.*;
import jcql.querytree.common.*;

public class EqOperator extends ComparisonOperator
{
	public EqOperator()
	{
		super("=");
	}

	public EqOperator(String s)
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
		Object o1 = getLeft().evaluate(o);
		Object o2 = getRight().evaluate(o);
		if (o1 == null && o2 == null)
			return true;
		if (o2 == null || o1 == null)
			return false;
		if (o1 instanceof Boolean && !(o2 instanceof Boolean) || !(o1 instanceof Boolean) && o2 instanceof Boolean)
			throw new InvalidOperationException("Dati non confrontabili");
		if (o1 instanceof Number && !(o2 instanceof Number) || !(o1 instanceof Number) && o2 instanceof Number)
			throw new InvalidOperationException("Dati non confrontabili");
		if (o1 instanceof Number)
			return Math.abs(((Number) o1).doubleValue() - ((Number) o2).doubleValue()) <= 1E-14;
		return o1.equals(o2);
	}
}
