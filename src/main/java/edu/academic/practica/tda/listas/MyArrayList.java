package edu.academic.practica.tda.listas;

public class MyArrayList<E> {
	
	private E[] dato;
	private int size;
	
	public MyArrayList() {
		this.size = 0;
		this.dato = (E[]) new Object[size]; 
	}

	public E[] getDato() {
		return dato;
	}

	public void setDato(E[] dato) {
		this.dato = dato;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	// #####################################
	public boolean isEmptyList() {
		return (this.size == 0 || this.dato == null);
	}
	
	public void addFirst(E element) {
		actualizar_dimension(); // Asegúrate de que hay suficiente capacidad
	    // Mueve los elementos hacia la derecha
	    System.arraycopy(dato, 0, dato, 1, size); // Desplaza todos los elementos a la derecha
	    dato[0] = element; // Inserta el nuevo elemento en la posición 0
	    size++; // Incrementa el tamaño
	}
	
	public void add(E element) {
		actualizar_dimension();
		dato[size++] = element;
	}
	
	private void actualizar_dimension() {
		if (size == dato.length) {
            // Duplica el tamaño del arreglo o inicia con tamaño 1
            int newSize = (dato.length == 0) ? 1 : dato.length * 2;
            E[] newData = (E[]) new Object[newSize];
            System.arraycopy(dato, 0, newData, 0, dato.length);
            dato = newData;
        }
	}
	// Metodo para obtener la longitud
	public int getLength() {
		return size;
	}
	
    // Método para obtener el primer elemento
    public E getFirst() {
        if (size == 0) {
            throw new IllegalStateException("La lista está vacía.");
        }
        return dato[0]; // Retorna el primer elemento
    }

    // Método para obtener el último elemento
    public E getLast() {
        if (size == 0) {
            throw new IllegalStateException("La lista está vacía.");
        }
        return dato[size - 1]; // Retorna el último elemento
    }
	
	public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango para obtener el elemento");
        }
        return dato[index];
    }
	
	public void removeLast() {
	    if (size == 0) {
	        throw new IllegalStateException("No hay elementos para eliminar.");
	    }
	    dato[--size] = null; // Elimina la última referencia
	}
	
	public void removeFirst() {
	    if (size == 0) {
	        throw new IllegalStateException("No hay elementos para eliminar.");
	    }
	    System.arraycopy(dato, 1, dato, 0, size - 1); // Desplaza los elementos hacia la izquierda
	    dato[--size] = null; // Elimina la última referencia
	}

	
	public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Índice fuera de rango para eliminar un dato.");
        }
        System.arraycopy(dato, index + 1, dato, index, size - index - 1);
        dato[--size] = null; // Elimina la última referencia
    }
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(dato[i]);
            if (i < size - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
	}
}
