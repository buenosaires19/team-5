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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "profesion", catalog = "socios", schema = "")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Profesion.findAll", query = "SELECT p FROM Profesion p")
	, @NamedQuery(name = "Profesion.findByIdprofesion", query = "SELECT p FROM Profesion p WHERE p.idprofesion = :idprofesion")
	, @NamedQuery(name = "Profesion.findByProfesionDesc", query = "SELECT p FROM Profesion p WHERE p.profesionDesc = :profesionDesc")})
public class Profesion implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprofesion", nullable = false)
	private Integer idprofesion;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "profesion_desc", nullable = false, length = 255)
	private String profesionDesc;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "profesionid")
	private Collection<Mentor> mentorCollection;
	@JoinColumn(name = "videoid", referencedColumnName = "idvideos", nullable = false)
    @ManyToOne(optional = false)
	private Videos videoid;

	public Profesion() {
	}

	public Profesion(Integer idprofesion) {
		this.idprofesion = idprofesion;
	}

	public Profesion(Integer idprofesion, String profesionDesc) {
		this.idprofesion = idprofesion;
		this.profesionDesc = profesionDesc;
	}

	public Integer getIdprofesion() {
		return idprofesion;
	}

	public void setIdprofesion(Integer idprofesion) {
		this.idprofesion = idprofesion;
	}

	public String getProfesionDesc() {
		return profesionDesc;
	}

	public void setProfesionDesc(String profesionDesc) {
		this.profesionDesc = profesionDesc;
	}

	@XmlTransient
	public Collection<Mentor> getMentorCollection() {
		return mentorCollection;
	}

	public void setMentorCollection(Collection<Mentor> mentorCollection) {
		this.mentorCollection = mentorCollection;
	}

	public Videos getVideoid() {
		return videoid;
	}

	public void setVideoid(Videos videoid) {
		this.videoid = videoid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idprofesion != null ? idprofesion.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Profesion)) {
			return false;
		}
		Profesion other = (Profesion) object;
		if ((this.idprofesion == null && other.idprofesion != null) || (this.idprofesion != null && !this.idprofesion.equals(other.idprofesion))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "socio.entities.Profesion[ idprofesion=" + idprofesion + " ]";
	}
	
}
