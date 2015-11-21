package jcql;

import jcql.optimize.Optimizer;
import jcql.parser.RecursiveDescentQueryParser;
import jcql.querytree.common.InvalidFieldException;
import jcql.querytree.common.InvalidOperationException;
import jcql.querytree.common.QueryNode;
import jcql.visitor.Visitor;

import java.io.Serializable;
import java.util.Collection;

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
     * @param c La collezione.
     * @param t La collezione contenente i risultati.
     * @return Il risultato della query.
     * @throws InvalidFieldException     Se il campo specificato non esiste.
     * @throws InvalidOperationException Se l'operazione specificata non e' applicabile ai tipi dei campi.
     * @throws ArithmeticException       Se si divide per zero.
     */
    public <T> Collection<T> evaluate(Collection<? extends T> c, Collection<T> t)
    {
        c.stream().filter(x -> (Boolean) query.evaluate(x)).forEach(t::add);
        return t;
    }

    /**
     * Metodo di utilita. (N.B. se si deve effettuare la stessa query piu' volte usare
     * {@link evaluate}.
     *
     * @param c    La collezione.
     * @param expr La query.
     * @return Il risultato.
     */
    public static <T> Collection<T> query(Collection<? extends T> c, String expr, Collection<T> t)
    {
        return new JCQLQuery(expr).evaluate(c, t);
    }

    @Override
    public String toString()
    {
        return query.toString();
    }

    /**
     * Effettua la visita della struttura da parte del Visitor <code>v</code>.
     *
     * @param v Il Visitor.
     */
    public void accept(Visitor v)
    {
        query.accept(v);
    }
}
