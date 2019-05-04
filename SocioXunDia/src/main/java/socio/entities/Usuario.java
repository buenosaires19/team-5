/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socio.entities;

public class Usuario {
	
	private String nombre;
	private String apellido;
	private int dni;
	private String ciudad;
	private String provincia;
	private int codpostal;
	private String calle;
	private int altura;
	
	public Usuario() {
		nombre="";
		apellido="";
		dni=0;
		ciudad="";
		provincia="";
		codpostal=0;
		calle="";
		altura=0;
	}
	
	public Usuario(String nombre,String ap,int dni,String ciudad,String provincia,int codpostal,String calle,int alt) {
		this.nombre=nombre;
		this.apellido=ap;
		this.dni=dni;
		this.ciudad=ciudad;
		this.provincia=provincia;
		this.codpostal=codpostal;
		this.calle=calle;
		altura=alt;
	}
	
	public void setNombre(String nombre) {
		this.nombre=nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public void setApellido(String ape) {
		apellido=ape;
	}
	
	public String getApellido() {
		return apellido;
	}
	
	public void setDni(int dni) {
		this.dni=dni;
	}
	
	public int getDni() {
		return dni;
	}
	
	public void setCiudad(String ciudad) {
		this.ciudad=ciudad;
	}
	
	public String getCiudad() {
		return ciudad;
	}
	
	public void setProvincia(String prov) {
		provincia=prov;
	}
	
	public String getProvincia() {
		return provincia;
	}
	
	public void setCodPostal(int cod) {
		codpostal=cod;
	}
	
	public int getCodPostal() {
		return codpostal;
	}
	
	public void setCalle(String calle) {
		this.calle=calle;
	}
	
	public String getCalle() {
		return calle;
	}
	
	public void setAltura(int alt) {
		altura=alt;
	}
	
	public int getAltura() {
		return altura;
	}
	
	//nombre, apellido, dni, ciudad, provincia, calle, nro, cod postal
	
	@Override
	public String toString() {
		return nombre+" "+apellido+"\nDNI: "+dni+"\nProvincia: "+provincia+"\nCiudad: "+ciudad+"\nCódigo Postal: "+codpostal+"\nDirección: "+calle+" "+altura;
	}
}