/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.epod.manager;

import java.util.List;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import sg.edu.nus.iss.ejava2016.epod.model.Delivery;
import sg.edu.nus.iss.ejava2016.epod.model.Pod;

/**
 *
 * @author sanja
 */
@Stateless
public class DeliveryManager {
    
    @PersistenceContext private EntityManager em;
    
    public Integer add(Delivery delivery){
        em.persist(delivery);
        em.flush();
        return delivery.getPkgId();
    }
    
    public Optional<Delivery> find(final Integer pkgId){
        return(Optional.ofNullable(em.find(Delivery.class, pkgId)));
    }
    
    public Optional<List<Delivery>> getAll(){
        
        TypedQuery<Delivery> query = em.createNamedQuery(
                "Delivery.findAll", Delivery.class);
        
        return(Optional.ofNullable(query.getResultList()));
    }
}
