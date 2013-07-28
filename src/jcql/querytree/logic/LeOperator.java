package jcql.querytree.logic;

import jcql.visitor.*;
import jcql.querytree.common.*;

public class LeOperator extends ComparisonOperator
{
	public LeOperator()
	{
		super("<=");
	}

	public LeOperator(String s)
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
		if (o1 instanceof Number && o2 instanceof Number)
			return ((Number) o1).doubleValue() <= ((Number) o2).doubleValue();
		if (o1 instanceof Comparable && o2 instanceof Comparable)
			return ((Comparable) o1).compareTo(o2) <= 0;
		throw new InvalidOperationException("Operator " + super.getSymbol() + " not valid on different types.");
	}
}