package jcql.querytree.common;

import jcql.visitor.Visitor;

import java.io.Serializable;

/**
 * La classe {@link QueryNode} e' la classe base della gerarchia che rappresenta un'espressione
 * aritmetica ed e' astratta.
 *
 * @author davide
 */
public abstract class QueryNode implements Serializable
{
    private QueryNode parent;

    /**
     * Restituisce l'operando sinistro dell'espressione.
     *
     * @return L'operando sinistro dell'espressione.
     */
    public QueryNode getLeft()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Imposta l'operando sinistro dell'espressione.
     *
     * @param left L'operando sinistro dell'espressione.
     */
    public void setLeft(QueryNode left)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Restituisce l'operando destro dell'espressione.
     *
     * @return L'operando destro dell'espressione.
     */
    public QueryNode getRight()
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Imposta l'operando sinistro dell'espressione.
     *
     * @param right L'operando sinistro dell'espressione.
     */
    public void setRight(QueryNode right)
    {
        throw new UnsupportedOperationException();
    }

    /**
     * Restituisce il genitore dell'espressione.
     *
     * @return Il genitore dell'espressione.
     */
    public QueryNode getParent()
    {
        return parent;
    }

    /**
     * Imposta il genitore dell'espressione.
     *
     * @param parent Il genitore dell'espressione.
     */
    public void setParent(QueryNode parent)
    {
        this.parent = parent;
    }

    /**
     * Metodo di utilit√† per consentire le visite. <br>
     * Da implementare nelle sottoclassi cos√¨:<br>
     * <p>
     * <pre>
     * public void accept(Visitor v)
     * {
     * 	v.visit(this);
     * }
     * </pre>
     *
     * @param v Il {@link Visitor} che deve visitare l'espressione.
     */
    public abstract void accept(Visitor v);

    /**
     * Valuta la query sull'oggetto <code>ctx</code>.
     *
     * @param ctx L'oggetto contesto della query.
     * @return Il risultato della query.
     */
    public abstract Object evaluate(Object ctx);

    /**
     * Restituisce <code>true</code> se l'albero Ë <i>bound</i> (ovvero se il valore di tutta
     * l'espressione puo' essere calcolato a priori in quanto costante). <br>
     * Esempio di espressioni bound:
     * <ul>
     * <li><code>3 + 2 > 3</code> (tutti i termini costanti)</li>
     * <li><code>this.a != 0 | 3 > 2</code> (vale sempre true quindi e' inutile valutare il ramo
     * sinistro)</li>
     * <li><code>this.a != 0 & 3 < 2</code> (vale sempre false quindi e' inutile valutare il ramo
     * sinistro)</li>
     * </ul>
     *
     * @return Se la query Ë <i>bound</i>.
     */
    public abstract boolean isBound();
}
