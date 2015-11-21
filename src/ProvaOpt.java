import jcql.JCQLQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProvaOpt
{
    public static void main(String[] args)
    {
        JCQLQuery q = new JCQLQuery("this > 2 | this + 1 < 5 & 1 < 2");
        System.out.println(q);

        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> a = (List<Integer>) q.evaluate(input, new ArrayList<>());
        System.out.println(a);
    }
}
