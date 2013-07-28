import java.util.*;
import jcql.*;

public class ProvaOpt
{
	public static void main(String[] args)
	{
		JCQLQuery q = new JCQLQuery("this > 2 | this + 1 < 5");
		System.out.println(q);
		System.out.println(q.evaluate(Arrays.asList(1, 2, 3, 4, 5), JCQLReturnType.ARRAYLIST));
	}
}
