/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.view.login;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import sg.edu.nus.iss.ejava2016.model.auth.Groups;
import sg.edu.nus.iss.ejava2016.model.auth.Users;
import sg.edu.nus.iss.ejava2016.utils.DigestUtils;
import sg.edu.nus.iss.ejava2016.utils.SessionUtils;

/**
 *
 * @author sanja
 */
@ViewScoped 
@ManagedBean
@RolesAllowed({"DEFAULT"})
public class UsersView implements Serializable{
    
    @PersistenceContext EntityManager em;
    @Resource UserTransaction ut;
    
    private static final long serialVersionUID = 1L;
    
    private String userid;
    private String password;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @PermitAll
    public void authenticate() throws IOException {
        
        try{
            SessionUtils.getRequest().login(userid, password);
            SessionUtils.getSession().setAttribute("username", userid);
            SessionUtils.getExternalContext()
                    .redirect(SessionUtils.getRequestContextPath()
                            +"/faces/secure/notes/notesmenu.xhtml?faces-redirect=true");
        }catch (Throwable t){
            SessionUtils.getContext().addMessage(null, new FacesMessage("Incorrect Login"));
            // Keep Messages after redirect
            SessionUtils.getExternalContext().getFlash().setKeepMessages(true);
            SessionUtils.getExternalContext()
                    .redirect(SessionUtils.getRequestContextPath()
                            +"/faces/login.xhtml");
        }
        
    }
    
    @PermitAll
    public void register() throws IOException {
        
        try {
            ut.begin();
            em.joinTransaction();
            em.persist(new Users(userid, DigestUtils.sha256hex(password)));
            em.persist(new Groups("DEFAULT", userid));
            ut.commit();
        } catch (NotSupportedException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex) {
            Logger.getLogger(UsersView.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        SessionUtils.getExternalContext()
                .redirect(SessionUtils.getRequestContextPath()
                        +"/faces/login.xhtml?faces-redirect=true");
    }
    
    public void logout() throws IOException{
        
        HttpSession session = SessionUtils.getSession();
		session.invalidate();
        
        SessionUtils.getExternalContext()
                .redirect(SessionUtils.getRequestContextPath()
                        +"/faces/login.xhtml?faces-redirect=true");
    }
    
}
