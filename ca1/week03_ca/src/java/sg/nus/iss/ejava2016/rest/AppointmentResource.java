/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.nus.iss.ejava2016.rest;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import sg.nus.iss.ejava2016.business.AppointmentManager;
import sg.nus.iss.ejava2016.business.PeopleManager;
import sg.nus.iss.ejava2016.model.Appointment;
import sg.nus.iss.ejava2016.model.People;

/**
 *
 * @author Joshua
 */
@RequestScoped
@Path("/appointment")
public class AppointmentResource {
    
    @EJB private AppointmentManager appointmentManager;
    @EJB private PeopleManager peopleManager;
    
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response add(MultivaluedMap<String,String> formData){
        System.out.println("description >>>>> " + formData.getFirst("description"));
        Appointment appointment=new Appointment();
        
        //Find through email
        Optional<List<People>> opt = peopleManager.findByEmail(formData.getFirst("email"));
        
        if(!opt.isPresent()){
            FacesMessage m = new FacesMessage("Team not found");
            FacesContext.getCurrentInstance().addMessage(null, m);
            return Response.noContent().entity("Team not found").build();
        }
        //Set Person
        appointment.setPid(opt.get().get(0));
        // Set Description
        appointment.setDescription(formData.getFirst("description"));
        // Set Date
        appointment.setApptDate(new Date(Long.parseLong(formData.getFirst("date"))));
       
        appointmentManager.add(appointment);
        return(Response.ok().build());
    }
    
    @GET
    @Path("{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verify(@PathParam("email") String email, @Context Request request){
        System.out.println("appointment email >>>>> " + email);
        //Find through email
        Optional<List<People>> optPeople = peopleManager.findByEmail(email);
        
        if(!optPeople.isPresent()){
            return (Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Not found: email=" + email)
                    .build());
        }
        
        Optional<List<Appointment>> optAppointment = appointmentManager.findByPid(optPeople.get().get(0).getPid());
        
        if(!optAppointment.isPresent()){
            return (Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Not found: email=" + email)
                    .build());
        }
        
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        
        optAppointment.get().stream()
                .map(c -> {return(c.toJSON());})
                .forEach(j -> {arrBuilder.add(j);});
        
        // Enable Cache Control
        int hashSum = 0;
        
        for(Appointment a : optAppointment.get()){
            //System.out.println(a.getApptId()+" - "+a.hashCode());
            hashSum += a.hashCode();
        }
        
        System.out.println("hashsum >>>>"+hashSum);
        
        CacheControl cc = new CacheControl();
        cc.setNoCache(true);
        
        EntityTag etag = new EntityTag(Integer.toString(hashSum));
        ResponseBuilder builder = request.evaluatePreconditions(etag);
        
        if(builder != null)
            return Response.notModified().build();
        
        // cached resource did change -> serve updated content
        builder = Response.ok(arrBuilder.build());
        builder.tag(etag);
        builder.cacheControl(cc);
        builder.expires(new Date(System.currentTimeMillis() + 3000));
        return(builder.build());
        //return(Response.ok(arrBuilder.build()).build());
    }
}
