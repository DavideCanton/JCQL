package jcql.querytree.expression;

import jcql.querytree.*;
import jcql.visitor.*;

/**
 * La classe {@link NegOperator} modella un operatore di negazione.
 * 
 * @author davide
 */
public class NegOperator extends SingleOperandLeafOperator
{
	/**
	 * Costruisce un operatore col simbolo di default "-".
	 */
	public NegOperator()
	{
		super("-");
	}

	/**
	 * Costruisce un operatore col simbolo s.
	 * 
	 * @param s
	 *            Il simbolo
	 */
	public NegOperator(String s)
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

	@Override
	public Object evaluate(Object o)
	{
		Object o1 = getLeft().evaluate(o);
		Validator.number(getSymbol(), o1);
		double v1 = ((Number) o1).doubleValue();
		return -v1;
	}

	@Override
	public String toString()
	{
		return "( - " + getLeft().toString() + ")";
	}
}