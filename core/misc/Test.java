class Test
{
	public class Param 
	{
		private String value;
		
		public void setValue(String v) { value = v; };
		public String getValue() { return value; };

		public Param() {
			value = "default";
		}
	}
	
	private static void doSomething(Param v)
	{
		v.setValue("new value");
	}
	
	public void test() 
	{
		Param p = new Param();
		p.setValue("old value");
		doSomething(p);
		System.out.println(p.getValue());        
	}
    
	public static void main (String[] args) throws java.lang.Exception
	{
			new Test().test();
	}
}