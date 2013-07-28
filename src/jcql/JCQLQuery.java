package jcql;

import java.io.*;
import java.util.*;
import jcql.optimize.*;
import jcql.parser.*;
import jcql.querytree.common.*;
import jcql.visitor.*;

/**
 * Classe che si occupa di effettuare le query.<br>
 * 
 * @author davide
 */
public class JCQLQuery implements Serializable
{
	private QueryNode query;

	/**
	 * Crea la query a partire da una stringa e la ottimizza.
	 * 
	 * @param s
	 */
	public JCQLQuery(String s)
	{
		this(s, true);
	}

	/**
	 * Crea la query a partire da una stringa e la ottimizza se il parametro <code>opt</code> e'
	 * true.
	 * 
	 * @param s
	 */
	public JCQLQuery(String s, boolean opt)
	{
		this.query = RecursiveDescentQueryParser.parseQuery(s);
		if (opt)
			query = Optimizer.optimize(query);
	}

	/**
	 * Esegue la query sulla collezione <code>c</code>.
	 * 
	 * @param c
	 *            La collezione.
	 * @param type
	 *            Il tipo del risultato.
	 * @return Il risultato della query.
	 * @throws IOException
	 *             Se si verifica un'eccezione.
	 * @throws InvalidFieldException
	 *             Se il campo specificato non esiste.
	 * @throws InvalidOperationException
	 *             Se l'operazione specificata non e applicabile ai tipi dei campi.
	 * @throws ArithmeticException
	 *             Se si divide per zero.
	 */
	public <T> Object evaluate(Collection<? extends T> c, JCQLReturnType type)
	{
		Collection<T> t = null;
		switch (type)
		{
			case ARRAYLIST:
				t = new ArrayList<T>();
				break;
			case HASHSET:
				t = new HashSet<T>();
				break;
			case LINKEDLIST:
				t = new LinkedList<T>();
				break;
			case TREESET:
				t = new TreeSet<T>();
				break;
		}
		for (T x : c)
			if ((Boolean) query.evaluate(x))
				t.add(x);
		return t;
	}

	/**
	 * Metodo di utilita. (N.B. se si deve effettuare la stessa query piu volte usare
	 * {@link JCQLQuery.evaluate}.
	 * 
	 * @param c
	 *            La collezione.
	 * @param expr
	 *            La query.
	 * @param type
	 *            Il tipo del risultato
	 * @return Il risultato.
	 */
	public static <T> Object query(Collection<? extends T> c, String expr, JCQLReturnType type)
	{
		return new JCQLQuery(expr).evaluate(c, type);
	}

	@Override
	public String toString()
	{
		return query.toString();
	}

	/**
	 * Effettua la visita della struttura da parte del Visitor <code>v</code>.
	 * 
	 * @param v
	 *            Il Visitor.
	 */
	public void accept(Visitor v)
	{
		query.accept(v);
	}
}
