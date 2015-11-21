package jcql.querytree.common;

/**
 * Eccezione sollevata se è stato specificato un campo inesistente.
 *
 * @author davide
 */
public class InvalidFieldException extends RuntimeException
{
    /**
     * Costruisce una {@link InvalidFieldException}.
     */
    public InvalidFieldException()
    {
        super();
    }

    /**
     * Costruisce una {@link InvalidFieldException} con messaggio s.
     *
     * @param s Il messaggio dell'eccezione.
     */
    public InvalidFieldException(String s)
    {
        super(s);
    }

    /**
     * Costruisce una {@link InvalidFieldException} con causa t.
     *
     * @param t La causa dell'eccezione.
     */
    public InvalidFieldException(Throwable t)
    {
        super(t);
    }
}
