package jcql.querytree.expression;

import jcql.querytree.Validator;
import jcql.querytree.common.InvalidOperationException;
import jcql.visitor.Visitor;

import java.lang.reflect.Array;
import java.util.List;

public class ArrayIndexOperator extends TwoOperandsLeafOperator
{
    public ArrayIndexOperator()
    {
        super("[]");
    }

    @Override
    protected boolean commutative()
    {
        return false;
    }

    @Override
    public String toString()
    {
        return getLeft().toString() + "[" + getRight().toString() + "]";
    }

    @Override
    public void accept(Visitor v)
    {
        v.visit(this);
    }

    @Override
    public Object evaluate(Object o)
    {
        Object o1 = getLeft().evaluate(o);
        Object o2 = getRight().evaluate(o);
        Validator.indexable(getSymbol(), o1);
        Validator.number(getSymbol(), o2);
        double d = ((Number) o2).doubleValue();
        if (!Validator.isInteger(d))
            throw new InvalidOperationException("Index not integer");
        int i = (int) Math.round(d);
        if (i < 0)
            i += Array.getLength(o1);
        if (Validator.array(o1))
            return Array.get(o1, i);
        else if (o1 instanceof List)
            return ((List) o1).get(i);
        else
            return String.valueOf(o.toString().charAt(i));
    }
}
