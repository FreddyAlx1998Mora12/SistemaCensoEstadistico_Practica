package edu.academic.practica.SistemaCensoEstadistic;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.Random;

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
		a.test_ordenamiento();
		
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
	
	private void test_ordenamiento() throws Exception {
		
		MyLinkedList<Integer> lista_test1 = new MyLinkedList<>();
		MyLinkedList<Integer> lista_test2 = new MyLinkedList<>();
		MyLinkedList<Integer> lista_test3 = new MyLinkedList<>();
		
		long start_Time, end_Time;
		
		
		lista_test1 = crear_lista(10000);
		lista_test2 = crear_lista(10000);
		lista_test3 = crear_lista(10000);
		
		// Medir el tiempo de QuickSort
        start_Time = System.currentTimeMillis();
        lista_test1.quick_Sort_byOrder(0); // Asumiendo que 0 es el tipo de orden
        end_Time = System.currentTimeMillis();
        long test_QuickSort = end_Time - start_Time;
        System.out.println("Tiempo QuickSort: "+(test_QuickSort/1000.0));
        
        // Medir el tiempo de MergeSor
        start_Time = System.currentTimeMillis();
        lista_test2.merge_Sort_byOrder(0); // Asumiendo que 0 es el tipo de orden
        end_Time = System.currentTimeMillis();
        long test_MergeSort = end_Time - start_Time;
        System.out.println("Tiempo MergeSort: "+(test_MergeSort/1000.0));
        
        // Medir el tiempo de ShellSort
        start_Time = System.currentTimeMillis();
        lista_test3.shellSort_byOrder(0); // Asumiendo que 0 es el tipo de orden
        end_Time = System.currentTimeMillis();
        long test_ShellSort = end_Time - start_Time;
        System.out.println("Tiempo ShellSort: "+(test_ShellSort/1000.0));
        
        // reset
        lista_test1.reset();
        lista_test2.reset();
        lista_test3.reset();
        
        //2 test
        lista_test1 = crear_lista(20000);
		lista_test2 = crear_lista(20000);
		lista_test3 = crear_lista(20000);
        
        
		// Medir el tiempo de QuickSort
        start_Time = System.currentTimeMillis();
        lista_test1.quick_Sort_byOrder(0); // Asumiendo que 0 es el tipo de orden
        end_Time = System.currentTimeMillis();
        long test_QuickSort2 = end_Time - start_Time;
        System.out.println("Tiempo QuickSort: "+(test_QuickSort2/1000.0));
        
        // Medir el tiempo de MergeSort
        start_Time = System.currentTimeMillis();
        lista_test2.merge_Sort_byOrder(0); // Asumiendo que 0 es el tipo de orden
        end_Time = System.currentTimeMillis();
        long test_MergeSort2 = end_Time - start_Time;
        System.out.println("Tiempo MergeSort: "+(test_MergeSort2/1000.0));
        
        // Medir el tiempo de ShellSort
        start_Time = System.currentTimeMillis();
        lista_test3.shellSort_byOrder(0); // Asumiendo que 0 es el tipo de orden
        end_Time = System.currentTimeMillis();
        long test_ShellSort2 = end_Time - start_Time;
        System.out.println("Tiempo ShellSort: "+(test_ShellSort2/1000.0));
        
        // reset
        lista_test1.reset();
        lista_test2.reset();
        lista_test3.reset();
        
        // 3 test
        lista_test1 = crear_lista(25000);
		lista_test2 = crear_lista(25000);
		lista_test3 = crear_lista(25000);
        
		// Medir el tiempo de QuickSort
        start_Time = System.currentTimeMillis();
        lista_test1.quick_Sort_byOrder(0); // Asumiendo que 0 es el tipo de orden
        end_Time = System.currentTimeMillis();
        long test_QuickSort3 = end_Time - start_Time;
        System.out.println("Tiempo QuickSort: "+(test_QuickSort3/1000.0));
        
        // Medir el tiempo de MergeSort
        start_Time = System.currentTimeMillis();
        lista_test2.merge_Sort_byOrder(0); // Asumiendo que 0 es el tipo de orden
        end_Time = System.currentTimeMillis();
        long test_MergeSort3 = end_Time - start_Time;
        System.out.println("Tiempo MergeSort: "+(test_MergeSort3/1000.0));
        
        // Medir el tiempo de ShellSort
        start_Time = System.currentTimeMillis();
        lista_test3.shellSort_byOrder(0); // Asumiendo que 0 es el tipo de orden
        end_Time = System.currentTimeMillis();
        long test_ShellSort3 = end_Time - start_Time;
        System.out.println("Tiempo ShellSort: "+(test_ShellSort3/1000.0));
        
        
	}
	
	private MyLinkedList<Integer> crear_lista(int n) {
		Random r = new Random();
		MyLinkedList<Integer> list= new MyLinkedList<>();
		
		for (int i = 0; i < n; i++) {
			list.add(r.nextInt());
		}
		
		return list; 
	}
}
