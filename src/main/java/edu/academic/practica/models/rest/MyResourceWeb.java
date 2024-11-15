package edu.academic.practica.models.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.academic.practica.tda.listas.MyLinkedList;


@Path("/init")
public class MyResourceWeb {
	
	@Path("/test")
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTest() {
		MyLinkedList<String> myList = new MyLinkedList<>();
//		Un diccionario de clave-valor, clave y valor almacenan valores de tipo String
        Map<String, String> map= new HashMap();

        String aux = "";

        map.put("msg", "OK");
        map.put("data", "test "+aux);
        return Response.ok(map).build();

    }
}
