package com.calzados.practica2ud3;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "comprador_calzado", schema = "tiendacalzados", catalog = "")
public class CompradorCalzado {

    private int idcompradorcalzado;

    @Id
    @Column(name = "idcompradorcalzado")
    public int getIdcompradorcalzado() {
        return idcompradorcalzado;
    }

    public void setIdcompradorcalzado(int idcompradorcalzado) {
        this.idcompradorcalzado = idcompradorcalzado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompradorCalzado that = (CompradorCalzado) o;
        return idcompradorcalzado == that.idcompradorcalzado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idcompradorcalzado);
    }
}
