package jcql.visitor;

import jcql.JCQLQuery;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Visitor per stampare un'espressione.
 *
 * @author davide
 */
public abstract class PrinterVisitor implements Visitor
{
    protected PrintWriter out;

    /**
     * Costruisce un {@link PrinterVisitor} che stampa su <code>System.out</code>.
     */
    public PrinterVisitor()
    {
        out = new PrintWriter(System.out);
    }

    /**
     * Costruisce un {@link PrinterVisitor} che stampa su <code>out</code>.
     *
     * @param out
     */
    public PrinterVisitor(Writer out)
    {
        this.out = new PrintWriter(out);
    }

    /**
     * Costruisce un {@link PrinterVisitor} che stampa su <code>out</code>.
     *
     * @param out
     */
    public PrinterVisitor(OutputStream out)
    {
        this.out = new PrintWriter(out);
    }

    /**
     * Stampa la {@link JCQLQuery} ricevuta.
     *
     * @param query La {@link JCQLQuery} da stampare.
     */
    public void print(JCQLQuery query)
    {
        query.accept(this);
        out.println();
        out.flush();
    }
}
