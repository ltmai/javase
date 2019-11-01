import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TestSerialization {

	public static void main(String[] args) {
		Person alice = new Person("Alice", "Goodman", 5);

		try (FileOutputStream fos = new FileOutputStream ("dummy.ser");
				ObjectOutputStream oos = new ObjectOutputStream (fos)) 
		{
			System.out.println("Serialized: " + alice.toString());
			oos.writeObject (alice);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (FileInputStream fis = new FileInputStream ("dummy.ser");
				ObjectInputStream ois = new ObjectInputStream (fis)) {
			final Person p = (Person) ois.readObject ();
			System.out.println("Deserialized: " + p.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
