package edu.academic.practica.models;

/**
 * Clase que describe un conjunto ya sea una familia, local, oficina, vivienda
 * para identificar si tal familia (censo) dispone de un generador.
 */
public class Familia {
	
	private int idCenso, nroIntegrantesFamilia;
	private String descripcion;
	private String direccion;
	private boolean haveGenerador;
	private Generador generador;
	
	public Generador getGenerador() {
		return generador;
	}
	public void setGenerador(Generador generador) {
		this.generador = generador;
	}
	// Getters and Setters	
	public int getIdCenso() {
		return idCenso;
	}
	public void setIdCenso(int idCenso) {
		this.idCenso = idCenso;
	}
	public int getNroIntegrantesFamilia() {
		return nroIntegrantesFamilia;
	}
	public void setNroIntegrantesFamilia(int nroIntegrantesFamilia) {
		this.nroIntegrantesFamilia = nroIntegrantesFamilia;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public boolean isHaveGenerador() {
		return haveGenerador;
	}
	public void setHaveGenerador(boolean haveGenerador) {
		this.haveGenerador = haveGenerador;
	}

	
	//	Constructor
	
	public Familia() {
		
	}
	
	public Familia(int idCenso, int nroIntegrantesFamilia, String descripcion, String direccion, boolean haveGenerador) {
		this.idCenso = idCenso;
		this.nroIntegrantesFamilia = nroIntegrantesFamilia;
		this.descripcion = descripcion;
		this.direccion = direccion;
		this.haveGenerador = haveGenerador;
	}
	
	public Familia(Generador generador) {
		this.generador = generador;
	}
	
	
}
