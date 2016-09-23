import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Comunicacion extends Thread {

	int puerto;
	ServerSocket ss;
	Socket cliente;
	public Comunicacion(int i) {
		try {
			ss = new ServerSocket(i);
			cliente = ss.accept();
			System.out.println("Conectado exitosamente :)");
		} catch (IOException e) {			
			e.printStackTrace();
		}		
	}
	
	@Override
	public void run() {
		while (true) {
			//System.out.print("voy a recibir aviso ................ ");
			recibirAvisos();
			//System.out.println("listo");
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
		
	}

	private void recibirAvisos() {
		InputStream entradaBytes;
		DataInputStream entradaDatos;
		try {
			entradaBytes = cliente.getInputStream();
			entradaDatos = new DataInputStream(entradaBytes);
			//System.out.println(" ........... ** ");
			String aviso = entradaDatos.readUTF();
			//System.out.println("aviso recibido:  " + aviso);
			if(aviso.contains("texto")){
				//System.out.println("aviso texto");
				String text = entradaDatos.readUTF();
				System.out.println("texto recibido: " + text);
			}else if(aviso.contains("objeto")){
				//System.out.println("aviso objeto");
				recibirObjeto();				
			}
		} catch (IOException e) {			
			e.printStackTrace();
		}			
	}

	private void recibirObjeto() {
		//System.out.println("recibiendo objecto" );
		InputStream entradaBytes;
		ObjectInputStream entradaObjetos;
		try {
			entradaBytes = cliente.getInputStream();
			entradaObjetos = new ObjectInputStream(entradaBytes);
			String temp = (String)entradaObjetos.readObject();
			System.out.println("Objeto recibido: " + temp);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {			
			e.printStackTrace();
		}
	}

}
