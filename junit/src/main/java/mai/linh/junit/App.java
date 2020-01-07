package mai.linh.junit;

public class App {

	private Service service;

	public String replaceAt(String s, String r, int i) {
		return service.replaceAt(s, r, i);
	}

	public String toUpper(String s) {
		return service.toUpper(s);
	}

	public App(Service service) {
		this.service = service;
	}

	public static void main(String[] args) {
	}
}
