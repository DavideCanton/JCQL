package jcql.optimize;

import jcql.querytree.common.*;
import jcql.querytree.expression.*;
import jcql.querytree.logic.*;

/**
 * Classe che si occupa dell'ottimizzazione delle query.
 * 
 * @author davide
 */
public class Optimizer
{
	/**
	 * Ottimizza la query e restituisce una equivalente.
	 * 
	 * @param query
	 *            La query da ottimizzare.
	 * @return La query ottimizzata.
	 */
	public static QueryNode optimize(QueryNode query)
	{
		// se e' una foglia non c'e' niente da ottimizzare
		if (query instanceof Leaf)
			return query;

		// recursive optimization
		if (query.getLeft() != null)
			optimize(query.getLeft());
		if (query.getRight() != null)
			optimize(query.getRight());

		// se e' un operatore unario
		if (query.getRight() == null)
		{
			/*
			 * se il figlio e' bound rimpiazza l'operatore con la valutazione del figlio
			 */
			if (query.getLeft().isBound())
			{
				Object res = query.evaluate(null);
				QueryNode n = make(res);
				if (query.getParent() != null)
					((Operator) query.getParent()).replaceSon(query, n);
				return n;
			}
		}
		else if (query.getLeft().isBound() && query.getRight().isBound())
		{
			/*
			 * Entrambi i figli sono bound
			 */
			Object res = query.evaluate(null);
			QueryNode n = make(res);
			if (query.getParent() != null)
				((Operator) query.getParent()).replaceSon(query, n);
			return n;
		}
		else if (query instanceof OrOperator)
		{
			// se e' un or e uno dei figli e' true allora tutto l'albero e' true, se invece e' false
			// si puo' rimpiazzare l'operatore col figlio
			if (query.getLeft().isBound())
			{
				boolean val = (Boolean) query.getLeft().evaluate(null);
				if (val)
					return new Bool(true);
				else if (query.getParent() != null)
					((Operator) query.getParent()).replaceSon(query, query.getRight());
				else
					return query.getRight();
			}
			if (query.getRight().isBound())
			{
				boolean val = (Boolean) query.getRight().evaluate(null);
				if (val)
					return new Bool(true);
				else if (query.getParent() != null)
					((Operator) query.getParent()).replaceSon(query, query.getLeft());
				else
					return query.getLeft();
			}
		}
		else if (query instanceof AndOperator)
		{
			// se e' un and e uno dei figli e' false allora tutto l'albero e' false, se invece e'
			// true si puo' rimpiazzare l'operatore col figlio
			if (query.getLeft().isBound())
			{
				boolean val = (Boolean) query.getLeft().evaluate(null);
				if (!val)
					return new Bool(false);
				else if (query.getParent() != null)
					((Operator) query.getParent()).replaceSon(query, query.getRight());
				else
					return query.getRight();
			}
			if (query.getRight().isBound())
			{
				boolean val = (Boolean) query.getRight().evaluate(null);
				if (!val)
					return new Bool(false);
				else if (query.getParent() != null)
					((Operator) query.getParent()).replaceSon(query, query.getLeft());
				else
					return query.getLeft();
			}
		}
		return query;
	}

	private static QueryNode make(Object res)
	{
		if (res == null)
			return new Null();
		else if (res instanceof Number)
			return new Constant(((Number) res).doubleValue());
		else if (res instanceof String)
			return new StringConstant(res.toString());
		else if (res instanceof Boolean)
			return new Bool((Boolean) res);
		return null;
	}

}
