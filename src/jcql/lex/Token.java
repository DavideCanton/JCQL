package jcql.lex;

/**
 * Il tipo dei simboli letti dal parser.
 *
 * @author davide
 */
public enum Token
{
    ADD("+"), SUB("-"), MUL("*"), DIV("/"), MOD("%"), EOF, INVALID,
    THIS("this"), OPAR("("), CPAR(")"), AND("&"), OR("|"), MATCH("|="),
    EQUAL("="), GT(">"), GE(">="), LT("<"), LE("<="), NEQUAL("!="), NOT("!"),
    DOUBLE_CONSTANT, STRING_CONSTANT, ID, NULL("null"), STR("str"), NUM("num"),
    SIZE("size"), OSPAR("["), CSPAR("]"), DOT(".");

    private String symbol;

    Token()
    {
    }

    Token(String s)
    {
        this.symbol = s;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    @Override
    public String toString()
    {
        return (symbol != null ? symbol + " " : "") + "[" + name() + "]";
    }
}
