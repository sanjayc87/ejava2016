/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.model.notes;

import java.io.Serializable;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sanja
 */
@Entity
@Table(name = "notes", catalog = "authdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notes.findAll", query = "SELECT n FROM Notes n"),
    @NamedQuery(name = "Notes.findByNotesid", query = "SELECT n FROM Notes n WHERE n.notesPK.notesid = :notesid"),
    @NamedQuery(name = "Notes.findByTitle", query = "SELECT n FROM Notes n WHERE n.title = :title"),
    @NamedQuery(name = "Notes.findByCreated", query = "SELECT n FROM Notes n WHERE n.created = :created"),
    @NamedQuery(name = "Notes.findByCategory", query = "SELECT n FROM Notes n WHERE n.category = :category"),
    @NamedQuery(name = "Notes.findByDescription", query = "SELECT n FROM Notes n WHERE n.description = :description"),
    @NamedQuery(name = "Notes.findByUserid", query = "SELECT n FROM Notes n WHERE n.notesPK.userid = :userid")})
public class Notes implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NotesPK notesPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "title", nullable = false, length = 32)
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "category", nullable = false, length = 32)
    private String category;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2048)
    @Column(name = "description", nullable = false, length = 2048)
    private String description;

    public Notes() {
    }

    public Notes(NotesPK notesPK) {
        this.notesPK = notesPK;
    }

    public Notes(NotesPK notesPK, String title, Date created, String category, String description) {
        this.notesPK = notesPK;
        this.title = title;
        this.created = created;
        this.category = category;
        this.description = description;
    }

    public Notes(String notesid, String userid) {
        this.notesPK = new NotesPK(notesid, userid);
    }

    public NotesPK getNotesPK() {
        return notesPK;
    }

    public void setNotesPK(NotesPK notesPK) {
        this.notesPK = notesPK;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notesPK != null ? notesPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notes)) {
            return false;
        }
        Notes other = (Notes) object;
        if ((this.notesPK == null && other.notesPK != null) || (this.notesPK != null && !this.notesPK.equals(other.notesPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.edu.nus.iss.ejava2016.model.notes.Notes[ notesPK=" + notesPK + " ]";
    }
    
    public JsonObject toJSON() {
            return (Json.createObjectBuilder()
                .add("Title", title)
                .add("Posted", created.toString())
                .add("User", notesPK.getUserid())
                .add("Category", category)
                .add("Description", description)
                .build());
	}
    
}
