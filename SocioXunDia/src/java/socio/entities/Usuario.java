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
@Table(name = "usuario", catalog = "socios", schema = "")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
	, @NamedQuery(name = "Usuario.findByIdusuario", query = "SELECT u FROM Usuario u WHERE u.idusuario = :idusuario")
	, @NamedQuery(name = "Usuario.findByNombreusuario", query = "SELECT u FROM Usuario u WHERE u.nombreusuario = :nombreusuario")
	, @NamedQuery(name = "Usuario.findByApellidousuario", query = "SELECT u FROM Usuario u WHERE u.apellidousuario = :apellidousuario")
	, @NamedQuery(name = "Usuario.findByMailusuario", query = "SELECT u FROM Usuario u WHERE u.mailusuario = :mailusuario")})
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idusuario", nullable = false)
	private Integer idusuario;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nombreusuario", nullable = false, length = 255)
	private String nombreusuario;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "apellidousuario", nullable = false, length = 255)
	private String apellidousuario;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "mailusuario", nullable = false, length = 255)
	private String mailusuario;
	@JoinColumn(name = "regionid", referencedColumnName = "idregion", nullable = false)
    @ManyToOne(optional = false)
	private Region regionid;
	@JoinColumn(name = "nivelid", referencedColumnName = "idnivel", nullable = false)
    @ManyToOne(optional = false)
	private Nivel nivelid;
	@JoinColumn(name = "videoid", referencedColumnName = "idvideos", nullable = false)
    @ManyToOne(optional = false)
	private Videos videoid;

	public Usuario() {
	}

	public Usuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public Usuario(Integer idusuario, String nombreusuario, String apellidousuario, String mailusuario) {
		this.idusuario = idusuario;
		this.nombreusuario = nombreusuario;
		this.apellidousuario = apellidousuario;
		this.mailusuario = mailusuario;
	}

	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	public String getNombreusuario() {
		return nombreusuario;
	}

	public void setNombreusuario(String nombreusuario) {
		this.nombreusuario = nombreusuario;
	}

	public String getApellidousuario() {
		return apellidousuario;
	}

	public void setApellidousuario(String apellidousuario) {
		this.apellidousuario = apellidousuario;
	}

	public String getMailusuario() {
		return mailusuario;
	}

	public void setMailusuario(String mailusuario) {
		this.mailusuario = mailusuario;
	}

	public Region getRegionid() {
		return regionid;
	}

	public void setRegionid(Region regionid) {
		this.regionid = regionid;
	}

	public Nivel getNivelid() {
		return nivelid;
	}

	public void setNivelid(Nivel nivelid) {
		this.nivelid = nivelid;
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
		hash += (idusuario != null ? idusuario.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Usuario)) {
			return false;
		}
		Usuario other = (Usuario) object;
		if ((this.idusuario == null && other.idusuario != null) || (this.idusuario != null && !this.idusuario.equals(other.idusuario))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "socio.entities.Usuario[ idusuario=" + idusuario + " ]";
	}
	
}
