package com.calzados.practica2ud3;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tienda")
public class Tienda {

    private int idtienda;
    private String nombre;
    private String telefono;
    private List<Comprador> compradores;
    private List<VentaCalzado> ventas;

    @Override
    public String toString() {
        return "Nombre: " + nombre + " | Teléfono: " + telefono;
    }

    @Id
    @Column(name = "idtienda")
    public int getIdtienda() {
        return idtienda;
    }

    public void setIdtienda(int idtienda) {
        this.idtienda = idtienda;
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
        Tienda tienda = (Tienda) o;
        return idtienda == tienda.idtienda &&
                Objects.equals(nombre, tienda.nombre) &&
                Objects.equals(telefono, tienda.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idtienda, nombre, telefono);
    }

    @ManyToMany(mappedBy = "tiendas")
    public List<Comprador> getCompradores() {
        return compradores;
    }

    public void setCompradores(List<Comprador> compradores) {
        this.compradores = compradores;
    }

    @OneToMany(mappedBy = "tienda")
    public List<VentaCalzado> getVentas() {
        return ventas;
    }

    public void setVentas(List<VentaCalzado> ventas) {
        this.ventas = ventas;
    }
}
