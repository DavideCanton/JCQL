package jcql.visitor;

import jcql.*;
import jcql.querytree.common.*;
import jcql.querytree.expression.*;
import jcql.querytree.logic.*;

public class TreeVisitor extends PrinterVisitor
{
	private int indent = 0;
	private static final int INC = 2;

	@Override
	public void visit(Leaf f)
	{
		visit0(f);
	}

	@Override
	public void visit(LeafOperator f)
	{
		visit0(f);
	}

	@Override
	public void visit(LogicOperator f)
	{
		visit0(f);
	}

	@Override
	public void visit(ComparisonOperator f)
	{
		visit0(f);
	}

	private void visit0(QueryNode f)
	{
		indent += INC;
		if (!(f instanceof Leaf) && f.getRight() != null)
			f.getRight().accept(this);
		indent -= INC;

		for (int i = 0; i < indent; i++)
			out.print('\t');

		String name = f.getClass().getSimpleName();
		out.printf("(%s)\n", name);

		indent += INC;
		if (!(f instanceof Leaf))
			f.getLeft().accept(this);
		indent -= INC;
	}

	public static void main(String[] args)
	{
		TreeVisitor x = new TreeVisitor();
		JCQLQuery q = new JCQLQuery("a[0].b > 0 & b < 0 | ! a == 0");
		System.out.println(q);
		x.print(q);
	}
}
