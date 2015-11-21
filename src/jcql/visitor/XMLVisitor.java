package jcql.visitor;

import jcql.JCQLQuery;
import jcql.querytree.common.Operator;
import jcql.querytree.expression.*;
import jcql.querytree.logic.ComparisonOperator;
import jcql.querytree.logic.LogicOperator;

import java.io.OutputStream;
import java.io.Writer;

public class XMLVisitor extends PrinterVisitor
{
    private int indent = 0;

    public XMLVisitor()
    {
        super();
    }

    public XMLVisitor(Writer out)
    {
        super(out);
    }

    public XMLVisitor(OutputStream out)
    {
        super(out);
    }

    @Override
    public void visit(Leaf f)
    {
        for (int i = 0; i < indent; i++)
            out.print('\t');
        if (f instanceof Constant)
            out.println("<constant value=\"" + ((Constant) f).getValue() + "\"/>");
        else if (f instanceof StringConstant)
            out.println("<string_constant value=\"" + ((StringConstant) f).getValue() + "\"/>");
        else if (f instanceof Null)
            out.println("<null/>");
        else if (f instanceof This)
            out.println("<this/>");
    }

    @Override
    public void visit(LeafOperator f)
    {
        visitOperator(f);
    }

    private void visitOperator(Operator f)
    {
        for (int i = 0; i < indent; i++)
            out.print('\t');
        indent++;
        String name = f.getClass().getSimpleName();
        int index = name.indexOf("Operator");
        name = name.substring(0, index).toLowerCase();
        out.println("<" + name + ">");
        f.getLeft().accept(this);
        if (f.getRight() != null)
            f.getRight().accept(this);
        indent--;
        for (int i = 0; i < indent; i++)
            out.print('\t');
        out.println("</" + name + ">");
    }

    @Override
    public void visit(LogicOperator f)
    {
        visitOperator(f);
    }

    @Override
    public void visit(ComparisonOperator f)
    {
        visitOperator(f);
    }

    /**
     * Stampa la {@link JCQLQuery} ricevuta.
     *
     * @param e La {@link JCQLQuery} da stampare.
     */
    @Override
    public void print(JCQLQuery e)
    {
        out.println("<query>");
        indent = 1;
        e.accept(this);
        out.println("</query>");
        out.flush();
    }

    public static void main(String[] args)
    {
        XMLVisitor x = new XMLVisitor();
        JCQLQuery q = new JCQLQuery("a[0].b > 0 & b < 0 | ! a == 0");
        System.out.println(q);
        x.print(q);
    }
}
