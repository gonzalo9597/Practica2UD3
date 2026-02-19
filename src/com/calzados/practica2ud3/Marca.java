package com.calzados.practica2ud3;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "marca")
public class Marca {

    private int idmarca;
    private String nombre;
    private String telefono;
    private List<Calzado> calzados;

    @Override
    public String toString() {
        return "Nombre: " + nombre + " | Teléfono: " + telefono;
    }

    @Id
    @Column(name = "idmarca")
    public int getIdmarca() {
        return idmarca;
    }

    public void setIdmarca(int idmarca) {
        this.idmarca = idmarca;
    }

    @Basic
    @Column(name = "nombre")
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Basic
    @Column(name = "telefono")
    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marca marca = (Marca) o;
        return idmarca == marca.idmarca &&
                Objects.equals(nombre, marca.nombre) &&
                Objects.equals(telefono, marca.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idmarca, nombre, telefono);
    }

    @OneToMany(mappedBy = "marca")
    public List<Calzado> getCalzados() {
        return calzados;
    }

    public void setCalzados(List<Calzado> calzados) {
        this.calzados = calzados;
    }
}
