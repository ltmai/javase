import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Person implements Serializable {
	/**
	 * There should be only one THE_BOSS
	 */
	public static final Person THE_BOSS = new Person ("Big", "Boss", 99);

	private static final long serialVersionUID = -7868888961830644188L;

	private String firstname;

	private String lastname;

	/**
	 * transient tells Java to ignore this field during serialization
	 */
	transient private int age;

	public Person(String firstname, String lastname, int age) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
	}

	/**
	 * When THE_BOSS is serialized and then derialized, there will be a copy of it,
	 * and therefore violate the Invariant there is only a single instance THE_BOSS.
	 * 
	 * If exists Java calls the public Object readResolve() during deserialization,
	 * where the Object is first deserialized, and then Java calls readResolve()
	 * with the deserialized object and takes the returned object as the result of
	 * deserialization.
	 * 
	 * To restore the transient fullname, readResolve() should return:
	 * new Person(firstname, lastname, age);
	 * where fullname is initialized inside the constructor. 
	 */
	public Object readResolve() {
		if (firstname.equals (THE_BOSS.firstname) && lastname.equals (THE_BOSS.lastname)) {
			return THE_BOSS;
		}
	    return this;
	}

	/**
	 * custom serializing method
	 * @param s
	 * @throws IOException
	 */
	private void writeObject(ObjectOutputStream s) throws IOException {
		s.writeUTF (firstname);
		s.writeUTF (lastname);
		/**
		 * transient member is not serialized by default
		 * but we can still do it in a custom implementation
		 */
		s.write(age);
	}

	/**
	 * custom deserialize method
	 * @param s
	 * @throws IOException
	 */
	private void readObject(ObjectInputStream s) throws IOException {
		firstname = s.readUTF ();
		lastname = s.readUTF ();
		age = s.read();
	}	

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return firstname + " " + lastname + ", " + age + " years old, id= " + serialVersionUID;
	}

}
