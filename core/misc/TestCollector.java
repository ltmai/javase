import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

class TestCollector
{
	public class Item 
	{
        private String category;
        private String value;
        private String name;
        
        public void setCategory(String c) { category = c; };
		public String getCategory() { return category; };

		public void setValue(String v) { value = v; };
		public String getValue() { return value; };

		public void setName(String n) { name = n; };
		public String getName() { return name; };

        /**
         * constructor used by test1
         */
        public Item (String category, String value) 
        {
            this.category = category;
            this.value = value;
		}        
        /**
         * constructor used by test2
         */
        public Item (String category, String name, String value) 
        {
            this.category = category;
            this.value = value;
            this.name = name;
		}
	}

    /**
     * Process a list of items (category and value): 
     * - items are groupped by category
     * - items of the same category are collected into list
     * Result is a mapping from category to list of values
     */
    public void simpleGroupBy() 
    {
        List<Item> items = Arrays.asList(
                new Item("A", "Anna"),
                new Item("B", "Bob"),
                new Item("C", "Cathy"),
                new Item("A", "Alex"),
                new Item("B", "Brad"),
                new Item("C", "Chris"),
                new Item("A", "Andre"),
                new Item("B", "Bruce"),
                new Item("C", "Celine"),
                new Item("A", "Adele"),
                new Item("B", "Brandon"),
                new Item("C", "Collins")
                );
        Map<String, List<String>> result = items.stream().collect(
            Collectors.groupingBy(
                Item::getCategory, 
                Collectors.mapping(Item::getValue, Collectors.toList())
            )
        );
        
        result.forEach((category, list) -> {
            System.out.println("Category: " + category);
            list.stream().forEach(System.out::println);
        });
    }

    /**
     * Process a list of items (category, name, value):
     * - items are groupped by category
     * - items of the same category form a map of name to value
     * Result is a mapping from category to mapping from name to value
     */
    public void simpleGroupByMapping() 
    {
        List<Item> items = Arrays.asList(
                new Item("Alpha", "A1", "A1-Value"),
                new Item("Beta" , "B1", "B1-Value"),
                new Item("Gamma", "C1", "C1-Value"),
                new Item("Alpha", "A2", "A2-Value"),
                new Item("Beta" , "B2", "B2-Value"),
                new Item("Gamma", "C2", "C2-Value"),
                new Item("Alpha", "A3", "A3-Value"),
                new Item("Beta" , "B3", "B3-Value"),
                new Item("Gamma", "C3", "C3-Value"),
                new Item("Alpha", "A4", "A4-Value"),
                new Item("Beta" , "B4", "B4-Value"),
                new Item("Gamma", "C4", "C4-Value")
                );

        Map<String, Map<String, String>> result = items.stream().collect(
            Collectors.groupingBy(
                Item::getCategory, 
                Collectors.mapping(
                    Function.identity(), 
                    Collectors.toMap(Item::getName, Item::getValue)
                    )
            )
        );
        
        result.forEach((category, map) -> {
            System.out.println("Category: " + category);
            map.forEach((key, value) -> System.out.println(key + " -> " + value));
        });
    }    
    
	public static void main (String[] args) throws java.lang.Exception
	{
        //new TestCollector().simpleGroupBy();
        new TestCollector().simpleGroupByMapping();
	}
}