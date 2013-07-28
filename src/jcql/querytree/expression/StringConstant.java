package jcql.querytree.expression;

import jcql.visitor.*;

/**
 * La classe {@link StringConstant} modella un nodo foglia di tipo costante.
 * 
 * @author davide
 */
public class StringConstant extends Leaf
{
	private String value;

	/**
	 * Crea un oggetto costante con valore <code>value</code>
	 * 
	 * @param value
	 *            Il valore da dare alla costante.
	 */
	public StringConstant(String value)
	{
		this.value = value;
	}

	/**
	 * Restituisce il valore della costante.
	 * 
	 * @return Il valore della costante.
	 */
	public String getValue()
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
		return "'" + value + "'";
	}

	/**
	 * Restituisce <code>true</code> se e solo se:<br>
	 * <code>((Constant)o).value == this.value;</code>
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o == null || !(o instanceof StringConstant))
			return false;
		StringConstant c = (StringConstant) o;
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
		return true;
	}
}
