package edu.academic.practica.models.rest;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.academic.practica.models.Familia;
import edu.academic.practica.models.Generador;
import edu.academic.practica.service.FamiliaService;
import edu.academic.practica.service.GeneradorService;
import edu.academic.practica.tda.listas.MyLinkedList;

@Path("/proyectoCenso")
public class ProyectoCensoApi {

	FamiliaService fs = new FamiliaService();
	GeneradorService gs = new GeneradorService();

	// lista todas las familias censadas
	@Path("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCenso() {
		HashMap map = new HashMap<>();
//		PersonaService personaService = new PersonaService();
		map.put("msg", "OK");
		map.put("data", fs.listarFamilias().toArray());

		return Response.ok(map).build();
	}

//	lista todos los generadores
	@Path("/list/generadores")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGenerador() {
		HashMap map = new HashMap<>();
//		PersonaService personaService = new PersonaService();
		map.put("msg", "OK");
		map.put("data", gs.listAl().toArray());

		return Response.ok(map).build();
	}

	@Path("/save")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(HashMap request) { // data en el hashmap, el cuerpo del json a receptar o que envia el usuario

		// Objetos
		Familia person = new Familia();
		Generador generador = new Generador();

		// Validacion

		person.setNroIntegrantesFamilia((int) request.get(("nroIntegrantesFamilia")));
		person.setDescripcion(request.get(("descripcion")).toString());
		person.setDireccion(request.get(("direccion")).toString());
		person.setHaveGenerador((boolean) request.get(("haveGenerador")));

		// dependiendo si tiene generador
		if (person.isHaveGenerador()) {
			// recordemos que tiene un objeto que contiene otros elementos
			HashMap reqGenerador = (HashMap) request.get(("generador"));

//			generador.setIdGenerador(0);
			generador.setConsumo_litrosHora((int) reqGenerador.get(("consumo_litrosHora")));
			generador.setKw((int) reqGenerador.get(("kw")));
			generador.setCosto((double) reqGenerador.get(("costo")));
			generador.setTipo(reqGenerador.get(("tipo")).toString());

			person.setGenerador(generador);
			fs.setGenerador(generador);
		}

		// envia el dato para guardar
		fs.setFamilia(person);

		HashMap res = new HashMap<>(); // Para construir un cuerpo de response

		try {
			fs.guardarCenso(); // guardo correctamente
			res.put("msg", "OK");
			res.put("data", "Censo registrada correctamente");
			return Response.ok(res).build();

		} catch (Exception e) {
			// TODO: handle exception
			res.put("msg", "ERROR");
			res.put("data", "Hubo un error al intentar registrar, " + e.toString());
			return Response.status(Status.BAD_REQUEST).entity(res).build(); // retorna un estado de tipo STATUS (Enum)
																			// de la entidad Buildr
		}
	}

	@Path("/update")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(HashMap request) throws IndexOutOfBoundsException, Exception { // data en el hashmap, el
																							// cuerpo del json a
																							// receptar o que envia el
																							// usuario

		HashMap res = new HashMap<>(); // Para construir un cuerpo de response
		// Validation
		int idC = (int) request.get(("idCenso"));
		Familia familia_censo = fs.obtenerFamilia(idC);
		Generador generador;
		try {
			if (familia_censo == null) { // si existe la persona
				res.put("msg", "NULL");
				res.put("data", "No existe el dato que desea encontrar, " + (int) request.get(("idCenso")));
				return Response.status(Status.NOT_FOUND).entity(res).build();
			} else { // si tiene id es por que se va a modificar
				familia_censo.setNroIntegrantesFamilia((int) request.get(("nroIntegrantesFamilia")));
				familia_censo.setDescripcion(request.get(("descripcion")).toString());
				familia_censo.setDireccion(request.get(("direccion")).toString());
				familia_censo.setHaveGenerador((boolean) request.get(("haveGenerador")));

				// dependiendo si tiene generador
				if (familia_censo.isHaveGenerador()) {
					HashMap reqGenerador = (HashMap) request.get(("generador"));
					int idG = (int) reqGenerador.get(("idGenerador"));

					generador = gs.obtenerGenerador(idG);
					generador.setConsumo_litrosHora((int) reqGenerador.get(("consumo_litrosHora")));
					generador.setKw((int) reqGenerador.get(("kw")));
					generador.setCosto((double) reqGenerador.get(("costo")));
					generador.setTipo(reqGenerador.get(("tipo")).toString());

					familia_censo.setGenerador(generador);
					fs.setGenerador(generador);
					gs.updatebyID(idG, generador);
				}

				// envia el dato para guardar
				fs.setFamilia(familia_censo);

				fs.actualizarFamiliaID(idC, familia_censo); // guardo correctamente
				res.put("msg", "OK");
				res.put("data", "Censo actualizado correctamente");
				return Response.ok(res).build();
			}
//			return Response.ok(res).build(); 
		} catch (Exception e) {
			res.put("msg", e.getLocalizedMessage());
			res.put("cause", e.getCause());
			res.put("S", e.getStackTrace());
			return Response.status(Status.BAD_REQUEST).entity(res).build(); // retorna un estado de tipo STATUS (Enum)
		}
	}

