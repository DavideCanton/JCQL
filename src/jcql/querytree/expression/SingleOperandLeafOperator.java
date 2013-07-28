package jcql.querytree.expression;

import jcql.querytree.common.*;

public abstract class SingleOperandLeafOperator extends LeafOperator
{
	public SingleOperandLeafOperator(String s)
	{
		super(s);
	}

	@Override
	public void setRight(QueryNode right)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString()
	{
		return getSymbol() + " (" + getLeft().toString() + ")";
	}

	/**
	 * Restituisce <code>true</code> se e solo vale la seguente condizione:<br>
	 * Se <code>o.getClass() == getClass()</code> e l'operatore è commutativo allora controlla se i
	 * sottoalberi sono uguali o scambiati, mentre se l'operatore non è commutativo controlla solo
	 * se i sottoalberi sono uguali.
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (o.getClass() != getClass())
			return false;
		Operator or = (Operator) o;
		return left.equals(or.getLeft()) && right.equals(or.getRight());
	}

	/**
	 * Calcola il codice dell'operatore sulla base della seguente formula:<br>
	 * <code>getClass().hashCode() * 31 + left.hashCode() * (commutative() ? 13 : 17) + right.hashCode() *
	 * 13</code>
	 */
	@Override
	public int hashCode()
	{
		return getClass().hashCode() * 31 + left.hashCode() * 13;
	}
}
