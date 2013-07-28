package jcql.parser;

import java.io.*;
import jcql.builder.*;
import jcql.lex.*;
import jcql.querytree.common.*;
import jcql.querytree.expression.*;
import jcql.querytree.logic.*;

/**
 * Parser che costruisce un'{@link QueryNode} a partire da un {@link QueryLexicalAnalyzer} in
 * notazione infissa.<br>
 * 
 * @author davide
 */
public class RecursiveDescentQueryParser implements QueryParser
{
	private QueryLexicalAnalyzer lex;
	private QueryBuilder builder;

	/**
	 * Costruisce un {@link RecursiveDescentQueryParser} a partire da un
	 * {@link QueryLexicalAnalyzer}.
	 * 
	 * @param lex
	 *            L'handler che restituisce i simboli.
	 */
	public RecursiveDescentQueryParser(QueryLexicalAnalyzer lex, QueryBuilder builder)
	{
		this.lex = lex;
		this.builder = builder;
	}

	private TwoOperandsLogicOperator relOp()
	{
		switch (lex.currentToken())
		{
			case GE:
				return new GeOperator();
			case GT:
				return new GtOperator();
			case LE:
				return new LeOperator();
			case LT:
				return new LtOperator();
			case EQUAL:
				return new EqOperator();
			case NEQUAL:
				return new NEqOperator();
			case MATCH:
				return new MatchOperator();
			default:
				throw new SyntaxErrorException("Found: " + lex.currentToken() + ", expected: OPERATOR");
		}
	}

	public QueryNode parseQuery() throws IOException
	{
		builder.reset();
		lex.nextToken();
		query();
		lex.accept(Token.EOF);
		return builder.getQuery();
	}

	private void query() throws IOException
	{
		orTerm();
		while (lex.currentToken() == Token.OR)
		{
			lex.accept(Token.OR);
			orTerm();
			builder.buildTwoOperandsLogicOperator(new OrOperator());
		}
	}

	private void orTerm() throws IOException
	{
		andTerm();
		while (lex.currentToken() == Token.AND)
		{
			lex.accept(Token.AND);
			andTerm();
			builder.buildTwoOperandsLogicOperator(new AndOperator());
		}
	}

	private void andTerm() throws IOException
	{
		if (lex.currentToken() == Token.NOT)
		{
			lex.accept(Token.NOT);
			andTerm();
			builder.buildSingleOperandsLogicOperator(new NotOperator());
		}
		else if (lex.currentToken() == Token.OPAR)
		{
			lex.accept(Token.OPAR);
			query();
			lex.accept(Token.CPAR);
		}
		else
		{
			expression();
			TwoOperandsLogicOperator relop = relOp();
			lex.nextToken();
			expression();
			builder.buildTwoOperandsLogicOperator(relop);
		}
	}

	private void expression() throws IOException
	{
		addTerm();
		while (lex.currentToken() == Token.ADD || lex.currentToken() == Token.SUB)
		{
			TwoOperandsLeafOperator addop = lex.currentToken() == Token.ADD ? new AddOperator() : new SubOperator();
			lex.accept(lex.currentToken());
			addTerm();
			builder.buildTwoOperandsLeafOperator(addop);
		}
	}

	private void addTerm() throws IOException
	{
		mulTerm();
		while (lex.currentToken() == Token.MUL || lex.currentToken() == Token.DIV || lex.currentToken() == Token.MOD)
		{
			TwoOperandsLeafOperator mulop = lex.currentToken() == Token.MUL ? new MulOperator() : lex.currentToken() == Token.DIV ? new DivOperator() : new ModOperator();
			lex.accept(lex.currentToken());
			mulTerm();
			builder.buildTwoOperandsLeafOperator(mulop);
		}
	}

