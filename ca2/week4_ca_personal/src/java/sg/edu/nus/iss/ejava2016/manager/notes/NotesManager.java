/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.manager.notes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import sg.edu.nus.iss.ejava2016.model.notes.Notes;
import sg.edu.nus.iss.ejava2016.model.notes.NotesPK;

/**
 *
 * @author sanja
 */
@Stateless
public class NotesManager {
    
    @PersistenceContext EntityManager em;
    
    public void add(Notes note){
        NotesPK notespKUpdated = note.getNotesPK();
        notespKUpdated.setNotesid(UUID.randomUUID().toString().substring(0, 8));
        note.setNotesPK(notespKUpdated);
        em.persist(note);
    }
    
    public Optional<List<Notes>> findAll(){
        TypedQuery<Notes> query = em.createNamedQuery(
                "Notes.findAll", Notes.class);
        return (Optional.ofNullable(query.getResultList()));       
    }
    
    public Optional<List<Notes>> findByUserId(String userId){
        TypedQuery<Notes> query = em.createNamedQuery(
                "Notes.findByUserid", Notes.class);
        query.setParameter("userid", userId);
        
        return (Optional.ofNullable(query.getResultList()));       
    }
    
    public Optional<List<Notes>> findByCategory(String category){
        TypedQuery<Notes> query = em.createNamedQuery(
                "Notes.findByCategory", Notes.class);
        query.setParameter("category", category);
        
        return (Optional.ofNullable(query.getResultList()));       
    }
    
}
