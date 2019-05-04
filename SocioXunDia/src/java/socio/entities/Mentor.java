/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socio.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author seba
 */
@Entity
@Table(name = "mentor", catalog = "socios", schema = "")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Mentor.findAll", query = "SELECT m FROM Mentor m")
	, @NamedQuery(name = "Mentor.findByIdmentor", query = "SELECT m FROM Mentor m WHERE m.idmentor = :idmentor")
	, @NamedQuery(name = "Mentor.findByNombrementor", query = "SELECT m FROM Mentor m WHERE m.nombrementor = :nombrementor")
	, @NamedQuery(name = "Mentor.findByApellidomentor", query = "SELECT m FROM Mentor m WHERE m.apellidomentor = :apellidomentor")
	, @NamedQuery(name = "Mentor.findByMailmentor", query = "SELECT m FROM Mentor m WHERE m.mailmentor = :mailmentor")})
public class Mentor implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmentor", nullable = false)
	private Integer idmentor;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombrementor", nullable = false, length = 255)
	private String nombrementor;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "apellidomentor", nullable = false, length = 255)
	private String apellidomentor;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "mailmentor", nullable = false, length = 255)
	private String mailmentor;
	@JoinColumn(name = "regionid", referencedColumnName = "idregion", nullable = false)
    @ManyToOne(optional = false)
	private Region regionid;
	@JoinColumn(name = "profesionid", referencedColumnName = "idprofesion", nullable = false)
    @ManyToOne(optional = false)
	private Profesion profesionid;

	public Mentor() {
	}

	public Mentor(Integer idmentor) {
		this.idmentor = idmentor;
	}

	public Mentor(Integer idmentor, String nombrementor, String apellidomentor, String mailmentor) {
		this.idmentor = idmentor;
		this.nombrementor = nombrementor;
		this.apellidomentor = apellidomentor;
		this.mailmentor = mailmentor;
	}

	public Integer getIdmentor() {
		return idmentor;
	}

	public void setIdmentor(Integer idmentor) {
		this.idmentor = idmentor;
	}

	public String getNombrementor() {
		return nombrementor;
	}

	public void setNombrementor(String nombrementor) {
		this.nombrementor = nombrementor;
	}

	public String getApellidomentor() {
		return apellidomentor;
	}

	public void setApellidomentor(String apellidomentor) {
		this.apellidomentor = apellidomentor;
	}

	public String getMailmentor() {
		return mailmentor;
	}

	public void setMailmentor(String mailmentor) {
		this.mailmentor = mailmentor;
	}

	public Region getRegionid() {
		return regionid;
	}

	public void setRegionid(Region regionid) {
		this.regionid = regionid;
	}

	public Profesion getProfesionid() {
		return profesionid;
	}

	public void setProfesionid(Profesion profesionid) {
		this.profesionid = profesionid;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idmentor != null ? idmentor.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Mentor)) {
			return false;
		}
		Mentor other = (Mentor) object;
		if ((this.idmentor == null && other.idmentor != null) || (this.idmentor != null && !this.idmentor.equals(other.idmentor))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "socio.entities.Mentor[ idmentor=" + idmentor + " ]";
	}
	
}
