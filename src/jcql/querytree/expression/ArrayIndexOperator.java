package jcql.querytree.expression;

import java.lang.reflect.*;
import java.util.*;
import jcql.querytree.*;
import jcql.querytree.common.*;
import jcql.visitor.*;

public class ArrayIndexOperator extends TwoOperandsLeafOperator
{
	public ArrayIndexOperator()
	{
		super("[]");
	}

	@Override
	protected boolean commutative()
	{
		return false;
	}

	@Override
	public String toString()
	{
		return getLeft().toString() + "[" + getRight().toString() + "]";
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
		Validator.indexable(getSymbol(), o1);
		Validator.number(getSymbol(), o2);
		double d = ((Number) o2).doubleValue();
		if (!Validator.isInteger(d))
			throw new InvalidOperationException("Index not integer");
		int i = (int) Math.round(d);
		if (Validator.array(o1))
			return Array.get(o1, i);
		else if (o1 instanceof List)
			return ((List) o1).get(i);
		else
			return String.valueOf(o.toString().charAt(i));
	}
}
