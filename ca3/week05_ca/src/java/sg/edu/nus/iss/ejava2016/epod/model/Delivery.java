/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.ejava2016.epod.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sanja
 */
@Entity
@Table(name = "delivery", catalog = "jlogistics", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Delivery.findAll", query = "SELECT d FROM Delivery d"),
    @NamedQuery(name = "Delivery.findByPkgId", query = "SELECT d FROM Delivery d WHERE d.pkgId = :pkgId"),
    @NamedQuery(name = "Delivery.findByName", query = "SELECT d FROM Delivery d WHERE d.name = :name"),
    @NamedQuery(name = "Delivery.findByAddress", query = "SELECT d FROM Delivery d WHERE d.address = :address"),
    @NamedQuery(name = "Delivery.findByPhone", query = "SELECT d FROM Delivery d WHERE d.phone = :phone"),
    @NamedQuery(name = "Delivery.findByCreateDate", query = "SELECT d FROM Delivery d WHERE d.createDate = :createDate")})
public class Delivery implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pkg_id", nullable = false)
    private Integer pkgId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "name", nullable = false, length = 256)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "address", nullable = false, length = 256)
    private String address;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 24)
    @Column(name = "phone", nullable = false, length = 24)
    private String phone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public Delivery() {
    }

    public Delivery(Integer pkgId) {
        this.pkgId = pkgId;
    }

    public Delivery(Integer pkgId, String name, String address, String phone, Date createDate) {
        this.pkgId = pkgId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.createDate = createDate;
    }

    public Integer getPkgId() {
        return pkgId;
    }

    public void setPkgId(Integer pkgId) {
        this.pkgId = pkgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pkgId != null ? pkgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Delivery)) {
            return false;
        }
        Delivery other = (Delivery) object;
        if ((this.pkgId == null && other.pkgId != null) || (this.pkgId != null && !this.pkgId.equals(other.pkgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sg.edu.nus.iss.ejava2016.epod.model.Delivery[ pkgId=" + pkgId + " ]";
    }
    
    public JsonObject toJSON() {
            return (Json.createObjectBuilder()
                .add("name", name)
                .add("address", address))
                .add("phone", phone)
                .build();
	}
    
}
