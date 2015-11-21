package jcql.querytree.expression;

import jcql.querytree.Validator;
import jcql.visitor.Visitor;

/**
 * La classe {@link Constant} modella un nodo foglia di tipo costante.
 *
 * @author davide
 */
public class Constant extends Leaf
{
    private double value;

    /**
     * Crea un oggetto costante con valore <code>value</code>
     *
     * @param value Il valore da dare alla costante.
     */
    public Constant(double value)
    {
        if (value == -0)
            value = 0;
        this.value = value;
    }

    /**
     * Restituisce il valore della costante.
     *
     * @return Il valore della costante.
     */
    public double getValue()
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
        if (Validator.isInteger(value))
            return "" + (int) Math.round(value);
        else
            return "" + value;
    }

    /**
     * Restituisce <code>true</code> se e solo se:<br>
     * <code>((Constant)o).value == this.value;</code>
     */
    @Override
    public boolean equals(Object o)
    {
        if (o == null || !(o instanceof Constant))
            return false;
        Constant c = (Constant) o;
        return Math.abs(c.value - value) < 1e-8;
    }

    /**
     * Restituisce <code>value</code>.
     */
    @Override
    public int hashCode()
    {
        return (int) Double.doubleToLongBits(value);
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
