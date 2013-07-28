package jcql.visitor;

import jcql.querytree.expression.*;
import jcql.querytree.logic.*;

/**
 * Visitor per visitare un'espressione.
 * 
 * @author davide
 */
public interface Visitor
{
	/**
	 * Visita una foglia (Campo, Costante o This).
	 * 
	 * @param f La foglia.
	 */
	void visit(Leaf f);

	/**
	 * Visita un operatore numerico (aritmetico)
	 * @param f L'operatore.
	 */
	void visit(LeafOperator f);

	/**
	 * Visita un operatore logico (booleano)
	 * @param f L'operatore.
	 */
	void visit(LogicOperator f);

	/**
	 * Visita un operatore di confronto (tra numeri o stringhe)
	 * @param f L'operatore.
	 */
	void visit(ComparisonOperator f);
}

