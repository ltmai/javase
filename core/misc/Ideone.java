/* package whatever; // don't place package name! */
abstract class Abs 
{
	protected static String type;
	
	public void handle(String t) 
	{
	   System.out.println("Handling " + t);
		if (t.equals(type))
		{
			process(t);
		}
	}
	
	protected abstract void process(String t);
}

class Con1 extends Abs
{
	protected static String type = "CON1";
	
	protected void process(String t)
	{
		System.out.println("Processing " + t);
	}
}


class Con2 extends Abs
{
	protected static String type = "CON2";
	
	protected void process(String t)
	{
		System.out.println("Processing " + t);
	}
}
class Ideone
{
	public static void main (String[] args) throws java.lang.Exception
	{
		Abs con1 = new Con1();
		con1.handle("CON1");
	}
}