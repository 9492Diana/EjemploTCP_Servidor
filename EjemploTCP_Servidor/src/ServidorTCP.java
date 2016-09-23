
public class ServidorTCP {
	
	static Comunicacion com;

	public static void main(String[] args) {
		com = new Comunicacion(5000);
		com.start();
	}

}
