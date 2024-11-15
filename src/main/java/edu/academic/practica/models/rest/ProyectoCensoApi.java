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
		
		person.setNroIntegrantesFamilia((int)request.get(("nroIntegrantesFamilia")));
		person.setDescripcion(request.get(("descripcion")).toString());
		person.setDireccion(request.get(("direccion")).toString());
		person.setHaveGenerador((boolean)request.get(("haveGenerador")));
		
		// dependiendo si tiene generador
		if (person.isHaveGenerador()) {
			//recordemos que tiene un objeto que contiene otros elementos
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
	public Response update(HashMap request) throws IndexOutOfBoundsException, Exception { // data en el hashmap, el cuerpo del json a receptar o que envia el usuario
		
		HashMap res = new HashMap<>(); // Para construir un cuerpo de response
		// Validation
		int idC = (int) request.get(("idCenso"));
		Familia familia_censo = fs.obtenerFamilia(idC);
		Generador generador;
		try {	
			if(familia_censo == null) { // si existe la persona
				res.put("msg", "NULL");
				res.put("data", "No existe el dato que desea encontrar, "+(int) request.get(("idCenso")));
				return Response.status(Status.NOT_FOUND).entity(res).build();
			}else { // si tiene id es por que se va a modificar
				familia_censo.setNroIntegrantesFamilia((int) request.get(("nroIntegrantesFamilia")));
				familia_censo.setDescripcion(request.get(("descripcion")).toString());
				familia_censo.setDireccion(request.get(("direccion")).toString());
				familia_censo.setHaveGenerador((boolean)request.get(("haveGenerador")));
				
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
	public Response delete(@PathParam("idCenso") int idFamilia) throws IndexOutOfBoundsException, Exception { // data en el hashmap, el cuerpo del json a receptar o que envia el usuario
		HashMap res = new HashMap<>();
		try {
			Familia f = fs.obtenerFamilia(idFamilia);
			System.out.println("Dato intentando eliminar "+f.getIdCenso());
			// eliminamos 
			if(f.isHaveGenerador()) {				
				gs.deletebyId(f.getGenerador().getIdGenerador()); //elimina el generador
			}
			fs.deletebyId(idFamilia); //elimina todo el dato completo
			
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
}
