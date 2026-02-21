package com.calzados.practica2ud3;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Calzado {

    private int idcalzado;
    private String modelo;
    private String sku;
    private List<Comprador> compradores;
    private List<VentaCalzado> ventas;
    private Marca marca;

    @Id
    @Column(name = "idcalzado")
    public int getIdcalzado() {
        return idcalzado;
    }

    public void setIdcalzado(int idcalzado) {
        this.idcalzado = idcalzado;
    }

    @Basic
    @Column(name = "modelo")
    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Basic
    @Column(name = "sku")
    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Calzado calzado = (Calzado) o;
        return idcalzado == calzado.idcalzado &&
                Objects.equals(modelo, calzado.modelo) &&
                Objects.equals(sku, calzado.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcalzado, modelo, sku);
    }

    @ManyToMany(mappedBy = "calzados")
    public List<Comprador> getCompradores() {
        return compradores;
    }

    public void setCompradores(List<Comprador> compradores) {
        this.compradores = compradores;
    }

    @OneToMany(mappedBy = "calzado")
    public List<VentaCalzado> getVentas() {
        return ventas;
    }

    public void setVentas(List<VentaCalzado> ventas) {
        this.ventas = ventas;
    }

    @ManyToOne
    @JoinColumn(name = "idmarca", referencedColumnName = "idmarca")
    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "Modelo: " + modelo + " | SKU: " + sku + " | Marca: " + marca;
    }
}
