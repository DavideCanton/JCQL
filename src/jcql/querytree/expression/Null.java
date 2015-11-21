package jcql.querytree.expression;

import jcql.visitor.Visitor;

public class Null extends Leaf
{
    @Override
    public void accept(Visitor v)
    {
        v.visit(this);
    }

    @Override
    public String toString()
    {
        return "null";
    }

    @Override
    public Object evaluate(Object o)
    {
        return null;
    }

    @Override
    public boolean isBound()
    {
        return true;
    }
}
