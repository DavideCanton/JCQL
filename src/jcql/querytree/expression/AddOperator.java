package jcql.querytree.expression;

import jcql.querytree.*;
import jcql.querytree.common.*;
import jcql.visitor.*;

/**
 * La classe {@link AddOperator} modella un operatore additivo.
 * 
 * @author davide
 */
public class AddOperator extends TwoOperandsLeafOperator
{
	/**
	 * Costruisce un operatore col simbolo di default "+".
	 */
	public AddOperator()
	{
		super("+");
	}

	/**
	 * Costruisce un operatore col simbolo s.
	 * 
	 * @param s
	 *            Il simbolo
	 */
	public AddOperator(String s)
	{
		super(s);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void accept(Visitor v)
	{
		v.visit(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean commutative()
	{
		return true;
	}

	@Override
	public Object evaluate(Object o)
	{
		Object o1 = getLeft().evaluate(o);
		Object o2 = getRight().evaluate(o);
		try
		{
			Validator.number(getSymbol(), o1);
			Validator.number(getSymbol(), o2);
			double v1 = ((Number) o1).doubleValue();
			double v2 = ((Number) o2).doubleValue();
			return v1 + v2;
		}
		catch (Exception e)
		{
			try
			{
				Validator.string(getSymbol(), o1);
				Validator.string(getSymbol(), o2);
				return o1.toString() + o2.toString();
			}
			catch (Exception e2)
			{
				throw new InvalidOperationException("Operator " + getSymbol() + " not valid on non-numeric or non-String types.");
			}
		}
	}
}
