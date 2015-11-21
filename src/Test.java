import jcql.JCQLQuery;
import jcql.visitor.PrinterVisitor;
import jcql.visitor.XMLVisitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        System.out.println(l);

        System.out.println("Res");
        JCQLQuery q = new JCQLQuery("figli > 1 & figli < 2");
        System.out.println(q);
        PrinterVisitor v = new XMLVisitor();
        v.print(q);

        Set<Entry> s = new HashSet<>();
        q.evaluate(l, s);

        System.out.println(s);
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
