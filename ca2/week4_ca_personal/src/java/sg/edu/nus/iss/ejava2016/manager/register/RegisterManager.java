/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.manager.register;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sg.edu.nus.iss.ejava2016.model.auth.Groups;
import sg.edu.nus.iss.ejava2016.model.auth.GroupsPK;
import sg.edu.nus.iss.ejava2016.model.auth.Users;

/**
 *
 * @author sanja
 */
public class RegisterManager {
    
    @PersistenceContext private EntityManager em;
    
    public void add(Users user){
        em.persist(user);
        add(new Groups("DEFAULT", user.getUserid()));
    }
    
    public Optional<Users> find(final String userid){
        return(Optional.ofNullable(em.find(Users.class, userid)));
    }
    
    private void add(Groups groups){
        em.persist(groups);
    }
    
}
