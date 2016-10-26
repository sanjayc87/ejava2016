/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.nus.iss.ejava2016.business;

import java.util.List;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import sg.nus.iss.ejava2016.model.Appointment;

/**
 *
 * @author Joshua
 */
@Stateless
public class AppointmentManager {
    
    @PersistenceContext private EntityManager em;
    
    public void add(Appointment app){
        em.persist(app);
    }

    public Optional<List<Appointment>> findByPid(String pid) {
        
        TypedQuery<Appointment> query = em.createNamedQuery(
                "People.findAppointmentByPid", Appointment.class);
        query.setParameter("pid", pid);
        
        return(Optional.ofNullable(query.getResultList()));
    }
    
}
