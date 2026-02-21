package com.calzados.practica2ud3;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "calzado_tienda", schema = "tiendacalzados", catalog = "")
public class VentaCalzado {

    private int idcalzadotienda;
    private Integer precio;
    private Calzado calzado;
    private Tienda tienda;

    @Override
    public String toString() {
        return "Calzado: " + calzado.getModelo() + " | Tienda: " + tienda.getNombre() + " | Precio: " + precio;
    }

    @Id
    @Column(name = "idcalzadotienda")
    public int getIdcalzadotienda() {
        return idcalzadotienda;
    }

    public void setIdcalzadotienda(int idcalzadotienda) {
        this.idcalzadotienda = idcalzadotienda;
    }

    @Basic
    @Column(name = "precio")
    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VentaCalzado that = (VentaCalzado) o;
        return idcalzadotienda == that.idcalzadotienda &&
                Objects.equals(precio, that.precio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcalzadotienda, precio);
    }

    @ManyToOne
    @JoinColumn(name = "idcalzado", referencedColumnName = "idcalzado")
    public Calzado getCalzado() {
        return calzado;
    }

    public void setCalzado(Calzado calzado) {
        this.calzado = calzado;
    }

    @ManyToOne
    @JoinColumn(name = "idtienda", referencedColumnName = "idtienda")
    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }
}
