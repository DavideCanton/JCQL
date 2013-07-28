package jcql.querytree.expression;

import jcql.visitor.*;

public class FieldName extends Leaf
{
	private String value;

	public FieldName(String value)
	{
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

	@Override
	public void accept(Visitor v)
	{
		v.visit(this);
	}

	@Override
	public String toString()
	{
		return value;
	}

	/**
	 * Restituisce <code>true</code> se e solo se:<br>
	 * <code>((Constant)o).value == this.value;</code>
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o == null || !(o instanceof FieldName))
			return false;
		FieldName c = (FieldName) o;
		return c.value.equals(value);
	}

	/**
	 * Restituisce <code>value</code>.
	 */
	@Override
	public int hashCode()
	{
		return value.hashCode();
	}

	@Override
	public Object evaluate(Object o)
	{
		return value;
	}

	@Override
	public boolean isBound()
	{
		return false;
	}
}
