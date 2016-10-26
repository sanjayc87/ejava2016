/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.nus.iss.ejava2016.business;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import sg.nus.iss.ejava2016.model.People;

/**
 *
 * @author Joshua
 */
@Stateless
public class PeopleManager {
    
    @PersistenceContext private EntityManager em;
    
    public void add(People people){
        String pid = UUID.randomUUID().toString().substring(0, 8);
        people.setPid(pid);
        em.persist(people);
    }
    
    public Optional<People> find(final String pid){
        return(Optional.ofNullable(em.find(People.class, pid)));
    }
    
    public Optional<List<People>> findByEmail(final String email){
        
        TypedQuery<People> query = em.createNamedQuery(
                "People.findByEmail", People.class);
        query.setParameter("email", email);
        
        return(Optional.ofNullable(query.getResultList()));
    }
}
