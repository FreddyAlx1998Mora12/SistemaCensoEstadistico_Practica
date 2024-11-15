package edu.academic.practica.models;

public class Generador {
	private int idGenerador, consumo_litrosHora, kw;
	private double costo;
	private String tipo; //enum tipo DAO
	
//	Getters and setters
	public int getIdGenerador() {
		return idGenerador;
	}
	public void setIdGenerador(int idGenerador) {
		this.idGenerador = idGenerador;
	}
	public int getConsumo_litrosHora() {
		return consumo_litrosHora;
	}
	public void setConsumo_litrosHora(int consumo_litrosHora) {
		this.consumo_litrosHora = consumo_litrosHora;
	}
	public int getKw() {
		return kw;
	}
	public void setKw(int kw) {
		this.kw = kw;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
//	Constructor
	public Generador() {
		
	}
	
	public Generador(int idGenerador, int consumo_litrosHora, int kw, double costo, String tipo) {
		this.idGenerador = idGenerador;
		this.consumo_litrosHora = consumo_litrosHora;
		this.kw = kw;
		this.costo = costo;
		this.tipo = tipo;
	}
	
	@Override
	public String toString() {
		return "Generador [idGenerador=" + idGenerador + ", consumo_litrosHora=" + consumo_litrosHora + ", kw=" + kw
				+ ", costo=" + costo + ", tipo=" + tipo + "]";
	}
	
	
	
}
