package edu.academic.practica.dao;

import edu.academic.practica.tda.listas.MyLinkedList;

public interface InterfaceDao<T> {
	
	void persist(T object) throws Exception;
	void merge(T object, Integer index) throws Exception;
    MyLinkedList listAll();
    T get(Integer id) throws Exception;
    void delete(int index) throws Exception;
}
