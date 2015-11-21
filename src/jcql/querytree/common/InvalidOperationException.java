package jcql.querytree.common;

/**
 * Eccezione sollevata se è stata specificata un'operazione non valida.
 *
 * @author davide
 */
public class InvalidOperationException extends RuntimeException
{
    /**
     * Costruisce una {@link InvalidOperationException}.
     */
    public InvalidOperationException()
    {
        super();
    }

    /**
     * Costruisce una {@link InvalidOperationException} con messaggio s.
     *
     * @param s Il messaggio dell'eccezione.
     */
    public InvalidOperationException(String s)
    {
        super(s);
    }

    /**
     * Costruisce una {@link InvalidOperationException} con causa t.
     *
     * @param t La causa dell'eccezione.
     */
    public InvalidOperationException(Throwable t)
    {
        super(t);
    }
}
