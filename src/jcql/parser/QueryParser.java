package jcql.parser;

import jcql.querytree.common.QueryNode;

import java.io.IOException;

/**
 * Interfaccia che modella un oggetto in grado di creare da una sorgente di input un'
 * {@link QueryNode}.
 *
 * @author davide
 */
public interface QueryParser
{
    /**
     * Costruisce e restituisce un {@link QueryNode}.
     *
     * @return Un oggetto di tipo {@link QueryNode}.
     * @throws IOException          Se ci sono problemi nella lettura.
     * @throws SyntaxErrorException Se l'espressione è malformata.
     */
    QueryNode parseQuery() throws IOException;
}
