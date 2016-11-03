/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.manager.user;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sg.edu.nus.iss.ejava2016.model.auth.Users;

/**
 *
 * @author sanja
 */
public class UserManager {
    
    @PersistenceContext EntityManager em;
    
    public Optional<Users> find(String userId){
        return (Optional.ofNullable(em.find(Users.class, userId)));
    }
    
}
