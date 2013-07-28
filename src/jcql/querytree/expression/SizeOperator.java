package jcql.querytree.expression;

import java.lang.reflect.*;
import java.util.*;
import jcql.querytree.*;
import jcql.visitor.*;

public class SizeOperator extends SingleOperandLeafOperator
{
	public SizeOperator()
	{
		super("size");
	}

	public void accept(Visitor v)
	{
		v.visit(this);
	}

	@Override
	public Object evaluate(Object o)
	{
		o = getLeft().evaluate(o);
		Validator.sizeable(o);
		if (o instanceof String)
			return ((String) o).length();
		if (o instanceof Collection)
			return ((Collection) o).size();
		if (o instanceof Map)
			return ((Map) o).size();
		return Array.getLength(o);
	}
}
