package jcql.querytree.expression;

import jcql.visitor.*;

public class Bool extends Leaf
{
	private final boolean value;

	public Bool(boolean value)
	{
		this.value = value;
	}

	public boolean getValue()
	{
		return value;
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
	public String toString()
	{
		return "" + value;
	}

	/**
	 * Restituisce <code>true</code> se e solo se:<br>
	 * <code>((Constant)o).value == this.value;</code>
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o == null || !(o instanceof Bool))
			return false;
		Bool c = (Bool) o;
		return c.value == value;
	}

	/**
	 * Restituisce <code>value</code>.
	 */
	@Override
	public int hashCode()
	{
		return value ? 1 : 0;
	}

	@Override
	public Object evaluate(Object o)
	{
		return value;
	}

	@Override
	public boolean isBound()
	{
		return true;
	}

}
