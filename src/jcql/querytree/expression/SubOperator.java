package jcql.querytree.expression;

import jcql.querytree.*;
import jcql.visitor.*;

/**
 * La classe {@link SubOperator} modella un operatore di sottrazione.
 * 
 * @author davide
 */
public class SubOperator extends TwoOperandsLeafOperator
{
	/**
	 * Costruisce un operatore col simbolo di default "-".
	 */
	public SubOperator()
	{
		super("-");
	}

	/**
	 * Costruisce un operatore col simbolo s.
	 * 
	 * @param s
	 *            Il simbolo
	 */
	public SubOperator(String s)
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
		return false;
	}

	@Override
	public Object evaluate(Object o)
	{
		Object o1 = getLeft().evaluate(o);
		Object o2 = getRight().evaluate(o);
		Validator.number(getSymbol(), o1);
		Validator.number(getSymbol(), o2);
		double v1 = ((Number) o1).doubleValue();
		double v2 = ((Number) o2).doubleValue();
		return v1 - v2;
	}
}
