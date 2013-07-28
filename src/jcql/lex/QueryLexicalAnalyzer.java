package jcql.lex;

import java.io.*;

/**
 * Gli oggetti di tipo {@link QueryLexicalAnalyzer} effettuano la tokenizzazione di uno stream e
 * restituiscono dei <b>simboli</b> ovvero un valore del tipo enumerato
 * {@link Token}. Per ottenere il valore letto, se il {@link Token} è di tipo
 * <code>{@link Token#DOUBLE_CONSTANT}</code> si invoca <code>getValue()</code>
 * altrimenti si invoca <code>getString()</code>
 * 
 * @author davide
 */
public interface QueryLexicalAnalyzer
{
	/**
	 * Legge un simbolo.
	 *  
	 * @throws IOException
	 *             Se la lettura fallisce.
	 */
	public void nextToken() throws IOException;
	
	/**
	 * Restituisce il simbolo corrente.
	 * @return Il simbolo corrente.
	 */
	public Token currentToken();

	/**
	 * Restituisce la stringa letta. (Questo metodo puo essere usato anche
	 * per leggere un token non valido se il tipo e {@link Token#INVALID}).
	 * 
	 * @return La stringa letta dallo stream.
	 */
	public String getString();

	/**
	 * Il valore double letto se il tipo restituito da nextSymbol e
	 * {@link Token#DOUBLE_CONSTANT}.
	 * 
	 * @return Il valore letto dallo stream.
	 */
	public double getValue();
	
	/**
	 * Accetta il simbolo s.
	 * @param s Il simbolo s.
	 * @throws IOException 
	 */
	public void accept(Token s) throws IOException;
}
