/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.view.notes;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import sg.edu.nus.iss.ejava2016.manager.notes.NotesManager;
import sg.edu.nus.iss.ejava2016.model.notes.Notes;
import sg.edu.nus.iss.ejava2016.model.notes.NotesPK;
import sg.edu.nus.iss.ejava2016.utils.SessionUtils;

/**
 *
 * @author sanja
 */
@DeclareRoles({"DEFAULT"})
@RequestScoped
@Named
@RolesAllowed({"DEFAULT"})
public class NotesView {
    
    @EJB private NotesManager notesManager;
    
    private String description;
    private String category;
    private String title;
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @PostConstruct
    public void init(){                
        setUsername(SessionUtils.getSession()
                .getAttribute("username").toString());
    }
    
    public void add() throws IOException{
        
        NotesPK notesPk = new NotesPK();
        notesPk.setUserid(username);
        
        Notes note = new Notes();
        note.setNotesPK(notesPk);
        note.setTitle(title);
        note.setCategory(category);
        note.setCreated(new Date());
        note.setDescription(description);
        
        notesManager.add(note);
        
        SessionUtils.getExternalContext()
                .redirect(SessionUtils.getRequestContextPath()
                        +"/faces/secure/notes/notesmenu.xhtml?faces-redirect=true");
    }
    
    public List<Notes> getAllNotes(){
        return (notesManager.findAll().get());
    }
    
    public List<Notes> getUserNotes(){
        return (notesManager.findByUserId(username).get());
    }
}
