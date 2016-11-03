/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.model.auth;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sanja
 */
@Entity
@Table(name = "groups", catalog = "authdb", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groups.findAll", query = "SELECT g FROM Groups g"),
    @NamedQuery(name = "Groups.findByGroupid", query = "SELECT g FROM Groups g WHERE g.groupsPK.groupid = :groupid"),
    @NamedQuery(name = "Groups.findByUserid", query = "SELECT g FROM Groups g WHERE g.groupsPK.userid = :userid")})
public class Groups implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected GroupsPK groupsPK;

    public Groups() {
    }

    public Groups(GroupsPK groupsPK) {
        this.groupsPK = groupsPK;
    }

    public Groups(String groupid, String userid) {
        this.groupsPK = new GroupsPK(groupid, userid);
    }

    public GroupsPK getGroupsPK() {
        return groupsPK;
    }

    public void setGroupsPK(GroupsPK groupsPK) {
        this.groupsPK = groupsPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (groupsPK != null ? groupsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groups)) {
            return false;
        }
        Groups other = (Groups) object;
        if ((this.groupsPK == null && other.groupsPK != null) || (this.groupsPK != null && !this.groupsPK.equals(other.groupsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.edu.nus.iss.ejava2016.model.Groups[ groupsPK=" + groupsPK + " ]";
    }
    
}
