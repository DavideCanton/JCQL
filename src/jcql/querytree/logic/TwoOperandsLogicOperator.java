package jcql.querytree.logic;

import jcql.querytree.common.*;

public abstract class TwoOperandsLogicOperator extends LogicOperator
{
	public TwoOperandsLogicOperator(String string)
	{
		super(string);
	}

	@Override
	public String toString()
	{
		return "(" + getLeft().toString() + " " + getSymbol() + " " + getRight().toString() + ")";
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
		if (!commutative())
			return left.equals(or.getLeft()) && right.equals(or.getRight());
		return left.equals(or.getLeft()) && right.equals(or.getRight()) || left.equals(or.getRight()) && right.equals(or.getLeft());
	}

	/**
	 * Calcola il codice dell'operatore sulla base della seguente formula:<br>
	 * <code>getClass().hashCode() * 31 + left.hashCode() * (commutative() ? 13 : 17) + right.hashCode() *
	 * 13</code>
	 */
	@Override
	public int hashCode()
	{
		return getClass().hashCode() * 31 + left.hashCode() * (commutative() ? 13 : 17) + right.hashCode() * 13;
	}

	/**
	 * Restituisce <code>true</code> se e solo se l'operatore è commutativo, ovvero l'ordine degli
	 * operandi non conta ai fini del risultato.
	 * 
	 * @return <code>true</code> se l'operando è commutativo, <code>false</code> altrimenti.
	 */
	protected abstract boolean commutative();
}
