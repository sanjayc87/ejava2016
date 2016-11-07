/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.epod.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import sg.edu.nus.iss.ejava2016.epod.manager.DeliveryManager;
import sg.edu.nus.iss.ejava2016.epod.manager.PodManager;
import sg.edu.nus.iss.ejava2016.epod.model.Delivery;
import sg.edu.nus.iss.ejava2016.epod.model.Pod;

/**
 *
 * @author sanja
 */
@RequestScoped
@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
public class MobileResource {
    
    @EJB private PodManager podManager;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        
            Optional<List<Pod>> opt = podManager.getAll();
            System.out.println(">> in getAll");
            if (!opt.isPresent())
                    return (Response
                                    .status(Response.Status.NOT_FOUND)
                                    .build());

            JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        
            opt.get().stream()
                    .map(c -> {return(c.toDeliveryJSON());})
                    .forEach(j -> {arrBuilder.add(j);});

            return (Response.ok(arrBuilder.build()).build());
    }
}
