/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.nus.iss.ejava2016.rest;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;
import sg.nus.iss.ejava2016.business.AppointmentManager;
import sg.nus.iss.ejava2016.business.PeopleManager;
import sg.nus.iss.ejava2016.model.Appointment;
import sg.nus.iss.ejava2016.model.People;

/**
 *
 * @author sanja
 */
public class AppointmentTask implements Runnable {
    
    private AsyncResponse asyncResponse;
    private String email;
    
    @EJB private AppointmentManager appointmentManager;
    @EJB private PeopleManager peopleManager;

    public AppointmentTask(AsyncResponse asyncResponse) {
        this.asyncResponse = asyncResponse;
        this.email = email;
    }
    
    private People getPersonByEmail() throws Exception{
        
        Optional<List<People>> optPeople = peopleManager.findByEmail(email);
        
        if(!optPeople.isPresent())
            throw new Exception("Person Does not exists");
        
        return optPeople.get().get(0);
        
    }
    
    private JsonArrayBuilder getAppointments() throws Exception{
        
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        
        People person = getPersonByEmail();
        
        Optional<List<Appointment>> optAppointment = appointmentManager.findByPid(person.getPid());
        
        if(!optAppointment.isPresent())
            return arrBuilder;
        
        optAppointment.get().stream()
                .map(c -> {return(c.toJSON());})
                .forEach(j -> {arrBuilder.add(j);});
        
        return arrBuilder;
    }

    @Override
    public void run() {
        try {
            asyncResponse.resume(Response.ok(getAppointments().build()).build());
        } catch (Exception ex) {
            Logger.getLogger(AppointmentTask.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
