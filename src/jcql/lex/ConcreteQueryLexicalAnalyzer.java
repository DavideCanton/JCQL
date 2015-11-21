package jcql.lex;

import jcql.parser.SyntaxErrorException;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Classe concreta che implementa l'interfaccia {@link QueryLexicalAnalyzer} e separa in token una
 * stringa o uno stream testuale.
 *
 * @author davide
 */
public class ConcreteQueryLexicalAnalyzer implements QueryLexicalAnalyzer
{
    private StreamTokenizer stream;
    private Token symbol;
    private String sval;
    private StringTokenizer tok;

    /**
     * Costruisce un {@link ConcreteQueryLexicalAnalyzer} che legge da un {@link InputStream}
     * convertito a un {@link Reader}.
     *
     * @param i Lo stream da cui leggere.
     */
    public ConcreteQueryLexicalAnalyzer(InputStream i)
    {
        stream = new StreamTokenizer(new InputStreamReader(i));
        setup();
    }

    /**
     * Costruisce un {@link ConcreteQueryLexicalAnalyzer} che legge da un {@link Reader}.
     *
     * @param r Il Reader da cui leggere.
     */
    public ConcreteQueryLexicalAnalyzer(Reader r)
    {
        stream = new StreamTokenizer(r);
        setup();
    }

    /**
     * Costruisce un {@link ConcreteQueryLexicalAnalyzer} che legge da una stringa.
     *
     * @param s La stringa da cui leggere.
     */
    public ConcreteQueryLexicalAnalyzer(String s)
    {
        stream = new StreamTokenizer(new StringReader(s));
        setup();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString()
    {
        return sval;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue()
    {
        return Double.parseDouble(sval);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void nextToken() throws IOException
    {
        if (tok != null)
        {
            if (!tok.hasMoreTokens())
                tok = null;
            else
            {
                sval = tok.nextToken();
                parseSymbol();
                symbol.setSymbol(sval);
                return;
            }
        }
        int n = stream.nextToken();
        if (n == StreamTokenizer.TT_EOF)
            symbol = Token.EOF;
        else if (n == StreamTokenizer.TT_WORD)
        {
            sval = stream.sval;
            if (sval.equals("null"))
                symbol = Token.NULL;
            else if (sval.equals("str"))
                symbol = Token.STR;
            else if (sval.equals("num"))
                symbol = Token.NUM;
            else if (sval.equals("this"))
                symbol = Token.THIS;
            else if (sval.equals("size"))
                symbol = Token.SIZE;
            else if (isNumber(sval))
                symbol = Token.DOUBLE_CONSTANT;
            else
            {
                symbol = Token.ID;
                tok = new StringTokenizer(sval, "[].", true);
                sval = tok.nextToken();
                parseSymbol();
            }
            symbol.setSymbol(sval);
        }
        else if (n > 0)
        {
            sval = "" + (char) n;
            switch (n)
            {
                case '>':
                    if (stream.nextToken() == '=')
                    {
                        symbol = Token.GE;
                        sval = ">=";
                    }
                    else
                    {
                        stream.pushBack();
                        symbol = Token.GT;
                    }
                    break;
                case '<':
                    if (stream.nextToken() == '=')
                    {
                        symbol = Token.LE;
                        sval = "<=";
                    }
                    else
                    {
                        stream.pushBack();
                        symbol = Token.LT;
                    }
                    break;
                case '|':
                    if (stream.nextToken() == '=')
                    {
                        symbol = Token.MATCH;
                        sval = "|=";
                    }
                    else
                    {
                        stream.pushBack();
                        symbol = Token.OR;
                    }
                    break;
                case '=':
                    if (stream.nextToken() == '=')
                    {
                        symbol = Token.EQUAL;
                        sval = "==";
                    }
                    else
                    {
                        stream.pushBack();
                        symbol = Token.INVALID;
                        symbol.setSymbol(sval);
                    }
                    break;
                case '+':
                    symbol = Token.ADD;
                    break;
                case '-':
                    symbol = Token.SUB;
                    break;
                case '*':
                    symbol = Token.MUL;
                    break;
                case '/':
                    symbol = Token.DIV;
                    break;
                case '%':
                    symbol = Token.MOD;
                    break;
                case '(':
                    symbol = Token.OPAR;
                    break;
                case ')':
                    symbol = Token.CPAR;
                    break;
                case '&':
                    symbol = Token.AND;
                    break;
                case '!':
                    if (stream.nextToken() == '=')
                    {
                        symbol = Token.NEQUAL;
                        sval = "!=";
                    }
                    else
                    {
                        stream.pushBack();
                        symbol = Token.NOT;
                    }
                    break;
                case '\'':
                    symbol = Token.STRING_CONSTANT;
                    sval = stream.sval;
                    symbol.setSymbol(sval);
                    break;
                default:
                    symbol = Token.INVALID;
                    symbol.setSymbol(sval);
            }
        }
    }

    // ( .NUM+ | NUM+[.[NUM+]])
    private boolean isNumber(String s)
    {
        return s.matches("\\.\\d+|\\d+(\\.(\\d+)?)?");
    }

    @Override
    public Token currentToken()
    {
        return symbol;
    }

    @Override
    public String toString()
    {
        return stream.toString();
    }

    private void parseSymbol()
    {
        if (sval.equals("null"))
            symbol = Token.NULL;
        else if (sval.equals("str"))
            symbol = Token.STR;
        else if (sval.equals("num"))
            symbol = Token.NUM;
        else if (sval.equals("this"))
            symbol = Token.THIS;
        else if (sval.equals("."))
            symbol = Token.DOT;
        else if (sval.equals("size"))
            symbol = Token.SIZE;
        else if (sval.equals("["))
            symbol = Token.OSPAR;
        else if (sval.equals("]"))
            symbol = Token.CSPAR;
        else if (isNumber(sval))
            symbol = Token.DOUBLE_CONSTANT;
        else
            symbol = Token.ID;
    }

    private void setup()
    {
        stream.resetSyntax();
        stream.eolIsSignificant(false);
        stream.whitespaceChars(0, ' '); // caratteri con codici da 0 a ' '
        stream.wordChars('0', '9'); // 0..9
        stream.wordChars('a', 'z');
        stream.wordChars('.', '.');
        stream.wordChars('A', 'Z');
        stream.wordChars('_', '_');
        stream.wordChars('[', '[');
        stream.wordChars(']', ']');
        stream.quoteChar('\'');
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void accept(Token s) throws IOException
    {
        if (symbol != s)
            throw new SyntaxErrorException("Found: " + symbol + " instead of " + s);
        nextToken();
    }

    public static void main(String[] args) throws IOException
    {
        String q = "a[0] |= 0.2";
        ConcreteQueryLexicalAnalyzer l = new ConcreteQueryLexicalAnalyzer(q);
        l.nextToken();
        while (l.currentToken() != Token.EOF)
        {
            System.out.print(l.currentToken() + " ");
            l.nextToken();
        }
    }
}