	@Path("/delete/{idCenso}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("idCenso") int idFamilia) throws IndexOutOfBoundsException, Exception { // data en
																												// el
																												// hashmap,
																												// el
																												// cuerpo
																												// del
																												// json
																												// a
																												// receptar
																												// o que
																												// envia
																												// el
																												// usuario
		HashMap res = new HashMap<>();
		try {
			Familia f = fs.obtenerFamilia(idFamilia);
			System.out.println("Dato intentando eliminar " + f.getIdCenso());
			// eliminamos
			if (f.isHaveGenerador()) {
				gs.deletebyId(f.getGenerador().getIdGenerador()); // elimina el generador
			}
			fs.deletebyId(idFamilia); // elimina todo el dato completo

		} catch (Exception e) {
			res.put("msg", e.getLocalizedMessage());
			res.put("cause", e.getCause());
			res.put("StackTrace", e.getStackTrace());
			return Response.status(Status.BAD_REQUEST).entity(res).build(); // retorna un estado de tipo STATUS (Enum)
		}
		res.put("msg", "OK");
		res.put("data", "Dato eliminado correctamente");
		return Response.ok(res).build();
	}

	/*
	 * #############################################################################
	 * ###################### PRACTICA 2 METODOS DE ORDENACION Y BUSQUEDA METODO DE
	 * ORDENACION LINEAL Y LINEAL BINARIO
	 * 
	 */
	// Metodo de busqueda Lineal
	// search/criterio/texto
	@Path("/search/id/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchbyId(@PathParam("id") Integer txt) throws Exception {
		HashMap map = new HashMap<>();
//		PersonaService personaService = new PersonaService();

		try {
			if (txt.intValue() > 0) {
				Familia persona = fs.findById(txt);
				map.put("msg", "OK");
				if (persona.equals(null)) {
					// debe retornar un arreglo vacio
					map.put("data", "No existe la persona con el ID");
				} else
					map.put("data", persona);

//				return Response.ok(map).build();
			} else {
				map.put("msg", "Invalid");
				map.put("data", "El id que ingreso es invalido");
				return Response.status(Status.NOT_ACCEPTABLE).entity(map).build();
			}
		} catch (Exception e) {
			// TODO: handle exception
			map.put("msg", "Error");
			map.put("data", e.getCause());
			return Response.status(Status.BAD_REQUEST).entity(map).build();
		}
//		return null;
		return Response.ok(map).build();

	}

	@Path("/search/generador/{texto}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchbyGenerador(@PathParam("texto") boolean txt) throws Exception {
		HashMap map = new HashMap<>();
//		PersonaService personaService = new PersonaService();
		// Validacion

		MyLinkedList listaBusqueda = fs.findByHaveGeneradors(txt);
		map.put("msg", "OK");
		if (listaBusqueda.isEmptyLinkedList()) {
			// debe retornar un arreglo vacio
			map.put("data", new Object());
		} else
			map.put("data", listaBusqueda.toArray());

		return Response.ok(map).build();
	}

