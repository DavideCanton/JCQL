import java.util.*;
import jcql.*;
import jcql.visitor.*;

public class Test
{
	private static class Entry
	{
		String cf;
		int eta;
		int figli;

		@Override
		public String toString()
		{
			return "Entry [cf=" + cf + ", eta=" + eta + ", figli=" + figli + "]";
		}

	}

	public static void main(String[] args)
	{
		List<Entry> l = new ArrayList<>();
		for (int i = 0; i < 20; i++)
			l.add(randomEntry());
		// for (Entry e : l)
		// System.out.println(e);

		System.out.println("Res");
		JCQLQuery q = new JCQLQuery("((a>1)|(a<2)&(a+1<-1))");
		System.out.println(q);
		PrinterVisitor v = new XMLVisitor();
		v.print(q);
		// List<Entry> res = (List<Entry>) q.evaluate(l, JCQLReturnType.ARRAYLIST);
		// for (Entry e : res)
		// System.out.println(e);
	}

	private static Entry randomEntry()
	{
		Entry e = new Entry();
		e.cf = Math.random() < 0.5 ? "a" : "ab";
		e.eta = (int) (Math.random() * 70 + 10);
		e.figli = (int) (Math.random() * 4);
		return e;
	}

}
