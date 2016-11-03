/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.model.notes;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author sanja
 */
@Embeddable
public class NotesPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "notesid", nullable = false, length = 32)
    private String notesid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "userid", nullable = false, length = 32)
    private String userid;

    public NotesPK() {
    }

    public NotesPK(String notesid, String userid) {
        this.notesid = notesid;
        this.userid = userid;
    }

    public String getNotesid() {
        return notesid;
    }

    public void setNotesid(String notesid) {
        this.notesid = notesid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notesid != null ? notesid.hashCode() : 0);
        hash += (userid != null ? userid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotesPK)) {
            return false;
        }
        NotesPK other = (NotesPK) object;
        if ((this.notesid == null && other.notesid != null) || (this.notesid != null && !this.notesid.equals(other.notesid))) {
            return false;
        }
        if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.edu.nus.iss.ejava2016.model.notes.NotesPK[ notesid=" + notesid + ", userid=" + userid + " ]";
    }
    
}
