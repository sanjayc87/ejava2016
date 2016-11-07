/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.epod.manager;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import sg.edu.nus.iss.ejava2016.epod.model.Delivery;
import sg.edu.nus.iss.ejava2016.epod.model.Pod;

/**
 *
 * @author sanja
 */
@Stateless
@Transactional(Transactional.TxType.REQUIRES_NEW)
public class PodManager {
    
    @PersistenceContext private EntityManager em;
    
    public void add(Pod pod){
        em.persist(pod);
    }
    
    public void update(Pod pod){
        em.merge(pod);
    }
    
    public Optional<Pod> find(final Integer pid){
        return(Optional.ofNullable(em.find(Pod.class, pid)));
    }
    
    public Optional<List<Pod>> getAll(){
        
        TypedQuery<Pod> query = em.createNamedQuery(
                "Pod.findAll", Pod.class);
        
        return(Optional.ofNullable(query.getResultList()));
    }
}
