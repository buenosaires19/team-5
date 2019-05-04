/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socio.entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author seba
 */
@Entity
@Table(name = "region", catalog = "socios", schema = "")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Region.findAll", query = "SELECT r FROM Region r")
	, @NamedQuery(name = "Region.findByIdregion", query = "SELECT r FROM Region r WHERE r.idregion = :idregion")
	, @NamedQuery(name = "Region.findByRegDesc", query = "SELECT r FROM Region r WHERE r.regDesc = :regDesc")})
public class Region implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idregion", nullable = false)
	private Integer idregion;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "reg_desc", nullable = false, length = 255)
	private String regDesc;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "regionid")
	private Collection<Mentor> mentorCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "regionid")
	private Collection<Usuario> usuarioCollection;

	public Region() {
	}

	public Region(Integer idregion) {
		this.idregion = idregion;
	}

	public Region(Integer idregion, String regDesc) {
		this.idregion = idregion;
		this.regDesc = regDesc;
	}

	public Integer getIdregion() {
		return idregion;
	}

	public void setIdregion(Integer idregion) {
		this.idregion = idregion;
	}

	public String getRegDesc() {
		return regDesc;
	}

	public void setRegDesc(String regDesc) {
		this.regDesc = regDesc;
	}

	@XmlTransient
	public Collection<Mentor> getMentorCollection() {
		return mentorCollection;
	}

	public void setMentorCollection(Collection<Mentor> mentorCollection) {
		this.mentorCollection = mentorCollection;
	}

	@XmlTransient
	public Collection<Usuario> getUsuarioCollection() {
		return usuarioCollection;
	}

	public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
		this.usuarioCollection = usuarioCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idregion != null ? idregion.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Region)) {
			return false;
		}
		Region other = (Region) object;
		if ((this.idregion == null && other.idregion != null) || (this.idregion != null && !this.idregion.equals(other.idregion))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "socio.entities.Region[ idregion=" + idregion + " ]";
	}
	
}
