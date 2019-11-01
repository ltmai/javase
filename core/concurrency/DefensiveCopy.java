import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This example shows how to protect your class from calling code to change 
 * your data, you should return only a copy of your data to the calling code.
 * This example also demonstrates the use of Collections.unmodifiableList()
 */
public class DefensiveCopy {
	private List<String> data = new ArrayList<>();

	private List<String> getData() {
		return Collections.unmodifiableList(data);
	}

	public void add(String str) {
		data.add(str);
	}

	public static void main(String[] args) {
		DefensiveCopy dc = new DefensiveCopy();
		dc.add("one");
		dc.add("two");
		dc.add("three");
		List<String> unmodifiableData = dc.getData();
		unmodifiableData.stream().forEach(System.out::println);
		
		try {
			/* try modifying it */
			unmodifiableData.add("four");
		} catch (Exception e) {
			System.out.println("Exception in modifying return data");
			e.printStackTrace();
		}
	}
}
