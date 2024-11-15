package edu.academic.practica.dao.impl;

import edu.academic.practica.dao.AdapterDao;
import edu.academic.practica.models.Familia;
import edu.academic.practica.models.Generador;
import edu.academic.practica.tda.listas.MyLinkedList;
import edu.academic.practica.tda.listas.Node;

public class FamiliaDAO extends AdapterDao<Familia>{
	// Aqui adjuntar o unir un objeto Generador, dado que el
	
	private Familia familia;
	private MyLinkedList listFamilia;
	private GeneradorDAO gDAO;
	
	public FamiliaDAO() {
		super(Familia.class);
//		this.listFamilia = new MyLinkedList<Familia>();
	}
	
	//getters
	public Familia getFamilia() {
		if(familia == null) {
			familia = new Familia();
		}
		return familia;
	}
	
	public void setFamilia(Familia censo) {
		this.familia = censo;
	}
	
	public MyLinkedList getListFamilias() {
		if(listFamilia == null) {
			this.listFamilia = listAll();
		}
		return listFamilia;
	}
	/*
	 * ##########################################
	 * CreateReadUpdateDelete
	 * ##########################################
	 */
	// Create	
	public boolean save() throws Exception{
		// incrementar el id
		int id = getListFamilias().getLength()+1;
		// validaciones de id
		
		// asigna el id
		familia.setIdCenso(id);
		this.persist(familia);
		
		// actualiza la lista para ProyectoCensoDAO
		this.listFamilia = listAll();
		
		return true;
	}
	// Read -> obtener listado
	// Read -> obtener un dato por id
	public Familia obtenerFamilia(Integer index) throws IndexOutOfBoundsException, Exception {
		return this.get(index);
	}
	
	// Update	
	public boolean updatebyId(int index, Familia dato_censo) throws Exception{
		this.merge(dato_censo, index);
		// actualiza la lista para ProyectoCensoDAO
		this.listFamilia = listAll();
		
		return true;
	}
	
	public void deletebyId(int index) throws Exception{
		this.delete(index);
		actualizar_lista_Ids();
		this.listFamilia = listAll();
	}
	
	// Metodo para verificacion si n familia tiene generador
	public boolean haveGenerador() {
		return this.familia.isHaveGenerador();
	}
	
	public Generador obtenerInfoGenerador() {
		return this.familia.getGenerador();
	}
	
	private void actualizar_lista_Ids() throws Exception {
        int contador = 0; // Comenzar desde 1
        int contadorGenerador = 0; 
        Node<Familia> current = getListFamilias().getHeader(); // Suponiendo que tienes un método para obtener la cabeza de la lista
        Familia mensajero;
        
        while (current != null) {
        	contador++; // cuenta 1
            mensajero = current.getInfo(); // obtiene el objeto de NODO
            mensajero.setIdCenso(contador); // actualiza el id del Objeto
            
            //debe verificar si tiene generador y actualizar el id tambien de los generadors
            if(mensajero.isHaveGenerador()) {
            	// actualiza tambien
            	//gDAO.actualizar_lista_Ids_Generador();
            	contadorGenerador++;
            	mensajero.getGenerador().setIdGenerador(contadorGenerador);
            }
            
        	current.setInfo(mensajero); // Asigna o guarda esa info en su respectivo Nodo
            current = current.getNext(); // Moverse al siguiente nodo
        }

        this.UpdateFile(listFamilia); // Actualiza el archivo si es necesario
    }
	
}
