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
@Table(name = "videos", catalog = "socios", schema = "")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Videos.findAll", query = "SELECT v FROM Videos v")
	, @NamedQuery(name = "Videos.findByIdvideos", query = "SELECT v FROM Videos v WHERE v.idvideos = :idvideos")
	, @NamedQuery(name = "Videos.findByUrl", query = "SELECT v FROM Videos v WHERE v.url = :url")})
public class Videos implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idvideos", nullable = false)
	private Integer idvideos;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "url", nullable = false, length = 255)
	private String url;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "videoid")
	private Collection<Usuario> usuarioCollection;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "videoid")
	private Collection<Profesion> profesionCollection;

	public Videos() {
	}

	public Videos(Integer idvideos) {
		this.idvideos = idvideos;
	}

	public Videos(Integer idvideos, String url) {
		this.idvideos = idvideos;
		this.url = url;
	}

	public Integer getIdvideos() {
		return idvideos;
	}

	public void setIdvideos(Integer idvideos) {
		this.idvideos = idvideos;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@XmlTransient
	public Collection<Usuario> getUsuarioCollection() {
		return usuarioCollection;
	}

	public void setUsuarioCollection(Collection<Usuario> usuarioCollection) {
		this.usuarioCollection = usuarioCollection;
	}

	@XmlTransient
	public Collection<Profesion> getProfesionCollection() {
		return profesionCollection;
	}

	public void setProfesionCollection(Collection<Profesion> profesionCollection) {
		this.profesionCollection = profesionCollection;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idvideos != null ? idvideos.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Videos)) {
			return false;
		}
		Videos other = (Videos) object;
		if ((this.idvideos == null && other.idvideos != null) || (this.idvideos != null && !this.idvideos.equals(other.idvideos))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "socio.entities.Videos[ idvideos=" + idvideos + " ]";
	}
	
}
