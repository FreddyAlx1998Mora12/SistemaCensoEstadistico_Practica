package edu.academic.practica.models.rest;

import java.util.HashMap;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.academic.practica.models.Familia;
import edu.academic.practica.models.Generador;
import edu.academic.practica.service.GeneradorService;

@Path("/generador")
public class GeneradorAPI {

	GeneradorService gs = new GeneradorService();

	@Path("/delete/{idGenerador}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("idGenerador") int idGenerador) throws IndexOutOfBoundsException, Exception { 
		HashMap res = new HashMap<>();
		try {
			gs.deletebyId(idGenerador); // elimina el generador

		} catch (Exception e) {
			res.put("msg", e.getLocalizedMessage());
			res.put("cause", e.getCause());
			res.put("StackTrace", e.getStackTrace());
			return Response.status(Status.BAD_REQUEST).entity(res).build(); // retorna un estado de tipo STATUS (Enum)
		}
		res.put("msg", "OK");
		res.put("data", "dato Generador eliminado correctamente");
		return Response.ok(res).build();
	}
	
	@Path("/update")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(HashMap request) throws IndexOutOfBoundsException, Exception { // data en el hashmap, el cuerpo del json a receptar o que envia el usuario
		
		HashMap res = new HashMap<>(); // Para construir un cuerpo de response
		// Validation
		int id = (int) request.get(("idGenerador"));
		Generador generador = gs.obtenerGenerador(id);
		try {	
			if(generador == null) { // si existe la persona
				res.put("msg", "NULL");
				res.put("data", "No existe el dato que desea encontrar, "+(int) request.get(("idCenso")));
				return Response.status(Status.NOT_FOUND).entity(res).build();
			}else { // si tiene id es por que se va a modificar
							
				generador.setConsumo_litrosHora((int) request.get(("consumo_litrosHora")));
				generador.setKw((int) request.get(("kw")));
				generador.setCosto((double) request.get(("costo")));
				generador.setTipo(request.get(("tipo")).toString());
				// dependiendo si tiene generador
				
				gs.setGenerador(generador);
				gs.updatebyID(id, generador);
				
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
}