	@Path("/search/{tipo}/{criterio}/{texto}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchbyTipo(@PathParam("tipo") String tipo, @PathParam("criterio") String criterio, @PathParam("texto") String txt) throws Exception {
		HashMap map = new HashMap<>();
		Familia censo;
		MyLinkedList listaBusqueda = null;
//		PersonaService personaService = new PersonaService();
		// Validacion 
		
		try {
			map.put("msg", "OK");
			if(tipo.compareToIgnoreCase("lineal") == 0) {

				
				switch (criterio) {
				case "descripcion":
					censo = fs.findByDescripcion(txt);
					if(!censo.equals(null)) {					
						map.put("data", censo);
					}else map.put("data", "No se encontro el dato");
					return Response.ok(map).build();

				case "haveGenerador":

					listaBusqueda = fs.findByHaveGeneradors(Boolean.parseBoolean(txt));
					break;
				case "numero_integrante":

					listaBusqueda = fs.findByNroIntegrantes(Integer.parseInt(txt));
					
					break;
				case "direccion":
					listaBusqueda = fs.findByDireccion(txt);
					break;
				default:
					break;
				}
			}else if(tipo.compareToIgnoreCase("binario") == 0) {
				
				switch (criterio) {
				case "descripcion":
					censo = fs.findByDescripcion_binar(txt);
					if(!censo.equals(null)) {					
						map.put("data", censo);
					}else map.put("data", "No se encontro el dato");
					return Response.ok(map).build();

				case "haveGenerador":

					listaBusqueda = fs.findByHaveGeneradors_binar(Boolean.parseBoolean(txt));
					break;
				case "numero_integrante":

					listaBusqueda = fs.findByNroIntegrantes_binar(Integer.parseInt(txt));
					
					break;
				case "direccion":
					listaBusqueda = fs.findByDireccion_binar(txt);
					break;
				default:
					break;
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			map.put("msg", "Error");
			map.put("data", e.getMessage());
			map.put("causa", e.getCause());
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(map).build();
		}
		
		if(!listaBusqueda.isEmptyLinkedList()) {
			map.put("data", listaBusqueda.toArray());
		}else map.put("data", "No existen datos");

		return Response.ok(map).build();
	}

	// Orden por tipo_orden y el valor a buscar 
	// 1 - a-z, 0 z - a
	
	@Path("/search/{tipo}/{texto}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response ordenarPorTipoOrden_Criterio(@PathParam("tipo") int tipo_orden, @PathParam("texto") String criterio)
			throws Exception {
		HashMap map = new HashMap<>();
//		PersonaService personaService = new PersonaService();
		// Validacion que no digite cualquier texto y que tipo no sea menor a 0

		MyLinkedList listaOrdenada = fs.ordenarPorCriterio(tipo_orden, criterio);
		map.put("msg", "OK");
		if (listaOrdenada.isEmptyLinkedList()) {
			// debe retornar un arreglo vacio
			map.put("data", new Object());
		} else
			map.put("data", listaOrdenada.toArray());

		return Response.ok(map).build();
	}

	// Metodo de busqueda binario
	/*
	 * 1. el arreglo debe de estar ordenado 2. Divide la lista en mitades mas
	 * ppequenias segun lo que se busca
	 */
	@Path("/search/descripcion/{texto}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchbyDescripcion(@PathParam("texto") String valor) throws Exception {
		HashMap map = new HashMap<>();
//		PersonaService personaService = new PersonaService();
		// Validacion
		try {
			Familia censo = fs.findByDescripcion_binar(valor);
//			System.out.println("Objeto censo: "+censo);
			map.put("msg", "OK");
			if (censo == null) {
				// debe retornar un arreglo vacio
				map.put("data", "No se encontro tal objeto");
			} else
				map.put("data", censo);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("msg", "Error");
			map.put("data", "Causa: " + e.getCause() + "\nMensaje Localizad: " + e.getLocalizedMessage());
		}

		return Response.ok(map).build();
	}

	/*
	 * 
	 * ################################################### Metodos de Ordenacion
	 * ###################################################
	 * 
	 * 
	 */
	@Path("/order/quick/{tipo}/{texto}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response ordenarQuick(@PathParam("tipo") int tipo_orden, @PathParam("texto") String criterio)
			throws Exception {
		HashMap map = new HashMap<>();
//		PersonaService personaService = new PersonaService();
		// Validacion que no digite cualquier texto y que tipo no sea menor a 0

		MyLinkedList listaOrdenada = fs.orderByQuickSort(tipo_orden, criterio);
		map.put("msg", "OK");
		if (listaOrdenada.isEmptyLinkedList()) {
			// debe retornar un arreglo vacio
			map.put("data", new Object());
		} else
			map.put("data", listaOrdenada.toArray());

		return Response.ok(map).build();
	}

	@Path("/order/shell/{tipo}/{texto}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response ordenarShell(@PathParam("tipo") int tipo_orden, @PathParam("texto") String criterio)
			throws Exception {
		HashMap map = new HashMap<>();
//		PersonaService personaService = new PersonaService();
		// Validacion que no digite cualquier texto y que tipo no sea menor a 0

		MyLinkedList listaOrdenada = fs.orderByShellSort(tipo_orden, criterio);
		map.put("msg", "OK");
		if (listaOrdenada.isEmptyLinkedList()) {
			// debe retornar un arreglo vacio
			map.put("data", new Object());
		} else
			map.put("data", listaOrdenada.toArray());

		return Response.ok(map).build();
	}

	@Path("/order/merge/{tipo}/{texto}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response ordenarMerge(@PathParam("tipo") int tipo_orden, @PathParam("texto") String criterio)
			throws Exception {
		HashMap map = new HashMap<>();
//		PersonaService personaService = new PersonaService();
		// Validacion que no digite cualquier texto y que tipo no sea menor a 0

		MyLinkedList listaOrdenada = fs.orderByMergeSort(tipo_orden, criterio);
		map.put("msg", "OK");
		if (listaOrdenada.isEmptyLinkedList()) {
			// debe retornar un arreglo vacio
			map.put("data", new Object());
		} else
			map.put("data", listaOrdenada.toArray());

		return Response.ok(map).build();
	}
}
