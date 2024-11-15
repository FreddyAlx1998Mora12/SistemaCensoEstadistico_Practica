package edu.academic.practica.service;

import edu.academic.practica.dao.impl.FamiliaDAO;
import edu.academic.practica.dao.impl.GeneradorDAO;
import edu.academic.practica.models.Familia;
import edu.academic.practica.models.Generador;
import edu.academic.practica.tda.listas.MyLinkedList;

public class GeneradorService {

	private GeneradorDAO generador_DAO;

	public GeneradorService() {
		generador_DAO = new GeneradorDAO();
	}

	public Generador getGenerador() {
		return this.generador_DAO.getGenerador();
	}

	public void setGenerador(Generador g) {
		generador_DAO.setGenerador(g);
	}

	public MyLinkedList listAl() {
		return generador_DAO.getListGeneradors();
	}

	public boolean updatebyID(int id, Generador py) throws Exception {
		return generador_DAO.updatebyId((id - 1), py);
	}

	public void deletebyId(int id) throws Exception {
		generador_DAO.deletebyId((id - 1));
	}

	public Generador obtenerGenerador(int id) throws IndexOutOfBoundsException, Exception {
		return generador_DAO.obtenerGenerador(id - 1);
	}
}
