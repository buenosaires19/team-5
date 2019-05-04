/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author NetLenovo
 */
public class Mentor {

    private int idmentor;
    private String nombre;
    private String apellido;
    private String profesion;

    public Mentor() {
    }

    @Override
    public String toString() {
        return "Mentor{" + "idmentor=" + idmentor + ", nombre=" + nombre + ", apellido=" + apellido + ", profesion=" + profesion + '}';
    }

    public int getIdmentor() {
        return idmentor;
    }

    public void setIdmentor(int idmentor) {
        this.idmentor = idmentor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public Mentor(String nombre, String apellido, String profesion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.profesion = profesion;
    }

    public Mentor(int idmentor, String nombre, String apellido, String profesion) {
        this.idmentor = idmentor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.profesion = profesion;
    }

}
