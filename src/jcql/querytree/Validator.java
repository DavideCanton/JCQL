package jcql.querytree;

import java.util.*;
import jcql.querytree.common.*;

public class Validator
{
	public static void sizeable(Object o)
	{
		if (!(o instanceof String || o instanceof Collection || o instanceof Map || array(o)))
			throw new InvalidOperationException("Operator size not valid on this type.");
	}

	public static boolean array(Object target)
	{
		boolean ok = false;
		if (target instanceof char[])
			ok = true;
		else if (target instanceof int[])
			ok = true;
		else if (target instanceof byte[])
			ok = true;
		else if (target instanceof long[])
			ok = true;
		else if (target instanceof short[])
			ok = true;
		else if (target instanceof boolean[])
			ok = true;
		else if (target instanceof float[])
			ok = true;
		else if (target instanceof double[])
			ok = true;
		else if (target instanceof Object[])
			ok = true;
		return ok;
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
