package jcql.querytree.common;

/**
 * Eccezione sollevata se è stato specificato un metodo inesistente.
 *
 * @author davide
 */
public class InvalidMethodException extends RuntimeException
{

    /**
     * Costruisce una {@link InvalidMethodException}.
     */
    public InvalidMethodException()
    {
        super();
    }

    /**
     * Costruisce una {@link InvalidMethodException} con messaggio s.
     *
     * @param s Il messaggio dell'eccezione.
     */
    public InvalidMethodException(String s)
    {
        super(s);
    }

    /**
     * Costruisce una {@link InvalidMethodException} con causa t.
     *
     * @param t La causa dell'eccezione.
     */
    public InvalidMethodException(Throwable t)
    {
        super(t);
    }

}