	private void mulTerm() throws IOException
	{
		int signum = 1;
		switch (lex.currentToken())
		{
			case SUB:
				signum = -1; // negativo
			case ADD:
				// salto il segno in ogni caso
				lex.nextToken();
			case DOUBLE_CONSTANT:
			{
				builder.buildLeaf(new Constant(signum * lex.getValue()));
				lex.nextToken();
				break;
			}
			case STR:
			{
				lex.nextToken();
				lex.accept(Token.OPAR);
				expression();
				lex.accept(Token.CPAR);
				builder.buildSingleOperandsLeafOperator(new StrOperator());
				break;
			}
			case NUM:
			{
				lex.nextToken();
				lex.accept(Token.OPAR);
				expression();
				lex.accept(Token.CPAR);
				builder.buildSingleOperandsLeafOperator(new NumOperator());
				break;
			}
			case SIZE:
			{
				lex.nextToken();
				lex.accept(Token.OPAR);
				expression();
				lex.accept(Token.CPAR);
				builder.buildSingleOperandsLeafOperator(new SizeOperator());
				break;
			}
			case THIS:
			case ID:
			{
				value();
				break;
			}
			case OPAR:
			{
				lex.accept(Token.OPAR);
				expression();
				lex.accept(Token.CPAR);
				break;
			}
			case STRING_CONSTANT:
			{
				builder.buildLeaf(new StringConstant(lex.getString()));
				lex.nextToken();
				break;
			}
			case NULL:
			{
				builder.buildLeaf(new Null());
				lex.nextToken();
				break;
			}
			default:
				throw new SyntaxErrorException("Found: " + lex.currentToken() + ", expected: CONSTANT, VARIABLE OR (");
		}
	}

	private void value() throws IOException
	{
		firstValue();
		while (lex.currentToken() == Token.DOT)
		{
			lex.nextToken();
			otherValues();
		}
	}

	private void firstValue() throws IOException
	{
		This t = new This();
		builder.buildLeaf(t);
		if (lex.currentToken() == Token.THIS)
			lex.nextToken();
		else if (lex.currentToken() == Token.ID)
		{
			FieldName s = new FieldName(lex.getString());
			lex.accept(Token.ID);
			builder.buildLeaf(s);
			builder.buildTwoOperandsLeafOperator(new ExtractFieldOperator());
		}
		else
			throw new SyntaxErrorException("Found: " + lex.currentToken() + ", expected: THIS OR ID");
		while (lex.currentToken() == Token.OSPAR)
		{
			lex.nextToken();
			expression();
			lex.accept(Token.CSPAR);
			builder.buildTwoOperandsLeafOperator(new ArrayIndexOperator());
		}
	}

	private void otherValues() throws IOException
	{
		FieldName s = new FieldName(lex.getString());
		lex.accept(Token.ID);
		builder.buildLeaf(s);
		builder.buildTwoOperandsLeafOperator(new ExtractFieldOperator());
		while (lex.currentToken() == Token.OSPAR)
		{
			lex.nextToken();
			expression();
			lex.accept(Token.CSPAR);
			builder.buildTwoOperandsLeafOperator(new ArrayIndexOperator());
		}
	}

	/**
	 * Metodo di comodo che crea parser e texthandler e restituisce direttamente l'espressione.
	 * 
	 * @param s
	 *            La stringa di cui fare il parsing.
	 * @return L'espressione corrispondente alla stringa.
	 * @throws InvalidFieldException
	 *             Se il campo specificato non esiste.
	 * @throws InvalidOperationException
	 *             Se l'operazione specificata non è applicabile ai tipi dei campi.
	 * @throws ThisNotValidException
	 *             Se non si può usare this (elementi non wrapper o stringhe).
	 */
	public static QueryNode parseQuery(String s)
	{
		try
		{
			return new RecursiveDescentQueryParser(new ConcreteQueryLexicalAnalyzer(s), new RPNQueryBuilder()).parseQuery();
		}
		catch (IOException e)
		{
			return null;
		}
	}
}
