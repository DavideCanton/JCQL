package jcql.querytree.common;

/**
 * La classe {@link Operator} modella il concetto di operando (binario).
 *
 * @author davide
 */
public abstract class Operator extends QueryNode
{
    protected QueryNode left, right;
    private String symbol;

    /**
     * Costruisce un {@link Operator} con simbolo <code>s</code>
     *
     * @param s Il simbolo.
     */
    protected Operator(String s)
    {
        symbol = s;
    }

    /**
     * Restituisce il simbolo dell'operatore.
     *
     * @return Il simbolo dell'operatore.
     */
    public String getSymbol()
    {
        return symbol;
    }

    /**
     * {@inheritDoc}
     */
    public QueryNode getLeft()
    {
        return left;
    }

    /**
     * Imposta l'espressione <code>left</code> come figlio sinistro di questo nodo (e aggiorna il
     * valore del campo <code>parent</code> di <code>left</code>).
     *
     * @param left L'espressione da legare come figlio sinistro.
     */
    public void setLeft(QueryNode left)
    {
        this.left = left;
        if (left != null)
            left.setParent(this);
    }

    /**
     * {@inheritDoc}
     */
    public QueryNode getRight()
    {
        return right;
    }

    /**
     * Imposta l'espressione <code>right</code> come figlio destro di questo nodo (e aggiorna il
     * valore del campo <code>parent</code> di <code>right</code>).
     *
     * @param right L'espressione da legare come figlio destro.
     */
    public void setRight(QueryNode right)
    {
        this.right = right;
        if (right != null)
            right.setParent(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBound()
    {
        // bound solo se sono bound i figli
        return left.isBound() && (right == null || right.isBound());
    }

    /**
     * Rimpiazza il figlio <code>old</code> con il figlio <code>new_son</code>
     *
     * @param old     Il figlio da rimpiazzare.
     * @param new_son Il figlio con cui sostituire.
     */
    public void replaceSon(QueryNode old, QueryNode new_son)
    {
        if (getLeft() == old)
            setLeft(new_son);
        else if (getRight() == old)
            setRight(new_son);
    }
}
