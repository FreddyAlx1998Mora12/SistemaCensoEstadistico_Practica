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
	
	// veremos si es necesario esto
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
}
