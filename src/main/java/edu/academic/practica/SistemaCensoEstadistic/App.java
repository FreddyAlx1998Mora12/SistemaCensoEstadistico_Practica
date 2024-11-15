package edu.academic.practica.SistemaCensoEstadistic;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import edu.academic.practica.models.Familia;
import edu.academic.practica.models.Generador;
import edu.academic.practica.service.FamiliaService;
import edu.academic.practica.tda.listas.MyLinkedList;

/**
 * Class main
 *
 */
public class App {
	public static final String BASE_URI = "http://localhost:8080/";
	public static String filePath = "src/main/resources/media/";

	public static void main(String[] args) throws IndexOutOfBoundsException, Exception {
		System.out.println("Hello World!");
//		testLinkDouble();
		App a = new App();
//		a.sistemaMain();
		
		// Levantar servidor
		try {
			final HttpServer server = startServer();
			System.out.println(String.format(
					"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
					BASE_URI));
			System.in.read();
			server.stop();
		} catch (Exception ex) {
			System.out.println("Error en el servidor" + ex);
		}
		
	}

//	Metodo para iniciar servidor
	public static HttpServer startServer() {

		final ResourceConfig rc = new ResourceConfig().packages("edu.academic.practica.models.rest");

		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
	}
	
//	metodo para verificar operaciones de la lista enlazada
	public static void testLinkDouble() throws IndexOutOfBoundsException, Exception {
		MyLinkedList<Integer> milist = new MyLinkedList<>();
		
		for (int i = 0; i < 5; i++) {
			milist.add(i);
		}
		
		System.out.println("Mi lista enlazada es: "+milist.toString());
		
		System.out.println("Elemento elegido; "+milist.get(3)+
				", elemento anterior "+milist.getNode(3).getPrev().getInfo()+
				", elemento siguiente "+milist.getNode(3).getNext().getInfo());
	}
	
//	metodo para testear lectura y escritura de archivo
	private void sistemaMain() {
		
		FamiliaService censoService = new FamiliaService();
		
		Familia f = new Familia(0,10,"Familia completa, comparte hogar con personas cercanas","Bolonia",false);
		Familia f1 = new Familia(0,5,"Familia de 5 integrantes, madre e hijos","TC",true);
		Familia f2 = new Familia(0,10,"vivienda compartida","Menfis",true);
		
		Generador g1 = new Generador(0, 20, 200, 690.50, "Gasolina") ;
		Generador g2 = new Generador(0, 40, 400, 895.00, "Diesel") ;
		
		f1.setGenerador(g1);
		f2.setGenerador(g2);
		
		
		try {
//			censoService.setFamilia(f);
//			censoService.guardarCenso();
			
			censoService.setFamilia(f1);
			censoService.guardarCenso();
			
			censoService.setFamilia(f2);
			censoService.guardarCenso();
			
			System.out.println(censoService.infoGenerador().toString());
		} catch (Exception e) {
			System.out.println("Ocurrio un error, error: "+e.getMessage());
		}
	}
	
	public static void writeFileList(String list, String filePath) throws IOException{
		StringBuilder sb = new StringBuilder();
		sb.append(filePath).append("ProyectoCenso.json");
		//	almacenamos en un archivo
		try (FileWriter writer = new FileWriter(sb.toString())) {
            writer.write(list);
            System.out.println("JSON guardado en: " + sb.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
