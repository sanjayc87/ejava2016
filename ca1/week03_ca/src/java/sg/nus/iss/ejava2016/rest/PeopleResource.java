/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.nus.iss.ejava2016.rest;

import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import sg.nus.iss.ejava2016.business.AppointmentManager;
import sg.nus.iss.ejava2016.business.PeopleManager;
import sg.nus.iss.ejava2016.model.People;

/**
 *
 * @author Joshua
 */
@RequestScoped
@Path("/people")
public class PeopleResource {
    
    @EJB private PeopleManager peopleManager;
    @EJB private AppointmentManager appointmentManager;
    
    @POST
    @Consumes("application/x-www-form-urlencoded")
    public Response add(MultivaluedMap<String,String> formData){
        
        People people=new People();
        people.setName(formData.getFirst("name"));
        people.setEmail(formData.getFirst("email"));
        
        peopleManager.add(people);
        
        return(Response.ok().build());
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response verify(@QueryParam("email") String email){
        System.out.println("people email >>>>> " + email);
        //Find through email
        Optional<List<People>> optPeople = peopleManager.findByEmail(email);
        
        if(!optPeople.isPresent()){
            return (Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Not found: email=" + email)
                    .build());
        }
        
        return(Response.ok().build());
    }
}
