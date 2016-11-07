/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.epod.business;

import java.util.Optional;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sg.edu.nus.iss.ejava2016.epod.model.Pod;

/**
 *
 * @author sanja
 */
@Stateless
public class PodManager {
    
    @PersistenceContext private EntityManager em;
    
    public void add(Pod pod){;
        em.persist(pod);
    }
    
    public Optional<Pod> find(final String pid){
        return(Optional.ofNullable(em.find(Pod.class, pid)));
    }
}
