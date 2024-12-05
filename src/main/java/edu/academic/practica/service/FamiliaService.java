package edu.academic.practica.service;


import edu.academic.practica.dao.impl.FamiliaDAO;
import edu.academic.practica.dao.impl.GeneradorDAO;
import edu.academic.practica.models.Familia;
import edu.academic.practica.models.Generador;
import edu.academic.practica.tda.listas.MyLinkedList;

public class FamiliaService {
	
	private FamiliaDAO familia_DAO;
	private GeneradorDAO generador_DAO;
	
	public FamiliaService() {
		familia_DAO = new FamiliaDAO();
		generador_DAO = new GeneradorDAO();
	}
	
	public Familia getFamilia() { //obtiene el objeto Familia
		return familia_DAO.getFamilia();
	}
	
	public void setFamilia(Familia f) {
		familia_DAO.setFamilia(f);
	}
	
	// Obtiene el generador de tal familia
	public Generador getGenerador() {
		return this.familia_DAO.getFamilia().getGenerador();
	}
	
	public void setGenerador(Generador g) {
		generador_DAO.setGenerador(g);
	}
	
//	
	public MyLinkedList listarFamilias() {
		return familia_DAO.getListFamilias();
	}
	
	public boolean guardarCenso() throws Exception{
		//1. En JSON aparte se debe guardar los generadores
		if(familia_DAO.getFamilia().isHaveGenerador()) { // si la familia tiene generador			
			generador_DAO.setGenerador(getGenerador());
			generador_DAO.save();
		}
		return familia_DAO.save();
	}
	
	public Familia obtenerFamilia(int id) throws IndexOutOfBoundsException, Exception {
		return familia_DAO.obtenerFamilia(id-1);
	}
	
	public boolean actualizarFamiliaID(int id, Familia py) throws Exception {
		return familia_DAO.updatebyId((id-1), py);
	}
	
	public void deletebyId(int id) throws Exception {
		familia_DAO.deletebyId((id-1));
	}
	
	public Generador infoGenerador() {
		if(isHaveGenerador()) {			
			return familia_DAO.obtenerInfoGenerador();
		}
		return null;
	}
	
	public boolean isHaveGenerador() {
		return familia_DAO.haveGenerador();
	}
	
	/*
	 * ###########################################3
	 * Metodos de Busqueda Lineal
	 * ###########################################
	 */
	public Familia findById(int id) throws Exception{
		return familia_DAO.buscarporIDCenso(id);
	}
	
	public MyLinkedList findByDireccion(String criterio) throws Exception{
		return familia_DAO.buscarporDireccion(criterio);
	}
	
	public MyLinkedList<Familia> findByHaveGeneradors(boolean criterio) throws Exception{
		return familia_DAO.buscarporGeneradors(criterio);
	}
	
	public MyLinkedList<Familia> findByNroIntegrantes(int criterio) throws Exception{
		return familia_DAO.buscarporNroIntegrantes(criterio);
	}
	
	public Familia findByDescripcion(String criterio) throws Exception{
		return familia_DAO.buscarporDescripcion(criterio);
	}
	
	/*
	 * ###########################################3
	 * Metodos de Busqueda binario
	 * ###########################################
	 */
	public Familia findByDescripcion_binar(String valor) throws Exception {
		return familia_DAO.buscarporDescripcion_Binario(valor);
	}
	
	public MyLinkedList<Familia> findByDireccion_binar(String criterio) throws Exception{
		return familia_DAO.buscarporDireccion_Binario(criterio);
	}
	
	public MyLinkedList<Familia> findByHaveGeneradors_binar(boolean criterio) throws Exception{
		return familia_DAO.buscarporHaveGenerador_Binario(criterio);
	}
	
	public MyLinkedList<Familia> findByNroIntegrantes_binar(int criterio) throws Exception{
		return familia_DAO.buscarporNroIntegrantes_Binario(criterio);
	}
	
	
	// ordenacion por criterio
	public MyLinkedList ordenarPorCriterio(int tipo_orden, String criterio) throws Exception{
		return familia_DAO.ordenarLista(tipo_orden, criterio);
	}
	/*
	 * ###########################################3
	 * Metodos de Ordenacion
	 * ###########################################
	 */
	
	public MyLinkedList orderByQuickSort(int tipo_orden, String criterio) {
		return familia_DAO.ordenarListaQuickSort(tipo_orden, criterio);
	}
	
	public MyLinkedList orderByMergeSort(int tipo_orden, String criterio) {
		return familia_DAO.ordenarListaMergeSort(tipo_orden, criterio);
	}
	
	public MyLinkedList orderByShellSort(int tipo_orden, String criterio) {
		return familia_DAO.ordenarListaShellSort(tipo_orden, criterio);
	}
	
	
}
