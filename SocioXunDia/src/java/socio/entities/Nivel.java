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
@Table(name = "nivel", catalog = "socios", schema = "")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Nivel.findAll", query = "SELECT n FROM Nivel n")
	, @NamedQuery(name = "Nivel.findByIdnivel", query = "SELECT n FROM Nivel n WHERE n.idnivel = :idnivel")
	, @NamedQuery(name = "Nivel.findByNivelDesc", query = "SELECT n FROM Nivel n WHERE n.nivelDesc = :nivelDesc")})
public class Nivel implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idnivel", nullable = false)
	private Integer idnivel;
	@Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nivel_desc", nullable = false, length = 255)
	private String nivelDesc;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "nivelid")
	private Collection<Usuario> usuarioCollection;

	public Nivel() {
	}

	public Nivel(Integer idnivel) {
		this.idnivel = idnivel;
	}

	public Nivel(Integer idnivel, String nivelDesc) {
		this.idnivel = idnivel;
		this.nivelDesc = nivelDesc;
	}

	public Integer getIdnivel() {
		return idnivel;
	}

	public void setIdnivel(Integer idnivel) {
		this.idnivel = idnivel;
	}

	public String getNivelDesc() {
		return nivelDesc;
	}

	public void setNivelDesc(String nivelDesc) {
		this.nivelDesc = nivelDesc;
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
		hash += (idnivel != null ? idnivel.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Nivel)) {
			return false;
		}
		Nivel other = (Nivel) object;
		if ((this.idnivel == null && other.idnivel != null) || (this.idnivel != null && !this.idnivel.equals(other.idnivel))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "socio.entities.Nivel[ idnivel=" + idnivel + " ]";
	}
	
}
