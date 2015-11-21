package jcql.parser;

/**
 * Eccezione sollevata dal parser se viene riscontrato un
 * errore di sintassi nell'input.
 *
 * @author davide
 */
public class SyntaxErrorException extends RuntimeException
{
    /**
     * Costruisce una {@link SyntaxErrorException}.
     */
    public SyntaxErrorException()
    {
        super();
    }

    /**
     * Costruisce una {@link SyntaxErrorException} con messaggio s.
     *
     * @param s Il messaggio dell'eccezione.
     */
    public SyntaxErrorException(String s)
    {
        super(s);
    }

    /**
     * Costruisce una {@link SyntaxErrorException} con causa t.
     *
     * @param t La causa dell'eccezione.
     */
    public SyntaxErrorException(Throwable t)
    {
        super(t);
    }
}
