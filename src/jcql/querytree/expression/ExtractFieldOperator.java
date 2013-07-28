package jcql.querytree.expression;

import java.lang.reflect.*;
import jcql.querytree.common.*;
import jcql.visitor.*;

/**
 * La classe {@link ExtractFieldOperator} modella un nodo di tipo campo.
 * 
 * @author davide
 */
public class ExtractFieldOperator extends TwoOperandsLeafOperator
{
	public ExtractFieldOperator()
	{
		super(".");
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
	public String toString()
	{
		return getLeft().toString() + "." + getRight().toString();
	}

	@Override
	public Object evaluate(Object o)
	{
		try
		{
			Object target = getLeft().evaluate(o);						
			String n = getRight().evaluate(o).toString();
			Field f = target.getClass().getDeclaredField(n);
			f.setAccessible(true);
			target = f.get(target);
			// tratto i caratteri come stringhe di lunghezza 1
			if (target instanceof Character)
				target = String.valueOf(target);
			return target;
		}
		catch (Exception e)
		{			
			InvalidFieldException i = new InvalidFieldException("Campo non valido: " + e.getMessage());
			i.initCause(e);
			throw i;
		}
	}

	@Override
	protected boolean commutative()
	{
		return false;
	}
}
