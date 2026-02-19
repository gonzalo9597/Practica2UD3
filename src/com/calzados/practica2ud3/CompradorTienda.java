package com.calzados.practica2ud3;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "comprador_tienda", schema = "tiendacalzados", catalog = "")
public class CompradorTienda {
    private int idcompradortienda;

    @Id
    @Column(name = "idcompradortienda")
    public int getIdcompradortienda() {
        return idcompradortienda;
    }

    public void setIdcompradortienda(int idcompradortienda) {
        this.idcompradortienda = idcompradortienda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompradorTienda that = (CompradorTienda) o;
        return idcompradortienda == that.idcompradortienda;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcompradortienda);
    }
}
