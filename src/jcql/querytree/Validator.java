package jcql.querytree;

import jcql.querytree.common.InvalidOperationException;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Validator
{
    public static void sizeable(Object o)
    {
        if (!(o instanceof String || o instanceof Collection || o instanceof Map || array(o)))
            throw new InvalidOperationException("Operator size not valid on this type.");
    }

    public static boolean array(Object target)
    {
        return (target instanceof char[] ||
                target instanceof int[] ||
                target instanceof byte[] ||
                target instanceof long[] ||
                target instanceof short[] ||
                target instanceof boolean[] ||
                target instanceof float[] ||
                target instanceof double[] ||
                target instanceof Object[]);
    }

    public static void number(String symbol, Object o1)
    {
        if (!(o1 instanceof Number))
            throw new InvalidOperationException("Operator " + symbol + " not valid on non-numeric types.");
    }

    public static void string(String symbol, Object o1)
    {
        if (!(o1 instanceof String))
            throw new InvalidOperationException("Operator " + symbol + " not valid on non-String types.");
    }

    public static void indexable(String symbol, Object o)
    {
        if (!array(o) && !(o instanceof List) && !(o instanceof String))
            throw new InvalidOperationException("Operator " + symbol + " not valid on non-array, non-list or non-string types.");
    }

    public static boolean isInteger(double value)
    {
        return Math.abs(value - Math.round(value)) < 1E-14;
    }
}
