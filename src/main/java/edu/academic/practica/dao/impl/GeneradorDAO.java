package edu.academic.practica.dao.impl;

import edu.academic.practica.dao.AdapterDao;
import edu.academic.practica.models.Familia;
import edu.academic.practica.models.Generador;
import edu.academic.practica.tda.listas.MyLinkedList;
import edu.academic.practica.tda.listas.Node;

public class GeneradorDAO extends AdapterDao<Generador>{
	
	private Generador generador;
	private MyLinkedList listGeneradors;
	
	public GeneradorDAO() {
		super(Generador.class);
	}
	
	public Generador getGenerador() {
		if(generador == null) {
			generador = new Generador();
		}
		return generador;
	}
	
	public void setGenerador(Generador censo) {
		this.generador = censo;
	}
	
	public MyLinkedList getListGeneradors() {
		if(listGeneradors == null) {
			this.listGeneradors = listAll();
		}
		return listGeneradors;
	}
	
	/*
	 * ##########################################
	 * CreateReadUpdateDelete
	 * ##########################################
	 */
	// Create	
	public boolean save() throws Exception{
		// incrementar el id
		int id = getListGeneradors().getLength()+1;
		
		
		// asigna el id
		this.generador.setIdGenerador(id);
		this.persist(generador);
		
		// actualiza la lista para ProyectoCensoDAO
		this.listGeneradors = listAll();
		
		return true;
	}
	// Read -> obtener listado
	// Read -> obtener un dato por id
	public Generador obtenerGenerador(int index) throws IndexOutOfBoundsException, Exception {
		return this.get(index);
	}
	
	// Update	
	public boolean updatebyId(int index, Generador dato_censo) throws Exception{
		this.merge(dato_censo, index);
		// actualiza la lista para ProyectoCensoDAO
		this.listGeneradors = listAll();
		
		return true;
	}
	
	public void deletebyId(int index) throws Exception{
		this.delete(index);
		actualizar_lista_Ids_Generador();
		this.listGeneradors = listAll();
	}
	
	public void actualizar_lista_Ids_Generador() throws Exception {
        int contador = 0; // Comenzar desde 1
        Node<Generador> current = getListGeneradors().getHeader(); // Suponiendo que tienes un método para obtener la cabeza de la lista
        Generador mensajero;
        
        while (current != null) {
        	contador++; // cuenta 1
            mensajero = current.getInfo(); // obtiene el objeto de NODO
            mensajero.setIdGenerador(contador); // actualiza el id del Objeto
        	current.setInfo(mensajero); // Asigna o guarda esa info en su respectivo Nodo
            current = current.getNext(); // Moverse al siguiente nodo
        }

        this.UpdateFile(listGeneradors); // Actualiza el archivo si es necesario
    }

}
